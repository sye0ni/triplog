package com.ssafy.trip.board.photo.model.service;

import java.util.List;

import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.BoardPhotoItemDto;

public interface PhotoService {

	void writePhoto(BoardPhotoDto boardPhotoDto) throws Exception;

	List<BoardPhotoItemDto> listPhoto(int page) throws Exception;

}
