package view;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.GridPane;

public class De extends GridPane{
	public De(int valeur) {
		setStyle("-fx-background-radius:10%;-fx-border-radius:10%");
		getStyleClass().addAll("bg-white","border","bw-3");
		getColumnConstraints().addAll(Utiles.getCC(100.0/3),Utiles.getCC(100.0/3),Utiles.getCC(100.0/3));
		getRowConstraints().addAll(Utiles.getRC(100.0/3),Utiles.getRC(100.0/3),Utiles.getRC(100.0/3));
		if(valeur%2==1) {
			add(getPoint(),1,1);
			valeur--;
		}
		for(int i=0;i<valeur*2;i+=2)add(getPoint(),i%4,(i==4?8:i==8?4:i)%3);
	}
	
	private GridPane getPoint(){
		GridPane point = new GridPane();
		point.styleProperty().bind(Bindings.concat("-fx-background-color:black;-fx-background-radius:50%;-fx-background-insets:",heightProperty().divide(3).multiply(0.2)));
		return point;
	}
}
