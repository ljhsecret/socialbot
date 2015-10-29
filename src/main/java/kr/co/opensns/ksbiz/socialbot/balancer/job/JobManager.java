package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.Configuration;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import kr.co.opensns.ksbiz.socialbot.balancer.BalancerConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentManager;
import kr.co.opensns.ksbiz.socialbot.balancer.http.HttpStatusListener;
import kr.co.opensns.ksbiz.socialbot.balancer.http.HttpTestClient;
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
	HttpTestClient client;
	BalancerConfig conf;

	public JobManager() {
		jobTable = SharedJobTable.getInstance();
		agentManager = new AgentManager(conf);
		seedManager = new SeedManager(conf);
		client = new HttpTestClient();
		client.setHttpStatusListener(new HttpStatusListener() {

			@Override
			public void onSendRequestToAgent(Map<String, String> paramMap) {
				// TODO Auto-generated method stub
				System.out.println(paramMap.get("jobId") + " sent to agent");
			}

			@Override
			public void onGetResponseFromAgent(Map<String, String> paramMap) {
				// TODO Auto-generated method stub
				System.out.println("Get response about "
						+ paramMap.get("jobId") + "from agent");
			}
		});
	}

	public JobManager(BalancerConfig conf) {
		this.conf = conf;
		jobTable = SharedJobTable.getInstance();
		agentManager = new AgentManager(conf);
		seedManager = new SeedManager(conf);
		client = new HttpTestClient();
		client.setHttpStatusListener(new HttpStatusListener() {

			@Override
			public void onSendRequestToAgent(Map<String, String> paramMap) {
				// TODO Auto-generated method stub
				System.out.println(paramMap.get("jobId") + " sent to agent");
			}

			@Override
			public void onGetResponseFromAgent(Map<String, String> paramMap) {
				// TODO Auto-generated method stub
				System.out.println("Get response about "
						+ paramMap.get("jobId") + "from agent");
			}
		});
		
		try {
			agentManager.load();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			int CheckResult = jobTable.checkRequireJob();
			if (CheckResult > 0) {
				JobEntity job = buildJob();
				doJob(job);
			}
		}
	}

	public JobEntity buildJob() {
		JobEntity job = new JobEntity();

		job.setAgent(agentManager.getAgentInfo());
		job.setSeed(seedManager.getSeedEntity("instagram"));

		return job;
	}

	public void doJob(JobEntity job) {

		String uri = job.getAgent().url("crawl");

		// -------------------------------------------------------
		// Init Request ...
		// -------------------------------------------------------
		HttpClient httpClient = client.init_send_request();

		if (httpClient == null)
			return;

		HashMap<String, String> params = job.makeReqestParamMap();

		// ---------------------------------------------------------
		// Make post method ...
		// ---------------------------------------------------------
		PostMethod method = client.make_post_method(uri, params);

		if (method == null)
			return;

		// -------------------------------------------------------
		// wait for response ...
		// -------------------------------------------------------
		String result = client.send_and_get_response(httpClient, method);
		System.out.println(result);
	}
}
