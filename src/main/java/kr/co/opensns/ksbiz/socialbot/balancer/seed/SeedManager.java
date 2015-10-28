package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.HashMap;
import java.util.List;

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
	public SeedManager(){
		
	}
	
	public SeedManager(BalancerConfig conf) {
		List<HashMap<String,String>> seedconf = conf.getSeedConfig();
		for (HashMap<String, String> map : seedconf){
			if("local".equals(map.get("repository"))){
				loader = new SeedLoader<FileSeedLoader>();
			}
			SeedQueue q = loader.LoadSeedQueue(map.get("path"),map.get("site")+"-"+map.get("type"));
		}
	}

	public SeedEntity getSeedEntity(String site){
		return queueMap.get(site).take();
	}

	public void update(SeedEntity seed) {
		
	}
}
