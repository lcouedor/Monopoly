package back;

import java.util.Comparator;
import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import view.FicheJoueur;
import view.JoueurClassementView;
import view.MonopolyIUT;
import view.Pion;
import view.ResumeJoueurView;

/**
 * 
 * Cette classe Joueur permet de créer des joueurs.
 * Un joueur se caractérise par son pseudo, son pion, sa position, ses statistiques, son argents, ses listes de matières et de cartes chances et son modificateur.
 *
 */

public class Joueur {
	private String pseudo;
	private Pion pion;
	private Statistiques stats;
	private IntegerProperty argent = new SimpleIntegerProperty();
	private SortedList<Propriete> listeMatieres;
	private ObservableList<Propriete> matieres;
	private ObservableList<CarteChance> chances;
	private Modificateur modificateur;
	private FicheJoueur fiche;
	private ResumeJoueurView resume;
	private int nbMaterielTotal;
	private int de1;
	private int de2;
	private boolean bot;
	private JoueurClassementView classement;

	// Nombre de propriétés, à modifier lors de l'achat/vente d'une propriétés
	private IntegerProperty nbProprietes = new SimpleIntegerProperty();
	// Capital, valeur de l'ensemble des possessions + argent,  à modifier à chaque gain/dépense d'argent, achat/vente propriétés/matériels
	private IntegerProperty capital = new SimpleIntegerProperty();



	/**
	 * Le constructeur de la classe Joueur permet de créer un nouveau joueur. 	
	 * @param pseudo Pseudo du joueur
	 * @param argent Argents du joueur
	 * @param couleur Couleur du pion
	 * @param bot Boolean permettant de savoir si le joueur est un bot ou non
	 */
	//Constructeur
	public Joueur(String pseudo, String couleur, int argent,boolean bot) {
		this.pseudo = pseudo;
		this.pion = new Pion(this, couleur);
		this.stats = new Statistiques();
		this.argent.setValue(argent);
		this.modificateur = new Modificateur();
		this.matieres = FXCollections.observableArrayList();
		this.listeMatieres= new SortedList<Propriete>(matieres);
		this.chances = FXCollections.observableArrayList();
		this.nbMaterielTotal=0;
		this.fiche=new FicheJoueur(this);
		this.resume=new ResumeJoueurView(this);
		this.classement=new JoueurClassementView(this);
		this.de1=0;
		this.de2=0;
		this.bot=bot;

		// A initialiser à 0 et à modifier dans les méthodes
		this.nbProprietes.setValue(0);
		this.capital.setValue(argent);


		MonopolyIUT.getPartie().setTrieArgent();
		this.argent.addListener(e->MonopolyIUT.getPartie().updateClassement());
		this.nbProprietes.addListener(e->MonopolyIUT.getPartie().updateClassement());
		this.capital.addListener(e->MonopolyIUT.getPartie().updateClassement());


		listeMatieres.setComparator(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return Integer.compare(((Propriete)o1).getId()+(o1 instanceof CentreCommercial?100:o1 instanceof Service?200:0), ((Propriete)o2).getId()+(o2 instanceof CentreCommercial?100:o2 instanceof Service?200:0));
			}
		});

	}


	//Getter et Setter

	public int getDe1() {
		return de1;
	}



	public void setDe1(int de1) {
		this.de1 = de1;
	}



	public int getDe2() {
		return de2;
	}



	public void setDe2(int de2) {
		this.de2 = de2;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public Pion getPion() {
		return pion;
	}

	public void setPion(Pion pion) {
		this.pion = pion;
	}

	public Statistiques getStats() {
		return stats;
	}

	public void setStats(Statistiques stats) {
		this.stats = stats;
		stats.setJoueur(this);
	}

	public IntegerProperty getArgent() {
		return argent;
	}

	public int getArgentBis() {
		return argent.getValue();
	}

	public void setArgent(int argent) {
		this.argent.setValue(argent);
	}

	public SortedList<Propriete> getMatieres() {
		return listeMatieres;
	}

	public ObservableList<CarteChance> getChances() {
		return chances;
	}

	public Modificateur getModificateur() {
		return modificateur;
	}

	public void setModificateur(Modificateur modificateur) {
		this.modificateur = modificateur;
	}

	public FicheJoueur getFicheDetailJoueur() {
		return fiche;
	}

	public ResumeJoueurView getResumeJoueurView() {
		return resume;
	}

	public IntegerProperty getNbProprietes() {
		return this.nbProprietes;
	}

	public IntegerProperty getCapital() {
		return this.capital;
	}

	public FicheJoueur getFiche() {
		return fiche;
	}

	public JoueurClassementView getClassement() {
		return classement;
	}

	public void setFiche(FicheJoueur fiche) {
		this.fiche = fiche;
	}



	public ResumeJoueurView getResume() {
		return resume;
	}



	public void setResume(ResumeJoueurView resume) {
		this.resume = resume;
	}



	public int getNbMaterielTotal() {
		return nbMaterielTotal;
	}



	public void setNbMaterielTotal(int nbMaterielTotal) {
		this.nbMaterielTotal = nbMaterielTotal;
	}



	public void setArgent(IntegerProperty argent) {
		this.argent = argent;
	}



	public void setNbProprietes(IntegerProperty nbProprietes) {
		this.nbProprietes = nbProprietes;
	}



	public void setCapital(int capital) {
		this.capital.setValue(capital);
	}


	public boolean isBot() {
		return bot;
	}


	public void setBot(boolean bot) {
		this.bot = bot;
	}


	//Fonction
	/**
	 * La méthode afficher_info() permet d'afficher les informations de la classe
	 */ 
	public void afficher_info() {
		int i;
		int j;
		System.out.println("Joueur : "+pseudo);
		System.out.println("Argent : "+argent);
		System.out.println("Matiï¿½res : ");
		for (i=0;i<matieres.size() ;i++) {
			System.out.println(this.matieres.get(i));
		}
		System.out.println("Cartes chances : ");
		for (j=0;j<chances.size() ;j++) {
			System.out.println(chances.get(j));
		}

	}

	/**
	 * Méthode ajouterMatiere permet d'ajouter une Propriete m dans l'ArrayList de Matiere et dans l'ArrayList sallleAchete de la class Stats.
	 * @param m Propriete à ajouter
	 */
	private void ajouterMatiere(Propriete m) {
		matieres.add(m);
		stats.ajouterUneSalleAchete(m);
		this.nbProprietes.setValue(this.nbProprietes.getValue()+1);


	}

	/**
	 * Méthode ajouterUneMatiere permet d'effectuer les tests afin de lancer la méthode ajouterMatière.
	 * @param m Propriete à ajouter
	 */
	public void ajouterUneMatiere(Propriete m) {
		if(existeMatiere(m) && m==null) {
			System.out.println("Erreur");
		}else{
			ajouterMatiere(m);
			perdreArgent(m.getPrixAchat(),1);
			this.capital.setValue(this.capital.getValue()+m.getPrixAchat());
		}
	}

	/**
	 * Méthode supprimerMatiere permet de supprimer une Matiere m de l'ArrayList de Matiere et dans les ArrayList SalleVendue et SalleAchete de la classe Statistiques.
	 * @param m Propriete à supprimer
	 */
	public void supprimerMatiere(Propriete m) {
		matieres.remove(m);
		stats.ajouterUneSalleVendue(m);
		this.nbProprietes.setValue(this.nbProprietes.getValue()-1);
	}

	/**
	 * Méthode existeMatiere permet de tester si une propriété est présente dans l'ArrayList matieres 
	 * @param m Propriete
	 * @return boolean , retourne true si la propriété est déjà présentes sinon false
	 */
	public boolean existeMatiere(Propriete m) {
		return matieres.contains(m);
	}

	/**
	 * Méthode ajouterCarteChance permet d'ajouter une carte chance c dans l'ArrayList de CarteChance.
	 * @param c carteChance à ajouter
	 */
	private void ajouterCarteChance(CarteChance c) {
		chances.add(c);
	}

	/**
	 * Méthode ajouterUneCarteChance permet d'effectuer les tests nécessaires pour lancer la  méthode ajouterCarteChance.
	 * @param c carteChance à ajouter
	 */
	public void ajouterUneCarteChance(CarteChance c) {
		if(c!=null) {
			ajouterCarteChance(c);
			this.stats.ajouterUneCarteChance(c);
		}else {
			System.out.println("Erreur la carte chance est nulle");
		}
	}

	/**
	 * Méthode supprimerCarteChance permet de supprimer une carte chance c de l'ArrayList de CarteChance.
	 * @param c carteChance à supprimer
	 */
	public void supprimerCarteChance(CarteChance c) {
		chances.remove(c);

	}

	/**
	 * Méthode existeChance permet de tester si une carte chance est présente dans l'ArrayList chnaces 
	 * @param c CarteChance 
	 * @return boolean , retourne true si la carte chance est déjà présentes sinon false
	 */
	public boolean existeChance(CarteChance c) {
		return chances.contains(c);
	}

	/**
	 * Méthode gagnerArgent permet d'ajouter l'argent gagner en augmentant argent et capital et les variable gagne et GagneLocation ou GagneChance de la class Statistiques  
	 * @param somme int à ajouter 
	 * @param qui int savoir si cela viens d'une location ou d'une carte chance.
	 */
	public void gagnerArgent(int somme,int qui) {
		this.argent.setValue(this.argent.getValue()+somme);
		stats.setGagne(stats.getGagne()+somme);
		this.capital.setValue(this.capital.getValue()+somme);
		if(qui==1) {
			stats.setGagneLocation(stats.getGagneLocation()+somme);
		}else if(qui==2){
			stats.setGagneChances(stats.getGagneChances()+somme);
		}
	}


	/**
	 * Méthode gagnerArgent permet d'ajouter l'argent gagner en diminuant argent et capital et en augmentant les variables perdu et PerduLocation ou PerduChance de la class Statistiques  
	 * @param somme int à enlever
	 * @param qui int savoir si cela viens d'une location ou d'une carte chance.
	 */
	public void perdreArgent(int somme, int qui) {
		this.argent.setValue(this.argent.getValue()-somme);
		stats.setPerdu(stats.getPerdu()+somme);
		this.capital.setValue(this.capital.getValue()-somme);
		if(qui==1) {
			stats.setPerduLocation(stats.getPerduLocation()+somme);
		}else if(qui==2){
			stats.setPerduChances(stats.getPerduChances()+somme);
		}
	}

	public void piocherCarteChance() {
	}

	/**
	 * Méthode lancerDe permet de mettre dans les variable de1 et de2 deux chiffres au hasard entre 0 et 4 et entre 1 et 4
	 */
	public void lancerDe() {
		Random r = new Random();
		de1 = r.nextInt(5);
		de2 = r.nextInt(4)+1;
	}

	public int getMin() {
		int maxLoyer = 0;
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()) {
			if(c instanceof Salle) {
				if(((Salle)c).estPossedee()&&((Salle)c).getProprio()!=this&&((Salle) c).getLoyer(((Salle) c).getNbMateriels())>maxLoyer)maxLoyer=((Salle) c).getLoyer(((Salle) c).getNbMateriels());
			}else if(c instanceof Propriete) {
				if(((Propriete)c).estPossedee()&&((Propriete)c).getProprio()!=this&&((Propriete) c).getLoyer(0)>maxLoyer)maxLoyer=((Propriete) c).getLoyer(0);
			}
		}
		if(maxLoyer>300&&this.getArgentBis()>maxLoyer)return maxLoyer;
		return 300;
	}


	public void quitterPartie() {
		MonopolyIUT.getPartie().retirerJoueur(this);
	}




}


