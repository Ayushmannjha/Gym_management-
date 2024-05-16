package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TrainerLogin
 */
@WebServlet("/TrainerLogin")
public class TrainerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainerLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ServletContext servletContext = request.getServletContext();

	        String className = servletContext.getInitParameter("DriverClassName");
	        String url = servletContext.getInitParameter("url");
	        String user = servletContext.getInitParameter("user");
	        String pw = servletContext.getInitParameter("password");
	        //HttpSession session = request.getSession(true); 
	        int id = Integer.parseInt(request.getParameter("id"));
	        String password = request.getParameter("password");
	        try {
	        	 Class.forName(className);
	             Connection con = DriverManager.getConnection(url, user, pw);
	             PreparedStatement pst = con.prepareStatement("SELECT name, role FROM staffinfo WHERE id=? AND password=?");
	             pst.setInt(1, id);
	             pst.setString(2, password);
	             ResultSet rs = pst.executeQuery();
	             if(rs.next()){
	            	 String name = rs.getString("name");
	            	 String role = rs.getString("role");
	            	 System.out.println(name+""+role);
	            	 if(!role.equalsIgnoreCase("trainer")) {
	                 	request.setAttribute("TerrorMessage", "Invalid credentials!");
	                 	 request.getRequestDispatcher("login.jsp").forward(request, response);

	                 }
	            	 else {
	            	 request.setAttribute("status", 1);
	            	 request.getSession().setAttribute("TrainerName", name);
	            	 request.getRequestDispatcher("Trainer.jsp").forward(request, response);
	        }     
	        }
	             else {
	            	 request.setAttribute("TerrorMessage", "tainer Not found");
	            	 request.getRequestDispatcher("login.jsp").forward(request, response);
	  	           
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
