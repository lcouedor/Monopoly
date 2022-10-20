package view;

import java.io.IOException;

import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class ReglesView extends GridPane{
	public ReglesView() throws IOException{
		
		
		//s�paration en 3 colonnes de 10% 80% 10%
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(7);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(86);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPercentWidth(7);
		this.getColumnConstraints().addAll(col1,col2,col3);
		
		//s�paration en lignes de 10% et 70%
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(10);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(70);
	    this.getRowConstraints().addAll(row1, row1, row2, row1);
	    
	    Pane head = new Pane();
	    head.setStyle("-fx-background-color: #0097d8;");
	    Pane body = new Pane();
	    body.setStyle("-fx-background-color: #95cde5;");
	    ScrollPane scroll = new ScrollPane();
	    scroll.setStyle("-fx-background-color: black; -fx-background: white;");
	    VBox scrollContent = new VBox();
	      
	    scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	    
	    ImageView imageView = new ImageView("retour.png");
	  	//imageView.setFitWidth(50);
	  	imageView.fitHeightProperty().bind(this.heightProperty().divide(20));
	  	imageView.setPreserveRatio(true);
	  	
	  	Pane img = new Pane();
	  	GridPane.setValignment(img, VPos.TOP);
	  	img.setTranslateX(30);
	  	img.setTranslateY(30);
	  	imageView.setTranslateX(35);
	  	imageView.setTranslateY(35);
	  	img.setCursor(Cursor.HAND);
	  	
	  	img.getChildren().add(imageView);
	  	img.maxHeightProperty().bind(imageView.fitHeightProperty().add(10));
	  	img.maxWidthProperty().bind(imageView.fitHeightProperty().add(10));
	  	
	  	img.setOnMouseClicked(e->{
	  		MonopolyIUT.setScene(new AccueilView());
	  	});
	  		
	  	scroll.setContent(scrollContent);
	    scrollContent.getChildren().add(imageView);
	  		  			  	
	    this.add(head, 0, 0, 3, 2);
	    
	    this.add(body, 0, 2, 3, 2);
	    this.add(scroll, 1, 1, 1, 2);
	    this.add(img, 1, 1, 3, 2);
	    
		
	}
	

}
