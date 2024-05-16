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

@WebServlet("/AddStaff")
public class AddStaff extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        String className = servletContext.getInitParameter("DriverClassName");
        String url = servletContext.getInitParameter("url");
        String user = servletContext.getInitParameter("user");
        String pw = servletContext.getInitParameter("password");

        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        try {
            // Load the database driver
            Class.forName(className);

            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, user, pw);

            // Prepare the SQL statement
            String sql = "INSERT INTO staffinfo (name, role, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the parameters for the SQL statement
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, role);
            preparedStatement.setString(3, password);

            // Execute the SQL statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Close the statement and connection
            preparedStatement.close();
            connection.close();

            // Redirect to a success page
            response.sendRedirect("Owner.jsp");
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
