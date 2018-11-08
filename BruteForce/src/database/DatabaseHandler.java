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
			//ps.setString(1, x);
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
				//Course c = new Course(resultSet.getString("courseName"))
				//return Course;
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
