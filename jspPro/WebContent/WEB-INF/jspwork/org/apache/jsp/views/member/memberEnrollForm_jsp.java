/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.58
 * Generated at: 2022-12-03 23:46:19 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.views.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class memberEnrollForm_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<style>\r\n");
      out.write("	\r\n");
      out.write("	*{\r\n");
      out.write("		 background-color: rgb(250, 142, 174);\r\n");
      out.write("    	font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;\r\n");
      out.write("	}\r\n");
      out.write("	\r\n");
      out.write(".outer{\r\n");
      out.write("    width: 800px;\r\n");
      out.write("    height: 1000px;\r\n");
      out.write("    margin: auto;\r\n");
      out.write("    border-radius: 10px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("h1{\r\n");
      out.write("    text-align: center;\r\n");
      out.write("    padding-top: 15px;\r\n");
      out.write("    \r\n");
      out.write("}\r\n");
      out.write("h4{\r\n");
      out.write("    text-align: center;\r\n");
      out.write("}\r\n");
      out.write("form{\r\n");
      out.write("    width: 700px;\r\n");
      out.write("\r\n");
      out.write("    margin: auto;\r\n");
      out.write("}\r\n");
      out.write("form label{\r\n");
      out.write("    display: flex;\r\n");
      out.write("    margin-top: 20px;\r\n");
      out.write("    font-size: 18px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("form input{\r\n");
      out.write("    width: 100%;\r\n");
      out.write("    padding: 0 5px;\r\n");
      out.write("    height: 40px;\r\n");
      out.write("    font-size: 16px;\r\n");
      out.write("    border: none;\r\n");
      out.write("    background: none;\r\n");
      out.write("    outline: none;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("input::placeholder {color:black;}\r\n");
      out.write("\r\n");
      out.write("input{background:white;}\r\n");
      out.write("\r\n");
      out.write("form input[type=\"text\"]{\r\n");
      out.write("    border-bottom: 1px solid #999;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"id\"]{\r\n");
      out.write("    border-bottom: 1px solid #999;\r\n");
      out.write("    margin-bottom: 10px;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"password\"]{\r\n");
      out.write("    border-bottom: 1px solid #999;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"email\"]{\r\n");
      out.write("    border-bottom: 1px solid #999;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"tel\"]{\r\n");
      out.write("    border-bottom: 1px solid #999;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"date\"]{\r\n");
      out.write("    border-bottom: 1px solid #999;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("form input[type=\"text\"]:focus{\r\n");
      out.write("    border-bottom: 3px solid white;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"id\"]:focus{\r\n");
      out.write("    border-bottom: 3px solid white;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"password\"]:focus{\r\n");
      out.write("    border-bottom: 3px solid white;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"email\"]:focus{\r\n");
      out.write("    border-bottom: 3px solid white;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"tel\"]:focus{\r\n");
      out.write("    border-bottom: 3px solid white;\r\n");
      out.write("}\r\n");
      out.write("form input[type=\"date\"]:focus{\r\n");
      out.write("    border-bottom: 3px solid white;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("form input[type=\"checkbox\"]{\r\n");
      out.write("    width: 20px;\r\n");
      out.write("    height: 20px;\r\n");
      out.write("    margin-top: 10px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("#enrollForm td:nth-child(1){text-align:right;}\r\n");
      out.write("	#enrollForm input{margin:3px;}\r\n");
      out.write("	\r\n");
      out.write("	#joinBtn{border-top-right-radius: 5px;\r\n");
      out.write("            border-bottom-right-radius: 5px;    \r\n");
      out.write("            margin-left:-3px}}\r\n");
      out.write("            \r\n");
      out.write("	#goMain{border-top-left-radius: 5px;\r\n");
      out.write("            border-bottom-left-radius: 5px;\r\n");
      out.write("            margin-right:-4px;}\r\n");
      out.write("</style>\r\n");
      out.write("    \r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("	\r\n");
      out.write("\r\n");
      out.write("	<div class=\"outer\">\r\n");
      out.write("		<br>\r\n");
      out.write("		<div class=\"loginbox\">\r\n");
      out.write("		\r\n");
      out.write("		<h1><img src=\"resources/images/isedol.png\"  width=158px\" alt=\"\" onclick=\"location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ pageContext.servletContext.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("';\"></h1>\r\n");
      out.write("		<h1>회원가입</h1>\r\n");
      out.write("        <h4>아래 정보를 기입해주세요</h4>\r\n");
      out.write("		<form id=\"enrollForm\" action=\"");
      out.print(request.getContextPath() );
      out.write("/insertMember.do\" method=\"post\" onsubmit=\"return joinValidate();\">\r\n");
      out.write("			<table>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td width=\"200px\" >* 아이디 :</td>\r\n");
      out.write("					<td><input type=\"text\" maxlength=\"13\" name=\"userId\" placeholder=\"아이디를 입력하세요\" required></td>\r\n");
      out.write("					\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>* 비밀번호 :</td>\r\n");
      out.write("					<td><input type=\"password\" maxlength=\"15\" name=\"userPwd\" placeholder=\"비밀번호를 입력하세요\" required></td>\r\n");
      out.write("					<td></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>* 비밀번호 확인 :</td>\r\n");
      out.write("					<td><input type=\"password\" maxlength=\"15\" name=\"checkPwd\" placeholder=\"비밀번호를 입력하세요\" required></td>\r\n");
      out.write("					<td><label id = \"pwdResult\"></label></td>\r\n");
      out.write("				</tr>	\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>* 이름 :</td>\r\n");
      out.write("					<td><input type=\"text\" maxlength=\"5\" name=\"userName\" placeholder=\"이름을 입력하세요\" required></td>\r\n");
      out.write("					<td></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>연락처 :</td>\r\n");
      out.write("					<td><input type=\"tel\" maxlength=\"11\" name=\"phone\" placeholder=\"(-없이)01012345678\"></td>\r\n");
      out.write("					<td></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>이메일 :</td>\r\n");
      out.write("					<td><input type=\"email\" name=\"email\" placeholder=\"이메일을 입력하세요\"></td>\r\n");
      out.write("					<td></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>주소 :</td>\r\n");
      out.write("					<td><input type=\"text\" name=\"address\" placeholder=\"주소를 입력하세요\"></td>\r\n");
      out.write("					<td></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("				<tr>\r\n");
      out.write("					<td>* 제일 좋아하는 멤버 :</td>\r\n");
      out.write("					<td>\r\n");
      out.write("						\r\n");
      out.write("						<input type=\"checkbox\" id=\"ine\" name=\"interest\" value=\"아이네\">\r\n");
      out.write("						<label for=\"ine\">아이네</label>\r\n");
      out.write("						\r\n");
      out.write("						<input type=\"checkbox\" id=\"jingburger\" name=\"interest\" value=\"징버거\">\r\n");
      out.write("						<label for=\"jingburger\">징버거</label>\r\n");
      out.write("						\r\n");
      out.write("						<input type=\"checkbox\" id=\"lilpa\" name=\"interest\" value=\"릴파\">\r\n");
      out.write("						<label for=\"lilpa\">릴파</label>\r\n");
      out.write("						\r\n");
      out.write("						<input type=\"checkbox\" id=\"jururu\" name=\"interest\" value=\"주르르\">\r\n");
      out.write("						<label for=\"jururu\">주르르</label>\r\n");
      out.write("						\r\n");
      out.write("						<input type=\"checkbox\" id=\"gosegu\" name=\"interest\" value=\"고세구\">\r\n");
      out.write("						<label for=\"gosegu\">고세구</label>\r\n");
      out.write("						\r\n");
      out.write("						<input type=\"checkbox\" id=\"vichan\" name=\"interest\" value=\"비챤\">\r\n");
      out.write("						<label for=\"vichan\">비챤</label>\r\n");
      out.write("						\r\n");
      out.write("						\r\n");
      out.write("					</td>\r\n");
      out.write("					<td></td>\r\n");
      out.write("				</tr>\r\n");
      out.write("			</table>\r\n");
      out.write("			<br>\r\n");
      out.write("			\r\n");
      out.write("			<div class=\"btns\" align=\"center\">\r\n");
      out.write("				<button type=\"button\" id=\"goMain\" onclick= \"location.href='");
      out.print( request.getContextPath());
      out.write("'\">메인으로</button>\r\n");
      out.write("				<button type=\"submit\" id=\"joinBtn\">가입하기</button>\r\n");
      out.write("				\r\n");
      out.write("			</div>\r\n");
      out.write("		</form>\r\n");
      out.write("	\r\n");
      out.write("	</div>\r\n");
      out.write("	</div>\r\n");
      out.write("	<script>\r\n");
      out.write("	\r\n");
      out.write("	function joinValidate(){\r\n");
      out.write("		\r\n");
      out.write("		if(!(/^[a-z][a-z\\d]{3,11}$/i.test($(\"#enrollForm input[name=userId]\").val()))){\r\n");
      out.write("			$(\"#enrollForm input[name=userId]\").focus();\r\n");
      out.write("	        return false;\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		if($(\"#enrollForm input[name=userPwd]\").val() != $(\"#enrollForm input[name=checkPwd]\").val()){\r\n");
      out.write("			$(\"#pwdResult\").text(\"비밀번호 불일치\").css(\"color\", \"red\");\r\n");
      out.write("			return false;			\r\n");
      out.write("		}\r\n");
      out.write("		\r\n");
      out.write("		 if(!(/^[가-힣]{2,}$/.test($(\"#enrollForm input[name=userName]\").val()))){\r\n");
      out.write("			 $(\"#enrollForm input[name=userName]\").focus();\r\n");
      out.write("	        return false;\r\n");
      out.write("		 }\r\n");
      out.write("		 \r\n");
      out.write("		 return true;\r\n");
      out.write("		\r\n");
      out.write("		\r\n");
      out.write("	}\r\n");
      out.write("	</script>\r\n");
      out.write("	\r\n");
      out.write("	\r\n");
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