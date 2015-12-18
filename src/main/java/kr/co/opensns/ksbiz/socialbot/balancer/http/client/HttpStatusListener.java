package kr.co.opensns.ksbiz.socialbot.balancer.http.client;

import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.job.JobEntity;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobStatus;

public interface HttpStatusListener {
	public abstract void onGetResponseFromAgent(JobRequestEvent event);
	public abstract void onSendRequestToAgent(JobRequestEvent event);
	public abstract void onRequestTimeout(JobRequestEvent event);
}
