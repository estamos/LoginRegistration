package com.mk.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import com.mk.dao.UserDao;

/**
 * Servlet implementation class ProcessLogin
 */
@WebServlet("/processLogin")
public class ProcessLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		HttpSession session=request.getSession();
		PrintWriter out=response.getWriter();
		
		UserDao dao=new UserDao();
		RequestDispatcher rd1=request.getRequestDispatcher("Home.jsp");
		RequestDispatcher rd2=request.getRequestDispatcher("login.jsp");

		try {
			boolean authenticate=dao.authenticate(username, password);
			if(authenticate) {
				session.setAttribute("username", username);
				rd1.forward(request, response);	
			}
			else {
				rd2.include(request, response);
				out.println("<span style='color:red'>Invalid username or password.</span>");
			}
	
		} catch (Exception e) {
			out.println("<span style='color:red'>Your request cannot be processed | the web application is not working properly , probably the virtual machine or MySQL database is not running.</span>");
		}
	}

}
