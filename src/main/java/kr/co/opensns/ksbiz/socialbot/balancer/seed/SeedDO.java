package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Date;

public class SeedDO {
	String seedId;
	int siteId;
	int seedType;
	int crlType;
	int channelId;
	String cursor;
	String writerName;
	String writerId;
	int avgUpdTime;
	int visitCnt;
	int docCnt;
	Date firstVisitDate;
	Date lastVisitDate;
	int friendsCnt;
	Date firstDocDate;
	Date lastDocDate;
	String activeYn;
	String location;
	String language;
	String timezone;
	public String getSeedId() {
		return seedId;
	}
	public int getSiteId() {
		return siteId;
	}
	public int getSeedType() {
		return seedType;
	}
	public int getCrlType() {
		return crlType;
	}
	public int getChannelId() {
		return channelId;
	}
	public String getCursor() {
		return cursor;
	}
	public String getWriterName() {
		return writerName;
	}
	public String getWriterId() {
		return writerId;
	}
	public int getAvgUpdTime() {
		return avgUpdTime;
	}
	public int getVisitCnt() {
		return visitCnt;
	}
	public int getDocCnt() {
		return docCnt;
	}
	public Date getFirstVisitDate() {
		return firstVisitDate;
	}
	public Date getLastVisitDate() {
		return lastVisitDate;
	}
	public int getFriendsCnt() {
		return friendsCnt;
	}
	public Date getFirstDocDate() {
		return firstDocDate;
	}
	public Date getLastDocDate() {
		return lastDocDate;
	}
	public String getActiveYn() {
		return activeYn;
	}
	public String getLocation() {
		return location;
	}
	public String getLanguage() {
		return language;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setSeedId(String seedId) {
		this.seedId = seedId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public void setSeedType(int seedType) {
		this.seedType = seedType;
	}
	public void setCrlType(int crlType) {
		this.crlType = crlType;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public void setAvgUpdTime(int avgUpdTime) {
		this.avgUpdTime = avgUpdTime;
	}
	public void setVisitCnt(int visitCnt) {
		this.visitCnt = visitCnt;
	}
	public void setDocCnt(int docCnt) {
		this.docCnt = docCnt;
	}
	public void setFirstVisitDate(Date firstVisitDate) {
		this.firstVisitDate = firstVisitDate;
	}
	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}
	public void setFriendsCnt(int friendsCnt) {
		this.friendsCnt = friendsCnt;
	}
	public void setFirstDocDate(Date firstDocDate) {
		this.firstDocDate = firstDocDate;
	}
	public void setLastDocDate(Date lastDocDate) {
		this.lastDocDate = lastDocDate;
	}
	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public SeedDO(String seedId, int siteId, int seedType, int crlType,
			int channelId, String cursor, String writerName, String writerId,
			Date firstVisitDate, int friendsCnt, Date firstDocDate,
			String activeYn, String location, String language, String timezone) {
		super();
		this.seedId = seedId;
		this.siteId = siteId;
		this.seedType = seedType;
		this.crlType = crlType;
		this.channelId = channelId;
		this.cursor = cursor;
		this.writerName = writerName;
		this.writerId = writerId;
		this.firstVisitDate = firstVisitDate;
		this.friendsCnt = friendsCnt;
		this.firstDocDate = firstDocDate;
		this.activeYn = activeYn;
		this.location = location;
		this.language = language;
		this.timezone = timezone;
	}
	public SeedDO() {
		super();
	}
	
}
