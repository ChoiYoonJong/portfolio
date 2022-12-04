<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>about</title>

<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet"
	href="<c:url value="/resources/user/css/boostrap.min.css"/>">
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="<c:url value="/resources/user/js/boostrap.min,js"/>"></script> 
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
  margin: 0;
}

html {
  box-sizing: border-box;
  border-radius:10px;
}

*, *:before, *:after {
  box-sizing: inherit;
}

.column {
  float: left;
  width: 33.3%;
  margin-bottom: 16px;
  padding: 0 8px;
}

.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  margin: 8px;
  border-radius:10px;
  background-color: white;
}

.column .ine:hover{
  border-radius:10px;
  background-color: BlueViolet;
  cursor:pointer;
}

.column .jing:hover{
  border-radius:10px;
  background-color: yellow;
  cursor:pointer;
}

.column .lilpa:hover{
  border-radius:10px;
  background-color: Indigo;
  cursor:pointer;
}

.column .jururu:hover{
  border-radius:10px;
  background-color: pink;
  cursor:pointer;
}

.column .gosegu:hover{
  border-radius:10px;
  background-color:lightblue;
  cursor:pointer;
}

.column .vichan:hover{
  border-radius:10px;
  background-color: lightgreen;
  cursor:pointer;
}



.about-section {
  padding: 50px;
  text-align: center;
  background-color: rgb(250, 142, 174);
  color: white;
}

.container {
  padding: 0 16px;
}

.container::after, .row::after {
  content: "";
  clear: both;
  display: table;
}

.title {
  color:rgb(250, 142, 174);
}

.button {
  border: none;
  outline: 0;
  display: inline-block;
  padding: 8px;
  color: white;
  background-color: rgb(250, 142, 174);
  text-align: center;
  cursor: pointer;
  width: 100%;
  border-radius:10px;
}

.button:hover {
  background-color: rgb(210, 57, 103);
}




 

}
</style>

</head>

<body>

<%@ include file="../common/menubar.jsp" %>
<div class="about-section">
  <h1>이세돌 맴버</h1>
  <p>이세계 아이돌은 트위치 스트리머 우왁굳이 기획한 프로젝트[9]를 통해 만들어진 6인조 버츄얼 걸그룹[10]으로, 2021년 12월 17일 디지털 싱글 앨범 RE : WIND를 발매하여 정식으로 데뷔하였다.</p>
  <p>1집 RE:WIND | 2집 겨울봄 | 3집 예정 | 4집 예정</p>
</div>

<h2 style="text-align:center">이세돌 프로필</h2>
<div class="row">
  <div class="column">
    <div class="card">
      <img class="ine" src="resources/images/ine.png" alt="아이네" onclick="goIne();" style="width:100%">
      <div class="container">
        <h2>아이네</h2>
        <p class="title">메인보컬</p>
        <p>출색:1994년 키:158cm 혈액형:B형</p>
        <a href="https://www.youtube.com/channel/UCroM00J2ahCN6k-0-oAiDxg"><p><button class="button">아이네 유튜브</button></p></a>
        <a href="https://www.twitch.tv/vo_ine"><p><button class="button">아이네 트위치</button></p></a>
      </div>
    </div>
  </div>

  <div class="column">
    <div class="card">
      <img class="jing" src="resources/images/jing.png" alt="징버거" onclick="goJing();" style="width:100%">
      <div class="container">
        <h2>징버거</h2>
        <p class="title">서브보컬</p>
         <p>출색:1995년10월08일 키:61.9cm 혈액형:B형</p>
        <a href="https://www.youtube.com/c/%EC%A7%95%EB%B2%84%EA%B1%B0"><p><button class="button">징버거 유튜브</button></p></a>
        <a href="https://www.twitch.tv/jingburger"><p><button class="button">징버거 트위치</button></p></a>
      </div>
    </div>
  </div>

  <div class="column">
    <div class="card">
      <img class="lilpa" src="resources/images/lilpa.png" alt="릴파" onclick="goLilpa();" style="width:100%">
      <div class="container">
        <h2>릴파</h2>
        <p class="title">메인보컬</p>
        <p>출색:1996년03월09일 키:164cm 혈액형:O형</p>
        <a href="https://www.youtube.com/channel/UC-oCJP9t47v7-DmsnmXV38Q"><p><button class="button">릴파 유튜브</button></p></a>
        <a href="https://www.twitch.tv/lilpaaaaaa"><p><button class="button">릴파 트위치</button></p></a>
      </div>
    </div>
  </div>
  
   <div class="column">
    <div class="card">
      <img class="jururu" src="resources/images/jururu.png" alt="주르르" onclick="goJururu();" style="width:100%">
      <div class="container">
        <h2>주르르</h2>
       <p class="title">서브보컬</p>
        <p>출색:1997년06월10일 키:161.9cm 혈액형:O형</p>
        <a href="https://www.youtube.com/channel/UCTifMx1ONpElK5x6B4ng8eg/featured"><p><button class="button">주르르 유튜브</button></p></a>
        <a href="https://www.twitch.tv/cotton__123"><p><button class="button">주르르 트위치</button></p></a>
      </div>
    </div>
  </div>
  
   <div class="column">
    <div class="card">
      <img class="gosegu" src="resources/images/gosegu.png" alt="고세구" onclick="goGosegu();" style="width:100%">
      <div class="container">
        <h2>고세구</h2>
       <p class="title">래퍼</p>
        <p>출색:1998년 키:300m 혈액형:B형</p>
        <a href="https://www.youtube.com/channel/UCV9WL7sW6_KjanYkUUaIDfQ"><p><button class="button">고세구 유튜브</button></p></a>
        <a href="https://www.twitch.tv/gosegugosegu"><p><button class="button">고세구 트위치</button></p></a>
      </div>
    </div>
  </div>
  
   <div class="column">
    <div class="card">
      <img class="vichan" src="resources/images/vichan.png" alt="비챤" onclick="goVichan();" style="width:100%">
      <div class="container">
        <h2>비챤</h2>
        <p class="title">리드보컬</p>
        <p>출색:2000년01월16일 키:160cm 혈액형:B형</p>
        <a href="https://www.youtube.com/channel/UCs6EwgxKLY9GG4QNUrP5hoQ"><p><button class="button">비챤 유튜브</button></p></a>
        <a href="https://www.twitch.tv/viichan6"><p><button class="button">비챤 트위치</button></p></a>
      </div>
    </div>
  </div>
</div>

		<script>
			function goIne(){
				location.href="<%= request.getContextPath()%>/Ine.do";
			}
			
			function goJing(){
				location.href="<%= request.getContextPath()%>/Jing.do";
			}
		
		
			function goLilpa(){
				location.href="<%= request.getContextPath()%>/lilpa.do";
			}
			
			function goJururu(){
				location.href="<%= request.getContextPath()%>/Jururu.do";
			}
			
			function goGosegu(){
				location.href="<%= request.getContextPath()%>/Gosegu.do";
			}
		
		
			function goVichan(){
				location.href="<%= request.getContextPath()%>/Vichan.do";
			}
	
		
		
		
		</script>



<%@ include file = "../common/footer.jsp" %>
</body>
</html>