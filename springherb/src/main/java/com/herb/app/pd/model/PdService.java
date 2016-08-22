package com.herb.app.pd.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PdService {
	@Autowired
	private PdDAO pdDao;
	
	//생성자
	public PdService(){
		System.out.println("생성자 호출: PdService()");
	}
	
	@Transactional
	public int insertPd(PdDTO dto){
		return pdDao.insertPd(dto);
	}
	
	public List<PdDTO> selectAllPd(){
		return pdDao.selectAllPd();
	}
	
	
	public PdDTO selectByNo(int no){
		return pdDao.selectByNo(no);
	}
	
	
	public int updatePd(PdDTO dto){
		return pdDao.updatePd(dto);
	}
		
	public int deletePd(int no){
		return pdDao.deletePd(no);
	}

	
}

