package view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

public class ControlPane extends VBox {

	private MenuBar mb;
	private Menu file, help;
	private MenuItem loadState, saveState, exit, about;
	private TabPane tp;
	private Tab studentProfile, selectModules, overview;
	private SelectModulesPane smp;
	private CreateProfilePane cpp;
	private OverviewSelectionPane osp;

	public ControlPane() {
		
		this.tp = new TabPane();
		
		// Creating each of the three tabs for the tab pane
		this.studentProfile = new Tab("Create Profile", cpp);
		this.selectModules = new Tab("Module Selector", smp);
		this.overview = new Tab("Overview Selection", osp);
		
		tp.getTabs().addAll(studentProfile, selectModules, overview);
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		
		this.mb = new MenuBar();
		this.file = new Menu("File");
		this.help = new Menu("Help");
		
		this.loadState = new MenuItem("_Load State");
		loadState.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));
		this.saveState = new MenuItem("_Save State");
		saveState.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		this.exit = new MenuItem("_Exit");
		exit.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));
		this.about = new MenuItem("_About");
		about.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
		
		file.getItems().add(saveState);
		file.getItems().add(loadState);
		file.getItems().add(exit);
		
		help.getItems().add(about);
		
		mb.getMenus().addAll(file, help);
		
		this.getChildren().addAll(mb,tp);
	}
	
	public Tab getCurrentTab() {
		return tp.getSelectionModel().getSelectedItem();
	}
	public void setCurrentTab(Tab t) {
		tp.getSelectionModel().select(t);
	}
	public Tab getProfileTab() {
		return studentProfile;
	}
	public Tab getModulesTab() {
		return selectModules;
	}
	public Tab getOverviewTab() {
		return overview;
	}
	public void addSelectCPPTabHandler(EventHandler<Event> handler) {
		studentProfile.setOnSelectionChanged(handler);
	}
	public void addSelectSMPTabHandler(EventHandler<Event> handler) {
		selectModules.setOnSelectionChanged(handler);
	}
	public void addSelectOSPTabHandler(EventHandler<Event> handler) {
		overview.setOnSelectionChanged(handler);
	}
	public void  addAboutHandler(EventHandler<ActionEvent> handler) {
		about.setOnAction(handler);
	}
	public void addExitHandler(EventHandler<ActionEvent> handler) {
		exit.setOnAction(handler);
	}
	public void addSaveHandler(EventHandler<ActionEvent> handler) {
		saveState.setOnAction(handler);
	}
	public void addLoadHandler(EventHandler<ActionEvent> handler) {
		loadState.setOnAction(handler);
	}
	
}
