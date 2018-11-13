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
  for (var i = 0; i < $("#course-list").children().length; i++) {
    var text = $("#course-list").children()[i].innerText;
    text = text.replace(/\n/gi, "");
    courseList.push(text);
  }
  var courseListJSON = JSON.stringify(courseList);
  var username = $('#username').text();
  console.log('username:' + username);
  $.ajax({
    url: "BruteForce",
    data: {
      callType: "check_schedule",
      username: username,
      startTime: startTime,
      endTime: endTime,
      courseList: courseListJSON
    },
    success: function(result) {
      if (result.valid == 'false') {
    	  alert('Invalid Schedule');
    	  $('#course-list li').remove();
    	  $('#start-time').val('');
    	  $('#end-time').val('');
      } else {
    	  //console.log(result.courses);
    	  var courses = JSON.parse(result.courses);
    	  console.log(result.courses);
    	  for(var i=0; i <courses.length; i++) {
    		  console.log(courses[i]);
    	  }
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
	  var courseListJSON = JSON.stringify(courseList);

	  $.ajax({
	    url: "BruteForce",
	    data: {
	      callType: "submit_schedule",
	      startTime: startTime,
	      endTime: endTime,
	      courseList: courseListJSON
	    },
	    success: function(result) {
	      //Result must include:
	      //Whether the algorithm was successful
	      //If it was, result must also include the optimized schedule
	      console.log(result);
	    }
	  });
};

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
//function getSchedule(){
//     var username = $('#username').text();
//     console.log(username);
//     $.ajax({
//         url: "BruteForce",
//         data: {
//           callType: "submit_schedule",
//           username: username
//         },
//         success: function(result) {
//             console.log(result);
//             console.log(username);
//             var courses = result.courses;
//             console.log(courses);
//             var data = JSON.parse(result);
//             
//             var courses = {};
//             for (var i=0; i<data.response.courses.length; i++){
//                 var current_course = data.response.courses[i];
//             }
//         }
//       });
// };
