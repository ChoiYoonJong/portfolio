/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.58
 * Generated at: 2022-12-01 22:58:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.views.notice;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import com.uni.notice.model.vo.Notice;
import com.uni.member.model.vo.Member;

public final class noticeListView_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/views/notice/../common/menubar.jsp", Long.valueOf(1669926799245L));
    _jspx_dependants.put("/views/notice/../common/footer.jsp", Long.valueOf(1669752492866L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.uni.member.model.vo.Member");
    _jspx_imports_classes.add("com.uni.notice.model.vo.Notice");
    _jspx_imports_classes.add("java.util.ArrayList");
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
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP?????? ?????? GET, POST ?????? HEAD ??????????????? ???????????????. Jasper??? OPTIONS ????????? ?????? ???????????????.");
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

      out.write('\r');
      out.write('\n');

	ArrayList<Notice> list = (ArrayList<Notice>) request.getAttribute("list");

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<style>\r\n");
      out.write("	.outer{\r\n");
      out.write("		width:800px;\r\n");
      out.write("		height:500px;\r\n");
      out.write("		background:black;\r\n");
      out.write("		color:white;\r\n");
      out.write("		margin:auto;\r\n");
      out.write("		margin-top:50px;\r\n");
      out.write("	}\r\n");
      out.write("	.listArea{\r\n");
      out.write("		width: 100%;\r\n");
      out.write("		border-top: 2px solid skyblue;\r\n");
      out.write("	}\r\n");
      out.write("	.listArea td{\r\n");
      out.write("		text-align: center;\r\n");
      out.write("		padding:10px;\r\n");
      out.write("		font-size: 14px;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("	.listArea tr{\r\n");
      out.write("		border-bottom: 1px solid #999;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("	.listArea tbody tr td:nth-child(2){\r\n");
      out.write("		text-align: left;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("	.searchArea{\r\n");
      out.write("		margin-top:50px;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("	.listArea tbody tr td:nth-child(2):hover{\r\n");
      out.write("		text-decoration: underline;\r\n");
      out.write("		cursor: pointer;\r\n");
      out.write("	}\r\n");
      out.write("	table{\r\n");
      out.write("		border-collapse: collapse;\r\n");
      out.write("	}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("	");
      out.write("\r\n");
      out.write("\r\n");

	Member loginUser = (Member)session.getAttribute("loginUser");

	String msg = (String)session.getAttribute("msg");
	String contextPath = request.getContextPath();

      out.write("\r\n");
      out.write("\r\n");
      out.write("<script src=\"https://cdn.jsdelivr.net/npm/sweetalert2@10\"></script>\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"EUC-KR\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n");
      out.write("<style>\r\n");
      out.write("	body{\r\n");
      out.write("		background:url('");
      out.print(request.getContextPath() );
      out.write("/resources/images/2.jpg') no-repeat; \r\n");
      out.write("		background-position: 50% 50%;\r\n");
      out.write("		background-repeat: no-repeat;\r\n");
      out.write("		background-size: 50% 50%;\r\n");
      out.write("		background-color: black;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	#loginForm, #userInfo{float:right;}\r\n");
      out.write("	.btns button{border: 1px solid skyblue;\r\n");
      out.write("            background-color: rgba(0,0,0,0);\r\n");
      out.write("            color: skyblue;\r\n");
      out.write("            padding: 5px;\r\n");
      out.write("				  }\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	#enrollBtn, #mypageBtn{\r\n");
      out.write("			border-top-right-radius: 5px;\r\n");
      out.write("            border-bottom-right-radius: 5px;    \r\n");
      out.write("            margin-left:-3px}\r\n");
      out.write("	#loginBtn, #logoutBtn{ \r\n");
      out.write("			border-top-left-radius: 5px;\r\n");
      out.write("            border-bottom-left-radius: 5px;\r\n");
      out.write("            margin-right:-4px;}\r\n");
      out.write("	#userInfo a{text-decoration:none;color:white;}\r\n");
      out.write("	\r\n");
      out.write("	.btns button:hover{border: 1px solid skyblue;\r\n");
      out.write("            background-color: skyblue;\r\n");
      out.write("            color: purple;\r\n");
      out.write("            padding: 5px;\r\n");
      out.write("				  }\r\n");
      out.write("	\r\n");
      out.write("	/* ???????????? ?????? ?????????*/\r\n");
      out.write("	.navWrap{background-color:black; width:100%; height:50px}\r\n");
      out.write("	.navWrap>.nav{width:600px;margin:auto;}\r\n");
      out.write("	.menu{text-align:center;color:white;font-weight:bold;width:150px;height:50px;display:table-cell;font-size:20px;vertical-align:middle;}\r\n");
      out.write("	.menu:hover{background-color:rgb(250, 142, 174); text-shadow:5px 5px 5px rgb(0,0,0);\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("\r\n");
      out.write("	.logo{\r\n");
      out.write("		float: lfet;\r\n");
      out.write("	}\r\n");
      out.write("	.logo img{\r\n");
      out.write("		height: 60px;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("<script>\r\n");
      out.write("	function loginValidate(){\r\n");
      out.write("		if($(\"#userId\").val().trim().length === 0){\r\n");
      out.write("			alert(\"???????????? ???????????????\");\r\n");
      out.write("			$(\"#userId\").focus();\r\n");
      out.write("			return false;\r\n");
      out.write("		}\r\n");
      out.write("		if($(\"#userPwd\").val().trim().length === 0){\r\n");
      out.write("			alert(\"??????????????? ???????????????\");\r\n");
      out.write("			$(\"#userPwd\").focus();\r\n");
      out.write("			return false;\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		return true;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("	<h1 class=\"logo\">\r\n");
      out.write("		<img src=\"resources/images/logo.png\" alt=\"??????\" >\r\n");
      out.write("	</h1>\r\n");
      out.write("	<h1 align=\"center\" style=\"color:white;\">????????? ?????????</h1>\r\n");
      out.write("	<div class=\"loginArea\">\r\n");
      out.write("	");
 if( loginUser == null) { 
      out.write("\r\n");
      out.write("	<form id = \"loginForm\" action=\"");
      out.print(request.getContextPath());
      out.write("/loginMember.do\" method=\"post\" onsubmit=\"return loginValidate();\">\r\n");
      out.write("			<table>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<th><label for = \"userId\" style=\"color:white;\">?????????</label></th>\r\n");
      out.write("					<td><input id=\"userId\" type=\"text\" name=\"userId\"></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<th><label for = \"userPwd\" style=\"color:white;\">????????????</label></th>\r\n");
      out.write("					<td><input type=\"password\" id=\"userPwd\" type=\"text\" name=\"userPwd\"></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("			</table>\r\n");
      out.write("			<div class =\"btns\" align=\"center\">\r\n");
      out.write("				\r\n");
      out.write("				<button id = \"loginBtn\" type=\"submit\">?????????</button>\r\n");
      out.write("			    <button id = \"enrollBtn\" type=\"button\" onclick=\"enrollPage();\">????????????</button>\r\n");
      out.write("			</div>\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		</form>\r\n");
      out.write("	");
} else{ 
      out.write("\r\n");
      out.write("			<div id = \"userInfo\">\r\n");
      out.write("				<b style = \"color:white;\">");
      out.print(loginUser.getUserName() );
      out.write(" ??? </b> ??? ????????? ???????????????.\r\n");
      out.write("				<br><br>\r\n");
      out.write("				<div class =\"btns\" align=\"center\">\r\n");
      out.write("					<a href = \"");
      out.print(request.getContextPath() );
      out.write("/mypageMember.do\">???????????????</a>\r\n");
      out.write("					<a href = \"");
      out.print(request.getContextPath() );
      out.write("/logoutMember.do\">????????????</a>\r\n");
      out.write("				</div>\r\n");
      out.write("			\r\n");
      out.write("			\r\n");
      out.write("			</div>	\r\n");
      out.write("	");
} 
      out.write("\r\n");
      out.write("	</div>\r\n");
      out.write("	\r\n");
      out.write("	<script type=\"text/javascript\">\r\n");
      out.write("		function enrollPage(){\r\n");
      out.write("			location.href =\"");
      out.print(request.getContextPath());
      out.write("/enrollFormMember.do\";\r\n");
      out.write("		}\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	</script>\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	<br clear=\"both\">\r\n");
      out.write("		\r\n");
      out.write("		<div class=\"navWrap\">\r\n");
      out.write("			<div class=\"nav\">\r\n");
      out.write("				<div class=\"menu\" onclick=\"goMain();\">HOME</div>\r\n");
      out.write("				<div class=\"menu\" onclick=\"goAbout();\">ABOUT</div>\r\n");
      out.write("				<div class=\"menu\" onclick=\"goNotice();\">????????????</div>\r\n");
      out.write("				<div class=\"menu\" onclick=\"goBoard();\">?????????</div>\r\n");
      out.write("				<div class=\"menu\" onclick=\"goThumbnail();\">???????????????</div>\r\n");
      out.write("				\r\n");
      out.write("			</div>\r\n");
      out.write("			\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		<script>\r\n");
      out.write("		function goMain(){\r\n");
      out.write("			location.href=\"");
      out.print( request.getContextPath());
      out.write("\";\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function goAbout(){\r\n");
      out.write("			location.href=\"");
      out.print( request.getContextPath());
      out.write("/about.do\";\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		function goNotice(){\r\n");
      out.write("			location.href=\"");
      out.print( request.getContextPath());
      out.write("/listNotice.do\";\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("		</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
      out.write("\r\n");
      out.write("	\r\n");
      out.write("	<div class=\"outer\">\r\n");
      out.write("		<br>\r\n");
      out.write("		<h2 align=\"center\">????????????</h2>\r\n");
      out.write("		<br>\r\n");
      out.write("		        \r\n");
      out.write("		<table class=\"listArea\" align=\"center\">\r\n");
      out.write("			<thead>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<th>?????????</th>\r\n");
      out.write("					<th width=\"300\">?????????</th>\r\n");
      out.write("					<th width=\"100\">?????????</th>\r\n");
      out.write("					<th>?????????</th>\r\n");
      out.write("					<th width=\"100\">?????????</th>\r\n");
      out.write("				</tr>\r\n");
      out.write("			</thead>\r\n");
      out.write("			<tbody>\r\n");
      out.write("				<!-- \r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>3</td>\r\n");
      out.write("					<td>????????? ??????????????????</td>\r\n");
      out.write("					<td>admin</td>\r\n");
      out.write("					<td>10</td>\r\n");
      out.write("					<td>2020-02-10</td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>2</td>\r\n");
      out.write("					<td>????????? ??????????????????</td>\r\n");
      out.write("					<td>admin</td>\r\n");
      out.write("					<td>100</td>\r\n");
      out.write("					<td>2020-02-01</td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>1</td>\r\n");
      out.write("					<td>????????? ???????????? ??????</td>\r\n");
      out.write("					<td>admin</td>\r\n");
      out.write("					<td>45</td>\r\n");
      out.write("					<td>2019-12-25</td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				 -->\r\n");
      out.write("\r\n");
      out.write("				");
 if(list.isEmpty()){ 
      out.write("\r\n");
      out.write("				 	<tr>\r\n");
      out.write("						<td colspan=\"5\">???????????? ??????????????? ????????????.</td>\r\n");
      out.write("					</tr>\r\n");
      out.write("				 ");
 }else{  
      out.write("\r\n");
      out.write("				 	");
 for(Notice n : list){ 
      out.write("\r\n");
      out.write("				 		<tr>\r\n");
      out.write("				 			<td>");
      out.print( n.getNoticeNo() );
      out.write("</td>\r\n");
      out.write("							<td>");
      out.print( n.getNoticeTitle() );
      out.write("</td>\r\n");
      out.write("							<td>");
      out.print( n.getNoticeWriter() );
      out.write("</td>\r\n");
      out.write("							<td>");
      out.print( n.getCount() );
      out.write("</td>\r\n");
      out.write("							<td>");
      out.print( n.getCreateDate() );
      out.write("</td>\r\n");
      out.write("				 		</tr>\r\n");
      out.write("				 	");
 } 
      out.write("\r\n");
      out.write("				 ");
 } 
      out.write("\r\n");
      out.write("			</tbody>\r\n");
      out.write("			\r\n");
      out.write("		</table>\r\n");
      out.write("		\r\n");
      out.write("		<div style=\"width:800px; text-align:center; margin-top:10px;\">\r\n");
      out.write("		\r\n");
      out.write("		");

		for(int i=1; i<=2; i++ ){
			
			out.print(i +" ");
			
		}
		
		
      out.write("\r\n");
      out.write("		\r\n");
      out.write("		</div>\r\n");
      out.write("\r\n");
      out.write("	<form class=\"searchArea\" align=\"center\">\r\n");
      out.write("			<select id=\"condition\" name=\"condition\">\r\n");
      out.write("				<option value=\"writer\">?????????</option>\r\n");
      out.write("				<option value=\"title\">??????</option>\r\n");
      out.write("				<option value=\"content\">??????</option>\r\n");
      out.write("			</select>\r\n");
      out.write("			<input type=\"search\" name=\"search\">\r\n");
      out.write("			<button type=\"submit\">????????????</button>\r\n");
      out.write("		</form>\r\n");
      out.write("		<br><br>\r\n");
      out.write("		<div align=\"center\">\r\n");
      out.write("			");
 if(loginUser != null && loginUser.getUserId().equals("administrator")) { 
      out.write("\r\n");
      out.write("			\r\n");
      out.write("			<button onclick=\"location.href='");
      out.print(contextPath);
      out.write("/enrollFormNotice.do'\">????????????</button> \r\n");
      out.write("		");
 } 
      out.write("\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("	</div>\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	<script type=\"text/javascript\">\r\n");
      out.write("	");
if(!list.isEmpty()){
      out.write("\r\n");
      out.write("	$(function(){\r\n");
      out.write("		$(\".listArea>tbody>tr\").click(function(){\r\n");
      out.write("			var nno = $(this).children().eq(0).text();\r\n");
      out.write("			\r\n");
      out.write("			location.href=\"");
      out.print(contextPath);
      out.write("/detailNotice.do?nno=\"+nno;\r\n");
      out.write("		})\r\n");
      out.write("	})\r\n");
      out.write("	\r\n");
      out.write("	");
}
      out.write("\r\n");
      out.write("	</script>\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
      out.write("	");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"EUC-KR\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<style>\r\n");
      out.write("    /* div{border:1px solid red;} */\r\n");
      out.write("    #footer{\r\n");
      out.write("        width:80%;\r\n");
      out.write("        height:200px;\r\n");
      out.write("        margin:auto;\r\n");
      out.write("        margin-top:50px;\r\n");
      out.write("    }\r\n");
      out.write("    #footer>div{\r\n");
      out.write("    	width:100%;\r\n");
      out.write("    	padding-left:10%;\r\n");
      out.write("    }\r\n");
      out.write("    #footer-1{\r\n");
      out.write("        height:20%;\r\n");
      out.write("        border-top:1px solid lightgray;\r\n");
      out.write("        border-bottom:1px solid lightgray;\r\n");
      out.write("    }\r\n");
      out.write("    #footer-2{\r\n");
      out.write("        height:80%;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    #footer-1 > a{\r\n");
      out.write("        text-decoration:none;\r\n");
      out.write("        font-weight: 600;\r\n");
      out.write("        margin:10px;\r\n");
      out.write("        line-height: 40px;\r\n");
      out.write("        color: white;\r\n");
      out.write("    }\r\n");
      out.write("    #footer-2>p{\r\n");
      out.write("        margin: 0;\r\n");
      out.write("        padding:10px;\r\n");
      out.write("        font-size: 13px;\r\n");
      out.write("    }\r\n");
      out.write("    #p1{\r\n");
      out.write("    	color:white;\r\n");
      out.write("    }\r\n");
      out.write("    #p2{\r\n");
      out.write("        text-align:center;\r\n");
      out.write("    }\r\n");
      out.write("    <style type=\"text/css\">\r\n");
      out.write("	a:link{\r\n");
      out.write("		color:royalblue;\r\n");
      out.write("	    transition : 1s; /* ?????? ????????? ??? ????????? ?????? ?????? */\r\n");
      out.write("	}\r\n");
      out.write("	a:visited {\r\n");
      out.write("		color:teal;\r\n");
      out.write("	}\r\n");
      out.write("	a:hover {\r\n");
      out.write("		color:purple;\r\n");
      out.write("		text-decoration: none;\r\n");
      out.write("	}\r\n");
      out.write("	a:active{\r\n");
      out.write("		color:blue;\r\n");
      out.write("		background-color: white;\r\n");
      out.write("		text-decoration: none;	\r\n");
      out.write("	}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("	<div id=\"footer\">\r\n");
      out.write("        <div id=\"footer-1\">\r\n");
      out.write("            <a href=\"https://woowakgood.com/\">?????????</a> | \r\n");
      out.write("            <a href=\"https://www.youtube.com/c/welshcorgimessi\">????????????</a> | \r\n");
      out.write("            <a href=\"https://github.com/ChoiYoonJong\">?????????:??????</a>  \r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"footer-2\">\r\n");
      out.write("            <p id=\"p1\">\r\n");
      out.write("                <a href=\"https://www.youtube.com/channel/UCroM00J2ahCN6k-0-oAiDxg\">?????????</a> : 1994???|158cm|B??? <br>\r\n");
      out.write("                <a href=\"https://www.youtube.com/c/%EC%A7%95%EB%B2%84%EA%B1%B0\">?????????</a> : 1995???10???08???|161.9cm|B??? <br>\r\n");
      out.write("                <a href=\"https://www.youtube.com/channel/UC-oCJP9t47v7-DmsnmXV38Q\">??????</a> : 1996???03???09???|164cm|O??? <br>\r\n");
      out.write("                <a href=\"https://www.youtube.com/c/%EC%A3%BC%EB%A5%B4%EB%A5%B4\">?????????</a> : 1997???06???10???|161.9cm|O??? <br>\r\n");
      out.write("                <a href=\"https://www.youtube.com/channel/UCV9WL7sW6_KjanYkUUaIDfQ\">?????????</a> : 1998???|300m|B??? <br>\r\n");
      out.write("                <a href=\"https://www.youtube.com/channel/UCs6EwgxKLY9GG4QNUrP5hoQ\">??????</a> : 2000???01???16???|160cm|B??? \r\n");
      out.write("                \r\n");
      out.write("            </p>  \r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
