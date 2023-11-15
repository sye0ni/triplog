package com.ssafy.trip.board.qna.model;

public class BoardQnaDto {
	private int boardQnaId;
	private String userId;
	private String title;
	private String content;
	private String registerTime;
	private int commentCnt;
	private int hit;

	public int getBoardQnaId() {
		return boardQnaId;
	}

	public void setBoardQnaId(int boardQnaId) {
		this.boardQnaId = boardQnaId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	@Override
	public String toString() {
		return "BoardQnaDto [boardQnaId=" + boardQnaId + ", userId=" + userId + ", title=" + title + ", content="
				+ content + ", registerTime=" + registerTime + ", commentCnt=" + commentCnt + ", hit=" + hit + "]";
	}

}
