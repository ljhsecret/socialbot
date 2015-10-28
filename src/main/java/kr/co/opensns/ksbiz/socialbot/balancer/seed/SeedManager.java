package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.HashMap;

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
	
	public SeedManager(){
		
	}
	
	public SeedEntity getSeedEntity(String site){
		return queueMap.get(site).poll();
	}

	public void update(SeedEntity seed) {
		
	}
}
