package kr.co.opensns.ksbiz.socialbot.balancer.http.client;

import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.job.JobStatus;

public interface HttpStatusListener {
	public abstract void onGetResponseFromAgent(JobStatus status);
	public abstract void onSendRequestToAgent(JobStatus status);
}
