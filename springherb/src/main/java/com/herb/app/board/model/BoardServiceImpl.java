package com.herb.app.board.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herb.app.common.SearchVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDao;
	
	private  static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	public BoardServiceImpl(){
		logger.info("생성자 : BoardServiceImpl()");
	}
	
	@Transactional
	public int insertBoard(BoardVO vo){
		return boardDao.insertBoard(vo);
	}
	
	public List<BoardVO> selectAll(SearchVO searchVo){
		return boardDao.selectAll(searchVo);
	}

	@Override
	public int selectTotalCount(SearchVO searchVo) {
		return boardDao.selectTotalCount(searchVo);
	}
	
	public int updateReadCount(int no){
		return boardDao.updateReadCount(no);
	}
	
	public BoardVO selectByNo(int no){
		return boardDao.selectByNo(no);
	}
	
	public int updateBoard(BoardVO vo){
		return boardDao.updateBoard(vo);
	}
	
	public boolean checkPwd(int no, String pwd) {
		boolean bool=false;
		
		String dbPwd = boardDao.selectPwd(no);
		if(dbPwd.equals(pwd)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public int deleteBoard(int no){
		return boardDao.deleteBoard(no);
	}
	
/*	public List<BoardVO> listMainNotice(){
		return boardDao.listMainNotice();
	}

	public int deleteBoard(int no){
		return boardDao.deleteBoard(no);
	}*/
}
