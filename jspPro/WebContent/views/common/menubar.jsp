<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.uni.member.model.vo.Member" %>
<%
	Member loginUser = (Member)session.getAttribute("loginUser");

	String msg = (String)session.getAttribute("msg");
	String contextPath = request.getContextPath();
%>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
	body{
		background:url('<%=request.getContextPath() %>/resources/images/2.jpg') no-repeat; 
		background-position: 50% 50%;
		background-repeat: no-repeat;
		background-size: 50% 50%;
		background-color: black;
	}
	
	
	#loginForm, #userInfo{float:right;}
	.btns button{border: 1px solid skyblue;
            background-color: rgba(0,0,0,0);
            color: skyblue;
            padding: 5px;
				  }
	
	
	
	#enrollBtn, #mypageBtn{
			border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;    
            margin-left:-3px}
	#loginBtn, #logoutBtn{ 
			border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
            margin-right:-4px;}
	#userInfo a{text-decoration:none;color:white;}
	
	.btns button:hover{border: 1px solid skyblue;
            background-color: skyblue;
            color: purple;
            padding: 5px;
				  }
	
	/* 메뉴영역 관련 스타일*/
	.navWrap{background-color:black; width:100%; height:50px}
	.navWrap>.nav{width:600px;margin:auto;}
	.menu{text-align:center;color:white;font-weight:bold;width:150px;height:50px;display:table-cell;font-size:20px;vertical-align:middle;}
	.menu:hover{background-color:rgb(250, 142, 174); text-shadow:5px 5px 5px rgb(0,0,0);
	}
	

	.logo{
		float: lfet;
	}
	.logo img{
		height: 60px;
	}
	
	

</style>
<script>
	function loginValidate(){
		if($("#userId").val().trim().length === 0){
			alert("아이디를 입력하세요");
			$("#userId").focus();
			return false;
		}
		if($("#userPwd").val().trim().length === 0){
			alert("비밀번호를 입력하세요");
			$("#userPwd").focus();
			return false;
		}
		
		return true;
	}
	
	
	
</script>
</head>
<body>
	<h1 class="logo">
		<img src="resources/images/logo.png" alt="로고" >
	</h1>
	<h1 align="center" style="color:white;">이세계 아이돌</h1>
	<div class="loginArea">
	<% if( loginUser == null) { %>
	<form id = "loginForm" action="<%=request.getContextPath()%>/loginMember.do" method="post" onsubmit="return loginValidate();">
			<table>
				<tr>
					<th><label for = "userId" style="color:white;">아이디</label></th>
					<td><input id="userId" type="text" name="userId"></td>
				</tr>
				<tr>
					<th><label for = "userPwd" style="color:white;">비밀번호</label></th>
					<td><input type="password" id="userPwd" type="text" name="userPwd"></td>
				</tr>
			</table>
			<div class ="btns" align="center">
				
				<button id = "loginBtn" type="submit">로그인</button>
			    <button id = "enrollBtn" type="button" onclick="enrollPage();">회원가입</button>
			</div>
		
		
		
		</form>
	<%} else{ %>
			<div id = "userInfo">
				<b style = "color:white;"><%=loginUser.getUserName() %> 님 </b> 의 방문을 환영합니다.
				<br><br>
				<div class ="btns" align="center">
					<a href = "<%=request.getContextPath() %>/mypageMember.do">마이페이지</a>
					<a href = "<%=request.getContextPath() %>/logoutMember.do">로그아웃</a>
				</div>
			
			
			</div>	
	<%} %>
	</div>
	
	<script type="text/javascript">
		function enrollPage(){
			location.href ="<%=request.getContextPath()%>/enrollFormMember.do";
		}
	
	
	
	</script>
	
	
	
	<br clear="both">
		
		<div class="navWrap">
			<div class="nav">
				<div class="menu" onclick="goMain();">HOME</div>
				<div class="menu" onclick="goAbout();">ABOUT</div>
				<div class="menu" onclick="goNotice();">공지사항</div>
				<div class="menu" onclick="goBoard();">게시판</div>
				<div class="menu" onclick="goThumbnail();">사진게시판</div>
				
			</div>
			
		</div>
		
		
		
		
		
		<script>
		function goMain(){
			location.href="<%= request.getContextPath()%>";
		}
		
		function goAbout(){
			location.href="<%= request.getContextPath()%>/about.do";
		}
		
		function goNotice(){
			location.href="<%= request.getContextPath()%>/listNotice.do";
		}
		
		
		
		
		</script>
</body>
</html>