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
	color:green;
	font-weight:bold;
	text-shadow: #FC0 1px 0 10px;
	font-size:17px;
}


/*비챤 소개글 디자인 */
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
	color:lightgreen;
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


/*비챤 맨위 자동 영상 디자인 및 위치 */
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
			<source src="resources/videos/vichan.mp4" type="video/mp4">
		</video>
		
		<nav>
			<img src="resources/images/isedol.png" class="logo">
			<ul>
				<li><a href="#" onclick="goMain();">HOME</a></li>
				<li><a href="#">ANOTHER</a></li>
				<li><a href="#">MUSICVIDEOS</a></li>
				<li><a href="https://www.twitch.tv/viichan6">TWITCH</a></li>
			</ul>
			
		</nav>
		<div class="videocontent">
			
			
		</div>
		
		
			<div class="pcontent">
				
				<h1>안녕하세요 저는 <span>비챤</span>에요!</h1>
				<br><br>
				<p>비챤은 버츄얼 걸그룹'이세계아이돌'소속 소프트 큐티를 담당하고 있는 막내 멤버입니다. 나이는 2000년생,한국 나이 23살로
				이세돌 멤버 6명중 유일한 2000년생이죠 생일은 1월16일 이며 키는 160cm입니다. MBTI 는 INFJ이며 퍼스널 컬러는 초록색입니다.
				홍대병 말기인 비챤은 다른 사람과 닉네임이 겹치는게 싫어다고 합니다 .비챤의 비는 무지개 꿈 태몽에 레이보우에서 레인의 비를 따왔으며 비는 일본에서 실명
				나게 노는 것에 chan 에서 따왔다고 합니다. 이로 인해 얼마나 홍대병 말기인것을 알 수 있습니다. 애니 오타쿠이며 게임도 이것저것 다양하게 하고 게임도 잘하는
				재능이 있는 편입니다. 롤은 최고 티어 플레티넘 오버워치는 마스터를 찍은적이 있습니다.성인이 되고 나서부터는 우타이테 활동은 본격적으로 시작했습니다. 2020년부터
				한달에 한곡씩 업로드해서 유튜브에 총 23곡이 업로드 되어있습니다. 비챤은 이세돌의. 브레인을 담당하고 있습니다 방송에서는 IQ테스트 결과 140이 나왔으며 
				어휘력 테스트는 상위 0.39% 나와서 천재챤이라는 별명이 있습니다.</p>
			</div>
			
			
		
		
	</div>
	<div class="image-box">
		<img src="resources/images/vichanprofile1.png">
	</div>
	

	
	<script>
			function goMain(){
				location.href="<%= request.getContextPath()%>";
			}
			
	</script>

</body>
</html>