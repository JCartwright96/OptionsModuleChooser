package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OverviewSelectionPane extends VBox {

	private ListView<String> overview;
	private Button submit;
	
	public OverviewSelectionPane() {
		
		this.setAlignment(Pos.CENTER);
		this.setSpacing(30);
		this.setPadding(new Insets(40,40,40,40));
		
		this.overview = new ListView<String>();
		this.submit = new Button("Save Overview");
		
		this.setVgrow(overview, Priority.ALWAYS);

		submit.setPrefSize(100, 40);
		submit.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(overview, submit);
		
	}
	
	public void clear() {
		overview.getItems().clear();
	}
	
	public void add(String s) {
		overview.getItems().add(s);
	}
	
	public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}
	
	public ObservableList<String> getOverview() {
		return overview.getItems();
	}
}
