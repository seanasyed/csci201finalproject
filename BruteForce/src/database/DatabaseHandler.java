package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Course;
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
		try {
			conn = getConnection();
			System.out.println("connected to the database");
			ps = conn.prepareStatement("INSERT INTO Student (userName, password, firstName, lastName, isActive) VALUE (?, ?, ?, ?, 1) ON DUPLICATE KEY UPDATE username=?;");
			ps.setString(1, username);
			ps.setString(2, password);
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
	public Course getCourseByName(String courseName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM Course WHERE courseName=?;");
			ps.setString(1, courseName);
			
			// 2. Execute SQL query
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				return new Course(resultSet.getInt("ID"), resultSet.getString("school"), resultSet.getString("major"), 
						resultSet.getString("number"), resultSet.getFloat("units"), resultSet.getString("name"), 
						resultSet.getString("description"), resultSet.getInt("semester"));
			}
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
		return null;
	}
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
