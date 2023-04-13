package com.ezen.spm01.service;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.spm01.dao.IAdminDao;
import com.ezen.spm01.dto.Paging;
import com.ezen.spm01.dto.ProductVO;

@Service
public class AdminService {
	@Autowired
	IAdminDao adao;
	
	/*
	public int workerCheck(String workId, String workPwd) {
		int result = 0;
		
		String getPwd = adao.workerCheck(workId);
		if (getPwd == null)
			result = -1;
		else if (workPwd.equals(getPwd))
			result = 1;
		else
			result = 0;
		return result;
	}
	

	public int getAllCount(String tableName, String fieldName, String key) {
		return adao.getAllCount(tableName, fieldName, key);
	}

	public List<ProductVO> listProduct(Paging paging, String key) { 
		return adao.listProduct(paging, key);
	}
	*/

	public int workerCheck(String workId, String workPwd) {
		String pwd = adao.workerCheck(workId);
		int result =0;
		if(workPwd == null) result = -1;
		else if(workPwd.equals(pwd)) result = 1;
		else if(!workPwd.equals(pwd)) result =0;
		return result;
	}

	public int getAllCount(String tablename, String fieldname, String key) {
		return adao.getAllCount(tablename, fieldname, key);
	}

	public ArrayList<ProductVO> listProduct(Paging paging, String key) {
		return adao.listProduct(paging, key);
	}

	public void insertProduct(ProductVO pvo) {
		adao.insertProduct(pvo);
		
	}

	public void updateProduct(ProductVO productvo) {
		adao.updateProduct(productvo);
		
	}
}
