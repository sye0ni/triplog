package com.ssafy.trip.board.photo.model;

public class BoardPhotoItemDto {
	int boardPhotoId; // 게시글 번호 
	String userId; // 게시글 작성자 
	int like;  // 좋아요 수 
	String thumbNail; // 폴더명(날짜) +/+ 파일명 
	int isLike; // 좋아요 여부 : 1이면 true 
	
	public int getBoardPhotoId() {
		return boardPhotoId;
	}
	public void setBoardPhotoId(int boardPhotoId) {
		this.boardPhotoId = boardPhotoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getThumbNail() {
		return thumbNail;
	}
	public void setThumbNail(String thumbNail) {
		this.thumbNail = thumbNail;
	}
	public int getIsLike() {
		return isLike;
	}
	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}
	@Override
	public String toString() {
		return "BoardPhotoItemDto [boardPhotoId=" + boardPhotoId + ", userId=" + userId + ", like=" + like
				+ ", thumbNail=" + thumbNail + ", isLike=" + isLike + "]";
	}
	
	
}
