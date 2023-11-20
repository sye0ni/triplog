package com.ssafy.trip.user.model;

import java.util.List;

// 사용자 게시글 조회시 필요한 데이터를 담고 있음 
public class UserPhotoDto {
	int boardPhotoId;
	int commentCnt;
	int like;
	String thumbnail; // 썸네일의 url (폴더명 + 파일명)
	String location;
	String registerTime;
	
	public int getBoardPhotoId() {
		return boardPhotoId;
	}
	public void setBoardPhotoId(int boardPhotoId) {
		this.boardPhotoId = boardPhotoId;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "UserPhotoDto [boardPhotoId=" + boardPhotoId + ", commentCnt=" + commentCnt + ", like=" + like
				+ ", thumbnail=" + thumbnail + ", location=" + location + ", registerTime=" + registerTime + "]";
	}
	
	
}
