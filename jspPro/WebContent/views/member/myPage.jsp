<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Member m = (Member)request.getAttribute("loginUser");

	String userId = m.getUserId();
	String userPwd = m.getUserPwd();
	String userName = m.getUserName();
	String phone = m.getPhone() !=null? m.getPhone() : "";
	String email = m.getEmail() !=null? m.getEmail() : "";
	String address = m.getAddress() !=null? m.getAddress() : "";
	String originPwd = (String)session.getAttribute("originPwd");
	
	String[] checkedInterest = new String[6];
	if(m.getInterest() !=null){
		String[] interests = m.getInterest().split(",");
		
		for(int i = 0; i < interests.length; i++){
			switch(interests[i]){
			case "아이네" :checkedInterest[0] = "checked";break;
			case "징버거" :checkedInterest[1] = "checked";break;
			case "릴파" :checkedInterest[2] = "checked";break;
			case "주르르" :checkedInterest[3] = "checked";break;
			case "고세구" :checkedInterest[4] = "checked";break;
			case "비챤" :checkedInterest[5] = "checked";break;
			
			}
		}
		
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	
	<style>
	
	*{
		 background-color: rgb(250, 142, 174);
    	font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
	}
	
.outer{
    width: 800px;
    height: 1000px;
    margin: auto;
    border-radius: 10px;
}

h1{
    text-align: center;
    padding-top: 15px;
    
}
h4{
    text-align: center;
}

form{
    width: 900px;

    margin: auto;
}

form input[type="checkbox"]{
    width: 20px;
    height: 20px;
    margin-top: 10px;
}


	.outer{
		background:black;
		width:1000px;
		height:500px;
		margin-top:50px;
		margin-left:auto;
		margin-right:auto;
		color:white;
	}
	#updateForm{border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
            margin-right:-4px;}
	#updateForm td:nth-child(1){text-align:right;}
	#updateForm input{margin:3px;}
	
	#joinBtn{border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
            margin-right:-4px;}
	#goMain{border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
            margin-right:-4px;}
</style>
</head>
<body>
<%@ include file="../common/menubar.jsp" %>

	<div class="outer">
		<br>
		
		<h1><img src="resources/images/isedol.png"  width=158px" alt="" onclick="location.href='${ pageContext.servletContext.contextPath }';"></h1>
		<h2 align="center">마이페이지</h2>
		<input type="text" id="originPwd" name ="originPwd" value="<%=originPwd %>" readonly>
		<form id="updateForm" action="<%=request.getContextPath() %>/updateMember.do" method="post">
			<table>
				<tr>
					<td width="200px">* 아이디</td>
					<td><input type="text" maxlength="13" name="userId" value = "<%= userId %>" readonly></td>
					
				</tr>
				
				<tr>
					<td>* 이름</td>
					<td><input type="text" maxlength="5" name="userName" value = "<%= userName %>" required></td>
					<td></td>
				</tr>
				<tr>
					<td>연락처</td>
					<td><input type="tel" maxlength="11" name="phone" value = "<%= phone %>" placeholder="(-없이)01012345678"></td>
					<td></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email" value = "<%= email %>"></td>
					<td></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="address" value = "<%= address %>" ></td>
					<td></td>
				</tr>
				<tr>
					<td>좋아하는 멤버</td>
					<td>
						<input type="checkbox" id="ine" name="interest" value="아이네" <%= checkedInterest[0] %>>
						<label for="ine">아이네</label>
						
						<input type="checkbox" id="jingburger" name="interest" value="징버거" <%= checkedInterest[1] %>>
						<label for="jingburger">징버거</label>
						
						<input type="checkbox" id="lilpa" name="interest" value="릴파" <%= checkedInterest[2] %>>
						<label for="lilpa">릴파</label>
						
						<input type="checkbox" id="jururu" name="interest" value="주르르" <%= checkedInterest[3] %>>
						<label for="jururu">주르르</label>
						
						<input type="checkbox" id="gosegu" name="interest" value="고세구" <%= checkedInterest[4] %>>
						<label for="gosegu">고세구</label>
						
						<input type="checkbox" id="vichan" name="interest" value="비챤" <%= checkedInterest[5] %>>
						<label for="vichan">비챤</label>
					</td>
					<td></td>
				</tr>
			</table>
			<br>
			
			<div class="btns" align="center">
				<button type="submit" id="updateBtn">수정하기</button>
				
				<button type="button" id = "pwdUpdateBtn" onclick="updatePwd();">비밀번호 변경</button>
				<button type="button" id = "deleteBtn" onclick="deleteMember();">탈퇴하기</button>
				
			</div>
		</form>
	</div>
	<script>
		function updatePwd(){
			window.open("<%= request.getContextPath()%>/updatePwdForm.do","비밀번호 변경창", "width=500, height=300")
		}
	
		function deleteMember(){
			var pwd = prompt("현재 비밀번호를 입력해주세요")
			var op = $("#originPwd").val();
			
			if(op === pwd){
			
				var val = confirm("정말로 탈퇴하시겠습니까?");
				if(val){
					$("#updateForm").attr("action","<%=request.getContextPath()%>/deleteMember.do");
					$("#updateForm").submit();
				}else{
					
					alert("취소하였습니다.");
				}
			}
		}
			
	</script>
	<%@ include file="../common/footer.jsp" %>
	
</body>
</html>