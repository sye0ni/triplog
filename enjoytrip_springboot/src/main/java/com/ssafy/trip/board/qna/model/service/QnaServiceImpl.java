package com.ssafy.trip.board.qna.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.qna.model.BoardQnaDto;
import com.ssafy.trip.board.qna.model.BoardQnaListDto;
import com.ssafy.trip.board.qna.model.mapper.QnaMapper;

@Service
public class QnaServiceImpl implements QnaService{
	
	private QnaMapper qnaMapper;
	
	@Autowired
	public QnaServiceImpl(QnaMapper qnaMapper) {
		super();
		this.qnaMapper = qnaMapper;
	}
	
	@Override
	public BoardQnaListDto listQna(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		// key
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		System.out.println("key!!!! " + param.get("key"));
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		
		List<BoardQnaDto> list = qnaMapper.listQna(param);
		
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = qnaMapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		//--- 담기
		BoardQnaListDto qnaListDto = new BoardQnaListDto();
		
		for(int i = 0; i < list.size(); i++) {
			String tmp = list.get(i).getRegisterTime();
			list.get(i).setRegisterTime(tmp.substring(0, 10));
		}
		
		qnaListDto.setList(list);
		qnaListDto.setCurrentPage(currentPage);
		qnaListDto.setTotalPageCount(totalPageCount);

		return qnaListDto;
	}
	
	@Override
	public BoardQnaDto getQna(int qnaId) throws SQLException {
		return qnaMapper.getQna(qnaId);
	}


	@Override
	public List<CommentDto> listComment(int qnaId) throws SQLException {
		return qnaMapper.listComment(qnaId);
	}
	
    @Override
    public void writeQna(BoardQnaDto qnaDto) throws SQLException {
        qnaMapper.writeQna(qnaDto);
    }


	@Override
	public void modifyQuestion(BoardQnaDto qnaDto) throws SQLException {
		qnaMapper.modifyQuestion(qnaDto);
	}


	@Override
	public void modifyComment(CommentDto commentDto) throws SQLException {
		qnaMapper.modifyComment(commentDto);
	}


	@Override
	public void writeComment(int qnaId, CommentDto commentDto) throws SQLException {
		Map<String,Integer> map=new HashMap<>();
		map.put("id",qnaId);
		map.put("type",1);
		
		commentDto.setBoardId(qnaId);
		qnaMapper.writeComment(commentDto);
		qnaMapper.updateCommentCnt(map);
	}


	@Override
	public void deleteQna(int qnaId) throws SQLException {
		// 관련 댓글들 먼저 삭제 
		qnaMapper.deleteAllComments(qnaId);
		// 글 삭제 
		qnaMapper.deleteQuestion(qnaId);
	}

	@Override
	public void deleteComment(int qnaId, int commentId) throws SQLException {
		Map<String,Integer> map=new HashMap<>();
		map.put("id",qnaId);
		map.put("type",-1);
		
		// 게시글 commentCnt -1 
		qnaMapper.updateCommentCnt(map);
		// 댓글 삭제
		qnaMapper.deleteComment(commentId);
	}


}
