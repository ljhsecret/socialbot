package kr.co.opensns.ksbiz.socialbot.balancer;

import java.util.Map;
import kr.co.opensns.ksbiz.socialbot.balancer.http.HttpPooledServer;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobManager;

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

public class Balancer {

	JobManager jobManager;

	public Balancer(BalancerConfig conf) {
		jobManager = new JobManager(conf);
	}

	public void start() {
		Thread th = new Thread(jobManager);
		th.start();
	}

	public static void main(String[] args) {
		BalancerConfig conf = new BalancerConfig();
		Balancer balancer = new Balancer(conf);

		// --------------------------------------------------------------
		// Start balancer ...
		// --------------------------------------------------------------
		balancer.start();
		System.out.println("Balancer Started ...");
		
		// --------------------------------------------------------------
		// Start Server ...
		// --------------------------------------------------------------
		new HttpPooledServer(8808).start();
		System.out.println("Server Started ...");
	}
}
