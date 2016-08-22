<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/mainstyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/clear.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/formLayout.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/mystyle.css'/>" />
<title>자유게시판 글쓰기 - 허브몰</title>
<script src="../ckeditor/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery-3.1.0.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
	});
	
	window.onload=function(){
		CKEDITOR.replace('content');	
		
		$("#frmWrite").submit(function(event){
			if($("#title").val()==""){
				alert("제목을 입력하세요");
				$("#title").focus();
				event.preventDefault();
			}else if($("#name").val().length<1){
				alert("이름을 입력하세요");
				$("#name").focus();
				event.preventDefault();
			}else if($("#pwd").val().length<1){
				alert("비밀번호를 입력하세요");
				$("pwd").focus();
				event.preventDefault();
			}
		})
	};
</script>

</head>
<body>
<div class="divForm">
<form name="frmWrite" method="post" id="frmWrite" action="<c:url value='/board/write.do'/>">
 <fieldset>
	<legend>글쓰기</legend>
        <div class="firstDiv">
            <label for="title">제목</label>
            <input type="text" id="title" name="title"  />
        </div>
        <div>
            <label for="name">작성자</label>
            <input type="text" id="name" name="name" />
        </div>
        <div>
            <label for="pwd">비밀번호</label>
            <input type="password" id="pwd" name="pwd" />
        </div>
        <div>
            <label for="email">이메일</label>
            <input type="text" id="email" name="email" />
        </div>
        <div>  
        	<label for="content">내용</label>
        </div>
        <div>	        
 			<textarea id="content" name="content" 
 			rows="12" cols="40"></textarea>
<!--  			<textarea id="content" name="content" 
 			rows="12" cols="40" class="ckeditor"></textarea> -->
        </div>
        <div class="center">
            <input type = "submit" value="등록"/>
            <input type = "Button" value="글목록" onclick="location.href='<c:url value="/board/list.do"/>';" />         
        </div>
    </fieldset>
</form>
</div>   

              
</body>
</html>



