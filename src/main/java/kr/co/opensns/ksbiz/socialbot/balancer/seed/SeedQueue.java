package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

import kr.co.opensns.ksbiz.socialbot.balancer.exception.BalancerException;

import org.apache.log4j.Logger;

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

public class SeedQueue {

	Logger logger;
	
	PriorityBlockingQueue<SeedEntity> queue = new PriorityBlockingQueue<>(
			10000000, new SeedComprator());
	
	Map<String, SeedEntity> bufMap = new HashMap<String, SeedEntity>();
	
	String type;

	public SeedQueue(String type) {
		this.logger = Logger.getLogger(this.getClass());
		this.type = type;
	}

	public void put(SeedEntity seed) {
		seed.setType(type);
		queue.put(seed);
	}
	
	public void update(String seedAsString,Map<String,String> field) throws BalancerException{
		if(bufMap.containsKey(seedAsString)){
			SeedEntity seed = bufMap.get(seedAsString);
			seed.update(field);
			queue.put(seed);
		} else {
			throw new BalancerException("not exist seed("+seedAsString+") in buffer map");
		}
	}

	public SeedEntity take() {
		SeedEntity seed;
		try {
			seed = queue.take();
			bufMap.put(seed.getSeed(), seed);
			logger.info("QUEUE SIZE : "+queue.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			seed = null;
		}
		
		return seed; 
		
//		return queue.peek();
	}
}
