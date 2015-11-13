/**
 * 
 */
package kr.co.opensns.ksbiz.socialbot.balancer.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;



/**
 * result context에 대한 비지니스 로직을 처리하는 클래스
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 28., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 28., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class HttpPooledMonitorWorker extends HttpPooledWorker
{
	static int id=0;
	
	public HttpPooledMonitorWorker(HttpExchange exchange)
	{
		super(exchange);
	}
	
	public void run(HashMap<String, String> query_map)
	{
		System.out.println((id++) +" Result worker running..");
		
		if(exchange == null)
			return;
		
		OutputStream out								= null;
		
		try
		{
			exchange.sendResponseHeaders(200, 0);
			out											= exchange.getResponseBody();
			
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try
			{
				if(out != null) out.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
