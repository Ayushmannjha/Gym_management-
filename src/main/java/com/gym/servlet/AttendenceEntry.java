package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AttendenceEntry")
public class AttendenceEntry extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AttendenceEntry() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roll = Integer.parseInt(request.getParameter("id"));
        ServletContext context = request.getServletContext();
        String className = context.getInitParameter("DriverClassName");
        String url = context.getInitParameter("url");
        String user = context.getInitParameter("user");
        String pwd = context.getInitParameter("password");
        try {
            Connection con = DriverManager.getConnection(url, user, pwd);
            PreparedStatement pst = con.prepareStatement("select name, dues_fee from student_data where id=?");
            pst.setInt(1, roll);
            
            ResultSet retrieved = pst.executeQuery();
            if (retrieved.next()) {
                String tableName = retrieved.getString(1) + "" + roll; // Modified table name format
                tableName = capitalizeWords(tableName);
                double dues = retrieved.getDouble(2);
                PreparedStatement pst1 = con.prepareStatement(
                        "insert into attendence(roll, name, entry_time, dues) values(?,?,?,?)");
                pst1.setInt(1, roll);
                pst1.setString(2, retrieved.getString(1));
                Time timeValue = new Time(System.currentTimeMillis());
                String time = timeValue.toString();
                pst1.setString(3, time);
                pst1.setDouble(4, dues);
                int status = pst1.executeUpdate();
                pst1.close();

                // Insert into student-specific attendance table
                pst = con.prepareStatement("insert into " + tableName + "(present) values(?)");
                pst.setInt(1, 1);
                int stat = pst.executeUpdate();
                System.out.println(stat);
                pst.close();

                request.setAttribute("attendanceEntry", 1);
                
                
                request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
            } else {
                request.setAttribute("attendanceEntry", 4);
                request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            request.setAttribute("attendanceEntry", 5);
            request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
            e.printStackTrace();
			response.sendRedirect("error.html");
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
    public  String capitalizeWords(String input) {
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        // If there is only one word, return it as is
        if (words.length == 1) {
            return input;
        }

        for (String word : words) {
            // Capitalize the first character of each word and append to the result
            result.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1));
        }

        return result.toString();
    }
}
