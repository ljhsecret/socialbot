package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.BalancerConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.exception.BalancerException;

/**
 * 클래스 설명
 *
 * <pre>
 * <br>
 * <b>History:</b>
 * 		mhyoo, v1.0.0, 2015. 10. 20., 최초작성
 * </pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class SeedManager {

	private static SeedManager instance;
	private HashMap<String, SeedQueue> queueMap;
	@SuppressWarnings("rawtypes")
	private SeedLoader loader;
	private Logger logger;
	private List<HashMap<String, String>> seedConfig;

	private Map<String, Double> weightPerSite;
	private Map<String, Integer> jobCountPerSite;

	private int sumOfWeight;

	private static int TOTAL_JOB_COUNT = 0;

	public static SeedManager getinstance() {
		if (instance == null) {
			synchronized (SeedManager.class) {
				instance = new SeedManager();
				return instance;
			}
		}
		return instance;
	}

	private SeedManager() {
		logger = Logger.getLogger(this.getClass());
		queueMap = new HashMap<String, SeedQueue>();
	}

	private void init() {
		weightPerSite = new HashMap<String, Double>();
		jobCountPerSite = new HashMap<String, Integer>();
	}

	private void Load() throws Exception {
		for (HashMap<String, String> map : seedConfig) {
			String repository = map.get("repository");
			if (repository == null)
				throw new BalancerException("seed repository is empty");

			if ("local".equals(repository)) {
				loader = new SeedLoader<FileSeedLoader>(FileSeedLoader.class);
			} else if ("db".equals(repository))
				loader = new SeedLoader<DBSeedLoader>(DBSeedLoader.class);
			else
				continue;

			// 모듈화, Exception handling
			// 추가해야함

			SeedQueue q;
			try {
				String path = map.get("path");
				String site = map.get("site");
				String type = map.get("type");
				String key = site + "-" + type;
				q = loader.LoadSeedQueue(path, key);
				queueMap.put(key, q);
				logger.info("SeedQueue Load done : " + key);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// e.printStackTrace();
				logger.info("SeedQueue Load fail");
			}
		}
	}

	public void setConfig(BalancerConfig conf) {
		if (conf == null) {
			logger.info("Configuration setting was not complete");
			return;
		}
		seedConfig = conf.getSeedConfig();

		init();
		initWeightValue();
		try {
			Load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initWeightValue() {
		for (HashMap<String, String> map : seedConfig) {
			int weight = Integer.parseInt(map.get("weight"));
			sumOfWeight += weight;
		}

		for (HashMap<String, String> map : seedConfig) {
			String key = map.get("site") + "-" + map.get("type");
			int weight = Integer.parseInt(map.get("weight"));

			double rate = (double) weight / sumOfWeight;

			weightPerSite.put(key, rate);
			jobCountPerSite.put(key, 0);
		}
	}

	public SeedEntity getSeedEntity() {
		String site = getSite();
		SeedEntity seed = queueMap.get(site).take();
		TOTAL_JOB_COUNT++;
		jobCountPerSite.put(site, jobCountPerSite.get(site) + 1);
		logger.info("GET SEED : site - " + site + ", seed - " + seed.getSeed()
				+ ", priority - " + seed.getPriority());
		return seed;
	}

	public void update(String seed, Map<String, String> fields) {

	}

	private String getSite() {
		String site = null;
		double distance = 0;

		for (String key : weightPerSite.keySet()) {
			double weight = weightPerSite.get(key);
			int jobCount = jobCountPerSite.get(key);

			if (TOTAL_JOB_COUNT == 0 || jobCount == 0) {
				return key;
			}

			double tmpdistance = (double) jobCount / TOTAL_JOB_COUNT - weight;

			if (distance >= tmpdistance) {
				distance = tmpdistance;
				site = key;
			}
		}

		return site;
	}
}
