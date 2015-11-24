/**
 * 
 */
package kr.co.opensns.ksbiz.socialbot.balancer.http.server;

import java.io.OutputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.agent.AgentManager;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobManager;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedManager;

import com.sun.net.httpserver.HttpExchange;

/**
 * result context에 대한 비지니스 로직을 처리하는 클래스
 *
 * <pre>
 * <br>
 * <b>History:</b>
 * 		mhyoo, v1.0.0, 2015. 10. 28., 최초작성
 * </pre>
 * 
 * @since 2015. 10. 28., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class HttpPooledResultWorker extends HttpPooledWorker {
	static int id = 0;

	JobManager jobManager;
	AgentManager agentManager;
	SeedManager seedManager;

	static Logger logger = Logger.getLogger(HttpPooledResultWorker.class);

	public HttpPooledResultWorker(HttpExchange exchange) {
		super(exchange);
		agentManager = AgentManager.getInstance();
		seedManager = SeedManager.getinstance();
		jobManager = JobManager.getInstance();
		// jobManager.
	}

	public void run(HashMap<String, String> query_map) {
		logger.info((id++) + " Result worker running..");

		if (exchange == null)
			return;

		query_map.get("jobId");
		query_map.get("Content-Type");
		query_map.get("elapsedTime");
		query_map.get("crawledCount");

		switch (query_map.get("StatusCode")) {
		case "01":
			
			break;
		
		case "02":

			break;

		default:
			break;
		}
		OutputStream out = null;

		try {
			exchange.sendResponseHeaders(200, 0);
			out = exchange.getResponseBody();

			// out.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
