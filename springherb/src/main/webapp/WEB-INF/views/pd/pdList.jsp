<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.herb.app.pd.model.PdDTO"%>
<%@page import="java.util.List"%>
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
</head>
<body>
	<%
		//뷰페이지 - 결과 얻어오기
		/* List<PdDTO> alist 
			= (List<PdDTO>)request.getAttribute("alist");
	
		SimpleDateFormat sdf 
			= new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#,###");  
		*/
	%>
	<h1>상품 목록</h1>
	<table border="1" 
	  summary="상품목록을 보여주는 표로써 번호,상품명,가격,
		 등록일 정보를 보여줍니다">
		<caption>상품 목록</caption>
		<colgroup>
			<col style="width:20%">
			<col style="width:40%">
			<col style="width:20%">
			<col style="width:20%">
		</colgroup>
		<thead>
			<tr>
				<th scope="col">번호</th>
				<th scope="col">상품명</th>
				<th scope="col">가격</th>
				<th scope="col">등록일</th>
			</tr>
		</thead>
		<tbody>
			<%-- <%if(alist==null || alist.isEmpty()){ %> --%>
			<c:if test="${empty alist}">
				<tr>
					<td colspan="4" 
						style="text-align: center">
						해당 내역이 없습니다.
					</td>
				</tr>
			</c:if>	
			<%-- <%}else{ %> --%>
			<c:if test="${!empty alist }">
				<!-- 반복 시작 -->
				<%-- <%for(PdDTO dto : alist){ %> --%>
				<c:forEach var="dto" items="${alist }">
				<tr>
					<td>${dto.no}</td>
					<td>
						<a href
		="<c:url value='/pd/pdDetail.do?no=${dto.no}'/>">
							${dto.pdName}</a>
					</td>
					<td style="text-align: right">
						<fmt:formatNumber 
							value="${dto.price }"
							pattern="#,###" />
						</td>
					<td>
						<fmt:formatDate 
							value="${dto.regdate}"
							pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<!-- 반복 끝 -->
			<%-- <%
				}//for
			}//if %> --%>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<br><hr>
	<a href="<c:url value='/pd/pdWrite.do'/>">
		상품 등록</a>
</body>
</html>










