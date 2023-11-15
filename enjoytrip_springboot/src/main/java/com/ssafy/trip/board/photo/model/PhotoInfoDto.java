package com.ssafy.trip.board.photo.model;

public class PhotoInfoDto {
	
	int photoInfoId;
	int photoId;
	String saveFolder;
	String originalFile;
	String saveFile;
	
	public int getPhotoInfoId() {
		return photoInfoId;
	}
	public void setPhotoInfoId(int photoInfoId) {
		this.photoInfoId = photoInfoId;
	}
	public int getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	public String getSaveFolder() {
		return saveFolder;
	}
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}
	public String getOriginalFile() {
		return originalFile;
	}
	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	
	@Override
	public String toString() {
		return "PhotoInfoDto [photoInfoId=" + photoInfoId + ", photoId=" + photoId + ", saveFolder=" + saveFolder
				+ ", originalFile=" + originalFile + ", saveFile=" + saveFile + "]";
	}
	
	
	
}
