package com.gym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Student_Login
 */
@WebServlet("/Student_Login")
public class Student_Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Student_Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		String query1 = "select id , name, membership from student_data where id = ? AND phone = ?";
		//String query2 = "insert into  ? values(?,?,?)";
		ServletContext context = request.getServletContext();
		String className = context.getInitParameter("DriverClassName");
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String pwd = context.getInitParameter("password");
		try {
			Class.forName(className);
			Connection con = DriverManager.getConnection(url,user,pwd);
			PreparedStatement pst = con.prepareStatement(query1);
			pst.setInt(1, id);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			//pst.close();
			if(rs.next()) {
				String name = rs.getString("name");
				int roll = rs.getInt("id");
				int membership = rs.getInt("membership");
				request.getSession().setAttribute("membership", membership);
				pst.close();
				System.out.println(membership);
				String tableName = name+""+roll;
				tableName = capitalizeWords(tableName);
				System.out.println(tableName);
				ArrayList<Integer> presentData = new ArrayList<Integer>();
				try {
				pst = con.prepareStatement("select * from "+tableName);
				
				rs = pst.executeQuery();
				}catch(Exception e) {
					System.out.println(e);
				}
				while(rs.next()) {
					int presentDay = rs.getInt(2);
					presentData.add(presentDay);
				}
				request.getSession().setAttribute("presentData", presentData);
				request.getRequestDispatcher("student.jsp").forward(request, response);
			}
			else {
				request.setAttribute("StudentStatus", "Student not found");
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
