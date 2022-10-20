package view;

import java.util.ArrayList;

import back.Case;
import back.Joueur;
import back.Propriete;
import back.Salle;
import back.Service;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

public class ProprieteView extends CaseView{
	protected HBox hboxPrix;
	protected GridPane gridModif;
	protected Label materiels;
	protected int modif;
	protected Rectangle carre;
	protected Button plus;
	protected Button moins;
	protected boolean possedee;

	public ProprieteView(Propriete uneCase) {
		super(uneCase);

		GridPane.setVgrow(nom, Priority.NEVER);

		hboxPrix = new HBox();
		GridPane.setVgrow(hboxPrix, Priority.ALWAYS);
		hboxPrix.setAlignment(Pos.CENTER);
		hboxPrix.setStyle("-fx-padding: 0 10 0 10");
		add(hboxPrix, 0, 1);

		prix = new Label(uneCase.getPrixAchat()+"€");
		hboxPrix.getChildren().add(prix);
		prix.setMaxWidth(Integer.MAX_VALUE);
		HBox.setHgrow(prix, Priority.ALWAYS);
		prix.setAlignment(Pos.CENTER);
		prix.fontProperty().bind(Bindings.createObjectBinding(()->{
			return new Font("Nunito",fontSize.multiply(0.9).doubleValue());
		},fontSize));
		possedee=false;
		carre = new Rectangle();
		materiels = new Label("-1");
		materiels.setMaxWidth(Integer.MAX_VALUE);
		HBox.setHgrow(materiels, Priority.ALWAYS);
		materiels.setPadding(new Insets(-2));
		carre.heightProperty().bind(prix.heightProperty());
		carre.widthProperty().bind(prix.heightProperty());
		carre.setStroke(Color.BLACK);
		carre.setStrokeWidth(2);
		carre.setStrokeType(StrokeType.INSIDE);
		materiels.setGraphic(carre);
		materiels.setContentDisplay(ContentDisplay.CENTER);
		materiels.setAlignment(Pos.CENTER);
		materiels.hoverProperty().addListener((val,oldVal,newVal)->{
			hoverToggle(newVal);
			materielsHoverEffect(newVal);
		});
		materiels.setOnMouseClicked(e->{
			e.consume();
			MonopolyIUT.getPartie().getView().afficherFiche(uneCase.getProprio().getFicheDetailJoueur());
		});
		materiels.fontProperty().bind(Bindings.createObjectBinding(()->{
			return new Font("Nunito",fontSize.multiply(0.9).doubleValue());
		},fontSize));

		gridModif = new GridPane();
		gridModif.getColumnConstraints().addAll(Utiles.getCC(50),Utiles.getCC(50));
		gridModif.getRowConstraints().add(Utiles.getRC(100));

		moins = new Button("−");
		gridModif.add(moins, 0, 0);
		moins.styleProperty().bind(Bindings.concat("-fx-background-radius:0;-fx-padding:-10;-fx-background-color:#FF0000AA;-fx-font-size: ", fontSize.multiply(1.2).asString()));
		Utiles.centrer(moins);
		HBox.setHgrow(moins, Priority.ALWAYS);
		moins.hoverProperty().addListener((val, oldVal, newVal)->{
			hoverToggle(newVal);
			setHoverEffect(moins,newVal);
		});

		plus = new Button("＋");
		gridModif.add(plus, 1, 0);
		plus.styleProperty().bind(Bindings.concat("-fx-background-radius:0;-fx-padding:-10;-fx-background-color:#00FF00AA;-fx-font-size: ", fontSize.multiply(1.2).asString()));
		Utiles.centrer(plus);
		HBox.setHgrow(plus, Priority.ALWAYS);
		plus.hoverProperty().addListener((val, oldVal, newVal)->{
			hoverToggle(newVal);
			setHoverEffect(plus,newVal);
		});
		bb.bind(hoverProperty().and(materiels.hoverProperty().or(gridModif.hoverProperty()).not()));
	}
	
	public Label getMateriels() {
		return materiels;
	}
	
	public boolean isPossedee() {
		return possedee;
	}
	
	public void setTransparent(boolean b) {
		moins.setMouseTransparent(b);
		plus.setMouseTransparent(b);
	}

	public void setPossedee(boolean b) {
		if(b) {
			prix.setText("x"+((Service) uneCase).recurence()*10);
			materielsHoverEffect(false);
			hboxPrix.getChildren().add(materiels);
			carre.setFill(Color.web(((Propriete)uneCase).getProprio().getPion().getCouleur()));
		}else {
			prix.setText(((Propriete) uneCase).getPrixAchat()+"€");
			hboxPrix.getChildren().remove(materiels);
		}
	}

	private void setHoverEffect(Button n, boolean b) {
		if(b) {
			n.toFront();
			n.setBorder(new Border(new BorderStroke(Color.BLACK, new BorderStrokeStyle(StrokeType.OUTSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,new ArrayList<Double>()),CornerRadii.EMPTY,new BorderWidths(2))));
			setCursor(Cursor.HAND);
		}else {
			n.setBorder(null);
		}
	}

	protected void materielsHoverEffect(boolean b) {
		if(b) {
			carre.setFill(Color.web(((Propriete)uneCase).getProprio().getPion().getCouleur()));
			carre.getStrokeDashArray().add(carre.getHeight()*0.50);
			carre.setStrokeDashOffset(carre.getHeight()*0.25);
			setCursor(Cursor.HAND);
		}else {
			carre.getStrokeDashArray().clear();
		}
	}

	public void setModif(Joueur joueur, int modif, boolean etat) {}

	public void reinitialisation() {}

	public double getValeur(boolean modif) {
		if(modif) {
			return 0;
		}else {
			if(((Propriete) uneCase).getProprio().getArgentBis()+ActionView.getArgentModif().get()<Math.abs(this.modif)) {
				return ((Propriete)uneCase).getPrixAchat()/2;
			}else {
				return Integer.MAX_VALUE;
			}
		}
	}
	
	public void update() {
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			if(c.getView() instanceof SalleView) {
				SalleView p = ((SalleView)c.getView());
				p.prix.setText(((Salle)p.uneCase).getLoyerSansSup(Integer.parseInt(p.materiels.getText()))+p.supplement()+"€");
			}
			if(c.getView() instanceof ServiceView) {
				ServiceView p = ((ServiceView)c.getView());
				p.prix.setText("x"+p.supplement()*10);
				p.materiels.setText(p.supplement()+"");
				if(!p.possedee)p.prix.setText(((Propriete)p.uneCase).getPrixAchat()+"€");
			}
			if(c.getView() instanceof CentreCommercialView) {
				CentreCommercialView p = ((CentreCommercialView)c.getView());
				p.prix.setText(50*p.supplement()+"€");
				p.materiels.setText(p.supplement()+"");
				if(!p.possedee)p.prix.setText(((Propriete)p.uneCase).getPrixAchat()+"€");
			}
		}
	}

	public void firePlus() {
		plus.fire();
	}

	public void fireMoins() {
		moins.fire();
	}

}


