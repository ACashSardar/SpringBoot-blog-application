package com.akash.blog.entity;

public class Like {
	private Integer likeId;
	private String likedBy;
	public Like(Integer likeId, String likedBy) {
		super();
		this.likeId = likeId;
		this.likedBy = likedBy;
	}
	public Integer getLikeId() {
		return likeId;
	}
	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}
	public String getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(String likedBy) {
		this.likedBy = likedBy;
	}
	
}
