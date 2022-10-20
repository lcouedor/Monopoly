package view;

import back.Depart;
import back.Joueur;
import back.Partie.Etapes;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 * 
 * Cette classe Pion permet de creer des pions.
 * Un pion se caracterise par sa couleur et sa position.
 *
 */
public class Pion extends Circle{
	private Joueur joueur;
	private String couleur;
	private int position;
	private SequentialTransition transitions=new SequentialTransition();

	/**
	 * Le constructeur de la classe Pion permet de creer un nouveau pion. 	
	 * @param couleur Couleur du pion
	 * @param joueur joueur du pion
	 */
	//Constructeur
	public Pion(Joueur joueur, String couleur) {
		super(0);
		setFill(Color.web(couleur));
		this.joueur=joueur;
		this.couleur = couleur;
		position=0;
		transitions.setOnFinished(e->{
			transitions.getChildren().clear();
			CaseView caseFrom = MonopolyIUT.getPartie().getPlateau().getListePlateau().get(position).getView();
			caseFrom.toFront();
			TranslateTransition ttFin = new TranslateTransition(Duration.millis(200),this);
			ttFin.setInterpolator(Interpolator.LINEAR);
			ttFin.setToY(caseFrom.getHeight()/2-getRadius());
			ttFin.setOnFinished(e2->{
				caseFrom.ajouterPion(this);
				setTranslateX(0);
				setTranslateY(0);
				MonopolyIUT.getPartie().jouerEtape();
			});
			ttFin.play();
		});
		GridPane.setHalignment(this, HPos.CENTER);
		GridPane.setValignment(this, VPos.CENTER);
		setOnMouseClicked(e->{
			MonopolyIUT.getPartie().getView().afficherFiche(joueur.getFicheDetailJoueur());
		});
		hoverProperty().addListener((val,oldVal,newVal)->{	
			MonopolyIUT.getPartie().getPlateau().getListePlateau().get(position).getView().hoverToggle(newVal);
			if(newVal) {
				setStroke(Color.BLACK);
				setStrokeType(StrokeType.INSIDE);
				setStrokeWidth(2);
				setCursor(Cursor.HAND);
			}else {
				setStroke(null);
			}
		});
	}

	//Getter et Setter
	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position=position;
	}

	/**
	 * Methode deplacer permet de changer la position du pion.
	 * 
	 * @param deplacement Nombre de cases de déplacement
	 */
	public void seDeplacer(int deplacement) {
		MonopolyIUT.getPartie().setEtape(Etapes.AVANT_CASE);
		boolean caseDepart=false;
		MonopolyIUT.getPartie().getPlateau().getListePlateau().get(position).getView().toFront();
		CaseView caseInit = MonopolyIUT.getPartie().getPlateau().getListePlateau().get((position)%MonopolyIUT.getPartie().getPlateau().getTaille()).getView();
		TranslateTransition ttInit = new TranslateTransition(Duration.millis(100),this);
		ttInit.setInterpolator(Interpolator.LINEAR);
		ttInit.setToX(sceneToLocal(caseInit.localToScene(caseInit.getBoundsInLocal())).getMinX()+caseInit.getWidth()/2);
		ttInit.setToY(sceneToLocal(caseInit.localToScene(caseInit.getBoundsInLocal())).getMinY()+caseInit.getHeight()/2);
		ttInit.setOnFinished(e->{
			setTranslateX(0);
			setTranslateY(0);
			caseInit.add(this,0,0,10,10);
			caseInit.toFront();
		});
		transitions.getChildren().add(ttInit);
		for(int i=0;i<Math.abs(deplacement);i++) {
			CaseView caseFrom = MonopolyIUT.getPartie().getPlateau().getListePlateau().get(position).getView();
			position=(position+(deplacement/Math.abs(deplacement))*joueur.getModificateur().getSensDeplacement()+MonopolyIUT.getPartie().getPlateau().getTaille())%MonopolyIUT.getPartie().getPlateau().getTaille();
			CaseView caseTo = MonopolyIUT.getPartie().getPlateau().getListePlateau().get(position).getView();
			TranslateTransition tt = new TranslateTransition(Duration.millis(100),this);
			tt.setInterpolator(Interpolator.LINEAR);
			tt.setToX(caseFrom.sceneToLocal(caseTo.localToScene(caseTo.getBoundsInLocal())).getMinX());
			tt.setToY(caseFrom.sceneToLocal(caseTo.localToScene(caseTo.getBoundsInLocal())).getMinY());
			tt.setOnFinished(e->{
				setTranslateX(0);
				setTranslateY(0);
				caseTo.add(this,0,0,10,10);
				caseTo.toFront();
			});
			transitions.getChildren().add(tt);
			if(position==0)caseDepart=true;
		}
		transitions.play();	
		if(caseDepart) {
			((Depart)MonopolyIUT.getPartie().getPlateau().getCase("Départ")).caseDepart(joueur);
			joueur.getModificateur().setToucheBourse(1);
			joueur.getStats().setNbTours(joueur.getStats().getNbTours()+1);
			joueur.getModificateur().setSensDeplacement(true);
		}
	}
	
	public void seRendre(int caseDestination) {
		seDeplacer(caseDestination-position+(caseDestination-position>0?0:MonopolyIUT.getPartie().getPlateau().getTaille()));
	}
	
	public void seRendre(String nom) {
		seRendre(MonopolyIUT.getPartie().getPlateau().getCase(nom).getId());
	}
	
	public void interrompre() {
		transitions.stop();
	}

}
