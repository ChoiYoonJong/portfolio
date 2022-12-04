package com.uni.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uni.member.model.service.MemberService;
import com.uni.member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginMember.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		System.out.println(userId);
		String userPwd = request.getParameter("userPwd");
		//String originPwd = request.getParameter("userPwd");
		String originPwd = (String)request.getAttribute("originPwd");
		Member loginUser = new MemberService().loginMember(userId, userPwd);
		System.out.println("loginUser" + loginUser);
		
		
		if(loginUser != null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("originPwd", originPwd);
			
			response.sendRedirect(request.getContextPath());
			
			
		}else {
			request.setAttribute("msg", "로그인에 실패하였습니다.	");
			
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
