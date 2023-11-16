package com.ssafy.trip.board.photo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.BoardPhotoItemDto;
import com.ssafy.trip.board.photo.model.PhotoInfoDto;
import com.ssafy.trip.board.photo.model.mapper.PhotoMapper;

@Service
public class PhotoServiceImpl implements PhotoService{

	private PhotoMapper photoMapper;
	
	@Value("${file.path}")
	private String uploadPath;
	
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
	public List<BoardPhotoItemDto> listPhoto(int page) throws Exception {
		// 게시글 번호, 작성자, 좋아요 수, thumbnail 경로 담기 
		List<BoardPhotoItemDto> list=photoMapper.getList((page-1)*4);
//		String temp="";
//		for(int i=0;i<list.size();i++) {
//			temp=list.get(i).getThumbNail();
//			list.get(i).setThumbNail(uploadPath+temp);
//		}
		return list; 
		
	}

}
