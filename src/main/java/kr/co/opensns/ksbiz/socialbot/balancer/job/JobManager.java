package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.Configuration;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.BalancerConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentManager;
import kr.co.opensns.ksbiz.socialbot.balancer.exception.SharedJobTableException;
import kr.co.opensns.ksbiz.socialbot.balancer.http.HttpStatusListener;
import kr.co.opensns.ksbiz.socialbot.balancer.http.HttpClientAsThread;
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

public class JobManager implements Runnable {

	private static long TOT_JOB_COUNT;

	SharedJobTable jobTable;
	SeedManager seedManager;
	AgentManager agentManager;
	BalancerConfig conf;
	Logger logger;

	public JobManager(BalancerConfig conf) {
		logger = Logger.getLogger(this.getClass());
		
		jobTable = SharedJobTable.getInstance();
		jobTable.setJobStatusListener(new JobStatusListener() {
			
			@Override
			public void onRequireJob() {
				
			}
			
			@Override
			public void onOccurErrorJob(JobEntity job) {
				
			}
			
			@Override
			public void onCompleteJob(JobEntity job) {
				
			}
		});
		
		agentManager = AgentManager.getInstance();
		agentManager.setConfig(conf);
		
		seedManager = SeedManager.getinstance();
		seedManager.setConfig(conf);
	}
	
	public void run() {
		while (true) {
			int CheckResult = jobTable.checkRequireJob();
			
			if (CheckResult > 0) {
				logger.info("Return value from jobtable : "+CheckResult);
				JobEntity job = buildJob();
				buildJobThread(job).start();
				try {
					jobTable.put(job.getJobId(), job);
				} catch (SharedJobTableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("do job");
			}
		}
	}

	public JobEntity buildJob() {
		JobEntity job = new JobEntity();

		job.setAgent(agentManager.getAgentInfo());
		job.setSeed(seedManager.getSeedEntity("instagram"));

		return job;
	}

	public Thread buildJobThread(final JobEntity job) {
		HttpClientAsThread client;
		client = new HttpClientAsThread();
		client.setJob(job);
		client.setHttpStatusListener(new HttpStatusListener() {

			@Override
			public void onSendRequestToAgent(JobStatus status) {
				// TODO Auto-generated method stub
				System.out.println(job.getJobId() + " sent to agent");
				try {
					jobTable.update(job.getJobId(), status);
				} catch (SharedJobTableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onGetResponseFromAgent(JobStatus status) {
				// TODO Auto-generated method stub
				System.out.println("Get response about "
						+ job.getJobId() + " from agent");
				try {
					jobTable.update(job.getJobId(), status);
				} catch (SharedJobTableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		return new Thread(client);
	}
}
