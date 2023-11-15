package com.ssafy.trip.board.photo.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.PhotoInfoDto;
import com.ssafy.trip.board.photo.model.service.PhotoService;


@RequestMapping("/board/photo")
@CrossOrigin("*")
@RestController
public class PhotoController {
	
private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.upload-files}")
	private String uploadFilePath;

	private PhotoService photoService;
	
	@Autowired
	public PhotoController(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	
	@PostMapping
	public ResponseEntity<?> writePhoto(@RequestPart("photoDto") BoardPhotoDto boardPhotoDto, @RequestPart(value="file",required=false) MultipartFile[] files) throws Exception {
		
		// FileUpload 관련 설정
		logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
		
		System.out.println("================ 들어온 dto : "+boardPhotoDto.toString());
		
		if(!files[0].isEmpty()) {
			String today=new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder=uploadPath+File.separator+today;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder=new File(saveFolder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			List<PhotoInfoDto> photoInfos=new ArrayList<PhotoInfoDto>();
			
			for (MultipartFile mfile : files) {
				// 썸네일 만들고.. thumbnailId 설정 해야함 ! 
				PhotoInfoDto photoInfoDto = new PhotoInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					photoInfoDto.setSaveFolder(today);
					photoInfoDto.setOriginalFile(originalFileName);
					photoInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				photoInfos.add(photoInfoDto);
			}
			boardPhotoDto.setPhotoInfos(photoInfos);
		}
		
		photoService.writePhoto(boardPhotoDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	
}
