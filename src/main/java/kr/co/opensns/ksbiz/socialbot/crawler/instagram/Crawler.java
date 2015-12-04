package kr.co.opensns.ksbiz.socialbot.crawler.instagram;

import java.io.IOException;

import kr.co.opensns.ksbiz.socialbot.crawler.fetcher.Fetcher;
import kr.co.opensns.ksbiz.socialbot.schedule.Job;
import kr.co.opensns.ksbiz.socialbot.util.json.JSONMarshaller;


public class Crawler  implements Runnable{
	private Fetcher fetcher;
	private Job job;
	public final static String DIR_NM_WRITE = "c://Temp//socialbot/result";
	
	public void init() {
		fetcher = new MediaFetcher();
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
	
	public Crawler(Job job) {
		super();
		this.job = job;
		init();
	}

	@Override
	public void run() {
		Object result = crawl();
		write(result);
	}
}
