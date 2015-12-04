package kr.co.opensns.ksbiz.socialbot.internal.http;

public class HttpServerSimpleTest {
	
	
	public static void main(String[] args) {
		HttpServerInterface server = new HttpServerSimple();
		server.start();
	}
}
