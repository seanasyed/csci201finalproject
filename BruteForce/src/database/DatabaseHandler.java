package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Course;
import model.LectureSection;
import model.Section;
import model.User;

public class DatabaseHandler {
	
	private Connection getConnection() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost/scheduling?useSSL=false", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * RETURN VALUE
	 * TRUE: AUTHENTICATED
	 * FALSE: NOT AUTHENTICATED
	 */
	public boolean authenticateUser(String username, String password) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM Student WHERE userName=? AND password=?;");
			ps.setString(1, username);
			//we want to store hashed password
			ps.setString(2, password);
			// 2. Execute SQL query
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString("userName") != null) return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	/*
	 * RETURN VALUE
	 * TRUE: USER EXISTS
	 * FALSE: USER DOES NOT EXIST
	 */
	public boolean userExists(String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM Student WHERE userName=?;");
			ps.setString(1, username);
			
			// 2. Execute SQL query
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString("userName") != null) return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	public void createUser(String username, String password, String fname, String lname) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		//hash the password
		HashPassword hp= new HashPassword();
		String hashedPassword = null;
		try {
			hashedPassword = hp.getSaltedHash(password);
		} catch (Exception e1) {
			System.out.println("Error: "+e1.getMessage());
		}
		try {
			conn = getConnection();
			System.out.println("connected to the database");
			ps = conn.prepareStatement("INSERT INTO Student (userName, password, firstName, lastName, isActive) VALUE (?, ?, ?, ?, 1) ON DUPLICATE KEY UPDATE username=?;");
			ps.setString(1, username);
			//we want to store the hashedpassword in DB
			ps.setString(2, hashedPassword);
			ps.setString(3, fname);
			ps.setString(4, lname);
			ps.setString(5, username);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
	/*
	 * RETURN VALUE
	 * USER: USER INSTANCE
	 * NULL: USER INSTANCE UNABLE TO BE CREATED
	 */
	public User getUser(String username, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM Student WHERE userName=? AND password=?;");
			ps.setString(1, username);
			ps.setString(2, password);
			// 2. Execute SQL query
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				return new User(resultSet.getString("username"), resultSet.getString("firstName"), resultSet.getString("lastName"));
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
//	
//	public void addCourse(int ID, String school, String major, String number, float units, String name, String description, int semester) {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		try {
//			conn = getConnection();
//			ps = conn.prepareStatement("INSERT INTO Course (ID, school, major, number, units, name, description, semester) VALUE (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE ID=?;");
//			ps.setInt(1, ID);
//			ps.setString(2, school);
//			ps.setString(3, major);
//			ps.setString(4, number);
//			ps.setFloat(5, units);
//			ps.setString(6, name);
//			ps.setString(7, description);
//			ps.setInt(8, semester);
//			ps.setInt(9, ID);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException sqle) {
//				System.out.println("sqle: " + sqle.getMessage());
//			}
//		}
//	}
	
	/*
	 * RETURN VALUE
	 * COURSE: COURSE INSTANCE
	 * NULL: COURSE INSTANCE UNABLE TO BE CREATED
	 */
	public Course getCourse(String major, String number) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * from Course JOIN Lecture_Sections ON Course.ID=Lecture_Sections.Course_ID WHERE Course.major=? AND Course.number=?;");
			ps.setString(1, major);
			ps.setString(2, number);
			// 2. Execute SQL query
			resultSet = ps.executeQuery();
			Course course = null;
			while (resultSet.next()) {
				
				if (course == null) {
					course = new Course(resultSet.getInt("ID"), resultSet.getString("school"), resultSet.getString("major"), 
							resultSet.getString("number"), resultSet.getFloat("units"), resultSet.getString("name"), 
							resultSet.getString("description"), resultSet.getInt("semester"));
				}
				String sectionID = resultSet.getString("sectionID");
				LectureSection lectureSection = new LectureSection(resultSet.getString("sectionID"), resultSet.getString("type"), 
								resultSet.getString("type"), resultSet.getString("start_time"), resultSet.getString("end_time"), 
								resultSet.getString("day"), resultSet.getString("instructor"),resultSet.getInt("numRegistered"), 
								resultSet.getInt("classCapacity"), resultSet.getString("Building_ID"), resultSet.getString("Course_ID"));
				ps = conn.prepareStatement("SELECT * from Disscussion_Sections WHERE Lecture_SectionID=?;");
				ps.setString(1, sectionID);
				ResultSet disResultSet = ps.executeQuery();
				while (disResultSet.next()) {
					Section dis = new Section(disResultSet.getString("sectionID"), disResultSet.getString("type"), 
							disResultSet.getString("type"), disResultSet.getString("start_time"), disResultSet.getString("end_time"), 
							disResultSet.getString("day"), disResultSet.getString("instructor"),disResultSet.getInt("numRegistered"), 
							disResultSet.getInt("classCapacity"), disResultSet.getString("Building_ID"), disResultSet.getString("Course_ID"));
					lectureSection.addDiscussion(dis);
				}
				ps = conn.prepareStatement("SELECT * from Lab_Sections WHERE Lecture_SectionID=?;");
				ps.setString(1, sectionID);
				ResultSet labResultSet = ps.executeQuery();
				while (labResultSet.next()) {
					Section lab = new Section(labResultSet.getString("sectionID"), labResultSet.getString("type"), 
							labResultSet.getString("type"), labResultSet.getString("start_time"), labResultSet.getString("end_time"), 
							labResultSet.getString("day"), labResultSet.getString("instructor"),labResultSet.getInt("numRegistered"), 
							labResultSet.getInt("classCapacity"), labResultSet.getString("Building_ID"), labResultSet.getString("Course_ID"));
					lectureSection.addLab(lab);
				}
				ps = conn.prepareStatement("SELECT * from Quiz_Sections WHERE Lecture_SectionID=?;");
				ps.setString(1, sectionID);
				ResultSet quizResultSet = ps.executeQuery();
				while (quizResultSet.next()) {
					Section quiz = new Section(quizResultSet.getString("sectionID"), quizResultSet.getString("type"), 
							quizResultSet.getString("type"), quizResultSet.getString("start_time"), quizResultSet.getString("end_time"), 
							quizResultSet.getString("day"), quizResultSet.getString("instructor"),quizResultSet.getInt("numRegistered"), 
							quizResultSet.getInt("classCapacity"), quizResultSet.getString("Building_ID"), quizResultSet.getString("courseID"));
					lectureSection.addQuiz(quiz);;
				}
				course.addLectureSection(lectureSection);
			}
			return course;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	/*
	 * RETURN VALUE
	 * ArrayList<String>: NAMES OF COURSES THAT CONTAIN KEYWORD AS PREFIX
	 * NULL: NO RESULT
	 */
	public ArrayList<String> getCourseNames(String keyword) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		ArrayList<String> courses = new ArrayList<>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT major, number FROM Course WHERE major LIKE ?;");
			ps.setString(1, "%" + keyword + "%");
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				courses.add(resultSet.getString("major") + "-" + resultSet.getString("number"));
			}
			return courses;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
}
