<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html lang="en">

        <head>

            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
            </script>

            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js">
            </script>

            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js">
            </script>


        </head>

        <body class="bg-dark">

            <!-- navbar -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="/">Bus Management System</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse text-primary bg-light" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/login">Admin</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/employee/login">Employee</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#">Disabled</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <div class="container"
                style="height:90vh; width: 100vw; align-items: center; display: flex; justify-content: center;">
                <form id="add-employee-form" action="/employee/login"
                    style="max-width: 25rem; padding: 5rem; border: 1px solid white;">
                    <div class="form-group vw-50">
                        <label for="firstname" class="text-light display-5">First Name</label>
                        <input type="text" class="form-control" id="firstname" placeholder="First Name">
                    </div>
                    <div class="form-group vw-50">
                        <label for="lastname" class="text-light display-5">Last Name</label>
                        <input type="text" class="form-control" id="lastname" placeholder="last Name">
                    </div>
                    <div class="form-group vw-50">
                        <label for="email" class="text-light display-5">Email address</label>
                        <input type="email" class="form-control" id="email" placeholder="employee@example.com">
                    </div>
                    <div class="form-group vw-50">
                        <label for="age" class="text-light display-5">Age</label>
                        <input type="number" class="form-control" id="age" placeholder="age">
                    </div>
                    <div class="form-group vw-50">
                        <label for="salary" class="text-light display-5">Salary</label>
                        <input type="number" class="form-control" id="salary" placeholder="salary">
                    </div>
                    <div class="form-group vw-50">
                        <label for="education" class="text-light display-5">Education</label>
                        <input type="text" class="form-control" id="education" placeholder="education">
                    </div>

                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="exampleCheck1">
                        <label class="form-check-label" for="exampleCheck1">Check me out</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>




        </body>

        </html>