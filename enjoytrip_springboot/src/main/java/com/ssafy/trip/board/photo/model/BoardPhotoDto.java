package com.ssafy.trip.board.photo.model;

import java.util.List;

import com.ssafy.trip.board.model.CommentDto;

public class BoardPhotoDto {
	int boardPhotoId;
	String userId;
	String content;
	int sidoCode;
	int gugunCode;
	String place; 
	int like;
	String registerTime;
	int thumbnailId;

	String detailPlace; // 상세 위치 ( 시 군 사용자입력 위치)
	List<PhotoInfoDto> photoInfos;
	List<String> photoPaths; // 사진의 경로만 있으면 됨 
	
	
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
	public int getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	public int getGugunCode() {
		return gugunCode;
	}
	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
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
	public String getDetailPlace() {
		return detailPlace;
	}
	public void setDetailPlace(String detailPlace) {
		this.detailPlace = detailPlace;
	}
	public List<PhotoInfoDto> getPhotoInfos() {
		return photoInfos;
	}
	public void setPhotoInfos(List<PhotoInfoDto> photoInfos) {
		this.photoInfos = photoInfos;
	}
	public List<String> getPhotoPaths() {
		return photoPaths;
	}
	public void setPhotoPaths(List<String> photoPaths) {
		this.photoPaths = photoPaths;
	}
	
	@Override
	public String toString() {
		return "BoardPhotoDto [boardPhotoId=" + boardPhotoId + ", userId=" + userId + ", content=" + content
				+ ", sidoCode=" + sidoCode + ", gugunCode=" + gugunCode + ", place=" + place + ", like=" + like
				+ ", registerTime=" + registerTime + ", thumbnailId=" + thumbnailId + ", detailPlace=" + detailPlace
				+ ", photoInfos=" + photoInfos + ", photoPaths=" + photoPaths + "]";
	}
	
	
	

}
