package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Course;
import model.Module;

public class SelectModulesPane extends VBox {

	private ModuleSelectorList term1, term2, yearLong, term1Selected, term2Selected;
	private CreditCountPane credits;
	private ModuleSelectorButtons buttons;
	private HBox modules;
	private VBox selectModules, selectedModules;
	
	public SelectModulesPane() {
		
		this.term1 = new ModuleSelectorList("Unselected", "Term 1", true);
		this.term2 = new ModuleSelectorList("Unselected", "Term 2", true);
		
		this.yearLong = new ModuleSelectorList("Selected", "Year Long", false);
		this.term1Selected = new ModuleSelectorList("Selected", "Term 1", false);
		this.term2Selected = new ModuleSelectorList("Selected", "Term 2", false);
		
		this.credits = new CreditCountPane();
		this.buttons = new ModuleSelectorButtons();
		
		this.modules = new HBox();
		modules.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		modules.setAlignment(Pos.CENTER);
		
		this.selectModules = new VBox();
		selectModules.getChildren().addAll(term1, term2);
		selectModules.setSpacing(25);
		selectModules.setPadding(new Insets(10,10,10,10));
		
		this.selectedModules = new VBox();
		selectedModules.getChildren().addAll(yearLong, term1Selected, term2Selected);
		selectedModules.setSpacing(20);
		selectedModules.setPadding(new Insets(10,10,10,10));

		yearLong.setMinHeight(70);
		yearLong.setMaxHeight(200);
		term1Selected.setMinHeight(75);
		term2Selected.setMinHeight(75);

		selectedModules.setVgrow(yearLong, Priority.ALWAYS);
		selectedModules.setVgrow(term1Selected, Priority.ALWAYS);
		selectedModules.setVgrow(term2Selected, Priority.ALWAYS);
		modules.setHgrow(selectedModules, Priority.SOMETIMES);
		modules.setHgrow(selectModules, Priority.SOMETIMES);
		modules.getChildren().addAll(selectModules, selectedModules);
		
		
		this.getChildren().addAll(modules, credits, buttons);
	}
	
	public ModuleSelectorList getTerm1() {
		return term1;
	}
	public ModuleSelectorList getTerm2() {
		return term2;
	}
	public ModuleSelectorList getYearLong() {
		return yearLong;
	}
	public ModuleSelectorList getTerm1Selected() {
		return term1Selected;
	}
	public ModuleSelectorList getTerm2Selected() {
		return term2Selected;
	}
	public CreditCountPane getCreditCountPane() {
		return credits;
	}
	public ModuleSelectorButtons getModuleSubmitPane() {
		return buttons;
	}
	
	public void addAddTerm1ModuleHandler(EventHandler<ActionEvent> handler) {
		term1.getAddButton().setOnAction(handler);
	}

	public void addRemoveTerm1ModuleHandler(EventHandler<ActionEvent> handler) {
		term1.getRemoveButton().setOnAction(handler);
	}
	public void addAddTerm2ModuleHandler(EventHandler<ActionEvent> handler) {
		term2.getAddButton().setOnAction(handler);
	}

	public void addRemoveTerm2ModuleHandler(EventHandler<ActionEvent> handler) {
		term2.getRemoveButton().setOnAction(handler);
	}
	public ModuleSelectorList getSelectedTerm1Modules() {
		return term1Selected;
	}
	public ModuleSelectorList getSelectedTerm2Modules() {
		return term2Selected;
	}
	public ModuleSelectorList getSelectedYearLongModules() {
		return yearLong;
	}

	public void clearModules() {
		term1.clear();
		term2.clear();
		term1Selected.clear();
		term2Selected.clear();
		yearLong.clear();
	}
	
}
