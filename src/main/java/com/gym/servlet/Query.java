package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Query
 */
@WebServlet("/Query")
public class Query extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Query() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		int fees = Integer.parseInt(request.getParameter("membership"));
		System.out.println(name+"  "+phone+" "+fees  );
		String fee  = "";
		if(fees==1) {
			fee = "1Month";
		}
		else if(fees==6) {
			fee = "6Month";
		}
		else {
			fee = "12Month";
		}
		ServletContext context = request.getServletContext();
		String className = context.getInitParameter("DriverClassName");
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String pwd = context.getInitParameter("password");
		
		try {
			Class.forName(className);
			Connection con = DriverManager.getConnection(url,user,pwd);
			PreparedStatement pst = con.prepareStatement("insert into query(name, phone, membership) values(?,?,?)");
			pst.setString(1, name);
			pst.setString(2, phone);
			pst.setString(3, fee);
			int status = pst.executeUpdate();
			if(status>0) {
				System.out.println("inserted");
				request.setAttribute("registrationStatus", 1);
				request.getRequestDispatcher("registration.jsp").forward(request, response);
			}
		}
		catch(SQLIntegrityConstraintViolationException e){
			request.setAttribute("registrationStatus", 2);
			request.getRequestDispatcher("registration.jsp").forward(request, response);
			
		}
		catch (Exception e) {
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
