package kr.co.opensns.ksbiz.socialbot.balancer.http;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpStatus;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Thread Pool을 이용한 HTTP 서버
 * 
 * @author shimkangseop
 * @see HttpPooledWorker, NoSuchHandlerException
 *
 */
public class HttpPooledServer {
	/**
	 * 최대로 허용할 쓰래드 개수
	 */
	public final static int MAX_THREAD_CNT = 20;

	/**
	 * 쓰래드 풀 excutor
	 */
	private ExecutorService exe_service = null;
	/**
	 * http 서버에서 사용할 포트
	 */
	private int port = 0;

	/**
	 * This variable represents Worker's class instance.
	 */
	private Class cls = null;

	/**
	 * The role of Handlers is to handle client's request by types of requests.
	 */

	private Class cls2 = null;

	/**
	 * 클래스 생성자<br>
	 * 
	 * @param port
	 *            서버에서 사용할 포트번호
	 * @param cls
	 *            실제 업무로직이 수행될 워커 쓰래드용 클래스
	 * @param handlers
	 *            client의 request를 처리할 핸들러
	 */
	public HttpPooledServer(int port) {
		this.port = port;
		this.cls = HttpPooledResultWorker.class;
		this.cls2 = HttpPooledBatchWorker.class;
		// this.handlers = handlers;

		init();
	}

	/**
	 * 서비스 excutor를 생성한다.
	 */
	private void init() {
		exe_service = Executors.newFixedThreadPool(MAX_THREAD_CNT);
	}

	/**
	 * Thread Pool에 작업(Runnable)을 등록한다.
	 * 
	 * @param command
	 *            작업을 등록할 워커 클래스
	 */
	public void execute(Runnable command) {
		exe_service.execute(command);
	}

	/**
	 * 서버를 시작한다.
	 */
	public void start() {
		InetSocketAddress addr = new InetSocketAddress(port);

		try {
			HttpServer server = HttpServer.create(addr, 0);
			server.createContext("/crawl", new HttpPooledCrawlHandler());
			server.createContext("/status", new HttpPooledStatusHandler());
			server.setExecutor(Executors.newCachedThreadPool());
			server.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * HTTP 핸들러 클래스
	 * 
	 * @author goodsouls
	 *
	 */
	private class HttpPooledCrawlHandler implements HttpHandler {
		private Constructor constructor = null;

		public HttpPooledCrawlHandler() {
			try {
				constructor = cls.getConstructor(HttpExchange.class);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		/**
		 * Thread Pool에 작업을 등록한다.
		 * 
		 * @param exchange
		 *            http exchange 클래스
		 * @exception IOException
		 */
		public void handle(HttpExchange exchange) throws IOException {
			try {
				execute((Runnable) constructor.newInstance(exchange));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class HttpPooledStatusHandler implements HttpHandler {
		private Constructor constructor = null;

		public HttpPooledStatusHandler() {
			try {
				constructor = cls2.getConstructor(HttpExchange.class);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		/**
		 * Thread Pool에 작업을 등록한다.
		 * 
		 * @param exchange
		 *            http exchange 클래스
		 * @exception IOException
		 */
		public void handle(HttpExchange exchange) throws IOException {
			try {
				execute((Runnable) constructor.newInstance(exchange));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// --------------------------------------------------------------
		// Start Server ...
		// --------------------------------------------------------------
		new HttpPooledServer(8808).start();

		System.out.println("Server Started ...");
	}
}
