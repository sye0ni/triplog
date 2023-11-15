package com.ssafy.trip.board.photo.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.board.photo.model.BoardPhotoDto;

@Mapper
public interface PhotoMapper {

	void writePhoto(BoardPhotoDto boardPhotoDto) throws SQLException;

	void registerFile(BoardPhotoDto boardPhotoDto) throws SQLException;

}
