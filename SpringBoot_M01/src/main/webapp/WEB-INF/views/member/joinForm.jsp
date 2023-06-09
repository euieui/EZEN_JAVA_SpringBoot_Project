<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub01/sub_image.html" %>
<%@ include file="../include/sub01/sub_menu.html" %>



<article>
<h2>Join Us</h2>
<form id="join" action="join" method="post" name="formm">
	<fieldset>
		<legend>Basic Info</legend>
			<label>User ID</label><input type="text" name="id" size="12" value="${dto.id }">
			<input type="hidden" name="reid" value="${reid }">
			<input type="button" value="중복 체크" class="dup" onClick="idcheck()"><br>
			<label>Password</label><input type="password" name="pwd"><br>
			<label>Retype Password</label><input type="password" name="pwdCheck"><br>
			<label>Name</label><input type="text" name="name" value="${dto.name }"><br>
			<label>E-Mail</label><input type="text" name="email" value="${dto.email }"><br>
			<label>${message}</label>
	</fieldset>
	<fieldSet>
		<legend>Optional</legend>
		<label>Zip Code</label><input type="text" name="zip_num" size="10">
		<input type="button" value="주소 찾기" class="dup" onClick="post_zip()"><br>
		<label>Address</label><input type="text" name="addr1" size="50"><br>
		<label>&nbsp;</label><input type="text" name="addr2" size="25"><br>
		<label>Phone Number</label><input type="text" name="phone"><br>
	</fieldSet>
	<div class="clear"></div>
	<div id="buttons">
		<input type="submit" value="회원가입" class="submit" >
		<input type="reset" value="취소" class="cancel">
	</div>

</form>
</article>

<%@ include file="../include/headerfooter/footer.jsp" %>