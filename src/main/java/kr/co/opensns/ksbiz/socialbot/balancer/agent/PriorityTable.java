package kr.co.opensns.ksbiz.socialbot.balancer.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kr.co.opensns.ksbiz.socialbot.balancer.exception.BalancerException;

public class PriorityTable {
	LinkedHashMap<String, AgentInfo> map;
	Comparator<Entry<String, AgentInfo>> comp;

	public PriorityTable(Comparator<Entry<String, AgentInfo>> comp) {
		this.comp = comp;

		this.map = new LinkedHashMap<String, AgentInfo>();
	}

	private void sort() {
		synchronized (PriorityTable.class) {

			List<Entry<String, AgentInfo>> tmpList = new ArrayList<Entry<String, AgentInfo>>(
					map.entrySet());
			Collections.sort(tmpList, comp);

			map.clear();

			for (Entry<String, AgentInfo> e : tmpList) {
				map.put(e.getKey(), e.getValue());
//				System.out.println("IP : "+e.getValue().getIp()+",    Priority : "+e.getValue().getPriority());
			}
		}
	}

	public void put(AgentInfo agent) {
		synchronized (PriorityTable.class) {
			map.put(agent.getIp(), agent);
			sort();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AgentInfo take() {
		synchronized (PriorityTable.class) {
			sort();
			Set<Entry<String, AgentInfo>> eSet = map.entrySet();
			Iterator itr = eSet.iterator();
			if (!itr.hasNext())
				return null;

			Entry<String, AgentInfo> entry = (Entry<String, AgentInfo>) itr
					.next();
			
			AgentInfo agent = entry.getValue();
			agent.setJobCount(agent.getJobCount() + 1);

			if (map.containsKey(agent.getIp()))
				map.remove(agent);

			map.put(agent.getIp(), agent);
			return agent;
		}
	}

	public void update(Map<String, String> fields) throws BalancerException {
		synchronized (PriorityTable.class) {
			String ip = fields.get("ip");
			map.remove(ip);
			if (!map.containsKey(ip)) {
				throw new BalancerException("not exist agent(ip:" + ip
						+ " into map");
			}
			
			AgentInfo agent = map.get(ip);
			switch (fields.get("StatusCode")) {
			case "00":
				long processingTime = Long.parseLong(fields.get("elapsedTime"));
				long avrJobProcessingTime = agent.getAvrJobProcessingTime();
				long jobCount = agent.getJobCount();
				
				avrJobProcessingTime = (avrJobProcessingTime*jobCount+processingTime)/(jobCount+1);
				
				agent.setAvrJobProcessingTime(avrJobProcessingTime);
				agent.setJobCount(jobCount+1);
				agent.setLastWorkingTime(new Date().getTime()/1000L);
				
				map.put(ip,agent);
				
				break;
			case "01":
				agent.dead();
				map.put(ip, agent);
			default:
				break;
			}
			
			sort();
		}
	}
	
	public AgentInfo get(String ip){
		return map.get(ip);
	}
	
	public Set<String> IpSet(){
		return map.keySet();
	}
	
	public int size(){
		return map.size();
	}
	
	/*public int runAgentSize(){
		for(Iterator iter = map.keySet().iterator();iter.hasNext();){
			if(map.get(iter.next()).getStatus().equals(null));
		}
	}*/
}
