package com.ezen.spm01.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spm01.dto.AddressVO;
import com.ezen.spm01.dto.MemberVO;

@Mapper
public interface IMemberDao {

	MemberVO getMember(String id);

	ArrayList<AddressVO> selectAddressByDong(String dong);

	void insertMember(MemberVO membervo);

	void updateMember(MemberVO membervo);

}
