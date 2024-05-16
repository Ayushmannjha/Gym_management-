<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*"%>
    <%@ page import="com.google.gson.Gson"%>
    <%@ page errorPage="error.jsp" %>  
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admantine Gym</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Gradient Background */
        body {
            background: linear-gradient(to bottom right, #00416A, #E4E5E6);
            color: #fff;
        }

        /* Navbar Styling */
        .navbar {
            background-color: #00416A;
        }

        .navbar-brand img {
            margin-right: 10px;
        }

        .navbar-brand {
            font-size: 24px;
        }

        /* Box Styling */
        .box-card {
            width: 80%;
            max-width: 600px;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 20px;
            margin: auto;
            background-color: rgba(255, 255, 255, 0.9);
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
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
            transition: background-color 0.3s ease;
        }

        .box:hover {
            background-color: #6CB5E4;
            cursor: pointer;
        }
    </style>
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
                        box.style.backgroundColor = '#6CB5E4';
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

	<footer class="py-4 bg-dark text-white text-center">
		<div class="container">
			<p>&copy; 2024 Admantine Gym. All rights reserved.</p>
		</div>
	</footer>
</body>

</html>
