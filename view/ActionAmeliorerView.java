package view;

import java.time.Instant;
import java.util.Date;

import back.Case;
import back.Joueur;
import back.Propriete;
import back.Salle;
import back.Partie.Etapes;
import back.Partie.TypesPartie;
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

public class ActionAmeliorerView extends ActionView {
	private Texte action;
	private Bouton ameliorer;
	private Bouton reinit;
	private Bouton auto;
	private int min;

	public ActionAmeliorerView(Joueur j, int min) {
		super(j);
		this.min=min;
		getColumnConstraints().addAll(Utiles.getCC(100.0/3),Utiles.getCC(100.0/3),Utiles.getCC(100.0/3));
		getRowConstraints().addAll(Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),new RowConstraints());
		setPadding(new Insets(20));
		setHgap(10);

		action=new Texte("Améliorer".toUpperCase());
		action.setPolice(Polices.COMICSTRICKS);
		action.setSize(1.8);
		Utiles.centrer(action);
		add(action,0,0,3,1);

		VBox vboxCout = getVBox("Coût",argentModif+"€");
		((Texte)vboxCout.getChildren().get(1)).textProperty().bind(Bindings.concat(argentModif,"€"));
		add(vboxCout,0,1,3,1);

		add(getVBox("Possédé",Integer.toString(j.getArgentBis())+"€"),0,2);

		VBox vbox = new VBox();
		GridPane.setHalignment(vbox, HPos.CENTER);
		GridPane.setValignment(vbox, VPos.CENTER);
		Texte tTexte = new Texte(argentModif+"€");
		tTexte.textProperty().bind(Bindings.concat("-",argentModif,"€"));
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
		((Texte)vboxRestant.getChildren().get(1)).textProperty().bind(Bindings.concat(argentModif.multiply(-1).add(j.getArgentBis()),"€"));
		add(vboxRestant,2,2);

		ameliorer = new Bouton("Améliorer");
		ameliorer.setDefaultButton(true);
		Utiles.centrer(ameliorer);
		ameliorer.setCouleurBordure("#00E500");
		ameliorer.setCouleurHover("#00E500");
		add(ameliorer,2,4);
		ameliorer.setOnAction(e->{
			ameliorer.setOnAction(null);
			ameliorerProprietes(j, false);
			PauseTransition pause = new PauseTransition(Duration.millis(1000));
			pause.setOnFinished(e2->{
				if(MonopolyIUT.getPartie().getTypePartie()==TypesPartie.BATISSEUR&&j.getNbMaterielTotal()>=5) {
					MonopolyIUT.getPartie().setEtape(Etapes.FIN_PARTIE);
				}
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
						reinit.fire();
						ameliorer.fire();
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
		ameliorer.disable(true, true);
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

	public void ameliorerProprietes(Joueur joueur) {
		ameliorerProprietes(joueur, true);
	}

	public void ameliorerProprietes(Joueur joueur, boolean etat) {		
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			c.getView().setModif(etat);
			if(c instanceof Salle) {
				((SalleView)c.getView()).setModif(joueur, -min, etat);
			}
		}
		MonopolyIUT.getPartie().getPlateau().getView().reinitialisation();
	}

	public void auto(Joueur joueur) {
		double max=1;
		while(max>0) {
			max=0;
			SalleView p=null;
			for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
				if(c instanceof Salle&&joueur.equals(((Propriete)c).getProprio())) {
					if(((SalleView)c.getView()).getValeur(true)>max) {
						max=((SalleView)c.getView()).getValeur(true);
						p=((SalleView)c.getView());
					}
				}
			}
			if(p!=null)p.firePlus();
			if(joueur.getArgentBis()-argentModif.get()<min) {
				if(p!=null)p.fireMoins();
				max=0;
			}
		}
	}

	public void fireAmeliorer() {
		ameliorer.fire();
	}

}
