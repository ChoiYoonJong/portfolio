package com.uni.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.uni.common.wrapper.EncryptWrapper;

/**
 * Servlet Filter implementation class EncryptFilter1
 */
//@WebFilter(filterName = "encryptFilter",urlPatterns={"/loginMember.do","/insertMember.do","/updatePwd.do"})
public class EncryptFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncryptFilter() {
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if(request.getParameter("newPwd") == null) {
			request.setAttribute("originPwd", request.getParameter("userPwd"));
			
			System.out.println("변경전 originPwd : " + request.getParameter("userPwd"));
		}else {
			request.setAttribute("originPwd", request.getParameter("newPwd"));
			
			System.out.println("변경전 newPwd : " + request.getParameter("newPwd"));
		}
		
		EncryptWrapper encRequest = new EncryptWrapper((HttpServletRequest)request);
		
		if(request.getParameter("newPwd") == null) {
			
			
			System.out.println("변경후 originPwd : " + encRequest.getParameter("userPwd"));
		}else {
			
			
			System.out.println("변경후 newPwd : " + encRequest.getParameter("newPwd"));
		}
		
		chain.doFilter(encRequest, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
