package back;

import view.ActionView;
import view.MonopolyIUT;

/**
 * Classe abstraite représentant les propriétés.
 * Cette classe étend la Classe Case.
 * 
 * @see Case
 *
 */
public abstract class Propriete extends Case {
	
	/**
	 * Le prix d'achat est la valeur d'un propriété.
	 * Cette valeur peut être modifiée.
	 * 
	 * @see Propriete#setPrixAchat(int)
	 * @see Propriete#getPrixAchat()
	 * 
	 */
	private int prixAchat;
	
	/**
	 * Le Joueur propriétaire de la propriété.
	 * 
	 * @see Joueur
	 * @see Propriete#setProprio(String)
	 * @see Propriete#getProprio()
	 */
	private Joueur proprio;
	
	/**
	 * Constructeur d'une propriété.
	 * 
	* @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 * 	L'identifiant de la case en int.
	 * @param couleur
	 * 	La couleur de la propriété.
	 * @param prixAchat
	 * 	Le prix d'achat de la propriete.
	 */
	public Propriete(String nom, int id,String couleur, int prixAchat) {
		super(nom, id, couleur);
		this.setProprio(null);
		this.setPrixAchat(prixAchat);
	}

	
	/**
	 * 
	 * @return Un int correspondant au prix de la propriété.
	 */
	public int getPrixAchat() {
		return prixAchat;
	}

	/**
	 * Méthode de modification du prix d'achat d'une propriété.
	 * 
	 * @param prixAchat
	 * 	Le nouveau prix d'achat.
	 */
	public void setPrixAchat(int prixAchat) {
		this.prixAchat = prixAchat;
	}
	
	/**
	 * Méthode de modification du propriétaire de la propriété.
	 * 
	 * @param proprio
	 * 	Le nouveau propriétaire de la propriété.
	 */
	public void setProprio(Joueur proprio) {
		this.proprio = proprio;
	}
	
	/**
	 * 
	 * @return
	 *  Le propriétaire de la propriété.
	 */
	public Joueur getProprio() {
		return proprio;
	}
	
	/**
	 * 
	 * Renvoie un booléen indiquant si la propriété est possédée.
	 * 
	 * @return vrai si la propriété est possédée, faux sinon.
	 */
	public boolean estPossedee() {
		return proprio!=null;
	}

	
	public int getLoyer(int nb) {
		return 0;
	}
	
	
	
	
	/**
	 * Méthode abstraite pour toutes les propriétés.
	 * Elle permet d'ajouter au joueur la propriété achetée.
	 * 
	 * @param j Joueur qui achète
	 * 
	 */
	public abstract void acheter(Joueur j);
	
	
	/**
	 * Méthode abstraite pour toutes les propriétés.
	 * Elle permet de supprimer au joueur la propriété achetée.
	 * 
	 */
	protected abstract void vendre();
	
	
	/**
	 * Méthode abstraite pour toutes les propriétés.
	 * Elle permet de payer le joueur propriétaire de la propriété.
	 * 
	 * @param j Joueur qui paye
	 * 
	 */
	public abstract void payerLoyer(Joueur j);
	
	
	/**
	 * Méthode d'action de la case.
	 * C'est cette méthode qui oriente l'action que doit réaliser le joueur.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * @param j
	 * 	Le joueur paye le propriétaire de la propriété.
	 */
	public void actionCase(Joueur j) {
		// Si il y a un proprio
		if(estPossedee()){
			//Le joueur est propriétaire
			if(j.equals(getProprio())){
				if(j.isBot()) {
					ActionView.ameliorer(j, j.getMin());
				}else {
					ActionView.setActionAmeliorer(j, 0);
				}
			}else {
				if(j.isBot()) {
					ActionView.payer(j, this);
				}else {
					ActionView.setActionPayer(j, this);
				}
			}
			// Si le joueur à assez d'argent pour acheter
		}else if (j.getArgent().getValue() >= getPrixAchat()) {
			if(j.isBot()) {
				if(j.getArgentBis()-getPrixAchat()>j.getMin()) {
					ActionView.acheter(j, this);
				}else {
					ActionView.pasAcheter(j, this);
				}
			}else {
				ActionView.setActionAcheter(j, this);
			}
		}else {
			MonopolyIUT.getPartie().jouerEtape();
		}
	}

}
