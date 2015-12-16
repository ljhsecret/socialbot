package kr.co.opensns.ksbiz.socialbot.util;

import java.util.HashMap;
import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentInfo;
import kr.co.opensns.ksbiz.socialbot.balancer.exception.BalancerException;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobEntity;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedEntity;

public class MapParser {
	public static Object parseMap(Map<String,String> map) throws BalancerException{
		
		if(map.containsKey("jobId")){
			
		} else if(map.containsKey("ip")){
			
		} else if(map.containsKey("seed")){
			
		} else {
			throw new BalancerException("failed parse map");
		}
		
		return null;
	}
	
	public static Map<String,String> convertMap(Object obj) throws BalancerException{
		
		if(obj instanceof SeedEntity){
			
		} else if(obj instanceof AgentInfo){
			
		} else if(obj instanceof JobEntity){
			HashMap<String, String> paramMap = new HashMap<String, String>();
			JobEntity job = (JobEntity)obj;
			paramMap.put("jobId", ""+job.getJobId());
			paramMap.put("channelId", "10");
			paramMap.put("siteId", "1010");
			paramMap.put("seed", job.getSeed().getSeedId());
			//코드화
			paramMap.put("cursor", "");
			//config
			paramMap.put("crawlType", "");
			paramMap.put("contentType", "JSON");
			paramMap.put("return", "");
			
			return paramMap;
		} else {
			throw new BalancerException("failed convert object");
		}
		
		return null;
	}
}
