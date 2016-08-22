package com.herb.app.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.herb.app.board.model.BoardService;
import com.herb.app.board.model.BoardVO;

@Controller
@RequestMapping("/board/write.do")
public class BoardWriteController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardWriteController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String write_get(){
		//handler
		/*1. 글쓰기 화면 보여주기
		/board/write.do => BoardWriteController
		=> /board/write.jsp*/
		logger.info("글쓰기 화면 보여주기");
		
		String viewPage = "board/write";
		return viewPage;
				
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String write_post(@ModelAttribute BoardVO boardVo){
		//글쓰기 처리 - post
		
		//1. 파라미터 읽어오기
		logger.info("글쓰기 처리, 파라미터 boardVo={}", boardVo);
		
		//2. db작업 - insert
		/*
			board.xml => BoardDAO => BoardDAOMybatis
			=> BoardService => BoardServiceImpl => BoardWriteController
		*/
		int cnt = boardService.insertBoard(boardVo);
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		//3. 결과저장, 뷰페이지 리턴
		return "redirect:/board/list.do";
	}
}
