package view;

import javafx.animation.TranslateTransition;
import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import view.Texte.Polices;

public class ClassementView extends GridPane{
	public ClassementView() {
		getStyleClass().addAll("bg-white","border","bw-3","liste-transparente");
		
		getColumnConstraints().add(Utiles.getCC(100));
		getRowConstraints().addAll(new RowConstraints(),new RowConstraints(),Utiles.getRC());
		
		Texte classement = new Texte("CLASSEMENT");
		classement.setPolice(Polices.COMICSTRICKS);
		classement.setSize(1.5);
		add(classement, 0, 0);
		classement.getStyleClass().add("p-5");
		Utiles.centrer(classement);
		
		HBox boutons = new HBox();
		add(boutons, 0, 1);
		boutons.getStyleClass().add("p-5");
		Texte argentBouton = getTexte("Argent");
		boutons.getChildren().add(argentBouton);
		argentBouton.setStyle("-fx-background-color:#00BBFF;");
		Texte proprietesBouton = getTexte("Propriétés");
		boutons.getChildren().add(proprietesBouton);
		Texte capitalBouton = getTexte("Capital");
		boutons.getChildren().add(capitalBouton);
		argentBouton.setOnMouseClicked(e->{
			argentBouton.setStyle("-fx-background-color:#00BBFF;");
			proprietesBouton.setStyle("");
			capitalBouton.setStyle("");
			MonopolyIUT.getPartie().setTrieArgent();
		});
		proprietesBouton.setOnMouseClicked(e->{
			proprietesBouton.setStyle("-fx-background-color:#00BBFF;");
			argentBouton.setStyle("");
			capitalBouton.setStyle("");
			MonopolyIUT.getPartie().setTriePropriete();
		});
		capitalBouton.setOnMouseClicked(e->{
			capitalBouton.setStyle("-fx-background-color:#00BBFF;");
			argentBouton.setStyle("");
			proprietesBouton.setStyle("");
			MonopolyIUT.getPartie().setTrieCapital();
		});
		
		ListView<JoueurClassementView> liste = new ListView<JoueurClassementView>(MonopolyIUT.getPartie().getListeJoueurs());
		liste.getItems().addListener(new ListChangeListener<Object>(){
			@Override
			public void onChanged(Change<?> c) {
				c.next();
				if(c.wasPermutated()) {
					for(int i=0;i<liste.getItems().size();i++) {
						if(i!=c.getPermutation(i)) {
							JoueurClassementView item = liste.getItems().get(c.getPermutation(i));
							Double hauteur = liste.getHeight()/liste.getItems().size();
							TranslateTransition tt = new TranslateTransition(Duration.millis(300),item);
							tt.setFromY(hauteur*i-hauteur*(c.getPermutation(i)));
							tt.setToY(0);
							tt.play();
						}
					}
				}
			}
		});	
		add(liste, 0, 2);
	}
	
	private Texte getTexte(String texte) {
		Texte label = new Texte(texte);
		label.getStyleClass().addAll("border","p-5");
		label.setOnMouseEntered(e->{
			if(!label.getStyle().matches(".*-fx-background-color:#00BBFF;")) {
				label.setCursor(Cursor.HAND);
				label.setStyle(label.getStyle()+";-fx-background-color:#00BBFF44");
			}
		});
		label.setOnMouseExited(e->{
			label.setCursor(Cursor.DEFAULT);
			label.setStyle(label.getStyle().replaceAll("-fx-background-color:#00BBFF44", ""));
		});
		HBox.setHgrow(label, Priority.ALWAYS);
		Utiles.centrer(label);
		return label;
	}
}
