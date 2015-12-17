package kr.co.opensns.ksbiz.socialbot.agent;

import kr.co.opensns.ksbiz.socialbot.crawler.instagram.Crawler;
import kr.co.opensns.ksbiz.socialbot.util.Observable;
import kr.co.opensns.ksbiz.socialbot.util.Observer;

public class JobThreadCounter implements Observer {
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

	@Override
	public void update(Observable observable) {
		if( observable instanceof Crawler) {
			Crawler crawler = (Crawler)observable;
			count = (crawler.isRun()? ++count : --count);
		}
	}

	public JobThreadCounter(int count) {
		super();
		this.count = count;
	}

	public JobThreadCounter() {
		super();
	}
}
