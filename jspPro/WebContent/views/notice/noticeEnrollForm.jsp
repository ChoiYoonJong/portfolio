<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.outer{
		width:800px;
		height:500px;
		background:black;
		color:white;
		margin:auto;
		margin-top:50px;
	}
	#enrollForm{border-top: 2px solid #FFFFFF;}
	#enrollForm>table{}
	#enrollForm>table input{
		width:100%;
		box-sizing:border-box;
	}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		
		<h2 align="center">공지사항 작성하기</h2>
		<p>*공지사항을 빠르고 정확하게 안내해드립니다.</p>
		
		<form id="enrollForm" action="<%= contextPath %>/insertNotice.do" method="post" >
			<table align="center">
				<tr>
					<td>제목</td>
					<td colspan="3"><input type="text" name="title" class="title"></td>
				</tr>
				
				<tr>
					<td>내용</td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea name="content" cols="60" rows="15" style="resize:none;" class="content"></textarea>
					</td>
				</tr>	
			</table>
			<br>
			
			<div class="btns" align="center">
				<button type="submit">등록하기</button>
				
			</div>
		</form>
	</div>
	<%@ include file="../common/footer.jsp" %>

</body>
</html>