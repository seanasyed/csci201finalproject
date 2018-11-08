package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Course;

public class DatabaseHandler {
	
	private Connection getConnection() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost/mydb?useSSL=false", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void createUser(String username, String password, String fname, String lname) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO Student (userName, password, firstName, lastName) VALUE (?, ?, ?, ?) ON DUPLICATE KEY UPDATE username=?;");
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
	public void addCourse(int ID, String school, String major, String number, int units, String name, String description, int semester) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO Course (ID, school, major, number, units, name, description, semester) VALUE (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE ID=?;");
			ps.setInt(1, ID);
			ps.setString(2, school);
			ps.setString(3, major);
			ps.setString(4, number);
			ps.setInt(5, units);
			ps.setString(6, name);
			ps.setString(7, description);
			ps.setInt(8, semester);
			ps.setInt(9, ID);
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
	public Course getCourseByName(String courseName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM <TABLE> WHERE courseName=?;");
			ps.setString(1, courseName);
			
			// 2. Execute SQL query
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				
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
}
