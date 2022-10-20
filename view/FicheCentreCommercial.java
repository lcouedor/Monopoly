package view;

import back.CentreCommercial;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import view.Texte.Polices;

public class FicheCentreCommercial extends Fiche{
	
	public FicheCentreCommercial(CentreCommercial centre) {		
		getColumnConstraints().clear();
		getColumnConstraints().add(Utiles.getCC());
		
		Texte nom = new Texte(centre.getNom().toUpperCase());
		nom.setPolice(Polices.COMICSTRICKS);
		nom.setSize(1.3);
		nom.getStyleClass().add("outline");
		Utiles.centrer(nom);
		nom.setStyle("-fx-padding:10;-fx-text-fill:"+centre.getCouleur());
		add(nom,0,0);
		
		GridPane grid = new GridPane();
		grid.getColumnConstraints().add(Utiles.getCC());
		grid.getRowConstraints().addAll(Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),Utiles.getRC());
		
		Texte nbCentres = new Texte("Nombre possédé");
		nbCentres.setBold();
		nbCentres.setSize(1);
		GridPane.setHalignment(nbCentres, HPos.LEFT);
		Texte loyer = new Texte("Loyer");
		loyer.setBold();
		loyer.setSize(1);
		loyer.setTextAlignment(TextAlignment.RIGHT);
		GridPane.setHalignment(loyer, HPos.RIGHT);
		loyer.setMinWidth(USE_PREF_SIZE);
		
		grid.add(nbCentres, 0, 0);
		grid.add(loyer, 1, 0);
		
		
		Texte texte1 = new Texte("- 1 centre commercial :");
		texte1.setPolice(Polices.NUNITO);
		texte1.setSize(1);
		GridPane.setHalignment(texte1, HPos.LEFT);
		grid.add(texte1, 0, 1);
		
		Texte loyer1 = new Texte("50€");
		loyer1.setPolice(Polices.NUNITO);
		loyer1.setSize(1);
		loyer1.setMinWidth(USE_PREF_SIZE);
		GridPane.setHalignment(loyer1, HPos.RIGHT);
		loyer1.setTextAlignment(TextAlignment.RIGHT);
		grid.add(loyer1, 1, 1);
		
		Texte texte2 = new Texte("- 2 centres commerciaux :");
		texte2.setPolice(Polices.NUNITO);
		texte2.setSize(1);
		GridPane.setHalignment(texte2, HPos.LEFT);
		grid.add(texte2, 0, 2);
		
		Texte loyer2 = new Texte("100€");
		loyer2.setPolice(Polices.NUNITO);
		loyer2.setSize(1);
		loyer2.setMinWidth(USE_PREF_SIZE);
		GridPane.setHalignment(loyer2, HPos.RIGHT);
		loyer2.setTextAlignment(TextAlignment.RIGHT);
		grid.add(loyer2, 1, 2);
		
		Texte texte3 = new Texte("- 3 centres commerciaux :");
		texte3.setPolice(Polices.NUNITO);
		texte3.setSize(1);
		GridPane.setHalignment(texte3, HPos.LEFT);
		grid.add(texte3, 0, 3);
		
		Texte loyer3 = new Texte("150€");
		loyer3.setPolice(Polices.NUNITO);
		loyer3.setSize(1);
		loyer3.setMinWidth(USE_PREF_SIZE);
		GridPane.setHalignment(loyer3, HPos.RIGHT);
		loyer3.setTextAlignment(TextAlignment.RIGHT);
		grid.add(loyer3, 1, 3);
		
		Texte texte4 = new Texte("- 4 centres commerciaux :");
		texte4.setPolice(Polices.NUNITO);
		texte4.setSize(1);
		GridPane.setHalignment(texte4, HPos.LEFT);
		grid.add(texte4, 0, 4);
		
		Texte loyer4 = new Texte("200€");
		loyer4.setPolice(Polices.NUNITO);
		loyer4.setSize(1);
		loyer4.setMinWidth(USE_PREF_SIZE);
		GridPane.setHalignment(loyer4, HPos.RIGHT);
		loyer4.setTextAlignment(TextAlignment.RIGHT);
		grid.add(loyer4, 1, 4);
		
		add(grid, 0, 1);
		
		getStyleClass().addAll("border","bw-5","bg-white");
		setStyle("-fx-padding:10 20 20 20;-fx-border-color:"+centre.getCouleur());
		setFillHeight(this,false);
		setOnMouseClicked(e->MonopolyIUT.getPartie().getView().afficherFiche(this));
	}
	
}
