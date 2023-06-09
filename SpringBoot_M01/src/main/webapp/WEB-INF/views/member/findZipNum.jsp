<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="/css/shopping.css">

<style type="text/css">
body{font-family: Verdana; background: #FDE8FF;}
#popup{padding: 0 10px;}
table#zipcode{border-collapse: collapse; border-top: 3px solid #6F0C78; border-bottom: 3px solid #6F0C78; 
	margin-top: 15px; width: 100%; font-size: 90%;}
table#zipcode th, table#zipcode td{text-align: center; border-bottom: 1px dotted #6F0C78;}
table th, td{padding: 10px;}
table#zipcode a{display: block; height: 20px; text-decoration: none; padding: 10px;}
table#zipcode a:hover{font-weight: bold;}
</style>

<script type="text/javascript">
	function result(zip_num,sido,gugun,dong){
		opener.document.formm.zip_num.value=zip_num;
		opener.document.formm.addr1.value=sido+" "+gugun+" "+dong;
		self.close();
	}
</script>
</head>
<body>

<div id="popup">
	<h1>우편번호검색</h1>
	<form method="get" name="formm" action="findZipNum">
		동 이름 : <input name="dong" type="text">
		<input type="submit" value="찾기" class="submit">
	</form><br><br>
	<table id="zipcode">
		<tr><th>우편번호</th><th>주소</th></tr>
		<c:forEach items="${addressList }" var="addressVO">
			<tr><td>${addressVO.zip_num }</td>
				<td><a href="#" onClick="result('${addressVO.zip_num}',
				'${addressVO.sido }', '${addressVO.gugun }','${addressVO.dong }')">
				${addressVO.sido } ${addressVO.gugun } ${addressVO.dong }</a></td></tr>
		</c:forEach>
	</table>
</div>

</body>
</html>