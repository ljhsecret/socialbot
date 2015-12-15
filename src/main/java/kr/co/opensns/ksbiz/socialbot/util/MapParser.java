package kr.co.opensns.ksbiz.socialbot.util;

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
			
		} else {
			throw new BalancerException("failed convert object");
		}
		
		return null;
	}
}
