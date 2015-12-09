package kr.co.opensns.ksbiz.socialbot.agent;

public class JobThreadCounter {
	private int count = 0;
	
	public void increase() {
		this.count++;
	}
	
	public void decrease() {
		this.count--;
	}
	
	public int getCount() {
		return this.count;
	}
}
