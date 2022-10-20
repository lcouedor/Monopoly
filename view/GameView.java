package view;

import back.Joueur;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class GameView extends GridPane{
	private GridPane fiche;
	private ClassementView classement;
	private GridPane resumesJoueurs;
	
	public GameView() {
		classement = new ClassementView();
		NumberBinding nb = Bindings.min(MonopolyIUT.getStage().heightProperty().subtract(80), MonopolyIUT.getStage().widthProperty().multiply(0.6).subtract(40));
		setStyle("-fx-background-color:#95cde5");
		getRowConstraints().addAll(Utiles.getRC(100));
		getColumnConstraints().addAll(Utiles.getCC(15),new ColumnConstraints(),Utiles.getCC());
		PlateauView plateau = MonopolyIUT.getPartie().getPlateau().getView();
		add(plateau, 1, 0);
		plateau.maxWidthProperty().bind(nb);
		plateau.minWidthProperty().bind(nb);
		plateau.maxHeightProperty().bind(nb);
		plateau.minHeightProperty().bind(nb);
		GridPane.setHalignment(plateau, HPos.CENTER);

		fiche=Utiles.placer(classement, 80, 75);
		add(fiche, 2, 0);

		resumesJoueurs = new GridPane();
		resumesJoueurs.setAlignment(Pos.CENTER_LEFT);
		resumesJoueurs.setVgap(20);
		resumesJoueurs.getColumnConstraints().addAll(Utiles.getCC(100));
		add(resumesJoueurs, 0, 0);

		for(Joueur j : MonopolyIUT.getPartie().getLesJoueurs()){
			ResumeJoueurView resume = j.getResumeJoueurView();
			GridPane.setFillHeight(resume, false);
			resumesJoueurs.getRowConstraints().add(Utiles.getRC());
			resumesJoueurs.add(resume, 0, MonopolyIUT.getPartie().getLesJoueurs().indexOf(j));
			MonopolyIUT.getPartie().getPlateau().getListePlateau().get(0).getView().ajouterPion(j.getPion());
		}
		
	}
	
	public void retirerResumeJoueur() {
		resumesJoueurs.getChildren().clear();
		resumesJoueurs.getRowConstraints().clear();
		for(Joueur j : MonopolyIUT.getPartie().getLesJoueurs()){
			ResumeJoueurView resume = j.getResumeJoueurView();
			GridPane.setFillHeight(resume, false);
			resumesJoueurs.getRowConstraints().add(Utiles.getRC());
			resumesJoueurs.add(resume, 0, MonopolyIUT.getPartie().getLesJoueurs().indexOf(j));
		}
	}

	public void afficherFiche(Node f) {
		fiche.getChildren().get(fiche.getChildren().size()-1).setVisible(false);
		if(fiche.getChildren().contains(f)) {
			fiche.getChildren().remove(f);
		}else {
			if(f instanceof FicheJoueur) {
				fiche.getChildren().clear();
				fiche.getChildren().add(classement);
			}
			if(fiche.getChildren().size()>1&&!(fiche.getChildren().get(fiche.getChildren().size()-1) instanceof FicheJoueur))fiche.getChildren().remove(fiche.getChildren().size()-1);
			fiche.add(f, 1, 1);
		}
		fiche.getChildren().get(fiche.getChildren().size()-1).setVisible(true);
	}
}
