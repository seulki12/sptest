package com.herb.app.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herb.app.board.model.BoardService;
import com.herb.app.board.model.BoardVO;
import com.herb.app.common.PaginationInfo;
import com.herb.app.common.SearchVO;
import com.herb.app.common.Utility;

@Controller
public class BoardListController {
	private static final Logger logger
	=LoggerFactory.getLogger(BoardListController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/list.do")
	public String listBoard(@ModelAttribute SearchVO searchVo,
			Model model){
		/*3. 글목록 조회
		/board/list.do => BoardListController
		=> /board/list.jsp*/
		//1. 파라미터 읽어오기
		logger.info("글목록 조회, 파라미터 searchVo={}",
				searchVo);
		
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		searchVo.setBlockSize(Utility.BLOCK_SIZE);
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
				
		//2. db작업 - select
		List<BoardVO> alist = boardService.selectAll(searchVo);
		logger.info("글목록 조회 결과 alist.size()={}", alist.size());
		
		//전체 레코드 개수 조회하기
		int totalRecord = boardService.selectTotalCount(searchVo);
		pagingInfo.setTotalRecord(totalRecord);
				
		//3. 결과 저장, 뷰페이지 리턴
		model.addAttribute("alist", alist);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "board/list";
	}
	
}










