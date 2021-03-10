package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Course;
import model.Module;

public class ModuleSelectorList extends VBox {

	private Label termLabel, titleLabel;
	private Button add, remove;
	private HBox buttons;
	private ListView<Module> modules;
	
	public ModuleSelectorList(String selection, String title, Boolean hasButtons) {

		// Create the label and list view for the module selection
		this.titleLabel = new Label(selection +  " " + title +  " modules");
		titleLabel.setAlignment(Pos.TOP_LEFT);
		this.modules = new ListView<Module>();
		
		// HBox to contain the add and remove buttons
		this.buttons = new HBox();
		this.termLabel = new Label(title);
		
		this.add = new Button("Add");
		add.setPrefHeight(25);
		add.setPrefWidth(75);
		
		this.remove = new Button("Remove");
		remove.setPrefHeight(25);
		remove.setPrefWidth(75);

		buttons.setPadding(new Insets(10));
		buttons.setSpacing(20);
		buttons.setAlignment(Pos.CENTER);		
		buttons.getChildren().addAll(termLabel, add, remove);
		
		if(hasButtons) {
			this.getChildren().addAll(titleLabel, modules, buttons);
		}
		else {
			this.getChildren().addAll(titleLabel, modules);
		}

	}
	
	public Button getAddButton() {
		return add;
	}
	public Button getRemoveButton() {
		return remove;
	}
	public void addModule(Module module) {
		modules.getItems().add(module);
	}
	public void removeModule(Module module) {
		modules.getItems().remove(module);
	}
	public void removeSelectedModule() {
		modules.getItems().remove(modules.getSelectionModel().getSelectedItem());
	}
	public Module getSelectedItem() {
		return modules.getSelectionModel().getSelectedItem();
	}
	public void clear() {
		modules.getItems().clear();
	}
	public ObservableList<Module> getSelectedModules() {
		return modules.getItems();
	}

}
