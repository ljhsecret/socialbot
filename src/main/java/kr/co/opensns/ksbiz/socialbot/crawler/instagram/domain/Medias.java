package kr.co.opensns.ksbiz.socialbot.crawler.instagram.domain;

import java.util.ArrayList;
import java.util.List;

public class Medias {
	private Meta meta = null;
	private Pagination pagination = null;
	private List<Media> data = null;
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public List<Media> getData() {
		return data;
	}
	public void setData(List<Media> data) {
		this.data = data;
	}
	
	public void addMedias(List<Media> medias) {
		data.addAll(medias);
	}
	public Medias() {
		super();
		data = new ArrayList<Media>();
		pagination = new Pagination();
		meta = new Meta();
	}
	
	public Medias(Meta meta, Pagination pagination, List<Media> data) {
		super();
		this.meta = meta;
		this.pagination = pagination;
		this.data = data;
	}
	@Override
	public String toString() {
		return "Medias [meta=" + meta + ", pagination=" + pagination
				+ ", data=" + data + "]";
	}
	
}
