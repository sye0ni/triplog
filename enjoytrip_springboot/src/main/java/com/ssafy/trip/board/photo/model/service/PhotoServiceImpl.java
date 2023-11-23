package com.ssafy.trip.board.photo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<BoardPhotoItemDto> listPhoto(Map<String,Object> map) throws Exception {
		// page: 1, word: "", // 단어 검색  order: "", // 정렬  sidocode: "", guguncode: "", userId: ""
		System.out.println("리스트서비스!!!!!!!!!!!");
		System.out.println(map);

		int pg=(Integer.parseInt((String) map.get("page"))-1)*4;
		map.replace("page", pg);

		if(((String) map.get("word")).length()==0) { // 키워드 검색이 아니면? -> 정렬 + 지역 검색으로 
			if(((String) map.get("order")).length()>0) { // 정렬 조건 ㅇ -> 정렬 + 지역 검색 
				
				if(((String)map.get("sidocode")).length() ==0) { // 정렬만 

					if(map.get("order").equals("latest") || map.get("order").equals("")) { // 최신 정렬 ->> 기본 값 
						System.out.println("최신정렬1");
						return photoMapper.getList(map);
					}
					else if(map.get("order").equals("oldest")) { // 오래된 순 정렬 
						System.out.println("오래된 순 정렬1");
						return photoMapper.getListByTimeAsc(map);
					}
					else { // 조회수 정렬 
						System.out.println("조회수 1");
						return photoMapper.getListByLike(map);
					}
				}
				
				else { // 정렬 + 지역 검색 
					// 지역코드 숫자로 
					int sido=Integer.parseInt((String) map.get("sidocode"));
					map.replace("sidocode", sido);
					int gugun=Integer.parseInt((String) map.get("guguncode"));
					map.replace("guguncode", gugun);
					
					if(map.get("order").equals("latest")) { // 최신 정렬 
						System.out.println("최신순 정렬2");
						return photoMapper.getListByTimeDesc(map);
					}
					else if(map.get("order").equals("oldest")) { // 오래된 순 정렬 
						System.out.println("오래된순 정렬2");
						return photoMapper.getListByTimeAsc(map);
					}
					else { // 조회수 정렬 
						System.out.println("조회수 정렬2");
						return photoMapper.getListByLike(map);
					}
				}
				
			}
			
			else { // 정렬 조건 x -> 지역 검색만 
				if(((String) map.get("sidocode")).length()>0) { // 지역 검색 있음 
					// 숫자로 바꿔주기 
					int sido=Integer.parseInt((String) map.get("sidocode"));
					map.replace("sidocode", sido);
					int gugun=Integer.parseInt((String) map.get("guguncode"));
					map.replace("guguncode", gugun);

					return photoMapper.getListByArea(map);
				}
				else { // 지역 검색도 없음 
					return photoMapper.getList(map); 
				}
			}
		}
		
		else { // 키워드 검색!!!! 
			System.out.println("키워드검색44");
			return photoMapper.getListByWord(map);
		}
		

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
		System.out.println(photoId);
		

		BoardPhotoDto photoDto=photoMapper.getPhoto(photoId);
		System.out.println("photoID!!!!!!!!!!!!"+photoDto);
		System.out.println(photoDto);
		
		// 이미지들 경로 가져오기 
		List<String> photoPaths=photoMapper.getPhotoList(photoDto.getBoardPhotoId());

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

	@Override
	public void modifyComment(CommentDto commentDto) throws Exception {
		photoMapper.modifyComment(commentDto);
	}

	@Override
	public void deleteComment(int commentId) throws Exception {
		photoMapper.deleteComment(commentId);
	}

	@Override
	public void updateLike(Map<String, Object> map) throws Exception {
		// userId, photoId, state 
		if(map.get("state")==Integer.valueOf(1)) { // 삭제
			photoMapper.deleteLike(map);
		}
		else {
			photoMapper.addLike(map);
		}
		
		if(map.get("state")==Integer.valueOf(1)) {
			map.replace("state", "1");
		}
		else {
			map.replace("state", "0");
		}
		photoMapper.updateLike(map); // 이미지의 like 수 변경 
	}



}
