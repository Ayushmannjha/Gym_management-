package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteQuery
 */
@WebServlet("/DeleteQuery")
public class DeleteQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = request.getParameter("phone");
		ServletContext context = request.getServletContext();
		String className = context.getInitParameter("DriverClassName");
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String pwd = context.getInitParameter("password");
		System.out.println(phone);
		try {
			Class.forName(className);
			Connection con = DriverManager.getConnection(url,user,pwd);
			PreparedStatement pst = con.prepareStatement("delete from query where phone=?");
			pst.setString(1, phone);
			int status = pst.executeUpdate();
			if(status>0) {
				System.out.println("deleted");
				request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    out.println("<html><head><title>Error Page</title></head><body>");
		    out.println("<div class=\"container mt-4\">");
		    out.println("<div class=\"row\">");
		    out.println("<div class=\"col-md-6 offset-md-3\">");
		    out.println("<div class=\"card\">");
		    out.println("<div class=\"card-header\">Error</div>");
		    out.println("<div class=\"card-body\">");
		    out.println("<p style=\"color: red;\">An error occurred: " + e.getMessage() + "</p>");
		    out.println("<a href=\"index.html\" class=\"btn btn-primary\">Go back to Home</a>");
		    out.println("</div></div></div></div></div>");
		    out.println("</body></html>");
		}

	}

}
