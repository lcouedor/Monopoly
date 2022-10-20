package view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import view.Texte.Polices;

public class AccueilView extends GridPane{
	
	public AccueilView(){
		
		//séparation en 3 colonnes de 30% 40% 30%
		ColumnConstraints col1 = new ColumnConstraints();
	    col1.setPercentWidth(30);
	    ColumnConstraints col2 = new ColumnConstraints();
	    col2.setPercentWidth(40);
	    ColumnConstraints col3 = new ColumnConstraints();
	    col3.setPercentWidth(30);
	    getColumnConstraints().addAll(col1,col2,col3);
	    
	    //séparation en 15 lignes de 10% et 5%
	    RowConstraints row10 = new RowConstraints();
	    row10.setPercentHeight(10);
	    RowConstraints row5 = new RowConstraints();
	    row5.setPercentHeight(5);
	    
	    for (int i = 0; i<20; i++) {
	    	getRowConstraints().add(row5);
	    }
	    
	    
	    //différents nodes et leur style
	    Pane head = new Pane();
	    head.setStyle("-fx-background-color: #0097d8;");
	    
	    Pane body = new Pane();
	    body.setStyle("-fx-background-color: #95cde5;");
	    
	    VBox titre = new VBox();
	    titre.setStyle("-fx-background-color: white; -fx-background-radius: 20px;");
	    
		Bouton btnJouer = new Bouton("Jouer");
		btnJouer.setDefaultButton(true);
		btnJouer.setSize(2);
		btnJouer.setCouleur("#0097d8");
		btnJouer.setCouleurHoverTexte("#000000");
		btnJouer.setCouleurBordure("transparent");
		btnJouer.setCornerRadius(new CornerRadii(20));
        btnJouer.setPrefWidth(Integer.MAX_VALUE);
        btnJouer.setPrefHeight(Integer.MAX_VALUE);
        btnJouer.setCursor(Cursor.HAND);
        btnJouer.setOnAction(e->MonopolyIUT.setScene(new ConfigView()));
 
        Bouton btnRegles = new Bouton("Règles");
        btnRegles.setDisable(true);
        btnRegles.setSize(2);
        btnRegles.setCouleur("#0097d8");
        btnRegles.setCouleurHoverTexte("#000000");
        btnRegles.setCouleurBordure("transparent");
        btnRegles.setCornerRadius(new CornerRadii(20));
        btnRegles.setPrefWidth(Integer.MAX_VALUE);
        btnRegles.setPrefHeight(Integer.MAX_VALUE);
        btnRegles.setCursor(Cursor.HAND);
        
        Texte txt1 = new Texte("MONOPOLY");
        txt1.setPolice(Polices.COMICSTRICKS);
        txt1.setSize(3);
        txt1.setAlignment(Pos.CENTER);
        txt1.setPrefWidth(Integer.MAX_VALUE);
        
        Texte txt2 = new Texte("IUT EDITION");
        txt2.setPolice(Polices.COMICSTRICKS);
        txt2.setSize(3);
        txt2.setAlignment(Pos.CENTER);
        txt2.setPrefWidth(Integer.MAX_VALUE);
                
        //centrer txt1 dans VBox
        VBox.setVgrow(txt1, Priority.ALWAYS);
        txt1.setMaxHeight(Integer.MAX_VALUE);
        VBox.setVgrow(txt2, Priority.ALWAYS);
        txt2.setMaxHeight(Integer.MAX_VALUE);
        
        //ajouter une image
        ImageView imageView = new ImageView("M.Monopoly.png");
        imageView.setMouseTransparent(true);
        GridPane.setHalignment(imageView,HPos.CENTER);
        imageView.fitHeightProperty().bind(MonopolyIUT.getStage().heightProperty().divide(2.5));
        
        //ajouter labels à la zone de titre
        titre.getChildren().addAll(txt1,txt2);
        
        //définir taille image
        imageView.setPreserveRatio(true);
        
		//ajouter éléments à la grid
        add(body, 0, 0, 3, 20);
        add(head, 0, 0, 3, 4);
        add(titre, 1, 2, 1, 4);
        add(btnJouer, 1, 13, 1, 2);	
        add(btnRegles, 1, 16, 1, 2);
        add(imageView, 1, 6, 1, 7);
	}
	
}

