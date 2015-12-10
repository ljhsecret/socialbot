package kr.co.opensns.ksbiz.socialbot.balancer.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

			if (!map.containsKey(ip)) {
				throw new BalancerException("not exist agent(ip:" + ip
						+ " into map");
			}

//			AgentInfo agent = map.get(ip);

			map.remove(ip);

			// agent.setJobCount();

			sort();
		}
	}
}
