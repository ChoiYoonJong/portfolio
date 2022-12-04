package com.uni.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uni.member.model.vo.Member;
import com.uni.notice.model.service.NoticeService;
import com.uni.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertServlet
 */
@WebServlet("/insertNotice.do")
public class NoticeInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = String.valueOf(((Member)request.getSession().getAttribute("loginUser")).getUserNo());
		System.out.println("______전______" + content);
		Notice n = new Notice(title, writer, content.replaceAll("\n", "<br>"));
		System.out.println("______후______" + content.replaceAll("\n", "<br>"));
		
		int result = new NoticeService().insertNotice(n);
		
		if(result>0) {
			request.getSession().setAttribute("msg", "공지사항이 성공적으로 등록되었습니다.");
			response.sendRedirect("listNotice.do");
		}else {
			request.setAttribute("msg", "공지사항등록실패");
			request.getRequestDispatcher("view/common/errorPage.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
