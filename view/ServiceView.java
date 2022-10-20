package view;

import back.Case;
import back.Joueur;
import back.Propriete;
import back.Service;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class ServiceView extends ProprieteView{

	public ServiceView(Service uneCase) {
		super(uneCase);

		possedee=false;
		
		GridPane.setVgrow(nom, Priority.ALWAYS);
		nom.getStyleClass().add("outline");
		nom.setStyle("-fx-background-color:transparent;-fx-text-fill:"+uneCase.getCouleur());
		
		carre.setRotate(45);
		carre.heightProperty().bind(materiels.heightProperty().divide(Math.sqrt(1.3)));
		carre.widthProperty().bind(materiels.heightProperty().divide(Math.sqrt(1.3)));
		
		moins.setOnAction(e->{
			e.consume();
			if(possedee&&uneCase.getProprio().getArgentBis()+ActionView.getArgentModif().get()<Math.abs(modif)) {
				ActionView.modifArgentModif(uneCase.getPrixAchat()/2);
				setPossedee(false);
				ActionView.modifArgentModif(1);
				ActionView.modifArgentModif(-1);
				update();
			}
		});

		plus.setOnAction(e->{
			e.consume();
			if(!possedee) {
				ActionView.modifArgentModif(-uneCase.getPrixAchat()/2);
				setPossedee(true);
				ActionView.modifArgentModif(1);
				ActionView.modifArgentModif(-1);
				update();
			}
		});
	}

	public int supplement() {
		int sup=0;
		for(Case p : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			if(p.getView() instanceof ServiceView && ((Propriete)p).estPossedee() && ((ServiceView)p.getView()).possedee && ((Propriete)p).getProprio().equals(((Propriete)uneCase).getProprio())) {
				sup+=1;
			}
		}
		return sup;
	}
	
	public void setPossedee(boolean b) {
		if(b) {
			prix.setText("x"+((Service) uneCase).recurence()*10);
			materiels.setText(((Service) uneCase).recurence()+"");
			materielsHoverEffect(false);
			if(!hboxPrix.getChildren().contains(materiels))hboxPrix.getChildren().add(materiels);
			carre.setFill(Color.web(((Propriete)uneCase).getProprio().getPion().getCouleur()));
			possedee=true;
		}else {
			prix.setText(((Propriete) uneCase).getPrixAchat()+"€");
			hboxPrix.getChildren().remove(materiels);
			possedee=false;
		}
	}

	public void setModif(Joueur joueur, int modif, boolean etat) {
		this.modif=modif;
		super.setModif(etat);
		if(modif>=0) {
			if(etat) {
				if(((Propriete)uneCase).estPossedee() && ((Propriete)uneCase).getProprio().equals(joueur)) {
					getChildren().remove(sombre);
					setMouseTransparent(false);
					if(!getChildren().contains(gridModif))add(gridModif, 0, 3);
				}
			}else {
				getChildren().remove(gridModif);
				if(((Propriete)uneCase).estPossedee()&&!possedee) {
					((Service)uneCase).vendre();
				}
			}
		}
	}

	public void reinitialisation() {
		if(((Propriete)uneCase).estPossedee()&&!hboxPrix.getChildren().contains(materiels))hboxPrix.getChildren().add(materiels);
		setPossedee(((Propriete)uneCase).estPossedee());
		materiels.setText(((Service) uneCase).recurence()+"");
	}

	public double getValeur(boolean modif) {
		if(modif) {
			return 0;
		}else {
			if(possedee&&((Propriete) uneCase).getProprio().getArgentBis()+ActionView.getArgentModif().get()<Math.abs(this.modif)) {
				return ((Propriete)uneCase).getPrixAchat()/2;
			}else {
				return Integer.MAX_VALUE;
			}
		}
	}
}


