package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

//You may change this class to extend another type if you wish
public class OptionsModuleChooserRootPane extends BorderPane {
	
		ControlPane cp;
		CreateProfilePane cpp;
		SelectModulesPane smp;
		OverviewSelectionPane osp;

	public OptionsModuleChooserRootPane() {
		
		this.setPrefSize(800, 800);
		
		this.cp = new ControlPane();
		this.cpp = new CreateProfilePane();
		this.smp = new SelectModulesPane();
		this.osp = new OverviewSelectionPane();
		
		this.setTop(cp);
		this.setCenter(cpp);

	}
	
	public ControlPane getControlPane() {
		return cp;
	}
	
	public CreateProfilePane getCreateProfilePane() {
		return cpp;
	}
	
	public SelectModulesPane getSelectModulesPane() {
		return smp;
	}
	
	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}
	public void updateView(Node n) {
		this.setCenter(n);
	}
}
