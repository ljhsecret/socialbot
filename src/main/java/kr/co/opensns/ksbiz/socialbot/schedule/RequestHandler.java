package kr.co.opensns.ksbiz.socialbot.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler{
	// TODO 리퀘스트 Job을 제어할 Queue 선언
	// 무제한적인 리퀘스트 요청시 오류가 생길수 있으므로 Blocking Queue를 고려해보자.
	public final static Queue queue;
	static {
		queue = new LinkedList();
	}
	
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		
		// TODO Context 의 요청 구분에 따라서 처리를 하자.
		
		// auth
		
		// crawl
		
		// status
	}
}
