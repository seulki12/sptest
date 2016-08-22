package com.herb.app.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.herb.app.board.model.BoardService;

@Controller
@RequestMapping("/board/delete.do")
public class BoardDeleteController {
	private static final Logger logger = LoggerFactory.getLogger(BoardDeleteController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String delete_get(@RequestParam(defaultValue="0") int no, Model model){
		/*8. 글 삭제 화면 보여주기 - get
		/board/delete.do => BoardDeleteController
		=> /board/delete.jsp*/

		//=> http://localhost:9090/springherb/board/delete.do?no=250
		
		//1.
		logger.info("글삭제 화면 보여주기, 파라미터 no={}",no);
		
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			return "common/message";
		}
		
		//2.		
		//3.
		return "board/delete";		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String delete_post(@RequestParam(defaultValue="0") int no, @RequestParam String pwd, Model model){
		/*9. 글 삭제 처리 -post
		/board/delete.do => BoardDeleteController
		=> /board/list.do 로 redirect*/
		//1.
		logger.info("글삭제 처리 파라미터 no={}, pwd={}",no,pwd);
				
		//2.
		String viewPage="";
		if(boardService.checkPwd(no, pwd)){
			//비밀번호 일치
			int cnt = boardService.deleteBoard(no);
			logger.info("글삭제 결과, cnt={}", cnt);
			
			viewPage="redirect:/board/list.do"; 
		}else{
			//비밀번호 불일치
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다");
			model.addAttribute("url", "/board/delete.do?no="+no);
			viewPage="common/message";
		}
		
		//3.
		return viewPage;
	}
	
}








