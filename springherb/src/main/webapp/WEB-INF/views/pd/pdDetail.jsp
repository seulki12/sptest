<%@page import="java.text.DecimalFormat"%>
<%@page import="com.herb.app.pd.model.PdDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" 
	uri="http://java.sun.com/jsp/jstl/fmt" %>
	    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function del(no){
		if(confirm("삭제하시겠습니까?")){
			location.href
		="<c:url value='/pd/pdDelete.do?no="+no +"' />";
		}
	}
</script>
</head>
<body>
<%
	/* PdDTO dto = (PdDTO)request.getAttribute("pdDto");
	
	DecimalFormat df = new DecimalFormat("#,###");	 */
%>
<h1>상품 상세 보기</h1>
<p>번호 : ${pdDto.no}</p>
<p>상품명 : ${pdDto.pdName}</p>
<p>가격 :  <fmt:formatNumber value="${pdDto.price}"
				pattern="#,###" />원</p>
<p>등록일 : ${pdDto.regdate}</p>
<br><hr>
<a href="<c:url value='/pd/pdList.do'/>">목록</a>
<a href
="<c:url value='/pd/pdEdit.do?no=${pdDto.no}'/>" >
	수정</a>
<a href="#" onclick="del(${pdDto.no})">삭제</a>

</body>
</html>








