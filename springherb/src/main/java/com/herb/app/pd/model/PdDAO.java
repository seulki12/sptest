package com.herb.app.pd.model;



import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PdDAO extends SqlSessionDaoSupport{
	
	private final String NAMESPACE="com.mybatis.mapper.pd";
	
	public PdDAO(){
		System.out.println("생성자 호출 : PdDAO()");
	}
	
	public int insertPd(PdDTO dto){
		//상품 등록
		System.out.println("insertPd() 입력전, dto="+dto);
		
		int cnt=getSqlSession().insert(NAMESPACE+".pdInsert",
					dto);
			
		System.out.println("PdDAO-상품등록 결과, cnt="
				+cnt+", 입력값 dto="+dto);
		
		return cnt;
		
	}
	
	public List<PdDTO> selectAllPd(){
		//상품 전체 목록 조회
		List<PdDTO> alist 
			=getSqlSession().selectList(NAMESPACE
				+".pdSelectAll");
		
		System.out.println("상품전체 조회 결과 "
				+ "alist.size()="+alist.size());
		
		return alist;			
	}
	
	public PdDTO selectByNo(int no){
		//상품 상세보기 조회
		PdDTO pdDto 
		= getSqlSession().selectOne(NAMESPACE+".pdSelectByNo",
				no);
		System.out.println("상품조회 결과 pdDto="+pdDto);
		
		return pdDto;		
	}

	public int updatePd(PdDTO dto){
		//상품 수정
		int cnt 
		= getSqlSession().update(NAMESPACE+".pdUpdate", dto);
		
		System.out.println("상품수정 결과 cnt="+cnt
				+", 입력값 dto="+dto);
		
		return cnt;
	}
	
	public int deletePd(int no){
		//상품 삭제
		int cnt 
		= getSqlSession().delete(NAMESPACE+".pdDelete", no);
		
		System.out.println("상품삭제 결과 cnt="+cnt
			+", 입력값 no="+no);
		
		return cnt;		
	}
	
	
}//













