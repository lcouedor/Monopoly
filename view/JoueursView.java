package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import back.Joueur;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;

public class JoueursView extends GridPane{


	int j = 0;
	int couleurCourante = 0;
	ArrayList<String> couleursJ; 
	ArrayList<String> couleursHexa;
	ArrayList<String> pseudoJ;
	
	private Button aleatButton;
	private Circle pion;

	public JoueursView(int nbJoueurs, int nbBots) {

		couleursHexa = new ArrayList<String>();
		couleursHexa.addAll(Arrays.asList("#c0c0c0","#23e494","#ef6898","#cc3a3a","#b754d7","#edee70"));

		//-----------------------------------------

		couleursJ = new ArrayList<String>();
		pseudoJ = new ArrayList<String>(nbJoueurs+nbBots);


		for (int i=0; i<(nbJoueurs+nbBots); i++) {
			pseudoJ.add("");
		}

		//------------------------------------------

		HBox hboxBtn = new HBox();
		GridPane gridPion = new GridPane();

		setStyle("-fx-background-color: #95cde5;");
		Texte titre;
		if (nbJoueurs>=1) {
			titre = new Texte("Joueur "+(j+1));
		}else {
			titre = new Texte("Bot "+(j+1));
		}
		
		
		titre.setBold();
		titre.setSize(4);
		TextField pseudo = new TextField();
		Utiles.setSize(pseudo, 2.5);
		Bouton retour = new Bouton("Retour");
		retour.setSize(1.5);
		retour.setPrefWidth(350);
		GridPane.setHalignment(retour, HPos.LEFT);
		Bouton suivant = new Bouton("Suivant");
		suivant.setSize(1.5);
		suivant.setPrefWidth(350);
		suivant.setDefaultButton(true);
		GridPane.setHalignment(suivant, HPos.RIGHT);
		GridPane space = new GridPane();
		HBox.setHgrow(space, Priority.ALWAYS);
		hboxBtn.setSpacing(20);
		hboxBtn.getChildren().addAll(retour,space,suivant);
		pion = new Circle();
		aleatButton = new Button();
		aleatButton.setMaxHeight(Integer.MAX_VALUE);
		aleatButton.setPrefWidth(200);
		GridPane.setHalignment(aleatButton, HPos.CENTER);
		aleatButton.setBackground(new Background(new BackgroundImage(new Image("aleat.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, true, false))));

		ImageView gauche = new ImageView("gauche.png");
		gauche.fitHeightProperty().bind(MonopolyIUT.getStage().heightProperty().multiply(0.125));
		gauche.setPreserveRatio(true);
		ImageView droite = new ImageView("droite.png");
		droite.fitHeightProperty().bind(MonopolyIUT.getStage().heightProperty().multiply(0.125));
		droite.setPreserveRatio(true);

		aleatButton.setCursor(Cursor.HAND);
		gauche.setCursor(Cursor.HAND);
		droite.setCursor(Cursor.HAND);
		retour.setCursor(Cursor.HAND);
		suivant.setCursor(Cursor.HAND);

		pion.radiusProperty().bind(MonopolyIUT.getStage().heightProperty().multiply(0.15));
		pion.setStyle("-fx-fill : "+couleursHexa.get(couleurCourante));

		Utiles.centrer(titre);
		
		pseudo.setPrefWidth(Integer.MAX_VALUE);
		
		Circle cercle = new Circle(15);
		cercle.radiusProperty().bind(MonopolyIUT.getFont());
		cercle.setStyle("-fx-fill : #ff0000AF;");
		Texte info = new Texte("!");
		info.getStyleClass().add("white");
		info.setGraphic(cercle);
		info.setContentDisplay(ContentDisplay.CENTER);
		info.setTranslateX(-40);
		GridPane.setHalignment(info, HPos.RIGHT);
		Tooltip tooltip=new Tooltip("Votre pseudo doit contenir entre 1 et 10 caractères.");
		tooltip.setStyle("-fx-background-color : #ff0000AF; -fx-font-size : 20px; -fx-background-radius : 0px;");
		tooltip.setWrapText(true);
		tooltip.setMaxWidth(350);
		installTooltip(cercle,tooltip);
		
		//séparation en colonnes
		getColumnConstraints().addAll(Utiles.getCC(10),Utiles.getCC(),Utiles.getCC(10));

		//séparation en lignes
		RowConstraints rc = new RowConstraints();
		setHgap(20);
		setVgap(20);
		setPadding(new Insets(20));
		getRowConstraints().addAll(rc,rc,Utiles.getRC(),Utiles.getRC(),rc,rc);

		gridPion.getColumnConstraints().addAll(new ColumnConstraints(),Utiles.getCC(),new ColumnConstraints());
		gridPion.getRowConstraints().addAll(Utiles.getRC(100));
		gridPion.add(gauche, 0, 0, 1 ,1);
		gridPion.add(droite, 2, 0, 1, 1);
		gridPion.add(pion, 1, 0, 1, 1);
		GridPane.setHalignment(pion,HPos.CENTER);

		gauche.setOnMouseClicked(e->{
			couleurCourante = (--couleurCourante+couleursHexa.size())%couleursHexa.size();
			ajouterCouleurJ();
			setCouleurJ();
		});

		droite.setOnMouseClicked(e->{
			couleurCourante = ++couleurCourante%couleursHexa.size();
			ajouterCouleurJ();
			setCouleurJ();
		});

		aleatButton.setOnAction(e->{
			getCouleurAleat();
		});

		suivant.setDisable(true);

		suivant.setOnAction(e->{ 
				pseudoJ.set(j, pseudo.getText());
				ajouterCouleurJ();
				couleursHexa.remove(couleurCourante);
				
				j++;
				
				suivant.setText(j==nbJoueurs+nbBots-1 ? "Jouer" : "Suivant");
				
				if (j==nbJoueurs+nbBots) {
					for(int i=0;i<couleursJ.size();i++)MonopolyIUT.getPartie().ajouterUnJoueur(new Joueur(pseudoJ.get(i),couleursJ.get(i),1500,i>=nbJoueurs));
					Collections.shuffle(MonopolyIUT.getPartie().getLesJoueurs());
					for(Joueur joueur : MonopolyIUT.getPartie().getLesJoueurs()) {
						joueur.setArgent(MonopolyIUT.getPartie().getArgentDep()+MonopolyIUT.getPartie().getLesJoueurs().indexOf(joueur)*100);
						joueur.setCapital(MonopolyIUT.getPartie().getArgentDep()+MonopolyIUT.getPartie().getLesJoueurs().indexOf(joueur)*100);
					}
					MonopolyIUT.getPartie().setView(new GameView());
					MonopolyIUT.setScene(MonopolyIUT.getPartie().getView());
					//for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau())if(c instanceof Propriete)((Propriete)c).acheter(MonopolyIUT.getPartie().getLesJoueurs().get(new Random().nextInt(couleursJ.size())));
					MonopolyIUT.getPartie().jouerEtape();
				}else {
					pseudo.setText(pseudoJ.get(j));
					setCouleurJ();
					
					retour.setDisable(false); 
					if (j==(nbJoueurs+nbBots)-1 || pseudoJ.get(j).toString().isEmpty()) { 
						suivant.setDisable(true);
					}else { 
						suivant.setDisable(false); 
					}
					
					if (j<nbJoueurs) {
						titre.setText("Joueur "+(j+1));
					}else {
						titre.setText("Bot "+(j+1-nbJoueurs));
					}
				}
			
		});
		
		

		pseudo.textProperty().addListener((val, oldval, newval)->{
			boolean longueur = !newval.toString().matches(".{1,10}");
			boolean contient = pseudoJ.contains(newval)&&!pseudoJ.get(j).equals(newval);
			suivant.setDisable(longueur||contient);
			info.setVisible(longueur||contient);
			if (longueur){
				tooltip.setText("Votre pseudo doit contenir entre 1 et 10 caractères.");
			}else if (contient) {
				tooltip.setText("Pseudo déjà utilisé par un autre joueur, veuillez en choisir un autre.");
			}
		});
		
		
		retour.setOnAction(e->{
			if(j==0) {
				MonopolyIUT.setScene(new ConfigView());
			}else {
				pseudoJ.set(j, pseudo.getText());	
				j--;
				pseudo.setText(pseudoJ.get(j));
				couleursHexa.add(couleursJ.get(j));
				
				setCouleurJ();
	
				suivant.setDisable(false);
				suivant.setText("Suivant");
				if (j<nbJoueurs) {
					titre.setText("Joueur "+(j+1));
				}else {
					titre.setText("Bot "+(j+1-nbJoueurs));
				}
			}
		});

		add(titre, 1, 0);
		add(pseudo, 1, 1);
		add(info, 1, 1);
		add(gridPion, 1, 2);
		add(aleatButton, 1, 3);
		add(hboxBtn, 1, 4);
	}
	
	private void ajouterCouleurJ() {
		if(couleursJ.size()>j) {
			couleursJ.set(j,couleursHexa.get(couleurCourante));
		}else {
			couleursJ.add(couleursHexa.get(couleurCourante));
		}
	}
	
	private void setCouleurJ() {
		if(couleursJ.size()>j&&couleursHexa.contains(couleursJ.get(j))) {
			pion.setStyle("-fx-fill : "+couleursJ.get(j));
			couleurCourante = couleursHexa.indexOf(couleursJ.get(j)); 
			ajouterCouleurJ();
		}else {
			getCouleurAleat();
		}
	}
	
	private void getCouleurAleat() {
		int couleurAleatoire = new Random().nextInt(couleursHexa.size());
		while (couleursHexa.size()>1 && couleurAleatoire == couleurCourante)couleurAleatoire = new Random().nextInt(couleursHexa.size());
		couleurCourante=couleurAleatoire;
		ajouterCouleurJ();
		setCouleurJ();
	}
	
	private void installTooltip(Node n, Tooltip t) {
		t.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				t.setAnchorX(event.getScreenX());
				t.setAnchorY(event.getScreenY());
				if(!n.contains(n.screenToLocal(event.getScreenX(), event.getScreenY())))t.hide();
			}
		});
		n.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				t.show(n, event.getScreenX(), event.getScreenY());
				if(!n.contains(n.screenToLocal(event.getScreenX(), event.getScreenY())))t.hide();
			}
		});
	}

}
