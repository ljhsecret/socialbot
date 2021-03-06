package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.Configuration;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.BalancerConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentInfo;
import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentManager;
import kr.co.opensns.ksbiz.socialbot.balancer.exception.SharedJobTableException;
import kr.co.opensns.ksbiz.socialbot.balancer.http.client.HttpClientAsThread;
import kr.co.opensns.ksbiz.socialbot.balancer.http.client.HttpStatusListener;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedEntity;
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

public class JobManager /* implements Runnable */{

	private static long TOT_JOB_COUNT;

	SharedJobTable jobTable;

	private static JobManager instance;

	BalancerConfig conf;

	Logger logger;

	public static JobManager getInstance() {
		if (instance == null) {
			synchronized (JobManager.class) {
				instance = new JobManager();
			}
		}
		return instance;
	}

	public void setConfig(BalancerConfig conf) {
		if (this.conf!=null) {
			return;
		} 
		this.conf = conf;
	}

	private JobManager() {
		logger = Logger.getLogger(this.getClass());

		jobTable = SharedJobTable.getInstance();
		jobTable.setJobStatusListener(new JobStatusListener() {

			@Override
			public void onRequireJob() {

			}

			@Override
			public void onOccurErrorJob(JobEntity job) {
				AgentInfo agent = job.getAgent();
				SeedEntity seed = job.getSeed();
			}

			@Override
			public void onCompleteJob(JobEntity job) {

			}
		});

		// agentManager = AgentManager.getInstance();
		// agentManager.setConfig(conf);
		//
		// seedManager = SeedManager.getinstance();
		// seedManager.setConfig(conf);
	}

	public int checkRequireJob() {

		return jobTable.checkRequireJob();

	}

	public JobEntity buildJob(SeedEntity seed, AgentInfo agent) {
		JobEntity job = new JobEntity();

		job.setAgent(agent);
		job.setSeed(seed);
		
		try {
			jobTable.put(job.getJobId(), job);
		} catch (SharedJobTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return job;
	}
	
	public JobEntity getJobEntity(String jobId){
//		jobTable.
		return null;
	}

	public void doJob(final JobEntity job) {
		HttpClientAsThread client;
		client = new HttpClientAsThread();
		client.setJob(job);
		client.setHttpStatusListener(new HttpStatusListener() {

			@Override
			public void onSendRequestToAgent(JobStatus status) {
				logger.info("["+Thread.currentThread().getId()+"]"+job.getSeed().getSeed() + " sent to agent("+ job.getAgent().getIp() +")- Job Id : "+job.getJobId());
				update(job.getJobId(),status);
			}

			@Override
			public void onGetResponseFromAgent(JobStatus status) {
				logger.info("["+Thread.currentThread().getId()+"]"+"Get response about " + job.getJobId()
						+ " from agent");
				update(job.getJobId(),status);
			}

			@Override
			public void onRequestTimeout(JobStatus status) {
				logger.info("["+Thread.currentThread().getId()+"]"+"Request Time Out : Job ID - "+job.getJobId());
				update(job.getJobId(),status);
			}
		});

		new Thread(client).start();
	}
	
	public void update(String jobId, JobStatus status){
		try {
			jobTable.update(jobId, status);
		} catch (SharedJobTableException e) {
			e.printStackTrace();
		}
		
	}

	public AgentInfo getAgent(String jobId) {
		AgentInfo agent = null;
		try {
			agent = jobTable.get(jobId).getAgent();
		} catch (SharedJobTableException e) {
			e.printStackTrace();
		}
		return agent;
	}

	public SeedEntity getSeed(String jobId) {
		SeedEntity seed = null;
		try {
			seed = jobTable.get(jobId).getSeed();
		} catch (SharedJobTableException e) {
			e.printStackTrace();
		}
		return seed;
	}

}
