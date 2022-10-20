package back;

import java.util.ArrayList;
import java.util.Random;

import view.ActionView;
import view.ChanceView;
import view.FicheChance;
import view.MonopolyIUT;

/**
 * Classe représentant la case chance.
 * Cette classe étend la Classe Case.
 * 
 * @see Case
 *
 */
public class Chance extends Case {
	/**
	 * Tableau de valeurs comprises entre 50 et 250, avec un écart de 50 entre chacune d'entre elles. 
	 */
	int tabValeursPossiblesAlea50250[];
	/**
	 * Tableau de valeurs comprises entre 100 et 200, avec un écart de 50 entre chacune d'entre elles. 
	 */
	int tabValeursPossiblesAlea100200[];
	/**
	 * Tableau permettant de savoir si un joueur va reculer ou avancer. (déterminer aléatoirement).
	 */
	int tabAvancerReculer[];
	
	/**
	 * Valeur aléatoire.
	 */
	int n = 0;
	/**
	 * Valeur aléatoire.
	 */
	int m = 0;
	/**
	 * Valeur aléatoire. 
	 */
	int positifNega = 0;
	/**
	 * Valeur aléatoire.
	 */
	int valeurTourDeBase = 0;
	/**
	 * Valeur aléatoire.
	 */
	int aleaMatiere = 0;
	/**
	 * Valeur aléatoire.
	 */
	int aleaSalle = 0;
	/**
	 * Valeur aléatoire.
	 */
	int aleaAvancerRecul = 0;
	/**
	 * Valeur aléatoire.
	 */
	int aleaReduc = 0;
	/**
	 * Carte chance tirée par un joueur. 
	 */
	CarteChance c;
	/**
	 * ArrayList de toutes les Propriété de la partie. 
	 */
	ArrayList<Propriete> listeProprietes;
	/**
	 * ArrayList de toutes les cases de la partie. 
	 */
	ArrayList<Case> listeCase;
	
	/**
	 * Constructeur de la case chance.
	 * 
	 * @see Case
	 * 
	 * @param nom
	 * 	Le nom de la case en String.
	 * @param id
	 *	L'identifiant de la case en int.
	 *@param couleur
	 *	Couleur du joueur
	 */
	public Chance(String nom, int id, String couleur) {
		super(nom, id, couleur);
		fiche=new FicheChance("Chance","Piochez une carte chance.");
		view=new ChanceView(this);
		
		//Pour obtenir les valeurs aléatoires possibles		
		//Attribution des valeurs possibles
		this.tabValeursPossiblesAlea50250 = new int[] {50,100,150,200,250};
		this.tabValeursPossiblesAlea100200 = new int[] {100,150,200};
		this.tabAvancerReculer= new int [] {-3,-2,-1,0,1,2,3};
		
		this.listeProprietes = new ArrayList<Propriete>();
		this.listeCase = new ArrayList<Case>();
	}
	
	/**
	 * Getter de la vue d'une case chance.
	 * 
	 * @return La vue de la case chance.
	 */
	public ChanceView getView() {
		return (ChanceView)view;
	}
	
	/**
	 * Getter d'une carte chance.
	 * 
	 * @return Une carte Chance.
	 */
	public CarteChance getCarteChance() {
		return c;
	}
	
	/**
	 * Setter d'une carte chance.
	 * 
	 * @param chance La nouvelle carte chance.
	 */
	public void setCarteChance(CarteChance chance) {
		c=chance;
	}
	
	/**
	 * Ajout d'un carte chance à la liste d'un joueur.
	 * 
	 * 
	 * 
	 * @return Carte Piochée
	 */
	public CarteChance piocherCarte() {
		return new CarteChance();
	}

	/**
	 * Méthode qui permet de réaliser les actions des cartes chance faisant perdre ou gagner de l'argent à des joueurs. 
	 * Celle-ci change uniquement les textes des cartes chance, et attribu les bonnes valeurs à CarteChance.plusMoinArgent().
	 * 
	 * @param j Le joueur qui tire la carte chance.
	 * 
	 */
	public void gagnerPerdreArgentTexte(Joueur j) {
		//Génération de nombre aléatoire pour les indices des tableaux
		this.n = (int)(Math.random() * 5); //obtiens un entier compris entre 0 et 4 utilisé pour les valeurs aléatoires de ce tableau :tabValeursPossiblesAlea50250
		this.m = (int)(Math.random() * 3); //obtiens un entier compris entre 0 et 3 utilisé pour les valeurs aléatoires de ce tableau :tabValeursPossiblesAlea100200
		this.positifNega = (int)(Math.random() * 2); //obtiens un entier entre 0 et 1, si c'est 0 alors notre résultat est négatif, autrement il est positif
		//action de la dernière carte tirée 
		if (!this.c.isImpactProTour()) {
			//avoir les cartes avec du texte à remplacer 
			//définir les valeurs à payer
			if (this.c.getTexte().contains("ALEACHIFFRE")) {
				c.setTexte(c.getTexte().replaceAll("ALEACHIFFRE", String.valueOf(this.tabValeursPossiblesAlea100200[this.m])));
				this.c.setPlusMoinsArgent(this.tabValeursPossiblesAlea100200[this.m]*-1);
			}
			if (this.c.getTexte().contains("nb_materiel")) {
				//carte chance n°12
				c.setTexte(c.getTexte().replaceAll("nb_materiel", String.valueOf(j.getNbMaterielTotal())));
				this.c.setPlusMoinsArgent(this.tabValeursPossiblesAlea100200[this.m]*j.getNbMaterielTotal()*-1);
			}
			if (this.c.getTexte().contains("ALEACHIFRE2")) {
				c.setTexte(c.getTexte().replaceAll("ALEACHIFRE2", String.valueOf(this.tabValeursPossiblesAlea50250[this.n])));
				this.c.setPlusMoinsArgent(this.tabValeursPossiblesAlea50250[this.n]);
			}
			if (this.c.getTexte().contains("PRIXPOSSESSION")) {
				int pourcentage=0;
				System.out.println(j.getCapital().getValue().intValue());
				//définir les valeurs à payer en fonction des possessions du joueur (carte n°13)
				if (j.getCapital().getValue().intValue()<800){ 
					//si la valeur de ses possessions sont inférieures à 800, ses impôts valent 20% du prix de ses possessions
					this.c.setPlusMoinsArgent((int)(j.getCapital().getValue().intValue()*-0.1));
					pourcentage=10;
				}else if (j.getCapital().getValue().intValue()>2000){ 
					//si la valeur de ses possessions sont supérieures à 2000, ses impôts valent 45% du prix de ses possessions
					this.c.setPlusMoinsArgent((int)(j.getCapital().getValue().intValue()*-0.25));
					pourcentage=25;
				}else {
					//si la valeur de ses possessions comprises entre 800 et 2000, ses impôts valent 30% du prix de ses possessions
					this.c.setPlusMoinsArgent((int)(j.getCapital().getValue().intValue()*-0.15));
					pourcentage=15;
				}
				c.setTexte(c.getTexte().replaceAll("PRIXPOSSESSION", String.valueOf(j.getCapital().getValue().intValue())));
				c.setTexte(c.getTexte().replaceAll("XXX", String.valueOf(pourcentage)));
			}
			if (this.c.getTexte().contains("gagner/perdre")) {
				if (positifNega==0) {
					c.setTexte(c.getTexte().replaceAll("gagner/perdre", String.valueOf("perdre")));
					this.c.setPlusMoinsArgent(this.tabValeursPossiblesAlea50250[this.n]*-1);
				}else {
					c.setTexte(c.getTexte().replaceAll("gagner/perdre", String.valueOf("gagner")));
					this.c.setPlusMoinsArgent(this.tabValeursPossiblesAlea50250[this.n]);
				}
			}
		}else {
			this.valeurTourDeBase = c.getNbEffetTour();
			//carte qui ont un impact sur 2+ tours
			//effectuer l'attribution des textes et valeurs une seule fois
			if (this.c.getNbEffetTour()==this.valeurTourDeBase) {
				if (this.c.getTexte().contains("ALEACHIFFRE")) {
					j.getModificateur().setAlternance(this.tabValeursPossiblesAlea100200[this.m]);
					c.setTexte(c.getTexte().replaceAll("ALEACHIFFRE", String.valueOf(this.tabValeursPossiblesAlea100200[this.m])));
					this.c.setPlusMoinsArgent(this.tabValeursPossiblesAlea100200[this.m]);
				}
			}	
		}	
	}
	
	/**
	 * Méthode qui permet de remplacer le texte des cartes chance ayant un impact sur la position du joueur. 
	 * 
	 * @param j Joueur qui a tiré la carte chance. 
	 * 
	 * 
	 */
	public void avancerReculerTexte(Joueur j){		
		
		for(Case c : MonopolyIUT.getPartie().getPlateau().getListePlateau()){
			if(c instanceof Salle)listeProprietes.add((Salle)c);
		}
		
		//Génération de nombre aléatoire pour les indices des listes
		this.aleaMatiere = (int)(Math.random() * this.listeProprietes.size()-1); //obtiens un entier entre 0 et le nombre de matiere de la partie, -1 car on commence à 0
		this.aleaSalle = (int)(Math.random() *  MonopolyIUT.getPartie().getPlateau().getTaille()); //obtiens un entier entre 0 et le nombre de salle de la partie, -1 car on commence à 0
		
		
		//action de la dernière carte tirée 
		if (!this.c.isImpactProTour()) {
			if (c.getTexte().contains("ALEACASE")) {
				//carte n°18
				c.setTexte(c.getTexte().replaceAll("ALEACASE", String.valueOf(MonopolyIUT.getPartie().getPlateau().getListePlateau().get(this.aleaSalle).getNom())));
				this.c.setNom(MonopolyIUT.getPartie().getPlateau().getListePlateau().get(this.aleaSalle).getNom());
				System.out.println(this.aleaSalle);
				System.out.println("Matière ou se rend le joueur : "+MonopolyIUT.getPartie().getPlateau().getListePlateau().get(this.aleaSalle).getNom());
				this.c.setSalle(MonopolyIUT.getPartie().getPlateau().getListePlateau().get(this.aleaSalle).getNom());
			}
			if (c.getTexte().contains("ALEAMATIERE")) {
				//carte n°33
				c.setTexte(c.getTexte().replaceAll("ALEAMATIERE", String.valueOf(this.listeProprietes.get(aleaMatiere))));
				this.c.setNom(this.listeProprietes.get(aleaMatiere).getNom());
				System.out.println("Salle ou se rend le joueur : "+this.listeProprietes.get(aleaMatiere).getNom());
				this.c.setSalle(this.listeProprietes.get(aleaMatiere).getNom());
				j.getModificateur().setSensDeplacement(false);
			}
			if (c.getNum()==29) {
				//carte n°29
				j.getModificateur().setToucheBourse(0);
				SalleDeDS.ajouterJoueurEnDS(j);
			}
			if (c.getNum()==35) {
				//carte n°35
				j.getModificateur().setToucheBourse(2);
				j.getModificateur().setRejoue(true);
			}
		}else {
			System.out.println("impact le pro tour");
			if (c.getNum()==30) {
				//carte n°30
				j.getModificateur().setPasserTour(c.getNbEffetTour());;
			}
		}
	}
	
	/**
	 * Méthode qui ajoute le texte aux cartes qui mofidient les déplacements du joueur.
	 * 
	 * @param j Joueur qui a pioché la carte chance. 
	 */
	public void lesModificateursTexte (Joueur j) {
		this.aleaAvancerRecul = (int)(Math.random() * 7); //obtiens un entier compris entre 0 et 4 utilisé pour les valeurs aléatoires de ce tableau :tabValeursPossiblesAlea50250

		if (c.getTexte().contains("ALEACASES")) {
			c.setTexte(c.getTexte().replaceAll("ALEACASES", String.valueOf(this.tabAvancerReculer[this.aleaAvancerRecul])));
			this.c.setPlusMoinsCase(this.tabAvancerReculer[this.aleaAvancerRecul]);
			System.out.println(this.tabAvancerReculer[this.aleaAvancerRecul]);
			if (this.tabAvancerReculer[this.aleaAvancerRecul]>0) {
				c.setTexte(c.getTexte().replaceAll("MOUVEMENT", String.valueOf("avancez")));
				System.out.println(c.toString());
			}else if(this.tabAvancerReculer[this.aleaAvancerRecul]<0){
				c.setTexte(c.getTexte().replaceAll("MOUVEMENT", String.valueOf("reculez")));
				System.out.println(c.toString());
			}else {
				c.setTexte("Vous ne reculez pas. Mais vous n'avancez pas non plus.");
				System.out.println(c.toString());
			}
		}
	}
	
	/**
	 * Méthode qui attribu le bons texte aux cartes pouvant être conservée.
	 * 
	 * @param j Joueur qui a tiré la carte chance. 
	 */
	public void cartesConserveeTexte(Joueur j) {
		if (c.isConservee()) {
			if (c.getTexte().contains("ALEASALLE")) {
				//TODO vérifier que ArrayList et nbAlea sont bien set grâce à avancerReculerTexte(Joueur j)
				c.setTexte(c.getTexte().replaceAll("ALEASALLE", String.valueOf(this.listeProprietes.get(aleaMatiere))));
				this.c.setNom(this.listeProprietes.get(aleaMatiere).getNom());
				System.out.println("Salle ou se rend le joueur : "+this.listeProprietes.get(aleaMatiere).getNom());
				this.c.setSalle(this.listeProprietes.get(aleaMatiere).getNom());
				
			}
		}
	}
	
	
	/**
	 * Méthode qui ajoute/retire l'argent d'un joueur ayant tiré une carte chance. 
	 * 
	 * @param j Joueur ayant tiré la carte Chance. 
	 */
	public void gagnerPerdreArgent(Joueur j) {
		//pour ajouter une somme définie pendant 1 tour
		if (this.c.getPlusMoinsArgent()>0)
			j.gagnerArgent(this.c.getPlusMoinsArgent(), 2); //gagner de l'argent
		else if (this.c.getPlusMoinsArgent()<0)
			j.perdreArgent(this.c.getPlusMoinsArgent()*-1, 2); //perdre de l'argent	
	}
	
	/**
	 * Méthode permettant d'envoyer un joueur à une salle précise.
	 * 
	 * @param j Joueur ayant tiré la carte chance. 
	 */
	public void avancerReculer(Joueur j) {
		j.getPion().seRendre(c.getSalle());
	}
	
	/**
	 * Méthode qui permet de déplacer un joueur.
	 * 
	 * @param j Joueur qui a tiré la carte chance. 
	 */
	public void seDeplacer(Joueur j) {
		if(c.getNum()==14) {
			j.getPion().seDeplacer(c.getPlusMoinsCase());
		}
	}
	
	/**
	 * Méthode permettant de mofifier les dés des joueurs, d'impacter leur position, de leur proposer un choix, 
	 * 
	 * @param j Joueur qui a tiré la carte chance.
	 */
	public void lesModificateurs(Joueur j) {
		if (c.isChoix()) {
			//TODO voir comment implémenter un choix dans l'interface graphique et le recup ici pour soustraire l'argent ou bien perdre le proTour
			
		}
		else if (c.isSautTour()&&c.getNum()!=31) {
			j.getModificateur().setPasserTour(c.getNbEffetTour());
		}
		else if (c.getNbEffetTour()>0) {
			j.getModificateur().setDes(j.getModificateur().getDes()+c.getPlusMoinsCase());
		}
	}
	
	/**
	 * Méthode qui modifie le temps du tour du joueur.
	 * 
	 * @param j Joueur ayant tiré la carte chance.
	 */
	public void tempsTour(Joueur j) {
		if (c.getNum()==25||c.getNum()==26){
			j.getModificateur().setTemps(c.getTempsTour());
			
			//Génération de nombre aléatoire pour les indices des listes
			this.aleaMatiere = (int)(Math.random() * this.listeProprietes.size()-1); //obtiens un entier entre 0 et le nombre de matiere de la partie, -1 car on commence à 0
			
			
		}
	}
	
	/**
	 * Méthode qui permet de changer la valeur de modificateur du joueur sur le nombre de DS. 
	 * 
	 * @param j Joueur qui a pioché la carte chance. 
	 */
	public void carteConservee(Joueur j) {
		if (c.isConservee()) {
			if (c.getNum()==37||c.getNum()==38) {
				j.getModificateur().setDs(j.getModificateur().getDs()+1);
			}
			if (c.isImpactProAchat()) {
				//carte n°39
				Random rand = new Random();
				this.aleaReduc = rand.nextInt((3 - 1) + 1) + 1;
				int tmp = this.aleaReduc*10;
				//TODO j'ai rentré un int qui correspond à 10, 20 ou 30 soit un pourcentage si on /100
				j.getModificateur().setReduireCout(tmp/100.0);
			}
			if (c.isImpactPayementLoyer()) {
				//carte n°40
				//TODO ajouter dans modificateur un truc pour ne pas payer une prochaine case --> ajouter un test avant de payer le loyer d'une case
			}
		}
	}
	
	
	/**
	 * Méthode qui permet de lancer l'éxécution des cartes.
	 * 
	 * @param j Joueur qui a tiré la carte chance.
	 */
	public void executeLaBonneCarte(Joueur j) {
        //si on ne conserve pas la carte, on éxécute aussitôt ses actions
        if (!this.c.isConservee()) {
            //carte qui font gagner/perdre de l'argent
            if (this.c.getPlusMoinsArgent()!=0) {
                this.gagnerPerdreArgent(j);
                MonopolyIUT.getPartie().jouerEtape();
            }
            if (!this.c.getSalle().contains("Non")) {
                this.avancerReculer(j);
            }
            if (c.getNum()==14) { //TODO carte avec MOUVEMENT
            	this.seDeplacer(j);
            }
            //TODO ajouter appel lesModuficateurs, tempsTour, carteConservee
        }else {
        	this.carteConservee(j); //TODO vérifier que ca fonctionne
            MonopolyIUT.getPartie().jouerEtape();
        } 
    }
	
	
	//MonopolyIUT.getPartie().getLesJoueurs()
	//MonopolyIUT.getPartie().getPlateau().getTaille()
	
	/**
	 * Action de la case.
	 * Pour la case chance le joueur tire une carte chance.
	 * 
	 */
	public void actionCase(Joueur j) {
		c=piocherCarte();
		if (c.isConservee()) {
			j.ajouterUneCarteChance(c);
		}
		this.gagnerPerdreArgentTexte(j);
		this.lesModificateursTexte(j);
		this.avancerReculerTexte(j);
		this.cartesConserveeTexte(j);
		//Appeler le front
		if (!j.isBot()) {
			  ActionView.setActionChance(this, j);  
			  //this.executeLaBonneCarte(j);
		}else {    
			  ActionView.chance(this, j);
		}		
	}
}
