package com.uni.notice.controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uni.notice.model.service.NoticeService;
import com.uni.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateFormServlet
 */
@WebServlet("/updateFormNotice.do")
public class NoticeUpdateFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int nno = Integer.parseInt(request.getParameter("nno"));// int 형식을 string 방식으로 getParameter 로 해서 nno을 받아준다.
		
		Notice notice = new NoticeService().selectNotice(nno); //Notice 객체를 notice에 담은후 NoticeService에 nno를 보낸다.
		
		RequestDispatcher page = null; // 화면 출력
		if(notice != null) { // 만약 notice 가 null이 아니면 화면을 불러온다. 
			request.setAttribute("notice", notice); 
			page = request.getRequestDispatcher("views/notice/noticeUpdateForm.jsp");
			
		}else {
			request.setAttribute("msg", "공지사항 수정 하기를 진행할수 없습니다.");
			page = request.getRequestDispatcher("views/common/errorPage.jsp");
		}
		page.forward(request, response);//원래 화면으로 돌아간다.
	}




	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				doGet(request, response);
			}

		}