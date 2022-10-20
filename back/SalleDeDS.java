package back;

import java.util.HashMap;

import view.AngleView;
import view.FicheAngle;
import view.MonopolyIUT;

/**
 * Classe représentant la case salle de DS.
 * Cette classe étend la Classe Case.
 * 
 * @see Case
 *
 */
public class SalleDeDS extends Case {

	/**
	 * Prix à payer pour sortir de la salle des ds.
	 */
	private int prixSortie;

	/**
	 * Liste des joueurs sur la case salle des DS
	 */
	static private HashMap<Joueur,Integer> joueursEnDS = new HashMap<Joueur,Integer>();

	/**
	 * Constructeur de la case salle de DS.
	 * 
	 * @see Case
	 * 
	 * @param nom
	 * Le nom de la case en String.
	 * @param id
	 * 	L'identifiant de la case en int.
	 * @param couleur
	 * 	La couleur de la case salle de DS en String.
	 * @param prixSortie
	 * 	Le prix pour sortir de la salle de DS quand on a ni carte chance ni fait un double, en int.
	 */
	public SalleDeDS(String nom, int id, String couleur, int prixSortie) {
		super(nom, id,couleur);
		this.prixSortie = prixSortie;
		fiche = new FicheAngle(nom,"Faites un double pour sortir de DS. Si vous n'avez pas fait de double à votre deuxième tour en DS, soudoyez le surveillant 100€ pour sortir.");
		view = new AngleView(this);
	}

	/**
	 * 
	 * @return La vue de la Salle de DS.
	 */
	public AngleView getView() {
		return (AngleView)view;
	}


	public int getPrixSortie() {
		return prixSortie;
	}



	/**
	 * Méthode permettant de vérifier si un joueur est en DS
	 * 
	 * @param j Le joueur qu'on cherche
	 * @return
	 * Un booléen de valeur true si le joueur est présent, false si il est absent
	 */
	public static boolean joueurEnDS(Joueur j) {
		return joueursEnDS.get(j)!=null;
	}

	/**
	 * Méthode de suppression d'un joueur de la liste des joueur en salle des DS
	 * 
	 * @param j Le joueur a supprimer de la liste
	 */
	public static void supprimerJoueurEnDs(Joueur j) {
		joueursEnDS.remove(j);
	}


	public static void ajouterJoueurEnDS(Joueur j) {
		joueursEnDS.put(j,0);
	}
	
	public static void tentativeEvasion(Joueur j) {
		joueursEnDS.replace(j, joueursEnDS.get(j)+1);
	}
	
	public static int getNbToursDS(Joueur j) {
		return joueursEnDS.get(j);
	}

	/**
	 * Methode d'action de la case.
	 * C'est cette methode qui oriente l'action que doit réaliser le joueur.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * @param j
	 * 	Le joueur qui reste enfermé dans la salle.
	 */
	public void actionCase(Joueur j) {
		MonopolyIUT.getPartie().jouerEtape();
	}
}
