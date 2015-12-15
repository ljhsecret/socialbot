package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Iterator;
import java.util.Map;

/**
 * 클래스 설명
 *
 * <pre>
 * <br>
 * <b>History:</b>
 * 		mhyoo, v1.0.0, 2015. 10. 20., 최초작성
 * </pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class SeedEntity {
	
	private SeedDO seed;
	
	public SeedEntity(){
		this(null);
	}

	public SeedEntity(SeedDO seed) {
		if(seed==null)
			seed = new SeedDO();
		this.seed = seed;
	}

	public void update(Map<String, String> fields) {
		for (Iterator<String> itr = fields.keySet().iterator(); itr.hasNext();) {
			String fieldName = (String) itr.next();

			seed.crawlCount++;

			switch (fieldName.toLowerCase()) {
			case "cursor":
				seed.cursor = fields.get(fieldName);
				break;
			case "lastcrawldate":
				seed.lastCrawledDate = Long.parseLong(fields.get(fieldName));
				break;
			case "crawleddoccount":
				seed.crawledDocCount += Long.parseLong(fields.get(fieldName));
				break;
			default:
				break;
			}
		}
	}

	private double getAvrUpdateTime() {
		try {
			return seed.lastCrawledDate - seed.firstCrawledDate / seed.crawlCount;
		} catch (Exception e) {
			return 0;
		} 
	}

	public double getPriority() {
		return Double.parseDouble(seed.seed);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	public void setSite(String site) {
		this.seed.setSite(site);
	}

	public void setSeed(String seed) {
		this.seed.setSeed(seed);
	}

	public void setCrawlCount(int crawlCount) {
		this.seed.setCrawlCount(crawlCount);
	}

	public void setCrawledDocCount(int crawledDocCount) {
		this.seed.setCrawledDocCount(crawledDocCount);
	}

	public void setLastCrawlDate(long lastCrawlDate) {
		this.seed.setLastCrawledDate(lastCrawlDate);
	}

	public void setFirstCrawlDate(long firstCrawlDate) {
		this.seed.setFirstCrawledDate(firstCrawlDate);
	}

	public void setCursor(String cursor) {
		this.seed.setCursor(cursor);
	}

	public void setType(String type) {
		this.seed.setType(type);
	}

	public void setCrawledDocCount(long crawledDocCount) {
		this.seed.setCrawledDocCount(crawledDocCount);
	}
	
	public String getSeed() {
		return this.seed.getSeed();
	}

	public String getSite() {
		return this.seed.getSite();
	}

	public String getType() {
		return this.seed.getType();
	}

	public String getCursor() {
		return this.seed.getCursor();
	}
}