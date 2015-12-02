package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

public class User {
	private String userName = null;
	private String profilePicture = null;
	private String id = null;
	private String fullName = null;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public User(String id) {
		super();
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", profilePicture="
				+ profilePicture + ", id=" + id + ", fullName=" + fullName
				+ "]";
	}
}
