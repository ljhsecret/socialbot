package kr.co.opensns.ksbiz.socialbot.balancer.agent;

import java.util.concurrent.PriorityBlockingQueue;

public class AgentQueue {
	PriorityBlockingQueue<AgentInfo> queue = new PriorityBlockingQueue<AgentInfo>(
			10000000, new AgentComprator());
	String type;

	public AgentQueue() {

	}

	public void put(AgentInfo seed) {
		queue.put(seed);
	}

	public AgentInfo take() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new NullPointerException("Queue is Empty");
		}
	}
}
