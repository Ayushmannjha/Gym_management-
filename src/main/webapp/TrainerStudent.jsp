<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*"%>
    <%@ page import="com.google.gson.Gson"%>
    <%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page errorPage="error.jsp" %>  
<%@ page import="java.sql.*"%>
    <%
String name = (String) request.getSession().getAttribute("name");
%>

<%
ServletContext context = request.getServletContext();
String className = context.getInitParameter("DriverClassName");
String url = context.getInitParameter("url");
String user = context.getInitParameter("user");
String pwd = context.getInitParameter("password");
// Initialize the list to store hired persons

List<String[]> performances = new ArrayList<>();
String studentName= (String)request.getAttribute("StudentName");
try {
	// Load the database driver
	Class.forName(className);
	String  tableName = (String)request.getAttribute("tableName");
	// Establish the database connection
	Connection con = DriverManager.getConnection(url, user, pwd);
	PreparedStatement pst = con.prepareStatement("select * from "+tableName );
	System.out.println(tableName);
	ResultSet rs = pst.executeQuery();
	while(rs.next()){
		String[] performance = new String[3];
		performance[0] = rs.getString(1);
		performance[1] = rs.getString(2);
		performance[2] = rs.getString(3);
		performances.add(performance);
	}
	
} catch (Exception e) {
	e.printStackTrace();
}
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bootstrap Carousel with Boxes</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .box-card {
            width: 80%;
            max-width: 600px;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 20px;
            margin: auto;
        }

        .box {
            width: 30px;
            height: 30px;
            background-color: #ccc;
            border: 1px solid #999;
            margin: 5px;
            display: inline-block;
            text-align: center;
            line-height: 30px;
        }
    </style>
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <!-- Logo and brand -->
            <a class="navbar-brand" href="#">
                <img src="your-logo.png" alt="My Gym Logo" width="45" height="45" class="d-inline-block align-top">
                Admantine Gym
            </a>

            <!-- Toggler icon (visible on small screens) -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Navigation links (visible on large screens) -->
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="index.html">Home</a>
                    </li>
                    
                   
                </ul>
            </div>
        </div>
    </nav>
    
    
    <!-- End Navbar -->

    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
            <!-- Single Slide with All Boxes -->
            <div class="carousel-item active">
                <div class="box-card">
                    <!-- Boxes Container -->
                    <div class="boxes-container">
                        <!-- Generate 360 boxes -->
                        <!-- 12 slides x 30 boxes per slide = 360 boxes -->
                        <!-- Each box is labeled with a number -->
                        <!-- Start from 1 and increment by 1 for each box -->
                        <!-- You can adjust the number of boxes per slide here -->
                        <!-- You can also style the boxes further if needed -->
                        <!-- This part is generated dynamically using JavaScript -->
                    </div>
                </div>
            </div>
        </div>
    </div>
<div class="col-md-12">
					<div class="card">
						<h4 class="card-header"><%=studentName%> performance </h4>
						<div class="card-body">
							<table class="table table-striped">
								<thead>
									<tr>
										
										<th>Number of Days</th>
										<th>Attendance</th>
										<th>Performance</th>
									</tr>
								</thead>
								<tbody>
									<!-- Populate table rows with data from hiredPersons list -->
									<%
									for (String[] performance : performances) {
									%>
									<tr>
									
										<td><%=performance[0]%></td>
										<td><%=performance[1]%></td>
										<td><%=performance[2]%></td>
										
									</tr>

									<%
									}
									%>

									
								</tbody>
							</table>

						</div>
					</div>
				</div>
    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // Retrieve presentData from the session
            var presentData = <%= new Gson().toJson(request.getSession().getAttribute("presentData")) %>;

            // Function to generate boxes
            function generateBoxes(container, count, data) {
                for (var i = 0; i < count; i++) {
                    var box = document.createElement('div');
                    box.classList.add('box');
                    box.textContent = i + 1; // Start from 1

                    // Set background color based on data
                    if (data[i] === 1) {
                        box.style.backgroundColor = 'green';
                    }

                    container.appendChild(box);
                }
            }

            // Get boxes container
            var boxesContainer = document.querySelector('.boxes-container');

            // Retrieve membership from the session
            var membership = <%= (int) request.getSession().getAttribute("membership") %>;

            // Generate boxes
            generateBoxes(boxesContainer, membership * 30, presentData);
        });
    </script>


</body>

</html>
