package com.mk.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mk.dao.UserDao;

/**
 * Servlet implementation class ProcessRegistration
 */
@WebServlet("/processRegistration")
public class ProcessRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String confirmPass=request.getParameter("confirmPass");
		
		RequestDispatcher rd1=request.getRequestDispatcher("register.jsp");
		RequestDispatcher rd2=request.getRequestDispatcher("login.jsp");
		PrintWriter out=response.getWriter();
		
		UserDao dao = new UserDao();
		try {
			boolean isExist=dao.isExist(username);
			if(isExist) {
				rd1.include(request, response);
				out.println("<span style='color:red'>User with username "+username+" already exists.</span>");
			}else {
				if(username.equals("") | password.equals("")) {
					rd1.include(request, response);
					out.println("<span style='color:red'>Username and Password should not be null.</span>");
				}
				else if(!password.equals(confirmPass)) {
					rd1.include(request, response);
					out.println("<span style='color:red'>Password and Confirm Password do not match.</span>");
				}
				else {
					dao.registerUser(firstname, lastname, username, password);
					rd2.include(request, response);
					out.println("<span style='color:green'>User Registered successfully</span>");
				}
			}
		} catch (Exception e) {
			//e.printStackTrace(out);
			out.println("<span style='color:red'>Your request cannot be processed | the web application is not working properly , probably the virtual machine or MySQL database is not running.</span>");
		}
		
	}

}
