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
function addCourseToList(className) {
  //CREATE A LIST ITEM
  var item = $(
    '<li id="' +
      className.toUpperCase() +
      '"class="list-group-item d-flex justify-content-between"></li>'
  );
  var form = $(
    '<form id="' +
      className +
      'Form" target="_blank" name="detailForm" method= "GET" action = "detail.jsp" ></form>'
  );
  var input = $(
    '<input style="display: none;" name="courseName" value="' +
      className +
      '"></input>"'
  );
  input.appendTo(form);
  var title = $("<span></span>");
  title.text(className);

  //SETUP DELETE BUTTON
  var button = $('<i class="fas fa-times my-auto">');
  button.on("click", function() {
    $("#" + className.toUpperCase()).remove();
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
  var startTime = $("#start-time").val();
  var endTime = $("#end-time").val();

  var courseList = [];
  for (var i = 0; i < $("#course-list").children().length; i++) {
    var text = $("#course-list").children()[i].innerText;
    text = text.replace(/\n/gi, "");
    courseList.push(text);
  }
  console.log(courseList);
  var courseListJSON = JSON.stringify(courseList);
  console.log(courseListJSON);
  $.ajax({
    url: "BruteForce",
    data: {
      callType: "check_schedule",
      startTime: startTime,
      endTime: endTime,
      courseList: courseListJSON
    },
    success: function(result) {
      //RESULT WILL CONTAIN:
      //valid: true or false
      //messages: ["a", "b"]
      //courses: {
      //  "CSCI-201": <sectionID>
      //}
      //if "valid" == "true", change check-button into submit
      //
      console.log(result);
    }
  });
}

var submitClassListToServer = () => {
  $.ajax({
    url: "BruteForce",
    data: {
      callType: "submit_class_list"
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
