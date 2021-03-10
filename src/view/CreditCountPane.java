package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CreditCountPane extends HBox { 

	CreditCounter term1Count;
	CreditCounter term2Count;
	
	public CreditCountPane() { 
		
		this.setSpacing(20);
		this.setPadding(new Insets(10,10,10,10));
		this.setAlignment(Pos.CENTER);
		
		this.term1Count = new CreditCounter("Term 1");
		this.term2Count = new CreditCounter("Term 2");		

		this.getChildren().addAll(term1Count, term2Count);

	}
	
	public void resetCredits() {
		term1Count.setCreditCount(0);
		term2Count.setCreditCount(0);
	}
	public void increaseTerm1Credits(int i) {
		term1Count.increaseCredits(i);
	}

	public void increaseTerm2Credits(int i) {
		term2Count.increaseCredits(i);
	}

	public void decreaseTerm1Credits(int i) {
		term1Count.decreaseCredits(i);
	}

	public void decreaseTerm2Credits(int i) {
		term2Count.decreaseCredits(i);
	}
	public void setTerm1Credits(int i) {
		term1Count.setCreditCount(i);
	}
	public void setTerm2Credits(int i) {
		term2Count.setCreditCount(i);
	}
	public int getTerm1Credits() {
		return term1Count.getCreditCount();
	}
	public int getTerm2Credits() {
		return term2Count.getCreditCount();
	}
	public int getTotalCredits() {
		return term1Count.getCreditCount() + term2Count.getCreditCount();
	}
}
