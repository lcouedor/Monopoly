package view;

import back.Service;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import view.Texte.Polices;

public class FicheService extends Fiche {
	public FicheService(Service service){		
		Texte logo = new Texte(" ",10);
		Utiles.centrer(logo);
		logo.setStyle("-fx-padding:-100;");
		add(logo,0,0,1,2);
		
		Texte nom = new Texte(service.getNom().toUpperCase());
		nom.setPolice(Polices.COMICSTRICKS);
		nom.setSize(1.3);
		nom.getStyleClass().add("outline");
		Utiles.centrer(nom);
		nom.setStyle("-fx-padding:10;-fx-text-fill:"+service.getCouleur());
		add(nom,0,0);
		
		Texte texte = new Texte("1 service possédé : Dés x 10");
		GridPane.setValignment(texte, VPos.TOP);
		texte.setStyle("-fx-padding:20 0 0 0;");
		texte.setTextAlignment(TextAlignment.CENTER);
		add(texte,0,1);
		
		Texte texte2 = new Texte("2 services possédés : Dés x 20");
		GridPane.setValignment(texte2, VPos.BOTTOM);
		texte2.setStyle("-fx-padding:0 0 20 0;");
		texte2.setTextAlignment(TextAlignment.CENTER);
		add(texte2,0,1);
		
		getStyleClass().addAll("border","bw-5","p-10","bg-white");
		setStyle("-fx-border-color:"+service.getCouleur());
		setFillHeight(this,false);
		setAlignment(Pos.CENTER);
		setOnMouseClicked(e->MonopolyIUT.getPartie().getView().afficherFiche(this));
	}
}
