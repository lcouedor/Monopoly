package view;

import java.util.ArrayList;

import back.CarteChance;
import back.Joueur;
import back.Propriete;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import view.Texte.Polices;

public class FicheJoueur extends Fiche{
	private ListView<Texte> proprietes;
	private ListView<Texte> cartesChance;
	
	public FicheJoueur(Joueur joueur) {

		// Pseudo
		Texte pseudo = new Texte(joueur.getPseudo().toUpperCase());
		pseudo.setPolice(Polices.COMICSTRICKS);
		pseudo.setSize(1.3);
		pseudo.setStyle("-fx-background-color:"+joueur.getPion().getCouleur());
		Utiles.centrer(pseudo);

		// Argent
		Texte texteArgent = new Texte("Argent sur le compte");
		Utiles.centrer(texteArgent);
		Texte argent = new Texte();
		argent.getStyleClass().setAll("bold");
		Utiles.centrer(argent);
		GridPane argentPlaceholder = Utiles.placer(argent,40,100);
		Texte argentChangement = new Texte("+0€");
		argentChangement.setOpacity(0);
		argentPlaceholder.add(argentChangement, 2, 1);
		argent.textProperty().bind(joueur.getArgent().asString().concat("€"));
		SequentialTransition st = new SequentialTransition();
		FadeTransition fade = new FadeTransition(Duration.millis(500),argentChangement);
		fade.setDelay(Duration.millis(300));
		fade.setToValue(0);
		argent.textProperty().addListener((val,oldVal,newVal)->{
			int difference=Integer.parseInt(newVal.substring(0, newVal.length()-1))-Integer.parseInt(oldVal.substring(0, oldVal.length()-1));
			if(Integer.parseInt(argentChangement.getText().substring(0, argentChangement.getText().length()-1))*difference<0)argentChangement.setText((difference>=0?"+":"")+difference+"€");
			
			// Animation
			TranslateTransition t1 = new TranslateTransition(Duration.millis(200));
			t1.setNode(argentChangement);
			t1.setDelay(Duration.millis(25));
			t1.setFromY(0);
			t1.setToY(-12.5);
			TranslateTransition t2 = new TranslateTransition(Duration.millis(50));
			t2.setNode(argentChangement);
			t2.setDelay(Duration.millis(25));
			t2.setFromY(-12.5);
			t2.setToY(0);
			st.getChildren().setAll(t1,t2);
			st.play();
			st.setOnFinished(e->st.play());
			
			// Compteur argent
			Texte argentBis = new Texte(oldVal);
			Utiles.centrer(argentBis);
			argentBis.getStyleClass().setAll("bold","bg-white");
			argentPlaceholder.add(argentBis, 1, 1);
			
			argentChangement.setOpacity(1);
			
			if(difference>=0) {
				argentChangement.getStyleClass().setAll("green");
				argentChangement.setText("+"+(difference+(st.getStatus().equals(Status.RUNNING)?Integer.parseInt(argentChangement.getText().substring(0, argentChangement.getText().length()-1)):0))+"€");
			}else {
				argentChangement.getStyleClass().setAll("red");
				argentChangement.setText(Integer.toString(difference+(st.getStatus().equals(Status.RUNNING)?Integer.parseInt(argentChangement.getText().substring(0, argentChangement.getText().length()-1)):0))+"€");
			}
			new Thread(){
				@Override 
				public void run() {
					int time = Math.abs(difference)<500?Math.abs(difference):500;
					for (int i = 1; i <= time; i++) {
						int counter = (int) (difference/(double)time*i);
						Platform.runLater(new Runnable() {
							@Override public void run() {
								argentBis.setText((Integer.parseInt(oldVal.substring(0, oldVal.length()-1))+counter)+"€");
							}
						});
						try {
							sleep(1);
						} catch (InterruptedException e) {}
					}
					Platform.runLater(new Runnable() {
						public void run() {
							argentPlaceholder.getChildren().remove(argentBis);
							st.setOnFinished(null);
							fade.stop();
							fade.play();
							fade.setOnFinished(e->argentChangement.setText("0€"));
						}
					});
				}
			}.start();

		});
		// Propriétés
		Texte proprietesTexte = new Texte("Propriétés possédées");
		proprietesTexte.setStyle("-fx-padding:0 0 -20 0");
		Utiles.centrer(proprietesTexte);
		proprietes = new ListView<Texte>();
		proprietes.setStyle("-fx-border-width:2;-fx-border-color:black");
		proprietes.getItems().addListener(new ListChangeListener<Object>() {
			@Override
			public void onChanged(Change<? extends Object> c) {
				proprietes.setStyle("-fx-border-width:"+(proprietes.getItems().isEmpty()?"2":"0"));
			}
		});
		proprietes.setPadding(new Insets(10));
		Texte placeholderProprietes = new Texte("Achetez des proprietes pour les voir apparaître ici".toUpperCase());
		placeholderProprietes.setPolice(Polices.COMICSTRICKS);
		placeholderProprietes.setSize(1);
		placeholderProprietes.setWrapText(true);
		placeholderProprietes.setAlignment(Pos.CENTER);
		placeholderProprietes.setTextAlignment(TextAlignment.CENTER);
		proprietes.setPlaceholder(placeholderProprietes);
		joueur.getMatieres().addListener(new ListChangeListener<Object>() {
			@Override
			public void onChanged(Change<?> c) {
				proprietes.getItems().clear();
				for(Propriete p : joueur.getMatieres()) {
					Texte propriete = new Texte(p.getNom(),p);

					propriete.hoverProperty().addListener((val,oldVal,newVal)->{
						if(newVal) {
							setCursor(Cursor.HAND);
							propriete.setBorder(new Border(new BorderStroke(Color.BLACK, new BorderStrokeStyle(StrokeType.OUTSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,new ArrayList<Double>()),CornerRadii.EMPTY,new BorderWidths(2))));
						}else {
							setCursor(Cursor.DEFAULT);
							propriete.setBorder(null);
						}
					});
					propriete.setOnMouseClicked(e->{
						e.consume();
						MonopolyIUT.getPartie().getView().afficherFiche(p.getFiche());
					});
					Utiles.centrer(propriete);
					propriete.setStyle("-fx-text-fill:black;-fx-background-color:"+p.getCouleur());
					proprietes.getItems().add(propriete);
				}
			}
		});


		// Cartes chances
		Texte cartesChanceTexte = new Texte("Cartes Chance");
		cartesChanceTexte.setStyle("-fx-padding:0 0 -20 0");
		Utiles.centrer(cartesChanceTexte);
		cartesChance = new ListView<Texte>();
		cartesChance.setStyle("-fx-border-width:2;-fx-border-color:black");
		cartesChance.getItems().addListener(new ListChangeListener<Object>() {
			@Override
			public void onChanged(Change<? extends Object> c) {
				cartesChance.setStyle("-fx-border-width:"+(cartesChance.getItems().isEmpty()?"2":"0"));
			}
		});
		cartesChance.setPadding(new Insets(10));
		Texte placeholderChance = new Texte("Obtenez des Cartes Chance concervables pour les voir apparaître ici".toUpperCase());
		placeholderChance.setPolice(Polices.COMICSTRICKS);
		placeholderChance.setSize(1);
		placeholderChance.setWrapText(true);
		placeholderChance.setAlignment(Pos.CENTER);
		placeholderChance.setTextAlignment(TextAlignment.CENTER);
		cartesChance.setPlaceholder(placeholderChance);
		joueur.getChances().addListener(new ListChangeListener<Object>() {
			@Override
			public void onChanged(Change<?> c) {
				cartesChance.getItems().clear();
				for(CarteChance cc : joueur.getChances()) {
					Texte chance = new Texte(cc.getNom());
					Utiles.centrer(chance);
					chance.setOnMouseClicked(e->{
						e.consume();
						MonopolyIUT.getPartie().getView().afficherFiche(new FicheChance(cc.getNom(),cc.getTexte()));
					});
					chance.setBorder(new Border(new BorderStroke(new Color(150.0/256.0, 115.0/256.0, 166.0/256.0, 1), new BorderStrokeStyle(StrokeType.OUTSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,new ArrayList<Double>()),CornerRadii.EMPTY,new BorderWidths(2))));
					Animation animation1 = getAnimation(chance, true);
					Animation animation2 = getAnimation(chance, false);
					chance.hoverProperty().addListener((val,oldVal,newVal)->{
						if(newVal) {
							setCursor(Cursor.HAND);
							animation2.stop();
							animation1.play();
							
						}else {
							setCursor(Cursor.DEFAULT);
							animation1.stop();
							animation2.play();
						}
					});
					cartesChance.getItems().add(chance);
				}
			}
		});

		// Bouton Quitter
		Bouton quitter = new Bouton("Quitter la partie");
		quitter.setCouleurHover("#E50000");
		quitter.setCouleur("#FF000088");
		quitter.setCouleurHoverTexte("#FFFFFF");
		quitter.setCouleurTexte("#000000");
		quitter.setCouleurHoverBordure("#000000");
		quitter.setCouleurBordure("#FF0000");
		GridPane.setHalignment(quitter, HPos.CENTER);
		quitter.getStyleClass().setAll("p-10");
		Utiles.pointeur(quitter);
		quitter.setOnAction(e->{
			joueur.quitterPartie();
		});

		getStyleClass().addAll("bg-white","pb-10","border","bw-3","liste-transparente");
		setStyle("-fx-border-color:"+joueur.getPion().getCouleur());
		add(pseudo, 0, 0, 3, 1);
		add(texteArgent, 1, 1);
		add(argentPlaceholder, 1, 2);
		add(proprietesTexte, 1, 3);
		add(proprietes, 1, 4);
		add(cartesChanceTexte, 1, 5);
		add(cartesChance, 1, 6);
		add(quitter, 1, 7);
		setVgap(20);
		getColumnConstraints().setAll(Utiles.getCC(),Utiles.getCC(80),Utiles.getCC());
		getRowConstraints().add(Utiles.getRC(15));
		setVisible(false);
		GridPane.setHgrow(this, Priority.ALWAYS);
		GridPane.setVgrow(this, Priority.ALWAYS);
		setOnMouseClicked(e->{
			MonopolyIUT.getPartie().getView().afficherFiche(joueur.getFicheDetailJoueur());
		});
		proprietes.setOnMouseClicked(e->{
			MonopolyIUT.getPartie().getView().afficherFiche(joueur.getFicheDetailJoueur());
		});
		cartesChance.setOnMouseClicked(e->{
			MonopolyIUT.getPartie().getView().afficherFiche(joueur.getFicheDetailJoueur());
		});
	}
	
	private Animation getAnimation(Texte n, boolean b) {
		return new Transition() {
            {
                setCycleDuration(Duration.millis(300));
                setInterpolator(Interpolator.EASE_OUT);
            }
            @Override
            protected void interpolate(double frac) {
            	Color color1 = new Color(150.0/256.0, 115.0/256.0, 166.0/256.0, b?frac:1-frac);
                Color color2 = new Color(b?frac:1-frac, b?frac:1-frac, b?frac:1-frac, 1);
                Color color3 = new Color(150.0/256.0-((150.0/256.0)*(b?frac:1-frac)), 115.0/256.0-((115.0/256.0)*(b?frac:1-frac)), 166.0/256.0-((166.0/256.0)*(b?frac:1-frac)), 1);
                n.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));
                n.setTextFill(color2);
                n.setBorder(new Border(new BorderStroke(color3, new BorderStrokeStyle(StrokeType.OUTSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,new ArrayList<Double>()),CornerRadii.EMPTY,new BorderWidths(2))));
            }
        };
	}
}
