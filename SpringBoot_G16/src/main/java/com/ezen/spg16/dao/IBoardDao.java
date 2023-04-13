package com.ezen.spg16.dao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spg16.dto.BoardVO;
import com.ezen.spg16.dto.Paging;
import com.ezen.spg16.dto.ReplyVO;

@Mapper
public interface IBoardDao {

	public List<BoardVO> selectBoardAll(Paging paging);
	public int getAllCount();
	public int getCount(int num);
	public void insertBoard(BoardVO bdto);
	public void plusReadCount(int num);
	public BoardVO getBoard(int num);
	public ArrayList<ReplyVO> selectReply(int num);
	public void addReply(ReplyVO rvo);
	public void deleteReply(int num);
	public void updateBoard(@Valid BoardVO boardvo);
	public void deleteReplyAll(int num);
	public void deleteBoard(int num);
	
	
}
