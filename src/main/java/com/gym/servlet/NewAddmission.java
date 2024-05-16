package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewAddmission
 */
@WebServlet("/NewAddmission")
public class NewAddmission extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAddmission() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//taking inputs
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		int fees = Integer.parseInt(request.getParameter("fees"));
		double fee  = 0.0;
		if(fees==1) {
			//System.out.println("hai");
			fee = 1100.0;
		}
		else if(fees==6) {
			fee = 6000.0;
		}
		else {
			//System.out.println("hai");
			fee = 11000.0;
		}
		System.out.println(name+" "+phone+ " "+email+" "+fee);
		ServletContext context = request.getServletContext();
		String className = context.getInitParameter("DriverClassName");
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String pwd = context.getInitParameter("password");
		 double paying_amount = Double.parseDouble(request.getParameter("paid"));
		try {
			Class.forName(className);
			Connection con = DriverManager.getConnection(url,user,pwd);
			PreparedStatement pst = con.prepareStatement("insert into student_data values(?,?,?,?,?,?,?,?,?)");
			Random random = new Random();

	        // Generating a random number
	        int randomNumber = random.nextInt();
	        if(randomNumber<0) {
	        	randomNumber = randomNumber*(-1); 
	        }
			//int random = (int) Math.random();
	        Date d = new Date();
	        long l = d.getTime();
	        java.sql.Date date = new java.sql.Date(l);
			pst.setInt(1, randomNumber);
			pst.setString(2, name);
			pst.setString(3, phone);
			pst.setString(4, email);
			pst.setDouble(5, paying_amount);
			pst.setDouble(6, fee-paying_amount);
			pst.setDate(7, date);
			pst.setDate(8, date);
			pst.setInt(9, fees);
			int status = pst.executeUpdate();
			if(status>0) {
				System.out.println("addmissin done");
				request.setAttribute("done", 1);
				System.out.println(randomNumber);
				request.setAttribute("Id", randomNumber);
				request.setAttribute("password", phone);
				Statement st =  con.createStatement();
				name = capitalizeWords(name);
				String sql = "CREATE TABLE `gymmanagementsystem`.`" + name + randomNumber + "` ("
			            + "`days` INT NOT NULL AUTO_INCREMENT,"
			            + "`present` INT NULL,"
			            + "`spendedTime` VARCHAR(400) NULL,"
			            + "PRIMARY KEY (`days`))";

			st.executeUpdate(sql);

				
				request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("done", 0);
			request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
			
		
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
