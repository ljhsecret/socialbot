package kr.co.opensns.ksbiz.socialbot.crawler.instagram;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis.ConnectionFactory;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedDO;
import kr.co.opensns.ksbiz.socialbot.crawler.fetcher.ApiFetcher;
import kr.co.opensns.ksbiz.socialbot.crawler.fetcher.Fetcher;
import kr.co.opensns.ksbiz.socialbot.crawler.instagram.auth.AccessToken;
import kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain.Media;
import kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain.Medias;
import kr.co.opensns.ksbiz.socialbot.internal.http.HttpClient;
import kr.co.opensns.ksbiz.socialbot.internal.http.HttpClientSimple;

public class MediaFetcher extends ApiFetcher {
	private AccessToken accessToken;
	private ConnectionFactory connectionFactory = new ConnectionFactory();
	public final static int SITE_ID = 1;
	public final static int SEED_TYPE = 2;
	public final static int CRL_TYPE = 1;
	public final static int CHANNEL_ID = 1;
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
		
		saveNewSeed(medias);
		
		return medias;
	}
	
	public void saveNewSeed(Medias medias) {
		List<SeedDO> newSeeds = new ArrayList<SeedDO>();
		for( Iterator<Media> iter = medias.getData().iterator(); iter.hasNext(); ) {
			Media media = iter.next();
			if( media.getId() == null ) continue;
			connectionFactory.insertSeedInfo(new SeedDO(media.getId(), SITE_ID, SEED_TYPE, CRL_TYPE, CHANNEL_ID, "", "", "", new Date(System.currentTimeMillis()), 0, new Date(System.currentTimeMillis()), "", "", "", ""));
//			System.out.println("new media id" +  media.getId());
		}
	}
}
