package kr.co.opensns.ksbiz.socialbot.balancer.job;

public interface JobStatusListener {
	public abstract void onRequireJob();
	public abstract void onCompleteJob(JobEntity job);
	public abstract void onOccurErrorJob(JobEntity job);
}
