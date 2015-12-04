package kr.co.opensns.ksbiz.socialbot.crawler.fetcher;

public interface Fetcher {
	public Object fetch();
	public Object fetch(Object obj);
	public void init();
}
