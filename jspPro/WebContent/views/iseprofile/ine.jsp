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
	color:BlueViolet;
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
	color:BlueViolet;
	font-style:Amarillo;
}

.pcontent p span{
	font-family:serif;
	font-weight:1000;
	color:Indigo;
	font-style:Amarillo;
}


/*아이네 소개 이미지 디자인 */
.image-box img{
	width:800px;
	display: block;
}

.image-box {
	width:100px;
	height:100%;
	right:40%;	
}


/*아이네 맨위 자동 영상 디자인 및 위치 */
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
			<source src="resources/videos/ine.mp4" type="video/mp4">
		</video>
		
		<nav>
			<img src="resources/images/isedol.png" class="logo">
			<ul>
				<li><a href="#" onclick="goMain();">HOME</a></li>
				<li><a href="#">ANOTHER</a></li>
				<li><a href="#">MUSICVIDEOS</a></li>
				<li><a href="https://www.twitch.tv/vo_ine">TWITCH</a></li>
			</ul>
			
		</nav>
		<div class="videocontent">
			
			
		</div>
		
		
			<div class="pcontent">
				
				<h1>안녕하세요 저는 <span>아이네</span>에요!</h1>
				<br><br>
				<p>이세계 아이돌 맏언니 자타공인 이세돌의 메인 보컬라인 하지만 평소 목소리는 데친 숙주나물과 같은 '아이네'는 1994년생으로
				올해나이 29세입니다. '아이네'라는 이름은 독일어로 '하나의'라는 'Eine'에서 'Ine'로 스펠링을 바꾼것이라고 하는데요
				퍼스널 컬러는 블루 바이올렛,머리카락은 회색이 아닌 은발이며 MBTI는 INFP입니다. 아이네는 릴파와 함께 메인보컬 라인으로 불립니다.
				아이네의 키는 158cm입니다. 여자키로는 작지도 크지도 않은, 정상범주 안에 있는 키라고 할 수 있지만 멤버중에서 가장 작은 역활을 담당하고 있습니다.
				 </p>
			</div>
			
			
		
		
	</div>
	<div class="image-box">
		<img src="resources/images/ineprofile.png">
	</div>
	

	
	<script>
			function goMain(){
				location.href="<%= request.getContextPath()%>";
			}
			
	</script>

</body>
</html>