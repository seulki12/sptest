package com.herb.app.reboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component("downloadView")
public class ReBoardDownloadView extends AbstractView{
	
	public ReBoardDownloadView(){
		setContentType("application/octet-stream");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(ReBoardDownloadView.class);
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//강제 다운로드 창 띄우기
		//map에 저장된 파일객체 얻어오기
		File myfile = (File) model.get("myfile");
		logger.info("myfile="+myfile);
		
		if(myfile==null || !myfile.exists() || !myfile.canRead()){
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('파일이 존재하지 않거나 손상되었습니다');");
			out.println("history.back();");
			out.println("</script>");
			
			return;
		}
		
		//파일명 읽어오기
		String fileName = new String(myfile.getName().getBytes("euc-kr"),"8859_1");
		
		response.setContentType(getContentType());
		response.setHeader("Content-disposition","attachment;filename="+fileName);
		response.setContentLength((int)myfile.length());
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		//파일 내려보내기
		FileInputStream fis = null;
		OutputStream os = response.getOutputStream();
		try{
			fis = new FileInputStream(myfile);
			FileCopyUtils.copy(fis, os);
		}finally{
			if(fis!=null)fis.close();
			os.flush();
		}
		
	}

}
