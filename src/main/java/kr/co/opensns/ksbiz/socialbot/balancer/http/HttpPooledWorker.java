package kr.co.opensns.ksbiz.socialbot.balancer.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

import com.sun.net.httpserver.HttpExchange;

/**
 * 하나의 요청을 처리하는 worker 클래스
 * @author shimkangseop
 */
public abstract class HttpPooledWorker implements Runnable
{
	//================================BEGIN====================================
	
	//--------------------------------------------------------------
	// Read/Write related definitions ...
	//--------------------------------------------------------------
	protected final static String DEFAULT_CHAR_SET		= "utf-8";
	protected final static String METHOD_POST			= "POST";
	protected final static String METHOD_GET			= "GET";
	protected final static int BUFFER_SIZE				= 4096;
	
	//--------------------------------------------------------------
	// String related definitions ...
	//--------------------------------------------------------------
	protected final static String STR_EMPTY				= "";
	protected final static byte[] BYTE_NEW_LINE			= "\n".getBytes();
	protected final static byte[] BYTE_TEST_STR			= "test\n".getBytes();
	
	//--------------------------------------------------------------
	// Delimiter related definitions ...
	//--------------------------------------------------------------
	protected final static String DELIM_QUERY_STR		= "[&]";
	protected final static char DELIM_DEF				= '=';
	
	//===================================END====================================
	
	protected HttpExchange exchange						= null;
	
	public HttpPooledWorker(HttpExchange exchange)
	{
		this.exchange									= exchange;
	}
	
	public void run()
	{
		if(exchange == null)
			return;
		
		HashMap<String, String>	query_map 				= null;
		String exchange_str								= exchange.getRequestMethod();
		
		if(METHOD_GET.equals(exchange_str))
		{
			try
			{
				query_map 								= parseQueryString(exchange.getRequestURI().toString());
			}catch(Exception e){
				e.printStackTrace();
				exchange.close();
				return;
			}
			
			run(query_map);
			exchange.close();
			return;
		}
		
		if(!METHOD_POST.equals(exchange_str))
			return;
		
		String query									= null;
		InputStream in 									= null;
		ByteArrayOutputStream out						= null;
		
		try
		{
			in											= exchange.getRequestBody();
			out 										= new ByteArrayOutputStream();
			byte buf[] 									= new byte[BUFFER_SIZE];
			int n_byte									= 0;
			
			while((n_byte = in.read(buf)) > 0)
				out.write(buf, 0, n_byte);

			query 										= new String(out.toByteArray());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try
			{
				if(in != null) in.close();
				if(out != null) out.close();
			}catch(Exception e){
				e.printStackTrace();
				exchange.close();
				return;
			}
		}
		
		try
		{
			query_map 									= parseQueryString(query);
		}catch(Exception e){
			e.printStackTrace();
			exchange.close();
			return;
		}
		
		run(query_map);
		exchange.close();
	}
	
	public abstract void run(HashMap<String, String> query_map);
	
	/**
	 * 질의를 파싱하는 메소드이다.
	 * @param query_str 사용자 요청 질의
	 * @return key-value로 요청 값을 저장한 map
	 * @throws Exception
	 */
	protected HashMap<String, String> parseQueryString(String query_str) throws Exception
	{
		HashMap<String, String> query_map 				= new HashMap<String, String>();
		
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

		return query_map;
	}
}
