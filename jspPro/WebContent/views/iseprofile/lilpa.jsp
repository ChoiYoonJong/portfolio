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
	color:Indigo;
	font-weight:bold;
	text-shadow: #FC0 1px 0 10px;
	font-size:17px;
}


/*릴파 소개글 디자인 */
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
	color:Indigo;
	font-style:Amarillo;
}

.pcontent p span{
	font-family:serif;
	font-weight:1000;
	color:Indigo;
	font-style:Amarillo;
}


/*릴파 소개 이미지 디자인 */
.image-box img{
	width:1000px;
	display: block;
}

.image-box {
	width:100px;
	height:100%;
	right:40%;	
}


/*릴파 맨위 자동 영상 디자인 및 위치 */
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
			<source src="resources/videos/lilpaintro.mp4" type="video/mp4">
		</video>
		
		<nav>
			<img src="resources/images/isedol.png" class="logo">
			<ul>
				<li><a href="#" onclick="goMain();">HOME</a></li>
				<li><a href="#">ANOTHER</a></li>
				<li><a href="#">MUSICVIDEOS</a></li>
				<li><a href="https://www.twitch.tv/lilpaaaaaa">TWITCH</a></li>
			</ul>
			
		</nav>
		<div class="videocontent">
			
			
		</div>
		
		
			<div class="pcontent">
				
				<h1>안녕하세요 저는 <span>릴파</span>에요!</h1>
				<br><br>
				<p>전직 아이돌 출신 이자 노력의 아이콘으로 불리며 이세계 아이돌 활동으로 떡상하게 된<span>'릴파'</span>는
				<span>1996년3월9일</span>생으로 올해 나이 <span>27세</span>입니다. 키는 164cm MBTI ENFP로 인싸의 기질이 충만한 그녀는
				고등학생 시절에는 여자애들끼리 같이 급식을 먹는데 계속 말하느라 항상 밥이 남을 정도였다고 합니다.
				방과 후 활동은 노래 부르는 동아리에 가입했고 대학교 학과는 실용음악 재즈보털 전공을 했다고합니다.
				릴파는 '이세계아이돌'에서 유일한 전직 아이돌 경력을 가진 멤버입니다. 연습생 시절 164cm 43kg까지
				감량을 했던 릴파는 무리한 다이어트와연습으로 열걸음 걸으면 쓰러질 정도였다고한다.이후 무대에 설 기회를
				얻어 메인보컬로 데뷔하게 됩니다.하지만 활동한지 1년만에 한 대학축제 마지막으로 아이돌 그룹이 해체됩니다.
				아이돌 그룹 해체이후 평소 우왁굳님의 방송을 챙겨봤던 릴파는 우왁굳이 버츄얼 아이돌을 모집하는 공지를 보고
				다시 한번 아이돌이 되고자 결심하였습니다. 현재는 이세돌에서 메인보컬로 좋은 활동을 하고 있습니다.</p>
			</div>
			
			
		
		
	</div>
	<div class="image-box">
		<img src="resources/images/Lilpanew1.png">
	</div>
	

	
	<script>
			function goMain(){
				location.href="<%= request.getContextPath()%>";
			}
			
	</script>

</body>
</html>