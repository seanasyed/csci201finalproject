package model;

import java.util.Vector;

public class LectureSection {
	private Vector<Section> discussions; 
	private Vector<Section> labs; 
	private Vector<Section> quizzes; 
	
	public LectureSection(Vector<Section> discussions, Vector<Section> labs, Vector<Section> quizzes) {
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
