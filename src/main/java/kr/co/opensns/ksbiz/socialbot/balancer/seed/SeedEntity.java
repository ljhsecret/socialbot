package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Date;
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

	public SeedEntity() {
		this(null);
	}

	public SeedEntity(SeedDO seed) {
		if (seed == null)
			seed = new SeedDO();
		this.seed = seed;
	}

	public void update(Map<String, String> fields) {
		for (Iterator<String> itr = fields.keySet().iterator(); itr.hasNext();) {
			String fieldName = (String) itr.next();

			seed.visitCnt++;

			switch (fieldName.toLowerCase()) {
			case "lastDocId":
				seed.cursor = fields.get(fieldName);
				break;
			case "lastcrawldate":
				// seed.lastCrawledDate = Long.parseLong(fields.get(fieldName));
				break;
			case "crawleddoccount":
				// seed.crawledDocCount +=
				// Long.parseLong(fields.get(fieldName));
				break;
			default:
				break;
			}
		}
	}

	private double getAvrUpdateTime() {
		try {
			return (seed.lastVisitDate.getTime() / 1000L - seed.firstVisitDate
					.getTime() / 1000L) / seed.docCnt;
		} catch (Exception e) {
			return 0;
		}
	}

	public double getPriority() {
		return getAvrUpdateTime();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void setSiteId(int site) {
		this.seed.setSiteId(site);
	}

	public void setSeedId(String seed) {
		this.seed.setSeedId(seed);
	}

	public void setVisitCnt(int crawlCount) {
		this.seed.setVisitCnt(crawlCount);
	}

	public void setLastVisitDate(Date lastCrawlDate) {
		this.seed.setLastVisitDate(lastCrawlDate);
	}

	public void setFirstVisitDate(Date firstVisitDate) {
		this.seed.setFirstVisitDate(firstVisitDate);
	}

	public void setCursor(String cursor) {
		this.seed.setCursor(cursor);
	}

	public void setSeedType(int seedType) {
		this.seed.setSeedType(seedType);
	}

	public void setDocCount(int crawledDocCount) {
		this.seed.setDocCnt(crawledDocCount);
	}

	public String getSeedId() {
		return this.seed.getSeedId();
	}

	public int getSiteId() {
		return this.seed.getSiteId();
	}

	public int getSeedType() {
		return this.seed.getSeedType();
	}

	public String getCursor() {
		return this.seed.getCursor();
	}

	public int getCrawlType() {
		return this.seed.getCrlType();
	}

	public void setCrlType(String crlType) {
		int code;
		switch (crlType) {
		case "media":
			code = 01;
			break;
		case "comments":
			code = 02;
			break;
		case "likes":
			code = 03;
			break;

		default:
			code = -1;
			break;
		}
		this.seed.setCrlType(code);
	}
}