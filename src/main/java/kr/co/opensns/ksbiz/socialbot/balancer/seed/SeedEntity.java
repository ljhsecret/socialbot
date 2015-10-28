package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Date;

/**
 * 클래스 설명
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 20., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class SeedEntity {
	String channel;
	String site;
	String seed;
	String type;
	long crawlCount;
	long crawledDocCount;
	Date lastCrawlDate;
	String cursor;
	
	public String getChannel() {
		return channel;
	}
	public String getSite() {
		return site;
	}
	public String getSeed() {
		return seed;
	}
	public String getType() {
		return type;
	}
	public long getCrawlCount() {
		return crawlCount;
	}
	public long getCrawledDocCount() {
		return crawledDocCount;
	}
	public Date getLastCrawlDate() {
		return lastCrawlDate;
	}
	public String getCursor() {
		return cursor;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCrawlCount(long crawlCount) {
		this.crawlCount = crawlCount;
	}
	public void setCrawledDocCount(long crawledDocCount) {
		this.crawledDocCount = crawledDocCount;
	}
	public void setLastCrawlDate(Date lastCrawlDate) {
		this.lastCrawlDate = lastCrawlDate;
	}
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}
}