package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ModuleSelectorButtons extends HBox {

	private Button reset, submit;
	
	public ModuleSelectorButtons() {
		
		this.setPadding(new Insets(10,10,10,10));
		this.setSpacing(30);
		this.setAlignment(Pos.CENTER);
		
		this.reset = new Button("Reset");
		reset.setPrefSize(75,25);
		this.submit = new Button("Submit");
		submit.setPrefSize(75, 25);
		
		this.getChildren().addAll(reset, submit);
		
	}
	
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}
	
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		reset.setOnAction(handler);
	}
}
