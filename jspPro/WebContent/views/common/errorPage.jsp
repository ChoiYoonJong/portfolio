<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String message = (String)request.getAttribute("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	body {
	    background-color: #000000;
	    font-family: Arial, Helvetica, sans-serif;
	}
	* {
	    box-sizing: border-box;
	}
	.container {
	    padding: 16px;
	    background-color: white;
	}
	
	h1 {
    color: #ffffff;
    font-size: 50px;
    text-align: center;
	}
	
	span.psw {
	    float: right;
	    padding-top: 16px;
	}
	/* Change styles for span and cancel button on extra small screens */
	@media screen and (max-width: 300px) {
	    span.psw {
	        display: block;
	        float: none;
	    }
	}
	button {
    position: relative;
    border: none;
    display: inline-block;
    padding: 15px 30px;
    border-radius: 15px;
    font-family: "paybooc-Light", sans-serif;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
    text-decoration: none;
    font-weight: 600;
    transition: 0.25s;
	}

	button:hover {
	    opacity: 0.8;
	}
	
	
</style>
</head>
<body>
	<h1 align="center"><%= message %></h1>
	<div align = "center">
	<button onclick = "location.href='<%= request.getContextPath()%>'" style ="width:50%">홈으로 돌아가기</button>
	</div>
</body>
</html>