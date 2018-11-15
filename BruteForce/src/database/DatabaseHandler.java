package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import model.BuildingCandidate;
import model.Course;
import model.LectureSection;
import model.Section;
import model.User;

//Use Singleton
public class DatabaseHandler {
	private Connection conn = null;
	private ResultSet rs = null;
	private Statement stmt = null;
	private PreparedStatement ps = null;
	
	private static DatabaseHandler dbh;

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/scheduling";

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";

	/*
	 * ---- Private constructor ----
	 */
	public DatabaseHandler() {
		// No code needs here.
	}
	
	public static DatabaseHandler getOneInstance() {
		if (dbh == null) dbh = new DatabaseHandler();
		return dbh;
	}
	
	public void connect() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (stmt != null) {
				stmt = null;
			}
		} catch (SQLException sqle) {
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param buildingID
	 * @return the coordinates for a given buildingID
	 */
	public double[] getLatitudeAndLongitude(String buildingID) {
		double[] coords = new double[2]; 
		try {
			System.out.println("getting latitude starting... ");
			ps = conn.prepareStatement("SELECT * FROM Building WHERE ID=?");
			ps.setString(1, buildingID);
			rs = ps.executeQuery(); 
			System.out.println("getting latitude" + rs);
			while(rs.next()) {
				coords[0] = Double.parseDouble(rs.getString("latitude")); 
				coords[1] = Double.parseDouble(rs.getString("longitude")); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return coords; 
	}
	
	
	/**
	 * Report whether {@code username} matches {@code password}.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean isAuthenticated(String username, String password) {
		
		if (conn == null)
			return false;

		try {
			ps = conn.prepareStatement("SELECT * FROM Student WHERE userName=?");
			ps.setString(1, username);
			//we want to store hashed password
			// 2. Execute SQL query
			rs = ps.executeQuery();
			while (rs.next()) {
				String storedPassword = rs.getString("password");
				return HashPassword.check(password, storedPassword);
			}
			return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}

	/**
	 * Report whether user with {@code username} exists.
	 * 
	 * @param username
	 * @return
	 */
	public boolean userExists(String username) {
		if (conn == null)
			return false;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM Student WHERE userName=?;");
			ps.setString(1, username);
			
			// 2. Execute SQL query
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("userName") != null) return true;
			}
			return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	public void createUser(String username, String password, String fname, String lname) throws SQLException {
		if (conn == null) return;
		String hashedPassword = null;
		try {
			hashedPassword = HashPassword.getSaltedHash(password);
		} catch (Exception e1) {
			System.out.println("Error: "+e1.getMessage());
		}
		try {
			ps = conn.prepareStatement("INSERT INTO Student (userName, password, firstName, lastName, isActive) VALUE (?, ?, ?, ?, 1) ON DUPLICATE KEY UPDATE username=?;");
			ps.setString(1, username);
			//we want to store the hashedpassword in DB
			ps.setString(2, hashedPassword);
			ps.setString(3, fname);
			ps.setString(4, lname);
			ps.setString(5, username);
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Return a user with {@code username} and {@code password}.
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUser(String username, String password) {
		if (conn == null) return null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM Student WHERE userName=? AND password=?;");
			ps.setString(1, username);
			ps.setString(2, password);
			// 2. Execute SQL query
			rs = ps.executeQuery();
			while (rs.next()) {
				return new User(rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"));
			}
			return null;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
	

	/**
	 * Return a course object with {@code number} under {@code major}.
	 * 
	 * @param major
	 * @param number
	 * @return
	 */
	public Course getCourse(String major, String number) {
		if (conn == null) return null;
		
		try {
			ps = conn.prepareStatement("SELECT * from Course JOIN Lecture_Sections ON Course.ID=Lecture_Sections.Course_ID WHERE Course.major=? AND Course.number=?;");
			ps.setString(1, major);
			ps.setString(2, number);
			// 2. Execute SQL query
			rs = ps.executeQuery();
			Course course = null;
			while (rs.next()) {
				if (course == null) {
					course = new Course(rs.getInt("ID"), rs.getString("school"), rs.getString("major"), 
							rs.getString("number"), rs.getFloat("units"), rs.getString("name"), 
							rs.getString("description"), rs.getInt("semester"));
				}
				String courseName = rs.getString("major") + "-" + rs.getString("number");
				String sectionID = rs.getString("sectionID");
				LectureSection lectureSection = new LectureSection(rs.getString("sectionID"), rs.getString("type"), 
								rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
								rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
								rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
				ps = conn.prepareStatement("SELECT * from Discussion_Sections WHERE Lecture_SectionID=?;");
				ps.setString(1, sectionID);
				rs = ps.executeQuery();
				while (rs.next()) {
					Section dis = new Section(rs.getString("sectionID"), rs.getString("type"), 
							rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
							rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
							rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
					System.out.println("Adding discussion:" + rs.getString("sectionID"));
					lectureSection.addDiscussion(dis);
				}
				ps = conn.prepareStatement("SELECT * from Lab_Sections WHERE Lecture_SectionID=?;");
				ps.setString(1, sectionID);
				
				rs = ps.executeQuery();
				while (rs.next()) {
					Section lab = new Section(rs.getString("sectionID"), rs.getString("type"), 
							rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
							rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
							rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
					System.out.println("Adding lab:" + rs.getString("sectionID"));
					lectureSection.addLab(lab);
				}
				ps = conn.prepareStatement("SELECT * from Quiz_Sections WHERE Lecture_SectionID=?;");
				ps.setString(1, sectionID);
				
				rs = ps.executeQuery();
				while (rs.next()) {
					Section quiz = new Section(rs.getString("sectionID"), rs.getString("type"), 
							rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
							rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
							rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
					System.out.println("Adding quiz:" + rs.getString("sectionID"));
					lectureSection.addQuiz(quiz);
				}
				course.addLectureSection(lectureSection);
			}
			System.out.println("Before returning a course:");
			System.out.println("LectureSections # :" + course.getLectureSections().size());
			Vector<LectureSection> sections = course.getLectureSections();
			for (int i = 0; i < sections.size(); i++) {
				System.out.println("Lecture: " + sections.get(i).getSectionID());
				System.out.println("Discussion#:" + sections.get(i).getDiscussions().size());
				System.out.println("Lab#:" + sections.get(i).getLabs().size());
			}
			//System.out.println("Before returning a course:");
			return course;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Return a list of course names or null.
	 * 
	 * @param keyword
	 * @return
	 */
	public ArrayList<String> getCourseNames(String keyword) {
		
		if (conn == null) return null;
		ArrayList<String> courses = new ArrayList<>();
		try {
			ps = conn.prepareStatement("SELECT major, number FROM Course WHERE major LIKE ?;");
			ps.setString(1, keyword + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				courses.add(rs.getString("major") + "-" + rs.getString("number"));
			}
			ps = conn.prepareStatement("SELECT major, number FROM Course WHERE major LIKE ?;");
			ps.setString(1, keyword + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				courses.add(rs.getString("major") + "-" + rs.getString("number"));
			}
			return courses;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public void createSchedule(String username, ArrayList<String> sectionIDs) throws SQLException {
		
		if (conn == null) return;
		try {
			String sql = "INSERT INTO Schedule (studentUserName ";
			for (int i = 0; i < sectionIDs.size(); i++) {
				sql += ", sectionID" + String.valueOf(i+1);
			}
			sql += ") VALUE (?";
			for (int i = 0; i < sectionIDs.size(); i++) {
				sql += ", ?";
			}
			sql += ") ON DUPLICATE KEY UPDATE ";
			for (int i = 0; i < 10; i++) {
				sql += "sectionID" + String.valueOf(i+1) + "=?";
				if (i < 9) sql += ",";
			}
			sql += ";";
			System.out.println("statement: " + sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			for (int i = 0; i < sectionIDs.size(); i++) {
				ps.setString(i+2, sectionIDs.get(i));
			}
			for (int i = 0; i < sectionIDs.size(); i++) {
				ps.setString(i + sectionIDs.size()+2, sectionIDs.get(i));
			}
			for (int i = sectionIDs.size(); i < 10; i++) {
				ps.setString(i + sectionIDs.size()+2, null);
			}
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public String getCourseNameByID(String courseID) {
		
		if (conn == null) return null;
		ResultSet temp = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM Course WHERE ID=?;");
			ps.setString(1, courseID);
			temp = ps.executeQuery();
			while (temp.next()) {
				String major = temp.getString("major");
				String number = temp.getString("number");
				if (major != null && number != null) return major + "-" + number;
			}
			return null;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public Section getSectionByID(String ID) {
		if (conn == null) return null;
		
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM Lecture_Sections WHERE sectionID=?;");
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while (rs.next()) {
				String courseID = rs.getString("Course_ID");
				String courseName = getCourseNameByID(courseID);
				System.out.println(courseName);
				System.out.println("sectionID is... " + rs.getString("type"));
				LectureSection lectureSection = new LectureSection(rs.getString("sectionID"), rs.getString("type"), 
						rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
						rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
						rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
				if (lectureSection != null) return lectureSection;
			}
			
			ps = conn.prepareStatement("SELECT * FROM Discussion_Sections WHERE sectionID=?;");
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while (rs.next()) {
				String courseID = rs.getString("Course_ID");
				String courseName = getCourseNameByID(courseID);
				Section disSection = new Section(rs.getString("sectionID"), rs.getString("type"), 
						rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
						rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
						rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
				if (disSection != null) return disSection;
			}
			
			ps = conn.prepareStatement("SELECT * FROM Lab_Sections WHERE sectionID=?;");
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while (rs.next()) {
				String courseID = rs.getString("Course_ID");
				String courseName = getCourseNameByID(courseID);
				Section labSection = new Section(rs.getString("sectionID"), rs.getString("type"), 
						rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
						rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
						rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
				if (labSection != null) return labSection;
			}
			
			ps = conn.prepareStatement("SELECT * FROM Quiz_Sections WHERE sectionID=?;");
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while (rs.next()) {
				String courseID = rs.getString("Course_ID");
				String courseName = getCourseNameByID(courseID);
				Section quizSection = new Section(rs.getString("sectionID"), rs.getString("type"), 
						rs.getString("type"), rs.getString("start_time"), rs.getString("end_time"), 
						rs.getString("day"), rs.getString("instructor"),rs.getInt("numRegistered"), 
						rs.getInt("classCapacity"), rs.getString("Building_ID"), rs.getString("Course_ID"), courseName);
				if (quizSection != null) return quizSection;
			}
			return null;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public Vector<Section> getSchedule(String username) throws SQLException {
		if (conn == null) return null;
		Vector<Section> schedule = new Vector<>();
		try {
			ps = conn.prepareStatement("SELECT * FROM Schedule WHERE studentUserName=?;");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				for (int i = 0; i < 10; i++) {
					String sectionID = rs.getString("sectionID" + String.valueOf(i+1));
					System.out.println("finding section: " + sectionID);
					if (sectionID != null) {
						Section section = getSectionByID(sectionID);
						if (section != null) schedule.add(section);
					}
				}
			}
			return schedule;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	public void addCourse(Course course) {
		if (conn == null)
			return;
		String sql = "";
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM Course WHERE major= " + "'" 
					+ course.getMajor() + "' AND number='" + course.getNumber() + "'";
			rs = stmt.executeQuery(sql);
			// Check whether the course has been stored
			if (!rs.next()) {
				sql = course.insertDBString();
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(sql);
		}
	}

	public void addBuilding(BuildingCandidate building) {
		if (conn == null)
			return;
		String sql = "";
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM Building WHERE ID= " + "'" 
					+ building.getID() + "'";
			rs = stmt.executeQuery(sql);
			// Check whether the building has been stored
			if (!rs.next()) {
				sql = building.insertDBString();
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(sql);
		}
	}
	
	public void addSection(Section section) {
		if (conn == null)
			return;
		String sql = "";
		try {
			stmt = conn.createStatement();
			sql = section.getSelectDBString();
			rs = stmt.executeQuery(sql);
			// Check whether the section has been stored
			if (!rs.next()) {
				sql = section.insertDBString();
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(sql);
		}
	}

	/**
	 * Report the course id of {@code course}.
	 * 
	 * @param course
	 * @return either a positive integer, or -1 if not existed, or -2 connection loss.
	 */
	public int getCourseId(Course course) {
		if (conn == null) return -2;
		int ID = -1;
		String sql = "";
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM Course WHERE major= " + "'" 
					+ course.getMajor() + "' AND number='" + course.getNumber() + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ID = Integer.parseInt(rs.getString("ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(sql);
		}
		
		return ID;
	}
	/*
	 * Returns a message(success or error) after registration
	 */
	public String registerSchedule(String username, Vector<Section> schedule) {
		if (conn == null) return null;
		ResultSet rs = null;
		String message = "";
		try {
			for(Section s: schedule) {
				String type = s.getType();
				String sectionID = s.getSectionID();
				/*
				 * CHECK numRegistered the update
				 * If numRegistered >= classCapacity, write an error message
				 * else, register them
				 */
				switch (type) {
				case "Lecture": {
					ps = conn.prepareStatement("SELECT * FROM Lecture_Sections WHERE sectionID=?;");
					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int registeredBefore = 0;
					int classCapacity = 0;
					while(rs.next()) {
						registeredBefore = rs.getInt("numRegistered");
						classCapacity = rs.getInt("classCapacity");
					}
					if (registeredBefore >= classCapacity) message += sectionID + " is already full\n";
					else {
						ps = conn.prepareStatement("UPDATE Lecture_Sections SET numRegistered= CASE WHEN (numRegistered<classCapacity) THEN numRegistered+1 ELSE classCapacity END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}
				break;
				case "Discussion": {
					ps = conn.prepareStatement("SELECT * FROM Discussion_Sections WHERE sectionID=?;");
					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int registeredBefore = 0;
					int classCapacity = 0;
					while(rs.next()) {
						registeredBefore = rs.getInt("numRegistered");
						classCapacity = rs.getInt("classCapacity");
					}
					if (registeredBefore >= classCapacity) message += sectionID + " is already full\n";
					else {
						ps = conn.prepareStatement("UPDATE Discussion_Sections SET numRegistered= CASE WHEN (numRegistered<classCapacity) THEN numRegistered+1 ELSE classCapacity END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}	
				break;
				case "Lab": {
					ps = conn.prepareStatement("SELECT * FROM Lab_Sections WHERE sectionID=?;");

					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int registeredBefore = 0;
					int classCapacity = 0;
					while(rs.next()) {
						registeredBefore = rs.getInt("numRegistered");
						classCapacity = rs.getInt("classCapacity");
					}
					if (registeredBefore >= classCapacity) message += sectionID + " is already full\n";
					else {
						ps = conn.prepareStatement("UPDATE Lab_Sections SET numRegistered= CASE WHEN (numRegistered<classCapacity) THEN numRegistered+1 ELSE classCapacity END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}
				break;
				case "Quiz": {
					ps = conn.prepareStatement("SELECT * FROM Quiz_Sections WHERE sectionID=?;");

					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int registeredBefore = 0;
					int classCapacity = 0;
					while(rs.next()) {
						registeredBefore = rs.getInt("numRegistered");
						classCapacity = rs.getInt("classCapacity");
					}
					if (registeredBefore >= classCapacity) message += sectionID + " is already full\n";
					else {
						ps = conn.prepareStatement("UPDATE Quiz_Sections SET numRegistered= CASE WHEN (numRegistered<classCapacity) THEN numRegistered+1 ELSE classCapacity END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}
				break;
				}
			}
			if (message.equals("")) {
				ArrayList<String> sectionIDs = new ArrayList<>();
				for (Section s: schedule) {
					sectionIDs.add(s.getSectionID());
				}
				//CAN ONLY CREATE A NEW SCHEDULE IF A STUDENT SUCCESSFULLY UNREGISTERED FROM ORIGINAL SCHEDULE
				if (unregister(username)) {
					createSchedule(username, sectionIDs);
					message = "Successfully registered";
				} else {
					message = "Couldn't unregister from sections from previous schedule.";
				}
			}
			return message;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = "An error has occurred. Please register again.";
			return message;
		}
		
		
		
	}
	public boolean unregister(String username) {
		if (conn == null) return false;
		ResultSet rs = null;
		try {
			Vector<Section> schedule = getSchedule(username);
			for (Section s: schedule) {
				String type = s.getType();
				String sectionID = s.getSectionID();
				switch (type) {
				case "Lecture": {
					ps = conn.prepareStatement("SELECT * FROM Lecture_Sections WHERE sectionID=?;");
					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int numRegistered = 0;
					while(rs.next()) {
						numRegistered = rs.getInt("numRegistered");
					}
					if (numRegistered > 0) {
						ps = conn.prepareStatement("UPDATE Lecture_Sections SET numRegistered= CASE WHEN (numRegistered>0) THEN numRegistered-1 END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}
				break;
				case "Discussion": {
					ps = conn.prepareStatement("SELECT * FROM Discussion_Sections WHERE sectionID=?;");
					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int numRegistered = 0;
					while(rs.next()) {
						numRegistered = rs.getInt("numRegistered");
					}
					if (numRegistered > 0) {
						ps = conn.prepareStatement("UPDATE Discussion_Sections SET numRegistered= CASE WHEN (numRegistered>0) THEN numRegistered-1 END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}	
				break;
				case "Lab": {
					ps = conn.prepareStatement("SELECT * FROM Lab_Sections WHERE sectionID=?;");
					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int numRegistered = 0;
					while(rs.next()) {
						numRegistered = rs.getInt("numRegistered");
					}
					if (numRegistered > 0) {
						ps = conn.prepareStatement("UPDATE Lab_Sections SET numRegistered= CASE WHEN (numRegistered>0) THEN numRegistered-1 END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}
				break;
				case "Quiz": {
					ps = conn.prepareStatement("SELECT * FROM Quiz_Sections WHERE sectionID=?;");
					ps.setString(1, sectionID);
					rs = ps.executeQuery();
					int numRegistered = 0;
					while(rs.next()) {
						numRegistered = rs.getInt("numRegistered");
					}
					if (numRegistered > 0) {
						ps = conn.prepareStatement("UPDATE Quiz_Sections SET numRegistered= CASE WHEN (numRegistered>0) THEN numRegistered-1 END WHERE sectionID=?;");
						ps.setString(1, sectionID);
						ps.executeUpdate();
					}
				}
				break;
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
