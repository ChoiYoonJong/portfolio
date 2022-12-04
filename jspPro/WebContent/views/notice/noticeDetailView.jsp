<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.uni.notice.model.vo.Notice" %>
<%
	Notice n = (Notice)request.getAttribute("notice");
%>



<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.outer{
		padding-top: 70px;
		padding-bottom: 300px;
		background:white;
	}
	
	.notice_title {

	font-weight : 700;

	font-size : 22pt;

	margin : 10pt;

}

.notice_info_box {

	color : #6B6B6B;

	margin : 10pt;

}

.notice_author {

	font-size : 10pt;

	margin-right : 10pt;

}

.notice_date {

	font-size : 10pt;

}

.notice_content {

	color : #444343;

	font-size : 12pt;

	margin : 10pt;

}
	
	.btns a{text-decoration:none; color:white;}
	
	
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		
		<h2 align="center">공지사항 상세보기</h2>
		

		<table id="detailArea" border="1">
			<tr>
				<td colspan="3" class="notice_title"><%= n.getNoticeTitle() %></td>
			</tr>
			<tr>
				<td id="notice_author"><%= n.getNoticeWriter() %></td>
				
				<td id="notice_date"><%= n.getCreateDate() %></td>
			</tr>
			
			<tr>
				<td colspan="4" id="notice_content">
					<p><%= n.getNoticeContent() %></p>
				</td>
			</tr>	
		</table>
		
		<br>
		
		<div class="btns" align="center">
		
			<a href="listNotice.do">목록으로</a> &nbsp;&nbsp;
				
			
			<% if(loginUser != null && loginUser.getUserId().equals("administrator")) { %>
			<a href="updateFormNotice.do?nno=<%=n.getNoticeNo()%>">수정하기</a> &nbsp;&nbsp;
			<a href="deleteNotice.do?nno=<%=n.getNoticeNo()%>">삭제하기</a>
		
			<% } %>
			
		</div>
	</div>
	<%@ include file="../common/footer.jsp" %>
</body>
</html>