package view;

import java.time.Instant;
import java.util.Date;

import back.Case;
import back.Chance;
import back.Joueur;
import back.Propriete;
import javafx.animation.Animation.Status;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.Texte.Polices;

public class ActionVendreView extends ActionView {
	private Texte action;
	private Bouton vendre;
	private Bouton reinit;
	private Bouton auto;
	private int aPayer;
	
	public ActionVendreView(Joueur j, int aPayer, Propriete p, Chance c) {
		super(j);
		this.aPayer=aPayer;
		getColumnConstraints().addAll(Utiles.getCC(100.0/3),Utiles.getCC(100.0/3),Utiles.getCC(100.0/3));
		getRowConstraints().addAll(Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),new RowConstraints());
		setPadding(new Insets(20));
		setHgap(10);
		
		action=new Texte("Vendre".toUpperCase());
		Utiles.centrer(action);
		action.setPolice(Polices.COMICSTRICKS);
		action.setSize(1.8);
		add(action,0,0,3,1);
		
		add(getVBox("À payer",aPayer+"€"),0,1,2,1);
		
		VBox vboxGain = getVBox("Gain",argentModif+"€");
		((Texte)vboxGain.getChildren().get(1)).textProperty().bind(Bindings.concat(argentModif,"€"));
		add(vboxGain,1,1,2,1);
		
		add(getVBox("Possédé",Integer.toString(j.getArgentBis())+"€"),0,2);
		
		VBox vbox = new VBox();
		GridPane.setHalignment(vbox, HPos.CENTER);
		GridPane.setValignment(vbox, VPos.CENTER);
		Texte tTexte = new Texte(argentModif+"€");
		tTexte.textProperty().bind(Bindings.concat(argentModif.subtract(aPayer),"€"));
		tTexte.setTextFill(Color.web("#E50000"));
		tTexte.setBold();
		tTexte.setSize(1.5);
		Texte tValeur = new Texte("→");
		tValeur.setBold();
		tValeur.setScaleX(10);
		tValeur.setScaleY(6);
		tValeur.setTextFill(Color.web("#E50000"));
		vbox.getChildren().addAll(tTexte,tValeur);
		vbox.setAlignment(Pos.CENTER);
		add(vbox,1,2);
		
		VBox vboxRestant = getVBox("Restant",argentModif+"€");
		((Texte)vboxRestant.getChildren().get(1)).textProperty().bind(Bindings.concat(argentModif.add(j.getArgentBis()).subtract(aPayer),"€"));
		((Texte)vboxRestant.getChildren().get(1)).textFillProperty().bind(Bindings.when(argentModif.add(j.getArgentBis()).subtract(aPayer).greaterThan(0)).then(Color.web("#3ca74b")).otherwise(Color.web("#E50000")));
		add(vboxRestant,2,2);
		
		vendre = new Bouton("Vendre");
		vendre.setDefaultButton(true);
		Utiles.centrer(vendre);
		vendre.setCouleurBordure("#00E500");
		vendre.setCouleurHover("#00E500");
		add(vendre,2,4);
		if(!j.isBot())vendre.disableProperty().bind(argentModif.add(j.getArgentBis()).subtract(aPayer).lessThan(0).and(Bindings.createBooleanBinding(()->peutVendre(j), argentModif)));
		vendre.setOnAction(e->{
			vendre.setOnAction(null);
			if(p!=null)p.payerLoyer(j);
			if(c!=null)c.executeLaBonneCarte(j);
			if(p==null&&c==null)j.perdreArgent(aPayer, 0);
			vendreProprietes(j, false);
			PauseTransition pause = new PauseTransition(Duration.millis(1000));
			pause.setOnFinished(e2->{
				MonopolyIUT.getPartie().jouerEtape();
			});
			pause.play();
		});
		
		reinit = new Bouton("Réinitialiser");
		reinit.setSize(0.8);
		Utiles.centrer(reinit);
		reinit.setCouleurBordure("#E50000");
		reinit.setCouleurHover("#E50000");
		add(reinit,1,4);
		reinit.setOnAction(e->{
			MonopolyIUT.getPartie().getPlateau().getView().reinitialisation();
		});
		
		auto = new Bouton("Auto");
		Utiles.centrer(auto);
		add(auto,0,4);
		auto.setOnAction(e->{
			auto(j);
		});
		
		if(MonopolyIUT.getPartie().getTempsTour()>=0) {
			if((MonopolyIUT.getPartie().getDebutTour()-Date.from(Instant.now()).getTime())/1000<0) {
				desactiverBoutons();
				MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
			}
			add(chrono, 2, 0);
			timeline.statusProperty().addListener((val, oldVal, newVal)->{
				if(newVal==Status.STOPPED&&!j.isBot()) {
					PauseTransition pause = new PauseTransition(Duration.millis(1500));
					pause.setOnFinished(e2->{
						auto.fire();
						vendre.fire();
						MonopolyIUT.getPartie().getPlateau().getView().setTransparent(true);
					});
					pause.play();	
				}
			});
			timeline.play();
		}
	}
	
	protected void desactiverBoutons() {
		auto.disable(true, true);
		reinit.disable(true, true);
		vendre.disable(true, true);
	}
	
	private VBox getVBox(String texte, String valeur) {
		VBox v = new VBox();
		GridPane.setHalignment(v, HPos.CENTER);
		GridPane.setValignment(v, VPos.CENTER);
		Texte tTexte = new Texte(texte);
		tTexte.setBold();
		tTexte.setSize(1.3);
		Texte tValeur = new Texte(valeur,1.3);
		v.getChildren().addAll(tTexte,tValeur);
		v.setAlignment(Pos.CENTER);
		return v;
	}
	
	public void vendreProprietes(Joueur joueur) {
		vendreProprietes(joueur, true);
	}

	public void vendreProprietes(Joueur joueur, boolean etat) {		
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			c.getView().setModif(etat);
			if(c instanceof Propriete) {
				((ProprieteView)c.getView()).setModif(joueur, aPayer, etat);
			}
		}
		MonopolyIUT.getPartie().getPlateau().getView().reinitialisation();
	}

	public void auto(Joueur joueur) {
		double min=0;
		while(min<Integer.MAX_VALUE) {
			min=Integer.MAX_VALUE;
			ProprieteView p=null;
			for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
				if(c instanceof Propriete&&joueur.equals(((Propriete)c).getProprio())) {
					if(((ProprieteView)c.getView()).getValeur(false)<min) {
						min=((ProprieteView)c.getView()).getValeur(false);
						p=((ProprieteView)c.getView());
					}
				}
			}
			if(p!=null)p.fireMoins();
			if(joueur.getArgentBis()+argentModif.get()>aPayer||min==Integer.MAX_VALUE)break;
		}
	}
	
	public static boolean peutVendre(Joueur joueur) {
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			if(c instanceof Propriete&&joueur.equals(((Propriete)c).getProprio())) {
				if(((ProprieteView)c.getView()).isPossedee()) return true;
			}
		}
		return false;
	}
	
	public void fireVendre() {
		vendre.fire();
	}
	
}
