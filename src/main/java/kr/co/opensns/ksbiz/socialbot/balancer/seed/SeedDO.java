package kr.co.opensns.ksbiz.socialbot.balancer.seed;

public class SeedDO {
	String site;
	String seed;
	String type;
	long crawlCount;
	long crawledDocCount;
	long lastCrawledDate;
	long firstCrawledDate;
	String cursor;
	
	
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
	public long getLastCrawlDate() {
		return lastCrawledDate;
	}
	public long getFirstCrawlDate() {
		return firstCrawledDate;
	}
	public String getCursor() {
		return cursor;
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
	public void setLastCrawledDate(long lastCrawlDate) {
		this.lastCrawledDate = lastCrawlDate;
	}
	public void setFirstCrawledDate(long firstCrawlDate) {
		this.firstCrawledDate = firstCrawlDate;
	}
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}
}
