package com.herb.app.reboard.model;

import java.util.List;
import java.util.Map;

import com.herb.app.common.SearchVO;

public interface ReBoardDAO {
	public int insertReBoard(ReBoardVO vo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	public int selectTotalCount(SearchVO searchVo);
	public int updateReadCount(int no);
	public ReBoardVO selectByNo(int no);
	public String selectPwd(int no);
	public int updateReBoard(ReBoardVO vo);
	public void deleteReBoard(Map<String, String> map);
	public int updateDownCount(int no);
	public int updateSortNo(ReBoardVO vo);
	public int insertReply(ReBoardVO vo);
}
