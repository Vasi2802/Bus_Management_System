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
                function addStop() {
                    var url = '/stop/insert';

                    var stop = {};
                    $("#add-stop-form").serializeArray().map(function (x) {
                        stop[x.name] = x.value;
                    });

                    console.log(stop);

                    $.ajax({
                        headers: {
                            Accept: 'application/json', //imp
                        },
                        type: "POST",
                        url: url,
                        data: JSON.stringify(stop), // imp
                        dataType: "json",
                        contentType: "application/json", //imp
                        success: function (response) {
                            console.log("added",response);
                        },
                        error: function () {
                            alert('Error occured');
                        }
                    });
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
                            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
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
                <form id="add-stop-form" style="max-width: 25rem; padding: 5rem; border: 1px solid white;">
                    <div class="form-group vw-50">
                        <label for="stopid" class="text-light display-5">Stop ID</label>
                        <input type="number" class="form-control" id="stopid" name="sid" placeholder="Stop ID">
                    </div>
                    <div class="form-group vw-50">
                        <label for="stopname" class="text-light display-5">Stop Name</label>
                        <input type="text" class="form-control" id="stopname" name="name" placeholder="Stop Name">
                    </div>

                    <button type="button" class="btn btn-primary" onclick="addStop()">Add Stop</button>
                </form>
            </div>


        </body>

        </html>