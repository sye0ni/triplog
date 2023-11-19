package com.ssafy.trip.board.photo.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	List<BoardPhotoItemDto> getList(Map<String, Object> map) throws SQLException;
	
	List<BoardPhotoItemDto> getListByTimeAsc(Map<String, Object> map) throws SQLException;

	List<BoardPhotoItemDto> getListByLike(Map<String, Object> map) throws SQLException;

	List<BoardPhotoItemDto> getListByArea(Map<String, Object> map) throws SQLException;
	
	List<BoardPhotoItemDto> getListByTimeDesc(Map<String, Object> sqlMap) throws SQLException;

	List<BoardPhotoItemDto> getListByWord(Map<String, Object> sqlMap) throws SQLException;
	
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

	void deleteLike(Map<String, Object> map) throws SQLException;

	void addLike(Map<String, Object> map) throws SQLException;

	void updateLike(Map<String, Object> map) throws SQLException;





	
	

}
