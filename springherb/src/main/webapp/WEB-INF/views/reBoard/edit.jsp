<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>자료실 글 수정 - 허브몰</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/mainstyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/clear.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/formLayout.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/mystyle.css'/>" />
<script type="text/javascript" src="<c:url value='/jquery/jquery-3.1.0.min.js' />"></script>
<script src="<c:url value='/ckeditor/ckeditor.js'/>" type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function(){
		CKEDITOR.replace('content');	
	
		$("#frmEdit").submit(function(event){
			if($("#title").val().length<1){
				alert("제목을 입력하세요");
				$("#title").focus();
				event.preventDefault();
			}else if($("#name").val().length<1){
				alert("이름을 입력하세요");
				$("#name").focus();
				event.preventDefault();			
			}else if($("#pwd").val().length<1){
				alert("비밀번호를 입력하세요");
				$("#pwd").focus();
				event.preventDefault();
			}
		});
	});
	

	
</script>

</head>
<body>
<div class="divForm">
<form name="frmEdit" id="frmEdit" method="post" enctype="multipart/form-data"
	action="<c:url value='/reBoard/edit.do'/>" > 
	<!-- update하기 위해 필요한 no를 hidden필드에 넣는다 -->
	<input type="hidden" name="no" value="${reBoardVo.no}">
	<input type="hidden" name="oldFileName" value="${reBoardVo.fileName }">
	<input type="hidden" name="oldOriginFileName" value="${reBoardVo.originalFileName }">
	<input type="hidden" name="oldFileSize" value="${reBoardVo.fileSize }">
	
    <fieldset>
	<legend>글수정</legend>
        <div class="firstDiv">
            <label for="title">제목</label>
            <input type="text" id="title" name="title"  
            	value="${reBoardVo.title}" />
        </div>
        <div>
            <label for="name">작성자</label>
            <input type="text" id="name" name="name" 
            	value="${reBoardVo.name}"/>
        </div>
        <div>
            <label for="pwd">비밀번호</label>
            <input type="password" id="pwd" name="pwd" />
        </div>
        <div>
            <label for="email">이메일</label>
            <input type="text" id="email" name="email" 
            	value="${reBoardVo.email}" />
        </div>
        <div>
            <label for="upfile">파일 첨부</label>
            <input type="file" id="upfile" name="upfile"/>
            <c:if test="${!empty reBoardVo.fileName }">
            	<p style="color:green;padding:5px 0 px 60px;">
            		※ 첨부파일을 새로 지정할 경우 
            		기존파일 <img src="<c:url value='/images/file.gif'/>" alt="파일이미지">
            		${reBoardVo.originalFileName }은 삭제됩니다.
            	</p>
            </c:if>
        </div>
        <div>  
        	<label for="content">내용</label> 
        </div>
        <div>	       
 			<textarea id="content" name="content" 
 			rows="12" cols="40">${reBoardVo.content}</textarea>
        </div>
        <div class="center">
            <input type = "submit" value="수정"/>
            <input type = "Button" value="글목록" 
            onclick
	="location.href='<c:url value="/reBoard/list.do"/>'" />         
        </div>
	</fieldset>
</form>    
</div>

</body>
</html>