package back;

import view.CaseView;
import view.Fiche;

/** Classe abstraite représentant une case
 * 
 *
 */
public abstract class Case {
	
	/**
	 * L'identifiant d'une case est un int. Il est unique.
	 * 
	 * @see Case#getId()
	 */
	private int id;
	
	/**
	 * L'identifiant d'une case est un String.
	 * 
	 * @see Case#getNom()
	 */
	private String nom;
	
	/**
	 * La couleur d'une case est un String.
	 * 
	 * @see Case#getNom()
	 */
	private String couleur;
	
	/**
	 * La fiche d'une case.
	 * 
	 * @see Case#getFiche()
	 */
	protected Fiche fiche;
	
	/**
	 * La vue de la case.
	 * 
	 * @see CaseView
	 */
	protected CaseView view;
	
	/**
	 * Constructeur d'une case contenat un nom, un identifiant et une couleur.
	 * 
	 * @param nom Nom de la case
	 * @param id Identifiant de la case
	 * @param couleur Couleur de la case
	 */
	public Case(String nom,int id,String couleur) {
		this.nom = nom;
		this.id = id;
		this.couleur = couleur;
	}
	
	public CaseView getView() {
		return view;
	}


	/**
	 * 
	 * @return Le nom de la case sous forme de String.
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * 
	 * @return L'identifiant de la case sous forme d'un int.
	 */
	public int getId() {
		return id;
	}
	
	public String getCouleur() {
		return couleur;
	}


	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	/**
	 * Renvoie la fiche de la salle.
	 * 
	 * @return La SalleView de la salle.
	 * 
	 */
	public Fiche getFiche() {
		return fiche;
	}
	
	
	/**
	 * Méthode d'action de la case.
	 * C'est cette methode qui oriente l'action que doit réaliser le joueur.
	 * 
	 * @see Joueur
	 * 
	 * 
	 * @param j
	 * 	Le joueur acteur de l'action.
	 */
	public abstract void actionCase(Joueur j);



}
