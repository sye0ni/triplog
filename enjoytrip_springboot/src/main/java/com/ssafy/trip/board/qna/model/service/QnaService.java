package com.ssafy.trip.board.qna.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.qna.model.BoardQnaDto;
import com.ssafy.trip.board.qna.model.BoardQnaListDto;

public interface QnaService {

	BoardQnaListDto listQna(Map<String, String> map) throws Exception;

	BoardQnaDto getQna(int qnaId) throws SQLException;

	List<CommentDto> listComment(int qnaId) throws SQLException;

	void writeQna(BoardQnaDto qnaDto) throws SQLException;

	void modifyQuestion(BoardQnaDto qnaDto) throws SQLException;

	void modifyComment(CommentDto commentDto) throws SQLException;

	void writeComment(int qnaId, CommentDto commentDto) throws SQLException;

	void deleteQna(int qnaId) throws SQLException;

	void deleteComment(int qnaId, int commentId) throws SQLException;
}
