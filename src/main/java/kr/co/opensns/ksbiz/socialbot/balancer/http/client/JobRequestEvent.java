package kr.co.opensns.ksbiz.socialbot.balancer.http.client;

import kr.co.opensns.ksbiz.socialbot.balancer.job.JobEntity;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobStatus;

public class JobRequestEvent {
	JobEntity job;
	
	public JobRequestEvent(JobEntity job){
		this.job = job;
	}
	
	public JobStatus getJobStatus(){
		return job.getStatus();
	}
	
	public String getCurrentSeed() {
		return null;
	}
	
	public String getJobAgentIP(){
		return job.getAgent().getIp();
	}
	
	public String getJobId(){
		return job.getJobId();
	}
}
