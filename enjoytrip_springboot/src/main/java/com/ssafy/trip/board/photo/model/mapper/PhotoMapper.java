package com.ssafy.trip.board.photo.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.BoardPhotoItemDto;
import com.ssafy.trip.board.photo.model.SigunDto;
import com.ssafy.trip.board.photo.model.ThumbnailDto;

@Mapper
public interface PhotoMapper {

	void writePhoto(BoardPhotoDto boardPhotoDto) throws SQLException;

	void registerFile(BoardPhotoDto boardPhotoDto) throws SQLException;

	List<BoardPhotoItemDto> getList(int page) throws SQLException;

	List<SigunDto> getSigun() throws SQLException;

	int writeThumbnail(ThumbnailDto thumbnailDto) throws SQLException;

	BoardPhotoDto getPhoto(int photoId) throws SQLException;

	List<String> getPhotoList(int photoId) throws SQLException;

	List<CommentDto> listComment(int photoId) throws SQLException;

	void modifyPhoto(BoardPhotoDto photoDto) throws SQLException;

	void writeComment(CommentDto commentDto) throws SQLException;

	void deleteAllComments(int photoId) throws SQLException;

	void deletePhoto(int photoId) throws SQLException;

	void deleteAllPhotos(int photoId) throws SQLException;

	void deleteThumbnail(int photoId) throws SQLException;

	void modifyComment(CommentDto commentDto) throws SQLException;

	void deleteComment(int commentId) throws SQLException;
	
	

}
