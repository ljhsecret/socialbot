package kr.co.opensns.ksbiz.socialbot.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler {
	// TODO 리퀘스트 Job을 제어할 Queue 선언
	// 무제한적인 리퀘스트 요청시 오류가 생길수 있으므로 Blocking Queue를 고려해보자.
	private Queue<Job> queue = new LinkedList<Job>();
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static RequestHandler requestHandler = null;
	
	public static RequestHandler getInstance() {
		if( requestHandler == null ) {
					requestHandler = new RequestHandler();
		}
		return RequestHandler.requestHandler;
	}
	
	private RequestHandler() {
		
	}
	
	public void addJob(Job job) {
		queue.add(job);
	}
	
	public Queue<Job> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Job> queue) {
		this.queue = queue;
	}

	public static RequestHandler getRequestHandler() {
		return requestHandler;
	}

	public static void setRequestHandler(RequestHandler requestHandler) {
		RequestHandler.requestHandler = requestHandler;
	}

	public Job getOneJob() {
		return queue.poll();
	}
	
	public boolean hasAJob() {
		System.out.println("queue size: " + queue.size());
		return ( queue.size() > 0 )? true : false;
	}
}
