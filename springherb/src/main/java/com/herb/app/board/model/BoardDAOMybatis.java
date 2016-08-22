package com.herb.app.board.model;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.herb.app.common.SearchVO;

@Repository
public class BoardDAOMybatis extends SqlSessionDaoSupport 
	implements BoardDAO{
	private static final Logger logger
		=LoggerFactory.getLogger(BoardDAOMybatis.class);
	
	private String namespace="com.mybatis.mapper.oracle.board"; 
	
	public BoardDAOMybatis(){
		logger.info("생성자 : BoardDAOMybatis()");
	}
	
	public int insertBoard(BoardVO vo){
		//board 테이블에 insert
		int cnt
		=getSqlSession().insert(namespace+".insertBoard", vo);
		
		return cnt;
	}
		
	public List<BoardVO> selectAll(SearchVO searchVo){
		//전체 글목록 조회, 검색
		/*select * from board
		where name like '%길동%';

		select * from board
		where title like '%안녕%';

		select * from board
		where content like '%내용%';*/
		
		/*
		페이징처리, 검색 관련 쿼리문
		
		--1페이지
		select *
		from
		    (select * from board order by no desc)
		where  rownum>0 and rownum<=0+5;

		--2페이지 - 원하는 결과가 안 나옴
		select *
		from
		    (select * from board order by no desc)
		where  rownum>5 and rownum<=5+5;

		--2페이지 - inline뷰를 한 번 더 사용
		select *
		from
		(
		    select rownum as RNUM, LIST_ALL.*
		    from
		        (select * from board order by no desc) LIST_ALL    
		) 
		where  RNUM>5 and RNUM<=5+5;

		--3페이지
		select *
		from
		(
		    select rownum as RNUM, LIST_ALL.*
		    from
		        (select * from board order by no desc) LIST_ALL    
		) 
		where  RNUM>10 and RNUM<=10+5;

		--검색 추가
		select *
		from
		(
		    select rownum as RNUM, LIST_ALL.*
		    from
		        (select * from board 
		        where title like '%안녕%'
		        order by no desc) LIST_ALL    
		) 
		where  RNUM>10 and RNUM<=10+5;
*/

		
		List<BoardVO> alist
		=getSqlSession().selectList(namespace+".selectAll", searchVo);
		
		return alist;
	}
	
	@Override
	public int selectTotalCount(SearchVO searchVo){
		return getSqlSession().selectOne(namespace+".selectTotalCount", searchVo);
	}
	public int updateReadCount(int no) {
		int cnt = getSqlSession().update(namespace+".updateReadCount", no);
		
		return cnt;	
	}
	
	
	public BoardVO selectByNo(int no) {
		BoardVO vo = getSqlSession().selectOne(namespace+".selectByNo", no);
		
		return vo;
	}
	
	
	
	public int updateBoard(BoardVO vo){
		return getSqlSession().update(namespace+".updateBoard", vo);
	}
	
	public String selectPwd(int no) {
		String dbPwd =  getSqlSession().selectOne(namespace+".checkPwd", no);
		
		return dbPwd;
	}
	
	public int deleteBoard(int no){
		return getSqlSession().delete(namespace+".deleteBoard", no);
	}
/*	
	
	public List<BoardVO> listMainNotice() throws SQLException{
		//최근 공지사항 6건만 조회하기
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<BoardVO> alist = new ArrayList<BoardVO>(10);
		try{
			//[1][2]
			conn=pool.getConnection();
			
			//[3]
			String sql="select *"
				+" from("
				+"    select * from board order by no desc"
				+" )"
				+" where rownum<=6";
			ps=conn.prepareStatement(sql);
			
			//[4]
			rs =ps.executeQuery();
			while(rs.next()){
				int no=rs.getInt("no");
				String title=rs.getString("title");
				
				BoardVO vo = new BoardVO();
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
