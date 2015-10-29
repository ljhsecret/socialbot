package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.concurrent.PriorityBlockingQueue;

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

public class SeedQueue{
	
	PriorityBlockingQueue<SeedEntity> queue = new PriorityBlockingQueue<>(10000000, new SeedComprator());
	String type;
	
	public SeedQueue(String type){
		this.type = type;
	}
	
	public void put(SeedEntity seed){
		seed.setType(type);
		queue.put(seed);
	}
	
	public SeedEntity take(){
		try {
			return queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new NullPointerException("Queue is Empty");
		}
	}
}
