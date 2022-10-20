package back;

import view.AngleView;
import view.FicheAngle;
import view.MonopolyIUT;

/**
 * Classe représentant la case Interactif.
 * Cette classe étend la Classe Case.
 * 
 * @see Case
 *
 */
public class Interactif extends Case {

	/**
	 * Constructeur de la case Interactif.
	 * 
	 * @see Case
	 * 
	 * @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 * L'identifiant de la case en int.
	 * @param couleur
	 * 	La couleur de la case en String.
	 */

	public Interactif(String nom, int id, String couleur) {
		super(nom, id, couleur);
		fiche=new FicheAngle("Parking","Un parking. Mais pas tout le temps.");
		view=new AngleView(this);
	}
	
	/**
	 * 
	 * @return La vue de la case Interactive.
	 */
	public AngleView getView() {
		return (AngleView)view;
	}

	
	/*
	 * Méthode pour faire l'action de la case. Celle-ci fait directement appelle à la méthode de la partie puisqu'elle 
	 * n'a pas d'action à faire.
	 */
	@Override
	public void actionCase(Joueur j) {
		MonopolyIUT.getPartie().jouerEtape();
	}

}
