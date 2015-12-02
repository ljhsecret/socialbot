package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

public class Comment {
	private String createdTime = null;
	private String text = null;
	private User from = null;
	private String id = null;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Comment(String createdTime, String text, User from, String id) {
		super();
		this.createdTime = createdTime;
		this.text = text;
		this.from = from;
		this.id = id;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Comment [createdTime=" + createdTime + ", text=" + text
				+ ", from=" + from + ", id=" + id + "]";
	}
}
