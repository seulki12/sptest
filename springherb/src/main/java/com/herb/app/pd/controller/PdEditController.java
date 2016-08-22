package com.herb.app.pd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.herb.app.pd.model.PdDTO;
import com.herb.app.pd.model.PdService;


@Controller
@RequestMapping("/pd/pdEdit.do")
public class PdEditController {
	@Autowired
	private PdService pdService;
	
	public PdEditController() {
		System.out.println("생성자 호출: PdEditController");
	}	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView editPd(
		@RequestParam(value="no",defaultValue="0") int no){
		/*
		 /pd/pdEdit.do => PdEditController
		  => pdEdit.jsp
		*/
		//상품 상세정보 조회
		//=> http://localhost:9090/springweb/pd/pdEdit.do?no=17
		
		//1. 파라미터 읽어오기
		System.out.println("컨트롤러:PdEditController의 "
				+ "editPd()메서드 호출, 파라미터 no="+no);
		
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
		mav.setViewName("pd/pdEdit");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView edit_post(
		@ModelAttribute PdDTO pdDto){
		//1. 파라미터 읽어오기
		System.out.println("컨트롤러:PdEditController의 "
				+ "edit_post()호출, 파라미터 pdDto="+pdDto);
		
		//2. db작업 - update
		int cnt = pdService.updatePd(pdDto);
				
		//3. 결과, 뷰페이지 저장, 리턴
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pd/pdDetail.do?no="
			+ pdDto.getNo());
		
		return mav;
	}
	
}








