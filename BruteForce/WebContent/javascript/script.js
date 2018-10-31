$(document).ready(function() {
  $('#add-button').click(function(event) {
    var keyword = $('#search-box').val();
    addClassToList(keyword);
    $('#search-box').val('');
  });
  $('#submit-button').click(function(event) {
    checkClassListOnServer();
  });
  $('#search-box').keyup(function() {
    getSuggestions();
  });
});

function addClassToList(className) {
  var item = document.createElement('li');
  item.classList.add('list-group-item');
  item.classList.add('d-flex');
  item.classList.add('justify-content-between');
  var title = document.createElement('span');
  title.innerText = className;
  var button = document.createElement('i');
  button.classList.add('fas');
  button.classList.add('fa-times');
  button.classList.add('my-auto');
  item.appendChild(title);
  item.appendChild(button);
  $('#class-list').append(item);
}
/*
 * FUNCTIONS FOR COMMUNICATING WITH THE SERVER
 * 
 */
var addUser = (email, fname, lname) => {
  $.ajax({
    url: 'BruteForce',
    data: {
      callType: 'add_user',
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
  for (var i = 0; i < $('#class-list').children().length; i++) {
    var text = $('#class-list').children()[i].innerText;
    text = text.replace(/\n/gi, '');
    classList.push(text);
  }
  var classListJSON = JSON.stringify(classList);

  $.ajax({
    url: 'BruteForce',
    contentType: 'application/json',
    dataType: 'json',
    data: {
      callType: 'check_class_list',
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
    url: 'BruteForce',
    data: {
      callType: 'submit_class_list'
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
  var ul = $('#suggestion-box');
  ul.empty();
  var suggestions = ['CSCI', 'PHYS', 'AHIS'];
  // Declare variables
  var input = $('#search-box');
  var filter = input.val().toUpperCase();
  if (filter == '') return;

  // Loop through all list items, and hide those who don't match the search query
  for (var i = 0; i < suggestions.length; i++) {
    if (suggestions[i].substr(0, filter.length) == filter) {
      var li = document.createElement('li');
      li.innerText = suggestions[i];
      li.classList.add('list-group-item');
      ul.append(li);
    }
  }
}
