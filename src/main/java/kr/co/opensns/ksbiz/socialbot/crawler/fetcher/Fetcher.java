package kr.co.opensns.ksbiz.socialbot.crawler.fetcher;

import kr.co.opensns.ksbiz.socialbot.lang.FetchException;

public interface Fetcher {
	public Object fetch() throws FetchException;
	public Object fetch(Object obj) throws FetchException;
	public void init();
}
