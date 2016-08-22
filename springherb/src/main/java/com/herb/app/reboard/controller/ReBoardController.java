package com.herb.app.reboard.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.herb.app.common.PaginationInfo;
import com.herb.app.common.SearchVO;
import com.herb.app.common.Utility;
import com.herb.app.reboard.model.ReBoardService;
import com.herb.app.reboard.model.ReBoardVO;

@Controller
public class ReBoardController {
	private static final Logger logger
	=LoggerFactory.getLogger(ReBoardController.class);
	
	@Autowired
	private ReBoardService reBoardService;
	
	@RequestMapping(value="/reBoard/write.do", method=RequestMethod.GET)
	public String write_get(){
		//handler
		/*1. 글쓰기 화면 보여주기
		/reBoard/write.do => ReBoardWriteController
		=> /reBoard/write.jsp*/
		logger.info("글쓰기 화면 보여주기");
				
		String viewPage="reBoard/write";
		return viewPage;
	}
	
	@RequestMapping(value="/reBoard/write.do", method=RequestMethod.POST)
	public String write_post(HttpServletRequest request
			,@ModelAttribute ReBoardVO reBoardVo){
		//글쓰기 처리 - post
		//1. 파라미터 읽어오기
		logger.info("글쓰기 처리, 파라미터 reBoardVo={}", reBoardVo);
		
		//2. db작업 - insert
		//[1] 파일업로드 처리하기
		List<Map<String, Object>> fileList
			=reBoardService.fileupload(request);
		
		String fileName ="";		
		String ofileName="";
		long fileSize=0;
		for(Map<String, Object> myMap : fileList){
			fileName =(String) myMap.get("fileName");
			ofileName
			=(String) myMap.get("originalFileName");
			fileSize=(Long) myMap.get("fileSize");
		}//for
		
		reBoardVo.setFileName(fileName);
		reBoardVo.setOriginalFileName(ofileName);
		reBoardVo.setFileSize(fileSize);
		
		//[2] db에 insert하기
		/*
		reBoard.xml => ReBoardDAO => ReBoardDAOMybatis
		=> ReBoardService => ReBoardServiceImpl
		=> ReBoardWriteController  
		*/
		int cnt = reBoardService.insertReBoard(reBoardVo);
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		//3. 결과저장, 뷰페이지 리턴
		return "redirect:/reBoard/list.do";
	}
	
	@RequestMapping("/reBoard/list.do")
	public String listReBoard(@ModelAttribute SearchVO searchVo,
			Model model){
		/*3. 글목록 조회
		/reBoard/list.do => ReBoardListController
		=> /reBoard/list.jsp*/
		//1. 파라미터 읽어오기
		logger.info("글목록 조회, 파라미터 searchVo={}",
				searchVo);
		
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		searchVo.setBlockSize(Utility.BLOCK_SIZE);
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
				
		//2. db작업 - select
		List<ReBoardVO> alist = reBoardService.selectAll(searchVo);
		logger.info("글목록 조회 결과 alist.size()={}", 
				alist.size());
		
		//전체 레코드 개수 조회하기
		int totalRecord 
			= reBoardService.selectTotalCount(searchVo);
		pagingInfo.setTotalRecord(totalRecord);
				
		//3. 결과 저장, 뷰페이지 리턴
		model.addAttribute("alist", alist);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "reBoard/list";
	}
	
	@RequestMapping("/reBoard/updateCount.do")
	public String updateCount(
		@RequestParam(defaultValue="0") int no, Model model){
		/*4. 조회수 증가
		/reBoard/updateCount.do => ReBoardUpdateCountController
		=> /reBoard/detail.do 로 redirect*/
		//http://localhost:9090/springherb/reBoard/updateCount.do?no=251
		
		//1. 파라미터 읽어오기
		logger.info("조회수 증가, 파라미터 no={}", no);
		
		//파라미터가 넘어오지 않은 경우
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
				
		//2. db작업 - update
		int cnt =reBoardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);
		
		//3. 결과 저장, 뷰페이지 리턴
		return "redirect:/reBoard/detail.do?no="+no;
	}
	
	@RequestMapping("/reBoard/detail.do")
	public String detail(
		@RequestParam(defaultValue="0") int no, 
		HttpServletRequest request,
		Model model){
		/*5. 글 상세보기 - 조회
		/reBoard/detail.do => ReBoardDetailController
		=> /reBoard/detail.jsp*/
		//http://localhost:9090/springherb/reBoard/detail.do?no=251
		
		//1. 파라미터
		logger.info("글상세보기, 파라미터 no={}",no);
		
		//파라미터가 넘어오지 않은 경우
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2. db-select
		ReBoardVO reBoardVo= reBoardService.selectByNo(no);
		
		//첨부파일 정보
		String fileInfo="", downInfo="";
		if(reBoardVo.getFileName()!=null && !reBoardVo.getFileName().isEmpty()){
			String contextPath = request.getContextPath();
			double fileSize = Math.round((reBoardVo.getFileSize()/1000.0)*10)/10.0;
			
			fileInfo="<img src='"+ contextPath 
					+"/images/file.gif' alt='파일이미지'>";
			fileInfo+=reBoardVo.getOriginalFileName() 
					+ "(" + fileSize + "KB)";
		
			downInfo="다운:" + reBoardVo.getDownCount();
		}
		
		
		//3. 결과 저장, 뷰페이지 리턴
		model.addAttribute("vo", reBoardVo);
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("downInfo", downInfo);
		
		
		return "reBoard/detail";	
		
	}
	
	@RequestMapping(value="/reBoard/edit.do", 
			method=RequestMethod.GET)
	public String edit_get(@RequestParam(defaultValue="0") 
		int no, Model model){
		/*6. 글 수정 화면 보여주기 - get
		/reBoard/edit.do => ReBoardEditController
		=> /reBoard/edit.jsp*/
		//http://localhost:9090/springherb/reBoard/edit.do?no=249
		
		//1.
		logger.info("글수정 화면 보여주기, 파라미터 no={}",no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		//2.
		ReBoardVO reBoardVo =reBoardService.selectByNo(no);
		
		//3.
		model.addAttribute("reBoardVo", reBoardVo);
		return "reBoard/edit";
	}
	
	@RequestMapping(value="/reBoard/edit.do", 
			method=RequestMethod.POST)	
	public String edit_post(@ModelAttribute ReBoardVO reBoardVo, HttpServletRequest request,
			@RequestParam String oldFileName, @RequestParam String oldOriginFileName,
			@RequestParam long oldFileSize,Model model){
		/*7. 글 수정 처리 - post
		/reBoard/edit.do => ReBoardEditController
		=> /reBoard/detail.do 로 redirect*/
		//1.
		logger.info("글 수정 처리, 파라미터 reBoardVo={},oldFileName={}", reBoardVo,oldFileName);
		logger.info("oldFileSize={},oldOriginFileName={}", oldFileSize,oldOriginFileName);
		
		//파일 업로드
		List<Map<String,Object>> fileList = reBoardService.fileupload(request);
		//새로 파일을 업로드하는 경우
		if(fileList!=null || !fileList.isEmpty()){
			String fileName="", originalFileName=""; long fileSize=0;
			for(Map<String, Object> fileMap: fileList){
				 fileName = (String) fileMap.get("fileName");
				 originalFileName = (String) fileMap.get("originalFileName");
				 fileSize = (Long) fileMap.get("fileSize");				 
			}
			reBoardVo.setFileName(fileName);
			reBoardVo.setFileSize(fileSize);
			reBoardVo.setOriginalFileName(originalFileName);
			
			//기존 파일이 존재하면, 기존 파일 삭제
			String upPath = reBoardService.getUploadPath(request);
			File oldFile = new File(upPath, oldFileName); 
			if(oldFile.exists()){
				boolean bool =oldFile.delete();
				logger.info("기존 파일 삭제 여부={}", bool);
			}
			
		}else{
			//업로드하지 않는 경우
			//기존 파일 정보를 다시 셋팅해준다
			reBoardVo.setFileName(oldFileName);
			reBoardVo.setFileSize(oldFileSize);
			reBoardVo.setOriginalFileName(oldOriginFileName);
		}
		
		logger.info("업로드 처리 후 reBoardVo={}", reBoardVo);
		
		//2.
		String msg="", url="/reBoard/edit.do?no="+reBoardVo.getNo();
		if(reBoardService.checkPwd(reBoardVo.getNo(), reBoardVo.getPwd())){
			//비밀번호 일치
			int cnt = reBoardService.updateReBoard(reBoardVo);
			logger.info("글수정 결과, cnt={}", cnt);
			
			if(cnt>0){
				msg="글 수정 성공";
				url="/reBoard/detail.do?no="+reBoardVo.getNo();
			}else{
				msg="글수정 실패";
			}
		}else{
			//비밀번호 불일치
			msg="비밀번호가 일치하지 않습니다";
		}
		
		//3.
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/reBoard/delete.do", method=RequestMethod.GET)
	public String delete_get(
		@RequestParam(defaultValue="0") int no, Model model){
		/*8. 글 삭제 화면 보여주기 - get
		/reBoard/delete.do => ReBoardDeleteController
		=> /reBoard/delete.jsp*/

		//=> http://localhost:9090/springherb/reBoard/delete.do?no=250
		
		//1.
		logger.info("글삭제 화면 보여주기, 파라미터 no={}",no);
		
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		//2.		
		//3.
		return "reBoard/delete";		
	}
	
	@RequestMapping(value="/reBoard/delete.do", method=RequestMethod.POST)
	public String delete_post(@RequestParam(defaultValue="0") int no, 
			@RequestParam String pwd, @RequestParam String fileName,HttpServletRequest request, Model model){
		/*9. 글 삭제 처리 -post
		/reBoard/delete.do => ReBoardDeleteController
		=> /reBoard/list.do 로 redirect*/
		//1.
		logger.info("글삭제 처리 파라미터 no={}, pwd={}",no,pwd);
				
		//2.
		String viewPage="";
		if(reBoardService.checkPwd(no, pwd)){
			//비밀번호 일치
			ReBoardVO ReBoardVo = reBoardService.selectByNo(no);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("no", ReBoardVo.getNo()+"");
			map.put("groupNo", ReBoardVo.getGroupNo()+"");
			map.put("step", ReBoardVo.getStep()+"");
			
			
			reBoardService.deleteReBoard(map);
			logger.info("글삭제시 파라미터 map={}", map);
			
			//파일이 첨부된 경우에는 파일도 삭제처리 한다
			if(fileName!=null && !fileName.isEmpty()){
				String upPath = reBoardService.getUploadPath(request);
				File file = new File(upPath,fileName);
				if(file.exists()){
					boolean bool =file.delete();
					logger.info("첨부 파일 삭제 여부={}", bool);
				}
			}
			
			viewPage="redirect:/reBoard/list.do"; 
		}else{
			//비밀번호 불일치
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다");
			model.addAttribute("url", "/reBoard/delete.do?no="+no + "&fileName=" + fileName);
			viewPage="common/message";
		}
		
		//3.
		return viewPage;
	}
	
	@RequestMapping("/reBoard/download.do")
	public ModelAndView download(@RequestParam(defaultValue="0") int no,
			@RequestParam String fileName,HttpServletRequest request){
		//상세보기 화면에서 첨부파일을 클릭하면 get 방식으로 이동함
		//[1] 파라미터인 no에 해당하는 다운로드 수 증가시키기
		//[2] 강제 다운로드 창을 띄우는 뷰로 보내기
		
		//http://localhost:9090/springherb/reBoard/download.do?no=4&fileName=hansim20160818123602260.jpg
		
		//1.
		logger.info("다운로드 수 증가, 파라미터 no={}, fileName={}",no,fileName);
		
		//2.
		int cnt = reBoardService.updateDownCount(no);
		logger.info("다운로드 수 증가 결과, cnt={}", cnt);
		
		//3.
		//다운로드할 파일정보를 이용해서 파일 객체를 만든 후 뷰에 넘긴다
		//ModelAndView(String viewName, Map map)
		Map<String, Object> map = new HashMap<String, Object>();
		String upPath = reBoardService.getUploadPath(request);
		
		File file = new File(upPath,fileName);
		map.put("myfile", file);
		
		ModelAndView mav = new ModelAndView("downloadView", map);
		
		return mav;
	}
	
	@RequestMapping(value="/reBoard/reply.do", method=RequestMethod.GET)
	public String reply_get(@RequestParam(defaultValue="0") int no, Model model){
		//답변달기 화면 보여주기
		//1.
		logger.info("답변달기 화면 보여주기, 파라미터 no={}",no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2.
		ReBoardVO vo = reBoardService.selectByNo(no);
		
		//3.
		model.addAttribute("reBoardVo", vo);
		
		return "reBoard/reply";
	}
	
	@RequestMapping(value="/reBoard/reply.do", method=RequestMethod.POST)
	public String reply_post(@ModelAttribute ReBoardVO reBoardVo, Model model){
		//답변달기 처리
		//1. 파라미터
		logger.info("답변달기 처리 파라미터 reBoardVo={}", reBoardVo);
		//2. db
		int cnt = reBoardService.insertReply(reBoardVo);
		logger.info("답변달기 처리 결과, cnt={}", cnt);
		
		//3. 결과저장, 뷰 페이지 리턴
		String msg="", url="";
		if(cnt>0){
			url="/reBoard/list.do";
		}else{
			msg="답변달기 실패";
			url="/reBoard/reply.do";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);
		
		return "common/message";
	}
}
