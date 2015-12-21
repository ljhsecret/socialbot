package kr.co.opensns.ksbiz.socialbot.internal.http;

import kr.co.opensns.ksbiz.socialbot.lang.FetchException;

public interface HttpClient {
	public String get(String url);

	public String getResponseString(String url) throws FetchException;
}
