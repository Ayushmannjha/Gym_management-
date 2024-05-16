<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.sql.*"%>
<%@ page errorPage="error.html" %>  
<%
String name = (String) request.getSession().getAttribute("name");

Double status = (Double) request.getAttribute("dues");
double s = (status != null) ? status.doubleValue() : 0;

%>

<%
ServletContext context = request.getServletContext();
String className = context.getInitParameter("DriverClassName");
String url = context.getInitParameter("url");
String user = context.getInitParameter("user");
String pwd = context.getInitParameter("password");
// Initialize the list to store hired persons
List<String[]> hiredPersons = new ArrayList<>();
List<String[]> attendance = new ArrayList<>(); // Corrected variable name

try {
	// Load the database driver
	Class.forName(className);

	// Establish the database connection
	Connection connection = DriverManager.getConnection(url, user, pwd);

	// Create and execute the SQL query for hired persons
	Statement statement = connection.createStatement();
	ResultSet resultSet = statement.executeQuery("SELECT name, phone, membership FROM query");

	// Iterate through the result set and add data to the hiredPersons list
	while (resultSet.next()) {
		String[] person = new String[3];
		person[0] = resultSet.getString("name");
		person[1] = resultSet.getString("phone");
		person[2] = resultSet.getString("membership");
		hiredPersons.add(person);
	}

	// Close the first result set
	resultSet.close();

	// Create and execute the SQL query for attendance
	resultSet = statement.executeQuery("SELECT roll, name, entry_time, exit_time, dues FROM attendence"); // Corrected table name

	// Iterate through the result set and add data to the attendance list
	while (resultSet.next()) {
		String[] student = new String[5];
		student[0] = resultSet.getString("roll");
		student[1] = resultSet.getString("name");
		student[2] = resultSet.getString("entry_time");
		student[3] = resultSet.getString("exit_time");
		student[4] = resultSet.getString("dues");
		attendance.add(student); // Add student data to the attendance list
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
<title>Receptionist Page - Admantine Gym</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">
<style>
* {
	margin: 0px;
	padding: 0px;
}
/* Add custom styles here */
.receptionist-name {
	position: relative;
	top: 10px;
	left: 10px;
	color: rgb(127, 179, 248);
	font-size: 18px;
	font-weight: bold;
}

.card {
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<!-- Logo and brand -->
			<a class="navbar-brand" href="#"> <img src="your-logo.png"
				alt="My Gym Logo" width="45" height="45"
				class="d-inline-block align-top" style="margin-right: 10px;">
				Admantine Gym
			</a>

			<!-- Toggler icon (visible on small screens) -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<!-- Navigation links (visible on large screens) -->
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="index.html">Home</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#NewAddmission">NewAddmission</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#Attendance">Attendance</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#Dues">Dues</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#Queries">Queries</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Receptionist's name -->
	<div class="receptionist-name">
		<h2><%=name%></h2>
	</div>

	<!-- Receptionist Forms Section -->
	<!-- Receptionist Forms Section -->
	<section id="receptionist-forms" class="py-5">
		<div class="container">
			<div class="row">
				<!-- Entry Time -->
				<div class="col-md-6">
					<div class="card">
						<h4 class="card-header">Entry Time</h4>
						<div class="card-body">
							<form action="AttendenceEntry" method="post">
								<div class="mb-3">
									<label for="inputIdEntry" class="form-label">ID</label> <input
										name="id" type="number" class="form-control" id="inputIdEntry"
										placeholder="Enter ID" required="required">
								</div>
								<%
								Integer entryStatusObj = (Integer) request.getAttribute("attendanceEntry");
								int entryStatus = (entryStatusObj != null) ? entryStatusObj.intValue() : 0;
								%>
								<%
								if (entryStatus > 0) {
								%>
								<%
								if (entryStatus == 1) {
								%>
								<p style="color: green">Attendence done</p>
								<%
								} else if (entryStatus == 4) {
								%>
								<p style="color: red">Roll no is incorrect</p>
								<%
								} else if (entryStatus == 5) {
								%>
								<p style="color: green">Already done</p>
								<%
								}
								%>
								<%
								}
								%>
								<!-- Add other form fields if needed -->
								<button type="submit" class="btn btn-primary">Submit</button>
							</form>
						</div>
					</div>
				</div>
				
				
				
				<!-- Exit Time -->
				<div class="col-md-6">
					<div class="card">
						<h4 class="card-header">Exit Time</h4>
						<div class="card-body">
							<form action="AttendenceExit" method="post">
								<div class="mb-3">
									<label for="inputIdExit" class="form-label">ID</label> <input
										name="id" type="text" class="form-control" id="inputIdExit"
										placeholder="Enter ID" required="required">
								</div>
								<%
								if (entryStatus > 0) {
								%>
								<%
								if (entryStatus == 2) {
								%>
								<p style="color: green">Attendence done</p>
								<%
								} else if (entryStatus == 3) {
								%>
								<p style="color: red">roll number is incorrect otherwise
									student is absent</p>
								<%
								}
								%>
								<%
								}
								%>
								<!-- Add other form fields if needed -->
								<button type="submit" class="btn btn-primary">Submit</button>
							</form>
						</div>
					</div>
				</div>
				<!-- Clear Dues -->
<div class="col-md-12">
    <div id =  "Dues" class="card">
        <h4 class="card-header">Clear Dues</h4>
        <div class="card-body">
            <form action="clearDues.jsp" method="post">
                <div class="mb-3">
                    <label for="inputRoll" class="form-label">Roll Number</label>
                    <input type="number" class="form-control" id="inputRoll" name="roll"
                           placeholder="Enter Roll Number" required>
                </div>
               
                <button type="submit" class="btn btn-primary">Clear Dues</button>
            </form>
        </div>
    </div>
</div>
				
				<div class="col-md-12">
					<div id = "Attendance" class="card">
						<h4 class="card-header">Today Attendance</h4>
						 <p>Refresh attendance for absent students:
						 <%String time = (String)request.getAttribute("time"); %>
						 <%if(time!=null){ %>
						<p style="color: red"> <%=time %><p>
						<%} %>
						 </p>
                        <a href="RefreshAttendance" class="btn btn-primary">Absent</a>	<div class="card-body">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>ROll</th>
										<th>Name</th>
										<th>Entry time</th>
										<th>Exit time</th>
										<th>dues</th>
									</tr>
								</thead>
								<tbody>
									<!-- Populate table rows with data from hiredPersons list -->
									<%
									for (String[] student : attendance) {
									%>
									<tr>
										<td><%=student[0]%></td>
										<td><%=student[1]%></td>
										<td><%=student[2]%></td>
										<td><%=student[3]%></td>
										<td><%=student[4]%></td>
									</tr>

									<%
									}
									%>
								</tbody>
							</table>

						</div>
					</div>
				</div>
				<!-- Hired Persons -->
				<div class="col-md-12">
					<div id = "Queries" class="card">
						<h4 class="card-header">Query</h4>
						<div class="card-body">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>Name</th>
										<th>Phone Number</th>
										<th>Membership Duration</th>
									</tr>
								</thead>
								<tbody>
									<!-- Populate table rows with data from hiredPersons list -->
									<%
									for (String[] person : hiredPersons) {
									%>
									<tr>
										<td><%=person[0]%></td>
										<td><%=person[1]%></td>
										<td><%=person[2]%></td>
									</tr>

									<td>
										<form action="DeleteQuery" method="post">
											<input type="hidden" name="phone" value="<%=person[1]%>">
											<input type="submit" value="remove">
										</form>
									</td>


									<%
									}
									%>
								</tbody>
							</table>

						</div>
					</div>
				</div>
				<!-- New Admission -->
				<div id = "NewAddmission" class="col-md-12">
					<div class="card">
						<h4 class="card-header">New Admission</h4>
						<div class="card-body">
							<form action="NewAddmission" method="post">
								<div class="mb-3">
									<label for="inputName" class="form-label">Name</label> <input
										type="text" class="form-control" id="inputName" name="name"
										placeholder="Enter Name">
								</div>
								<div class="mb-3">
									<label for="inputPhone"  class="form-label">Phone Number</label>
									<input pattern="[0-9]{10}" type="text" class="form-control" id="inputPhone"
										name="phone" placeholder="Enter Phone Number" required="required">
								</div>
								<div class="mb-3">
									<label for="inputEmail" class="form-label">Email</label> <input
										type="email" class="form-control" id="inputEmail" name="email"
										placeholder="Enter Email" required="required">
								</div>
								<div class="mb-3">
									<label for="inputMembership" class="form-label">Membership
										Duration</label> <select required="required" name="fees" class="form-select"
										id="inputMembership">
										<option selected disabled>Select Membership Duration</option>
										<option value="1">1 Month</option>
										<option value="6">6 Months</option>
										<option value="12">12 Months</option>
									</select>
								</div>
								<div class="mb-3">
									<label for="inputAmount" class="form-label">Paying
										Amount</label> <input required="required" type="number" class="form-control"
										id="inputAmount" placeholder="Enter Paying Amount" name="paid">
								</div>
								<%
								Object doneAttribute = request.getAttribute("done");
								int addmission = 3; // Default value in case the attribute is not set correctly

								if (doneAttribute instanceof Integer) {
									addmission = ((Integer) doneAttribute).intValue();
								}
								%>

								<%
								Integer rollno = (Integer) request.getAttribute("Id");
								int roll = (rollno != null) ? rollno.intValue() : 0;
								String password = (String)request.getAttribute("password");
								%>
								<%
								if (addmission == 1) {
								%>
								<p style="color: green">Addmission succefull</p>
								<p>
									Your generated Id no =
									<%=roll%></p>
									<p>
									Your generated password =
									<%=password%></p>
								<%
								} else if (addmission == 0) {
								%>
								<p style="color: red">Something went wrong</p>
								<%
								}
								%>



								<!-- Add other form fields if needed -->
								<button type="submit" class="btn btn-primary">Submit</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Footer Section -->
	<footer class="py-4 bg-dark text-white text-center">
		<div class="container">
			<p>&copy; 2024 Admantine Gym. All rights reserved.</p>
		</div>
	</footer>

	<!-- Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
