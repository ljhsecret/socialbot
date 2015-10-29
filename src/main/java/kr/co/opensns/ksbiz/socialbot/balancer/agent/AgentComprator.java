package kr.co.opensns.ksbiz.socialbot.balancer.agent;

import java.util.Comparator;

public class AgentComprator implements Comparator<AgentInfo>{

	@Override
	public int compare(AgentInfo o1, AgentInfo o2) {
		double o1Pri=o1.getPriority();
		double o2Pri=o2.getPriority();
		if(o1Pri>o2Pri)
			return 1;
		else if(o1Pri<o2Pri){
			return -1;
		} else
			return 0;
	}
}
