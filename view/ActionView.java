package view;

import java.time.Instant;
import java.util.Date;

import back.Chance;
import back.Joueur;
import back.Propriete;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ActionView extends GridPane {
	private static ActionView actionAffichee;
	private static PauseTransition pause = new PauseTransition(Duration.millis(1000));
	protected static IntegerProperty argentModif=new SimpleIntegerProperty(0);
	protected Texte chrono;
	protected Timeline timeline;

	public ActionView(Joueur j) {
		argentModif.set(0);
		setStyle("-fx-border-width:10;-fx-border-color:"+j.getPion().getCouleur());
		chrono=new Texte();	
		GridPane.setValignment(chrono, VPos.TOP);
		GridPane.setHalignment(chrono, HPos.RIGHT);
		chrono.getStyleClass().addAll("outline","grosoutline");
		chrono.setStyle("-fx-text-fill:green;");
		if(MonopolyIUT.getPartie().getTempsTour()>=0) {
			long tempsTour = (MonopolyIUT.getPartie().getDebutTour()-Date.from(Instant.now()).getTime())/1000;
			if(tempsTour<=0) {
				chrono.setText("0");
				chrono.setStyle("-fx-text-fill:red;");
				setStyle("-fx-border-insets:3, 0;-fx-border-width:10, 3;-fx-border-color:"+j.getPion().getCouleur()+",red;");
			}
			chrono.setSize(2);
			chrono.setTranslateX(-20);
			timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
	    	    @Override
	    	    public void handle(ActionEvent event) {
	    	    	long tempsTour = (MonopolyIUT.getPartie().getDebutTour()-Date.from(Instant.now()).getTime())/1000;
					chrono.setText(tempsTour>0?Long.toString(tempsTour):"0");
					if(tempsTour<20) {
						chrono.setStyle("-fx-text-fill:orange");
					}
					if(tempsTour<10) {
						chrono.setStyle("-fx-text-fill:red");
					}
					if(tempsTour<0) {
						desactiverBoutons();
						setStyle("-fx-border-insets:3, 0;-fx-border-width:10, 3;-fx-border-color:"+j.getPion().getCouleur()+",red;");
						timeline.stop();
					}
	    	    }
	    	}));
			timeline.setCycleCount(Timeline.INDEFINITE);
		}
		
	}
	
	protected void desactiverBoutons() {}
	
	public static IntegerProperty getArgentModif() {
		return argentModif;
	}

	public static void modifArgentModif(int nb) {
		argentModif.set(argentModif.get()+nb);
	}
	

	private static void afficher(ActionView av) {		
		actionAffichee=av;
		MonopolyIUT.getPartie().getPlateau().getView().afficheAction(actionAffichee);
	}
	
	public static void stopChrono() {
		actionAffichee.timeline.pause();
	}

	private static ActionAvancerView getActionAvancerView(Joueur joueur) {
		setActionAvancer(joueur);
		return (ActionAvancerView)actionAffichee;
	}

	public static void setActionAvancer(Joueur joueur) {
		afficher(new ActionAvancerView(joueur));
	}

	public static void avancer(Joueur joueur) {		
		setActionAvancer(joueur);
		MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
		pause.setOnFinished(e->{
			getActionAvancerView(joueur).fireDes();
			MonopolyIUT.getPartie().getPlateau().getView().setTransparent(false);
		});
		pause.play();
	}
	
	
	private static ActionChanceView getActionChanceView(Chance chance, Joueur j) {
		if(!(actionAffichee instanceof ActionChanceView))setActionChance(chance, j);
		return (ActionChanceView)actionAffichee;
	}
	
	public static void setActionChance(Chance chance, Joueur j) {
		afficher(new ActionChanceView(chance, j));
	}
	
	public static void chance(Chance chance, Joueur j) {
		setActionChance(chance, j);
		MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
		pause.setOnFinished(e->{
			getActionChanceView(chance, j).fireValider();
			MonopolyIUT.getPartie().getPlateau().getView().setTransparent(false);
		});
		pause.play();
	}
	
	
	
	
	private static ActionAcheterView getActionAcheterView(Joueur j, Propriete p) {
		if(!(actionAffichee instanceof ActionAcheterView))setActionAcheter(j, p);
		return (ActionAcheterView)actionAffichee;
	}
	
	public static void setActionAcheter(Joueur j, Propriete p) {
		afficher(new ActionAcheterView(j, p));
	}
	
	public static void acheter(Joueur j, Propriete p) {	
		setActionAcheter(j, p);
		MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
		pause.setOnFinished(e->{
			getActionAcheterView(j, p).fireAcheter();
			MonopolyIUT.getPartie().getPlateau().getView().setTransparent(false);
		});
		pause.play();
	}
	
	public static void pasAcheter(Joueur j, Propriete p) {	
		setActionAcheter(j, p);
		MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
		pause.setOnFinished(e->{
			getActionAcheterView(j, p).firePasAcheter();
			MonopolyIUT.getPartie().getPlateau().getView().setTransparent(false);
		});
		pause.play();
	}
	
	
	
	
	private static ActionAmeliorerView getActionAmeliorerView(Joueur j, int min) {
		if(!(actionAffichee instanceof ActionAmeliorerView))setActionAmeliorer(j, min);
		return (ActionAmeliorerView)actionAffichee;
	}
	
	public static void setActionAmeliorer(Joueur j, int min) {
		afficher(new ActionAmeliorerView(j, min));
		((ActionAmeliorerView)actionAffichee).ameliorerProprietes(j, true);
	}
	
	public static void ameliorer(Joueur j, int min) {	
		setActionAmeliorer(j, min);
		MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
		pause.setOnFinished(e->{
			((ActionAmeliorerView)actionAffichee).auto(j);
			pause.setOnFinished(e2->{
				getActionAmeliorerView(j, min).fireAmeliorer();
				MonopolyIUT.getPartie().getPlateau().getView().setTransparent(false);
			});
			pause.play();
		});
		pause.play();
	}
	
	
	
	private static ActionVendreView getActionVendreView(Joueur j, int val, Propriete p, Chance c) {
		if(!(actionAffichee instanceof ActionVendreView))setActionVendre(j, val, p, c);
		return (ActionVendreView)actionAffichee;
	}
	
	public static void setActionVendre(Joueur j, int val, Propriete p, Chance c) {
		afficher(new ActionVendreView(j, val, p, c));
		((ActionVendreView)actionAffichee).vendreProprietes(j, true);
	}
	
	public static void vendre(Joueur j, int val, Propriete p, Chance c) {	
		setActionVendre(j, val, p, c);
		MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
		pause.setOnFinished(e->{
			((ActionVendreView)actionAffichee).auto(j);
			pause.setOnFinished(e2->{
				getActionVendreView(j, val, p, c).fireVendre();
				MonopolyIUT.getPartie().getPlateau().getView().setTransparent(false);
			});
			pause.play();
		});
		pause.play();
	}
	
	
	
	private static ActionPayerView getActionPayerView(Joueur j, Propriete p) {
		if(!(actionAffichee instanceof ActionPayerView))setActionPayer(j, p);
		return (ActionPayerView)actionAffichee;
	}
	
	public static void setActionPayer(Joueur j, Propriete p) {
		afficher(new ActionPayerView(j, p));
	}
	
	public static void payer(Joueur j, Propriete p) {	
		setActionPayer(j, p);
		MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
		pause.setOnFinished(e->{
			getActionPayerView(j, p).firePayer();
			MonopolyIUT.getPartie().getPlateau().getView().setTransparent(false);
		});
		pause.play();
	}
}
