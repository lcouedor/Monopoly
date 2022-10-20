package back;

import view.AngleView;
import view.FicheAngle;
import view.MonopolyIUT;

/**
 * Classe représentant la case départ.
 * Cette classe étend la Classe Case.
 * 
 * @see Case
 *
 */
public class Depart extends Case{
	
	/**
	 * La valeur des gains en passant par la case départ.
	 * 
	 * @see Depart#getGainPassage()
	 * @see Depart#setGainPassage(int)
	 */
	private int gainPassage;
	
	/**
	 * La valeur des gains à l'arret sur la case départ.
	 * 
	 * @see Depart#getGainArret()
	 * @see Depart#setGainArret(int)
	 */
	private int gainArret;

	/**
	 * Constructeur de la case départ.
	 * 
	 * @see Case
	 * 
	 * @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 * 	L'identifiant de la case en int.
	 * @param gainPassage
	 * 	La valeur des gains au passage de la case départ en int.
	 * @param gainArret
	 * 	La valeur des gains à l'arrêt de la case départ en int.
	 * @param couleur
	 * 	La couleur de la case départ en String.
	 */
	public Depart(String nom, int id, int gainPassage, int gainArret, String couleur) {
		super(nom, id, couleur);
		this.gainPassage = gainPassage;
		this.gainArret = gainArret;
		fiche = new FicheAngle(nom, "C'est une nouvelle semaine qui commence, touchez "+gainPassage+"€.\n Si vous prenez le temps de profiter de cette bonne nouvelle, touchez "+gainArret+"€");
		view = new AngleView(this);
	}
	
	/**
	 * 
	 * @return La vue de la case départ.
	 */
	public AngleView getView() {
		return (AngleView)view;
	}

	/**
	 * 
	 * @return La valeur des gains en passant par la case départ.
	 */
	public int getGainPassage() {
		return gainPassage;
	}

	/**
	 * Modifier la valeur de la case départ.
	 * 
	 * @param gainPassage
	 * 	La valeur des gains de la case départ.
	 * 
	 */
	public void setGainPassage(int gainPassage) {
		this.gainPassage = gainPassage;
	}
	
	/**
	 * 
	 * @return La valeur des gains à l'arrêt sur la case départ.
	 */
	public int getGainArret() {
		return gainArret;
	}

	/**
	 * Modifier la valeur à l'arrêt sur la case départ.
	 * 
	 * @param gainArret
	 * 	La valeur des gains de la case départ.
	 * 
	 */
	public void setGainArret(int gainArret) {
		this.gainArret = gainArret;
	}
	
	
	/**
	 * Donne au joueur qui s'arrête sur la case deux fois les gains de la case départ.
	 * 
	 *
	 * @see Joueur#ajouterArgent(int)
	 * 
	 * @param j
	 * 	Le joueur sur la case départ.
	 */
	private void arretDepart(Joueur j) {
		j.gagnerArgent(gainArret*j.getModificateur().getToucheBourse(),1);
	}
	
	/**
	 * Donne au joueur qui passe sur la case les gains de la case départ.
	 * 
	 * @see Joueur#ajouterArgent(int)
	 * 
	 * @param j
	 * 	Le joueur sur la case départ.
	 */
	private void passageDepart(Joueur j) {
		j.gagnerArgent(gainPassage*j.getModificateur().getToucheBourse(),1);
	}

	/**
	 * Méthode d'action de la case.
	 * C'est cette méthode qui oriente l'action que doit réaliser le joueur.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * @param j
	 * 	Le joueur paye le propriétaire du service.
	 */
	public void actionCase(Joueur j) {
		MonopolyIUT.getPartie().jouerEtape();
	}
	
	
	/**
	 * Méthode d'action de la case.
	 * C'est cette méthode qui oriente l'action que doit réaliser le joueur.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * @param j
	 * 	Le joueur qui passe par la case
	 */
	public void caseDepart(Joueur j) {
		if(j.getPion().getPosition() == super.getId()) {
			this.arretDepart(j);
		}else {
			this.passageDepart(j);
		}
	}
	
	
	
//	public static void main(String[] args) {
//		Joueur j1 = new Joueur("Moi", new Pion("", 1), 0);
//		Depart d = new Depart("depart", 1, 1000);
//		d.actionCase(j1);
//	}

}
