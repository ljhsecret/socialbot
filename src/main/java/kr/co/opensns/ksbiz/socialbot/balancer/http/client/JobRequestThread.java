package kr.co.opensns.ksbiz.socialbot.balancer.http.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.spi.SyncResolver;

import kr.co.opensns.ksbiz.socialbot.balancer.job.JobEntity;
import kr.co.opensns.ksbiz.socialbot.balancer.job.JobStatus;
import kr.co.opensns.ksbiz.socialbot.util.MapParser;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * 클래스 설명
 *
 * <pre>
 * <br>
 * <b>History:</b>
 * 		mhyoo, v1.0.0, 2015. 10. 30., 최초작성
 * </pre>
 * 
 * @since 2015. 10. 30., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class JobRequestThread implements Runnable {
	HttpStatusListener listener;
	JobEntity job;
	HttpClient httpClient;
	static Logger logger;

	static {
		logger = Logger.getLogger(JobRequestThread.class);
	}

	public JobRequestThread() {
		httpClient = buildClient();
	}

	public void setHttpStatusListener(HttpStatusListener listener) {
		this.listener = listener;
	}

	public void setJob(JobEntity job) {
		this.job = job;
	}

	public HttpClient buildClient() {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "BALANCER");
		client.getParams().setContentCharset("UTF-8");
		return client;
	}

	/**
	 * 사용자의 요청에 해당하는 PostMethod를 생성해주는 메소드
	 * 
	 * @param uri
	 *            서버 uri
	 * @param params
	 *            사용자 요청을 저장하고 있는 map
	 * @return PostMethod
	 */
	public PostMethod buildPostMethod(String uri, Map<String, String> params) {
		PostMethod method = new PostMethod(uri);

		if (params == null)
			return null;

		Iterator<String> key_iter = params.keySet().iterator();

		while (key_iter.hasNext()) {
			String key = key_iter.next();

			if (key == null)
				continue;

			String value = params.get(key);

			if (value == null)
				throw new NullPointerException("Value is null ... (KEY : "
						+ key + ")");

			method.addParameter(key, value);
		}

		return method;
	}

	public GetMethod buildGetMethod(String uri, Map<String, String> params) {
		GetMethod method = new GetMethod(uri);

		if (params == null)
			return null;

		Iterator<String> key_iter = params.keySet().iterator();
		StringBuilder queryString = new StringBuilder();
		queryString.append("?");
		while (key_iter.hasNext()) {
			NameValuePair nvp = new NameValuePair();

			String key = key_iter.next();

			if (key == null)
				continue;

			String value = params.get(key);

			if (value == null)
				throw new NullPointerException("Value is null ... (KEY : "
						+ key + ")");

			nvp.setName(key);
			nvp.setValue(value);

			queryString.append(key + "=" + value);
			if (key_iter.hasNext())
				queryString.append("&");
		}

		method.setQueryString(queryString.toString());

		return method;
	}

	/**
	 * 서버로 요청을 보내고 응답을 받는 메소드
	 * 
	 * @param client
	 *            HttpClient
	 * @param method
	 *            사용자 요청을 저장하고 있는 PostMethod
	 * @return 서버로 부터 받은 응답
	 */
	public String getResponseString(PostMethod method) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		try {
			int returnCode = httpClient.executeMethod(method);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err
						.println("The Post method is not implemented by this URI");
				method.getResponseBodyAsString();

				throw new Exception(returnCode + "");
			}

			br = new BufferedReader(new InputStreamReader(
					method.getResponseBodyAsStream()));
			String line = null;

			while ((line = br.readLine()) != null)
				sb.append(line).append('\n');

		} catch (Exception e) {
			if (e instanceof java.net.ConnectException) {
				listener.onRequestTimeout(new JobRequestEvent(job));
			}
			return null;
		} finally {
			method.releaseConnection();

			try {
				if (br != null)
					br.close();
			} catch (Exception fe) {
				fe.printStackTrace();
			}
		}

		return sb.toString();
	}

	public String getResponseString(GetMethod method) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		try {
			int returnCode = httpClient.executeMethod(method);
			listener.onSendRequestToAgent(new JobRequestEvent(job));
			// listener.onSendRequestToAgent(job);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				logger.info("The Get method is not implemented by this URI");

				throw new Exception(returnCode
						+ method.getResponseBodyAsString());
			}
			
			br = new BufferedReader(new InputStreamReader(
					method.getResponseBodyAsStream()));
			String line = null;

			while ((line = br.readLine()) != null)
				sb.append(line).append('\n');

		} catch (Exception e) {
			// e.printStackTrace();
			if (e instanceof java.net.ConnectException) {
				listener.onRequestTimeout(new JobRequestEvent(job));
			}
			return null;
		} finally {
			method.releaseConnection();

			try {
				if (br != null)
					br.close();
			} catch (Exception fe) {
				fe.printStackTrace();
			}
		}

		return sb.toString();
	}

	@Override
	public void run() {
		try {
			String uri = job.getAgent().url("crawl");

			// -------------------------------------------------------
			// Client Initialize ...
			// -------------------------------------------------------
			HashMap<String, String> params = (HashMap<String, String>) MapParser.convertMap(job);

			// ---------------------------------------------------------
			// Make post method ...
			// ---------------------------------------------------------
//			 PostMethod method = make_post_method(uri, params);
			GetMethod method = buildGetMethod(uri, params);

			if (method == null)
				return;

			// -------------------------------------------------------
			// wait for response ...
			// -------------------------------------------------------
			String result = getResponseString(method);

			if (result == null)
				throw new Exception("response error");

			listener.onGetResponseFromAgent(new JobRequestEvent(job));

			System.out.println(result);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}
