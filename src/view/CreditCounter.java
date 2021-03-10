package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CreditCounter extends HBox { 
	
	private Label termCredits;
	private TextField creditDisplay;

	public CreditCounter(String term) { 
		
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(15);
		this.setPadding(new Insets(10,10,10,10));
		
		this.termCredits = new Label("Current " + term + " credits:");
		this.creditDisplay = new TextField("0");
		creditDisplay.setEditable(false);
		creditDisplay.setPrefSize(65, USE_COMPUTED_SIZE);
		
		this.getChildren().addAll(termCredits, creditDisplay);

	}
	
	public int getCreditCount() {
		return Integer.parseInt(creditDisplay.getText());
	}
	public void setCreditCount(int i) {
		creditDisplay.setText(Integer.toString(i));
	}
	public void increaseCredits(int i) {
		int currentCredits = Integer.parseInt(creditDisplay.getText());
		currentCredits += i;
		creditDisplay.setText(Integer.toString(currentCredits));
	}
	public void decreaseCredits(int i) {
		int currentCredits = Integer.parseInt(creditDisplay.getText());
		currentCredits -= i;
		creditDisplay.setText(Integer.toString(currentCredits));
	} 

}
