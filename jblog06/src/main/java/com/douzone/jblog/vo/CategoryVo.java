package com.douzone.jblog.vo;

public class CategoryVo {
	private Long no;
	private Long count;
	private String name;
	private String desc;
	private String blogId;

	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", count=" + count + ", name=" + name + ", desc=" + desc + ", blogId=" + blogId
				+ "]";
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
}
