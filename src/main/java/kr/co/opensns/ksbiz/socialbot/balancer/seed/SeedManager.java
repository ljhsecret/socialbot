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
	
	HashMap<String,SeedQueue> queueMap;
	SeedLoader loader;
	Logger logger;
	
	public SeedManager(){
		logger=Logger.getLogger(this.getClass());
		queueMap = new HashMap<String,SeedQueue>();
	}
	
	public SeedManager(BalancerConfig conf) {
		this();
		List<HashMap<String,String>> seedconf = conf.getSeedConfig();
		for (HashMap<String, String> map : seedconf){
			if("local".equals(map.get("repository"))){
				loader = new SeedLoader<FileSeedLoader>(FileSeedLoader.class);
			} else
				continue;
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

	public SeedEntity getSeedEntity(String site){
		return queueMap.get(site).take();
	}

	public void update(SeedEntity seed) {
		
	}
}
