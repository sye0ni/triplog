package com.ssafy.trip.board.photo.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.BoardPhotoItemDto;

@Mapper
public interface PhotoMapper {

	void writePhoto(BoardPhotoDto boardPhotoDto) throws SQLException;

	void registerFile(BoardPhotoDto boardPhotoDto) throws SQLException;

	List<BoardPhotoItemDto> getList(int page) throws SQLException;

}
