package view;

import back.Partie;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MonopolyIUT extends Application{
	private static DoubleProperty font = new SimpleDoubleProperty();
	public static Partie partie;
	private static GridPane grid = new GridPane();
	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		MonopolyIUT.stage=stage;
		grid.getColumnConstraints().add(Utiles.getCC(100));
		grid.getRowConstraints().add(Utiles.getRC(100));
		Scene scene = new Scene(grid);
		grid.getStylesheets().add("style.css");
		stage.setScene(scene);
		// Fullscreen
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.F11) {
					stage.setFullScreen(!stage.isFullScreen());
				}
			}
		});
		setScene(new AccueilView());
		
		
		
		stage.show();
		NumberBinding nbFont = Bindings.min(stage.heightProperty().divide(35), stage.widthProperty().divide(70));
		font.bind(nbFont);
		stage.setTitle("MonopolIUT");
		stage.setMaximized(true);
		stage.setFullScreen(true);
	}
	
	public static void setScene(Node n) {
		grid.getChildren().clear();
		grid.add(n, 0, 0);
	}
	
	public static DoubleProperty getFont() {
		return font;
	}
	
	public static void setPartie(Partie p) {
		partie=p;
	}
	
	public static Partie getPartie() {
		return partie;
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void main(String[] args) {
		launch();
	}

}
