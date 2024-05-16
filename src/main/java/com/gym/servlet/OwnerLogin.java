package com.gym.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OwnerLogin
 */
@WebServlet("/OwnerLogin")
public class OwnerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnerLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("id");
		String password = request.getParameter("password");
		if(name.equalsIgnoreCase("ayush")&&password.equalsIgnoreCase("hello")) {
			request.getRequestDispatcher("Owner.jsp").forward(request, response);
		}
		else {
			request.setAttribute("OwnerLogin", "invalid credential");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
