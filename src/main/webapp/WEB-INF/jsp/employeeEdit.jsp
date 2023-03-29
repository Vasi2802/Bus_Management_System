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
                function updateDetails() {
                    var url = '/employee/editemployeedetails';

                    var employee = {};
                    $("#employee-edit-form").serializeArray().map(function (x) {
                        employee[x.name] = x.value;
                    });

                    
                    console.log(employee);

                    $.ajax({
                        headers: {
                            Accept: 'application/json', //imp
                        },
                        type: "POST",
                        url: url,
                        data: JSON.stringify(employee), // imp
                        dataType: "json",
                        contentType: "application/json", //imp
                        success: function (response) {
                            console.log("updated employee to", response);
                            pop
                        },
                        error: function () {
                            alert('Error occured');
                        }
                    });
                    window.location.href = "/employee/dashboard"; //redirect
                }
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

            <div class="container text-light"
                style="height:90vh; width: 100vw; align-items: center; display: flex; justify-content: center;">
                <form id="employee-edit-form" style="max-width: 75rem; padding: 5rem; border: 1px solid white;">
                    <h2 class="pb-4">Edit Profile Details</h2>
                    <div class="form-group vw-50">
                        <label for="name" class="text-light display-5">Name</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Name...">
                    </div>
                    <div class="form-group vw-50">
                        <label for="contactNo" class="text-light display-5">Phone No.</label>
                        <input type="number" class="form-control" id="contactNo" name="contactNo" placeholder="12345678890">
                    </div>
                    <div class="form-group vw-50">
                        <label for="email" class="text-light display-5">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="abc@email.com">
                    </div>

                    <button type="button" class="btn btn-primary" onclick="updateDetails()">Update Details</button>
                </form>
            </div>


        </body>

        </html>