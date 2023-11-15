package com.ssafy.trip.board.photo.model;

public class ThumbnailDto {
	
	int thumbnailId;
	String saveFoler;
	String originalFile;
	String saveFile;
	
	public int getThumbnailId() {
		return thumbnailId;
	}
	public void setThumbnailId(int thumbnailId) {
		this.thumbnailId = thumbnailId;
	}
	public String getSaveFoler() {
		return saveFoler;
	}
	public void setSaveFoler(String saveFoler) {
		this.saveFoler = saveFoler;
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
		return "ThumbnailDto [thumbnailId=" + thumbnailId + ", saveFoler=" + saveFoler + ", originalFile="
				+ originalFile + ", saveFile=" + saveFile + "]";
	}
	
	
}
