package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AttendenceExit
 */
@WebServlet("/AttendenceExit")
public class AttendenceExit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendenceExit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int roll = Integer.parseInt(request.getParameter("id"));
		ServletContext context = request.getServletContext();
		String className = context.getInitParameter("DriverClassName");
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String pwd = context.getInitParameter("password");
		try {
		    Connection con = DriverManager.getConnection(url, user, pwd);

		    // Query to fetch the entry time from the database based on roll
		    PreparedStatement pst = con.prepareStatement("SELECT roll, name,entry_time FROM attendence WHERE roll = ?");
		    pst.setInt(1, roll); // Set the roll parameter
		    ResultSet rs = pst.executeQuery();

		    if (rs.next()) {
		        // Get the entry time from the database as a String
		        String entryTimeString = rs.getString("entry_time");
		        int rollN = Integer.parseInt(rs.getString("roll"));
		        String name = rs.getString("name");
		        String tableName = capitalizeWords(name+""+rollN);
		        // Parse the entry time string into a java.util.Date object
		        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		        Date entryDate = sdf.parse(entryTimeString);

		        // Convert the java.util.Date object to a java.sql.Time object
		        Time entryTime = new Time(entryDate.getTime());
		        System.out.println(entryTime);	
		        // Calculate the current time
		        Time currentTime = new Time(System.currentTimeMillis());
		        System.out.println(currentTime);	
		        // Calculate the time difference
		        long timeDifferenceInMillis = currentTime.getTime() - entryTime.getTime();
		        System.out.println(currentTime.getTime());		        // Convert milliseconds to hours
		        long hours = timeDifferenceInMillis % (1000 * 60 * 60); // Milliseconds to hours conversion
		        System.out.println(hours);
		        // Convert milliseconds to remaining minutes
		        long remainingMinutesInMillis = timeDifferenceInMillis % (1000 * 60 * 60);
		        long minutes = remainingMinutesInMillis / (1000 * 60); // Remaining milliseconds to minutes conversion

		        // Convert milliseconds to remaining seconds
		        long remainingSecondsInMillis = remainingMinutesInMillis % (1000 * 60);
		        long seconds = remainingSecondsInMillis / 1000; // Remaining milliseconds to seconds conversion

		        System.out.println( " Minutes: " + minutes + ", Seconds: " + seconds);

		        // Now you can use the time difference in your code as needed

		        // Update exit time in the database
		        PreparedStatement pst1 = con.prepareStatement("UPDATE attendence SET exit_time = ? WHERE roll = ?");
		        pst1.setString(1, currentTime.toString());
		      // pst.setString(2, "Hours: " + hours + ", Minutes: " + minutes + ", Seconds: " + seconds);
		       pst1.setInt(2, roll);
		       int status = pst1.executeUpdate();
		       pst1.close();
		       pst1 = con.prepareStatement("UPDATE "+tableName+" Set spendedTime=?");
		       pst1.setString(1,  " Minutes: " + minutes + ", Seconds: " + seconds);
		       pst1.executeUpdate();
		        if (status > 0) {
		            request.setAttribute("attendanceEntry", 2);
		        } else {
		            request.setAttribute("attendanceEntry", 3);
		        }
		        request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
		    } else {
		        // No entry time found for the roll
		        request.setAttribute("attendanceEntry", 3);
		        request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
		    }
		} catch (Exception e) {
		    // Handle exceptions
		    e.printStackTrace();
		    // Print error message
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
