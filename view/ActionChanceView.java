package view;

import java.time.Instant;
import java.util.Date;

import back.Chance;
import back.Joueur;
import javafx.animation.Animation.Status;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

public class ActionChanceView extends ActionView{
	private Bouton valider;
	private Bouton vendre;
	
	public ActionChanceView(Chance chance, Joueur j) {
		super(j);
		getColumnConstraints().addAll(Utiles.getCC(100/3.0),Utiles.getCC(100/3.0),Utiles.getCC(100/3.0));
		getRowConstraints().addAll(Utiles.getRC(), new RowConstraints());
		setPadding(new Insets(20));
		FicheChance carte = new FicheChance(chance.getCarteChance().getNom(),chance.getCarteChance().getTexte());
		carte.setMouseTransparent(true);
		add(Utiles.placer(carte, 80, 50),0,0,3,2);
		valider = new Bouton("Valider");
		valider.setDefaultButton(true);
		Utiles.centrer(valider);
		valider.setCouleurBordure("#00E500");
		valider.setCouleurHover("#00E500");
		
		vendre = new Bouton("Vendre");
		vendre.setDefaultButton(true);
		Utiles.centrer(vendre);
		vendre.setCouleurBordure("#E50000");
		vendre.setCouleurHover("#E50000");
		
		if(chance.getCarteChance().getPlusMoinsArgent()<0&&j.getArgentBis()-chance.getCarteChance().getPlusMoinsArgent()<0&&!j.getMatieres().isEmpty()) {
			add(vendre,2,1);
			vendre.setOnAction(e->{
				vendre.setOnAction(null);
				if(j.isBot()) {
					ActionView.vendre(j, chance.getCarteChance().getPlusMoinsArgent(), null, chance);
				}else {
					ActionView.setActionVendre(j, chance.getCarteChance().getPlusMoinsArgent(), null, chance);
				}
			});
			valider.setOnAction(null);
		}else {
			add(valider,2,1);
			valider.setOnAction(e->{
				valider.setOnAction(null);
				valider.setOpacity(0);
				valider.setMouseTransparent(true);
				chance.executeLaBonneCarte(j);
			});
			vendre.setOnAction(null);
		}
		
		if(MonopolyIUT.getPartie().getTempsTour()>=0) {
			if((MonopolyIUT.getPartie().getDebutTour()-Date.from(Instant.now()).getTime())/1000<0)desactiverBoutons();;
			add(chrono, 2, 0);
			timeline.statusProperty().addListener((val, oldVal, newVal)->{
				if(newVal==Status.STOPPED&&!j.isBot()) {
					PauseTransition pause = new PauseTransition(Duration.millis(1500));
					pause.setOnFinished(e2->valider.fire());
					pause.play();	
				}
			});
			timeline.play();
		}
	}
	
	protected void desactiverBoutons() {
		valider.disable(true, true);
		vendre.disable(true, true);
	}
	
	public void fireValider() {
		valider.fire();
	}
}
