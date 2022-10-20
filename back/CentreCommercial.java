package back;

import java.util.ArrayList;

import view.CentreCommercialView;
import view.FicheCentreCommercial;
import view.MonopolyIUT;


/*
 * Cette classe correspond à la classe des centres commerciaux qui herite de la classe Propriete. 
 */

public class CentreCommercial extends Propriete {

	/**
	 * Liste des joueurs propriétaires de services.
	 * 
	 * @see CentreCommercial#getListeProprio()
	 */
	static private ArrayList<Joueur> listeProprio=new ArrayList<Joueur>();

	/**
	 * Prix que les joueurs non propriétaires doivent payer si le propriétaire possède 1 centre commerciale.
	 * 
	 * @see CentreCommercial#setCoutLoyer0(int)
	 * @see CentreCommercial#getCoutLoyer0()
	 * 
	 */
	private int coutLoyer0;

	/**
	 * Prix que les joueurs non propriétaires doivent payer si le propriétaire possède 2 centres commerciaux.
	 * 
	 * @see Salle#setCoutLoyer1(int)
	 * @see Salle#getCoutLoyer1()
	 * 
	 */
	private int coutLoyer1;

	/**
	 * Prix que les joueurs non propriétaires doivent payer si le propriétaire possède 3 centres commerciaux.
	 * 
	 * @see Salle#setCoutLoyer2(int)
	 * @see Salle#getCoutLoyer2()
	 * 
	 */
	private int coutLoyer2;

	/**
	 * Prix que les joueurs non proprieéaires doivent payer si le propriétaire possède 4 centres commerciaux.
	 * 
	 * @see Salle#setCoutLoyer3(int)
	 * @see Salle#getCoutLoyer3()
	 * 
	 */
	private int coutLoyer3;

	/**
	 * Constructeur d'une case CentreCommercial.
	 * 
	 * @see Propriete
	 * 
	 * @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 * 	L'identifiant de la case en int.
	 * @param couleur
	 * 	La couleur de la case en String.
	 * @param prixAchat
	 * 	Le prix d'achat de la propriété en int.
	 * @param coutLoyer0
	 * 	Le coût du loyer quand le propriétaire possède 1 centre commercial.
	 * @param coutLoyer1
	 * 	Le coût du loyer quand le propriétaire possède 2 centres commerciaux.
	 * @param coutLoyer2
	 * 	Le coût du loyer quand le propriétaire possède 3 centres commerciaux.
	 * @param coutLoyer3
	 * 	Le coût du loyer quand le propriétaire possède 4 centres commerciaux.
	 */
	public CentreCommercial(String nom, int id,String couleur, int prixAchat, int coutLoyer0, int coutLoyer1, int coutLoyer2, int coutLoyer3) {
		super(nom, id, couleur, prixAchat);
		view=new CentreCommercialView(this);
		fiche=new FicheCentreCommercial(this);
		this.coutLoyer0 = coutLoyer0;
		this.coutLoyer1 = coutLoyer1;
		this.coutLoyer2 = coutLoyer2;
		this.coutLoyer3 = coutLoyer3;
	}

	
	/**
	 * 
	 * @return La vue de la propriété.
	 */
	public CentreCommercialView getView() {
		return (CentreCommercialView)view;
	}

	/**
	 * Méthode de récuperation de la listes des propriétaires.
	 * 
	 * @return
	 * 	Un ArrayList des propriétaires de Centre commerciaux
	 */
	public static ArrayList<Joueur> getListeProprio() {
		return listeProprio;
	}


	/**
	 * Compte le nombre de Centre commerciaux possédés par le propriétaire.
	 * 
	 * 
	 * @return
	 * 	Le nombre de centre commerciaux possédé
	 */
	public int recurence() {
		int res=0;
		for(int i=0;i<CentreCommercial.listeProprio.size();i++) {
			if(CentreCommercial.listeProprio.get(i).equals(super.getProprio())) {
				res++;
			}
		}
		return res;
	}

	
	public int getLoyer(int nb) {
		return recurence()*50;
	}



	/**
	 * Méthode d'achat d'un centre commercial.
	 * Changement de propriétaire via la super classe.
	 * 
	 * @see Propriete
	 * 
	 * @param j
	 * 	Le joueur qui achète le centre commercial.
	 */
	public void acheter(Joueur j) {
		//Vérification du propriétaire avant affectation.
		if (j.equals(super.getProprio())) {
			System.out.println(j.getPseudo()+" est d�j� le propri�taire.");
		}else if(super.getProprio()==null){
			super.setProprio(j);
			listeProprio.add(j);
			j.ajouterUneMatiere(this);
			getView().setPossedee(true);
			MonopolyIUT.getPartie().getPlateau().getView().reinitialisation();
		}else {
			System.out.println("Cette propri�t� appartient � un autre joueur, impossible de l'acheter.");
		}
	}



	/**
	 * Méthode de vente d'un centre commercial.
	 * Ajout de l'argent au joueur en paramètre.
	 * Le joueur recupère la moitié du prix d'achat du centre commercial.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * 	Le joueur qui vend son centre commercial.
	 */
	public void vendre() {
		int prixVente = super.getPrixAchat()/2;
		super.getProprio().gagnerArgent(prixVente, 1);
		listeProprio.remove(super.getProprio());
		super.setProprio(null);
		getView().setPossedee(false);
	}


	/**
	 * Méthode de payement d'un centre commercialß.
	 * Ajout de l'argent au joueur propriétaire..
	 * 
	 * @see Joueur
	 * 
	 * 
	 * @param j
	 * 	Le joueur paye le propriétaire du centre commercial.
	 */
	public void payerLoyer(Joueur j) {
		int centrePossede = this.recurence();
		int prix;
		switch (centrePossede) {
			case 1 : prix = coutLoyer0;
				break;
			case 2 : prix = coutLoyer1;
				break;
			case 3 : prix = coutLoyer2;
				break;
			case 4 : prix = coutLoyer3;
				break;
			default : prix = coutLoyer0;
		}
		j.perdreArgent(prix, 1);
		super.getProprio().gagnerArgent(prix, 1);
	}


	//Accesseurs
	public int getCoutLoyer3() {
		return coutLoyer3;
	}


	public void setCoutLoyer3(int coutLoyer3) {
		this.coutLoyer3 = coutLoyer3;
	}


	public int getCoutLoyer2() {
		return coutLoyer2;
	}


	public void setCoutLoyer2(int coutLoyer2) {
		this.coutLoyer2 = coutLoyer2;
	}


	public int getCoutLoyer1() {
		return coutLoyer1;
	}


	public void setCoutLoyer1(int coutLoyer1) {
		this.coutLoyer1 = coutLoyer1;
	}


	public int getCoutLoyer0() {
		return coutLoyer0;
	}


	public void setCoutLoyer0(int coutLoyer0) {
		this.coutLoyer0 = coutLoyer0;
	}
	

}
