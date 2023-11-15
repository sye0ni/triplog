package com.ssafy.trip.board.photo.model.service;

import com.ssafy.trip.board.photo.model.BoardPhotoDto;

public interface PhotoService {

	void writePhoto(BoardPhotoDto boardPhotoDto) throws Exception;

	void listPhoto(int page, String userId) throws Exception;

}
