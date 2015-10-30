package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.BalancerConfig;

/**
 * 클래스 설명
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 20., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class SeedManager {
	
	private static SeedManager instance;
    private HashMap<String,SeedQueue> queueMap;
	private SeedLoader loader;
	private Logger logger;
	private BalancerConfig conf;
	
	public static SeedManager getinstance(){
		if(instance==null){
			synchronized (SeedManager.class) {
				instance = new SeedManager();
				return instance;
			}
		}
		return instance;
	}
	
	private SeedManager(){
		logger=Logger.getLogger(this.getClass());
		queueMap = new HashMap<String,SeedQueue>();
	}
	
	private void init(){
		if(conf==null){
			logger.info("Configuration setting was not complete");
			return;
		}
			
		List<HashMap<String,String>> seedconf = conf.getSeedConfig();
		seedLoading(seedconf);
	}
	
	private void seedLoading(List<HashMap<String,String>> seedconf) {
		for (HashMap<String, String> map : seedconf){
			if("local".equals(map.get("repository"))){
				loader = new SeedLoader<FileSeedLoader>(FileSeedLoader.class);
			} else
				continue;
			
//			모듈화, Exception handling
//			추가해야함
			
			SeedQueue q;
			try {
				String path = map.get("path");
				String site = map.get("site");
				String type = map.get("type");
				q = loader.LoadSeedQueue(path,site+"-"+type);
				queueMap.put(site,q);
				logger.info("SeedQueue Load done : "+site+"-"+type);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
//				e.printStackTrace();
				logger.info("SeedQueue Load fail");
			}
		}
	}

	public void setConfig(BalancerConfig conf) {
		this.conf = conf;
		init();
	}

	public SeedEntity getSeedEntity(String site){
		return queueMap.get(site).take();
	}

	public void update(SeedEntity seed) {
		
	}
}
