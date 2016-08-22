package com.herb.app.reboard.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.herb.app.common.SearchVO;

@Repository
public class ReBoardDAOMybatis extends SqlSessionDaoSupport 
implements ReBoardDAO{
	private static final Logger logger
		=LoggerFactory.getLogger(ReBoardDAOMybatis.class);
	
	private String namespace="com.mybatis.mapper.oracle.reBoard"; 
	
	public ReBoardDAOMybatis(){
		logger.info("생성자 : ReBoardDAOMybatis()");
	}
	
	public int insertReBoard(ReBoardVO vo){
		//reBoard 테이블에 insert
		int cnt
		=getSqlSession().insert(namespace+".insertReBoard", vo);
		
		return cnt;
	}
		
	public List<ReBoardVO> selectAll(SearchVO searchVo){
		//전체 글목록 조회, 검색
		/*select * from reBoard
		where name like '%길동%';
	
		select * from reBoard
		where title like '%안녕%';
	
		select * from reBoard
		where content like '%내용%';*/
		
		/*
		페이징처리, 검색 관련 쿼리문
		
		--1페이지
		select *
		from
		    (select * from reBoard order by no desc)
		where  rownum>0 and rownum<=0+5;
	
		--2페이지 - 원하는 결과가 안 나옴
		select *
		from
		    (select * from reBoard order by no desc)
		where  rownum>5 and rownum<=5+5;
	
		--2페이지 - inline뷰를 한 번 더 사용
		select *
		from
		(
		    select rownum as RNUM, LIST_ALL.*
		    from
		        (select * from reBoard order by no desc) LIST_ALL    
		) 
		where  RNUM>5 and RNUM<=5+5;
	
		--3페이지
		select *
		from
		(
		    select rownum as RNUM, LIST_ALL.*
		    from
		        (select * from reBoard order by no desc) LIST_ALL    
		) 
		where  RNUM>10 and RNUM<=10+5;
	
		--검색 추가
		select *
		from
		(
		    select rownum as RNUM, LIST_ALL.*
		    from
		        (select * from reBoard 
		        where title like '%안녕%'
		        order by no desc) LIST_ALL    
		) 
		where  RNUM>10 and RNUM<=10+5;
	*/
	
		
		List<ReBoardVO> alist
		=getSqlSession().selectList(namespace+".selectAll",
				searchVo);
		
		return alist;
	}
	
	@Override
	public int selectTotalCount(SearchVO searchVo) {
		return getSqlSession().selectOne(namespace
				+".selectTotalCount", searchVo);
	}
	
	public int updateReadCount(int no){
		//조회수 1 증가시키는 메서드
		return getSqlSession().update(namespace
				+".updateReadCount",no);	
	}
	
	public ReBoardVO selectByNo(int no){
		ReBoardVO reBoardVo 
		=getSqlSession().selectOne(namespace+".selectByNo", no);
		
		return reBoardVo;
	}
	
	public int updateReBoard(ReBoardVO vo){
		//수정 처리
		return getSqlSession().update(namespace+".updateReBoard", vo);
	}
	
	public String selectPwd(int no){
		//no에 해당하는 비밀번호가 일치하는지 체크하는 메서드
		String dbPwd = getSqlSession().selectOne(namespace+".selectPwd",	no);
		return dbPwd;
	}
	
	public void deleteReBoard(Map<String, String> map){
		getSqlSession().delete(namespace+".deleteReBoard", map);		
	}
	
	@Override
	public int updateDownCount(int no) {
		return getSqlSession().update(namespace+".updateDownCount",no);
	}

	@Override
	public int updateSortNo(ReBoardVO vo) {
		return getSqlSession().update(namespace+".updateSortNo", vo);
	}

	@Override
	public int insertReply(ReBoardVO vo) {
		//groupNo는 그대로, sortNo, step는 1증가시킨다
		vo.setSortNo(vo.getSortNo()+1);
		vo.setStep(vo.getStep()+1);
		return getSqlSession().insert(namespace+".insertReply", vo);
	}
	
	
	
	/*	
	public List<ReBoardVO> listMainNotice() throws SQLException{
		//최근 공지사항 6건만 조회하기
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<ReBoardVO> alist = new ArrayList<ReBoardVO>(10);
		try{
			//[1][2]
			conn=pool.getConnection();
			
			//[3]
			String sql="select *"
				+" from("
				+"    select * from reBoard order by no desc"
				+" )"
				+" where rownum<=6";
			ps=conn.prepareStatement(sql);
			
			//[4]
			rs =ps.executeQuery();
			while(rs.next()){
				int no=rs.getInt("no");
				String title=rs.getString("title");
				
				ReBoardVO vo = new ReBoardVO();
				vo.setNo(no);
				vo.setTitle(title);
				
				alist.add(vo);
			}
			System.out.println("최근공지사항 조회 결과 alist.size="
					+alist.size());
		}finally{
			pool.dbClose(rs, ps, conn);
		}
		
		return alist;
	}*/

}
