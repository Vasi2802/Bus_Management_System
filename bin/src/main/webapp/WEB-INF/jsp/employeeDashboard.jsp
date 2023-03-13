<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <body class="bg-dark vh-100">

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
                            <a class="nav-link" href="/employee/dashboard">Home </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/employee/book">Book Bus</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/employee/edit">Edit Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#">Disabled</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <div class="align-items-center justify-content-center d-flex" style="height: 90vh;">

                <div class="card text-white ">
                    <div class="card-header bg-secondary">
                        <h1>Employee Dashboard</h1>
                    </div>

                    <div class="table-responsive table table-striped table-dark">
                        <table class="table text-white">

                            <tbody>
                                <tr>
                                    <td>Employee ID</td>
                                    <td>put Emp ID</td>
                                </tr>
                                <tr>
                                    <td>Name</td>
                                    <td>put Emp Name</td>
                                </tr>
                                <tr>
                                    <td>Phone No </td>
                                    <td>put Emp phno</td>
                                </tr>
                                <tr>
                                    <td>Email</td>
                                    <td>put Emp email</td>
                                </tr>
                                <tr>
                                    <td>Bus Id </td>
                                    <td>display bus id</td>
                                    <!-- if in waitlist -->
                                    <!-- employee/getStatus returns assigned, not applied, in waitlist -->
                                </tr>
                                <tr>
                                    <td><a href="book" class="btn btn-primary">Book a bus</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>





        </body>

        </html>