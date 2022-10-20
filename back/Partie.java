package back;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import view.ActionView;
import view.FinPartieView;
import view.GameView;
import view.JoueurClassementView;
import view.MonopolyIUT;


/**
 * Cette classe permet de gerer toutes les configurations d'une partie.
 */

public class Partie {


	//variables
	private long duree;
	private int tempsTour;
	private long debutTour;
	private int argentDep;
	private int nbTours;
	private TypesPartie typePartie;

	public enum ReglesAdditionnelles{
		COUP_DU_SORT,
		CRISE_IMMOBILIER,
		REDISTRIBUTION_RICHESSES
	}

	public enum TypesPartie{
		MONOPOLE,
		MORT_SUBITE,
		INVESTISSEUR,
		BATISSEUR
	}

	private ArrayList<ReglesAdditionnelles> reglesAdd;
	private ArrayList<Joueur> lesJoueurs;
	// Listes pour le classement
	private ObservableList<JoueurClassementView> listejoueurs = FXCollections.observableArrayList();
	private SortedList<JoueurClassementView> joueurs = new SortedList<JoueurClassementView>(listejoueurs);

	private Plateau plateau;
	private GameView view;



	//constructeur
	/**
	 * Constructeur de la partie avec l'instanciation des variables d'instance et 
	 * la liste des joueurs.
	 * @param argentD l'argent qu'on a au début de la partie
	 * @param argentCD l'argent que l'on touche quand on passe par la case départ
	 * @param argentSCD l'argent que l'on touche quand on est sur la case départ
	 * @param taille la taille du plateau choisi
	 * @param tempsT le temps pour jouer un tour en secondes
	 * @param tours le nombre de tours minimum
	 */
	public Partie( int argentD, int argentCD, int argentSCD, int taille, int tempsT, int tours) {
		this.duree = Date.from(Instant.now()).getTime();
		this.tempsTour = tempsT;
		this.argentDep = argentD;
		this.nbTours = tours+1;
		this.lesJoueurs = new ArrayList<Joueur>();
		plateau = new Plateau(taille, argentCD, argentSCD);
		this.reglesAdd=new ArrayList<ReglesAdditionnelles>();
	}

	public boolean hasRegleAdd(ReglesAdditionnelles regle) {
		return reglesAdd.contains(regle);
	}

	public void ajouterRegle(ReglesAdditionnelles regle) {
		reglesAdd.add(regle);
	}

	public GameView getView() {
		return view;
	}

	public void setView(GameView view) {
		this.view=view;
	}
	
	//getters and setters
	public long getDuree() {
		return duree;
	}

	public int getTempsTour() {
		return tempsTour;
	}

	public void setTempsTour(int tempsTour) {
		this.tempsTour = tempsTour;
	}

	public long getDebutTour() {
		return debutTour;
	}

	public Etapes getEtape() {
		return etape;
	}

	public void setEtape(Etapes etape) {
		this.etape = etape;
	}

	public int getArgentDep() {
		return argentDep;
	}

	public void setArgentDep(int argentDep) {
		this.argentDep = argentDep;
	}

	public int getNbTours() {
		return nbTours;
	}

	public void setNbTours(int nbTours) {
		this.nbTours = nbTours;
	}

	public TypesPartie getTypePartie() {
		return typePartie;
	}

	public void setTypePartie(TypesPartie typePartie) {
		this.typePartie = typePartie;
	}

	public ArrayList<Joueur> getLesJoueurs() {
		return lesJoueurs;
	}

	public void setLesJoueurs(ArrayList<Joueur> lesJoueurs) {
		this.lesJoueurs = lesJoueurs;
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public ObservableList<JoueurClassementView> getListeJoueurs(){
		return listejoueurs;
	}

	// Donne la liste du classement des joueurs.
	public SortedList<JoueurClassementView> getSortedListeJoueurs(){
		return joueurs;
	}


	//methodes
	/**
	 * Test l'existence d'un Joueur dans l'ArrayList de Joueur.
	 * @param joueur Joueur que l'on souhaite teste.
	 * @return Vrai si le client se trouve deja dans la liste, faux sinon.
	 */
	public boolean contientJoueur(Joueur joueur) {
		return lesJoueurs.contains(joueur);
	}

	/**
	 * Permet d'ajouter un Joueur à l'ArrayList, sans faire de test.
	 * @param joueur
	 */
	private void ajouterJoueur(Joueur joueur) {
		lesJoueurs.add(joueur);
		listejoueurs.add(joueur.getClassement());
	}

	/**
	 * Permet d'ajouter un Joueur a l'ArrayList de Joueur.
	 * @param joueur Joueur que l'on souhaite ajouter.
	 */
	public void ajouterUnJoueur(Joueur joueur) {
		if(joueur!=null&&!contientJoueur(joueur))ajouterJoueur(joueur);
	}

	int nbDouble;
	boolean unDouble;
	Joueur joueurDuTour;
	private Etapes etape = Etapes.DEBUT_TOUR;
	public enum Etapes{
		DEBUT_TOUR, // Règles additionnelles
		AVANT_DE, // cartes chance
		AVANT_AVANCER, // Double => Prison ?
		AVANT_CASE, // Piscine
		APRES_CASE, // UGO
		FIN_TOUR, // Test carte chance => Si oui => pas étape d'après // Joueur suivant, test fin de partie // test fin de tour => De
		FIN_PARTIE // Fin de la partie
	}

	public void retirerJoueur(Joueur j) {
		for(Case c : plateau.getListePlateau()) {
			if(c instanceof Propriete && ((Propriete) c).estPossedee() && ((Propriete) c).getProprio().equals(j)) {
				if(c instanceof Salle)while(((Salle) c).estPossedee())((Salle)c).retirerMateriel();
				if(c instanceof Service)((Service)c).vendre();
				if(c instanceof CentreCommercial)((CentreCommercial)c).vendre();
			}
		}
		plateau.getListePlateau().get(0).getView().getChildren().add(j.getPion());
		j.getPion().setVisible(false);
		j.getPion().setMouseTransparent(true);
		j.getPion().setTranslateX(0);
		j.getPion().setTranslateY(0);
		lesJoueurs.remove(j);
		listejoueurs.remove(j.getClassement());
		view.afficherFiche(j.getFiche());
		view.retirerResumeJoueur();
		if(testFinPartie()==-1) {
			etape=Etapes.FIN_PARTIE;
			jouerEtape();
			return;
		}
		if(j.equals(joueurDuTour)) {
			j.getPion().interrompre();
			joueurDuTour=lesJoueurs.get((lesJoueurs.indexOf(joueurDuTour)+1)%lesJoueurs.size());
			while(joueurDuTour.getArgentBis()<0) {
				joueurDuTour=lesJoueurs.get((lesJoueurs.indexOf(joueurDuTour)+1)%lesJoueurs.size());
			}
			etape=Etapes.DEBUT_TOUR;
			jouerEtape();
		}
	}

	public void jouerEtape() {
		Platform.runLater(()->{
			switch(etape) {
			case FIN_PARTIE:
				ActionView.stopChrono();
				new FinPartieView().show();
				MonopolyIUT.getStage().setFullScreen(true);
				break;
			case DEBUT_TOUR:
				if(joueurDuTour==null)joueurDuTour=lesJoueurs.get(0);
				if(testFinPartie()==1)break;
				if(testFinPartie()==-1) {
					etape=Etapes.FIN_PARTIE;
					jouerEtape();
					break;
				}
				nbDouble = 0;
				if(debutTour>=0)debutTour=Date.from(Instant.now()).getTime()+tempsTour*1000;

				if(typePartie==TypesPartie.MONOPOLE && joueurDuTour==lesJoueurs.get(0)) {
					if(nbTours>0)nbTours--;
					if(nbTours==0) {
						etape=Etapes.FIN_PARTIE;
						jouerEtape();
					}else {
						if(hasRegleAdd(ReglesAdditionnelles.COUP_DU_SORT)) {
							etape=Etapes.AVANT_DE;
							unDouble=true;
							plateau.getCase("?").actionCase(joueurDuTour);
						}else {
							etape=Etapes.AVANT_DE;
							jouerEtape();
						}
					}
				}else {
					if(hasRegleAdd(ReglesAdditionnelles.REDISTRIBUTION_RICHESSES)&&joueurDuTour==lesJoueurs.get(0)) {
						Joueur jMax = joueurDuTour;
						for (Joueur joueur : lesJoueurs) {
							if(joueur.getArgentBis()>=jMax.getArgentBis()) {
								jMax=joueur;
							}
						}
						int max=jMax.getArgentBis();
						max=(int)(max*0.1);
						jMax.perdreArgent(max,0);
						for (Joueur joueur : lesJoueurs) {
							if(!joueur.equals(jMax)) {
								joueur.gagnerArgent(max/(lesJoueurs.size()-1), 0);
							}
						}
					}

					//on test les cartes chances
					for (CarteChance c : joueurDuTour.getChances()) {
						if(c.getNbEffetTour()>=0) {
							//new Chance("", 0, "#000000").actionCase(joueurDuTour);
							c.setNbEffetTour(c.getNbEffetTour()-1);
						}
					}

					if (joueurDuTour.getModificateur().getPasserTour()>0) {
						joueurDuTour.getModificateur().setPasserTour(joueurDuTour.getModificateur().getPasserTour()-1);
						etape=Etapes.FIN_TOUR;
						jouerEtape();
						break;
					}else {
						if(hasRegleAdd(ReglesAdditionnelles.COUP_DU_SORT)) {
							unDouble=true;
							etape=Etapes.AVANT_DE;
							plateau.getCase("?").actionCase(joueurDuTour);
						}else {
							etape=Etapes.AVANT_DE;
							jouerEtape();
						}
					}
				}
				break;
			case AVANT_DE:
				if(testFinPartie()==1)break;
				if(testFinPartie()==-1) {
					etape=Etapes.FIN_PARTIE;
					jouerEtape();
					break;
				}
				etape=Etapes.AVANT_AVANCER;
				//lance les des
				if(joueurDuTour.isBot()) {
					ActionView.avancer(joueurDuTour);
				}else {
					ActionView.setActionAvancer(joueurDuTour);
				}
				break;
			case AVANT_AVANCER:
				if(testFinPartie()==1)break;
				if(testFinPartie()==-1) {
					etape=Etapes.FIN_PARTIE;
					jouerEtape();
					break;
				}
				boolean deplacer=true;
				unDouble=false;
				// faire les tests si le joueur se trouve dans la salle de ds
				if(SalleDeDS.joueurEnDS(joueurDuTour)) {
					SalleDeDS.tentativeEvasion(joueurDuTour);
					if(joueurDuTour.getChances().size()>0) { //si il a la carte chance il l'utilise
						for (CarteChance c : joueurDuTour.getChances()) {
							if(c.getNom()=="Sortir de DS") {
								SalleDeDS.supprimerJoueurEnDs(joueurDuTour);
								joueurDuTour.getChances().remove(c);
							}
						}
					}else if(joueurDuTour.getDe1()==joueurDuTour.getDe2()) { //si il fait un double il sort de la salle sans probleme
						SalleDeDS.supprimerJoueurEnDs(joueurDuTour);
					}else if(SalleDeDS.getNbToursDS(joueurDuTour)==2){ //sinon il paye pour sortir
						joueurDuTour.perdreArgent(100,0);
						SalleDeDS.supprimerJoueurEnDs(joueurDuTour);
					}else {
						deplacer=false;
					}
				}else if(joueurDuTour.getDe1()==joueurDuTour.getDe2()) { // Si le joueur a fait un double, incrémente nbDouble
					nbDouble++;
					unDouble=true;
				}

				// On verifie si on a fait 3 doubles et on va en prison
				if(nbDouble == 3) {
					SalleDeDS.ajouterJoueurEnDS(joueurDuTour);
					deplacer=false;
					nbDouble = 0;
					unDouble=false;	
					joueurDuTour.getPion().seRendre("Salle de DS");
				}else {
					etape=Etapes.AVANT_CASE;
					if(deplacer) {
						joueurDuTour.getPion().seDeplacer(joueurDuTour.getDe1()+joueurDuTour.getDe2());		
					}else {
						jouerEtape();
					}
				}

				break;
			case AVANT_CASE:
				if(testFinPartie()==1)break;
				if(testFinPartie()==-1) {
					etape=Etapes.FIN_PARTIE;
					jouerEtape();
					break;
				}
				Case caseAVenir = getPlateau().getListePlateau().get(joueurDuTour.getPion().getPosition());
				if(joueurDuTour.getChances().size()>0) { //si il a la carte chance il l'utilise Réduction,Sécher un cours,UGO,...
					for (CarteChance c : joueurDuTour.getChances()) {
						if(c.getNom()=="Sécher un cours"&& caseAVenir instanceof Propriete && !joueurDuTour.equals(((Propriete)caseAVenir).getProprio())) {
							// Réduction prochaine loyer
							joueurDuTour.getModificateur().setReduireCout(1);
						}else if(c.getNom()=="Réduction"&& caseAVenir instanceof Propriete && !joueurDuTour.equals(((Propriete)caseAVenir).getProprio())) {
							// Réduction prochaine loyer
							joueurDuTour.getModificateur().setReduireCout(0.5 /*Pourcentage aléatoire*/);
						}
					}
				}
				etape=Etapes.APRES_CASE;
				caseAVenir.actionCase(joueurDuTour);

				break;
			case APRES_CASE:
				if(testFinPartie()==1)break;
				if(testFinPartie()==-1) {
					etape=Etapes.FIN_PARTIE;
					jouerEtape();
					break;
				}
				Case casePresent = getPlateau().getListePlateau().get(joueurDuTour.getPion().getPosition());
				for (CarteChance c : joueurDuTour.getChances()) {
					if(c.getNom()=="UGO"&&(!(casePresent instanceof Propriete)||(casePresent instanceof Propriete && !joueurDuTour.equals(((Propriete)casePresent).getProprio())))) {
						// Amélioration
						if(joueurDuTour.isBot()) {
							ActionView.ameliorer(joueurDuTour, joueurDuTour.getMin());
						}else {
							ActionView.setActionAmeliorer(joueurDuTour, 0);
						}
					}
				}
				etape=Etapes.FIN_TOUR;
				jouerEtape();
				break;
			case FIN_TOUR:
				if(testFinPartie()==1)break;
				if(testFinPartie()==-1) {
					etape=Etapes.FIN_PARTIE;
					jouerEtape();
					break;
				}
				if(typePartie==TypesPartie.MORT_SUBITE&&joueurDuTour.getArgentBis()<0) {
					etape=Etapes.FIN_PARTIE;
					jouerEtape();
				}else {
					if(unDouble) {
						etape=Etapes.AVANT_AVANCER;
						// Position avance 
						if(joueurDuTour.isBot()) {
							ActionView.avancer(joueurDuTour);
						}else {
							ActionView.setActionAvancer(joueurDuTour);
						}	
					}else if(typePartie==TypesPartie.MONOPOLE && MonopolyIUT.getPartie().getNbTours()==0){
						etape=Etapes.FIN_PARTIE;
						jouerEtape();
					}else {
						joueurDuTour=lesJoueurs.get((lesJoueurs.indexOf(joueurDuTour)+1)%lesJoueurs.size());
						while(joueurDuTour.getArgentBis()<0) {
							joueurDuTour=lesJoueurs.get((lesJoueurs.indexOf(joueurDuTour)+1)%lesJoueurs.size());
						}
						etape=Etapes.DEBUT_TOUR;
						jouerEtape();
					}
				}
				break;
			}
		});
	}
	
	private int testFinPartie() {
		if(joueurDuTour.getArgentBis()<0&&!joueurDuTour.getMatieres().isEmpty()) {
			if(joueurDuTour.isBot()) {
				ActionView.vendre(joueurDuTour, joueurDuTour.getArgentBis()*-1, null, null);
			}else {
				ActionView.setActionVendre(joueurDuTour, joueurDuTour.getArgentBis()*-1, null, null);
			}
			return 1;
		}
		if(joueurDuTour.getArgentBis()<0)MonopolyIUT.getPartie().getLesJoueurs().remove(joueurDuTour);
		if(lesJoueurs.size()<2)return -1;
		return 0;
	}


	// M�thodes pour le classement
	public void updateClassement() {
		listejoueurs.sort(joueurs.getComparator());
	}

	public void setTrieArgent() {
		joueurs.setComparator(new Comparator<JoueurClassementView>(){
			@Override
			public int compare(JoueurClassementView o1, JoueurClassementView o2) {
				return o2.getJoueur().getArgent().getValue().compareTo(o1.getJoueur().getArgent().getValue());
			}
		});
		for(JoueurClassementView g : listejoueurs)g.setClassement("Argent");
		updateClassement();
	}

	public void setTriePropriete() {
		joueurs.setComparator(new Comparator<JoueurClassementView>(){
			@Override
			public int compare(JoueurClassementView o1, JoueurClassementView o2) {
				return o2.getJoueur().getNbProprietes().getValue().compareTo(o1.getJoueur().getNbProprietes().getValue());
			}
		});
		for(JoueurClassementView g : listejoueurs)g.setClassement("Proprietes");
		updateClassement();
	}

	public void setTrieCapital() {
		joueurs.setComparator(new Comparator<JoueurClassementView>(){
			@Override
			public int compare(JoueurClassementView o1, JoueurClassementView o2) {
				return o2.getJoueur().getCapital().getValue().compareTo(o1.getJoueur().getCapital().getValue());
			}
		});
		for(JoueurClassementView g : listejoueurs)g.setClassement("Capital");
		updateClassement();
	}




}
