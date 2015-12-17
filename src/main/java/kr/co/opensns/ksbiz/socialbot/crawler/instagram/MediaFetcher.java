package kr.co.opensns.ksbiz.socialbot.crawler.instagram;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.opensns.ksbiz.socialbot.crawler.fetcher.ApiFetcher;
import kr.co.opensns.ksbiz.socialbot.crawler.fetcher.Fetcher;
import kr.co.opensns.ksbiz.socialbot.crawler.instagram.auth.AccessToken;
import kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain.Medias;
import kr.co.opensns.ksbiz.socialbot.internal.http.HttpClient;
import kr.co.opensns.ksbiz.socialbot.internal.http.HttpClientSimple;

public class MediaFetcher extends ApiFetcher {
	private AccessToken accessToken;
	
	public void init() {
		
	}

	public MediaFetcher(AccessToken accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public MediaFetcher() {
		super();
		accessToken = new AccessToken();
	}

	@Override
	public Object fetch() {
		return null;
	}
	
	public Object fetchById(String id) {
		HttpClient httpClient = new HttpClientSimple();
		String url = getUrlRecentMedia(id);
		String jsonStr = httpClient.getResponseString(url);
		
		Gson gson = new GsonBuilder()
	    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
	    .create();
		Medias medias = gson.fromJson(jsonStr, Medias.class);
		return null;
	}
	
	public String getUrlRecentMedia(String userId) {
		return "https://api.instagram.com/v1/users/" + userId + "/media/recent?access_token=" + accessToken.getToken(); 
	}

	@Override
	public Object fetch(Object obj) {
		System.out.println("Call fetch. jobId: " + obj.toString());
		String id = (String)obj;
		HttpClient httpClient = new HttpClientSimple();
		String url = getUrlRecentMedia(id);
		String jsonStr = httpClient.getResponseString(url);
		
		Gson gson = new GsonBuilder()
	    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
	    .create();
		Medias medias = gson.fromJson(jsonStr, Medias.class);
		return medias;
	}
	
}
