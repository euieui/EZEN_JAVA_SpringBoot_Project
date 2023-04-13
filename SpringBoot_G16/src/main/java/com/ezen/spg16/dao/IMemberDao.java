package com.ezen.spg16.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.spg16.dto.MemberVO;

@Mapper
public interface IMemberDao {

	public MemberVO getMember(String id);

	public void insertMember(MemberVO membervo);

	public void updateMember(MemberVO membervo);
}
