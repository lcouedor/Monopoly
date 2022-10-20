package view;

import back.Case;
import back.Plateau;
import back.Propriete;
import javafx.scene.layout.GridPane;

public class PlateauView extends GridPane{
	private GridPane action;

	public PlateauView(Plateau plateau){
		int t=plateau.getTaille()/4;
		for(int i=0;i<=t;i++) {
			getColumnConstraints().add(Utiles.getCC(100.0/t));
			getRowConstraints().add(Utiles.getRC(100.0/t));
		}
		setStyle("-fx-background-color:#95cde5");
		int x,y;
		for(int i=0;i<plateau.getTaille();i++){
			x=t-i;
			if(x<-t)x=-x-t;
			if(x>t)x=t;
			if(x<0)x=0;
			y=i-t;
			if(y>t)y=-y+3*t;
			if(y>t)y=t;
			if(y<0)y=0;
			add(plateau.getListePlateau().get(i).getView(),y,x);
		}

		action=new GridPane();
		action.getColumnConstraints().add(Utiles.getCC());
		action.getRowConstraints().add(Utiles.getRC());
		add(action, 1, 1, t-1, t-1);
	}
	
	public void afficheAction(ActionView a) {
		action.getChildren().clear();
		action.add(a, 0, 0);
	}
	
	public void reinitialisation() {
		ActionView.getArgentModif().set(0);
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			if(c.getView() instanceof ProprieteView) {
				((ProprieteView)c.getView()).reinitialisation();
			}
		}
	}
	
	public void setTransparent(boolean b) {
		action.setMouseTransparent(b);
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			if(c instanceof Propriete)((ProprieteView)((Propriete)c).getView()).setTransparent(b);
		}
	}
	
}
