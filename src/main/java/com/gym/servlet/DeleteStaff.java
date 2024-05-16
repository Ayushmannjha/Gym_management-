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
 * Servlet implementation class DeleteStaff
 */
@WebServlet("/DeleteStaff")
public class DeleteStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		String className = context.getInitParameter("DriverClassName");
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String pwd = context.getInitParameter("password");
		 try (Connection conn = DriverManager.getConnection(url, user, pwd)) {
			 String id = request.getParameter("id");
	            String sql = "DELETE FROM staffinfo WHERE id=?";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, id);

	            int rowsDeleted = statement.executeUpdate();
	            if (rowsDeleted > 0) {
	                // If the deletion was successful, redirect to a success page
	                response.sendRedirect("Owner.jsp");
	            } else {
	                // If no rows were deleted, display an error message
	                response.getWriter().println("Failed to delete staff information.");
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


