package kr.co.opensns.ksbiz.socialbot.balancer.agent;

import java.util.Comparator;
import java.util.Map.Entry;

public class AgentComprator implements Comparator<Entry<String, AgentInfo>> {

	@Override
	public int compare(Entry<String, AgentInfo> o1, Entry<String, AgentInfo> o2) {
		double o1Pri = o1.getValue().getPriority();
		double o2Pri = o2.getValue().getPriority();
		if (o1Pri > o2Pri)
			return 1;
		else if (o1Pri < o2Pri) {
			return -1;
		} else
			return 0;
	}
}
