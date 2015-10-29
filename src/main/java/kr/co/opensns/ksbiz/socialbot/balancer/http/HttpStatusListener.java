package kr.co.opensns.ksbiz.socialbot.balancer.http;

import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.job.JobEntity;

public interface HttpStatusListener {
	public abstract void onGetResponseFromAgent(Map<String,String> paramMap);
	public abstract void onSendRequestToAgent(Map<String,String> paramMap);
}
