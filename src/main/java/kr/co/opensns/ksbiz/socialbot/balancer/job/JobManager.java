package kr.co.opensns.ksbiz.socialbot.balancer.job;

import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentManager;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedManager;

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

public class JobManager implements Runnable{
	private static long TOT_JOB_COUNT;
	
	AgentManager agentManager;
	SeedManager seedManager;
	
	
	public JobManager(){
		
	}
	
	public void run() {
		
	}
	
}
