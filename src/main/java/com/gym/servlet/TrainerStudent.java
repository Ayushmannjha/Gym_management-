package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TrainerStudent
 */
@WebServlet("/TrainerStudent")
public class TrainerStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainerStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		String query1 = "select id , name, membership from student_data where id = ? ";
		//String query2 = "insert into  ? values(?,?,?)";
		ServletContext context = request.getServletContext();
		String className = context.getInitParameter("DriverClassName");
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String pwd = context.getInitParameter("password");
		Connection con = null;
	    PreparedStatement pst = null; // Set the parameter for the student ID
	    ResultSet rs = null;
		try {
		   con = DriverManager.getConnection(url, user, pwd);
		     pst = con.prepareStatement("SELECT name, id, membership FROM student_data WHERE id = ?");
		    pst.setInt(1, id); // Set the parameter for the student ID
		    rs = pst.executeQuery();

		    if (rs.next()) {
		        String name = rs.getString("name");
		        int roll = rs.getInt("id");
		        int membership = rs.getInt("membership");
		        request.getSession().setAttribute("membership", membership);
		        pst.close();
		        System.out.println(membership);
		        String tableName = capitalizeWords(name + roll);
		        System.out.println(tableName);
		        
		        // ArrayList to store data from the student's table
		        ArrayList<Integer> presentData = new ArrayList<Integer>();
		        try {
		            // Construct and execute the query for the student's table
		            String tableQuery = "SELECT * FROM " + tableName;
		            pst = con.prepareStatement(tableQuery);
		            rs = pst.executeQuery();

		            // Process the results from the student's table
		            while (rs.next()) {
		                int presentDay = rs.getInt(2); // Assuming the data is in the second column
		                presentData.add(presentDay);
		            }
		        } catch (SQLException e) {
		            System.out.println(e);
		        }

		        // Set attributes for forwarding to JSP
		        request.setAttribute("StudentName", name);
		        request.setAttribute("tableName", tableName);
		        request.getSession().setAttribute("presentData", presentData);
		        request.setAttribute("id", id);
		        request.getRequestDispatcher("TrainerStudent.jsp").forward(request, response);
		    } else {
		        // Student not found
		        request.setAttribute("StudentStatus", "Student not found");
		        request.getRequestDispatcher("Trainer.jsp").forward(request, response);
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		    // Handle database errors and display error message
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
		} finally {
		    // Close resources in a finally block to ensure they are always closed
		    try {
		        if (rs != null) {
		            rs.close();
		        }
		        if (pst != null) {
		            pst.close();
		        }
		        if (con != null) {
		            con.close();
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
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
