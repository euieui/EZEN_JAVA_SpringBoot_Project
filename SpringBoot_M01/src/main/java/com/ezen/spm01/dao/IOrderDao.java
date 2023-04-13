package com.ezen.spm01.dao;

import java.util.ArrayList;


import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.CartVO;
import com.ezen.spm01.dto.OrderVO;

@Mapper
public interface IOrderDao {

	void InsertOrders(String id);

	int LookupMaxOseq();

	void insertOrderDetail(CartVO cvo, int oseq);

	void deleteCart(Integer cseq);

	ArrayList<OrderVO> listOrderByOseq(int oseq);

	ArrayList<Integer> selectOseqOrderIng(String id);

	ArrayList<Integer> oseqListAll(String id);

	void InsertOders(String id);

}
