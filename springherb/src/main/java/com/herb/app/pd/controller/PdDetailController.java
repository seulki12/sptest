package com.herb.app.pd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.herb.app.pd.model.PdDTO;
import com.herb.app.pd.model.PdService;


@Controller
public class PdDetailController {
	@Autowired
	private PdService pdService;
	
	public PdDetailController() {
		System.out.println("생성자 호출: PdDetailController()");
	}
		
	@RequestMapping("/pd/pdDetail.do")
	public ModelAndView detailPd(
		@RequestParam(value="no",defaultValue="0") int no ){
		/*
		 /pd/pdDetail.do => PdDetailController
				=> pdDetail.jsp
		 */
		//상품 상세보기
		//=> http://localhost:9090/springweb/pd/pdDetail.do?no=16
		//1. 파라미터 읽어오기
		/*
		[1] @RequestParam
		[2] @ModelAttribute		  
		*/
		System.out.println("컨트롤러:PdDetailController의 "
				+ "detailPd()메서드 호출, 파라미터 no="+no);
		
		ModelAndView mav = new ModelAndView();
		if(no==0){
			mav.addObject("msg", "잘못된 URL입니다");
			mav.addObject("url", "/pd/pdList.do");
			mav.setViewName("common/message");
			
			return mav;
		}
		
		//2. db작업 - select
		PdDTO pdDto = pdService.selectByNo(no);
		
		//3. 결과, 뷰페이지 저장, 리턴
		mav.addObject("pdDto", pdDto);
		mav.setViewName("pd/pdDetail");
		
		return mav;
	}
	
}







