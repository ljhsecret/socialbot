package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentInfo;
import kr.co.opensns.ksbiz.socialbot.balancer.http.client.HttpClientAsThread;
import kr.co.opensns.ksbiz.socialbot.balancer.http.client.HttpStatusListener;
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
	private String siteId;
	private String seed;
	private String cursor;
	private String crawlType;
	private String contentType;
	
	
	JobStatus status;
	
	
	
	public JobEntity(){
		this(null,null);
		this.status = JobStatus.READY;
		this.jobId = System.nanoTime();
	}
	
	public void setStatus(JobStatus status){
		this.status = status;
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
		this.siteId = seed.getSite();
		this.seed = seed.getSeed();
		this.cursor = seed.getCursor();
		this.crawlType = seed.getType();
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
	
	public HashMap<String, String> toMap(){
		HashMap<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("jobId", ""+jobId);
		paramMap.put("channelId", "10");
		paramMap.put("siteId", "1010");
		paramMap.put("seed", seed);
		//코드화
		paramMap.put("cursor", "");
		//config
		paramMap.put("crawlType", "");
		paramMap.put("contentType", "JSON");
		paramMap.put("return", "");
		
		return paramMap;
	}
}
