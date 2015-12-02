package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

import java.util.List;

public class Comments {
	private int count = 0;
	private List<Comment> data = null;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Comment> getData() {
		return data;
	}
	public void setData(List<Comment> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Comments [count=" + count + ", data=" + data + "]";
	}
}
