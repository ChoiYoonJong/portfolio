<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.uni.notice.model.vo.Notice"%>

<%
	ArrayList<Notice> list = (ArrayList<Notice>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>이세계 아이돌 팬 사이트</title>
<style>
.content{
	width:70%;
	height:800px;
}

#idx_top{
		width:100%; 
		padding: 100px 0;
		background: url(resources/images/ise.jfif) no-repeat 50% 80% /cover;
		text-align:center;
		
	}
	
	#idx_top strong,
	#idx_top p{
		color:black;
	}
	
	#idx_top strong{
		font-size:60px;
		letter-spacing: -2px;
	}
	#idx_top p span{
		color:purple;
	}
	#idx_top p{
		margin-top:20px;
		font-size:20px;
	}
	
	
	#idx_notice_wrap > div{
		width:90%;
		min-width:1000px;
		min-width:1200px;
		margin:0 auto;
	}
	
	
	#idx_notice_wrap{
		padding: 50px 0;
		background: gray;
	}
	
	.idx_notice{
		width:500px;
		background:white;
	}
	
	.idx_notice .title{
		position:relative;
		padding: 20px;
		border-bottom: 1px solid #aaa;
	}
	.idx_notice .title a{
		display: inline-block;
		vertical-align:middle;
	}
	.idx_notice .name{
		font-size: 24px;
		font-weight: 600;
		letter-soacing: -2px;
	}
	
	.idx_notice .more{
		position:absolute;
		top:25px;
		right:20px;
	}
	
	.idx_notice .more img{
		width: 24px;
		margin-left: 3px;
	}
	
	.idx_notice .list{
		padding: 20px;
	}
	
	.idx_notice .list ul li{
		margin-top:10px;
		font-size:0;
	}
	
	.idx_notice .list ul li:first-child{
		margin-top:0;
	}
	
	.idx_notice .list a,
	.idx_notice .list span{
		display: inline-block;
		vertical-align:middle;
	}
	
	.idx_notice .list a{
		width: 80%;
		font-size: 14px;
	}
	.idx_notice .list span{
		width: 20%;
		text-align:right;
		font-size: 12px;
		color:gray;
	}
	
	.section input[id*="slide"]{
	display:none;
}
.section .slidewrap{
	max-width:1500px;
	margin:0 auto;
	overflow:hidden;
}
.section .slidelist{
	white-space:nowrap;
	font-size:0;
}
.section .slidelist > li{
	display:inline-block;
	vertical-align:middle;
	width:100%;
	transition:all .5s;
}
.section .slidelist > li > a {
	display:block;
	position:relative;
}
.section .slidelist > li > a img {
	width:100%;
}
.section .slidelist label{
	position:absolute;
	z-index:10;
	top:50%;
	transform:translateY(-50%);
	padding:50px;
	cursor:pointer;
}
.section .slidelist .left{
	left:30px;
	background:url('resources/images/left.png') center center / 100% no-repeat;	
}
.section .slidelist .right{
	right:30px;
	background:url('resources/images/right.png') center center / 100% no-repeat;	
}

.section [id="slide01"]:checked ~ .slidewrap .slidelist > li {transform:translateX(0%);}
.section [id="slide02"]:checked ~ .slidewrap .slidelist > li {transform:translateX(-100%);}
.section [id="slide03"]:checked ~ .slidewrap .slidelist > li {transform:translateX(-200%);}
	
	
	
</style>
</head>
<body>
	<%@ include file = "views/common/menubar.jsp" %>
	<div class="header">
		
	</div>
	
	<div class="container">
			<div id="idx_top">
				<strong>이세계아이돌</strong>
				<p>둘, 셋! 차원을 넘어! 안녕하세요, <span>이세계 아이돌</span>입니다!</p>
			</div>
		</div>
		
		
		
		<div id="idx_notice_wrap">
			<div class="idx_notice">
				<div class="title">
					<a href="#" class="name">공지사항</a>
					<a href="#" class="more"><div onclick="goNotice();"><img src="resources/images/more.png" alt="더 보기"></div></a>
				</div>
				<div class="list">
					<ul>
						<li>
							<a href="#">글 제목이 들어갑니다.</a>
							<span>2019-11-14</span>
						</li>
						<li>
							<a href="#">글 제목이 들어갑니다.</a>
							<span>2019-11-14</span>
						</li>
						<li>
							<a href="#">글 제목이 들어갑니다.</a>
							<span>2019-11-14</span>
						</li>
						<li>
							<a href="#">글 제목이 들어갑니다.</a>
							<span>2019-11-14</span>
						</li>
						<li>
							<a href="#">글 제목이 들어갑니다.</a>
							<span>2019-11-14</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	
	
	<div class="section">
		<input type="radio" name="slide" id="slide01" checked>
		<input type="radio" name="slide" id="slide02">
		<input type="radio" name="slide" id="slide03">
		
		<div class="slidewrap">
			<ul class="slidelist">
				<li>
					<a>
						<label for="slide03" class="left"></label>
						<img src="resources/images/01.jpg">
						<label for="slide02" class="right"></label>
					</a>
				</li>
				<li>
					<a>
						<label for="slide01" class="left"></label>
						<img src="resources/images/02.jpg">
						<label for="slide03" class="right"></label>
					</a>
				</li>
				<li>
					<a>
						<label for="slide02" class="left"></label>
						<img src="resources/images/03.jpg">
						<label for="slide01" class="right"></label>
					</a>
				</li>
			</ul>
		</div>
	</div>
	
	
	
	
	
		
	<div class="content">
	
	</div>
	
	<%@ include file = "views/common/footer.jsp" %>
</body>
</html>