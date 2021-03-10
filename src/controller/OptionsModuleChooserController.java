package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.Course;
import model.Delivery;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.ControlPane;
import view.CreateProfilePane;
import view.ModuleSelectorButtons;
import view.OptionsModuleChooserRootPane;
import view.OverviewSelectionPane;
import view.SelectModulesPane;

public class OptionsModuleChooserController{

	//fields to be used throughout the class
	private OptionsModuleChooserRootPane view;
	private StudentProfile model;
	private CreateProfilePane cpp;
	private SelectModulesPane smp;
	private OverviewSelectionPane osp;
	private ControlPane cp;

	public OptionsModuleChooserController(OptionsModuleChooserRootPane view, StudentProfile model) {
		//initialise model and view fields
		this.model = model;
		this.view = view;

		cpp = view.getCreateProfilePane();
		smp = view.getSelectModulesPane();
		osp = view.getOverviewSelectionPane();
		cp = view.getControlPane();

		//populate combobox in create profile pane, e.g. if profilePane represented your create profile pane you could invoke the line below
		cpp.populateComboBoxWithCourses(setupAndRetrieveCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}

	private void attachEventHandlers() {
		cpp.addCreateProfileHandler(new CreateProfileHandler());
		smp.addAddTerm1ModuleHandler(new AddTerm1ModuleHandler());
		smp.addRemoveTerm1ModuleHandler(new RemoveTerm1ModuleHandler());
		smp.addAddTerm2ModuleHandler(new AddTerm2ModuleHandler());
		smp.addRemoveTerm2ModuleHandler(new RemoveTerm2ModuleHandler());
		smp.getModuleSubmitPane().addResetHandler(new ResetButtonHandler());
		smp.getModuleSubmitPane().addSubmitHandler(new SubmitButtonHandler());
		cp.addSelectCPPTabHandler(e -> view.setCenter(cpp));
		cp.addSelectSMPTabHandler(e -> view.setCenter(smp));
		cp.addSelectOSPTabHandler(e -> view.setCenter(osp));
		cp.addAboutHandler(new AboutHandler());
		cp.addExitHandler(e -> Platform.exit());
		cp.addSaveHandler(new SaveHandler());
		cp.addLoadHandler(new LoadHandler());
		osp.addSaveOverviewHandler(new SaveOverviewHandler());
	}

	/**
	 * Changes the selected scene to selectModulesPane
	 * updates current selected tab to be Select Modules
	 * Populates studentprofile in the model with input information
	 */
	private class CreateProfileHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {	

			int count = 0;
			if(!cpp.getPNumber().isEmpty() && cpp.getPNumber().toLowerCase().charAt(0) == 'p' && cpp.getPNumber().length() == 9) {
				count++;
				if(!cpp.getFirstName().isEmpty()) {
					count++;
					if(!cpp.getSurname().isEmpty()) {
						count++;
						if(!cpp.getEmail().isEmpty() && cpp.getEmail().contains("@")) {
							count++;
						}
						else {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Input information Error");
							alert.setHeaderText(null);
							alert.setContentText("Please Enter a valid email address.\n A valid email address should contain the letter '@'.");	
							alert.showAndWait();
						}

					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Input information Error");
						alert.setHeaderText(null);
						alert.setContentText("Please enter a Surname.");	
						alert.showAndWait();
					}
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Input information Error");
					alert.setHeaderText(null);
					alert.setContentText("Please enter a First Name");	
					alert.showAndWait();
				}
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Input information Error");
				alert.setHeaderText(null);
				alert.setContentText("Please Enter a Valid P-Number.\nA P-Number should begin with 'P' and contain a total of 9 characters.");	
				alert.showAndWait();
			}

			if (count == 4) {
				// execute create profile	

				//if(createProfileValidation()) {
				view.updateView(smp);
				cp.setCurrentTab(cp.getModulesTab());
				model.setCourseOfStudy(cpp.getCourse());
				model.setPnumber(cpp.getPNumber());
				model.setStudentName(new Name(cpp.getFirstName(), cpp.getSurname()));
				model.setEmail(cpp.getEmail());
				model.setSubmissionDate(cpp.getSubDate());

				// Clear the list view pane before populating to prevent stacking of compulsory modules on repeating create profile clicks. 
				view.getSelectModulesPane().clearModules();
				view.getOverviewSelectionPane().clear();

				// Populates the 2 list views containing the modules to be chosen depending on Delivery (term 1, 2, or yearlong)
				Collection<Module> modules = model.getCourseOfStudy().getAllModulesOnCourse();
				for(Module m : modules) {
					if(m.getRunPlan().equals(Delivery.YEAR_LONG)) {
						smp.getYearLong().addModule(m);
					}
					else if(m.getRunPlan().equals(Delivery.TERM_1)) {
						smp.getTerm1().addModule(m);
					}
					else if (m.getRunPlan().equals(Delivery.TERM_2)) {
						smp.getTerm2().addModule(m);
					}
				}

				// Adds compulsory modules for each course and updates the initial credits
				if(model.getCourseOfStudy().getCourseName() == "Software Engineering") {
					smp.getTerm1Selected().addModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
					smp.getTerm2Selected().addModule(model.getCourseOfStudy().getSingleModuleByCode("CTEC3902"));
					smp.getTerm1().removeModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
					smp.getTerm2().removeModule(model.getCourseOfStudy().getSingleModuleByCode("CTEC3902"));
					smp.getCreditCountPane().setTerm1Credits(30);
					smp.getCreditCountPane().setTerm2Credits(30);

				}
				else if (model.getCourseOfStudy().getCourseName() == "Computer Science") {
					smp.getTerm1Selected().addModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
					smp.getTerm1().removeModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
					smp.getCreditCountPane().setTerm1Credits(30);
					smp.getCreditCountPane().setTerm2Credits(15);
				}
			}

		}


	}

	/**
	 * Handles addition of modules for term 1
	 * Adds the module to listView containing selected moudles and removes it from listView containing available modules
	 * @author joeca
	 */
	private class AddTerm1ModuleHandler implements EventHandler<ActionEvent>  {
		@Override
		public void handle(ActionEvent event) {
			Module temp = smp.getTerm1().getSelectedItem();
			if(temp!= null) {
				smp.getTerm1Selected().addModule(temp);
				smp.getTerm1().removeModule(temp);
				smp.getCreditCountPane().increaseTerm1Credits(temp.getCredits());

			}
		}

	}
	/**
	 * Handles removing of modules for term 1. 
	 * Removes the module from the selected listView and re-adds it to the listView containing available modules
	 * @author joeca
	 */
	private class RemoveTerm1ModuleHandler implements EventHandler<ActionEvent>  {
		@Override
		public void handle(ActionEvent event) {
			Module temp = smp.getTerm1Selected().getSelectedItem();
			if(temp!= null && temp.getModuleName() == "Systems Building: Methods") {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("You cannot remove compulsory modules.");		
				alert.showAndWait();
			}
			else if(temp != null) {
				smp.getTerm1Selected().removeModule(temp);
				smp.getTerm1().addModule(temp);
				smp.getCreditCountPane().decreaseTerm1Credits(temp.getCredits());

			}
		}

	}

	/**
	 * Handles addition of modules for term 2
	 * Adds the module to listView containing selected moudles and removes it from listView containing available modules
	 * @author joeca
	 */
	private class AddTerm2ModuleHandler implements EventHandler<ActionEvent>  {
		@Override
		public void handle(ActionEvent event) {
			Module temp = smp.getTerm2().getSelectedItem();
			if(temp!=null) {
				smp.getTerm2Selected().addModule(temp);
				smp.getTerm2().removeModule(temp);
				smp.getCreditCountPane().increaseTerm2Credits(temp.getCredits());
			}
		}

	}
	/**
	 * Handles removing of modules for term 2. 
	 * Removes the module from the selected listView and re-adds it to the listView containing available modules
	 * @author joeca
	 */
	private class RemoveTerm2ModuleHandler implements EventHandler<ActionEvent>  {
		@Override
		public void handle(ActionEvent event) {
			Module temp = smp.getTerm2Selected().getSelectedItem();
			if(temp!= null && model.getCourseOfStudy().getCourseName() == "Software Engineering") {
				if(temp.getModuleName() == "Rigorous Systems") {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("You cannot remove compulsory modules.");		
					alert.showAndWait();
				}
				else {
					smp.getTerm2Selected().removeModule(temp);
					smp.getTerm2().addModule(temp);
					smp.getCreditCountPane().decreaseTerm2Credits(temp.getCredits());
				}
			}
			else if(temp!= null) {
				smp.getTerm2Selected().removeModule(temp);
				smp.getTerm2().addModule(temp);
				smp.getCreditCountPane().decreaseTerm2Credits(temp.getCredits());
			}
		}
	}

	private class ResetButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			view.getSelectModulesPane().clearModules();
			Collection<Module> modules = model.getCourseOfStudy().getAllModulesOnCourse();
			for(Module m : modules) {
				if(m.getRunPlan().equals(Delivery.YEAR_LONG)) {
					smp.getYearLong().addModule(m);
				}
				else if(m.getRunPlan().equals(Delivery.TERM_1)) {
					smp.getTerm1().addModule(m);
				}
				else if (m.getRunPlan().equals(Delivery.TERM_2)) {
					smp.getTerm2().addModule(m);
				}
			}

			// Adds compulsory modules for each course and updates the initial credits
			if(model.getCourseOfStudy().getCourseName() == "Software Engineering") {
				smp.getTerm1Selected().addModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
				smp.getTerm2Selected().addModule(model.getCourseOfStudy().getSingleModuleByCode("CTEC3902"));
				smp.getTerm1().removeModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
				smp.getTerm2().removeModule(model.getCourseOfStudy().getSingleModuleByCode("CTEC3902"));
				smp.getCreditCountPane().setTerm1Credits(30);
				smp.getCreditCountPane().setTerm2Credits(30);

			}
			else if (model.getCourseOfStudy().getCourseName() == "Computer Science") {
				smp.getTerm1Selected().addModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
				smp.getTerm1().removeModule(model.getCourseOfStudy().getSingleModuleByCode("IMAT3423"));
				smp.getCreditCountPane().setTerm1Credits(30);
				smp.getCreditCountPane().setTerm2Credits(15);
			}

		}

	}

	private class SubmitButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
			model.clearAllSelectedModules();

			if(smp.getCreditCountPane().getTotalCredits() == 120 && smp.getCreditCountPane().getTerm1Credits() == 60) {
				for(Module m :smp.getSelectedTerm1Modules().getSelectedModules()) {
					model.addToSelectedModules(m);
				}
				for(Module m :smp.getSelectedTerm2Modules().getSelectedModules()) {
					model.addToSelectedModules(m);
				}
				for(Module m :smp.getSelectedYearLongModules().getSelectedModules()) {
					model.addToSelectedModules(m);
				}

				Set<Module> selectedModules = model.getAllSelectedModules();
				view.setCenter(osp);
				cp.setCurrentTab(cp.getOverviewTab());
				osp.clear();

				osp.add("Name: " + model.getStudentName().getFirstName() + " " + model.getStudentName().getFamilyName());
				osp.add("P-Number: " + model.getPnumber().toUpperCase());
				osp.add("Email: " + model.getEmail());
				osp.add("Submission Date: " + model.getSubmissionDate().toString());
				osp.add("Course: " + model.getCourseOfStudy());

				osp.add("\nSelected Modules:\n---------------------------------------------------");
				for(Module m : selectedModules) {
					if(model.getCourseOfStudy().getCourseName() == "Software Engineering") {
						if(m.getModuleName() == "Computing Project" || m.getModuleName() == "Systems Building: Methods" || m.getModuleName() == "Rigorous Systems") {
							osp.add(m.toString() + "\nCredits : " + m.getCredits() + " -- Compulsory : Yes -- " + "Delivery : " + deliveryToString(m.getRunPlan()) + "\n\n");
						} else {
							osp.add(m.toString() + "\nCredits : " + m.getCredits() + " -- Compulsory : No -- " + "Delivery : " + deliveryToString(m.getRunPlan()) + "\n\n");
						}
					}
					else if(model.getCourseOfStudy().getCourseName() == "Computer Science") {
						if(m.getModuleName() == "Computing Project" || m.getModuleName() == "Systems Building: Methods") {
							osp.add(m.toString() + "\nCredits : " + m.getCredits() + " -- Compulsory : Yes -- " + "Delivery : " + deliveryToString(m.getRunPlan()) + "\n\n");
						} else {
							osp.add(m.toString() + "\nCredits : " + m.getCredits() + " -- Compulsory : No -- " + "Delivery : " + deliveryToString(m.getRunPlan()) + "\n\n");
						}
					}

				}		
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Incorrect number of credits selected. Please make sure you match the following:\n\nTerm 1: 60 Credits\nTerm 2: 60 Credits");		
				alert.showAndWait();
			}

		}
	}

	private class SaveOverviewHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			ObservableList<String> overview = osp.getOverview();
			FileChooser fileChooser = new FileChooser();
			File file = new File(fileChooser.showSaveDialog(null) + ".txt");
			if(file!=null) {
				try {
						PrintWriter printWriter = new PrintWriter(file);
						for(String s : overview) { 
							printWriter.println(s);			
						}
						printWriter.flush();
						printWriter.close();
						
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class SaveHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentProfile.dat"));) {
				// Saving currently entered information even if not processed
				model.setCourseOfStudy(cpp.getCourse());
				model.setPnumber(cpp.getPNumber());
				model.setStudentName(new Name(cpp.getFirstName(), cpp.getSurname()));
				model.setEmail(cpp.getEmail());
				model.setSubmissionDate(cpp.getSubDate());
				
				for(Module m :smp.getSelectedTerm1Modules().getSelectedModules()) {
					model.addToSelectedModules(m);
				}
				for(Module m :smp.getSelectedTerm2Modules().getSelectedModules()) {
					model.addToSelectedModules(m);
				}
				for(Module m :smp.getSelectedYearLongModules().getSelectedModules()) {
					model.addToSelectedModules(m);
				}
				
				oos.writeObject(model); 
				oos.flush();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alert");
				alert.setHeaderText(null);
				alert.setContentText("Saved to 'StudentProfile.dat'");	
				alert.showAndWait();
			}
			catch (IOException ioExcep){
				System.out.println("Error saving");
			}

		}
	}

	/**
	 * Load handler is partially broken. Some of the information loaded is incorrect like the selected course.
	 * @author joeca
	 *
	 */
	
	private class LoadHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("StudentProfile.dat"));) {
				model = (StudentProfile) ois.readObject();
				
				osp.clear();
				smp.clearModules();
				smp.getCreditCountPane().resetCredits();
				model.setCourseOfStudy(cpp.getCourse());
//				cpp.clearComboBox();
//				cpp.populateComboBoxWithCourses(setupAndRetrieveCourses());
				// populating the createProfilePane with loaded information
				if(model.getCourseOfStudy().getCourseName() == "Computer Science") {
					cpp.getCourseCMB().getSelectionModel().select(0);
				}
				else {
					cpp.getCourseCMB().getSelectionModel().select(1);
				}
				cpp.setPNumberInput(model.getPnumber());
				cpp.setFirstNameInput(model.getStudentName().getFirstName());
				cpp.setSurnameInput(model.getStudentName().getFamilyName());
				cpp.setEmailInput(model.getEmail());
				cpp.setSubDateInput(model.getSubmissionDate());
				
				// populating the selectModulesPane with loaded information
				
				Collection<Module> modules = model.getCourseOfStudy().getAllModulesOnCourse();
				for(Module m : modules) {
					if(m.getRunPlan().equals(Delivery.YEAR_LONG)) {
						smp.getYearLong().addModule(m);
						smp.getCreditCountPane().increaseTerm1Credits(15);
						smp.getCreditCountPane().increaseTerm2Credits(15);
					}
					else if(m.getRunPlan().equals(Delivery.TERM_1)) {
						smp.getTerm1().addModule(m);
					}
					else if (m.getRunPlan().equals(Delivery.TERM_2)) {
						smp.getTerm2().addModule(m);
					}
				}
				for(Module m : model.getAllSelectedModules()) {
					if(m.getRunPlan() == Delivery.TERM_1) {
						smp.getTerm1Selected().addModule(m);
						smp.getTerm1().removeModule(m);
						smp.getCreditCountPane().increaseTerm1Credits(m.getCredits());
						
					}
					else if (m.getRunPlan() == Delivery.TERM_2) {
						smp.getTerm2Selected().addModule(m);
						smp.getTerm2().removeModule(m);
						smp.getCreditCountPane().increaseTerm2Credits(m.getCredits());
					}
				}

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alert");
				alert.setHeaderText(null);
				alert.setContentText("Successfully loaded from 'StudentProfile.dat'.");		
				alert.showAndWait();
			}
			catch (IOException ioExcep){
				System.out.println("Error loading");
			}
			catch (ClassNotFoundException c) {
				System.out.println("Class Not found");
			}
		}
	}

	private class AboutHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("MODULE OPTIONS CHOOSER V1.0");
			alert.setHeaderText(null);
			alert.setContentText(
					"Module Options Chooser allows second year university students to create and manage their selected third year modules through a profile."
							+ "\n\nActions include adding and removing of modules, management of credit score per module, and an overview function to revise all input information.");		
			alert.showAndWait();
		}
	}

	private String deliveryToString(Delivery d) {
		if(d.equals(Delivery.TERM_1)) {
			return "Term 1";
		}
		else if(d.equals(Delivery.TERM_2)) {
			return "Term 2";
		}
		else return "Year Long";
	}

	private Course[] setupAndRetrieveCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
		Module imat3451 = new Module("IMAT3451", "Computing Project", 30, true, Delivery.YEAR_LONG);
		//Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Delivery.TERM_2);	
		Module ctec3902 = new Module("CTEC3902", "Rigorous Systems", 15, false, Delivery.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
		Module ctec3426 = new Module("CTEC3426", "Telematics", 15, false, Delivery.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Popular Technology Ethics", 15, false, Delivery.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Delivery.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);

		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(imat3451);
		compSci.addModuleToCourse(ctec3902);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3426);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(imat3451);
		softEng.addModuleToCourse(ctec3902);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3426);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
