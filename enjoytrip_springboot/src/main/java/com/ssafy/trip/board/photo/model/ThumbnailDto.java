package com.ssafy.trip.board.photo.model;

public class ThumbnailDto {
	
	int thumbnailId;
	String saveFolder;
	String originalFile;
	String saveFile;
	
	public int getThumbnailId() {
		return thumbnailId;
	}
	public void setThumbnailId(int thumbnailId) {
		this.thumbnailId = thumbnailId;
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
		return "ThumbnailDto [thumbnailId=" + thumbnailId + ", saveFolder=" + saveFolder + ", originalFile="
				+ originalFile + ", saveFile=" + saveFile + "]";
	}
	
	
}
