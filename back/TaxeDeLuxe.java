package back;

import view.AngleView;
import view.FicheAngle;
import view.MonopolyIUT;

/**
 * Classe représentant la case taxe de luxe.
 * Cette classe étend la Classe Case.
 * 
 * @see Case
 *
 */
public class TaxeDeLuxe extends Case {

	/**
	 * Constructeur de la case taxe de luxe.
	 * 
	 * @see Case
	 * 
	 * @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 * 	L'identifiant de la case en int.
	 * @param couleur
	 * 	La couleur de la case TaxeDeLuxe en String.
	 */

	public TaxeDeLuxe(String nom, int id, String couleur) {
		super(nom, id, couleur);
		fiche=new FicheAngle(nom, "Votre compte en suisse a été découvert ! Le FISC vous déleste d'1/8 de votre capital !");
		view=new AngleView(this);
	}
	
	/**
	 * 
	 * @return La vue de la Taxe de Luxe.
	 */
	public AngleView getView() {
		return (AngleView)view;
	}
	
	
	/**
	 * Fait payer au joueur la taxe.
	 * 
	 * 
	 * 
	 * @param j
	 * 	Le joueur sur le case Taxe de Luxe
	 */

	public void actionCase(Joueur j) {
		j.perdreArgent(this.montantTaxe(j),0);
		MonopolyIUT.getPartie().jouerEtape();
	}
	
	
	/**
	 * Calcul le montant de la taxe à payer. Ce montant est un entier et est défini avec le prix des propriétés du joueur ainsi que son argent de poche. 
	 * Ce montant est arrondie à la dixaine inférieur.
	 * 
	 * @see Joueur#getArgent()
	 * 
	 * @param j
	 * 	Le joueur sur le case Taxe de Luxe
	 * @return Un entier correspondant au montant que doit payer le joueur sur la case.
	 */
	public int montantTaxe(Joueur j) {
		int total = j.getCapital().getValue();
		total = Math.round(total/10);
		total = total - (total%10);
		return total;
	}
}
