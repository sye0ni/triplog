package com.ssafy.trip.board.photo.model;

import java.util.List;

public class BoardPhotoDto {
	int boardPhotoId;
	String userId;
	String content;
	int location;
	String place;
	int like;
	String registerTime;
	int thumbnailId;
	List<PhotoInfoDto> photoInfos;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public int getThumbnailId() {
		return thumbnailId;
	}
	public void setThumbnailId(int thumbnailId) {
		this.thumbnailId = thumbnailId;
	}
	public List<PhotoInfoDto> getPhotoInfos() {
		return photoInfos;
	}
	public void setPhotoInfos(List<PhotoInfoDto> photoInfos) {
		this.photoInfos = photoInfos;
	}
	
	@Override
	public String toString() {
		return "BoardPhotoDto [boardPhotoId=" + boardPhotoId + ", userId=" + userId + ", content=" + content
				+ ", location=" + location + ", place=" + place + ", like=" + like + ", registerTime=" + registerTime
				+ ", thumbnailId=" + thumbnailId + ", photoInfos=" + photoInfos + "]";
	}

}
