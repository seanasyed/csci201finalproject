/**
 * Group 14
 * 
 * CP: Aya Shimizu (ashimizu@usc.edu)
 * Yiyang Hou (yiyangh@usc.edu)
 * Sean Syed (seansyed@usc.edu)
 * Eric Duguay (eduguay@usc.edu)
 * Xing Gao (gaoxing@usc.edu)
 * Sangjun Lee (sangjun@usc.edu)
 * 
 */

package model;

import java.util.Vector;

public class LectureSection extends Section {
	
	/*
	 * ---- Private members ----
	 */
	
	private Vector<Section> discussions; 
	private Vector<Section> labs; 
	private Vector<Section> quizzes; 
	
	/*
	 * ---- Constructors ----
	 */
	
	public LectureSection(String sectionID, String session, String type, String startTime, String endTime, String day, String instructor, 
			int numRegistered, int classCapacity, String buildingID, String courseID, String courseName) {
		
		super(sectionID, session, type, startTime, endTime, day, instructor, 
				numRegistered, classCapacity, buildingID, courseID, courseName);
		this.discussions = new Vector<>(); 
		this.labs = new Vector<>(); 
		this.quizzes = new Vector<>(); 
	}
	
	/**
	 * Report if the section contains the correct lecture number.
	 * 
	 * @param section
	 * @return
	 */
	public boolean isInLecture(Section section) {
		return !section.isLecture() && section.getLectureSection_ID().equals(getSectionID());
	}
	
	/*
	 * ---- Add sections into vector ----
	 */
	
	public void addDiscussion(Section section) {
		//if (isInLecture(section))
			discussions.add(section);
	}
	public void addLab(Section section) {
		//if (isInLecture(section))
			labs.add(section);
	}
	public void addQuiz(Section section) {
		//if (isInLecture(section))
			quizzes.add(section);
	}
	
	/*
	 * ---- Getters and setters ---- 
	 */
	
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
