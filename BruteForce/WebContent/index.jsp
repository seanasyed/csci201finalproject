<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
   crossorigin="anonymous">
  </script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
   crossorigin="anonymous">
   <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
  <title>Brute Force</title>
</head>
<body>
<<<<<<< HEAD
  <h1 class="display-4 text-center bg-cardinal">
    <span class="align-text-top text-gold"><strong>Brute</strong></span>
    <span class="align-text-top text-white">Force</span>
  </h1>
  <nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Brute Force</a>
      <button class="navbar-toggler" data-toggle="collapse" data-target="#nav-items">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="nav-items">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="#">Search</a>
          </li>
          <li class="nav-item">
            <a id="scheduleLink" class="nav-link">My Schedule</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="jumbotron">
    <div class="form-inline mx-auto">
      <div class="container">
        <div class="row">
          <div class="col text-center my-2">
          <span id="username" class="mx-auto" style="font-size: 24px;"><%= request.getParameter("email") %></span>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <input type="text" id="start-time" class="form-control timepicker" placeholder="Start Time">
            <input type="text" id="end-time" class="form-control timepicker" placeholder="End Time">
            <input type="text" id="distance" class="form-control" placeholder="Distance">
          </div>
        </div>
        <div class="row">
          <div class="col-sm-10">
            <input id="search-box" class="form-control w-100" type="search" placeholder="Search" aria-label="Search">
            <ul id="suggestion-box" class="w-100 list-group"></ul>
          </div>
          <div class="col-sm-2">
            <button id="add-button" class="btn btn-cardinal my-2 my-sm-0 w-100">Add</button>
          </div>
        </div>
        <div class="row my-4">
          <div class="col-sm-12">
            <ul id="course-list" class="list-group">
            </ul>
          </div>
        </div>
        <div class="row">
          <div class="col">
            <button id="check-button" class="btn btn-cardinal my-2 my-sm-0 w-100">Check</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <footer id="main-footer">
    <div class="container">
      <div class="row">
        <div class="col text-center py-4">
          <h4>Brute Force</h4>
          <p>Copyright &copy;
            <span id="year"></span>
          </p>
        </div>
      </div>
    </div>
  </footer>
  <!-- CONTACT MODAL -->
  <!-- <div class="modal fadse text-dark" id="contactModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Contact Me</h5>
          <button class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form action="">
            <div class="form-group">
              <label for="name">Name</label>
              <input type="text" class="form-control">
            </div>
            <div class="form-group">
              <label for="email">Email</label>
              <input type="email" class="form-control">
            </div>
            <div class="form-group">
              <label for="message">Message</label>
              <textarea class="form-control"></textarea>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-info btn-block">Submit</button>
        </div>
      </div>
    </div>
  </div> -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
   crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
   crossorigin="anonymous"></script>
  <!-- Optional JavaScript -->
  <script type="text/javascript" src="javascript/script.js"></script>
  <script>
    $('#year').text(new Date().getFullYear());
  </script>
=======
	<h1 class="display-4  text-center bg-cardinal">
		<span class="align-text-top text-gold"><strong>Brute</strong></span>
		<span class="align-text-top text-white">Force</span>
	</h1>
	<nav class="navbar navbar-expand-md navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Brute Force</a>
			<button class="navbar-toggler" data-toggle="collapse" data-target="#nav-items">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="nav-items">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
						<a class="nav-link" href="#">Search</a>
					</li>
					<li class="nav-item">
						<a id="scheduleLink" class="nav-link">My Schedule</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="jumbotron">
		<div class="form-inline mx-auto">
			<div class="container">
				<div class="row">
					<div class="col text-center my-2">
					<span id="username" class="mx-auto" style="font-size: 24px;"><%= request.getParameter("email") %></span>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<input type="text" id="start-time" class="form-control timepicker" placeholder="Start Time">
						<input type="text" id="end-time" class="form-control timepicker" placeholder="End Time">
						<input type="text" id="distance" class="form-control" placeholder="Distance">
					</div>
				</div>
				<div class="row">
					<div class="col-sm-10">
						<input id="search-box" class="form-control w-100" type="search" placeholder="Search" aria-label="Search">
						<ul id="suggestion-box" class="w-100 list-group"></ul>
					</div>
					<div class="col-sm-2">
						<button id="add-button" class="btn btn-cardinal my-2 my-sm-0 w-100">Add</button>
					</div>
				</div>
				<div class="row my-4">
					<div class="col-sm-12">
						<ul id="course-list" class="list-group">
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<button id="check-button" class="btn btn-cardinal my-2 my-sm-0 w-100">Check</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<footer id="main-footer">
		<div class="container">
			<div class="row">
				<div class="col text-center py-4">
					<h4>Brute Force</h4>
					<p>Copyright &copy;
						<span id="year"></span>
					</p>
				</div>
			</div>
		</div>
	</footer>

	<!-- CONTACT MODAL -->
	<!-- <div class="modal fadse text-dark" id="contactModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Contact Me</h5>
					<button class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="">
						<div class="form-group">
							<label for="name">Name</label>
							<input type="text" class="form-control">
						</div>
						<div class="form-group">
							<label for="email">Email</label>
							<input type="email" class="form-control">
						</div>
						<div class="form-group">
							<label for="message">Message</label>
							<textarea class="form-control"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-info btn-block">Submit</button>
				</div>
			</div>
		</div>
	</div> -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	 crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	 crossorigin="anonymous"></script>

	<!-- Optional JavaScript -->
	<script type="text/javascript" src="javascript/script.js"></script>
	<script>
		$('#year').text(new Date().getFullYear());
	</script>
>>>>>>> 5d05a3cf00184c198244b2cacb6175f04185fe87
</body>
</html>
