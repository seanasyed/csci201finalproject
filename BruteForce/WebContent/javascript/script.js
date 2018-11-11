$(document).ready(function() {
  $("#add-button").click(function(event) {
    var keyword = $("#search-box").val();
    addClassToList(keyword);
    $("#search-box").val("");
    $("#add-button").attr("disabled", true);
  });
  $("#add-button").attr("disabled", true);
  $("#submit-button").click(function(event) {
    checkClassListOnServer();
  });
  $("#search-box").keyup(function() {
    $("#add-button").attr("disabled", true);
    getSuggestions();
  });
});
/*
 * FUNCTIONS FOR ADDING AN ITEM TO THE LIST
 * 
 */
function addClassToList(className) {
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
  $("#class-list").append(item);
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

var checkClassListOnServer = () => {
  var classList = [];
  for (var i = 0; i < $("#class-list").children().length; i++) {
    var text = $("#class-list").children()[i].innerText;
    text = text.replace(/\n/gi, "");
    classList.push(text);
  }
  var classListJSON = JSON.stringify(classList);

  $.ajax({
    url: "BruteForce",
    contentType: "application/json",
    dataType: "json",
    data: {
      callType: "check_class_list",
      classList: classListJSON
    },
    success: function(result) {
      //Result must include:
      //Whether the algorithm was successful
      //If it was, result must also include the optimized schedule
      console.log(result);
    }
  });
};

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
      $("#class-list li span").each(function() {
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

  // Loop through all list items, and hide those who don't match the search query
}
