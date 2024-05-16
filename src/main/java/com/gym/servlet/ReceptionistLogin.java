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
 * Servlet implementation class ReceptionistLogin
 */
@WebServlet("/ReceptionistLogin")
public class ReceptionistLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ReceptionistLogin() {
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
        int id = Integer.parseInt(request.getParameter("email"));
        String password = request.getParameter("password");
       
        try {
            Class.forName(className);
            Connection con = DriverManager.getConnection(url, user, pw);
            PreparedStatement pst = con.prepareStatement("SELECT name, role FROM staffinfo WHERE id=? AND password=?");
            pst.setInt(1, id);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String role = rs.getString("role");
                if(!role.equalsIgnoreCase("receptionist")) {
                	request.setAttribute("errorMessage", "Invalid credentials!");
                	 request.getRequestDispatcher("login.jsp").forward(request, response);

                }
                // Create or retrieve session
                request.getSession().setAttribute("name", name);
                request.setAttribute("role", role);
                request.setAttribute("done", "nothing");
                request.setAttribute("attendeceEntry", 0);
                request.setAttribute("Id", 0);
                System.out.println(name);
                request.getRequestDispatcher("receptionistWork.jsp").forward(request, response);
            } else {
                System.out.println("Invalid credentials");
                // Set error message and redirect to login page
                request.setAttribute("errorMessage", "Invalid credentials!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
//orward to login page with an error message
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
