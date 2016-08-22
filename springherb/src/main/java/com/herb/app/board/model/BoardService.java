package com.herb.app.board.model;

import java.util.List;

import com.herb.app.common.SearchVO;

public interface BoardService {
	public int insertBoard(BoardVO vo);
	public List<BoardVO> selectAll(SearchVO searchVo);
	public int selectTotalCount(SearchVO searchVo);
	public int updateReadCount(int no);
	public BoardVO selectByNo(int no);
	public int updateBoard(BoardVO vo);
	public boolean checkPwd(int no, String pwd);
	public int deleteBoard(int no);
}
