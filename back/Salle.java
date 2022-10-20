package back;


import java.util.ArrayList;

import back.Partie.ReglesAdditionnelles;
import view.FicheSalle;
import view.MonopolyIUT;
import view.SalleView;
public class Salle extends Propriete {

	/**
	 * Prix que les joueurs non propriétaires doivent payer si la case à 0 matériel.
	 * 
	 * @see Salle#setCoutLoyer0(int)
	 * @see Salle#getCoutLoyer0()
	 * 
	 */
	private int coutLoyer0;


	/**
	 * Prix que les joueurs non propriétaires doivent payer si la case à 1 matériel.
	 * 
	 * @see Salle#setCoutLoyer1(int)
	 * @see Salle#getCoutLoyer1()
	 * 
	 */
	private int coutLoyer1;

	/**
	 * Prix que les joueurs non propriétaires doivent payer si la case à 2 matàriels.
	 * 
	 * @see Salle#setCoutLoyer2(int)
	 * @see Salle#getCoutLoyer2()
	 * 
	 */
	private int coutLoyer2;

	/**
	 * Prix que les joueurs non propriétaires doivent payer si la case à 3 matériels.
	 * 
	 * @see Salle#setCoutLoyer3(int)
	 * @see Salle#getCoutLoyer3()
	 * 
	 */
	private int coutLoyer3;

	/**
	 * Prix de l'achat d'un matériel.
	 * 
	 * @see Salle#setCoutMateriel(int)
	 * @see Salle#getCoutMateriel()
	 * 
	 */
	private int coutMateriel;

	/**
	 * Nombre de matériels.
	 * 
	 * @see Salle#ajouterMateriel()
	 * @see Salle#retirerMateriel()
	 * @see Salle#getNbMateriels()
	 * 
	 */
	private int nbMateriels;

	/**
	 * Constructeur d'une case salle.
	 * 
	 * @see Propriete
	 * 
	 * @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 * 	L'identifiant de la case en int.
	 * @param couleur
	 * 	La couleur de la salle en String.
	 * @param prixAchat
	 * 	Le prix d'achat de la propriete en int.
	 * @param coutMateriel
	 * 	Le coût du matériel de la salle en int.
	 * @param coutLoyer0
	 * 	Le coût du loyer de la salle avec 0 matériel.
	 * @param coutLoyer1
	 * 	Le coût du loyer de la salle avec 1 matériels.
	 * @param coutLoyer2
	 * 	Le coût du loyer de la salle avec 2 matériels.
	 * @param coutLoyer3
	 * 	Le coût du loyer de la salle avec 3 matériels.
	 */
	public Salle(String nom, int id, String couleur, int prixAchat, int coutMateriel, int coutLoyer0, int coutLoyer1, int coutLoyer2, int coutLoyer3) {
		super(nom, id, couleur, prixAchat);
		//fiche=new FicheSalle(this);
		this.coutLoyer0 = coutLoyer0;
		this.coutLoyer1 = coutLoyer1;
		this.coutLoyer2 = coutLoyer2;
		this.coutLoyer3 = coutLoyer3;
		this.coutMateriel = coutMateriel;
		view = new SalleView(this);
		fiche=new FicheSalle(this);
		nbMateriels=-1;
	}

	/**
	 * 
	 * @return La vue de la propriété.
	 */
	public SalleView getView() {
		return (SalleView)view;
	}
	
	public void setCoutLoyer0(int coutLoyer0) {
		this.coutLoyer0 = coutLoyer0;
	}

	public void setCoutLoyer1(int coutLoyer1) {
		this.coutLoyer1 = coutLoyer1;
	}

	public void setCoutLoyer2(int coutLoyer2) {
		this.coutLoyer2 = coutLoyer2;
	}

	public void setCoutLoyer3(int coutLoyer3) {
		this.coutLoyer3 = coutLoyer3;
	}


	/**
	 * 
	 * @return Un int correspondant au prix de la salle avec 0 loyer.
	 * 
	 */
	public int getCoutLoyer0() {
		return coutLoyer0;
	}


	/**
	 * 
	 * @return Un int correspondant au prix de la salle avec 1 loyer.
	 * 
	 */
	public int getCoutLoyer1() {
		return coutLoyer1;
	}


	/**
	 * 
	 * @return Un int correspondant au prix de la salle avec 2 loyers.
	 * 
	 */
	public int getCoutLoyer2() {
		return coutLoyer2;
	}


	/**
	 * 
	 * @return Un int correspondant au prix de la salle avec 3 loyers.
	 * 
	 */
	public int getCoutLoyer3() {
		return coutLoyer3;
	}



	/**
	 * 
	 * @return Un int correspondant au prix de l'achat d'un matériel.
	 * 
	 */
	public int getCoutMateriel() {
		return coutMateriel;
	}


	/**
	 * Méthode de modification du propriétaire de la propriété.
	 * 
	 * @see Joueur
	 * 
	 * @param proprio
	 * 	Le nouveau propriétaire de la propriete.
	 */
	public void setProprio(Joueur proprio) {
		super.setProprio(proprio);
		if(proprio!=null)nbMateriels=0;
	}



	/**
	 * Ajoute un matériel s'il y a moins de 3 matériels.
	 */
	public void ajouterMateriel() {
		if(nbMateriels<3) {
			ameliorer();
			//Modif sur le joueur
			super.getProprio().getStats().ajouterUnMateriel(super.getNom());
			super.getProprio().setNbMaterielTotal(super.getProprio().getNbMaterielTotal()+1);
			super.getProprio().getCapital().setValue(super.getProprio().getCapital().getValue()+this.getArgentAmelioration());
			nbMateriels++;
			if(MonopolyIUT.getPartie().hasRegleAdd(ReglesAdditionnelles.CRISE_IMMOBILIER))MonopolyIUT.getPartie().getPlateau().modifierLoyers(true);
		}
	}

	/**
	 * Supprime un matériel s'il y a au moins un matériel.
	 */
	public void retirerMateriel() {
		vendre();
		super.getProprio().getStats().supprimerUnMateriel(super.getNom());
		super.getProprio().setNbMaterielTotal(super.getProprio().getNbMaterielTotal()-1);
		super.getProprio().getCapital().setValue(super.getProprio().getCapital().getValue()-this.getArgentVente()*2);
		nbMateriels--;

		if(nbMateriels==-1) {
			vendreSalle();
		}
		if(MonopolyIUT.getPartie().hasRegleAdd(ReglesAdditionnelles.CRISE_IMMOBILIER))MonopolyIUT.getPartie().getPlateau().modifierLoyers(false);
	}

	/**
	 * Renvoie le nombre de matériels de la salle
	 * 
	 * @return Nombre de matèeriels
	 */
	public int getNbMateriels() {
		return nbMateriels;
	}

	/**
	 * 
	 * Renvoie le coût du loyer correspondant au nombre de matériels demandé. Renvoie le prix d'achat si le nombre de matériels passé en paramètre n'est pas compris entre 0 et 3 inclus.
	 * 
	 * @param n Nombre de matériels
	 * @return Loyer correspondant
	 * 
	 */
	public int getLoyer(int n) {
		int sup=0;
		if(estPossedee())sup=this.calculerSupplement();
		switch (n) {
		case 0 : return coutLoyer0+sup;
		case 1 : return coutLoyer1+sup;
		case 2 : return coutLoyer2+sup;
		case 3 : return coutLoyer3+sup;
		default : return getPrixAchat()+sup;
		}
	}


	public int getLoyerSansSup(int n) {
		switch (n) {
		case 0 : return coutLoyer0;
		case 1 : return coutLoyer1;
		case 2 : return coutLoyer2;
		case 3 : return coutLoyer3;
		default : return getPrixAchat();
		}
	}
	

	/**
	 * Calcule le supplément que doit payer le joueur
	 * 
	 * @return Un entier correspondant au supplément
	 */
	private int calculerSupplement() {
		int sup = 0;
		ArrayList<Propriete> listePropriete = new ArrayList<Propriete>(getProprio().getMatieres());
		for(int i=0;i<listePropriete.size();i++) {
			if(listePropriete.get(i).getCouleur().equals(getCouleur()) && listePropriete.get(i).getId() != getId()) {
				sup+=100;
			}
		}
		return sup;
	}



	/**
	 * 
	 * Renvoie l'argent obtenu par la vente d'un matériel, ou de la propriété s'il n'y a pas de matériel.
	 * 
	 * @return Prix de vente
	 * 
	 */
	public int getArgentVente() {
		switch (nbMateriels) {
		case 0 : return getPrixAchat()/2;
		case 1 : return coutMateriel/2;
		case 2 : return coutMateriel/2;
		case 3 : return coutMateriel;
		default : return getPrixAchat()/2;
		}
	}
	
	/**
	 * 
	 * Renvoie l'argent obtenu par la vente d'un matériel, ou de la propriété s'il n'y a pas de matériel.
	 * 
	 * @param nb Nombre de matériels
	 * @return Prix de vente
	 * 
	 */
	public int getArgentVente(int nb) {
		switch (nb) {
		case 0 : return getPrixAchat()/2;
		case 1 : return coutMateriel/2;
		case 2 : return coutMateriel/2;
		case 3 : return coutMateriel;
		default : return getPrixAchat()/2;
		}
	}

	/**
	 * 
	 * Renvoie l'argent depensé pour l'achat d'un matériel.
	 * 
	 * @return Prix d'achat
	 * 
	 */
	public int getArgentAmelioration() {
		if(nbMateriels==2)return coutMateriel*2;
		return coutMateriel;
	}
	
	/**
	 * 
	 * Renvoie l'argent depensé pour l'achat d'un matériel.
	 * 
	 * @param nb Nombre de matériels
	 * @return Prix d'achat
	 * 
	 */
	public int getArgentAmelioration(int nb) {
		if(nb==2)return coutMateriel*2;
		return coutMateriel;
	}

	/**
	 * Méthode de vente d'un matériel ou de la salle.
	 * 
	 */
	protected void vendre() {
		getProprio().gagnerArgent(getArgentVente(), 1);
	}

	/**
	 * Méthode d'amélioration de la salle.
	 * 
	 */
	protected void ameliorer() {
		getProprio().perdreArgent(getArgentAmelioration(), 1);
	}


	/**
	 * Methode d'achat d'une Salle.
	 * Changement de propriétaire via la super classe.
	 * 
	 * @see Propriete
	 * 
	 * @param j
	 * 	Le joueur qui achéte la propriété.
	 */
	public void acheter(Joueur j) {
		//Vérification du propriétaire avant affectation.
		if (!estPossedee()) {
			setProprio(j);
			j.ajouterUneMatiere(this);
			getView().setPossedee(true);
			MonopolyIUT.getPartie().getPlateau().getView().reinitialisation();
		}
	}
	
	/**
	 * Méthode de vente d'une salle.
	 * 
	 * @see Joueur
	 * 
	 */
	protected void vendreSalle() {
		getProprio().supprimerMatiere(this);
		setProprio(null);
		getView().setPossedee(false);
	}


	/**
	 * 
	 * Renvoie le coût du loyer en fonction du nombre de matériels de la salle
	 * 
	 * @see Joueur
	 * 
	 * 
	 */
	public void payerLoyer(Joueur j) {
		int loyer = getLoyer(getNbMateriels());
		j.perdreArgent(loyer, 1);
		getProprio().gagnerArgent(loyer, 1);
	}

}
