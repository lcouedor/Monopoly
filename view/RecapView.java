package view;

import back.Propriete;
import javafx.collections.transformation.SortedList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Texte.Polices;

public class RecapView extends Stage {
	int c = 0;
	
	public RecapView() {
		GridPane grid = new GridPane();
		
		SortedList<JoueurClassementView> j = MonopolyIUT.getPartie().getSortedListeJoueurs();

		grid.getStylesheets().add("style.css");
		grid.getStyleClass().add("liste-transparente");
		grid.setStyle("-fx-background-color : #95cde5");

		RowConstraints row20 = new RowConstraints();
		row20.setPercentHeight(20);
		ColumnConstraints cc = Utiles.getCC(20);

		grid.getColumnConstraints().addAll(cc,cc,cc,cc,cc);
		grid.getRowConstraints().addAll(row20,row20,row20,row20,row20);

		Pane pane = new Pane();
		pane.setStyle("-fx-background-color :"+ j.get(c).getJoueur().getPion().getCouleur());
		Texte nom = new Texte(j.get(c).getJoueur().getPseudo().toUpperCase());
		nom.setPolice(Polices.COMICSTRICKS);
		nom.setSize(4);
		StackPane gstack = new StackPane();
		ImageView gauche = new ImageView("fgauche.png");
		gstack.setCursor(Cursor.HAND);
		gstack.getChildren().add(gauche);

		StackPane dstack = new StackPane();
		ImageView droite = new ImageView("fdroite.png");
		dstack.setCursor(Cursor.HAND);
		dstack.getChildren().add(droite);

		Texte lRestant = new Texte(String.valueOf(j.get(c).getJoueur().getArgentBis()) + "€ restants");
		HBox plRestant = new HBox();
		plRestant.getChildren().add(lRestant);
		plRestant.setStyle("-fx-border-style : hidden hidden solid hidden; -fx-border-color : black; -fx-border-width : 3;");
		plRestant.setAlignment(Pos.CENTER);

		Texte lLoc = new Texte("Locations");
		Texte lChance = new Texte("Cartes chances");
		Texte lLoc2 = new Texte("Locations");
		Texte lChance2 = new Texte("Cartes chances");

		VBox vGagne = new VBox();
		Texte titreGagne = new Texte(String.valueOf(j.get(c).getJoueur().getStats().getGagne())+"€ gagnés au total");
		Texte valGagneLoc = new Texte(String.valueOf(j.get(c).getJoueur().getStats().getGagneLocation())+"€");
		Texte valGagneChance = new Texte(String.valueOf(j.get(c).getJoueur().getStats().getGagneChances())+"€");
		VBox vGagneLoc = new VBox();
		VBox vGagneChance = new VBox();
		HBox hGagne = new HBox();
		vGagneLoc.getChildren().addAll(lLoc,valGagneLoc);
		vGagneLoc.setAlignment(Pos.CENTER);
		vGagneLoc.setPadding(new Insets(0,20,0,0));
		vGagneChance.getChildren().addAll(lChance,valGagneChance);
		vGagneChance.setAlignment(Pos.CENTER);
		vGagneChance.setPadding(new Insets(0,0,0,20));
		hGagne.getChildren().addAll(vGagneLoc,vGagneChance);
		hGagne.setAlignment(Pos.CENTER);
		vGagne.getChildren().addAll(titreGagne,hGagne);
		vGagne.setAlignment(Pos.CENTER);
		vGagne.setStyle("-fx-border-style : hidden hidden solid hidden; -fx-border-color : black; -fx-border-width : 3;");

		VBox vDepense = new VBox();
		Texte titreDepense = new Texte(String.valueOf(j.get(c).getJoueur().getStats().getPerdu())+ "€ dépensés au total");
		Texte valDepenseLoc = new Texte(String.valueOf(j.get(c).getJoueur().getStats().getPerduLocation())+"€");
		Texte valDepenseChance = new Texte(String.valueOf(j.get(c).getJoueur().getStats().getPerduChances())+"€");
		VBox vDepenseLoc = new VBox();
		VBox vDepenseChance = new VBox();
		HBox hDepense = new HBox();
		vDepenseLoc.getChildren().addAll(lLoc2,valDepenseLoc);
		vDepenseLoc.setAlignment(Pos.CENTER);
		vDepenseLoc.setPadding(new Insets(0,20,0,0));
		vDepenseChance.getChildren().addAll(lChance2,valDepenseChance);
		vDepenseChance.setAlignment(Pos.CENTER);
		vDepenseChance.setPadding(new Insets(0,0,0,20));
		hDepense.getChildren().addAll(vDepenseLoc,vDepenseChance);
		hDepense.setAlignment(Pos.CENTER);
		vDepense.getChildren().addAll(titreDepense,hDepense);
		vDepense.setAlignment(Pos.CENTER);
		vDepense.setStyle("-fx-border-style : hidden hidden solid hidden; -fx-border-color : black; -fx-border-width : 3;");

		VBox vDSplat = new VBox();
		Texte lTours = new Texte("Tours de plateau: " + String.valueOf(j.get(c).getJoueur().getStats().getNbTours()));
		Texte lDS = new Texte("Nombre de DS passés: " + String.valueOf(j.get(c).getJoueur().getStats().getNbDS()));
		vDSplat.getChildren().addAll(lTours,lDS);
		vDSplat.setAlignment(Pos.CENTER);

		VBox vClassement = new VBox();
		Texte classement = new Texte("1er");
		classement.setSize(3);
		classement.setStyle("-fx-text-fill : gold;");
		classement.getStyleClass().addAll("outline","grosoutline");
		
		vClassement.getChildren().add(classement);
		vClassement.setAlignment(Pos.CENTER);

		Pane rectangle = new Pane();
		rectangle.setStyle("-fx-background-color : black");
		rectangle.setMaxWidth(5);

		//--------------------------------
		VBox vSalles = new VBox();
		Texte lSalles = new Texte("Salles");
		Texte lAchete = new Texte("Achetées");
		Texte lVendue = new Texte("Vendues");
		HBox hSalles = new HBox();
		VBox vASalles = new VBox();
		VBox vVSalles = new VBox();

		
		ListView<Texte> proprietesA = new ListView<Texte>();
		for(Propriete p : j.get(c).getJoueur().getStats().getSalleAchetes()) {
			Texte propriete = new Texte(p.getNom().toUpperCase());
			propriete.setPolice(Polices.COMICSTRICKS);
			propriete.setSize(1.2);
			Utiles.centrer(propriete);
			propriete.setStyle("-fx-background-color:"+p.getCouleur());
			proprietesA.getItems().add(propriete);
		}
		
		ListView<Texte> proprietesV = new ListView<Texte>();
		for(Propriete p : j.get(c).getJoueur().getStats().getSalleVendues()) {
			Texte propriete = new Texte(p.getNom().toUpperCase());
			propriete.setPolice(Polices.COMICSTRICKS);
			propriete.setSize(1.2);
			Utiles.centrer(propriete);
			propriete.setStyle("-fx-background-color:"+p.getCouleur());
			proprietesV.getItems().add(propriete);
			
			
			Texte propriete2 = new Texte(p.getNom().toUpperCase());
			propriete2.setPolice(Polices.COMICSTRICKS);
			propriete2.setSize(1.2);
			Utiles.centrer(propriete2);
			propriete2.setStyle("-fx-background-color:"+p.getCouleur());
			proprietesA.getItems().add(propriete2);
		}
		proprietesA.getStyleClass().add("border");
		proprietesA.maxHeightProperty().bind(heightProperty().multiply(0.2));
		proprietesV.getStyleClass().add("border");
		proprietesV.maxHeightProperty().bind(heightProperty().multiply(0.2));

		vASalles.getChildren().addAll(lAchete, proprietesA);
		vASalles.setAlignment(Pos.CENTER);
		vASalles.setPadding(new Insets(0,20,0,0));
		vVSalles.getChildren().addAll(lVendue, proprietesV);
		vVSalles.setAlignment(Pos.CENTER);
		vVSalles.setPadding(new Insets(0,0,0,20));
		hSalles.getChildren().addAll(vASalles,vVSalles);
		hSalles.setAlignment(Pos.CENTER);
		vSalles.getChildren().addAll(lSalles,hSalles);
		vSalles.setAlignment(Pos.CENTER);
		vSalles.setStyle("-fx-border-style : hidden hidden solid hidden; -fx-border-color : black; -fx-border-width : 3;");
		
		//-----------------------------
		
		VBox vMat = new VBox();
		Texte lMat = new Texte("Matériels");
		Texte lAchete1 = new Texte("Achetés");
		Texte lVendue1 = new Texte("Vendus");
		HBox hMat = new HBox();
		VBox vAMat = new VBox();
		VBox vVMat = new VBox();
		
		Bouton fermer = new Bouton("Fermer");
		fermer.setCursor(Cursor.HAND);
		fermer.setPrefWidth(250);
		fermer.setOnAction(e->{
			MonopolyIUT.setScene(new AccueilView());
			close();
		});
		
		HBox hbtn = new HBox();
		hbtn.getChildren().add(fermer);
		hbtn.setAlignment(Pos.CENTER_RIGHT);
		hbtn.setPadding(new Insets(50,50,0,0));
		
		ListView<Texte> matA = new ListView<Texte>();
		for(Propriete p : j.get(c).getJoueur().getStats().getSalleAchetes()) {
			Texte propriete = new Texte(p.getNom().toUpperCase()+" - 1");
			propriete.setPolice(Polices.COMICSTRICKS);
			propriete.setSize(1.2);
			Utiles.centrer(propriete);
			propriete.setStyle("-fx-background-color:"+p.getCouleur());
			matA.getItems().add(propriete);
		}
		
		ListView<Texte> matV = new ListView<Texte>();
		for(Propriete p : j.get(c).getJoueur().getStats().getSalleVendues()) {
			Texte propriete = new Texte(p.getNom().toUpperCase()+" - 1");
			propriete.setPolice(Polices.COMICSTRICKS);
			propriete.setSize(1.2);
			Utiles.centrer(propriete);
			propriete.setStyle("-fx-background-color:"+p.getCouleur());
			matV.getItems().add(propriete);
			
			Texte propriete2 = new Texte(p.getNom().toUpperCase()+" - 1");
			propriete2.setPolice(Polices.COMICSTRICKS);
			propriete2.setSize(1.2);
			Utiles.centrer(propriete2);
			propriete2.setStyle("-fx-background-color:"+p.getCouleur());
			matA.getItems().add(propriete2);
		}
		matV.getStyleClass().add("border");
		matV.maxHeightProperty().bind(heightProperty().multiply(0.2));
		matA.getStyleClass().add("border");
		matA.maxHeightProperty().bind(heightProperty().multiply(0.2));
		
		vAMat.getChildren().addAll(lAchete1, matA);
		vAMat.setAlignment(Pos.CENTER);
		vAMat.setPadding(new Insets(0,20,0,0));
		vVMat.getChildren().addAll(lVendue1, matV);
		vVMat.setAlignment(Pos.CENTER);
		vVMat.setPadding(new Insets(0,0,0,20));
		hMat.getChildren().addAll(vAMat,vVMat);
		hMat.setAlignment(Pos.CENTER);
		vMat.getChildren().addAll(lMat,hMat, hbtn);
		vMat.setAlignment(Pos.CENTER);
		
		gstack.setOnMouseClicked(e->{
			c = (c-1 + MonopolyIUT.getPartie().getLesJoueurs().size() )%MonopolyIUT.getPartie().getLesJoueurs().size();
			pane.setStyle("-fx-background-color :"+ j.get(c).getJoueur().getPion().getCouleur());
			nom.setText(j.get(c).getJoueur().getPseudo().toUpperCase());
			
			matA.getItems().clear();
			matV.getItems().clear();
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleAchetes()) {
				Texte propriete = new Texte(p.getNom().toUpperCase());
				propriete.setPolice(Polices.COMICSTRICKS);
				propriete.setSize(1.2);
				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				matA.getItems().add(propriete);
			}
			
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleVendues()) {
				Texte propriete = new Texte(p.getNom().toUpperCase());
				propriete.setPolice(Polices.COMICSTRICKS);
				propriete.setSize(1.2);
				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				matV.getItems().add(propriete);
			}
			
			proprietesA.getItems().clear();
			proprietesV.getItems().clear();
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleAchetes()) {
				Texte propriete = new Texte(p.getNom().toUpperCase());
				propriete.setPolice(Polices.COMICSTRICKS);
				propriete.setSize(1.2);
				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				proprietesA.getItems().add(propriete);
			}
			
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleVendues()) {
				Texte propriete = new Texte(p.getNom().toUpperCase());
				propriete.setPolice(Polices.COMICSTRICKS);
				propriete.setSize(1.2);
				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				proprietesV.getItems().add(propriete);
			}
			
			switch (c) {
				case(0): classement.setText("1er"); classement.setStyle("-fx-text-fill : gold; -fx-font-size : 50;");
					break;
				case(1): classement.setText("2ème"); classement.setStyle("-fx-text-fill : silver; -fx-font-size : 50;");
					break;
				case(2): classement.setText("3ème"); classement.setStyle("-fx-text-fill : #a8882e; -fx-font-size : 50;");
					break;
				default: classement.setText(c+"ème"); classement.setStyle("-fx-text-fill : black; -fx-font-size : 50;");
			}
			
			lRestant.setText(String.valueOf(j.get(c).getJoueur().getArgentBis()) + "€ restants");
			titreGagne.setText(String.valueOf(j.get(c).getJoueur().getStats().getGagne())+"€ gagnés au total");
			valGagneLoc.setText(String.valueOf(j.get(c).getJoueur().getStats().getGagneLocation())+"€");
			valGagneChance.setText(String.valueOf(j.get(c).getJoueur().getStats().getGagneChances())+"€");
			titreDepense.setText(String.valueOf(j.get(c).getJoueur().getStats().getPerdu())+ "€ dépensés au total");
			valDepenseLoc.setText(String.valueOf(j.get(c).getJoueur().getStats().getPerduLocation())+"€");
			valDepenseChance.setText(String.valueOf(j.get(c).getJoueur().getStats().getPerduChances())+"€");
			lTours.setText("Tours de plateau: " + String.valueOf(j.get(c).getJoueur().getStats().getNbTours()));
			lDS.setText("Nombre de DS passés: " + String.valueOf(j.get(c).getJoueur().getStats().getNbDS()));
		});
		
		
		
		dstack.setOnMouseClicked(e->{
			c= (c+1)%MonopolyIUT.getPartie().getLesJoueurs().size();
			pane.setStyle("-fx-background-color :"+ j.get(c).getJoueur().getPion().getCouleur());
			nom.setText(j.get(c).getJoueur().getPseudo().toUpperCase());
			
			matA.getItems().clear();
			matV.getItems().clear();
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleAchetes()) {
				Texte propriete = new Texte(p.getNom());

				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				matA.getItems().add(propriete);
			}
			
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleVendues()) {
				Texte propriete = new Texte(p.getNom());

				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				matV.getItems().add(propriete);
			}
			
			proprietesA.getItems().clear();
			proprietesV.getItems().clear();
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleAchetes()) {
				Texte propriete = new Texte(p.getNom());

				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				proprietesA.getItems().add(propriete);
			}
			
			for(Propriete p : j.get(c).getJoueur().getStats().getSalleVendues()) {
				Texte propriete = new Texte(p.getNom());

				Utiles.centrer(propriete);
				propriete.setStyle("-fx-background-color:"+p.getCouleur());
				proprietesV.getItems().add(propriete);
			}
			
			switch (c) {
			case(0): classement.setText("1er"); classement.setStyle("-fx-text-fill : gold; -fx-font-size : 50;");
				break;
			case(1): classement.setText("2ème"); classement.setStyle("-fx-text-fill : silver; -fx-font-size : 50;");
				break;
			case(2): classement.setText("3ème"); classement.setStyle("-fx-text-fill : #a8882e; -fx-font-size : 50;");
				break;
			default: classement.setText(c+"ème"); classement.setStyle("-fx-text-fill : black; -fx-font-size : 50;");
			}
			
			lRestant.setText(String.valueOf(j.get(c).getJoueur().getArgentBis()) + "€ restants");
			titreGagne.setText(String.valueOf(j.get(c).getJoueur().getStats().getGagne())+"€ gagnés au total");
			valGagneLoc.setText(String.valueOf(j.get(c).getJoueur().getStats().getGagneLocation())+"€");
			valGagneChance.setText(String.valueOf(j.get(c).getJoueur().getStats().getGagneChances())+"€");
			titreDepense.setText(String.valueOf(j.get(c).getJoueur().getStats().getPerdu())+ "€ dépensés au total");
			valDepenseLoc.setText(String.valueOf(j.get(c).getJoueur().getStats().getPerduLocation())+"€");
			valDepenseChance.setText(String.valueOf(j.get(c).getJoueur().getStats().getPerduChances())+"€");
			lTours.setText("Tours de plateau: " + String.valueOf(j.get(c).getJoueur().getStats().getNbTours()));
			lDS.setText("Nombre de DS passés: " + String.valueOf(j.get(c).getJoueur().getStats().getNbDS()));
		});





		grid.add(pane, 0, 0, 5, 1);
		grid.add(nom, 1, 0, 3, 1);
		grid.add(gstack, 0, 0, 1, 1);
		grid.add(dstack, 4, 0, 1, 1);
		grid.add(plRestant, 0, 1, 2, 1);
		grid.add(vGagne, 0, 2, 2, 1);
		grid.add(vDepense, 0, 3, 2, 1);
		grid.add(vDSplat, 0, 4, 2, 1);
		grid.add(vClassement, 2, 1, 1, 1);
		grid.add(rectangle, 2, 2, 1, 4);
		grid.add(vSalles, 3, 1, 2, 2);
		grid.add(vMat, 3, 3, 2, 2);

		GridPane.setHalignment(nom, HPos.CENTER);
		GridPane.setHalignment(gauche, HPos.CENTER);
		GridPane.setHalignment(droite, HPos.CENTER);
		GridPane.setHalignment(rectangle, HPos.CENTER);
		
		initModality(Modality.APPLICATION_MODAL);
		setTitle("MonopolyIUT");
		setScene(new Scene(grid));
		setResizable(false);
		setFullScreen(true);
		setOnCloseRequest(e->{
			MonopolyIUT.setScene(new AccueilView());
		});

	}

}
