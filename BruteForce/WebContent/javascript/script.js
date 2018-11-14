$(document).ready(function() {
  $("#add-button").click(function(event) {
    var keyword = $("#search-box").val();
    addCourseToList(keyword);
    $("#search-box").val("");
    $("#add-button").attr("disabled", true);
  });
  $("#add-button").attr("disabled", true);
  $("#check-button").click(function(event) {
    checkCourseListOnServer();
  });
  $("#search-box").keyup(function() {
    $("#add-button").attr("disabled", true);
    getSuggestions();
  });
  $("input.timepicker").timepicker({
    timeFormat: "HH:mm"
  });
  $('#scheduleLink').click(function(event) {
	  linkToSchedule();
  });
});
/*
 * FUNCTIONS FOR ADDING AN ITEM TO THE LIST
 * 
 */
function addCourseToList(courseName) {
  //CREATE A LIST ITEM
  var item = $(
    '<li id="' +
    courseName.toUpperCase() +
      '"class="list-group-item d-flex justify-content-between"></li>'
  );
  var form = $(
    '<form id="' +
    courseName +
      'Form" target="_blank" name="detailForm" method= "GET" action = "detail.jsp" ></form>'
  );
  var input = $(
    '<input style="display: none;" name="courseName" value="' +
    courseName +
      '"></input>"'
  );
  input.appendTo(form);
  var title = $("<span></span>");
  title.text(courseName);

  //SETUP DELETE BUTTON
  var button = $('<i class="fas fa-times my-auto">');
  button.on("click", function() {
    $("#" + courseName.toUpperCase()).remove();
  });
  title.appendTo(item);
  button.appendTo(item);
  form.appendTo($(document.body));
  title.on("click", function() {
    form.submit();
  });
  $("#course-list").append(item);
}
/*
 * FUNCTIONS FOR COMMUNICATING WITH THE SERVER
 * 
 */
var addUser = (email, fname, lname) => {
  $.ajax({
    url: "BruteForce",
    data: {
      callType: "add_user",
      email: email,
      fname: fname,
      lname: lname
    },
    success: function(result) {
      console.log(result);
    }
  });
};

function checkCourseListOnServer() {
  var courseList = [];
  var startTime = $("#start-time").val();
  var endTime = $("#end-time").val();

  var distance = $("#distance").val();
  console.log("distance: " + distance);
  
  for (var i = 0; i < $("#course-list").children().length; i++) {
    var text = $("#course-list").children()[i].innerText;
    text = text.replace(/\n/gi, "");
    courseList.push(text);
  }
  var courseListJSON = JSON.stringify(courseList);
  var username = $('#username').text();
<<<<<<< HEAD
  var distance = $('#distance').val();
  console.log('distance:' + distance);
=======
  console.log('username is:' + username);
>>>>>>> 5d05a3cf00184c198244b2cacb6175f04185fe87
  $.ajax({
    url: "BruteForce",
    data: {
      callType: "check_schedule",
      username: username,
      startTime: startTime,
      endTime: endTime,
<<<<<<< HEAD
      distanceConstraint: distance,
=======
      distance: distance,
>>>>>>> 5d05a3cf00184c198244b2cacb6175f04185fe87
      courseList: courseListJSON
    },
    success: function(result) {
      if (result.valid == 'false') {
    	  alert('Invalid Schedule');
    	  $('#course-list li').remove();
    	  $('#start-time').val('');
    	  $('#end-time').val('');
      } else {
    	  alert('The schedule is valid. Click submit to continue.')
    	  var checkButton = $('#check-button');
    	  checkButton.text('Submit');
    	  checkButton.unbind("click");
    	  checkButton.click(function(event) {
    		  submitCourseListToServer();
    	  });
      }
    }
  });
};

var submitCourseListToServer = () => {
	var checkButton = $('#check-button');
	checkButton.click(function(event) {
		checkCourseListToServer();
	});
	var courseList = [];
	  var startTime = $("#start-time").val();
	  var endTime = $("#end-time").val();
	  for (var i = 0; i < $("#course-list").children().length; i++) {
	    var text = $("#course-list").children()[i].innerText;
	    text = text.replace(/\n/gi, "");
	    courseList.push(text);
	  }
	  var username = $('#username').text();
	  var courseListJSON = JSON.stringify(courseList);
	  var distance = $('#distance').val();
	  $.ajax({
	    url: "BruteForce",
	    data: {
	      callType: "submit_schedule",
	      username: username,
	      startTime: startTime,
	      endTime: endTime,
	      distanceConstraint: distance,
	      courseList: courseListJSON
	    },
	    success: function(result) {
	    	console.log(result);
	    	alert(result.message);
	    	reset();
	    }
	  });
};
function reset() {
	$('#course-list li').remove();
	$('#start-time').val('');
	$('#end-time').val('');
	var checkButton = $('#check-button');
	  checkButton.text('Check');
	  checkButton.unbind("click");
	  checkButton.click(function(event) {
		  checkCourseListOnServer();
	  });
}
function getSuggestions() {
  var ul = $("#suggestion-box");
  ul.empty();
  // Declare variables
  var input = $("#search-box");
  var filter = input.val().toUpperCase();
  if (filter == "") return;

  $.ajax({
    url: "BruteForce",
    data: {
      callType: "suggestions",
      keyword: filter
    },
    success: function(result) {
      var data = JSON.parse(result);
      var texts = [];
      $("#course-list li span").each(function() {
        texts.push($(this).text());
      });
      for (var i = 0; i < data.length; i++) {
        if (jQuery.inArray(data[i], texts) !== -1) continue;
        var li = $('<li class="list-group-item py-1"></li>');
        li.text(data[i]);
        li.on("click", function() {
          $("#search-box").val($(this).text());
          $("#suggestion-box").empty();
          $("#add-button").attr("disabled", false);
        });
        li.appendTo(ul);
      }
    }
  });
}

function linkToSchedule() {
	var form = $('<form id="schedule-form" method="POST" action="integrated_schedule.jsp" style="display:none;"></form>');
	var input = $('<input type="text" name="username">');
	input.val($('#username').text());
	input.appendTo(form);
	form.appendTo($('body'));
	form.submit();
}

