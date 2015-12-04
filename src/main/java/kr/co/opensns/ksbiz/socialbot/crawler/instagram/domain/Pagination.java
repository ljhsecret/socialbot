package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

public class Pagination {
	private String nextUrl = null;
	private String nextMaxId = null;
	public String getNextUrl() {
		return nextUrl;
	}
	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
	public String getNextMaxId() {
		return nextMaxId;
	}
	public void setNextMaxId(String nextMaxId) {
		this.nextMaxId = nextMaxId;
	}
	
	public boolean hasNext() {
		try {
			return nextUrl.isEmpty()? false : true;
		}catch(NullPointerException e){
			System.out.println("INFO	Null Exception");
			return false;
		}
	}
	@Override
	public String toString() {
		return "Pagination [nextUrl=" + nextUrl + ", nextMaxId=" + nextMaxId
				+ "]";
	}
	
}
