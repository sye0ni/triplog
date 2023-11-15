package com.ssafy.trip.board.photo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.PhotoInfoDto;
import com.ssafy.trip.board.photo.model.mapper.PhotoMapper;

@Service
public class PhotoServiceImpl implements PhotoService{

	private PhotoMapper photoMapper;
	
	@Autowired
	public PhotoServiceImpl(PhotoMapper photoMapper) {
		super();
		this.photoMapper = photoMapper;
	}

	@Override
	@Transactional
	public void writePhoto(BoardPhotoDto boardPhotoDto) throws Exception{
		System.out.println("글입력 전 dto : " + boardPhotoDto);
		photoMapper.writePhoto(boardPhotoDto);
//		System.out.println("글입력 후 dto : " + boardPhotoDto);

		List<PhotoInfoDto> photoInfos=boardPhotoDto.getPhotoInfos();
		if(photoInfos!=null && !photoInfos.isEmpty()) {
			photoMapper.registerFile(boardPhotoDto);
		}
	}

	@Override
	public void listPhoto(int page, String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
