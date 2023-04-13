package com.ezen.spm01.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MemberVO {
	
	
	//<label>User ID</label><input type="text" name="id" value="${dto.id }" readonly><br>
	// <label>Password</label><input type="password" name="pwd"><br>
	// <label>Retype Password</label><input type="password" name="pwdCheck"><br>
	// <label>Name</label><input type="text" name="name" value="${dto.name }"><br>
	// <label>E-Mail</label><input type="text" name="email" value="${dto.email }"><br>
	// 여기서 잘 보면 pwd 는 Null이고 id name email 은 empty야 value가 들어가 있어서 !
	
	
	@NotEmpty(message="id를 입력하세요")
	@NotNull(message="id를 입력하세요")
	private String id;
	@NotEmpty(message="비밀번호를 입력하세요")
	@NotNull(message="비밀번호를 입력하세요")
	private String pwd;
	@NotEmpty(message="이름을 입력하세요")
	@NotNull(message="이름을 입력하세요")
	private String name;
	@NotEmpty(message="이메일을 입력하세요")
	@NotNull(message="이메일을 입력하세요")
	private String email;
	private String zip_num;
	private String address;
	private String phone;
	private String useyn;
	private Timestamp indate;
	
	
}
