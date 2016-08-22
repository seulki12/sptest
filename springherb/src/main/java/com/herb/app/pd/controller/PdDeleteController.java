package com.herb.app.pd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.herb.app.pd.model.PdService;


@Controller
public class PdDeleteController {
	@Autowired
	private PdService pdService;
	
	public PdDeleteController() {
		System.out.println("생성자 호출:PdDeleteController");
	}
		
	@RequestMapping("/pd/pdDelete.do")
	public ModelAndView deletePd(
		@RequestParam(value="no", defaultValue="0") int no ){
		/*
		 /pd/pdDelete.do => PdDeleteController
		*/
		//상품 삭제 처리
		//=> http://localhost:9090/springweb/pd/pdDelete.do?no=16
		//1. 
		System.out.println("컨트롤러: PdDeleteController의 "
				+ "deletePd() 호출, 파라미터 no="+no);
		
		//2.
		ModelAndView mav = new ModelAndView();
		if(no==0){
			mav.addObject("msg", "잘못된 URL입니다");
			mav.addObject("url", "/pd/pdList.do");
			mav.setViewName("common/message");
			return mav;
		}
		
		//3.
		int cnt = pdService.deletePd(no);
		String msg="", url="";
		if(cnt>0){
			msg="상품 삭제 성공";
			url="/pd/pdList.do";
		}else{
			msg="상품 삭제 실패";
			url="/pd/pdDetail.do?no="+no;
		}
		
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		return mav;
	}
	
}

