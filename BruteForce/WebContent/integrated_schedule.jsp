<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>  
    <%
    	String username = request.getParameter("username"); 
    %>
    
    <!-- Required meta tags -->
     <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
     crossorigin="anonymous">
    </script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="js/modernizr.js"></script>
<script src="js/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <!-- Bootstrap CSS -->
    
    
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
     crossorigin="anonymous">
     
    
    <link rel="stylesheet" href="css/schedule_reset.css"> <!-- CSS reset -->
    <link rel="stylesheet" href="css/schedule_style.css"> <!-- Resource style -->
    
    <style>
    *{
          
     }
     h1{
        
     }
        body{
          margin: 0;
          font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
          font-size: 13px;
          font-weight: 400;
          line-height: 1.5;
          color: #212529;
          text-align: left;
          background-color: #fff;
        }
        .events{
        	font-size: 12px;
        }
        .navbar navbar-expand-md navbar-light bg-light .container-fluid .navbar-download{
        	font-weight: bold;
        	color: #00ff00;
        }
    </style>
    
    <title>Brute Force Schedule</title>
</head>
<body>
    <script>
    function download(){
    	var downloadLink = 'http://localhost:8080/BruteForce/';
    	console.log("Downloading. ");
    	$.ajax({
    		url: "BruteForce",
    		data: {
      			callType: "get_download",
      			username: $('#username').text()
    		},
    		success: function(result) {
		      //console.log("Result: ******: " + result);
		      //printSchedule(result);
		      downloadLink += result;
		      console.log("Download link is: " + downloadLink);
		      window.open(downloadLink, _blank);
		      
    		}
		  });
    }
    
    function getSchedule(){
    	//TODO: Connect with back end
			$.ajax({
	    		url: "BruteForce",
	    		data: {
	      			callType: "get_schedule",
	      			username: $('#username').text()
	    		},
	    		success: function(result) {
			      console.log("Result: ******: " + result);
			      printSchedule(result);
	    		}
			  });
    };
    function printSchedule(data) {
    	var courseList = JSON.parse(data);
	      for (var i=0; i<courseList.length; i++){
          	var currentCourse = courseList[i]; //current course
          	var name = currentCourse.courseName;
          	console.log("Current Course: "+ name);
          	var fullname = currentCourse.courseName + ' ' + currentCourse.type; //lecture, lab, quiz, discussion
          	console.log("Current Course Full Name: "+ fullname);
          	var start_time = currentCourse.startTime; // HH:MM
          	if (start_time[0] != '1' && start_time[0] != '2') {
          		start_time = '0' + start_time;
          	}
          	console.log("Start Time: "+ start_time);
          	var end_time = currentCourse.endTime; // HH:MM
          	if (end_time[0] != '1' && end_time[0] != '2') {
          		end_time = '0' + end_time;
          	}
          	console.log("End Time: "+ end_time);
          	var day = currentCourse.day; //MWF, TTh
          	console.log("Day: "+ day);
          	for (var j=0; j<day.length; j++){
          		var classnum = i+1;
          		var inner = '<a href="#0"><em class="event-name">'+ fullname+ '</em></a>';
          		var itemDay;
          		var listDay;
          		if (day[j]=='M'){ //monday's class
          			itemDay = 'mon';
          			listDay = 'monday';
          		} else if (day[j]=='T'){
          			itemDay = 'tue';
          			listDay = 'tuesday';
          		} else if (day[j]=='W'){
          			itemDay = 'wed';
          			listDay = 'wednesday';
          		} else if (day[j]=='H'){
          			itemDay = 'thu';
          			listDay = 'thursday';
          		} else if (day[j]=='F'){
          			itemDay = 'fri';
          			listDay = 'friday';
          		}
                var item = $('<li class="single-event" id="' + itemDay + '-class-'+ classnum + '" data-start="' + start_time + '" data-end="' + end_time + '" data-content="' + name + '" data-event="event-' + classnum + '">' + inner + '</li>');
      			item.appendTo($('#' + listDay + '-list'));
               
          	}
          }
	      initialize();
    };
</script>
    <p id="username" style="display:none;"><%= username %></p>
    <h1 class="display-4  text-center bg-cardinal">
        <span class="align-text-top text-gold"><strong>Brute</strong></span>
        <span class="align-text-top text-white">Force</span>
        
    </h1>
    <nav class="navbar navbar-expand-md navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Brute Force</a>
            <a class="navbar-download" id="download_link" href="javascript:download();">DOWNLOAD SCHEDULE</a>
            <button class="navbar-toggler" data-toggle="collapse" data-target="#nav-items">
                <span class="navbar-toggler-icon"></span>
            </button>
            
        </div>
    </nav>
    
    <div class="cd-schedule loading">
    <div class="timeline">
        <ul>
            <li><span>08:00</span></li>
            <li><span>08:30</span></li>
            <li><span>09:00</span></li>
            <li><span>09:30</span></li>
            <li><span>10:00</span></li>
            <li><span>10:30</span></li>
            <li><span>11:00</span></li>
            <li><span>11:30</span></li>
            <li><span>12:00</span></li>
            <li><span>12:30</span></li>
            <li><span>13:00</span></li>
            <li><span>13:30</span></li>
            <li><span>14:00</span></li>
            <li><span>14:30</span></li>
            <li><span>15:00</span></li>
            <li><span>15:30</span></li>
            <li><span>16:00</span></li>
            <li><span>16:30</span></li>
            <li><span>17:00</span></li>
            <li><span>17:30</span></li>
            <li><span>18:00</span></li>
            <li><span>18:30</span></li>
            <li><span>19:00</span></li>
            <li><span>19:30</span></li>
            <li><span>20:00</span></li>
            <li><span>20:30</span></li>
            <li><span>21:00</span></li>
        </ul>
    </div> <!-- .timeline -->
    <script>
        
    </script>
    <div class="events">
        <ul>
            <li class="events-group">
                <div class="top-info"><span>Monday</span></div>
                <ul id="monday-list">
				</ul>
            </li>
            <li class="events-group">
                <div class="top-info"><span>Tuesday</span></div>
                <ul id="tuesday-list">
                </ul>
            </li>
            <li class="events-group">
                <div class="top-info"><span>Wednesday</span></div>
                <ul id="wednesday-list">
                </ul>
            </li>
            <li class="events-group">
                <div class="top-info"><span>Thursday</span></div>
                <ul id="thursday-list">
                </ul>
            </li>
            <li class="events-group">
                <div class="top-info"><span>Friday</span></div>
                <ul id="friday-list">
                </ul>
            </li>
       </ul>
    </div>
    <div class="event-modal">
        <header class="header">
            <div class="content">
                <span class="event-date"></span>
                <h3 class="event-name"></h3>
            </div>
            <div class="header-bg"></div>
        </header>
        <div class="body">
            <div class="event-info"></div>
            <div class="body-bg"></div>
        </div>
        <a href="#0" class="close">     </a>
    </div>
    <div class="cover-layer"></div>
</div> <!-- .cd-schedule -->

<script>
    if( !window.jQuery ) document.write('<script src="js/jquery-3.0.0.min.js"><\/script>');
</script>
 <!-- Resource jQuery -->
    <footer id="main-footer">
        <div class="container">
            <div class="row">
                <div class="col text-center py-4">
                    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
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
        getSchedule();
        
    </script>
</body>
</html>
