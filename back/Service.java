package back;

import java.util.ArrayList;

import view.FicheService;
import view.MonopolyIUT;
import view.ProprieteView;
import view.ServiceView;


/*
 * Classe représentant les services.
 * Cette classe étend la Classe Propriete.
 * 
 * @see Propriete
 */

public class Service extends Propriete {

	/**
	 * Liste des joueurs propriétaires de services.
	 * 
	 * @see Service#getListeProprio()
	 */
	static private ArrayList<Joueur> listeProprio=new ArrayList<Joueur>();

	/**
	 * Constructeur d'une case service.
	 * 
	 * @see Propriete
	 * 
	 * @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 * 	L'identifiant de la case en int.
	 * @param couleur
	 * 	La couleur de la case des services en String.
	 * @param prixAchat
	 * 	Le prix d'achat de la propriété en int.
	 */
	public Service(String nom, int id,String couleur, int prixAchat) {
		super(nom, id, couleur, prixAchat);
		view = new ServiceView(this);
		fiche = new FicheService(this);
	}


	/**
	 * 
	 * @return La vue de la propriété.
	 */
	public ProprieteView getView() {
		return (ProprieteView)view;
	}

	/**
	 * Méthode de récuperation de la listes des propriétaires.
	 * 
	 * @return
	 * 	Un ArrayList des propriétaires de Services
	 */
	public static ArrayList<Joueur> getListeProprio() {
		return listeProprio;
	}


	/**
	 * Compte le nombre de Service possédés par le propriétaire.
	 * 
	 * 
	 * @return
	 * 	Le nombre de services possedé
	 */
	public int recurence() {
		int res=0;
		for(int i=0;i<Service.listeProprio.size();i++) {
			if(Service.listeProprio.get(i).equals(super.getProprio())) {
				res++;
			}
		}
		return res;
	}



	/**
	 * Méthode d'achat d'un Service.
	 * Changement de propriétaire via la super classe.
	 * 
	 * @see Propriete
	 * 
	 * @param j
	 * 	Le joueur qui achéte le service.
	 */
	public void acheter(Joueur j) {
		//V�rification du propri�taire avant affectation.
		if (j.equals(super.getProprio())) {
			System.out.println(j.getPseudo()+" est d�j� le propri�taire.");
		}else if(super.getProprio()==null){
			super.setProprio(j);
			Service.listeProprio.add(j);
			j.ajouterUneMatiere(this);
			getView().setPossedee(true);
			MonopolyIUT.getPartie().getPlateau().getView().reinitialisation();
		}else {
			System.out.println("Cette propri�t� appartient � un autre joueur, impossible de l'acheter.");
		}
	}

	public int getLoyer(int nb) {
		return recurence();
	}


	/**
	 * Méthode de vente d'un service.
	 * Ajout de l'argent au joueur en paramètre.
	 * Le joueur recupère la moitié du prix d'achat du service.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * 	Le joueur qui vend son service.
	 */
	public void vendre() {
		//V�rification du propri�taire avant vente.
		int prixVente = super.getPrixAchat()/2;
		super.getProprio().gagnerArgent(prixVente, 1);
		super.setProprio(null);
		Service.listeProprio.remove(super.getProprio());
		getView().setPossedee(false);
	}


	/**
	 * Méthode de payement d'un service.
	 * Ajout de l'argent au joueur propriétaire.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * @param j
	 * 	Le joueur paye le propriétaire du service.
	 */
	public void payerLoyer(Joueur j) {
		int multiplicateur = this.recurence()*10;
		multiplicateur *= (j.getDe1()+j.getDe2());
		j.perdreArgent(multiplicateur, 1);
		getProprio().gagnerArgent(multiplicateur, 1);
	}

}
