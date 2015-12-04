package kr.co.opensns.ksbiz.socialbot.internal.http;

public interface HttpClient {
	public String get(String url);

	public String getResponseString(String url);
}
