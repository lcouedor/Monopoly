package view;

import java.time.Instant;
import java.util.Date;

import back.Joueur;
import back.Propriete;
import back.Salle;
import back.Service;
import javafx.animation.Animation.Status;
import javafx.animation.PauseTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.Texte.Polices;

public class ActionPayerView extends ActionView {
	private Texte action;
	private Bouton payer;
	private Bouton vendre;
	private int loyer;
	
	public ActionPayerView(Joueur j, Propriete p) {
		super(j);
		getColumnConstraints().addAll(Utiles.getCC(100.0/3),Utiles.getCC(100.0/3),Utiles.getCC(100.0/3));
		getRowConstraints().addAll(Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),new RowConstraints());
		setPadding(new Insets(20));
		setHgap(10);
		setVgap(10);
		
		loyer = p.getLoyer((p instanceof Salle)?((Salle)p).getNbMateriels():0);
		if(p instanceof Service)loyer=((Service)p).getLoyer(0)*10*(j.getDe1()+j.getDe2());
		
		HBox hboxaction = new HBox();
		hboxaction.setAlignment(Pos.CENTER);
		hboxaction.setSpacing(10);
		add(hboxaction,0,0,3,1);
		action=new Texte("Payer".toUpperCase());
		action.setPolice(Polices.COMICSTRICKS);
		action.setSize(1.8);
		hboxaction.getChildren().add(action);
		
		Texte cout = new Texte(loyer+"€");
		cout.setBold();
		cout.setSize(1.8);
		hboxaction.getChildren().add(cout);
		
		HBox hboxJoueurPaye = new HBox();
		hboxJoueurPaye.setAlignment(Pos.CENTER);
		hboxJoueurPaye.setSpacing(50);
		hboxJoueurPaye.setStyle("-fx-border-width:5;-fx-border-color:"+j.getPion().getCouleur());
		add(hboxJoueurPaye,0,1,3,1);
		
		HBox hboxJoueurGagne = new HBox();
		hboxJoueurGagne.setAlignment(Pos.CENTER);
		hboxJoueurGagne.setSpacing(50);
		hboxJoueurGagne.setStyle("-fx-border-width:5;-fx-border-color:"+p.getProprio().getPion().getCouleur());
		add(hboxJoueurGagne,0,2,3,1);
		
		hboxJoueurPaye.getChildren().add(getVBox("Possédé",j.getArgentBis()+"€"));
		
		hboxJoueurGagne.getChildren().add(getVBox("Possédé",p.getProprio().getArgentBis()+"€"));
		
		VBox vbox = new VBox();
		GridPane.setHalignment(vbox, HPos.CENTER);
		GridPane.setValignment(vbox, VPos.CENTER);
		Texte tTexte = new Texte("-"+loyer+"€");
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
		hboxJoueurPaye.getChildren().add(vbox);
		
		VBox vbox2 = new VBox();
		GridPane.setHalignment(vbox2, HPos.CENTER);
		GridPane.setValignment(vbox2, VPos.CENTER);
		Texte tTexte2 = new Texte("+"+loyer+"€");
		tTexte2.setTextFill(Color.web("#3ca74b"));
		tTexte2.setBold();
		tTexte2.setSize(1.5);
		Texte tValeur2 = new Texte("→");
		tValeur2.setBold();
		tValeur2.setScaleX(10);
		tValeur2.setScaleY(6);
		tValeur2.setTextFill(Color.web("#3ca74b"));
		vbox2.getChildren().addAll(tTexte2,tValeur2);
		vbox2.setAlignment(Pos.CENTER);
		hboxJoueurGagne.getChildren().add(vbox2);
		
		hboxJoueurPaye.getChildren().add(getVBox("Restant",j.getArgentBis()-loyer+"€"));
		
		hboxJoueurGagne.getChildren().add(getVBox("Après",p.getProprio().getArgentBis()+loyer+"€"));
		
		payer = new Bouton("Payer");
		payer.setDefaultButton(true);
		Utiles.centrer(payer);
		payer.setCouleurBordure("#00E500");
		payer.setCouleurHover("#00E500");
		
		vendre = new Bouton("Vendre");
		vendre.setDefaultButton(true);
		Utiles.centrer(vendre);
		vendre.setCouleurBordure("#E50000");
		vendre.setCouleurHover("#E50000");
		
		if(j.getArgentBis()-loyer<0&&!j.getMatieres().isEmpty()) {
			add(vendre,1,4);
			vendre.setOnAction(e->{
				vendre.setOnAction(null);
				if(j.isBot()) {
					ActionView.vendre(j, loyer, p, null);
				}else {
					ActionView.setActionVendre(j, loyer, p, null);
				}
			});
			payer.setOnAction(null);
		}else {
			add(payer,2,4);
			payer.setOnAction(e->{
				payer.setOnAction(null);
				((Propriete)p).payerLoyer(j);
				MonopolyIUT.getPartie().jouerEtape();
			});
			vendre.setOnAction(null);
		}
		if(MonopolyIUT.getPartie().getTempsTour()>=0) {
			if((MonopolyIUT.getPartie().getDebutTour()-Date.from(Instant.now()).getTime())/1000<0)desactiverBoutons();
			add(chrono, 2, 0);
			timeline.statusProperty().addListener((val, oldVal, newVal)->{
				if(newVal==Status.STOPPED&&!j.isBot()) {
					PauseTransition pause = new PauseTransition(Duration.millis(1500));
					pause.setOnFinished(e2->{
						payer.fire();
						vendre.fire();
					});
					pause.play();	
				}
			});
			timeline.play();
		}
	}
	
	protected void desactiverBoutons() {
		payer.disable(true, true);
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
	
	public void firePayer() {
		payer.fire();
		vendre.fire();
	}
}
