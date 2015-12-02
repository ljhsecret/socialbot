package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

import java.util.List;

public class Likes {
	private List<User> data = null;
	private int count = 0;
	public List<User> getData() {
		return data;
	}
	public void setData(List<User> data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Likes [data=" + data + ", count=" + count + "]";
	}
}
