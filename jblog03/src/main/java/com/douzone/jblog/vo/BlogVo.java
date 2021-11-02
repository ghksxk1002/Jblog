package com.douzone.jblog.vo;


public class BlogVo {
	private String id;
	private String title;
	private String logoUrl;
	public  String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logoUrl;
	}
	public void setLogo(String logo) {
		this.logoUrl = logo;
	}
	@Override
	public String toString() {
		return "BlogVo [id=" + id + ", title=" + title + ", logo=" + logoUrl + "]";
	}
	
	
}
