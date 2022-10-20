package view;

import back.Case;
import javafx.scene.text.TextAlignment;

public class AngleView extends CaseView{

	public AngleView(Case angle) {
		super(angle);
		this.setStyle("-fx-background-color:"+/*angle.getCouleur()*/angle.getCouleur()+";");
		nom.setWrapText(true);
		nom.setTextAlignment(TextAlignment.CENTER);
	}

}
