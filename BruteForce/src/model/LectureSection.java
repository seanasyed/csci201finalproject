package model;

import java.util.Vector;

public class LectureSection extends Section {
	private Vector<Section> discussions; 
	private Vector<Section> labs; 
	private Vector<Section> quizzes; 
	
	public LectureSection(String sectionID, String session, String type, String time, String day, String instructor, 
			int numRegistered, int classCapacity, String buildingID, String courseID, Vector<Section> discussions, 
			Vector<Section> labs, Vector<Section> quizzes) {
		
		super(sectionID, session, type, time, day, instructor, numRegistered, classCapacity, buildingID, courseID);
		this.discussions = discussions; 
		this.labs = labs; 
		this.quizzes = quizzes; 
	}
	
	public Vector<Section> getDiscussions() {
		return discussions; 
	}
	
	public Vector<Section> getLabs() {
		return labs; 
	}
	
	public Vector<Section> getQuizzes() {
		return quizzes; 
	}
}
