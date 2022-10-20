package view;

import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import view.Texte.Polices;

public class FicheAngle extends Fiche {
	public FicheAngle(String nomAngle, String texteAngle){		
		Texte logo = new Texte(" ",10);
		Utiles.centrer(logo);
		logo.setStyle("-fx-padding:-100;");
		add(logo,0,0,1,2);
		
		Texte nom = new Texte(nomAngle.toUpperCase());
		nom.setPolice(Polices.COMICSTRICKS);
		nom.setSize(1.3);
		Utiles.centrer(nom);
		nom.setStyle("-fx-text-fill:#3D663D;-fx-padding:10");
		add(nom,0,0);
		
		Texte texte = new Texte(texteAngle);
		Utiles.centrer(texte);
		texte.setStyle("-fx-padding:0 0 10 0;");
		texte.setWrapText(true);
		texte.setTextAlignment(TextAlignment.CENTER);
		add(texte,0,1);
		
		getStyleClass().addAll("border","bw-5","p-10");
		setStyle("-fx-border-color:#3D663D;-fx-background-color:#cde7ce");
		setFillHeight(this,false);
		setAlignment(Pos.CENTER);
		setOnMouseClicked(e->MonopolyIUT.getPartie().getView().afficherFiche(this));
	}
}
