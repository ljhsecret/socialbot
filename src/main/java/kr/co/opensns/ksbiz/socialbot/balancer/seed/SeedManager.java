package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.Manager;
import kr.co.opensns.ksbiz.socialbot.balancer.config.BalancerConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.config.SeedConfig;
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

public class SeedManager implements Manager {

	private static SeedManager instance;
	private HashMap<String, SeedQueue> queueMap;
	private SeedLoader<?> loader;
	private Logger logger;
	private List<SeedConfig> seedConfList;

	private Map<String, Double> weightPerSite;
	private Map<String, Integer> jobCountPerSite;

	private int sumOfWeight;

	private static int TOTAL_JOB_COUNT = 0;

	public static SeedManager getInstance() {
		if (instance == null) {
			synchronized (SeedManager.class) {
				instance = new SeedManager();
				return instance;
			}
		}
		return instance;
	}

	private SeedManager() {
		init();
	}

	private void init() {
		logger = Logger.getLogger(this.getClass());
		queueMap = new HashMap<String, SeedQueue>();
		weightPerSite = new HashMap<String, Double>();
		jobCountPerSite = new HashMap<String, Integer>();
	}

	public void setConfig(BalancerConfig conf) {
		if (conf == null) {
			logger.info("Configuration setting was not complete");
			return;
		}
		seedConfList = conf.getSeedConfig();

		initWeightValue();
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initWeightValue() {
		for (SeedConfig seedConf : seedConfList) {
			int weight = Integer.parseInt(seedConf.getCrawlWeight());
			sumOfWeight += weight;
		}

		for (SeedConfig seedConf: seedConfList) {
			String key = seedConf.getSite() + "-" + seedConf.getSeedType();
			int weight = Integer.parseInt(seedConf.getCrawlWeight());

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
		logger.info("GET SEED : site - " + site + ", seed - " + seed.getSeedId()
				+ ", priority - " + seed.getPriority());
		return seed;
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

	@Override
	public void load() throws BalancerException {
		for (SeedConfig seedConf : seedConfList) {
			String repository = seedConf.getRepository();
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
				String path = seedConf.getRepositoryPath();
				String seedType = seedConf.getSeedType();
				String site = seedConf.getSite();
				String key = site+"-"+seedType;
				
				HashMap<String,String> fields = new HashMap<String,String>();
				
				fields.put("type", seedType);
				fields.put("siteId", site);
				
				q = loader.LoadSeedQueue(seedConf);
				queueMap.put(key, q);
				logger.info("SeedQueue Load done : " + key);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				 e.printStackTrace();
				logger.info("SeedQueue Load fail : " + e.getMessage());
			}
		}
	}

	@Override
	public void update(String key, Map<String, String> fields) {
		String[] tmp = key.split("-");
		String site = tmp[0];
		String seedId = tmp[1];
		
		try {
			queueMap.get(site).update(seedId, fields);
		} catch (BalancerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String monitor() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{\"count\":"+queueMap.size()+",");
		sb.append("\"data\":[");
		for(Iterator iter = queueMap.keySet().iterator();iter.hasNext();){
			SeedQueue queue = (SeedQueue) queueMap.get(iter.next());
			sb.append("{\"seed_type\":\""+queue.getType()+"\",");
			sb.append("\"queue_size\":"+queue.getQueueSize()+",");
			sb.append("\"buffer_size\":"+queue.getBufSize()+"}");
			if(iter.hasNext())
				sb.append(",");
			else
				sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
}
