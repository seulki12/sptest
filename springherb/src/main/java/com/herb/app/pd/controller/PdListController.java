package com.herb.app.pd.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.herb.app.pd.model.PdDTO;
import com.herb.app.pd.model.PdService;

@Controller
public class PdListController {
	@Autowired
	private PdService pdService;
	
	public PdListController() {
		System.out.println("생성자 호출 : PdListController()");
	}	
	
	@RequestMapping("/pd/pdList.do")
	public ModelAndView listPd(){
		/*
		 /pd/pdList.do => PdListController
				=> pdList.jsp */
		//상품 목록 조회
		
		//1. 파라미터 읽어오기
		System.out.println("컨트롤러 : PdListController의"
				+" listPd()메서드 호출");
		
		//2. db작업 - select
		List<PdDTO> alist =pdService.selectAllPd();
		
		//3. 결과, 뷰페이지 저장, 리턴
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pd/pdList");
		mav.addObject("alist", alist);
		
		return mav;
	}
}









