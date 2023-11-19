package com.ssafy.trip.board.photo.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.photo.model.BoardPhotoDto;
import com.ssafy.trip.board.photo.model.BoardPhotoItemDto;
import com.ssafy.trip.board.photo.model.PhotoInfoDto;
import com.ssafy.trip.board.photo.model.SigunDto;
import com.ssafy.trip.board.photo.model.ThumbnailDto;
import com.ssafy.trip.board.photo.model.service.PhotoService;
import com.ssafy.trip.board.qna.model.BoardQnaDto;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

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
	public ResponseEntity<?> writePhoto(@RequestPart("photoDto") BoardPhotoDto boardPhotoDto, @RequestPart(value="file",required=false) List<MultipartFile> files) throws Exception {
		
		System.out.println("================ 들어온 dto : "+boardPhotoDto.toString());
		System.out.println(files);
		
		// FileUpload 관련 설정
		logger.debug("MultipartFile.isEmpty : {}", files.get(0).isEmpty());
		
		
		ThumbnailDto thumbnailDto=new ThumbnailDto();
		
		if(!files.get(0).isEmpty()) {
			String today=new SimpleDateFormat("yyMMdd").format(new Date());

			// 파일들 업로드
			String saveFolder=uploadPath+File.separator+today;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder=new File(saveFolder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			
			List<PhotoInfoDto> photoInfos=new ArrayList<PhotoInfoDto>();
			MultipartFile mfile;
			String saveFileName = null;

			for (int i=0;i<files.size();i++) {
				mfile=files.get(i);			
				PhotoInfoDto photoInfoDto = new PhotoInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					photoInfoDto.setSaveFolder(today);
					photoInfoDto.setOriginalFile(originalFileName);
					photoInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				photoInfos.add(photoInfoDto);
				
				if(i==0) { // 썸네일 만들기 -> 사용자가 첫번째로 업로드한 파일로
					// 썸네일 생성 
					String fileName=files.get(0).getOriginalFilename(); // 썸네일 저장할 파일의 원본명 
					String thumbnailFile=UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf('.')); // 저장될 썸네일 파일명 

					String thumbnailFolder=uploadPath+File.separator+today+"_s"; // 썸네일 폴더명 
					
					File thumbnail = new File(thumbnailFolder); //세이브경로
					
					if(!thumbnail.exists()) {
						thumbnail.mkdirs();
					}

					File saveFile = new File(folder, saveFileName); // 기존 저장된 파일
					File thumbnailSaveFile = new File(thumbnailFolder + File.separator + thumbnailFile); // 썸네일 파일 경로
					String saveFilePath = saveFolder + File.separator + saveFileName; // 기존 저장된 파일 경로

					
					Thumbnails.of(saveFilePath)
		            	.size(200, 200)
		            	.outputQuality(0.9)
		            	.toFile(thumbnailSaveFile);
					
					
					thumbnailDto.setOriginalFile(fileName);
					thumbnailDto.setSaveFile(thumbnailFile);
					thumbnailDto.setSaveFolder(today+"_s");
					
				}
			}
			boardPhotoDto.setPhotoInfos(photoInfos);
		}
		
		photoService.writePhoto(thumbnailDto,boardPhotoDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> listPhoto(@RequestParam int page) throws Exception{
//        logger.info("listPhoto - 호출 {}", page);
        
        List<BoardPhotoItemDto> list=photoService.listPhoto(page);
        return new ResponseEntity<List<BoardPhotoItemDto>>(list,HttpStatus.OK);

	}
	
	@GetMapping("/sigun")
	public ResponseEntity<?> getSigun() throws Exception{
        logger.info("listPhoto - 호출 ");
        
        List<SigunDto> list=photoService.getSigun();
        return new ResponseEntity<List<SigunDto>>(list,HttpStatus.OK);
	}
	
	@GetMapping("/{photoId}")
	public ResponseEntity<?> getPhoto(@PathVariable int photoId) throws Exception{
		BoardPhotoDto photoDto=photoService.getPhoto(photoId);
		return new ResponseEntity<BoardPhotoDto>(photoDto,HttpStatus.OK);
	}
	
	@GetMapping("/{photoId}/comment")
	public ResponseEntity<?> listComment(@PathVariable int photoId) throws Exception {
		List<CommentDto> list=photoService.listComment(photoId);
		if(list!=null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(list);
		}
		return new ResponseEntity<List>(Collections.EMPTY_LIST,HttpStatus.OK);
	}
	
	@PutMapping("/{photoId}")
	public ResponseEntity<?> modifyPhoto(@PathVariable int photoId, @RequestBody BoardPhotoDto photoDto) throws Exception {
		logger.info("modifyPhoto - 호출 {}", photoDto);

		photoService.modifyPhoto(photoDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("/{photoId}")
	public ResponseEntity<?> deletePhoto(@PathVariable int photoId) throws Exception {
		logger.info("deletePhoto - 호출 {}", photoId);
		photoService.deletePhoto(photoId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@PostMapping("/{photoId}/comment")
	public ResponseEntity<?> writeComment(@PathVariable int photoId, @RequestBody CommentDto commentDto) throws Exception {
		logger.info("writeComment - 호출 {}", commentDto);
		System.out.println(photoId);
		photoService.writeComment(photoId,commentDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{photoId}/comment/{commentId}")
	public ResponseEntity<?> modifyComment(@PathVariable int photoId, @PathVariable int commentId, @RequestBody CommentDto commentDto) throws Exception {
		logger.info("modifyComment - 호출 {}", commentDto);
		
		photoService.modifyComment(commentDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@DeleteMapping("/{photoId}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int photoId, @PathVariable int commentId) throws Exception {
		logger.info("deleteComment - 호출 {}", commentId);
		photoService.deleteComment(commentId);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
}
