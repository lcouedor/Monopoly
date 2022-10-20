package view;

import back.Joueur;
import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import view.Texte.Polices;

public class JoueurClassementView extends GridPane{
	private Joueur joueur;
	private Texte principal;
	private Texte secondaire1;
	private Texte secondaire2;
	
	
	public JoueurClassementView(Joueur j) {
		joueur=j;
		
		getStyleClass().add("border");
		setStyle("-fx-border-width:3;-fx-border-color:"+j.getPion().getCouleur());
		getColumnConstraints().addAll(Utiles.getCC(40),Utiles.getCC(),Utiles.getCC(50));
		getRowConstraints().addAll(Utiles.getRC(50),Utiles.getRC(50));
		setPrefWidth(0);
		
		Texte nom = new Texte(j.getPseudo(),0.9);
		add(nom, 0, 0);
		nom.getStyleClass().add("border");
		nom.setStyle("-fx-text-fill:black;-fx-background-color:"+j.getPion().getCouleur()+";-fx-border-width:0 2 2 0;-fx-padding:5;-fx-border-color:"+j.getPion().getCouleur());
		Utiles.centrer(nom);
		
		principal = new Texte();
		principal.setSize(0.9);
		principal.textProperty().bind(Bindings.concat(j.getArgent(),"€ sur le compte"));
		add(principal, 1, 0, 2, 1);
		principal.getStyleClass().add("border");
		principal.setStyle("-fx-text-fill:black;-fx-border-width:0 0 2 0;-fx-padding:5;-fx-border-color:"+j.getPion().getCouleur());
		Utiles.centrer(principal);
		
		secondaire1 = new Texte();
		secondaire1.setPolice(Polices.NUNITO);
		secondaire1.setSize(0.9);
		secondaire1.textProperty().bind(Bindings.concat(j.getNbProprietes()," propriété",Bindings.when(Bindings.createBooleanBinding(()->joueur.getNbProprietes().getValue()>1,joueur.getNbProprietes())).then("s").otherwise("")));
		add(secondaire1, 0, 1, 2, 1);
		secondaire1.getStyleClass().add("border");
		secondaire1.setStyle("-fx-text-fill:black;-fx-border-width:0 2 0 0;-fx-padding:5;-fx-border-color:"+j.getPion().getCouleur());
		Utiles.centrer(secondaire1);
		
		secondaire2 = new Texte();
		secondaire2.setPolice(Polices.NUNITO);
		secondaire2.setSize(0.9);
		secondaire2.textProperty().bind(Bindings.concat(j.getCapital(),"€ de capital"));
		add(secondaire2, 2, 1);
		secondaire2.setStyle("-fx-text-fill:black;-fx-padding:5;");
		Utiles.centrer(secondaire2);
		
		Utiles.pointeur(this);
		setOnMouseClicked(e->{
			MonopolyIUT.getPartie().getView().afficherFiche(j.getFicheDetailJoueur());
		});
		String backgroundHover = ";-fx-background-color:"+j.getPion().getCouleur()+"88;";
		setOnMouseEntered(e->{
			setCursor(Cursor.HAND);
			principal.setStyle(principal.getStyle()+backgroundHover);
			secondaire1.setStyle(secondaire1.getStyle()+backgroundHover);
			secondaire2.setStyle(secondaire2.getStyle()+backgroundHover);
		});
		setOnMouseExited(e->{
			setCursor(Cursor.DEFAULT);
			principal.setStyle(principal.getStyle().replaceAll(backgroundHover, ""));
			secondaire1.setStyle(secondaire1.getStyle().replaceAll(backgroundHover, ""));
			secondaire2.setStyle(secondaire2.getStyle().replaceAll(backgroundHover, ""));
		});
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	
	public void setClassement(String classement) {
		switch(classement) {
		case "Argent":
			principal.textProperty().bind(Bindings.concat(joueur.getArgent(),"€ sur le compte"));
			secondaire1.textProperty().bind(Bindings.concat(joueur.getNbProprietes()," propriété",Bindings.when(Bindings.createBooleanBinding(()->joueur.getNbProprietes().getValue()>1,joueur.getNbProprietes())).then("s").otherwise("")));
			secondaire2.textProperty().bind(Bindings.concat(joueur.getCapital(),"€ de capital"));
			break;
		case "Proprietes":
			principal.textProperty().bind(Bindings.concat(joueur.getNbProprietes()," propriété",Bindings.when(Bindings.createBooleanBinding(()->joueur.getNbProprietes().getValue()>1,joueur.getNbProprietes())).then("s").otherwise("")));
			secondaire1.textProperty().bind(Bindings.concat(joueur.getArgent(),"€"));
			secondaire2.textProperty().bind(Bindings.concat(joueur.getCapital(),"€ de capital"));
			break;
		case "Capital":
			principal.textProperty().bind(Bindings.concat(joueur.getCapital(),"€ de capital"));
			secondaire1.textProperty().bind(Bindings.concat(joueur.getNbProprietes()," propriété",Bindings.when(Bindings.createBooleanBinding(()->joueur.getNbProprietes().getValue()>1,joueur.getNbProprietes())).then("s").otherwise("")));
			secondaire2.textProperty().bind(Bindings.concat(joueur.getArgent(),"€"));
			break;
		}
	}
}
