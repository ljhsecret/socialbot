package kr.co.opensns.ksbiz.socialbot.balancer.job;

import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentInfo;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedEntity;

/**
 * 클래스 설명
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 20., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class JobEntity {
	
	private SeedEntity targetSeed;
	private AgentInfo workingAgent;
	
	private long jobId;
	
	JobStatus status;
	
	public JobEntity(){
		this(null,null);
		this.status = JobStatus.READY;
		this.jobId = System.nanoTime();
	}
	
	public void setStatus(JobStatus status){
		this.status = status;
	}
	
	public JobStatus getStatus(){
		return status;
	}
	
	public JobEntity(SeedEntity seed, AgentInfo agent){
//		this.channelId = seed.getChannel();
//		this.siteId = seed.getSite();
//		this.seed = seed.getSeed();
//		this.cursor = seed.getCursor();
//		this.crawlType = seed.getType();
	}
	
	public String getJobId(){
		return ""+jobId;
	}
	
	public void setSeed(SeedEntity seed){
		this.targetSeed = seed;
	}
	
	public void setAgent(AgentInfo agent){
		this.workingAgent = agent;
	}
	
	public SeedEntity getSeed(){
		return this.targetSeed;
	}
	
	public AgentInfo getAgent(){
		return this.workingAgent;
	}
	
}
