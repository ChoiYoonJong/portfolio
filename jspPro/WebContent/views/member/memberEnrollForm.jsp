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
		<h1>????????????</h1>
        <h4>?????? ????????? ??????????????????</h4>
		<form id="enrollForm" action="<%=request.getContextPath() %>/insertMember.do" method="post" onsubmit="return joinValidate();">
			<table>
				<tr>
					<td width="200px" >* ????????? :</td>
					<td><input type="text" maxlength="13" name="userId" placeholder="???????????? ???????????????" required></td>
					
				</tr>
				<tr>
					<td>* ???????????? :</td>
					<td><input type="password" maxlength="15" name="userPwd" placeholder="??????????????? ???????????????" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* ???????????? ?????? :</td>
					<td><input type="password" maxlength="15" name="checkPwd" placeholder="??????????????? ???????????????" required></td>
					<td><label id = "pwdResult"></label></td>
				</tr>	
				<tr>
					<td>* ?????? :</td>
					<td><input type="text" maxlength="5" name="userName" placeholder="????????? ???????????????" required></td>
					<td></td>
				</tr>
				<tr>
					<td>????????? :</td>
					<td><input type="tel" maxlength="11" name="phone" placeholder="(-??????)01012345678"></td>
					<td></td>
				</tr>
				<tr>
					<td>????????? :</td>
					<td><input type="email" name="email" placeholder="???????????? ???????????????"></td>
					<td></td>
				</tr>
				<tr>
					<td>?????? :</td>
					<td><input type="text" name="address" placeholder="????????? ???????????????"></td>
					<td></td>
				</tr>
				<tr>
					<td>* ?????? ???????????? ?????? :</td>
					<td>
						
						<input type="checkbox" id="ine" name="interest" value="?????????">
						<label for="ine">?????????</label>
						
						<input type="checkbox" id="jingburger" name="interest" value="?????????">
						<label for="jingburger">?????????</label>
						
						<input type="checkbox" id="lilpa" name="interest" value="??????">
						<label for="lilpa">??????</label>
						
						<input type="checkbox" id="jururu" name="interest" value="?????????">
						<label for="jururu">?????????</label>
						
						<input type="checkbox" id="gosegu" name="interest" value="?????????">
						<label for="gosegu">?????????</label>
						
						<input type="checkbox" id="vichan" name="interest" value="??????">
						<label for="vichan">??????</label>
						
						
					</td>
					<td></td>
				</tr>
			</table>
			<br>
			
			<div class="btns" align="center">
				<button type="button" id="goMain" onclick= "location.href='<%= request.getContextPath()%>'">????????????</button>
				<button type="submit" id="joinBtn">????????????</button>
				
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
			$("#pwdResult").text("???????????? ?????????").css("color", "red");
			return false;			
		}
		
		 if(!(/^[???-???]{2,}$/.test($("#enrollForm input[name=userName]").val()))){
			 $("#enrollForm input[name=userName]").focus();
	        return false;
		 }
		 
		 return true;
		
		
	}
	</script>
	
	
</body>
</html>