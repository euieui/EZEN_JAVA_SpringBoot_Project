package com.ezen.spm01.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.Paging;
import com.ezen.spm01.dto.ProductVO;

@Mapper
public interface IAdminDao {

	String workerCheck(String workId);

	int getAllCount(String tablename, String fieldname, String key);

	ArrayList<ProductVO> listProduct(Paging paging, String key);

	void insertProduct(ProductVO pvo);

	void updateProduct(ProductVO productvo);

	
	/*
	int getAllCount(String tableName, String fieldName, String key);

	List<ProductVO> listProduct(Paging paging, String key);

	String workerCheck(String workId);
	*/

}
