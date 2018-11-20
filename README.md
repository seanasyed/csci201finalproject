# csci201finalproject
## Team Info
Team Name: Brute Force (Group 14)

Members: 
	CP: Aya Shimizu (ashimizu@usc.edu)
	Yiyang Hou (yiyangh@usc.edu)
	Sean Syed (seansyed@usc.edu)
	Eric Duguay (eduguay@usc.edu)
	Xing Gao (gaoxing@usc.edu)
	Sangjun Lee (sangjun@usc.edu)

Semester: Fall 2018 (20183)

Environment: `Java SE 1.8` (If your JRE library is 10.0, please re-build your path)

## Project Description
The main goal of our project is to create an web application which is able to parse the USC Course Catalog and allow the user to select preferred classes for the semester. A user should be able to specify the times within a given day that they would either like to attend class or have a break. Users will be able to register based on a predetermined priority scale, and will be given a schedule based on courses in which there is space available for them at the time of their respective registration.

## How to Run the Program
1. Launch MySQL Workbench and open and run the following script files respectively:
- SampleBuilding.sql
- SampleCourse.sql
- SampleLecture.sql
- SampleDiscussion.sql
- SampleLab.sql
- SampleQuiz.sql
2. Ensure all of the scripts were executed without any errors.
3. Launch Eclipse and open the project “BruteForce” in “csci201finalproject” folder.
4. Go to WebContent and open “login.jsp”
5. Run “login.jsp” on server at localhost on port 8080.
6. Upon successfully opening “login.jsp”, click on
“register” button.
7. Fill out the form and register the account.
8. Upon successfully logging into the system, “index.jsp” will open.
9. Search course names on the search box.
10. Click the course in a suggestion box to autocomplete the search box.
11. Click on “add” button and the selected course will be added to the course list.
12. Click on “check” button to check if the schedule is valid.
13. Click on “submit” button if the schedule is valid. Otherwise, return to step 9.
14. Upon successfully submitting the schedule, click on “My Schedule” button.
15. Perform the following tests to ensure that all the functionalities are working:
16. A schedule will display if the user has the schedule.
17. Upon successfully having displayed the schedule, click on “Download Schedule” button.
18. The button will link to a download of an ics file.
19. The user will receive an email with the ics file attached.

## Limitations
### Front-End
#### Connection between the client and the server is not based on sessions and tokens.

## About Program Functionalities
The functionalities of our program include login/register users, search courses, arrange schedule based on starting time, end time and weekly walk distance, check whether the desired schedule is valid, submit valid schedule, view valid schedule in a time table, and download the schedule into an ICS format. Our program runs as a web application, where users can enjoy responsive, easy-to-use interfaces to plan their desired schedule for semesters in USC. If their desired schedule is not valid, they will be unregistered from the database and arrange a new schedule. Users can view their schedules on the website or download it to view in exterior applications such as Google Calendar or Apple Calendar.

## About Schedule Optimization
Our scheduling algorithm is a derivative of the N-Queens problem and uses it to create a valid schedule without any collisions. Factors taken into account include class sizes, as well as section dependencies that may arise due to the various nuances of courses offered. For instance, lecture sections may have specific discussion, lab, or quiz sections. Constraints unique to our application include a manual time constraint specified by the user, as well as a weekly walking distance constraint. A schedule is not considered valid unless it conforms to both of those constraints. 
