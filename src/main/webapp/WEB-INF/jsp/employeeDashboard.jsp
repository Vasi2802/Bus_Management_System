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

            <script>
            //    ( __INCOMPLETE__ )
                const removeBooking = (employeeId) => {
                    url = "/employee/removebooking/";
                    console.log("the employee id selected = ", employeeId);
                    $.ajax({
                        headers: {
                            Accept: 'application/json', //imp
                        },
                        type: "POST",
                        url: url,
                        data: JSON.stringify({employeeId: employeeId}), // imp
                        dataType: "json",
                        contentType: "application/json", //imp
                        async: false,
                        success: function (response) {
                            alert(response);
                        },
                        error: function () {
                            alert('Error occured in fetching routes');
                        }
                    })
                }
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
                                    <td>${employee.getEid()}</td>
                                </tr>
                                <tr>
                                    <td>Name</td>
                                    <td>${employee.getName()}</td>
                                </tr>
                                <tr>
                                    <td>Phone No </td>
                                    <td>${employee.getContactNo()}</td>
                                </tr>
                                <tr>
                                    <td>Email</td>
                                    <td>${employee.getEmail()}</td>
                                </tr>
                                <tr>
                                    <td>Bus Details </td>
                                    <td>${employee.getB()==null?"--":employee.getB().getBid()}</td>
                                    <!-- if in waitlist -->
                                    <!-- employee/getStatus returns assigned, not applied, in waitlist -->
                                </tr>
                                <tr>
                                    <td><a href="book" class="btn btn-primary">Book a bus</a></td>
                                    <td><a class="btn btn-primary" onclick="removeBooking(<c:out value="
                                            ${employee.getEid()}" />)">Remove Booking/Waiting</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>





        </body>

        </html>