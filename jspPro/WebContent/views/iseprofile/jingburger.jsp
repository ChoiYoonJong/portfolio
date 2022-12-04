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
	color:yellow;
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
	color:yellow;
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
			<source src="resources/videos/jing.mp4" type="video/mp4">
		</video>
		
		<nav>
			<img src="resources/images/isedol.png" class="logo">
			<ul>
				<li><a href="#" onclick="goMain();">HOME</a></li>
				<li><a href="#">ANOTHER</a></li>
				<li><a href="#">MUSICVIDEOS</a></li>
				<li><a href="https://www.twitch.tv/jingburger">TWITCH</a></li>
			</ul>
			
		</nav>
		<div class="videocontent">
			
			
		</div>
		
		
			<div class="pcontent">
				
				<h1>안녕하세요 저는 <span>징버거</span>에요!</h1>
				<br><br>
				<p>징버거는 버츄얼 걸그룹 이세계아이돌 소속 스트롱 섹시를 담당하고 있는 멤버입니다. 나이는 1995년생,한국나이로 28살로
				이세돌 멤버중 둘째이며 첫째인 아이네 셋째인 릴하퐈 함께 언니즈로도 불립니다. 생일은 10월8일 키는 161.9cm입니다.
				MBTI는 ISFP입니다. 징버거 닉에임의 유래는 본인의 별명인 '징'과 좋아하는 음식인'햄버거'를 붙여서 만들었다고 합니다. 그리고 햄버거중
				가장 좋아하는건 맘스터치의 '휠렛버거'라고 합니다. 징버거 말투에는 살짝 사투리끼가 있는데요 본인은 울산 출신 대학교는 부산에서 나오고 가족일부는
				전라도 출신이여서 사투리가 골고로 섞여 있습니다. 이세돌 활동전 프리랜서로 그림그리는 일을 하였다고 합니다. </p>
			</div>
			
			ㄴ
		
		
	</div>
	<div class="image-box">
		<img src="resources/images/jingburgerprofile.png">
	</div>
	

	
	<script>
			function goMain(){
				location.href="<%= request.getContextPath()%>";
			}
			
	</script>

</body>
</html>