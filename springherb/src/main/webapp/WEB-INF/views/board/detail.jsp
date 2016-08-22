<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>자유게시판 상세보기 - 허브몰</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/mainstyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/clear.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/formLayout.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/mystyle.css'/>" />
<script type="text/javascript" src="<c:url value='/jquery/jquery-3.1.0.min.js'/>"></script>
<style type="text/css">
	body{
		padding:5px;
		margin:5px;
	 }
	.divForm {
		width: 500px;
		}
</style>  
</head>
<body>
	<h2>글 상세보기</h2>
	<div class="divForm">
		<div class="firstDiv">
			<span class="sp1">제목</span> 
			<span>${vo.title }</span>
		</div>
		<div>
			<span class="sp1">작성자</span> 
			<span>${vo.name }</span>
		</div>
		<div>
			<span class="sp1">등록일</span> 
			<span>${vo.regdate }</span>
		</div>
		<div>
			<span class="sp1">조회수</span> 
			<span>${vo.readcount }</span>
		</div>
		<div class="lastDiv">			
			<p class="content">${vo.content }</p>
		</div>
		<div class="center">
			<a href="<c:url value='/board/edit.do?no=${vo.no}'/>">수정</a> |
        	<a href="<c:url value='/board/delete.do?no=${vo.no}'/>">삭제</a> |
        	<a href="<c:url value='/board/list.do'/>">목록</a>			
		</div>
	</div>

	
</body>
</html>






