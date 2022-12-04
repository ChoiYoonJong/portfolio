<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>
    /* div{border:1px solid red;} */
    #footer{
        width:80%;
        height:200px;
        margin:auto;
        margin-top:50px;
    }
    #footer>div{
    	width:100%;
    	padding-left:10%;
    }
    #footer-1{
        height:20%;
        border-top:1px solid lightgray;
        border-bottom:1px solid lightgray;
    }
    #footer-2{
        height:80%;
    }

    #footer-1 > a{
        text-decoration:none;
        font-weight: 600;
        margin:10px;
        line-height: 40px;
        color: white;
    }
    #footer-2>p{
        margin: 0;
        padding:10px;
        font-size: 13px;
    }
    #p1{
    	color:white;
    }
    #p2{
        text-align:center;
    }
    <style type="text/css">
	a:link{
		color:royalblue;
	    transition : 1s; /* 속성 변경할 때 효과의 속도 조절 */
	}
	a:visited {
		color:teal;
	}
	a:hover {
		color:purple;
		text-decoration: none;
	}
	a:active{
		color:blue;
		background-color: white;
		text-decoration: none;	
	}
</style>
</head>
<body>
	<div id="footer">
        <div id="footer-1">
            <a href="https://woowakgood.com/">왁물원</a> | 
            <a href="https://www.youtube.com/c/welshcorgimessi">왁타버스</a> | 
            <a href="https://github.com/ChoiYoonJong">제작자:초이</a>  
        </div>

        <div id="footer-2">
            <p id="p1">
                <a href="https://www.youtube.com/channel/UCroM00J2ahCN6k-0-oAiDxg">아이네</a> : 1994년|158cm|B형 <br>
                <a href="https://www.youtube.com/c/%EC%A7%95%EB%B2%84%EA%B1%B0">징버거</a> : 1995년10월08일|161.9cm|B형 <br>
                <a href="https://www.youtube.com/channel/UC-oCJP9t47v7-DmsnmXV38Q">릴파</a> : 1996년03월09일|164cm|O형 <br>
                <a href="https://www.youtube.com/c/%EC%A3%BC%EB%A5%B4%EB%A5%B4">주르르</a> : 1997년06월10일|161.9cm|O형 <br>
                <a href="https://www.youtube.com/channel/UCV9WL7sW6_KjanYkUUaIDfQ">고세구</a> : 1998년|300m|B형 <br>
                <a href="https://www.youtube.com/channel/UCs6EwgxKLY9GG4QNUrP5hoQ">비챤</a> : 2000년01월16일|160cm|B형 
                
            </p>  
        </div>
    </div>
</body>
</html>