package back;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Classe représentant une carte chance.
 * Cette classe permet de créer dynamiquement via la lecture d'un fichier des cartes chance. 
 * De plus, lors de son appel un système de probabilité est mit en place, afin de simuler le tirage de carte dans un paquet de carte. 
 * Une carte chance est créer à chaque fois qu'un joueur tombe sur une case Chance dans une partie de MonopolyIUT. 
 * 
 * @see Chance
 *
 */
public class CarteChance {
	
	final String CARTESCHANCE=
			"numéro;contenu;conservable;impactProTour;plusMoinsArgent;NbEffetTour;plusMoinsCases;sauteSonTour;choix;tempsTour;envoieVersCase;impactProAchat;impactPayementLoyer;impactAchatMateriel;impactToutLesJoueurs;probabilité;nom;salleDestination\n" + 
			"1;C'est votre jour de chance, vous trouvez un billet de 50€ par terre.;0;0;50;0;0;0;0;0;0;0;0;0;0;4;Gagner argent;Non\n" + 
			"2;Vous gagnez un concours de programmation, gagnez 50€.;0;0;50;0;0;0;0;0;0;0;0;0;0;4; Gagner argent;Non\n" + 
			"3;Vous trouvez un stage, gagnez 100€.;0;0;100;0;0;0;0;0;0;0;0;0;0;3;Gagner argent;Non\n" + 
			"4;Vous trouvez une alternance, gagnez 150€.;0;0;100;0;0;0;0;0;0;0;0;0;0;3;Gagner argent;Non\n" + 
			"5;La banque vous fait gagner/perdre ALEACHIFRE2€.;0;0;1;0;0;0;0;0;0;0;0;0;0;3;Banque;Non\n" + 
			"6;Vous avez trop dépensé au bar hier soir et êtes maintenant à découvert, payez les AGIO: 50€.;0;0;-50;0;0;0;0;0;0;0;0;0;0;4;Perdez argent;Non\n" + 
			"7;Votre radiateur est en panne et vous attrapez froid, payez les frais de médecin : 50€.;0;0;-50;0;0;0;0;0;0;0;0;0;0;4;Perdez argent;Non\n" + 
			"8;Vous démontez votre micro-ondes et n'arrivez pas à le remonter. Payez pour rachetez un micro-ondes: 100€.;0;0;-100;0;0;0;0;0;0;0;0;0;0;3;Perdez argent;Non\n" + 
			"9;Vous avez joué avec un extincteur, vous payez 100€ pour réparer les dégâts.;0;0;-100;0;0;0;0;0;0;0;0;0;0;3;Perdez argent;Non\n" + 
			"10;Payez les frais de scolarité : 150€.;0;0;-150;0;0;0;0;0;0;0;0;0;0;2;Perdez argent;Non\n" + 
			"11;Vous vous êtes réveillé en retard et n'avez pas eu le temps de prendre votre petit déjeuner, allez en acheter un à la cafet.;0;0;0;0;0;0;0;0;0;0;0;0;0;2;Petit déjeuner;Cafet\n" + 
			"12;C'est votre jour de chance, vous trouvez un billet par terre, gagnez 100€.;0;0;50;0;0;0;0;0;0;0;0;0;0;4;Gagner argent;Non\n" + 
			"13;Impôts, payez XXX% du prix total de vos possessions (PRIXPOSSESSION).;0;0;1;0;0;0;0;0;0;0;0;0;0;1;Impôts;Non\n" + 
			"14;Vous MOUVEMENT de ALEACASES cases.;0;1;0;1;-1;0;0;0;0;0;0;0;0;4;Mouvement;Non\n" + 
			"15;Les chaises roulantes vous attirent, rendez vous à la salle des alternants.;0;0;0;0;0;0;0;0;1;0;0;0;0;2;Chaises roulantes;Alternant\n" + 
			"16;Vous découvrez l'existence de la BU au bout de deux ans, allez y faire un tour.;0;0;0;0;0;0;0;0;1;0;0;0;0;2;BU;Parking\n" + 
			"17;\"Eh dis, Eddy, c'est marrant ça\". Allez en com.;0;0;0;0;0;0;0;0;1;0;0;0;0;2;Eddy;Com\n" + 
			"18;Vous partez à l'aventure comme Xavier, allez à : ALEACASE. ;0;0;0;0;0;0;0;0;1;0;0;0;0;3;Xavier l'aventurier;Oui\n" + 
			"19;C'est la rentrée ! Rendez vous à la case départ.;0;0;0;0;0;0;0;0;1;0;0;0;0;2;Rentrée;Départ\n" + 
			"20;Vous avez bien révisé vos partiels, sortez de la salle des DS.;1;0;0;1;0;0;0;0;0;0;0;0;0;3;Sortir de DS;Non\n" + 
			"21;Vous avez géré vos partiels, sortez de la salle des DS.;1;0;0;1;0;0;0;0;0;0;0;0;0;3;Sortir de DS;Non";

	/**
	 * Le nombre de carte chance est le total des cartes du fichier csv.
	 * C'est une variable de classe, elle n'est pas modifiable.
	 * Cette variable est initialisee lors de la creation de la premiere carte chance.
	 * 
	 * @see CarteChance#getNbCarte();
	 * @see CarteChance#getNombreCarte();
	 * 
	 */
	private static int nbCarte=0;
	
	/**
	 * Compteur qui est incrémenté à chaque instanciation d'une carte chance. 
	 */
	private static int compteur=0;
	
	
	/**
	 * Stocke les certaines valeurs du fichier csv. 
	 */
	static float[][]tableauDesCartes;
	
	/**
	 * Stocke les valeurs de bases de certaines valeurs du fichier csv. 
	 */
	static float tableauDesCartesDeBase[][];

	
	/**
	 * Le numero contient l'identifiant de la cartes.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#getNum();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private int num;
	
	/**
	 * Le texte est le descriptif de la cartes.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#getTexte();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private String texte;
	
	/**
	 * Le booleen conservee contient vrai si le joueur doit la conservee.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isConservee();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean conservee;
	
	/**
	 * Le booleen impactProTour contient vrai si la carte à un effet au prochain tour.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isImpactProTour();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean impactProTour;
	
	/**
	 * L'entier plusMoinsArgent contient le prix que la carte fait gagner/perdre au joueur.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#getPlusMoinsArgent();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private int plusMoinsArgent;
	
	/**
	 * L'entier nbEffetTour contient le nombre de tour ou l'effet de la carte s'applique.
	 * Cette variable est récupéree a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#getNbEffetTour();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private int nbEffetTour;
	
	/**
	 * L'entier plusMoinsCase contient le nombre de case que le joueur doit parcourir.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#getNum();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private int plusMoinsCase;
	
	/**
	 * Le booleen sautTour contient vrai si la carte fait sauter un tour au joueur.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isSautTour();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean sautTour;
	
	/**
	 * Le booleen choix contient vrai si le joueur à le choix entre plusieurs options.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isChoix();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean choix;
	/**
	 * L'entier tempsTour contient la durée du tour du joueur.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#getTempsTour();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private int tempsTour;
	
	/**
	 * Le booleen envoieVersCase contient vrai si la carte envoie le joueur sur une case.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isEnvoieVersCase();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean envoieVersCase;
	
	/**
	 * Le booleen impactProAchat contient vrai si l'effet de la carte s'applique au prochain achat du joueur.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isImpactProAchat();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean impactProAchat;
	
	/**
	 * Le booleen impactPayementLoyer contient vrai si l'effet de la carte s'applique sur un payement de loyer.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isImpactPayementLoyer();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean impactPayementLoyer;
	
	/**
	 * Le booleen impactAchatMateriel contient vrai si l'effet de la carte s'applique sur un achat de matériel.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isImpactAchatMateriel();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean impactAchatMateriel;
	
	/**
	 * Le booleen impactPayementLoyer contient vrai si la carte affecte tous les joueurs.
	 * Cette variable est récupérée a partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#isImpactPayementLoyer();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private boolean impactTousLesJoueurs;
	
	/**
	 * Le numero contient la probabilité de la carte.
	 * Cette variable est récupérée à partir du fichier csv, elle n'est pas modifiable.
	 * 
	 * @see CarteChance#getProbabilite();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private int probabilite;
	
	/**
	 * Le numero contient la probabilité temporaire de la carte.
	 * Cette variable est initialisée à la valeur du fichier csv, puis change au cours de la partie.
	 * 
	 * @see CarteChance#getProbabilite();
	 * @see CarteChance#getInfo(int);
	 * 
	 */
	private int probabiliteTempo;

	
	/**
	 * Le "String" nom contient le nom de la carte chance.
	 * 
	 * @see CarteChance#getNom();
	 * @see CarteChance#getInfo(int);
	 */
	private String nom;


	/**
	 * Le String salle contient le salle destination si une carte chance envoie sur une salle.
	 * 
	 * @see CarteChance#getSalle();
	 * @see CarteChance#getInfo(int):
	 */
	private String salle;
	
	
	/**
	 * Constructeur de la carte chance.
	 * La premiere carte chance initialise la variable de classe nbCarte.
	 * Le constructeur récupère une carte aléatoirement dans le fichier csvn en notant que moins une carte est tirée, plus elle a de chance d'être piochée.
	 * 
	 * 
	 * 
	 */	
	public CarteChance(){
		initDossierCartesChance();
		if(CarteChance.nbCarte == 0) {
			CarteChance.nbCarte = this.getNombreCarte();
		}
		try {
			float a=CarteChance.tableauDesCartesDeBase[0][0];
		}
		catch (NullPointerException e) {
			CarteChance.tableauDesCartesDeBase=new float[CarteChance.nbCarte][2];
			this.initialisationTableauDeProbaFixes(nbCarte);
		}
		try {
			float a=CarteChance.tableauDesCartes[0][0];
		}
		catch(NullPointerException e) {
			CarteChance.tableauDesCartes=new float[CarteChance.nbCarte][3];
			this.initialisationTableauDeProbaTemporaire(nbCarte);
		}
		float a = this.randRange();
		this.getInfo((int)this.rechercheDichotomique(nbCarte, a));
		//this.getInfo(12);
		this.ajoutProba(this.num);
		CarteChance.compteur++;
	}


	/**
	 * Accesseur nbCarte.
	 * 
	 * @return Le nombre total de carte du jeu. 
	 * 
	 */
	public static int getNbCarte() {
		return nbCarte;
	}

	/**
	 * Accesseur num.
	 * 
	 * @return L'identifiant de la carte. 
	 *  
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Accesseur texte.
	 * 
	 * @return Le descriptif de la carte. 
	 *  
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * Accesseur conservee.
	 * 
	 * @return  Vrai si il faut conservee la carte. 
	 * 
	 */
	public boolean isConservee() {
		return conservee;
	}

	/**
	 * Accesseur impactProTour.
	 * 
	 * @return Vrai si l'effet impact le prochain tour. 
	 *  
	 */
	public boolean isImpactProTour() {
		return impactProTour;
	}

	/**
	 * Accesseur plusMoinsArgent.
	 * 
	 * @return L'argent que doit recevoir/debourser le joueur. 
	 *  
	 */
	public int getPlusMoinsArgent() {
		return plusMoinsArgent;
	}

	/**
	 * Accesseur nbEffetTour.
	 * 
	 * @return  Le nombre de tour sur lesquelles l'effet s'appliquera. 
	 */
	public int getNbEffetTour() {
		return nbEffetTour;
	}

	/**
	 * Accesseur plusMoinsCase.
	 * 
	 * @return Le nombre de case que le joueur doit parcourir. 
	 *  
	 */
	public int getPlusMoinsCase() {
		return plusMoinsCase;
	}

	/**
	 * Accesseur du nom de la carte. 
	 * 
	 * @return Le nom de la carte. 
	 * 
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter du nom de la carte. 
	 * 
	 * @param nom Nouveau nom de la carte. 
	 * 
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * Accesseur sautTour.
	 * 
	 * @return Vrai si le joueur doit sauter son tour. 
	 *  
	 */
	public boolean isSautTour() {
		return sautTour;
	}

	/**
	 * Accesseur choix.
	 * 
	 * @return Vrai si le joueur a un choix. 
	 *  
	 */
	public boolean isChoix() {
		return choix;
	}
	
	/**
	 * Accesseur tempsTour.
	 * 
	 * @return Le temps du tour. 
	 *  
	 */
	public int getTempsTour() {
		return tempsTour;
	}

	/**
	 * Accesseur envoieVersCase.
	 * 
	 * @return Vrai si le joueur se rend à une case. 
	 *  
	 */
	public boolean isEnvoieVersCase() {
		return envoieVersCase;
	}

	/**
	 * Accesseur impactProAchat.
	 * 
	 * @return Vrai si l'effet impact le prochain achat. 
	 *  
	 */
	public boolean isImpactProAchat() {
		return impactProAchat;
	}

	/**
	 * Accesseur impactPayementLoyer.
	 * 
	 * @return Vrai si l'effet impact le prochain payement du loyer. 
	 *  
	 */
	public boolean isImpactPayementLoyer() {
		return impactPayementLoyer;
	}

	/**
	 * Accesseur impactAchatMateriel.
	 * 
	 * @return Vrai si l'effet impact l'achat d'un prochain materiel.
	 *  
	 */
	public boolean isImpactAchatMateriel() {
		return impactAchatMateriel;
	}

	/**
	 * Accesseur impactTousLesJoueurs.
	 * 
	 * @return  Vrai si l'effet impact tous les joueurs. 
	 *   
	 */
	public boolean isImpactTousLesJoueurs() {
		return impactTousLesJoueurs;
	}
	
	/**
	 * Accesseur plusMoinsArgent.
	 * 
	 * @return L'argent que doit recevoir/debourser le joueur. 
	 *  
	 */
	public int getProbabilite() {
		return probabilite;
	}
	
	
	/**
	 * Accesseur de Salle.
	 * 
	 * @return La salle.
	 * 
	 */
	public String getSalle() {
		return salle;
	}


	/**
	 * Setter de la salle de la carte. 
	 * 
	 * @param salle Nouvelle salle de la carte. 
	 * 
	 */
	public void setSalle(String salle) {
		this.salle = salle;
	}


	/**
	 * Setter de la variable +/- argent.
	 * 
	 * @param plusMoinsArgent Nouvelle valeur d'argent de la carte.
	 */
	public void setPlusMoinsArgent(int plusMoinsArgent) {
		this.plusMoinsArgent = plusMoinsArgent;
	}


	/**
	 * Setter du texte de la carte chance.
	 * @param texte Texte de la carte chance
	 * 
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	/**
	 * Setter du nombre de tours ou la carte fait effet.
	 * 
	 * @param nbEffetTour Nouveau nombre de tour.
	 */
	public void setNbEffetTour(int nbEffetTour) {
		this.nbEffetTour = nbEffetTour;
	}
	
	

	/**
	 * Setter de plusMoinsCase.
	 * 
	 * @param plusMoinsCase Nouvelle valeur de plusMoinsCase.
	 * 
	 */
	public void setPlusMoinsCase(int plusMoinsCase) {
		this.plusMoinsCase = plusMoinsCase;
	}


	@Override
	public String toString() {
		return "CarteChance [num=" + num + ", texte=" + texte + ", conservee=" + conservee + ", impactProTour="
				+ impactProTour + ", plusMoinsArgent=" + plusMoinsArgent + ", nbEffetTour=" + nbEffetTour
				+ ", plusMoinsCase=" + plusMoinsCase + ", sautTour=" + sautTour + ", choix=" + choix + ", tempsTour="
				+ tempsTour + ", envoieVersCase=" + envoieVersCase + ", impactProAchat=" + impactProAchat
				+ ", impactPayementLoyer=" + impactPayementLoyer + ", impactAchatMateriel=" + impactAchatMateriel
				+ ", impactTousLesJoueurs=" + impactTousLesJoueurs + ", probabilite=" + probabilite
				+ ", probabiliteTempo=" + probabiliteTempo + "]"+ ", nom=" +nom+ " Salle : "+salle;
	}

	/**
	 * Méthode d'initialisation d'une carte chance, elle est utilisée dans le constructeur.
	 * Elle commence par lire le fichier csv puis va jusqu'à la ligne correspondant à l'identifiant en paramètre.
	 * Arrivée à cette ligne, elle lit les informations de la carte, et le informations lues sont enregistrées dans les variables.
	 * 
	 * @see CarteChance#CarteChance();
	 * 
	 * @param id L'identifiant de la carte a recupérer.	
	 */
	private void getInfo(int id){
		try {
			boolean premiereLigne=true;
			for(String ligne : Files.readAllLines(Paths.get("chances/cartesChance.csv"), StandardCharsets.UTF_8)) {
				if(!premiereLigne) {
					String[] data = ligne.split(";");
					if(Integer.parseInt(data[0])==id) {
						this.num = Integer.parseInt(data[0]);
						this.texte = data[1];
						this.conservee = (Integer.parseInt(data[2])==1);
						this.impactProTour = (Integer.parseInt(data[3])==1);
						this.plusMoinsArgent = Integer.parseInt(data[4]);
						this.nbEffetTour = Integer.parseInt(data[5]);
						this.plusMoinsCase = Integer.parseInt(data[6]);
						this.sautTour = (Integer.parseInt(data[7])==1);
						this.choix = (Integer.parseInt(data[8])==1);
						this.tempsTour= Integer.parseInt(data[9]);
						this.envoieVersCase = (Integer.parseInt(data[10])==1);
						this.impactProAchat = (Integer.parseInt(data[11])==1);
						this.impactPayementLoyer = (Integer.parseInt(data[12])==1);
						this.impactAchatMateriel = (Integer.parseInt(data[13])==1);
						this.impactTousLesJoueurs = (Integer.parseInt(data[14])==1);
						this.probabilite= Integer.parseInt(data[15]);
						this.nom=data[16];
						this.salle=data[17];
						return;
					}
				}else {
					premiereLigne=false;
				}
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Méthode d'initialisation d'un tableau associant l'identifiant d'une carte à la valeur temporaire de probabilité. 
	 * @param borneMax Nombre de carte(s) du fichier csv
	 */
	private void initialisationTableauDeProbaTemporaire(int borneMax) {
		for (int i=1;i<=borneMax;i++) { //parcours le fichier
			this.getInfo(i); //attribu les bonnes valeurs aux variables
			this.probabiliteTempo=this.probabilite;
			CarteChance.tableauDesCartes[i-1][0]=this.num;
			CarteChance.tableauDesCartes[i-1][1]=this.probabilite;	
			if (i>1)
				CarteChance.tableauDesCartes[i-1][2]=CarteChance.tableauDesCartes[i-1][1]+CarteChance.tableauDesCartes[i-2][2];
			else
				CarteChance.tableauDesCartes[i-1][2]=CarteChance.tableauDesCartes[i-1][1];
		}
	}
	
	/**
	 * Méthode d'initialisation d'un tableau associant l'identifiant d'une carte à la valeur initiale de probabilité. 
	 * @param borneMax Nombre de carte(s) du fichier csv
	 */
	private void initialisationTableauDeProbaFixes(int borneMax) {
		for (int i=1;i<=borneMax;i++) { //parcours le fichier
			this.getInfo(i); //attribu les bonnes valeurs aux variables
			CarteChance.tableauDesCartesDeBase[i-1][0]=this.num;
			CarteChance.tableauDesCartesDeBase[i-1][1]=this.probabilite;
		}
	}
	
	/**
	 * Permet l'affichage de l'entièreté du tableau de valeurs avec l'identifiant d'une carte, et sa probabilité temporaire. 
	 */
	private void afficheTab() {
		for (int i=0;i<CarteChance.tableauDesCartes.length;i++) {
			System.out.println("Numéro "+CarteChance.tableauDesCartes[i][0]+" de proba "+CarteChance.tableauDesCartes[i][1]+" de proba cummule : "+CarteChance.tableauDesCartes[i][2]);
		}
	}

	
	
	/**private void augmentationProba(int id) {
	 
	  M�thode permettant l'augmentation de toute les probabilités sauf celle de la carte dont l'identifiant est placé en paramètre, qui voit sa probabilité temporaire réaffactée à sa valeur de initiale. 
	  @param id Num de la carte tirée au tour précédent. 
	 
		int pos=-1;
		float sum=0;
		for (int i=0;i<CarteChance.tableauDesCartes.length;i++) {
			if (CarteChance.tableauDesCartes[i][0]==id) {
				int a =(int)CarteChance.tableauDesCartesDeBase[i][1];
				float tmp=0;
				if (compteur>1) {
					//gère le fait que si on a tiré plus de 2 cartes, les probabilités sont impactées 
					switch (a) {
						case 1 : tmp=(float)0.01*(compteur-1)-(float)0.01; break;
						case 2 : tmp=(float)0.02*(compteur-1)-(float)0.02; break;
						case 3 : tmp=(float)0.03*(compteur-1)-(float)0.03; break;
						case 4 : tmp=(float)0.04*(compteur-1)-(float)0.04; break;
						default : tmp=(float)0;
					}
				}
				CarteChance.tableauDesCartes[i][1]=CarteChance.tableauDesCartesDeBase[i][1]+tmp;
				pos=i;
			}else {
				float a = CarteChance.tableauDesCartesDeBase[i][1]/this.probaTotale(nbCarte);
				a = (float) Math.round(a * 100) / 100; //permet d'arrondir au centième
				sum+=a;
				CarteChance.tableauDesCartes[i][1]+=a;
			}	
		}
		CarteChance.tableauDesCartes[pos][1]-=sum;
		float b = CarteChance.tableauDesCartes[pos][1];
		b = (float) Math.round(b * 100) / 100; //permet d'arrondir au centième
		CarteChance.tableauDesCartes[pos][1]=b;
		float c = CarteChance.tableauDesCartes[pos][2];
		c = (float) Math.round(c * 100) / 100; //permet d'arrondir au centième
		CarteChance.tableauDesCartes[pos][2]=c;
		//pas le choix de faire deux for, des valeurs sont chang�es dans le premier for et sont utilisées dans le deuxième for
		for (int i=0;i<CarteChance.tableauDesCartes.length;i++) {
			if (i<1) {
				CarteChance.tableauDesCartes[i][2]=CarteChance.tableauDesCartes[i][1];
			} else {
				 float a = CarteChance.tableauDesCartes[i][1]+CarteChance.tableauDesCartes[i-1][2];
				 a = (float) Math.round(a * 100) / 100; //permet d'arrondir au centième
				 CarteChance.tableauDesCartes[i][2]=a;
			}
		}
	} //la somme des probabilités totale doit toujours être égale à 100 (avec le fichier csv de base). **/
	
	/**
	 * Méthode d'ajout de probabilité, celle-ci ajoute 1 à toutes les cartes, sauf la carte que vient de tiré l'utilisateur, qui voit sa valeur remise à sa valeur d'origine.
	 * @param id Id de la carte qui vient d'être tirée par l'utilisateur.
	 */
	private void ajoutProba(int id) {
		for (int i=0;i<tableauDesCartes.length;i++) {
			if (tableauDesCartes[i][0]==id) {
				CarteChance.tableauDesCartes[i][1]=CarteChance.tableauDesCartesDeBase[i][1];
			}else {
				tableauDesCartes[i][1]++;
			}
		}
		for (int i=0;i<tableauDesCartes.length;i++) {
			if (i<1)
				tableauDesCartes[i][2]=tableauDesCartes[i][1];
			else
				tableauDesCartes[i][2]=CarteChance.tableauDesCartes[i][1]+CarteChance.tableauDesCartes[i-1][2];
		}
	}
	
	/**
	 * Méthode permettant la creation d'un entier aléatoire comprit entre 0 et la borne max passée en paramètre.
	 * Cette methode est utilisée pour avoir une carte aléatoire parmis celles inscrites dans le fichier csv.
	 * le résultat de cette fonction est utile à la fonction getInfo(int);
	 * 
	 * 
	 * @param borneMax Un entier représentant le nombre limite, le résultat est inférieur à cette borne.
	 * 		
	 * @return Un nombre aléatoire entre 1 et la borne passée en paramètre.
	 * 
	 * @see CarteChance#getInfo(int);

	 */
	private int nombreAleatoire(int borneMax) {
		// génération d'un nombre >= 0 et < borneMax
		Random r = new Random();
		int alea = r.nextInt(borneMax); 
		//retour d'un nombre > 0 et <= borneMax
		return alea+1;
	}
	
	/**
	 * Méthode permettant d'obtenir un float aléatoire comprit entre 0 et la somme des probabilités des cartes arrondi au centième. 
	 * @return Un float aléatoire arrondi au centième. 
	 */
	private float randRange() {
		float max = this.probaTotale(nbCarte);
		float min = (float)0.0;
		float val = min + (float)Math.random() * (max - min);
		float a = (float) Math.round(val * 100) / 100;
		return a;
	}
	
	/**
	 * Méthode d'obtention de la probabilité temporaire totale possible. 
	 * Cette méthode fait la somme des valeurs comprises dans le tableau de valeurs temporaires de probabilités. 
	 * @param borneMax Nombre de carte(s) maximum du fichier.
	 * @return Retourne la somme des probabilités temporaires (int). 
	 */
	private float probaTotale(int borneMax) {
		float total = 0; //probabilité totale
		for (int i=0;i<=borneMax-1;i++) { //parcours le tableau de probabilites temporaires
			total+=CarteChance.tableauDesCartes[i][1]; //fais la somme de ces probabilites
		}
		total = (float) Math.round(total * 100) / 100; //permet d'arrondir au centième
		return total; //probabilité totale
	}
	
	/**
	 * Méthode de recherche dichotomique sur un tableau trié.
	 * Utilisé pour trouver l'id de la carte aléatoire précédemment tirée dans le tableau. 
	 * @param Tab tableau sur lequel on effectue la recherche. 
	 * @param TailleTableau Taille du tableau sur lequel on recherche. 
	 * @param ProbaAlea probaAleatoire obtenue grâce à la méthode randRange()
	 * @return L'id de la carte dont la probabilité aléatoire  à été placée en paramètre.
	 */
	private float rechercheDichotomique( int tailleTableau, float probaAlea){ //TODO ajouter le test si c'est la première valeur ou dernière valeur du tableau 
		boolean trouve = false;
		int g = 0;
		int n;
		int h = CarteChance.tableauDesCartes.length-1;
		do{
			n = (g+h)/2;
			if ((n>0)&&(CarteChance.tableauDesCartes[n][2] >= probaAlea)&&(CarteChance.tableauDesCartes[n-1][2]<probaAlea)){ //test si notre probaAlea est comprise entre la valeur [m} et [m+1] du tableau
				trouve = true;
			}
			else if (CarteChance.tableauDesCartes[n][2] < probaAlea){
				g = n+1;
			}
			else{
				h = n-1;
			}
		} while ((!trouve)&&(g<=h)); //boucle tant qu'on a pas trouvé la valeur recherché
		float a=CarteChance.tableauDesCartes[n][0];
		return a; //retourne l'id trouvé
	}


	/**
	 * Méthode comptant le nombre total de carte dans le fichier csv.
	 * 
	 * @return Le nombre total de carte(s) chance.		
	 */
	private int getNombreCarte(){
		try {
			return Files.readAllLines(Paths.get("chances/cartesChance.csv"), StandardCharsets.UTF_8).size()-1;
		} catch (IOException e) {e.printStackTrace();}
		return 0;
	}
	
	
	private void initDossierCartesChance(){
		File chances = new File("chances");
		if(!chances.exists()) {
			chances.mkdirs();
			try {
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("chances/cartesChance.csv"), StandardCharsets.UTF_8);
				writer.write(CARTESCHANCE);
				writer.close();
			}catch(Exception e){}
		}
	}

}
