package view;

import back.Salle;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import view.Texte.Polices;

public class FicheSalle extends Fiche{
	
	public FicheSalle(Salle salle) {
		getStyleClass().addAll("bg-white","border","bw-3");
		GridPane.setFillHeight(this, false);
		GridPane.setFillWidth(this, false);
		
		RowConstraints rc = new RowConstraints();
		getColumnConstraints().add(Utiles.getCC(100));
		getRowConstraints().addAll(rc,rc,rc,rc,rc,rc,rc);
		
		Texte titre = new Texte("Nom de matière");
		add(titre, 0, 0);
		Utiles.centrer(titre);
		titre.getStyleClass().add("p-10");
		titre.setStyle("-fx-background-color:"+salle.getCouleur());
		titre.setSize(1.2);
		
		Texte nom = new Texte(salle.getNom().toUpperCase());
		add(nom, 0, 1);
		Utiles.centrer(nom);
		nom.getStyleClass().add("p-10");
		nom.setStyle("-fx-background-color:"+salle.getCouleur()+";-fx-border-width: 0 0 3 0;-fx-border-color:black");
		nom.setPolice(Polices.COMICSTRICKS);
		nom.setSize(1.5);
		
		GridPane loyers = new GridPane();
		add(loyers, 0, 2);
		loyers.setHgap(20);
		loyers.setVgap(20);
		loyers.getStyleClass().add("p-10");
		RowConstraints rc2 = Utiles.getRC();
		loyers.getRowConstraints().addAll(rc2,rc2,rc2,rc2);
		loyers.getColumnConstraints().addAll(new ColumnConstraints(),Utiles.getCC(),new ColumnConstraints());
		Texte loyerText = new Texte("Loyer");
		loyers.add(loyerText, 0, 0);
		Texte salleVide = new Texte("- Salle vide");
		loyers.add(salleVide, 1, 0);
		Texte salleVidePrix = new Texte(salle.getCoutLoyer0()+"€");
		loyers.add(salleVidePrix, 2, 0);
		GridPane.setHalignment(salleVidePrix, HPos.RIGHT);
		Texte avec = new Texte("- Avec");
		loyers.add(avec, 1, 1);
		Texte unMateriel = new Texte("1 matériel  ");
		unMateriel.getStyleClass().add("bg-white");
		loyers.add(unMateriel, 1, 1);
		GridPane.setHalignment(unMateriel, HPos.RIGHT);
		Texte unMaterielPrix = new Texte(salle.getCoutLoyer1()+"€");
		loyers.add(unMaterielPrix, 2, 1);
		GridPane.setHalignment(unMaterielPrix, HPos.RIGHT);
		Texte deuxMateriel = new Texte("2 matériels");
		loyers.add(deuxMateriel, 1, 2);
		GridPane.setHalignment(deuxMateriel, HPos.RIGHT);
		Texte deuxMaterielPrix = new Texte(salle.getCoutLoyer2()+"€");
		loyers.add(deuxMaterielPrix, 2, 2);
		GridPane.setHalignment(deuxMaterielPrix, HPos.RIGHT);
		Texte troisMateriel = new Texte("3 matériels");
		loyers.add(troisMateriel, 1, 3);
		GridPane.setHalignment(troisMateriel, HPos.RIGHT);
		Texte troisMaterielPrix = new Texte(salle.getCoutLoyer3()+"€");
		loyers.add(troisMaterielPrix, 2, 3);
		GridPane.setHalignment(troisMaterielPrix, HPos.RIGHT);
		
		Texte ue = new Texte("Si un joueur possède 2 matières d'une même UE, alors 100€ sont ajoutés au loyer, s'il en possède 3, 200€ sont ajoutés au loyer.");
		add(ue, 0, 3);
		Utiles.centrer(ue);
		ue.setWrapText(true);
		ue.setTextAlignment(TextAlignment.CENTER);
		ue.setStyle("-fx-padding:10;-fx-background-color: black, white;-fx-background-insets: 0 20 0 20, 1;");
		
		Texte prixMaterielTexte = new Texte("Prix d'un matériel");
		add(prixMaterielTexte, 0, 4);
		GridPane.setValignment(prixMaterielTexte, VPos.TOP);
		GridPane.setHalignment(prixMaterielTexte, HPos.LEFT);
		prixMaterielTexte.getStyleClass().add("p-10");
		
		Texte prixMateriel = new Texte(salle.getCoutMateriel()+"€");
		add(prixMateriel, 0, 4);
		GridPane.setValignment(prixMateriel, VPos.TOP);
		GridPane.setHalignment(prixMateriel, HPos.RIGHT);
		prixMateriel.getStyleClass().add("p-10");
		
		Texte prixDernierMaterielTexte = new Texte("Prix du dernier matériel");
		add(prixDernierMaterielTexte, 0, 5);
		GridPane.setHalignment(prixDernierMaterielTexte, HPos.LEFT);
		prixDernierMaterielTexte.setStyle("-fx-padding:10 10 0 10;");
		
		Texte prixDernierMateriel = new Texte(salle.getCoutMateriel()*2+"€");
		add(prixDernierMateriel, 0, 5);
		GridPane.setHalignment(prixDernierMateriel, HPos.RIGHT);
		prixDernierMateriel.setStyle("-fx-padding:10 10 0 10;");
		
		Texte plus2materiels = new Texte("plus 2 matériels");
		add(plus2materiels, 0, 6);
		GridPane.setValignment(plus2materiels, VPos.TOP);
		GridPane.setHalignment(plus2materiels, HPos.RIGHT);
		plus2materiels.setStyle("-fx-padding:0 10 10 10;");
		plus2materiels.setSize(0.5);
		
		HBox hcopyleft = new HBox();
		add(hcopyleft, 0, 6);
		hcopyleft.setStyle("-fx-padding:10");
		hcopyleft.setAlignment(Pos.CENTER_LEFT);
		GridPane.setValignment(hcopyleft, VPos.BOTTOM);
		GridPane.setHalignment(hcopyleft, HPos.LEFT);
		
		Texte copyleft = new Texte("©");
		hcopyleft.getChildren().add(copyleft);
		
		copyleft.setSize(2);
		copyleft.setRotate(180);
		Texte copyLeftTexte = new Texte("Aucun droit réservé");
		hcopyleft.getChildren().add(copyLeftTexte);
		
		setOnMouseClicked(e->{
			MonopolyIUT.getPartie().getView().afficherFiche(this);
		});
		
	}
	
}
