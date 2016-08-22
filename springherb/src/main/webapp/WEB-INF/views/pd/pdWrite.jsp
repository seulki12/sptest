<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>상품 등록</h1>
	<form name="frm1" 
	action="<c:url value='/pd/pdWrite.do'/>" 
		method="post">
		<fieldset>
			<legend>상품 등록</legend>
			<div>
				<label for="pdName">상품명</label>
				<input type="text" name="pdName" 
					id="pdName">
			</div>
			<div>
				<label for="price">가격</label>
				<input type="text" name="price" 
					id="price">
			</div>
			<div>
				<input type="submit" value="등록">
				<input type="reset" value="취소">				
			</div>
		</fieldset>
	</form>
	<br>
	<hr>
	<a href="<c:url value='/pd/pdList.do'/>">
		상품목록</a>
</body>
</html>










