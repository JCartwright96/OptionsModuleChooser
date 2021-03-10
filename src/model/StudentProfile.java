package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;



public class StudentProfile implements Serializable {
	
	private String pNumber;
	private Name studentName;
	private String email;
	private LocalDate date;
	private Course course;
	private Set<Module> selectedModules;
	
	public StudentProfile() {
		pNumber = "";
		studentName = new Name();
		email = "";
		date = null;
		course = null;
		selectedModules = new TreeSet<Module>();
	}
	
	public String getPnumber() {
		return pNumber;
	}
	
	public void setPnumber(String pNumber) {
		this.pNumber = pNumber;
	}
	
	public Name getStudentName() {
		return studentName;
	}
	
	public void setStudentName(Name studentName) {
		this.studentName = studentName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getSubmissionDate() {
		return date;
	}
	
	public void setSubmissionDate(LocalDate date) {
		this.date = date;
	}
	
	public Course getCourseOfStudy() {
		return course;
	}
	
	public void setCourseOfStudy(Course course) {
		this.course = course;
	}
		
	public boolean addToSelectedModules(Module m) {
		return selectedModules.add(m);
	}
	
	public Set<Module> getAllSelectedModules() {
		return selectedModules;
	}
	
	public void clearAllSelectedModules() {
		selectedModules.clear();
	}
	
	@Override
	public String toString() {
		return "StudentProfile:[pNumber=" + pNumber + ", studentName="
				+ studentName + ", email=" + email + ", date="
				+ date + ", course=" + course.actualToString() 
				+ ", selectedModules=" + selectedModules + "]";
	}
	
}
