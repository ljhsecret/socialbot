package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.security.auth.login.Configuration;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.google.gson.annotations.Until;

import kr.co.opensns.ksbiz.socialbot.balancer.Manager;
import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentInfo;
import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentManager;
import kr.co.opensns.ksbiz.socialbot.balancer.config.BalancerConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.exception.SharedJobTableException;
import kr.co.opensns.ksbiz.socialbot.balancer.http.client.JobRequestEvent;
import kr.co.opensns.ksbiz.socialbot.balancer.http.client.JobRequestThread;
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

public class JobManager implements Manager {

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
		if (this.conf != null) {
			return;
		}
		this.conf = conf;
	}

	private JobManager() {
		logger = Logger.getLogger(this.getClass());

		jobTable = SharedJobTable.getInstance();
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

	public JobEntity getJobEntity(String jobId) {
		// jobTable.
		return null;
	}

	public void doJob(final JobEntity job) {
		JobRequestThread client;
		client = new JobRequestThread();
		client.setJob(job);
		client.setHttpStatusListener(new HttpStatusListener() {


			public void onGetResponseFromAgent(JobRequestEvent event) {
				logger.info("[" + Thread.currentThread().getId() + "]"
						+ "Get response about " + event.getJobId()
						+ " from agent");
				try {
					jobTable.update(event.getJobId(), event.getJobStatus());
				} catch (SharedJobTableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onSendRequestToAgent(JobRequestEvent event) {
				logger.info("[" + Thread.currentThread().getId() + "]"
						+ event.getCurrentSeed() + " sent to agent("
						+ event.getJobAgentIP() + ")- Job Id : "
						+ event.getJobId());
				try {
					jobTable.update(job.getJobId(), event.getJobStatus());
				} catch (SharedJobTableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onRequestTimeout(JobRequestEvent event) {
				logger.info("[" + Thread.currentThread().getId() + "]"
						+ "Request Time Out : Job ID - " + event.getJobId());
				try {
					jobTable.update(event.getJobId(), event.getJobStatus());
				} catch (SharedJobTableException e) {
					e.printStackTrace();
				}
			}
		});

		new Thread(client).start();
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

	@Override
	@Deprecated
	public void load() {

	}
	
	@Override
	public void update(String key, Map<String, String> fields) {
		String jobId = key;
		String resultCode = fields.get("StatusCode");
		
		try {
			switch (resultCode) {
			case "01":
				jobTable.update(jobId, JobStatus.DONE);
				break;
			case "02":
				jobTable.update(jobId, JobStatus.ERROR);
				break;

			default:
				break;
			}
			
		} catch (SharedJobTableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String monitor() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"max_job_count\":" + jobTable.getMaxSize() + ",");
		sb.append("\"current_job_count\":" + jobTable.getSize() + ",");
		sb.append("\"jobs\":[");
		for (Iterator iter = jobTable.JobIdSet().iterator(); iter.hasNext();) {
			String jobId = (String) iter.next();
			JobEntity job = null;
			try {
				job = jobTable.get(jobId);
			} catch (SharedJobTableException e) {
				logger.info(e);
				continue;
			}
			sb.append("{\"jobId\":\"" + job.getJobId() + "\",");
			sb.append("\"agent_ip\":\"" + job.getAgent().getIp() + "\",");
			sb.append("\"seed\":\"" + job.getSeed().getSeedId() + "\",");
			sb.append("\"site\":\"" + job.getSeed().getSiteId() + "\",");
			sb.append("\"type\":\"" + job.getSeed().getCrawlType() + "\"}");
			if (iter.hasNext())
				sb.append(",");
			else
				sb.append("]}");
		}
		return sb.toString();
	}
}
