/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.58
 * Generated at: 2022-12-03 23:27:51 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.views.iseprofile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class vichan_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<style>\r\n");
      out.write("*{\r\n");
      out.write("	margin:0;\r\n");
      out.write("	padding:0;\r\n");
      out.write("	box-sizing:border-box;\r\n");
      out.write("	font-family:'Poppins',sans-serif;	\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".content{\r\n");
      out.write("	width:100%;\r\n");
      out.write("	height:100vh;\r\n");
      out.write("	background-image:linear-gradient(rgba(12,3,51,0.3),rgba(12,3,51,0.3));\r\n");
      out.write("	padding:0 5%;\r\n");
      out.write("	display:flex;\r\n");
      out.write("	align-items:center;\r\n");
      out.write("	justify-content:center;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(":root{\r\n");
      out.write("	--backgroung-color:#fff;\r\n");
      out.write("	--text-color:#555;\r\n");
      out.write("	--title-color:#000;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("body{\r\n");
      out.write("	background:var(--background-color);\r\n");
      out.write("	color:var(--text-color);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("nav{\r\n");
      out.write("	width:100%;\r\n");
      out.write("	position:absolute;\r\n");
      out.write("	top:0;\r\n");
      out.write("	left:0;\r\n");
      out.write("	padding:20px 8%;\r\n");
      out.write("	display:flex;\r\n");
      out.write("	align-items:center;\r\n");
      out.write("	justify-content:space-between;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/*로고,메뉴창 디자인 정리*/\r\n");
      out.write("nav .logo{\r\n");
      out.write("	width:250px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("nav ul li{\r\n");
      out.write("	list-style:none;\r\n");
      out.write("	display: inline-block;\r\n");
      out.write("	margin-left:40px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("nav ul li a{\r\n");
      out.write("	text-decoration:none;\r\n");
      out.write("	color:white;\r\n");
      out.write("	font-size:17px;\r\n");
      out.write("}\r\n");
      out.write("nav ul li a:hover{\r\n");
      out.write("	text-decoration:none;\r\n");
      out.write("	color:green;\r\n");
      out.write("	font-weight:bold;\r\n");
      out.write("	text-shadow: #FC0 1px 0 10px;\r\n");
      out.write("	font-size:17px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("/*비챤 소개글 디자인 */\r\n");
      out.write(".pcontent{\r\n");
      out.write("	margin-top:110%;\r\n");
      out.write("	max-width:700%;\r\n");
      out.write("	margin-left:50%;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".pcontent h1{\r\n");
      out.write("	font-size:70px;\r\n");
      out.write("	font-family:serif;\r\n");
      out.write("	color:var(--title-color);\r\n");
      out.write("	font-weight:500;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".pcontent h1 span{\r\n");
      out.write("	font-family:serif;\r\n");
      out.write("	font-weight:1000;\r\n");
      out.write("	color:lightgreen;\r\n");
      out.write("	font-style:Amarillo;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".pcontent p span{\r\n");
      out.write("	font-family:serif;\r\n");
      out.write("	font-weight:1000;\r\n");
      out.write("	color:Indigo;\r\n");
      out.write("	font-style:Amarillo;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("/*릴파 소개 이미지 디자인 */\r\n");
      out.write(".image-box img{\r\n");
      out.write("	width:1000px;\r\n");
      out.write("	display: block;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".image-box {\r\n");
      out.write("	width:100px;\r\n");
      out.write("	height:100%;\r\n");
      out.write("	right:40%;	\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("/*비챤 맨위 자동 영상 디자인 및 위치 */\r\n");
      out.write(".videocontent{\r\n");
      out.write("	text-align:center;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(".back-video{\r\n");
      out.write("	position:absolute;\r\n");
      out.write("	right:0;\r\n");
      out.write("	bottom:0;\r\n");
      out.write("	z-index:-1;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("@media(min-aspect-ratio: 16/9){\r\n");
      out.write("	.back-video{\r\n");
      out.write("		width:120%;\r\n");
      out.write("		height:auto;\r\n");
      out.write("	}\r\n");
      out.write("}\r\n");
      out.write("@media(max-aspect-ratio: 16/9){\r\n");
      out.write("	.back-video{\r\n");
      out.write("		width:auto;\r\n");
      out.write("		height:100%;\r\n");
      out.write("	}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("	<div class=\"content\">\r\n");
      out.write("	\r\n");
      out.write("		<video autoplay loop muted plays-inline class=\"back-video\">\r\n");
      out.write("			<source src=\"resources/videos/vichan.mp4\" type=\"video/mp4\">\r\n");
      out.write("		</video>\r\n");
      out.write("		\r\n");
      out.write("		<nav>\r\n");
      out.write("			<img src=\"resources/images/isedol.png\" class=\"logo\">\r\n");
      out.write("			<ul>\r\n");
      out.write("				<li><a href=\"#\" onclick=\"goMain();\">HOME</a></li>\r\n");
      out.write("				<li><a href=\"#\">ANOTHER</a></li>\r\n");
      out.write("				<li><a href=\"#\">MUSICVIDEOS</a></li>\r\n");
      out.write("				<li><a href=\"https://www.twitch.tv/viichan6\">TWITCH</a></li>\r\n");
      out.write("			</ul>\r\n");
      out.write("			\r\n");
      out.write("		</nav>\r\n");
      out.write("		<div class=\"videocontent\">\r\n");
      out.write("			\r\n");
      out.write("			\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("			<div class=\"pcontent\">\r\n");
      out.write("				\r\n");
      out.write("				<h1>안녕하세요 저는 <span>비챤</span>에요!</h1>\r\n");
      out.write("				<br><br>\r\n");
      out.write("				<p>비챤은 버츄얼 걸그룹'이세계아이돌'소속 소프트 큐티를 담당하고 있는 막내 멤버입니다. 나이는 2000년생,한국 나이 23살로\r\n");
      out.write("				이세돌 멤버 6명중 유일한 2000년생이죠 생일은 1월16일 이며 키는 160cm입니다. MBTI 는 INFJ이며 퍼스널 컬러는 초록색입니다.\r\n");
      out.write("				홍대병 말기인 비챤은 다른 사람과 닉네임이 겹치는게 싫어다고 합니다 .비챤의 비는 무지개 꿈 태몽에 레이보우에서 레인의 비를 따왔으며 비는 일본에서 실명\r\n");
      out.write("				나게 노는 것에 chan 에서 따왔다고 합니다. 이로 인해 얼마나 홍대병 말기인것을 알 수 있습니다. 애니 오타쿠이며 게임도 이것저것 다양하게 하고 게임도 잘하는\r\n");
      out.write("				재능이 있는 편입니다. 롤은 최고 티어 플레티넘 오버워치는 마스터를 찍은적이 있습니다.성인이 되고 나서부터는 우타이테 활동은 본격적으로 시작했습니다. 2020년부터\r\n");
      out.write("				한달에 한곡씩 업로드해서 유튜브에 총 23곡이 업로드 되어있습니다. 비챤은 이세돌의. 브레인을 담당하고 있습니다 방송에서는 IQ테스트 결과 140이 나왔으며 \r\n");
      out.write("				어휘력 테스트는 상위 0.39% 나와서 천재챤이라는 별명이 있습니다.</p>\r\n");
      out.write("			</div>\r\n");
      out.write("			\r\n");
      out.write("			\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("	</div>\r\n");
      out.write("	<div class=\"image-box\">\r\n");
      out.write("		<img src=\"resources/images/vichanprofile1.png\">\r\n");
      out.write("	</div>\r\n");
      out.write("	\r\n");
      out.write("\r\n");
      out.write("	\r\n");
      out.write("	<script>\r\n");
      out.write("			function goMain(){\r\n");
      out.write("				location.href=\"");
      out.print( request.getContextPath());
      out.write("\";\r\n");
      out.write("			}\r\n");
      out.write("			\r\n");
      out.write("	</script>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
