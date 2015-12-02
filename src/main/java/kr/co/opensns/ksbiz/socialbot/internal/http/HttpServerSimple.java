package kr.co.opensns.ksbiz.socialbot.internal.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.schedule.Job;
import kr.co.opensns.ksbiz.socialbot.schedule.RequestHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpServerSimple implements HttpServerInterface, Runnable{
	HttpServer httpServer;
	private int port = 8080;
	public final static String crawlContext = "/crawl";
	public final static String authContext = "/auth";
	public final static String statusContext = "/status";
	protected final static char DELIM_STARTQUERY_STR	= '?';
	protected final static String DELIM_QUERY_STR		= "[&]";
	protected final static char DELIM_DEF				= '=';
	protected final static String STR_EMPTY				= "";
	protected final static String DEFAULT_CHAR_SET		= "utf-8";
	
	@Override
	public void run() {
		System.out.println("HttpServerSimple start...");
		start();
	}

	@Override
	public void stop() {
		httpServer.stop(0);
	}
	
	static class StatusHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange arg0) throws IOException {
			System.out.println("handle status");
		}
		
	}
	
	static class AuthHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange arg0) throws IOException {
		}
		
	}

	static class CrawlHandler implements HttpHandler {

		public Map<String, String> parseQueryByMethod(HttpExchange httpExchange) {
			Map<String, String> queryMap = null;
			String requestUri = null; 
			if( httpExchange.getRequestMethod().equals("GET")) {
				requestUri = httpExchange.getRequestURI().getQuery();
			} else {
				InputStream in = httpExchange.getRequestBody();
				try
				{
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte buf[] = new byte[4096];
					for (int n = in.read(buf); n > 0; n = in.read(buf))
					{
						out.write(buf, 0, n);
					}
					requestUri = new String(out.toByteArray());
				} catch ( Exception e) {
					
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			try {
				queryMap =  parseQueryString(requestUri);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return queryMap;
		}

		@Override
		public void handle(HttpExchange arg0) throws IOException {
			Map<String, String> params = null;
			try {
				System.out.println("Received query: " + arg0.getRequestURI().getQuery());
				params = parseQueryString(arg0.getRequestURI().getQuery());
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*new Job(params.get(HttpParamCollection.JOB_ID),
					params.get(HttpParamCollection.CHANNEL_ID),
					params.get(HttpParamCollection.SITE_ID),
					params.get(HttpParamCollection.SEED),
					params.get(HttpParamCollection.CRAWL_TYPE),
					params.get(HttpParamCollection.CRAWL_TYPE),
					params.get(HttpParamCollection.CRAWL_TYPE));*/
			Job job = new Job();
			job.setJobId(params.get(HttpParamCollection.JOB_ID));
			job.setSeed(params.get(HttpParamCollection.SEED));
			job.setChannelId(params.get(HttpParamCollection.CHANNEL_ID));
			job.setSiteId(params.get(HttpParamCollection.SITE_ID));
			RequestHandler.getInstance().addJob(
					job);
			System.out.println("End of handle crawl");
		}
	}
	
	protected static HashMap<String, String> parseQueryString(String query_str) throws Exception
	{
		HashMap<String, String> query_map 				= new HashMap<String, String>();
		
		int SQindex;
		if((SQindex=query_str.indexOf(DELIM_STARTQUERY_STR))>-1)
			query_str = query_str.substring(SQindex+1);
		
		if(query_str == null)
			return query_map;

		String defs[] 									= query_str.trim().split(DELIM_QUERY_STR);
		
		if(defs == null)
			return query_map;
		
		for(int i=0; i<defs.length; i++)
		{
			String def									= defs[i];
			int ix 										= def.indexOf(DELIM_DEF);
			
			String name									= ix < 0 ? def : def.substring(0, ix);
			String value								= ix < 0 ? STR_EMPTY : URLDecoder.decode(def.substring(ix + 1), DEFAULT_CHAR_SET);

			query_map.put(name, value);
		}
		System.out.println("parse end");
		return query_map;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		// port 설정
		port = 8080;
	}

	@Override
	public void start() {
		try {
			httpServer = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpServer.createContext(crawlContext, new CrawlHandler());
		httpServer.createContext(authContext, new AuthHandler());
		httpServer.createContext(statusContext, new StatusHandler());
		httpServer.setExecutor(null);
		httpServer.start();
	}
}
