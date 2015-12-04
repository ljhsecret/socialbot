package kr.co.opensns.ksbiz.socialbot.agent;

import kr.co.opensns.ksbiz.socialbot.internal.http.HttpServerInterface;
import kr.co.opensns.ksbiz.socialbot.internal.http.HttpServerSimple;

/**
 * Socialbot의 실행 클래스이다.
 * Http Server 를 구동시키며 수집 리퀘스트 큐와 수집기의 작동을 제어한다.
 * @author jaeho
 *
 */
public class SocialBotAgent {
	// TODO Http Server 동작
	// 
	
	public void start() {
		httpServer.start();
		crawlJobManager.start();
	}
	
	private Thread crawlJobManager;
	
	private Thread httpServer;
	
	public void init() {
		httpServer = new Thread(new HttpServerSimple());
		crawlJobManager = new Thread(new CrawlJobManager());
	}
	
	public SocialBotAgent() {
		super();
		init();
	}


	public static void main(String[] args) {
		SocialBotAgent agent = new SocialBotAgent();
		agent.start();
	}
}
