package com.ssafy.trip.board.qna.controller;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.qna.model.BoardQnaDto;
import com.ssafy.trip.board.qna.model.BoardQnaListDto;
import com.ssafy.trip.board.qna.model.service.QnaService;

@RequestMapping("/board/qna")
@CrossOrigin("*")
@RestController
public class QnaController {
	
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	
	private QnaService qnaService;
	
	@Autowired
	public QnaController(QnaService qnaService) {
		this.qnaService = qnaService;
	}
	
	@PostMapping
    public ResponseEntity<?> writeQna(@RequestBody BoardQnaDto qnaDto) throws SQLException {
        logger.info("writeQna - 호출 {}", qnaDto);
        qnaService.writeQna(qnaDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
	@GetMapping
	public ResponseEntity<?> listQna(@RequestParam Map<String, String> map) {
		// map -> ... ?pgno, key, word ..
		logger.info("listArticle map - {}", map);
		System.out.println("list map " + map);
		try {
			BoardQnaListDto qnaListDto = qnaService.listQna(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(qnaListDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	@GetMapping("/{qnaId}")
	public ResponseEntity<?> getQna(@PathVariable int qnaId) throws SQLException {
	
		BoardQnaDto qnaDto=qnaService.getQna(qnaId);
		return new ResponseEntity<BoardQnaDto>(qnaDto, HttpStatus.OK);
	}
	
	
	@PutMapping("/{qnaId}")
	public ResponseEntity<?> modifyQna(@PathVariable int qnaId, @RequestBody BoardQnaDto qnaDto) throws SQLException {
//		BoardQnaDto qnaDto=qnaService.getQna(qnaId);
		logger.info("modifyQna - 호출 {}", qnaDto);

		qnaService.modifyQuestion(qnaDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@DeleteMapping("/{qnaId}")
	public ResponseEntity<?> deleteQna(@PathVariable int qnaId) throws SQLException {
		logger.info("deleteQna - 호출 {}", qnaId);
		qnaService.deleteQna(qnaId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/{qnaId}/comment")
	public ResponseEntity<?> writeComment(@PathVariable int qnaId, @RequestBody CommentDto commentDto) throws SQLException {
		logger.info("writeComment - 호출 {}", commentDto);
		System.out.println(qnaId);
		qnaService.writeComment(qnaId,commentDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{qnaId}/comment")
	public ResponseEntity<?> listComment(@PathVariable int qnaId) throws SQLException {
		List<CommentDto> list=qnaService.listComment(qnaId);
		if(list!=null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(list);
		}
		return new ResponseEntity<List>(Collections.EMPTY_LIST,HttpStatus.OK);
	}
	
	// 댓글 하나만 조회하는 기능은 필요 X.. ?
	// --
	
	@PutMapping("/{qnaId}/comment/{commentId}")
	public ResponseEntity<?> modifyComment(@PathVariable String  qnaId, @PathVariable String commentId, @RequestBody CommentDto commentDto) throws SQLException {
		logger.info("modifyComment - 호출 {}", commentDto);
		
		qnaService.modifyComment(commentDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@DeleteMapping("/{qnaId}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int qnaId, @PathVariable int commentId) throws SQLException {
		logger.info("deleteComment - 호출 {}", commentId);
		qnaService.deleteComment(qnaId,commentId);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
}
