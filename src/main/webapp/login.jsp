<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - Admantine Gym</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">
<style>
/* Customize card styles */
.login-card {
	border: 1px solid #ccc;
	border-radius: 10px;
	padding: 20px;
	margin-bottom: 20px;
	transition: box-shadow 0.3s ease;
}

.login-card:hover {
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
</style>
</head>
<body>
	<!-- Navbar -->
	<!-- Navigation Bar -->
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
					
				</ul>
			</div>
		</div>
	</nav>

	<!-- Login Section -->
	<section id="login" class="py-5">
		<div class="container">
			<div class="row">


				<!-- Owner Login -->


				<div class="col-md-6 col-lg-6 col-xl-3 mb-4">
					<div class="card login-card">
						<h5 class="card-title text-center">Owners Login</h5>
						<p class="text-center">
							<i class="fas fa-user"></i>
						</p>
						<!-- Owner login form goes here -->
						<form class="owner-form" action="OwnerLogin" method="post">
							<!-- Add your owner login form fields here -->
							<div class="form-group">
								<label for="owner-email">Id</label> <input type="text"
									name="id" class="form-control" id="owner-email"
									placeholder="Enter email">
							</div>
							<div class="form-group">
								<label for="owner-password">Password</label> <input
									type="password" name="password" class="form-control"
									id="owner-password" placeholder="Password">
							</div>
							<%String Omsg = (String)request.getAttribute("OwnerLogin"); %>
							<p style="color: red">
							<%if(Omsg!=null){ %>
							<%=Omsg %></p>
							<%} %>
							<button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>


				<!--------------------------------- Trainer Login ----------------------------------------->


				<div class="col-md-6 col-lg-6 col-xl-3 mb-4">
					<div class="card login-card">
						<h5 class="card-title text-center">Trainer Login</h5>
						<p class="text-center">
							<i class="fas fa-user"></i>
						</p>
						<!-- Owner login form goes here -->
						<form class="owner-form" action="TrainerLogin" method="post">
							<!-- Add your owner login form fields here -->
							<div class="form-group">
								<label for="owner-email">Email</label> <input type="number"
									class="form-control" name = "id" id="owner-email" placeholder="Your Id" required="required">
							</div>
							<div class="form-group">
								<label for="owner-password">Password</label> <input
									name = "password" type="password" class="form-control" id="owner-password"
									placeholder="Password" required="required">
							</div>
							<%String Tmsg = (String)request.getAttribute("TerrorMessage"); %>
							<p style="color: red">
							<%if(Tmsg!=null){ %>
							<%=Tmsg %></p>
							<%} %>
							<button type="submit" class="btn btn-primary">Login</button>
						</form>
					</div>
				</div>


				<!--------------------------------- Student Login ----------------------------------------->


				<div class="col-md-6 col-lg-6 col-xl-3 mb-4">
					<div class="card login-card">
						<h5 class="card-title text-center">Student Login</h5>
						<p class="text-center">
							<i class="fas fa-user"></i>
						</p>
						<!-- Owner login form goes here -->
						<form class="owner-form" action="Student_Login" method="post">
							<!-- Add your owner login form fields here -->
							<div class="form-group">
								<label for="owner-email">Id</label> <input name="id"
									type="number" class="form-control" id="owner-email"
									placeholder="Enter email" required="required">
							</div>
							<div class="form-group">
								<label for="owner-password">Password</label> <input
									name="password" type="password" class="form-control"
									id="owner-password" placeholder="Password" required="required">
							</div>
							<%String Smsg = (String)request.getAttribute("StudentStatus"); %>
							<p style="color: red">
							<%if(Smsg!=null){ %>
							<%=Smsg %></p>
							<%} %>
							<button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>



				<!--------------------------------- Receptionist Login ----------------------------------------->




				<div class="col-md-6 col-lg-6 col-xl-3 mb-4">
					<div class="card login-card">
						<h5 class="card-title text-center">Receptionist Login</h5>
						<p class="text-center">
							<i class="fas fa-user"></i>
						</p>
						<!-- Owner login form goes here -->
						<form class="owner-form" action="ReceptionistLogin" method="post">
							<!-- Add your owner login form fields here -->
							<div class="form-group">
								<label for="owner-email">Id</label> <input required="required" name="email"
									type="number" class="form-control" id="owner-email"
									placeholder="Id">
							</div>
							<div class="form-group">
								<label for="owner-password">Password</label> <input
									name="password" type="password" class="form-control"
									id="owner-password" placeholder="Password" required="required">
							</div>
							<%String Rmsg = (String)request.getAttribute("errorMessage"); %>
							<p style="color: red">
							<%if(Rmsg!=null){ %>
							<%=Rmsg %></p>
							<%} %>
							<button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>
				<!-- Add similar cards for other user types (Trainer, Student, Receptionist) -->
			</div>
		</div>
	</section>

	<!-- Your login form and content here -->
	<div class="container">
		<!-- Your login form content goes here -->
	</div>
	<!-- Footer Section -->
	<footer class="py-4 bg-dark text-white text-center">
		<div class="container">
			<p>&copy; 2024 Admantine Gym. All rights reserved.</p>
		</div>
	</footer>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
