package com.ssafy.trip.board.qna.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.qna.model.BoardQnaDto;

@Mapper
public interface QnaMapper {
	
	List<BoardQnaDto> listQna(Map<String, Object> param) throws SQLException;
	
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	
	BoardQnaDto getQna(int qnaId) throws SQLException;

	List<CommentDto> listComment(int qnaId) throws SQLException;

    void writeQna(BoardQnaDto qnaDto) throws SQLException;
	
	void modifyQuestion(BoardQnaDto qnaDto) throws SQLException;

	void modifyComment(CommentDto commentDto) throws SQLException;

	void writeComment(CommentDto commentDto) throws SQLException;

	void updateCommentCnt(Map<String,Integer> map) throws SQLException;

	void deleteAllComments(int qnaId) throws SQLException;

	void deleteQuestion(int qnaId) throws SQLException;

	void deleteComment(int commentId) throws SQLException;


}
