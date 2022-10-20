package view;

import java.time.Instant;
import java.util.Date;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Texte.Polices;

public class FinPartieView extends Stage{
	
	@SuppressWarnings("unchecked")
	public FinPartieView() {
		GridPane grid = new GridPane();
		
		grid.getStylesheets().add("style.css");
		grid.getStyleClass().add("liste-transparente");
		
		@SuppressWarnings("rawtypes")
		ListView classementJ = new ListView<GridPane>();
		MonopolyIUT.getPartie().setTrieCapital();
		int i = 1;
		for(JoueurClassementView j : MonopolyIUT.getPartie().getSortedListeJoueurs()) {
		      Texte joueur = new Texte(j.getJoueur().getPseudo());
		      joueur.setPadding(new Insets(10, 0, 10, 0));
		      Utiles.centrer(joueur);
		      switch (i) {
		    	  case 1: joueur.setStyle("-fx-font-size : 20; -fx-background-insets: 0,2,4,6;-fx-background-color: black, gold, black,"+j.getJoueur().getPion().getCouleur());
		    	  	break;
		    	  case 2: joueur.setStyle("-fx-font-size : 20; -fx-background-insets: 0,2,4,6;-fx-background-color: black, silver, black,"+j.getJoueur().getPion().getCouleur());
		    	  	break;
		    	  case 3: joueur.setStyle("-fx-font-size : 20; -fx-background-insets: 0,2,4,6;-fx-background-color: black, #a8882e, black,"+j.getJoueur().getPion().getCouleur());
		    	  	break;
		    	  default : joueur.setStyle("-fx-font-size : 20; -fx-background-insets: 0,3;-fx-background-color: black,"+j.getJoueur().getPion().getCouleur());
		      }
		      i++;
		      classementJ.getItems().add(Utiles.placer(joueur, 60, 100));
		}
		
		
		
		RowConstraints rc = Utiles.getRC();
		ColumnConstraints cc = Utiles.getCC();
		
		grid.setStyle("-fx-background-color : #95cde5");
		
		RowConstraints rowFixe = new RowConstraints();
		
		grid.getRowConstraints().addAll(rowFixe,rowFixe,rowFixe,rc,rowFixe);
		grid.getColumnConstraints().add(cc);
		
		Texte titre = new Texte("Fin de la partie".toUpperCase());
		titre.setPadding(new Insets(10, 0, 10, 0));
		titre.setPolice(Polices.COMICSTRICKS);
		titre.setSize(2);
		titre.setStyle("-fx-text-fill : #ff0000;");
		
		Texte duree = new Texte("Durée totale de la partie : "+(Date.from(Instant.now()).getTime()-MonopolyIUT.getPartie().getDuree())/1000);
		duree.setPadding(new Insets(20, 0, 10, 0));
		
		Texte classement = new Texte("Classement");
		classement.setPadding(new Insets(20, 0, 15, 0));
		
		Bouton recap = new Bouton("Récapitulatif");
		recap.setCursor(Cursor.HAND);
		recap.setPrefWidth(250);
		recap.setOnAction(e->{
			new RecapView().show();
			close();
		});
		recap.setDisable(true);
		
		Bouton continuer = new Bouton("Continuer");
		continuer.setCursor(Cursor.HAND);
		continuer.setPrefWidth(250);
		continuer.setOnAction(e->{
			MonopolyIUT.setScene(new AccueilView());
			close();
		});
		
		ImageView imageView = new ImageView("M.Monopoly.png");
        imageView.fitHeightProperty().bind(this.heightProperty().divide(3.5));
        imageView.setPreserveRatio(true);
        imageView.setTranslateX(20);
        imageView.setTranslateY(10);
		
		GridPane.setHalignment(titre, HPos.CENTER);
		GridPane.setHalignment(duree, HPos.CENTER);
		GridPane.setHalignment(classement, HPos.CENTER);
		GridPane.setHalignment(recap, HPos.LEFT);
		GridPane.setHalignment(imageView, HPos.RIGHT);
		GridPane.setHalignment(continuer, HPos.RIGHT);
		GridPane.setValignment(recap, VPos.BOTTOM);
		GridPane.setValignment(continuer, VPos.BOTTOM);
		GridPane.setValignment(imageView, VPos.BOTTOM);
		grid.setPadding(new Insets(20,20,20,20));
		grid.add(titre, 0, 0);
		grid.add(duree, 0, 1);
		grid.add(classement, 0, 2);
		grid.add(classementJ, 0, 3);
		grid.add(recap, 0, 4);	
		grid.add(imageView, 0, 3);
		grid.add(continuer, 0, 4);	
		initModality(Modality.APPLICATION_MODAL);
		initOwner(MonopolyIUT.getStage());
		setAlwaysOnTop(true);
		setTitle("MonopolyIUT");
		setScene(new Scene(grid));
		setWidth(800);
		setHeight(800);
		centerOnScreen();
		setResizable(false);
		setOnCloseRequest(e->{
			MonopolyIUT.setScene(new AccueilView());
		});
	}
}
