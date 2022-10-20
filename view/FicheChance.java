package view;

import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import view.Texte.Polices;

public class FicheChance extends Fiche {
	public FicheChance(String nomChance, String texteChance){		
		Texte logo = new Texte("?");
		logo.setPolice(Polices.NUNITOBOLD);
		logo.setSize(10);
		Utiles.centrer(logo);
		logo.setStyle("-fx-text-fill:#9673a677;-fx-padding:-100;");
		add(logo,0,0,1,2);
		
		Texte nom = new Texte(nomChance.toUpperCase());
		nom.setPolice(Polices.COMICSTRICKS);
		nom.setSize(1.3);
		nom.setWrapText(true);
		nom.setTextAlignment(TextAlignment.CENTER);
		Utiles.centrer(nom);
		nom.setStyle("-fx-text-fill:#9673a6;-fx-padding:10;-fx-font-weight:bold");
		add(nom,0,0);
		
		Texte texte = new Texte(texteChance);
		Utiles.centrer(texte);
		texte.setStyle("-fx-padding:0 0 10 0;");
		texte.setWrapText(true);
		texte.setTextAlignment(TextAlignment.CENTER);
		add(texte,0,1);
		
		getStyleClass().addAll("bg-white","border","bw-5","p-10");
		setStyle("-fx-border-color:#9673a6");
		setFillHeight(this,false);
		setAlignment(Pos.CENTER);
		setOnMouseClicked(e->MonopolyIUT.getPartie().getView().afficherFiche(this));
	}
}
