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
1. TODO: Add Your Step 1
2. TODO: Add Your Step 2

## Limitations
1. TODO: Add Your Limitation 1

## About Schedule Optimization
Our scheduling algorithm is a derivative of the N-Queens problem and uses it to create a valid schedule without any collisions. Factors taken into account include class sizes, as well as section dependencies that may arise due to the various nuances of courses offered. For instance, lecture sections may have specific discussion, lab, or quiz sections. Constraints unique to our application include a manual time constraint specified by the user, as well as a weekly walking distance constraint. A schedule is not considered valid unless it conforms to both of those constraints. 
