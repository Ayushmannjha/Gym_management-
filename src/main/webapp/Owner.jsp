<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="error.jsp" %>  
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.sql.*"%>
<%
String name = (String) request.getSession().getAttribute("name");
%>
<% List<String[]> students = new ArrayList<>();
List<String[]> staffs = new ArrayList<>();
int studentRowCount = 0;
int staffRowCount = 0;
%>
<%
ServletContext context = request.getServletContext();
String className = context.getInitParameter("DriverClassName");
String url = context.getInitParameter("url");
String user = context.getInitParameter("user");
String pwd = context.getInitParameter("password");
// Initialize the list to store hired persons
 // Corrected variable name

try {
    // Load the database driver
    Class.forName(className);

    // Establish the database connection
    Connection connection = DriverManager.getConnection(url, user, pwd);

    // Create and execute the SQL query for hired persons
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * from staffinfo");

    // Iterate through the result set and add data to the hiredPersons list
    while (resultSet.next()) {
        staffRowCount++;
        String[] person = new String[4];
        person[0] = resultSet.getString(1);
        person[1] = resultSet.getString(2);
        person[2] = resultSet.getString(3);
        person[3] = resultSet.getString(4);
        staffs.add(person);
    }

    // Close the first result set
    resultSet.close();

    // Create and execute the SQL query for attendance
    resultSet = statement.executeQuery("SELECT id, name, dues_fee, addmission_date FROM student_data"); // Corrected table name

    // Iterate through the result set and add data to the attendance list
    while (resultSet.next()) {
        studentRowCount++;
        String[] student = new String[4];
        student[0] = resultSet.getString("id");
        student[1] = resultSet.getString("name");
        student[2] = resultSet.getString("dues_fee");
        student[3] = resultSet.getString("addmission_date");
        students.add(student); // Add student data to the attendance list
    }

    // Close the result set, statement, and connection
    resultSet.close();
    statement.close();
    connection.close();
} catch (Exception e) {
    e.printStackTrace();
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Owner Dashboard - Your Gym Name</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="styles.css" rel="stylesheet">
</head>
<body>
    <!-- Navbar -->
     <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <!-- Logo and brand -->
            <a class="navbar-brand" href="#">
                <img src="your-logo.png" alt="My Gym Logo" width="45" height="45" class="d-inline-block align-top" style="margin-right: 10px;">
               Admantine Gym 
            </a>
    
            <!-- Toggler icon (visible on small screens) -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
    
            <!-- Navigation links (visible on large screens) -->
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="index.html">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#Staffs">Staffs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#Students">Students</a>
                    </li>
                   
                </ul>
            </div>
        </div>
    </nav>
    
    
    

    <!-- Main Content -->
    <div class="container mt-4">
        <div class="row">
            
            <!-- Dashboard Content -->
            <div class="col-lg-9">
                <!-- Staff Management Section -->
                <div id = "Staffs" class="card mb-4">
                    <div class="card-header">
                        <h5 class="card-title">Staff Management (Number of Staff: <%= staffRowCount %>)</h5>
                    <p> <a href="addStaff.jsp">Add staff</a></p>
                    </div>
                    <div class="card-body">
                        <!-- Add your staff management features here -->
                        <table class="table">
    <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th> <!-- Add Actions column for edit and delete icons -->
        </tr>
    </thead>
    <tbody>
        <!-- Iterate through staff data and create table rows -->
        <% for (String[] staff : staffs) { %>
        <tr>
            <td><%= staff[0] %></td>
            <td><%= staff[1] %></td>
            <td><%= staff[2] %></td>
            <td><%= staff[3] %></td>
            <!-- Actions column with edit and delete icons -->
            <td>
                <!-- Edit icon with link to edit staff -->
                <a href="edit.jsp?id=<%= staff[0] %>&name=<%=staff[1]%>&role=<%=staff[2]%>&password=<%=staff[3]%>">
    <i class="fas fa-edit"></i>
    Edit
</a>

                <!-- Delete icon with link to delete staff (You may want to use a confirmation dialog) -->
                <a href="DeleteStaff?id=<%= staff[0] %>">
                    <i class="fas fa-trash"></i>
                </a>
            </td>
        </tr>
        <% } %>
    </tbody>
</table>

                    </div>
                </div>
                <!-- Student Management Section -->
                <div class="card mb-4">
                    <div id= "Students" class="card-header">
                        <h5 class="card-title">Student Management (Number of student: <%= studentRowCount %>)</h5>
                    
                    </div>
                    <div class="card-body">
                        <!-- Add your student management features here -->
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Dues Fee</th>
                                    <th>Admission Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (String[] student : students) { %>
                                    <tr>
                                        <td><%= student[0] %></td>
                                        <td><%= student[1] %></td>
                                        <td><%= student[2] %></td>
                                        <td><%= student[3] %></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
               
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="py-4 bg-dark text-white text-center">
        <div class="container">
            <p>&copy; 2024 Gym Management. All rights reserved.</p>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
