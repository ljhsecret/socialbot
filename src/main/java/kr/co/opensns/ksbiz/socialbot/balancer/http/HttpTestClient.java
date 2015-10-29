

package kr.co.opensns.ksbiz.socialbot.balancer.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * 데몬을 테스트 하기 위한 클라이언트 테스트 클래스<br>
 * USAGE : java HttpPooledClientStarter ["call_A"|"call_B"]<br>
 * 파라메터 : call_A 또는 call_B 문자열을 직접 입력.<br>
 * <p>
 * 
 * @author shimkangseop
 * @see HttpPooledServer, HttpPooledWorker
 *
 */
public class HttpTestClient
{
	HttpStatusListener listener;
	
	public HttpTestClient(){
		
	}
	
	public void setHttpStatusListener(HttpStatusListener listener){
		this.listener = listener;
	}
	
	public HttpClient init_send_request()
	{
		HttpClient client 							= new HttpClient();
		client.getParams().setParameter("http.useragent", "HTTP_POOLED_CLIENT");
		client.getParams().setContentCharset("UTF-8");
		return client;
	}
	
	/**
	 * 사용자의 요청에 해당하는 PostMethod를 생성해주는 메소드 
	 * @param uri 서버 uri
	 * @param params 사용자 요청을 저장하고 있는 map
	 * @return PostMethod
	 */
	public PostMethod make_post_method(String uri, Map<String, String> params)
	{
		PostMethod method 							= new PostMethod(uri);
		
		if(params == null)
			return null;
		
		Iterator<String> key_iter					= params.keySet().iterator();
		
		while(key_iter.hasNext())
		{
			String key								= key_iter.next();
			
			if(key == null)
				continue;
			
			String value							= params.get(key);
			
			if(value == null)
				throw new NullPointerException("Value is null ... (KEY : " + key +")");
			
			method.addParameter(key, value);
		}
	
		return method;
	}
	
	/**
	 * 서버로 요청을 보내고 응답을 받는 메소드 
	 * @param client HttpClient
	 * @param method 사용자 요청을 저장하고 있는 PostMethod
	 * @return 서버로 부터 받은 응답
	 */
	public String send_and_get_response(HttpClient client, PostMethod method)
	{
		BufferedReader br 							= null;
		StringBuffer sb								= new StringBuffer();
		
		try
		{
			int returnCode 							= client.executeMethod(method);
//			listener.onSendRequestToAgent(job);

			if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED)
			{
				System.err.println("The Post method is not implemented by this URI");
				method.getResponseBodyAsString();
				
				throw new Exception(returnCode + "");
			}

			br 										= new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
			String line								= null;
			
			while((line = br.readLine()) != null)
				sb.append(line).append('\n');

		}catch (Exception e){
			e.printStackTrace();
			return null;
		}finally{
			method.releaseConnection();
			
			try
			{
				if (br != null)
					br.close();
			}catch (Exception fe){ fe.printStackTrace();}
		}
		
		return sb.toString();
	}
}
