package view;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

import back.Joueur;
import javafx.animation.Animation.Status;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ActionAvancerView extends ActionView{
	private Bouton lancer;
	
	public ActionAvancerView(Joueur joueur) {
		super(joueur);
		getColumnConstraints().addAll(Utiles.getCC(50),Utiles.getCC(50));
		getRowConstraints().addAll(Utiles.getRC(70),Utiles.getRC(30));

		ImageView javabien = new ImageView("M.Monopoly.png");
		javabien.setPreserveRatio(true);
		javabien.setFitHeight(1);
		javabien.setMouseTransparent(true);
		GridPane.setValignment(javabien, VPos.BOTTOM);
		GridPane.setHalignment(javabien, HPos.CENTER);
		heightProperty().addListener(e->javabien.setFitHeight(getHeight()/2));
		javabien.setTranslateY(20);
		add(javabien,0 ,0 , 2, 1);

		lancer = getBouton("Lancer les Dés");
		add(lancer, 0, 1, 2, 1);
		lancer.setDefaultButton(true);


		lancer.setOnAction(e->{
			lancer.setOnAction(null);
			joueur.lancerDe();
			new Thread() {
				public void run() {
					Platform.runLater(()->{
						getChildren().remove(javabien);
						getChildren().remove(lancer);
					});
					for(int i=0;i<5;i++) {
						De de1 = new De(new Random().nextInt(4)+1);
						De de2 = new De(new Random().nextInt(4));
						Platform.runLater(()->{
							add(Utiles.placer(de1, 70, 35),0,0,1,2);
							add(Utiles.placer(de2, 70, 35),1,0,1,2);
						});
						try {sleep(100);} catch (InterruptedException e) {}
						Platform.runLater(()->getChildren().removeAll(de1,de2));
					}
					Platform.runLater(()->{
						add(Utiles.placer(new De(joueur.getDe1()), 70, 35),0,0,1,2);
						add(Utiles.placer(new De(joueur.getDe2()), 70, 35	),1,0,1,2);
					});
					PauseTransition pause = new PauseTransition(Duration.millis(1500));
					pause.setOnFinished(e2->MonopolyIUT.getPartie().jouerEtape());
					pause.play();
				}
			}.start();
		});
		if(MonopolyIUT.getPartie().getTempsTour()>=0) {
			if((MonopolyIUT.getPartie().getDebutTour()-Date.from(Instant.now()).getTime())/1000<0)desactiverBoutons();
			add(chrono, 1, 0);
			timeline.statusProperty().addListener((val, oldVal, newVal)->{
				if(newVal==Status.STOPPED&&!joueur.isBot()) {
	        		lancer.fire();
				}
			});
			timeline.play();
		}
		javabien.toFront();
	}
	
	protected void desactiverBoutons() {
		lancer.disable(true, true);
	}

	private Bouton getBouton(String texte) {
		Bouton b = new Bouton(texte);
		b.setStyle("-fx-padding:25 50 25 50");
		GridPane.setHalignment(b, HPos.CENTER);
		GridPane.setValignment(b, VPos.TOP);
		return b;
	}

	public void fireDes() {
		lancer.fire();
	}
}
