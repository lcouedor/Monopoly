package view;

import back.Propriete;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Texte extends Label{
	public enum Polices{
		COMICSTRICKS,
		NUNITO,
		NUNITOBOLD
	}
	private double size;
	private Polices police = Polices.NUNITOBOLD;
	private Propriete propriete;
	
	public Texte(String value, Propriete p) {
		this(value);
		propriete=p;
	}
	
	public Texte(String value) {
		setSize(1);
		setText(value);
	}
	
	public Texte(double size) {
		setSize(size);
	}
	
	public Texte(String value, double size) {
		setText(value);
		setSize(size);
	}
	
	public Texte(String value, boolean bold) {
		setText(value);
		setBold();
		setSize(1);
	}
	
	public Texte(String value, double size, boolean bold) {
		setText(value);
		setBold();
		setSize(size);
	}
	
	public Texte() {
		setSize(1);
	}
	
	public void setBold() {
		police=Polices.NUNITOBOLD;
	}
	
	public void setPolice(Polices p) {
		police=p;
	}
	
	public void setSize(double value) {
		size=value;
		fontProperty().bind(Bindings.createObjectBinding(()->{
			setPadding(new Insets(0, 0, 0, 0));
			return Font.font(getPolice(), MonopolyIUT.getFont().multiply(size).doubleValue());
		},MonopolyIUT.getFont()));
	}
	
	private String getPolice() {
		switch(police) {
		case NUNITO:
			return "Nunito Light";
		case NUNITOBOLD:
			return "Nunito";
		case COMICSTRICKS:
			return "Comics Tricks";
		}
		return "Nunito Light";
	}
	
	public Propriete getPropriete() {
		return propriete;
	}
}
