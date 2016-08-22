package com.herb.app.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.herb.app.board.model.BoardService;
import com.herb.app.board.model.BoardVO;
import com.herb.app.common.PaginationInfo;
import com.herb.app.common.SearchVO;
import com.herb.app.common.Utility;

@Controller
public class BoardUpdateController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardUpdateController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/updateCount")
	public String updateCount(@RequestParam(defaultValue="0") int no, Model model){
		/*4. 조회수 증가
		/board/updateCount.do => BoardUpdateCountController
		=> /board/detail.do로 redirect*/
		//1. 파라미터 읽어오기
		logger.info("조회수 증가, 파라미터 no={}", no);
		
		//파라미터가 넘어오지 않은 경우
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			
			return "common/message";
		}
		
		//2. db작업 - update
		int cnt = boardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);
		
		//3. 결과 저장, 뷰페이지 리턴
		return "redirect:/board/detail.do?no=" + no;
	}
}










