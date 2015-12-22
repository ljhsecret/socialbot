package kr.co.opensns.ksbiz.socialbot.balancer.http.client;

public interface HttpStatusListener {
	public abstract void onGetResponseFromAgent(JobRequestEvent event);
	public abstract void onSendRequestToAgent(JobRequestEvent event);
	public abstract void onRequestTimeout(JobRequestEvent event);
}
