package kr.co.opensns.ksbiz.socialbot.crawler.instagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.co.opensns.ksbiz.socialbot.agent.JobThreadCounter;
import kr.co.opensns.ksbiz.socialbot.crawler.fetcher.Fetcher;
import kr.co.opensns.ksbiz.socialbot.schedule.Job;
import kr.co.opensns.ksbiz.socialbot.util.Observable;
import kr.co.opensns.ksbiz.socialbot.util.Observer;
import kr.co.opensns.ksbiz.socialbot.util.json.JSONMarshaller;


public class Crawler implements Runnable, Observable{
	private Fetcher fetcher;
	private Job job;
	public final static String DIR_NM_WRITE = "c://Temp//socialbot/result";
	private boolean run;
	private List<Observer> observers;
	
	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
		notifyObservers();
	}
	
	public void init() {
		fetcher = new MediaFetcher();
		observers = new ArrayList<Observer>();
	}
	
	public Object crawl() {
		// TODO what is the crawl site and type
		// do check job
		return fetcher.fetch(job.getSeed());
	}
	
	public void write(Object object) {
		try {
			JSONMarshaller.writeJson(DIR_NM_WRITE, job.getJobId() + "_" + job.getSiteId() + "_" + job.getSeed() + ".json", object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Fetcher getFetcher() {
		return fetcher;
	}

	public void setFetcher(Fetcher fetcher) {
		this.fetcher = fetcher;
	}

	public Crawler() {
		super();
		init();
	}
	
	public Crawler(Job job, JobThreadCounter jobThreadCounter) {
		super();
		this.job = job;
		init();
		registerObserver(jobThreadCounter);
	}

	@Override
	public void run() {
		setRun(true);
		
		Object result = crawl();
		write(result);
		
		setRun(false);
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		int i = observers.size();
		if( i >= 0 )
			observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		Observer observer;
		for( int i = 0; i < observers.size(); i++ ) {
			observer = observers.get(i);
			observer.update(this);
		}
	}
}
