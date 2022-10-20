package view;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Bouton extends Button{
	private double taille;
	private CornerRadii radius;
	private Color couleur;
	private Color couleurBordure;
	private Color couleurTexte;
	private Color couleurHover;
	private Color couleurHoverBordure;
	private Color couleurHoverTexte;
	private ChangeListener<Boolean> change;
	
	public Bouton(String value) {
		this();
		this.setText(value);
	}
	
	public Bouton(double size) {
		this();
		this.setSize(size);
	}
	
	public Bouton(String value, double size) {
		this();
		this.setText(value);
		this.setSize(size);
	}
	
	public Bouton() {
		setSize(1);
		couleur=Color.WHITE;
		couleurBordure=Color.BLACK;
		couleurTexte=Color.BLACK;
		couleurHover=Color.web("#0097d8");
		couleurHoverBordure=Color.BLACK;
		couleurHoverTexte=Color.WHITE;
		color(false);
		radius=CornerRadii.EMPTY;
		Animation a1 = getAnimation(true);
		Animation a2 = getAnimation(false);
		setCursor(Cursor.HAND);
		change = new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					a2.stop();
					a1.play();
				}else {
					a1.stop();
					a2.play();
				}
			}
			
		};
		hoverProperty().addListener(change);
	}
	
	public void disable(boolean etat, boolean disabled) {
		if(etat) {
			hoverProperty().removeListener(change);
			color(disabled);
			if(disabled)setMouseTransparent(true);
		}else {
			hoverProperty().addListener(change);
			setMouseTransparent(false);
		}
	}
	
	public void setSize(double value) {
		taille=value;
		fontProperty().bind(Bindings.createObjectBinding(()->{
			return Font.font("Nunito", MonopolyIUT.getFont().multiply(taille).doubleValue());
		},MonopolyIUT.getFont()));
	}
	
	private Animation getAnimation(boolean b) {
		return new Transition() {
            {
                setCycleDuration(Duration.millis(200));
                setInterpolator(Interpolator.EASE_OUT);
            }
            @Override
            protected void interpolate(double frac) {
                setBackground(new Background(new BackgroundFill((b?couleur:couleurHover).interpolate((b?couleurHover:couleur), frac), radius, Insets.EMPTY)));
                setBorder(new Border(new BorderStroke((b?couleurBordure:couleurHoverBordure).interpolate((b?couleurHoverBordure:couleurBordure), frac), new BorderStrokeStyle(StrokeType.INSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,new ArrayList<Double>()),radius,new BorderWidths(2))));
                setTextFill((b?couleurTexte:couleurHoverTexte).interpolate((b?couleurHoverTexte:couleurTexte), frac));
            }
        };
	}
	
	private void color(boolean disabled) {
		setBackground(new Background(new BackgroundFill(disabled?Color.color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), 0.7):couleur, radius, Insets.EMPTY)));
		setBorder(new Border(new BorderStroke(disabled?Color.color(couleurBordure.getRed(), couleurBordure.getGreen(), couleurBordure.getBlue(), 0.7):couleurBordure, new BorderStrokeStyle(StrokeType.INSIDE,StrokeLineJoin.MITER,StrokeLineCap.BUTT,10,0,new ArrayList<Double>()),radius,new BorderWidths(2))));
		setTextFill(disabled?Color.color(couleurTexte.getRed(), couleurTexte.getGreen(), couleurTexte.getBlue(), 0.7):couleurTexte);
	}
	
	public void setTaille(double taille) {
		this.taille = taille;
	}
	
	public void setCornerRadius(CornerRadii cr) {
		radius=cr;
		color(false);
	}

	public void setCouleur(String couleur) {
		this.couleur = Color.web(couleur);
		color(false);
	}

	public void setCouleurBordure(String couleurBordure) {
		this.couleurBordure = Color.web(couleurBordure);
		color(false);
	}

	public void setCouleurTexte(String couleurTexte) {
		this.couleurTexte = Color.web(couleurTexte);
		color(false);
	}

	public void setCouleurHover(String couleurHover) {
		this.couleurHover = Color.web(couleurHover);
		color(false);
	}

	public void setCouleurHoverBordure(String couleurHoverBordure) {
		this.couleurHoverBordure = Color.web(couleurHoverBordure);
		color(false);
	}

	public void setCouleurHoverTexte(String couleurHoverTexte) {
		this.couleurHoverTexte = Color.web(couleurHoverTexte);
		color(false);
	}
}
