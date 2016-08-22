package com.herb.app.reboard.model;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.herb.app.common.SearchVO;

public interface ReBoardService {
	public int insertReBoard(ReBoardVO vo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	public int selectTotalCount(SearchVO searchVo);
	public int updateReadCount(int no);
	public ReBoardVO selectByNo(int no);
	public boolean checkPwd(int no, String pwd);
	public int updateReBoard(ReBoardVO vo);
	public void deleteReBoard(Map<String, String> map);
	
	public List<Map<String, Object>> fileupload(HttpServletRequest request);
	public int updateDownCount(int no);
	
	public String getUniqueFileName(String ofileName);
	public String getCurrentTime();
	public String getUploadPath(HttpServletRequest request);
	public int insertReply(ReBoardVO vo);
}
