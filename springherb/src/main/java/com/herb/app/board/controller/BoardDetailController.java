package com.herb.app.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.herb.app.board.model.BoardService;
import com.herb.app.board.model.BoardVO;

@Controller
public class BoardDetailController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDetailController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/detail.do")
	public String detail(@RequestParam(defaultValue="0") int no, Model model){
		/*	5. 글 상세보기 - 조회
		/board/detail.do => BoardDetailController
		=> /board/detail.jsp */
		
		//1. 파라미터
		logger.info("파라미터 no={}", no);
		
		
		//2. db-select
		BoardVO BoardVo = boardService.selectByNo(no);
		
		//3. 결과 저장, 뷰페이지 리턴
		model.addAttribute("vo",BoardVo);
		
		return "board/detail";
		
	}
}
