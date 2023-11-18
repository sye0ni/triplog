package com.ssafy.trip.board.photo.model.service;

import java.util.List;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.BoardPhotoItemDto;
import com.ssafy.trip.board.photo.model.SigunDto;
import com.ssafy.trip.board.photo.model.ThumbnailDto;

public interface PhotoService {

	void writePhoto(ThumbnailDto thumbnailDto, BoardPhotoDto boardPhotoDto) throws Exception;

	List<BoardPhotoItemDto> listPhoto(int page) throws Exception;

	List<SigunDto> getSigun() throws Exception;

	int writeThumbnail(ThumbnailDto thumbnailDto) throws Exception;

	BoardPhotoDto getPhoto(int photoId) throws Exception;

	List<CommentDto> listComment(int photoId) throws Exception;

	void modifyPhoto(BoardPhotoDto photoDto) throws Exception;

	void writeComment(int photoId, CommentDto commentDto) throws Exception;

	void deletePhoto(int photoId) throws Exception;


}
