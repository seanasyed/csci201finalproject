<!--
	Name: Sangjun Lee
	USC ID: 1202289016 
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
    crossorigin="anonymous">
    </script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
    crossorigin="anonymous">

  <title>Brute Force</title>
</head>

<body>
  <h1 class="display-4  text-center bg-cardinal">
    <span class="align-text-top text-gold"><strong>Brute</strong></span>
    <span class="align-text-top text-white">Force</span>
  </h1>
  <nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid text-center">
      <span class="mx-auto">Course Information</span>
    </div>
  </nav>
  <h1>${param.courseName} Status</h1>
  <table>
    <tr>
      <th style="width: 250px; text-align:left;">Section</th>
      <th style="text-align: left;">Opening Seats</th>
    </tr>
    <tr>
      <td>Event Title</td>
      <td></td>
    </tr>
    <tr>
      <td colspan="2" id="button-row">
        <button id="close-button" type="button" onclick="window.close();">Close</button>
      </td>
    </tr>
  </table>
  <script>
    var socket;
    connectToServer();
    function connectToServer() {
      socket = new WebSocket("ws://localhost:8080/BruteForce/ss");
      socket.onopen = function (event) {
        //document.getElementById("mychat").innerHTML += "Connected <br>";
      };
      socket.onmessage = function (event) {
        //document.getElementById("mychat").innerHTML += event.data + "<br>";
      };
      socket.onclose = function (event) {
        //document.getElementById("mychat").innerHTML += "Disconnected <br>";
      };
    }
    function sendMessage() {
      // socket.send("Sangjun1: " + document.chatform.message.value);
      return false;
    }
/*
  </script>
</body>

</html>