package back;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * Cette classe Statistique permet de créer des statistiques.
 * Une statistique se caractérise par combien à gagner le joueur, combien a-t-il gagné de location, combien il a gagné de cartes chances,
 * combien il a perdu, combien il a perdu de location et de cartes chances, le nombre de tours qu'il a fait et le nombre de fois qu'il a été 
 * en ds,une liste des salles achetés et des salles vendues ainsi que son classement.
 *
 */

public class Statistiques {
	private int gagne;
	private int gagneLocation;
	private int gagneChances;
	private int perdu;
	private int perduLocation;
	private int perduChances;
	private int nbTours; //tours de plateau
	private int nbDS;
	private ArrayList<Propriete> salleAchetes;
	private ArrayList<Propriete> salleVendues;
	private ArrayList<String> materielAcheterParSalle;
	private ArrayList<String> materielVendueParSalle;
	private ArrayList<CarteChance> cartesChances;
	HashMap<String, Integer> materielAcheter;
	HashMap<String, Integer> materielVendue;
	private int classement;
	private Joueur joueur;
	

	/**
	 * Le constructeur de la classe Statistique permet de créer une nouvelle statistique ou tout est mis à 0. 	
	 */
	//Constructeur
	public Statistiques() {
		this.gagne = 0;
		this.gagneLocation = 0;
		this.gagneChances = 0;
		this.perdu = 0;
		this.perduLocation = 0;
		this.perduChances = 0;
		this.nbTours = 0;
		this.nbDS = 0;
		this.classement = 0;
		cartesChances= new ArrayList<CarteChance>();
		salleAchetes= new ArrayList<Propriete>();
		salleVendues= new ArrayList<Propriete>();
		materielAcheterParSalle=new ArrayList<String>();
		materielVendueParSalle=new ArrayList<String>();
		materielAcheter = new HashMap<String,Integer>();
		materielVendue= new HashMap<String,Integer>();
	}
	
	//Getter et Setter
	public int getGagne() {
		return gagne;
	}
	public void setGagne(int gagne) {
		this.gagne = gagne;
	}
	public int getGagneLocation() {
		return gagneLocation;
	}
	public void setGagneLocation(int gagneLocation) {
		this.gagneLocation = gagneLocation;
	}
	public int getGagneChances() {
		return gagneChances;
	}
	public void setGagneChances(int gagneChances) {
		this.gagneChances = gagneChances;
	}
	public int getPerdu() {
		return perdu;
	}
	public void setPerdu(int perdu) {
		this.perdu = perdu;
	}
	public int getPerduLocation() {
		return perduLocation;
	}
	public void setPerduLocation(int perduLocation) {
		this.perduLocation = perduLocation;
	}
	public int getPerduChances() {
		return perduChances;
	}
	public void setPerduChances(int perduChances) {
		this.perduChances = perduChances;
	}
	public int getNbTours() {
		return nbTours;
	}
	public void setNbTours(int nbTours) {
		this.nbTours = nbTours;
	}
	public int getNbDS() {
		return nbDS;
	}
	public void setNbDS(int nbDS) {
		this.nbDS = nbDS;
	}
	public ArrayList<Propriete> getSalleAchetes() {
		return salleAchetes;
	}
	public void setSalleAchetes(ArrayList<Propriete> salleAchetes) {
		this.salleAchetes = salleAchetes;
	}
	public ArrayList<Propriete> getSalleVendues() {
		return salleVendues;
	}
	public void setSalleVendues(ArrayList<Propriete> salleVendues) {
		this.salleVendues = salleVendues;
	}
	public int getClassement() {
		return classement;
	}
	public void setClassement(int classement) {
		this.classement = classement;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	public ArrayList<CarteChance> getCartesChances() {
		return cartesChances;
	}

	public void setCartesChances(ArrayList<CarteChance> cartesChances) {
		this.cartesChances = cartesChances;
	}
	
	//Fontions
	/**
	 * Méthode ajouterSalleAchete permet d'ajouter une Propriete dans l'ArrayList SalleAchetes.
	 * @param p Propriete à ajouter
	 */
	private void ajouterSalleAchete(Propriete p) {
		salleAchetes.add(p);
		materielAcheter.put(p.getNom(), 0);
		materielVendue.put(p.getNom(), 0);
	}

	/**
	 * Méthode ajouterUneSalleAchete permet d'effectuer les tests nécessaire pour lancer la méthode ajouterSalleAchete.
	 * @param p Propriete à ajouter
	 */
	public void ajouterUneSalleAchete(Propriete p) {
		if(p!=null) {
			ajouterSalleAchete(p);
		}else {
			System.out.println("Erreur la propri�t� est nulle");
		}
	}


	/**
	 * Méthode supprimerSalleAchete permet de supprimer une salleAchete de l'ArrayList de salleAchetes.
	 * @param p Propriete à suprimer
	 */
	public void supprimerSalleAchete(Propriete p) {
		salleAchetes.remove(p);
	}

	/**
	 * Méthode existeSalleAchete permet de tester si une propriété est présente dans l'ArrayList salleAchetes
	 * @param p Propriete
	 * @return boolean , retourne true si la propriété est déjà présentes sinon false
	 */
	public boolean existeSalleAchete(Propriete p) {
		return salleAchetes.contains(p);
	}
	
	/**
	 * Méthode ajouterSalleVendue permet d'ajouter une Propriete dans l'ArrayList SalleVendue.
	 * @param p Propriete à ajouter
	 */
	private void ajouterSalleVendue(Propriete p) {
		salleVendues.add(p);
	}

	/**
	 * Méthode ajouterUneSalleVendue permet d'effectuer les tests necessaire pour lancer la méthode ajouterSalleVendue.
	 * @param p Propriete à ajouter
	 */
	public void ajouterUneSalleVendue(Propriete p) {
		if(p!=null) {
			ajouterSalleVendue(p);
		}else {
			System.out.println("Erreur la propri�t�e est nulle");
		}
	}


	/**
	 * Méthode supprimerSalleVendue permet de supprimer une salleVendue de l'ArrayList de salleVendue.
	 * @param p Propriete à suprimer
	 */
	public void supprimerSalleVendue(Propriete p) {
		salleVendues.remove(p);
	}

	
	/**
	 * Méthode existeSalleVendue permet de tester si une propriété est présente dans l'ArrayList salleVendues
	 * @param p Propriete
	 * @return boolean , retourne true si la propriété est déjà présentes sinon false
	 */
	public boolean existeSalleVendue(Propriete p) {
		return salleVendues.contains(p);
	}
	
	/**
	 * Méthode ajouterCarteChance permet d'ajouter une CarteChance dans l'ArrayList cartesChances.
	 * @param c CarteChance à ajouter
	 */
	private void ajouterCarteChance(CarteChance c) {
		cartesChances.add(c);
	}

	/**
	 * Méthode ajouterUneCarteChance permet d'effectuer les tests nécessaire pour lancer la méthode ajouterCarteChance.
	 * @param c CarteChance à ajouter
	 */
	public void ajouterUneCarteChance(CarteChance c) {
		if(c!=null) {
			ajouterCarteChance(c);
		}else {
			System.out.println("Erreur la carte chance est nulle");
		}
	}
	
	/**
	 * Méthode ajouterUnMateriel permet d'ajouter un matèriel dans le hashMap materielAcheter.
	 * @param nom est le nom de la propriété
	 */
	public void ajouterUnMateriel(String nom ) {
		int m=materielAcheter.get(nom).intValue();
		materielAcheter.replace(nom, m+1);
	}

	/**
	 * Methode supprimerUnMateriel permet d'ajouter un matèriel dans le hashMap materielVendue.
	 * @param nom est le nom de la propriété
	 */
	public void supprimerUnMateriel(String nom) {
		int m=materielVendue.get(nom).intValue();
		materielAcheter.replace(nom, m+1);
	}

	


}
