package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

public class Media {
	private String id = null;
	private Comments comments = null;
	private Caption caption = null;
	private Likes likes = null;
	
	public Likes getLikes() {
		return likes;
	}
	public void setLikes(Likes likes) {
		this.likes = likes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Comments getComments() {
		return comments;
	}
	public void setComments(Comments comments) {
		this.comments = comments;
	}
	public Caption getCaption() {
		return caption;
	}
	public void setCaption(Caption caption) {
		this.caption = caption;
	}
	
}
