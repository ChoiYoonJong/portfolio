<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
    width: 700px;

    margin: auto;
}
form label{
    display: flex;
    margin-top: 20px;
    font-size: 18px;
}

form input{
    width: 100%;
    padding: 0 5px;
    height: 40px;
    font-size: 16px;
    border: none;
    background: none;
    outline: none;
}

input::placeholder {color:black;}

input{background:white;}

form input[type="text"]{
    border-bottom: 1px solid #999;
}
form input[type="id"]{
    border-bottom: 1px solid #999;
    margin-bottom: 10px;
}
form input[type="password"]{
    border-bottom: 1px solid #999;
}
form input[type="email"]{
    border-bottom: 1px solid #999;
}
form input[type="tel"]{
    border-bottom: 1px solid #999;
}
form input[type="date"]{
    border-bottom: 1px solid #999;
}

form input[type="text"]:focus{
    border-bottom: 3px solid white;
}
form input[type="id"]:focus{
    border-bottom: 3px solid white;
}
form input[type="password"]:focus{
    border-bottom: 3px solid white;
}
form input[type="email"]:focus{
    border-bottom: 3px solid white;
}
form input[type="tel"]:focus{
    border-bottom: 3px solid white;
}
form input[type="date"]:focus{
    border-bottom: 3px solid white;
}


form input[type="checkbox"]{
    width: 20px;
    height: 20px;
    margin-top: 10px;
}



#enrollForm td:nth-child(1){text-align:right;}
	#enrollForm input{margin:3px;}
	
	#joinBtn{border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;    
            margin-left:-3px}}
            
	#goMain{border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
            margin-right:-4px;}
</style>
    
</style>
</head>
<body>


	

	<div class="outer">
		<br>
		<div class="loginbox">
		
		<h1><img src="resources/images/isedol.png"  width=158px" alt="" onclick="location.href='${ pageContext.servletContext.contextPath }';"></h1>
		<h1>회원가입</h1>
        <h4>아래 정보를 기입해주세요</h4>
		<form id="enrollForm" action="<%=request.getContextPath() %>/insertMember.do" method="post" onsubmit="return joinValidate();">
			<table>
				<tr>
					<td width="200px" >* 아이디 :</td>
					<td><input type="text" maxlength="13" name="userId" placeholder="아이디를 입력하세요" required></td>
					
				</tr>
				<tr>
					<td>* 비밀번호 :</td>
					<td><input type="password" maxlength="15" name="userPwd" placeholder="비밀번호를 입력하세요" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* 비밀번호 확인 :</td>
					<td><input type="password" maxlength="15" name="checkPwd" placeholder="비밀번호를 입력하세요" required></td>
					<td><label id = "pwdResult"></label></td>
				</tr>	
				<tr>
					<td>* 이름 :</td>
					<td><input type="text" maxlength="5" name="userName" placeholder="이름을 입력하세요" required></td>
					<td></td>
				</tr>
				<tr>
					<td>연락처 :</td>
					<td><input type="tel" maxlength="11" name="phone" placeholder="(-없이)01012345678"></td>
					<td></td>
				</tr>
				<tr>
					<td>이메일 :</td>
					<td><input type="email" name="email" placeholder="이메일을 입력하세요"></td>
					<td></td>
				</tr>
				<tr>
					<td>주소 :</td>
					<td><input type="text" name="address" placeholder="주소를 입력하세요"></td>
					<td></td>
				</tr>
				<tr>
					<td>* 제일 좋아하는 멤버 :</td>
					<td>
						
						<input type="checkbox" id="ine" name="interest" value="아이네">
						<label for="ine">아이네</label>
						
						<input type="checkbox" id="jingburger" name="interest" value="징버거">
						<label for="jingburger">징버거</label>
						
						<input type="checkbox" id="lilpa" name="interest" value="릴파">
						<label for="lilpa">릴파</label>
						
						<input type="checkbox" id="jururu" name="interest" value="주르르">
						<label for="jururu">주르르</label>
						
						<input type="checkbox" id="gosegu" name="interest" value="고세구">
						<label for="gosegu">고세구</label>
						
						<input type="checkbox" id="vichan" name="interest" value="비챤">
						<label for="vichan">비챤</label>
						
						
					</td>
					<td></td>
				</tr>
			</table>
			<br>
			
			<div class="btns" align="center">
				<button type="button" id="goMain" onclick= "location.href='<%= request.getContextPath()%>'">메인으로</button>
				<button type="submit" id="joinBtn">가입하기</button>
				
			</div>
		</form>
	
	</div>
	</div>
	<script>
	
	function joinValidate(){
		
		if(!(/^[a-z][a-z\d]{3,11}$/i.test($("#enrollForm input[name=userId]").val()))){
			$("#enrollForm input[name=userId]").focus();
	        return false;
		}
		
		if($("#enrollForm input[name=userPwd]").val() != $("#enrollForm input[name=checkPwd]").val()){
			$("#pwdResult").text("비밀번호 불일치").css("color", "red");
			return false;			
		}
		
		 if(!(/^[가-힣]{2,}$/.test($("#enrollForm input[name=userName]").val()))){
			 $("#enrollForm input[name=userName]").focus();
	        return false;
		 }
		 
		 return true;
		
		
	}
	</script>
	
	
</body>
</html>