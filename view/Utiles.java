package view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class Utiles {
	public static GridPane placer(Node node, double percentWidth, double percentHeight){
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth((100-percentWidth)/2);
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight((100-percentHeight)/2);
		RowConstraints rPercent = new RowConstraints();
		rPercent.setPercentHeight(percentHeight);
		ColumnConstraints cPercent = new ColumnConstraints();
		cPercent.setPercentWidth(percentWidth);
		GridPane grid = new GridPane();
		grid.getRowConstraints().addAll(rc,rPercent,rc);
		grid.getColumnConstraints().addAll(cc,cPercent,cc);
		grid.add(node, 1, 1);
		return grid;
	}
	
	public static void centrer(Label label) {
		label.setMaxHeight(Double.MAX_VALUE);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setAlignment(Pos.CENTER);
	}
	
	public static void centrer(Button button) {
		button.setMaxHeight(Double.MAX_VALUE);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setAlignment(Pos.CENTER);
	}
	
	public static void pointeur(Node objet) {
		objet.setOnMouseEntered(e -> objet.getScene().setCursor(Cursor.HAND));
		objet.setOnMouseExited(e -> objet.getScene().setCursor(Cursor.DEFAULT));
	}
	
	public static void setSize(Node n, double value) {
		n.styleProperty().bind(Bindings.concat("-fx-font-size:",MonopolyIUT.getFont().multiply(value)));
	}
	
	public static ColumnConstraints getCC() {
		ColumnConstraints cc = new ColumnConstraints();
		cc.setHgrow(Priority.ALWAYS);
		return cc;
	}
	
	public static ColumnConstraints getCC(double d) {
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(d);
		return cc;
	}
	
	public static RowConstraints getRC() {
		RowConstraints rc = new RowConstraints();
		rc.setVgrow(Priority.ALWAYS);
		return rc;
	}
	
	public static RowConstraints getRC(double d) {
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(d);
		return rc;
	}
	
}
