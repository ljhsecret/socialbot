package kr.co.opensns.ksbiz.socialbot.balancer;

import java.util.Map;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentManager;
import kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis.ConnectionFactory;
import kr.co.opensns.ksbiz.socialbot.balancer.http.server.HttpPooledServer;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobEntity;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobManager;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedManager;

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

public class Balancer implements Runnable {

	JobManager jobManager;
	SeedManager seedManager;
	AgentManager agentManager;

	Logger logger;

	public Balancer(BalancerConfig conf) {
		jobManager = JobManager.getInstance();
		seedManager = SeedManager.getinstance();
		agentManager = AgentManager.getInstance();

		agentManager.setConfig(conf);
		seedManager.setConfig(conf);
		jobManager.setConfig(conf);

		logger = Logger.getLogger(Balancer.class);
	}

	@Override
	public void run() {
		long remainingJobCount;
		while (true) {
			if ((remainingJobCount = jobManager.checkRequireJob()) > 0) {
				logger.info("Remaining Job Count : " + remainingJobCount);

				JobEntity job = jobManager.buildJob(
						seedManager.getSeedEntity(),
						agentManager.getAgentInfo());

				jobManager.doJob(job);
			}
		}
	}

	private void start() {
		new Thread(this).start();
	}

	public static void main(String[] args) {
		BalancerConfig conf = new BalancerConfig();
		Balancer balancer = new Balancer(conf);

		// --------------------------------------------------------------
		// Start balancer ...
		// --------------------------------------------------------------
		balancer.start();
//		ConnectionFactory cf = new ConnectionFactory();
//		System.out.println(cf.ConnectionTest(0));
		
		System.out.println("Balancer Started ...");

		// --------------------------------------------------------------
		// Start Server ...
		// --------------------------------------------------------------
		new HttpPooledServer(8808).start();
		System.out.println("Server Started ...");
	}
}
