package kr.co.opensns.ksbiz.socialbot.internal.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;

public class HttpClientSimple implements HttpClient {
	private Logger logger = Logger.getLogger(this.getClass());
	public String get(String url) {
		Executor exe;
		return "";
	}

	public HttpClientSimple() {
		super();
	}

	public BufferedReader getReader(String url) throws IOException {
		BufferedReader reader = null;
		HttpURLConnection httpConn = null;
		URL openURL = null;
		try {
			openURL = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
			logger.error(url);
		}
		
			try {
				httpConn = (HttpURLConnection)openURL.openConnection();
				httpConn.setRequestMethod("POST");
				httpConn.connect();
				reader = new BufferedReader(new InputStreamReader(openURL.openStream()));
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				logger.error(openURL.toString());
				/*String line = null;
				StringBuffer stringBuffer = new StringBuffer();
				while( ( line = reader.readLine() ) != null) {
					stringBuffer.append(line);
				}
				System.out.println("ERROR	" + stringBuffer.toString());*/
			}
			openURL = new URL(url.replaceAll("&__paging_token=[^&]+", ""));
		httpConn.disconnect();
		return reader;
	}
	
	@Override
	public String getResponseString(String url)  {
		BufferedReader br = null;
		try {
			br = getReader(url);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String line = null;
		StringBuffer buffer = new StringBuffer();
		
		try {
			while((line = br.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("ERROR	Message:[" + buffer + "]");
			return new String();
		}
		
		return buffer.toString();
	}
}
