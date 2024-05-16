<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.sql.*"%>
<%
ServletContext context = request.getServletContext();
String className = context.getInitParameter("DriverClassName");
String url = context.getInitParameter("url");
String user = context.getInitParameter("user");
String pwd = context.getInitParameter("password");
int roll = Integer.parseInt(request.getParameter("roll"));
String name = "";
double paidFee = 0.0;
double duesFee = 0.0;
try{
    Class.forName(className);
    Connection con = DriverManager.getConnection(url, user, pwd);
    PreparedStatement pst = con.prepareStatement("select name, paid_fee, dues_fee from student_data where id = ?");
    pst.setInt(1,roll);
    ResultSet rs = pst.executeQuery();
    if(rs.next()){
        name = rs.getString("name");
        paidFee = rs.getDouble("paid_fee");
        duesFee = rs.getDouble("dues_fee");
    }
}catch(Exception e){
    e.printStackTrace();
}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pay Dues</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Pay Dues</h2>
        <div class="mt-3">
            <p>Name: <%= name %></p>
            <p>Paid Amount: <%= paidFee %></p>
            <p>Dues: <%= duesFee %></p>
            <!-- Form to input paying amount -->
            <form action="PayDuesServlet" method="post">
                <div class="mb-3">
                    <label for="inputPayingAmount" class="form-label">Paying Amount</label>
                    <input required="required" name="payingAmount" type="number" class="form-control" id="inputPayingAmount" placeholder="Enter Paying Amount" required>
                </div>
                <!-- Hidden input to pass roll number to servlet -->
                
                <input type="hidden" name="roll" value="<%= roll %>">
                			
               	<%if(duesFee>0) {%>
                <button type="submit" class="btn btn-primary">Pay Now</button>
            	<%} %>
            </form>
        </div>
    </div>
</body>
</html>
