package view;

import back.Joueur;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class ResumeJoueurView extends GridPane{
	private Texte pseudo;
	private Texte argent;
	private Texte plus;

	public ResumeJoueurView(Joueur joueur) {
		// Pseudo
		pseudo=new Texte(joueur.getPseudo());
		pseudo.setBold();
		GridPane.setHgrow(pseudo, Priority.ALWAYS);
		Utiles.centrer(pseudo);
		
		// Argent
		argent=new Texte();
		argent.setBold();
		argent.textProperty().bind(joueur.getArgent().asString().concat("€"));
		GridPane.setHgrow(argent, Priority.ALWAYS);
		Utiles.centrer(argent);
		argent.textProperty().addListener((val,oldVal,newVal)->{
			int difference=Integer.parseInt(newVal.substring(0, newVal.length()-1))-Integer.parseInt(oldVal.substring(0, oldVal.length()-1));
			Texte argentBis = new Texte(oldVal);
			argentBis.setBold();
			Utiles.centrer(argentBis);
			argentBis.setStyle("-fx-background-color: "+joueur.getPion().getCouleur()+";-fx-background-insets: 0 0 5 0");
			add(argentBis, 1, 1);
			new Thread(){
				@Override 
				public void run() {
					int time = Math.abs(difference)<500?Math.abs(difference):500;
					for (int i = 1; i <= time; i++) {
						int counter = difference/time*i;
						Platform.runLater(new Runnable() {
							@Override public void run() {
								argentBis.setText((Integer.parseInt(oldVal.substring(0, oldVal.length()-1))+counter)+"€");
							}
						});
						try {
							sleep(1);
						} catch (InterruptedException e) {}
					}
					Platform.runLater(new Runnable() {
						public void run() {
							getChildren().remove(argentBis);
						}
					});
				}
			}.start();
		});

		// Bouton
		plus=new Texte("＋");
		plus.getStyleClass().add("bg-none");
		plus.setSize(2);
		plus.setMouseTransparent(true);

		Polygon poly = new Polygon();
		poly.setStyle("-fx-fill:"+joueur.getPion().getCouleur()+";-fx-stroke:#000;-fx-stroke-width:5");
		poly.setStrokeType(StrokeType.INSIDE);
		poly.setManaged(true);
		plus.heightProperty().addListener(e->{
			setTranslateX(-plus.getWidth());
			poly.getPoints().setAll(new Double[]{        
					0.0, 0.0, 
					0.0, getHeight(),
					getWidth()*0.8, getHeight(),
					getWidth(), 0.0,
			});
		});
		plus.widthProperty().addListener(e->{
			setTranslateX(-plus.getWidth());
			poly.getPoints().setAll(new Double[]{        
					0.0, 0.0, 
					0.0, getHeight(),
					getWidth()*0.8, getHeight(),
					getWidth(), 0.0,
			});
		});
		
		
		// Grille
		getColumnConstraints().addAll(new ColumnConstraints(),Utiles.getCC(),Utiles.getCC());
		getRowConstraints().addAll(Utiles.getRC(50),Utiles.getRC(50));
		add(poly, 0, 0, 3, 2);
		add(pseudo, 1, 0);
		add(argent, 1, 1);
		add(plus, 0, 0, 1, 2);
		getStyleClass().setAll("bold");
		setHgap(20);
		setVgap(5);
		setMinHeight(0);
		setMinWidth(0);
		setCursor(Cursor.HAND);
		RotateTransition rtplus = new RotateTransition(Duration.millis(150),plus);
		setOnMouseClicked(e->{
			MonopolyIUT.getPartie().getView().afficherFiche(joueur.getFicheDetailJoueur());
		});
		joueur.getFicheDetailJoueur().visibleProperty().addListener((val,oldVal,newVal)->{
			if(newVal) {
				rtplus.stop();
				rtplus.setFromAngle(0);
				rtplus.setToAngle(45);
				rtplus.play();
			}else {
				rtplus.stop();
				rtplus.setFromAngle(45);
				rtplus.setToAngle(0);
				rtplus.play();
			}
		});
		TranslateTransition ttthis = new TranslateTransition(Duration.millis(100),this);
		TranslateTransition ttplus = new TranslateTransition(Duration.millis(100),plus);
		setOnMouseEntered(e->{
			ttthis.stop();
			ttthis.setFromX(-plus.getWidth());
			ttthis.setToX(-5);
			ttthis.setDelay(Duration.millis(0));
			ttthis.play();
			
			ttplus.stop();
			ttplus.setFromX(-20);
			ttplus.setToX(20);
			ttplus.setDelay(Duration.millis(100));
			ttplus.play();	
		});
		setOnMouseExited(e->{
			ttthis.stop();
			ttthis.setFromX(-5);
			ttthis.setToX(-plus.getWidth());
			ttthis.setDelay(Duration.millis(100));
			ttthis.play();

			ttplus.stop();
			ttplus.setFromX(20);
			ttplus.setToX(-20);
			ttplus.setDelay(Duration.millis(0));
			ttplus.play();
		});
	}
}
