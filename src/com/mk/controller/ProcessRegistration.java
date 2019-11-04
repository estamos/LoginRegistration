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
				out.println("User with username "+username+" already exist.");
				rd1.include(request, response);
			}else {
				if(!password.equals(confirmPass)) {
					out.println("Password and confirm password not match");
					rd1.include(request, response);
				}else {
					dao.registerUser(firstname, lastname, username, password);
					out.println("User Registered successfully");
					rd2.include(request, response);
				}
			}
		} catch (Exception e) {
			out.println("Your request cannot be processed at this time, please try later.");
		}
		
	}

}
