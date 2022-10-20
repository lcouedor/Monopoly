package view;

import back.Case;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class CaseView extends GridPane{
	protected Case uneCase;
	protected Label nom;
	protected Label prix;
	protected HBox pions;
	protected GridPane grid;
	protected GridPane sombre;
	protected BooleanProperty bb;
	protected DoubleProperty fontSize = new SimpleDoubleProperty();

	public CaseView(Case uneCase) {
		getStyleClass().addAll("bg-white","border");
		this.uneCase=uneCase;
		
		// Nom
		nom = new Label(uneCase.getNom().toUpperCase());
		add(nom, 0, 0);
		Utiles.centrer(nom);
		GridPane.setHgrow(nom, Priority.ALWAYS);
		GridPane.setVgrow(nom, Priority.ALWAYS);
		fontSize.bind(heightProperty().divide(5));
		nom.fontProperty().bind(Bindings.createObjectBinding(()->{
			nom.setPadding(new Insets(10, 0, 10, 0));
			return new Font("Comics Tricks",fontSize.multiply(0.8).doubleValue());
		},fontSize));
		nom.setBackground(new Background(new BackgroundFill(Color.web(uneCase.getCouleur()), CornerRadii.EMPTY, Insets.EMPTY)));
		
		// Pions
		pions = new HBox();
		add(pions, 0, 0);
		pions.setAlignment(Pos.CENTER);
		GridPane.setValignment(pions, VPos.BOTTOM);
		pions.getChildren().addListener(new ListChangeListener<Object>(){
			@Override
			public void onChanged(Change<?> c) {
				if(!pions.getChildren().isEmpty()) {
					GridPane.setRowIndex(pions, getChildren().size()-1);
				}else {
					GridPane.setRowIndex(pions, 0);
				}
				NumberBinding nb = Bindings.min(heightProperty().subtract(40).multiply(0.125), widthProperty().subtract(40).divide(2).divide(pions.getChildren().size()));
				for(Node circle : pions.getChildren())((Circle)circle).radiusProperty().bind(nb);
			}
		});
		
		sombre = new GridPane();
		sombre.setStyle("-fx-background-color:#00000077");	
		
		// Effet hover
		grid = new GridPane();
		add(grid, 0, 0, 10, 10);
		setOnMouseClicked(e->{
			if(bb.get())MonopolyIUT.getPartie().getView().afficherFiche(uneCase.getFiche());
		});
		bb=new SimpleBooleanProperty();
		bb.bind(hoverProperty());
		bb.addListener((val, oldVal, newVal)->hoverEffect(newVal));
		pions.toFront();
	}

	public void ajouterPion(Pion pion) {
		pions.getChildren().add(pion);
	}
	
	public void enleverPion(Pion pion) {
		pions.getChildren().remove(pion);
	}
	
	protected void hoverEffect(boolean b) {
		setCursor(b?Cursor.HAND:Cursor.DEFAULT);
		grid.setStyle(b?
				"-fx-background-color: black, black, black, black, black, black, black, black;"
				+ "-fx-background-insets: "
				+ "0 "+(grid.getWidth()-20)+" "+(grid.getHeight()-2)+" 0, "
				+ "0 0 "+(grid.getHeight()-2)+" "+(grid.getWidth()-20)+", "
				+ "0 0 "+(grid.getHeight()-20)+" "+(grid.getWidth()-2)+", "
				+(grid.getHeight()-20)+" 0 0 "+(grid.getWidth()-2)+", "
				+(grid.getHeight()-2)+" 0 0 "+(grid.getWidth()-20)+", "
				+(grid.getHeight()-2)+" "+(grid.getWidth()-20)+" 0 0, "
				+(grid.getHeight()-20)+" "+(grid.getWidth()-2)+" 0 0, "
				+ "0 "+(grid.getWidth()-2)+" "+(grid.getHeight()-20)+" 0"
				:"");
	}
	
	public void setModif(boolean b) {	
		if(b) {
			if(!getChildren().contains(sombre))add(sombre, 0, 0, 10, 10);
			setMouseTransparent(true);
			GridPane.setRowSpan(grid, 3);
		}else {
			getChildren().remove(sombre);
			setMouseTransparent(false);
			GridPane.setRowSpan(grid, 10);
		}
	}
	
	public void hoverToggle(boolean b) {
		bb.bind(b?new SimpleBooleanProperty(false):hoverProperty());
	}
	
	public Case getCase() {
		return uneCase;
	}
}
