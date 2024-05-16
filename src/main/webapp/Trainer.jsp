<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page errorPage="error.jsp" %>  
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trainer Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
</head>

<body>
    <!-- Navbar -->
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

    <div class="container mt-4">
        <div class="row">
            <!-- Input box to enter student roll number -->
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        Check Student Performance
                    </div>
                    <div class="card-body">
                        <form action="TrainerStudent" method="post">
                            <div class="mb-3">
                                <label for="rollNumber" class="form-label">Enter Student Roll Number:</label>
                                <input required="required" name = "id" type="number" class="form-control" id="rollNumber" placeholder="Enter Roll Number">
                            </div>
                            <%String stS = (String)request.getAttribute("StudentStatus"); %>
                            <%if(stS!=null){ %>
                            <p style="color: red"><%= stS%></p>
                            <%} %>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
