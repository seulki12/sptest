package com.herb.app.pd.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.herb.app.pd.model.PdDTO;
import com.herb.app.pd.model.PdService;


@Controller
public class PdWriteController {
	/*	
	  /pd/pdWrite.do => PdWriteController
	  => pdWrite.jsp */
	
	@Autowired
	private PdService pdService;
	
	public PdWriteController() {
		System.out.println("생성자 호출 : PdWriteController()");
	}
		
	@RequestMapping(value="/pd/pdWrite.do", 
			method=RequestMethod.GET)
	public ModelAndView pdWrite_get(){
		//=> get방식으로 /pd/pdWrite.do 요청이 들어온 경우
		//=> 화면을 보여줄 때 => 상품 등록화면 보여주기
		
		//1. 파라미터 읽어오기
		System.out.println("컨트롤러 : PdWriteController - "
				+ "pdWrite_get() 메서드 호출"); 
		
		//2. db작업
		//3. 결과, 뷰페이지 저장, 리턴
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pd/pdWrite");
		
		/*
		   /WEB-INF/view/ + pd/pdWrite + .jsp
		=> /WEB-INF/view/pd/pdWrite.jsp
		 */
		
		return mav;
	}

	@RequestMapping(value="/pd/pdWrite.do", 
			method=RequestMethod.POST)
	public ModelAndView pdWrite_post(
			@ModelAttribute PdDTO pdDto){
		/*
		  post방식으로 /pd/pdWrite.do 요청이 들어오는 경우
		  처리
		  => 상품 등록 처리-insert작업		   
		*/
		//1. 파라미터 읽어오기
		System.out.println("컨트롤러: PdWriteController-"
				+ "pdWrite_post() 메서드 호출, "
				+ "파라미터 pdDto="+pdDto);
		
		//2. db작업 - insert
		int cnt = pdService.insertPd(pdDto);
		
		//3. 결과, 뷰페이지 저장, 리턴
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pd/pdList.do");
		
		return mav;		
	}
	
}













