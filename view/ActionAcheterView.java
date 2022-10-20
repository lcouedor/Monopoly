package view;

import java.time.Instant;
import java.util.Date;

import back.Joueur;
import back.Propriete;
import back.Partie.Etapes;
import back.Partie.TypesPartie;
import javafx.animation.PauseTransition;
import javafx.animation.Animation.Status;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import view.Texte.Polices;

public class ActionAcheterView extends ActionView {
	private Texte action;
	private Bouton oui;
	private Bouton non;
	
	public ActionAcheterView(Joueur j, Propriete p) {
		super(j);		
		
		getColumnConstraints().addAll(Utiles.getCC(100.0/3),Utiles.getCC(100.0/3),Utiles.getCC(100.0/3));
		getRowConstraints().addAll(Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),Utiles.getRC(),new RowConstraints());
		setPadding(new Insets(20));
		setHgap(10);
		
		HBox hboxaction = new HBox();
		hboxaction.setAlignment(Pos.CENTER);
		hboxaction.setSpacing(10);
		add(hboxaction,0,0,3,1);
		action=new Texte("Acheter".toUpperCase());
		action.setPolice(Polices.COMICSTRICKS);
		action.setSize(1.8);
		hboxaction.getChildren().add(action);
		
		Texte propriete = new Texte(p.getNom().toUpperCase());
		propriete.setPolice(Polices.COMICSTRICKS);
		propriete.setSize(1.8);
		propriete.setBorder(new Border(new BorderStroke(Color.BLACK,new BorderStrokeStyle(StrokeType.OUTSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,null),CornerRadii.EMPTY,new BorderWidths(2))));
		propriete.setStyle("-fx-padding:0 10 0 10;-fx-background-color:"+p.getCouleur());
		propriete.setCursor(Cursor.HAND);
		propriete.hoverProperty().addListener((val,oldVal,newVal)->{
			if(newVal) {
				propriete.setBorder(new Border(new BorderStroke(Color.BLACK,new BorderStrokeStyle(StrokeType.OUTSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,null),CornerRadii.EMPTY,new BorderWidths(4))));
			}else {
				propriete.setBorder(new Border(new BorderStroke(Color.BLACK,new BorderStrokeStyle(StrokeType.OUTSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,null),CornerRadii.EMPTY,new BorderWidths(2))));
			}
		});
		propriete.setOnMouseClicked(e->MonopolyIUT.getPartie().getView().afficherFiche(p.getFiche()));
		hboxaction.getChildren().add(propriete);
		
		add(getVBox("Coût",Integer.toString(p.getPrixAchat())+"€"),0,1,3,1);
		
		add(getVBox("Possédé",Integer.toString(j.getArgentBis())+"€"),0,2);
		
		VBox vbox = new VBox();
		GridPane.setHalignment(vbox, HPos.CENTER);
		GridPane.setValignment(vbox, VPos.CENTER);
		Texte tTexte = new Texte("-"+Integer.toString(p.getPrixAchat())+"€");
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
		
		add(getVBox("Restant",Integer.toString(j.getArgentBis()-p.getPrixAchat())+"€"),2,2);
		
		oui = new Bouton("Oui");
		oui.setDefaultButton(true);
		Utiles.centrer(oui);
		oui.setCouleurBordure("#00E500");
		oui.setCouleurHover("#00E500");
		add(oui,2,4);
		oui.setOnAction(e->{
			oui.setOnAction(null);
			non.setOnAction(null);
			((Propriete)p).acheter(j);
			if(MonopolyIUT.getPartie().getTypePartie()==TypesPartie.INVESTISSEUR&&MonopolyIUT.getPartie().getPlateau().toutesCasesAchetees()) {
				MonopolyIUT.getPartie().setEtape(Etapes.FIN_PARTIE);
			}
			MonopolyIUT.getPartie().jouerEtape();
		});
		
		non = new Bouton("Non");
		Utiles.centrer(non);
		non.setCouleurBordure("#E50000");
		non.setCouleurHover("#E50000");
		add(non,1,4);
		non.setOnAction(e->{
			oui.setOnAction(null);
			non.setOnAction(null);
			MonopolyIUT.getPartie().jouerEtape();
		});
		
		if(MonopolyIUT.getPartie().getTempsTour()>=0) {
			if((MonopolyIUT.getPartie().getDebutTour()-Date.from(Instant.now()).getTime())/1000<0)desactiverBoutons();
			add(chrono, 2, 0);
			timeline.statusProperty().addListener((val, oldVal, newVal)->{
				if(newVal==Status.STOPPED&&!j.isBot()) {
					PauseTransition pause = new PauseTransition(Duration.millis(1500));
					pause.setOnFinished(e2->non.fire());
					pause.play();	
				}
			});
			timeline.play();
		}
	}
	
	protected void desactiverBoutons() {
		oui.disable(true, true);
		non.disable(true, true);
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
	
	public void fireAcheter() {
		oui.fire();
	}
	
	public void firePasAcheter() {
		non.fire();
	}
}
