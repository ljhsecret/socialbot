package kr.co.opensns.ksbiz.socialbot.agent;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.crawler.instagram.Crawler;
import kr.co.opensns.ksbiz.socialbot.schedule.Job;
import kr.co.opensns.ksbiz.socialbot.schedule.RequestHandler;

/**
 * 수집 작업을 관리.
 * RequestHandler로부터 jobQueue를 얻어와 대기하는 작업을 조회하여
 * Thread하게 작업을 실시한다.
 * @author jaeho
 *
 */
public class CrawlJobManager implements Runnable{
	public static int MAX_JOB_COUNT = 20;
	private Logger logger = Logger.getLogger(this.getClass());
	private List<Thread> jobThreads = new ArrayList<Thread>();
	private JobThreadCounter jobThreadCounter = new JobThreadCounter();
	@Override
	public void run() {
		manageJob();
	}
	
	public void manageJob() {
		while(true) {
			waitForJob();
			Job job = RequestHandler.getInstance().getOneJob();
			Thread thread = new Thread(new Crawler(job, jobThreadCounter));
			thread.start();
			jobThreads.add(thread);
			System.out.println("New Crawl job added. the job id: " + job.getJobId() + ", site id: " + job.getSiteId() + ", seed: " + job.getSeed());
		}
	}
	
	public void waitForJob() {
		
		while( ( jobThreadCounter.getCount() >= MAX_JOB_COUNT ) || ( ! RequestHandler.getInstance().hasAJob()) ) {
			try {
				Thread.sleep(5000);
				System.out.println("Wait for job. Job thread count: " + jobThreadCounter.getCount() + ", Job Queue size: " + RequestHandler.getInstance().getQueue().size());
			} catch (InterruptedException e) {
			}
		}
	}
}
