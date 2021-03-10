package view;

import java.time.LocalDate;

import com.sun.javafx.geom.Rectangle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Course;
import model.Module;

public class CreateProfilePane extends GridPane {

	private TextField pNumberInput, firstNameInput, surnameInput, emailInput;
	private Button createProfile;
	private ComboBox<Course> courseCMB;
	private DatePicker subDateInput;
	
	public CreateProfilePane() {
		
		this.setPadding(new Insets(10,10,10,10));
		this.setAlignment(Pos.CENTER);
		this.setVgap(15);
		this.setHgap(25);
		
		//Creating the combo box for course selection
		this.courseCMB = new ComboBox<Course>();

		// Creating the combo box for the submission date
		this.subDateInput = new DatePicker();
		subDateInput.getEditor().setDisable(true);
		subDateInput.setValue(LocalDate.now());
		subDateInput.setStyle("-fx-opacity: 1");
		subDateInput.getEditor().setStyle("-fx-opacity: 1");
		
		// Create text fields for inputting information
		this.pNumberInput = new TextField();
		this.firstNameInput = new TextField();
		this.surnameInput = new TextField();
		this.emailInput = new TextField();
		
		// Create labels for each text field
		Label courseLabel = new Label("Select Course:");
		Label pNumberLabel = new Label("P-Number:");
		Label firstNameLabel = new Label("First Name:");
		Label surnameLabel = new Label("Surname:");
		Label emailLabel = new Label("Email:");
		Label subDateLabel = new Label("Submission Date:");
		
		// Create button to submit info and generate profile
		this.createProfile = new Button("Create Profile");
		
		// Adding the labels to the grid pane
		this.add(courseLabel, 0, 0);
		this.add(pNumberLabel, 0, 1);
		this.add(firstNameLabel, 0, 2);
		this.add(surnameLabel, 0, 3);
		this.add(emailLabel, 0, 4);
		this.add(subDateLabel, 0, 5);
		
		// Adding the input fields to the grid pane
		this.add(courseCMB, 1, 0);
		this.add(pNumberInput, 1, 1);
		this.add(firstNameInput, 1, 2);
		this.add(surnameInput, 1, 3);
		this.add(emailInput, 1, 4);
		this.add(subDateInput, 1, 5);
		
		// Adding the create profile button to the gridpane
		this.add(createProfile, 1, 6);
	}
	
	public void setCourseInput(Course c) {
		courseCMB.getSelectionModel().select(c);
	}
	public void setPNumberInput(String s) {
		pNumberInput.setText(s);
	}
	public void setFirstNameInput(String s) {
		firstNameInput.setText(s);
	}
	public void setSurnameInput(String s) {
		surnameInput.setText(s);
	}
	public void setEmailInput(String s) {
		emailInput.setText(s);
	}
	public void setSubDateInput(LocalDate d) {
		subDateInput.setValue(d);
	}
	public String getPNumber() {
		return pNumberInput.getText();
	}
	public String getFirstName() {
		return firstNameInput.getText();
	}
	public String getSurname() {
		return surnameInput.getText();
	}
	public String getEmail() {
		return emailInput.getText();
	}
	public Course getCourse() {
		return courseCMB.getValue();
	}
	public LocalDate getSubDate() {
		return subDateInput.getValue();
	}
	public ComboBox<Course> getCourseCMB() {
		return courseCMB;
	}
	public void populateComboBoxWithCourses(Course[] courses) {
		courseCMB.getItems().addAll(courses);
		courseCMB.getSelectionModel().select(0);
	}
	public void clearComboBox() {
		courseCMB.getItems().clear();
	}
	public void addCreateProfileHandler(EventHandler<ActionEvent> handler) {
		createProfile.setOnAction(handler);
	}

}
