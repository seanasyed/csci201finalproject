package model;

import java.util.Vector;

public class LectureSection extends Section {
	private Vector<Section> discussions; 
	private Vector<Section> labs; 
	private Vector<Section> quizzes; 
	
	public LectureSection(String sectionID, String session, String type, String startTime, String endTime, String day, String instructor, 
			int numRegistered, int classCapacity, String buildingID, String courseID, String courseName) {
		
		super(sectionID, session, type, startTime, endTime, day, instructor, numRegistered, classCapacity, buildingID, courseID, courseName);
		this.discussions = new Vector<>(); 
		this.labs = new Vector<>(); 
		this.quizzes = new Vector<>(); 
	}
	
	public void addDiscussion(Section discussion) {
		discussions.add(discussion);
	}
	public void addLab(Section lab) {
		labs.add(lab);
	}
	public void addQuiz(Section quiz) {
		quizzes.add(quiz);
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
