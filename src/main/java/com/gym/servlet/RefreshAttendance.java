package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RefreshAttendance
 */
@WebServlet("/RefreshAttendance")
public class RefreshAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshAttendance() {
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
        ArrayList<String> total_Student = new ArrayList<String>();
        ArrayList<String> present_Student = new ArrayList<String>();
        try {
            Connection con = DriverManager.getConnection(url,user,pwd);
            java.sql.Statement st = con.createStatement();
            Date d = new Date();
            if (d.getHours() > 11) {
                System.out.println("Deleting records from attendance table...");
                int status = st.executeUpdate("delete from attendence");
                System.out.println("Deletion status: " + status);
                PreparedStatement pst = con.prepareStatement("select id, name from student_data");
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    String name = rs.getString(2)+""+rs.getInt(1);
                    name = capitalizeWords(name);
                    total_Student.add(name);
                }
                pst.close();
                pst = con.prepareStatement("select roll, name from attendence");
                rs = pst.executeQuery();
                while(rs.next()) {
                    present_Student.add(rs.getString(2)+""+rs.getInt(1));
                }
                pst.close();
                
                ArrayList<String> absent_Student = getAbsentStudents(total_Student, present_Student);

                System.out.println(absent_Student);
                insertRowForStudent(con,absent_Student);

                request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
          
            }
            else {
            	request.setAttribute("time", "this will work after 11am");

                request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
          
            }
            
                 
        }catch (Exception e) {
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


    public ArrayList<String> getAbsentStudents(ArrayList<String> totalStudents, ArrayList<String> presentStudents) {
        ArrayList<String> absentStudents = new ArrayList<String>();

        // Iterate through total students
        for (String student : totalStudents) {
            // If the student is not present, add them to the absent students list
            if (!presentStudents.contains(student)) {
                absentStudents.add(student);
            }
        }

        return absentStudents;
    }

    public String capitalizeWords(String input) {
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

    public void insertRowForStudent(Connection connection, ArrayList<String> students) throws SQLException {
        // Prepare SQL statement for inserting a row for the student

        for(String studentName: students) {
            String tableName = studentName ; // Adjust table name according to your database schema
            String sql = "INSERT INTO " + tableName + " (present) VALUES (?)"; // Adjust column names accordingly

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Set values for the columns
                statement.setInt(1, 0);
                // Set values for other columns as needed

                // Execute the SQL statement
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A row has been inserted for " + studentName);
                }
            }
        }
    }

}
