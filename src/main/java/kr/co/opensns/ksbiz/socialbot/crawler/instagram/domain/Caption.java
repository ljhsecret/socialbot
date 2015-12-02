package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

public class Caption {
	private String createdTime = null;
	private String text = null;
	private User from = null;
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	@Override
	public String toString() {
		return "Caption [createdTime=" + createdTime + ", text=" + text
				+ ", from=" + from + "]";
	}
}
