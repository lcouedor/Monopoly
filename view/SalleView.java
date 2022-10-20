package view;

import back.Case;
import back.Joueur;
import back.Propriete;
import back.Salle;
import javafx.scene.paint.Color;

public class SalleView extends ProprieteView{

	public SalleView(Salle uneCase) {
		super(uneCase);

		moins.setOnAction(e->{
			e.consume();
			if(Integer.parseInt(materiels.getText())>(modif<=0?uneCase.getNbMateriels():-1)) {
				if(modif<=0||(modif>0&&uneCase.getProprio().getArgentBis()+ActionView.getArgentModif().get()<Math.abs(modif))) {
					if(modif<=0) {
						ActionView.modifArgentModif(-uneCase.getArgentVente(Integer.parseInt(materiels.getText()))*2);
					}else {
						ActionView.modifArgentModif(uneCase.getArgentVente(Integer.parseInt(materiels.getText())));
					}
					int nb = Integer.parseInt(materiels.getText())-1;
					if(nb<0)setPossedee(false);
					prix.setText(uneCase.getLoyer(nb)+"€");
					materiels.setText(nb+"");
					ActionView.modifArgentModif(1);
					ActionView.modifArgentModif(-1);
				}
				update();
			}
		});

		plus.setOnAction(e->{
			e.consume();
			if(Integer.parseInt(materiels.getText())<(modif<=0?3:uneCase.getNbMateriels())) {
				if(modif>0||(modif<=0&&ActionView.getArgentModif().get()+uneCase.getArgentAmelioration(Integer.parseInt(materiels.getText()))<=uneCase.getProprio().getArgentBis())) {
					if(modif<=0) {
						ActionView.modifArgentModif(uneCase.getArgentAmelioration(Integer.parseInt(materiels.getText())));
					}else {
						ActionView.modifArgentModif(-uneCase.getArgentVente(Integer.parseInt(materiels.getText())+1));
					}
					int nb = Integer.parseInt(materiels.getText())+1;
					if(nb==0)setPossedee(true);
					prix.setText(uneCase.getLoyer(nb)+"€");
					materiels.setText(nb+"");
					ActionView.modifArgentModif(1);
					ActionView.modifArgentModif(-1);
				}
				update();
			}
		});
	}

	public int supplement() {
		int sup=0;
		for(Case p : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			if(p.getView() instanceof SalleView && ((Propriete)p).estPossedee() && !((SalleView)p.getView()).materiels.getText().equals("-1") && !materiels.getText().equals("-1") && p.getCouleur().equals(uneCase.getCouleur()) && ((Propriete)p).getProprio().equals(((Propriete)uneCase).getProprio()) && p.getId()!=uneCase.getId()) {
				sup+=100;
			}
		}
		return sup;
	}

	public void setPossedee(boolean b) {
		if(b) {
			prix.setText(((Salle) uneCase).getLoyer(((Salle) uneCase).getNbMateriels())+"€");
			materiels.setText(((Salle) uneCase).getNbMateriels()+"");
			materielsHoverEffect(false);
			hboxPrix.getChildren().add(materiels);
			carre.setFill(Color.web(((Salle)uneCase).getProprio().getPion().getCouleur()));
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
		if(etat) {
			if(((Propriete)uneCase).estPossedee() && ((Propriete)uneCase).getProprio().equals(joueur)) {
				getChildren().remove(sombre);
				setMouseTransparent(false);
				if(!getChildren().contains(gridModif))add(gridModif, 0, 3);
			}
		}else {
			getChildren().remove(gridModif);
			if(((Propriete)uneCase).estPossedee()&&((Salle)uneCase).getNbMateriels()!=Integer.parseInt(materiels.getText()))
				while(modif<=0?((Salle)uneCase).getNbMateriels()<Integer.parseInt(materiels.getText()):((Salle)uneCase).getNbMateriels()>Integer.parseInt(materiels.getText())) {
					if(modif<=0) {
						((Salle)uneCase).ajouterMateriel();
					}else {
						((Salle)uneCase).retirerMateriel();
					}
				}
		}
	}

	public void reinitialisation() {
		if(((Propriete)uneCase).estPossedee()) {
			if(!hboxPrix.getChildren().contains(materiels))hboxPrix.getChildren().add(materiels);
			materiels.setText(((Salle)uneCase).getNbMateriels()+"");
		}
		prix.setText(((Salle)uneCase).getLoyer(((Salle)uneCase).getNbMateriels())+"€");
	}

	public double getValeur(boolean modif) {
		if(modif) {
			if(Integer.parseInt(materiels.getText())<3&&ActionView.getArgentModif().get()+((Salle) uneCase).getArgentAmelioration(Integer.parseInt(materiels.getText()))<=((Salle) uneCase).getProprio().getArgentBis()) {
				return (((Salle)uneCase).getLoyer(Integer.parseInt(materiels.getText())+1)-((Salle)uneCase).getLoyer(Integer.parseInt(materiels.getText()))*1.0)/((Salle)uneCase).getArgentAmelioration(Integer.parseInt(materiels.getText()));
			}else {
				return 0;
			}
		}else {
			if(Integer.parseInt(materiels.getText())>-1&&((Salle) uneCase).getProprio().getArgentBis()+ActionView.getArgentModif().get()<Math.abs(this.modif)) {
				return ((Salle)uneCase).getArgentVente(Integer.parseInt(materiels.getText()));
			}else {
				return Integer.MAX_VALUE;
			}
		}
	}
}


