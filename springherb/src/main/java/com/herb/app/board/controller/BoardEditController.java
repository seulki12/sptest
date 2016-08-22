package com.herb.app.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.herb.app.board.model.BoardService;
import com.herb.app.board.model.BoardVO;

@Controller
@RequestMapping("/board/edit.do")
public class BoardEditController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardEditController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String updateBoard_get(@RequestParam(defaultValue="0") int no, Model model){
		
		logger.info("글수정 화면 파라미터 no={}", no);
		BoardVO boardVo = boardService.selectByNo(no);
		
		model.addAttribute("vo", boardVo);
		
		return "board/edit";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String updateBoard_post(@ModelAttribute BoardVO BoardVo, Model model){
		
		logger.info("글수정 파라미터 BoardVO={}", BoardVo);
		
		String msg="수정 완료",url="/board/detail.do?no=" + BoardVo.getNo();
		
		if(boardService.checkPwd(BoardVo.getNo(),BoardVo.getPwd())){
			//비밀번호 일치
			int cnt = boardService.updateBoard(BoardVo);
		}else{
			//비밀번호 불일치
			msg="비밀번호가 일치하지 않습니다";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		
		
		
		return "common/message";
		
	}
}
