<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
*{
	margin:0;
	padding:0;
	box-sizing:border-box;
	font-family:'Poppins',sans-serif;	
}

.content{
	width:100%;
	height:100vh;
	background-image:linear-gradient(rgba(12,3,51,0.3),rgba(12,3,51,0.3));
	padding:0 5%;
	display:flex;
	align-items:center;
	justify-content:center;
}

:root{
	--backgroung-color:#fff;
	--text-color:#555;
	--title-color:#000;
}

body{
	background:var(--background-color);
	color:var(--text-color);
}



nav{
	width:100%;
	position:absolute;
	top:0;
	left:0;
	padding:20px 8%;
	display:flex;
	align-items:center;
	justify-content:space-between;
}

/*로고,메뉴창 디자인 정리*/
nav .logo{
	width:250px;
}

nav ul li{
	list-style:none;
	display: inline-block;
	margin-left:40px;
}

nav ul li a{
	text-decoration:none;
	color:white;
	font-size:17px;
}
nav ul li a:hover{
	text-decoration:none;
	color:blue;
	font-weight:bold;
	text-shadow: #FC0 1px 0 10px;
	font-size:17px;
}


/*고세구 소개글 디자인 */
.pcontent{
	margin-top:110%;
	max-width:700%;
	margin-left:50%;
}

.pcontent h1{
	font-size:70px;
	font-family:serif;
	color:var(--title-color);
	font-weight:500;
}

.pcontent h1 span{
	font-family:serif;
	font-weight:1000;
	color:lightblue;
	font-style:Amarillo;
}

.pcontent p span{
	font-family:serif;
	font-weight:1000;
	color:Indigo;
	font-style:Amarillo;
}


/*고세구 소개 이미지 디자인 */
.image-box img{
	width:1000px;
	display: block;
}

.image-box {
	width:100px;
	height:100%;
	right:40%;	
}


/*고세구 맨위 자동 영상 디자인 및 위치 */
.videocontent{
	text-align:center;
}






.back-video{
	position:absolute;
	right:0;
	bottom:0;
	z-index:-1;
}

@media(min-aspect-ratio: 16/9){
	.back-video{
		width:120%;
		height:auto;
	}
}
@media(max-aspect-ratio: 16/9){
	.back-video{
		width:auto;
		height:100%;
	}
}






</style>
</head>
<body>


	<div class="content">
	
		<video autoplay loop muted plays-inline class="back-video">
			<source src="resources/videos/gosegu.mp4" type="video/mp4">
		</video>
		
		<nav>
			<img src="resources/images/isedol.png" class="logo">
			<ul>
				<li><a href="#" onclick="goMain();">HOME</a></li>
				<li><a href="#">ANOTHER</a></li>
				<li><a href="#">MUSICVIDEOS</a></li>
				<li><a href="https://www.twitch.tv/gosegugosegu?lang=ko">TWITCH</a></li>
			</ul>
			
		</nav>
		<div class="videocontent">
			
			
		</div>
		
		
			<div class="pcontent">
				
				<h1>안녕하세요 저는 <span>고세구</span>에요!</h1>
				<br><br>
				<p>과거 섹큐버스로 활동하다가 세계 최초 킹받는 아이돌로 변신에 성공한 고세구는 1998년생으로 올해 25살입니다. '고세구'라는 닉네임은
				고양이가 세상을 구한다의 줄임말입니다. MBTI는 ENTJ 이며 퍼스널 컬러는 파란색입니다. 키는 프로필에 300m적혀있는데요 이는 비공개이기 때문에 그런것이고
				이세돌 중에 제일 클 것이다. 작을 것이다 추측은 많지만 일반 아바타로는 머리가 제일 큰 것은 확실합니다.</p>
			</div>
			
			
		
		
	</div>
	<div class="image-box">
		<img src="resources/images/goseguprofile.png">
	</div>
	

	
	<script>
			function goMain(){
				location.href="<%= request.getContextPath()%>";
			}
			
	</script>

</body>
</html>