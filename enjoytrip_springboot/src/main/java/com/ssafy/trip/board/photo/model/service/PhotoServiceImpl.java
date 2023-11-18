package com.ssafy.trip.board.photo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.BoardPhotoItemDto;
import com.ssafy.trip.board.photo.model.PhotoInfoDto;
import com.ssafy.trip.board.photo.model.SigunDto;
import com.ssafy.trip.board.photo.model.ThumbnailDto;
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
	public void writePhoto(ThumbnailDto thumbnailDto, BoardPhotoDto boardPhotoDto) throws Exception{
		System.out.println("글입력 전 dto : " + thumbnailDto);
		
		// 썸네일 먼저 생성 
		photoMapper.writeThumbnail(thumbnailDto);
		System.out.println("글입력 후 dto : " + thumbnailDto);

		// 게시글 생성 
		boardPhotoDto.setThumbnailId(thumbnailDto.getThumbnailId());
		photoMapper.writePhoto(boardPhotoDto);

		// 파일 생성 
		List<PhotoInfoDto> photoInfos=boardPhotoDto.getPhotoInfos();
		if(photoInfos!=null && !photoInfos.isEmpty()) {
			photoMapper.registerFile(boardPhotoDto);
		}
	}


	@Override
	public List<BoardPhotoItemDto> listPhoto(int page) throws Exception {
		// 게시글 번호, 작성자, 좋아요 수, thumbnail 경로 담기 
		return photoMapper.getList((page-1)*4);
		
	}

	@Override
	public List<SigunDto> getSigun() throws Exception {
		return photoMapper.getSigun();
	}

	@Override
	public int writeThumbnail(ThumbnailDto thumbnailDto) throws Exception {
		return photoMapper.writeThumbnail(thumbnailDto);
	}

	@Override
	public BoardPhotoDto getPhoto(int photoId) throws Exception {
		BoardPhotoDto photoDto=photoMapper.getPhoto(photoId);

		// 이미지들 경로 가져오기 
		List<String> photoPaths=photoMapper.getPhotoList(photoId);
		photoDto.setPhotoPaths(photoPaths);

		return photoDto;
	}

	@Override
	public List<CommentDto> listComment(int photoId) throws Exception {
		
		return photoMapper.listComment(photoId);
	}

	@Override
	public void modifyPhoto(BoardPhotoDto photoDto) throws Exception {
		photoMapper.modifyPhoto(photoDto);
	}

	@Override
	public void writeComment(int photoId, CommentDto commentDto) throws Exception {
		commentDto.setBoardId(photoId);
		photoMapper.writeComment(commentDto);
	}

	@Override
	public void deletePhoto(int photoId) throws Exception {
		// 관련 댓글들 먼저 삭제
		photoMapper.deleteAllComments(photoId);
		// 관련 파일들 삭제 
		photoMapper.deleteAllPhotos(photoId);
		
		int thumbnailId=getPhoto(photoId).getThumbnailId();
		
		// 글 삭제
		photoMapper.deletePhoto(photoId);
		
		// 썸네일 삭제
		photoMapper.deleteThumbnail(thumbnailId);
	
		
		
	}

}
