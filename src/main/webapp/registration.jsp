<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="error.html" %>  


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Query Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Gym Management</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.html">Home</a>
                    </li>
                    
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div class="card">
                    <div class="card-header">
                        Query Form
                    </div>
                    <div class="card-body">
                        <!-- Query form -->
                        <form action="Query" method="post">
                            <div class="mb-3">
                                <label for="firstName" class="form-label">First Name:</label>
                                <input required="required" name = "name" type="text" class="form-control" id="firstName" placeholder="Enter First Name" required="required">
                            </div>
                            <div class="mb-3">
                                <label for="phoneNumber" class="form-label">Phone Number:</label>
                                <input required="required" name = "phone" type="tel" class="form-control" id="phoneNumber" required="required"
                                    placeholder="Enter Phone Number">
                            </div>
                            <div class="mb-3">
                                <label for="membershipDuration" class="form-label">Membership Duration:</label>
                                <select required="required" name = "membership" class="form-select" id="membershipDuration" required="required">
                                    <option value="1">1 Month</option>
                                    <option value="6">6 Months</option>
                                    <option value="12">1 Year</option>
                                </select>
                            </div>
                            
                            <%
                           
							
                            Integer statu = (Integer)request.getAttribute("registrationStatus");
                            int status = (statu!=null)?statu.intValue():0;
                            %>
                            
                            <%if(status==1){%>
                          <p style="color: green">Query submitted our experts call you soon</p>
                           <% } else if(status==2){%>
                        	   <p>Query already submitted</p>
                           <%}%>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
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
