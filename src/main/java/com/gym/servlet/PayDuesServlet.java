package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PayDuesServlet")
public class PayDuesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Retrieve parameters from the request
            int roll = Integer.parseInt(request.getParameter("roll"));
            double payingAmount = Double.parseDouble(request.getParameter("payingAmount"));

            // Database connection parameters
            String className = getServletContext().getInitParameter("DriverClassName");
            String url = getServletContext().getInitParameter("url");
            String user = getServletContext().getInitParameter("user");
            String password = getServletContext().getInitParameter("password");

            // Load the database driver
            Class.forName(className);

            // Establish the database connection
            connection = DriverManager.getConnection(url, user, password);

            // Retrieve current dues amount
            pstmt = connection.prepareStatement("SELECT dues_fee, paid_fee FROM student_data WHERE id = ?");
            pstmt.setInt(1, roll);
            
            rs = pstmt.executeQuery();

            if (rs.next()) {
                double currentDues = rs.getDouble("dues_fee");

                double paidFee = rs.getDouble("paid_fee");
                
                paidFee = paidFee+payingAmount;
                currentDues = currentDues-payingAmount;
                // Update student data with new dues and paid amount
                pstmt = connection.prepareStatement("UPDATE student_data SET dues_fee = ?, paid_fee = ? WHERE id = ?");
                pstmt.setDouble(1, currentDues);
                pstmt.setDouble(2, paidFee);
                pstmt.setInt(3, roll);
                int rowsUpdated = pstmt.executeUpdate();

                if (rowsUpdated > 0) {
                    request.setAttribute("PayingDues", payingAmount);
                    request.getRequestDispatcher("clearDues.jsp").forward(request, response);
                } else {
                	request.setAttribute("PayingDues", 0);
                	request.getRequestDispatcher("clearDues.jsp").forward(request, response);
                }
            } else {
                out.println("<html><body><h3>Student with ID " + roll + " not found!</h3></body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            out = response.getWriter();
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
 finally {
            // Close JDBC objects
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
