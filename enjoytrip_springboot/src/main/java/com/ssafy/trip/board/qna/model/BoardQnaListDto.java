package com.ssafy.trip.board.qna.model;

import java.util.List;

public class BoardQnaListDto {

	private List<BoardQnaDto> list;
	private int currentPage;
	private int totalPageCount;

	public List<BoardQnaDto> getList() {
		return list;
	}

	public void setList(List<BoardQnaDto> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	@Override
	public String toString() {
		return "BoardQnaListDto [list=" + list + ", currentPage=" + currentPage + ", totalPageCount=" + totalPageCount
				+ "]";
	}

}
