package back;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import view.PlateauView;

/**
 * Plateau est la classe représentant un plateau du monopoly.
 *
 */
public class Plateau {
	
	final String CASESPETITPLATEAU=
			"id;nom;type;prixAchat;materiel;loyer0;loyer1;loyer2;loyer3;couleur\n" + 
			"0;Départ;1;0;0;0;0;0;0;#cde7ce\n" + 
			"1;Com;2;60;50;10;30;90;250;#80E1FC\n" + 
			"2;Droit;2;80;50;20;60;180;450;#80E1FC\n" + 
			"3;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"4;Anglais;2;120;50;40;100;300;600;#80E1FC\n" + 
			"5;Parking;4;0;0;0;0;0;0;#cde7ce\n" + 
			"6;Réseaux;2;160;100;60;180;500;900;#FF8201\n" + 
			"7;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"8;Archi;2;180;100;70;200;550;600;#FF8201\n" + 
			"9;Système;2;200;100;80;220;600;1000;#FF8201\n" + 
			"10;Salle de DS;5;0;0;0;0;0;0;#cde7ce\n" + 
			"11;Maths;2;240;150;100;300;750;1100;#FFFC01\n" + 
			"12;Algo;2;260;150;110;330;800;1150;#FFFC01\n" + 
			"13;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"14;Web;2;280;150;120;360;850;1200;#FFFC01\n" + 
			"15;Taxe de luxe;6;0;0;0;0;0;0;#cde7ce\n" + 
			"16;BDD;2;300;200;130;390;900;1275;#4474E0\n" + 
			"17;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"18;Projet;2;350;200;175;500;1100;1500;#4474E0\n" + 
			"19;Alternant;2;400;200;200;600;1400;2000;#4474E0";
	
	final String CASESGRANDPLATEAU=
			"id;nom;type;prixAchat;materiel;loyer0;loyer1;loyer2;loyer3;couleur\n" + 
			"0;Départ;1;0;0;0;0;0;0;#cde7ce\n" + 
			"1;Com;2;60;50;10;30;90;250;#a640a6\n" + 
			"2;Droit;2;80;50;20;60;180;450;#a640a6\n" + 
			"3;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"4;Monoprix;7;200;0;50;100;150;200;#F8CECC\n" + 
			"5;PPP;2;100;50;30;90;270;550;#80e1fc\n" + 
			"6;Anglais;2;120;50;40;100;300;600;#80e1fc\n" + 
			"7;Parking;4;0;0;0;0;0;0;#cde7ce\n" + 
			"8;Réseaux;2;160;100;60;180;500;900;#ff972f\n" + 
			"9;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"10;Super U;7;200;0;50;100;150;200;#F8CECC\n" + 
			"11;Archi;2;180;100;70;200;550;950;#ff972f\n" + 
			"12;RU;8;150;0;0;0;0;0;#FFF2CC\n" + 
			"13;Système;2;200;100;80;220;600;1000;#ff972f\n" + 
			"14;Salle de DS;5;0;0;0;0;0;0;#cde7ce\n" + 
			"15;Maths;2;240;150;100;300;750;1100;#ffff85\n" + 
			"16;Géant;7;200;0;50;100;150;200;#F8CECC\n" + 
			"17;Algo;2;260;150;110;330;800;1150;#ffff85\n" + 
			"18;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"19;Web;2;280;150;120;360;850;1200;#ffff85\n" + 
			"20;Cafet;8;150;0;0;0;0;0;#FFF2CC\n" + 
			"21;Taxe de luxe;6;0;0;0;0;0;0;#cde7ce\n" + 
			"22;BDD;2;300;200;130;390;900;1275;#0bb142\n" + 
			"23;POO;2;320;200;150;450;1000;1400;#0bb142\n" + 
			"24;?;3;0;0;0;0;0;0;#9673a6\n" + 
			"25;E.Leclerc;7;200;0;50;100;150;200;#F8CECC\n" + 
			"26;Projet;2;350;200;175;500;1100;1500;#4474e0\n" + 
			"27;Alternant;2;400;200;200;600;1400;2000;#4474e0";
	
	/**
	 * La taille correspond au nombre de cases du plateau.
	 * Elle doit être égale à 20 ou 28.
	 * 
	 * @see Plateau#getTaille()
	 * @see Plateau#Plateau(int,int)
	 */
	private int taille;

	/**
	 * La valeur que l'on reçoit quand on passe la case départ.
	 * 
	 * @see Plateau#getCaseDepartPassage()
	 * @see Plateau#setCaseDepartPassage(int)
	 * @see Plateau#Plateau(int,int)
	 */
	private int caseDepartPassage;

	/**
	 * La valeur que l'on reçoit quand on passe la case départ.
	 * 
	 * @see Plateau#getCaseDepartArret()
	 * @see Plateau#setCaseDepartArret(int)
	 * @see Plateau#Plateau(int,int)
	 */
	private int caseDepartArret;


	/**
	 * L'id correspond au numéro de la case sur le plateau.
	 * 
	 * @see Plateau#getId()
	 * @see Plateau#setId(int)
	 */
	private int id;

	/**
	 * Le nom correspond au nom de la case sur le plateau.
	 * 
	 * @see Plateau#getNom()
	 * @see Plateau#setNom(String)
	 */
	private String nom;

	/**
	 * Le type correspond au type de la case sur le plateau.
	 * Cela permet de savoir à quelle classe elle appartient.
	 * 
	 * @see Plateau#getType()
	 * @see Plateau#setType(int)
	 */
	private int type;

	/**
	 * Le prix d'achat correspond au prix d'achat d'une case du plateau.
	 * 
	 * @see Plateau#getPrixAchat()
	 * @see Plateau#setPrixAchat(int)
	 */
	private int prixAchat;

	/**
	 * mat1 correspond au prix d'un matériel sur une matière. 
	 * Pour le deuxième matériel on reprend la même valeur.
	 * Et pour le dernier matériel on double cette valeur.
	 * 
	 * @see Plateau#getMat1()
	 * @see Plateau#setMat1(int)
	 */
	private int mat1;

	/**
	 * Loy0 correspond au loyer d'une case quand on a aucun matériel dessus.
	 * 
	 * @see Plateau#getLoy0()
	 * @see Plateau#setLoy0(int)
	 */
	private static int loy0;

	/**
	 * Loy1 correspond au loyer d'une case quand on a un matériel dessus.
	 * 
	 * @see Plateau#getLoy1()
	 * @see Plateau#setLoy1(int)
	 */
	private static int loy1;

	/**
	 * Loy2 correspond au loyer d'une case quand on a deux matériels dessus.
	 * 
	 * @see Plateau#getLoy2()
	 * @see Plateau#setLoy2(int)
	 */
	private static int loy2;

	/**
	 * Loy3 correspond au loyer d'une case quand on a trois matériels dessus.
	 * 
	 * @see Plateau#getLoy3()
	 * @see Plateau#setLoy3(int)
	 */
	private static int loy3;

	/**
	 * La couleur correspond à la couleur de la case sur le plateau.
	 * 
	 * @see Plateau#getCouleur()
	 * @see Plateau#setCouleur(String)
	 */
	private String couleur;

	/**
	 * La listePlateau correspond à la liste de toutes les cases sur le plateau.
	 * 
	 * @see Plateau#getListePlateau()
	 * @see Plateau#setListePlateau(ArrayList<Case>)
	 */
	private ArrayList<Case> listePlateau;




	private PlateauView view;


	/** Constructeur d'un plateau
	 * Quand la valeur entrée n'est pas bonne le plateau est automatiquement initialisé à 20 cases. 
	 * On initialise la liste des cases du plateau dans la liste.
	 * On initialise aussi le nombre de ligne dans le fichier csv.s
	 * 
	 * @param taille 
	 * 		Le nombre de cases du plateau à créer, 20 ou 28.
	 * @param caseDepPassage
	 * 		l'argent que l'on recupère quand on passe la case départ
	 * 
	 * @param caseDepArret
	 * 		l'argent que l'on recupère quand on passe la case départ
	 */
	public Plateau(int taille, int caseDepPassage, int caseDepArret) {
		this.taille=taille==28?taille:20;
		this.caseDepartPassage = caseDepPassage;
		this.caseDepartArret = caseDepArret;
		this.listePlateau=new ArrayList<>();
		initialisationListe();
		view=new PlateauView(this);
	}

	public PlateauView getView() {
		return view;
	}


	/**	
	 * Récupérer la taille du plateau
	 * 
	 * @return Un entier correspondant à la taille du plateau, il s'agit du nombre de cases du plateau.
	 */
	public int getTaille() {
		return taille;
	}

	/**	
	 * Récupérer la valeur de la case départ.
	 * 
	 * @return Un entier correspondant à la valeur au passage de la case départ.
	 */
	public int getCaseDepartPassage() {
		return caseDepartPassage;
	}

	/**	
	 * Méthode de modification de la valeur de la case départ.
	 * 
	 * @param caseDepartPassage
	 * 		La nouvelle valeur de la case départ.
	 */
	public void setCaseDepartPassage(int caseDepartPassage) {
		this.caseDepartPassage = caseDepartPassage;
	}

	/**	
	 * Récupérer la valeur de la case départ.
	 * 
	 * @return Un entier correspondant à la valeur à l'arrêt sur la case départ.
	 */
	public int getCaseDepartArret() {
		return caseDepartArret;
	}

	/**	
	 * Méthode de modification de la valeur de la case départ.
	 * 
	 * @param caseDepartArret
	 * 		La nouvelle valeur de la case départ.
	 */
	public void setCaseDepartArret(int caseDepartArret) {
		this.caseDepartPassage = caseDepartArret;
	}


	/**	
	 * Récupérer la valeur de l'id de la case.
	 * 
	 * @return Un entier correspondant à la valeur de l'id.
	 */
	public int getId() {
		return id;
	}


	/**	
	 * Méthode de modification de l'id.
	 * 
	 * @param id
	 * 		La nouvelle valeur de l'id.
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**	
	 * Récupérer la valeur du nom de la case.
	 * 
	 * @return Une chaine de charatères correspondant au nom de la case.
	 */
	public String getNom() {
		return nom;
	}


	/**	
	 * Méthode de modification du nom de la case.
	 * 
	 * @param nom
	 * 		Le nouveau nom.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**	
	 * Récupérer la valeur du type de la case.
	 * 
	 * @return Un entier correspondant au type de la case.
	 */
	public int getType() {
		return type;
	}


	/**	
	 * Méthode de modification du type de la case.
	 * 
	 * @param type
	 * 		La nouvelle valeur du type.
	 */
	public void setType(int type) {
		this.type = type;
	}


	/**	
	 * Récupérer la valeur du prix d'achat.
	 * 
	 * @return Un entier correspondant au prix d'achat.
	 */
	public int getPrixAchat() {
		return prixAchat;
	}


	/**	
	 * Méthode de modification du prix d'achat.
	 * 
	 * @param prixAchat caseDepartPassage
	 * 		La nouvelle valeur du prix d'achat.
	 */
	public void setPrixAchat(int prixAchat) {
		this.prixAchat = prixAchat;
	}


	/**	
	 * Récupérer la valeur pour l'achat d'un matériel.
	 * 
	 * @return Un entier correspondant la valeur d'achat d'un matériel.
	 */
	public int getMat1() {
		return mat1;
	}


	/**	
	 * Méthode de modification de la valeur d'un achat de matériel.
	 * 
	 * @param mat1
	 * 		La nouvelle valeur d'achat d'un matériel.
	 */
	public void setMat1(int mat1) {
		this.mat1 = mat1;
	}


	/**	
	 * Récupérer la valeur du premier loyer, sans matériel.
	 * 
	 * @return Un entier correspondant à la valeur du permier loyer.
	 */
	public static int getLoy0() {
		return loy0;
	}


	/**	
	 * Methode de modification de la valeur du premier loyer, avec un matériel.
	 * 
	 * @param loy0
	 * 		La nouvelle valeur du premier loyer.
	 */
	public static void setLoy0(int loy0) {
		Plateau.loy0 = loy0;
	}


	/**	
	 * Récupérer la valeur du deuxième loyer, avec un matériel.
	 * 
	 * @return Un entier correspondant à la valeur du deuxième loyer.
	 */
	public static int getLoy1() {
		return loy1;
	}


	/**	
	 * Methode de modification de la valeur du deuxième loyer, avec un matériel.
	 * 
	 * @param loy1
	 * 		La nouvelle valeur du deuxième loyer.
	 */
	public static void setLoy1(int loy1) {
		Plateau.loy1 = loy1;
	}


	/**	
	 * Récupérer la valeur du troisième loyer, avec deux matériels.
	 * 
	 * @return Un entier correspondant à la valeur du troisième loyer.
	 */
	public static int getLoy2() {
		return loy2;
	}


	/**	
	 * Methode de modification de la valeur du troisième loyer, avec deux matériels.
	 * 
	 * @param loy2 caseDepartPassage
	 * 		La nouvelle valeur du troisième loyer.
	 */
	public static void setLoy2(int loy2) {
		Plateau.loy2 = loy2;
	}


	/**	
	 * Récupérer la valeur du quatrième loyer, avec trois matériels.
	 * 
	 * @return Un entier correspondant à la valeur du quatrième loyer.
	 */
	public static int getLoy3() {
		return loy3;
	}


	/**	
	 * Methode de modification de la valeur du quatrième loyer, avec trois matériels.
	 * 
	 * @param loy3
	 * 		La nouvelle valeur du quatrième loyer.
	 */
	public static void setLoy3(int loy3) {
		Plateau.loy3 = loy3;
	}


	/**	
	 * Récupérer la valeur de la couleur.
	 * 
	 * @return Une chaine de charactères correspondant à la couleur de la case.
	 */
	public String getCouleur() {
		return couleur;
	}


	/**	
	 * Methode de modification de la couleur de la case.
	 * 
	 * @param couleur
	 * 		La nouvelle valeur de la couleur.
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}


	/**	
	 * Récupérer la liste des cases du plateau.
	 * 
	 * @return Une ArrayList de Case correspondant aux cases du plateau.
	 */
	public ArrayList<Case> getListePlateau() {
		return listePlateau;
	}


	/**	
	 * Methode de modification des cases du plateau.
	 * 
	 * @param listePlateau
	 * 		La nouvelle ArrayList de Case.
	 */
	public void setListePlateau(ArrayList<Case> listePlateau) {
		this.listePlateau = listePlateau;
	}

	public Case getCase(String nom) {
		for(Case c : listePlateau)if(c.getNom().equals(nom))return c;
		return getInteractif();
	}

	private Case getInteractif() {
		for(Case c : listePlateau)if(c instanceof Interactif)return c;
		return null;
	}

	public int getNbMateriels() {
		int total=0;
		for(Case c : listePlateau) {
			if(c instanceof Salle && ((Salle) c).estPossedee()) {
				total+=((Salle) c).getNbMateriels();
			}
		}
		return total;
	}

	public void modifierLoyers(boolean modif) {
		for(Case c : listePlateau) {
			if(c instanceof Salle) {
				((Salle) c).setCoutLoyer0((int)(((Salle) c).getCoutLoyer0()*(1+getNbMateriels()*0.001*(modif?1:-1))));
				((Salle) c).setCoutLoyer1((int)(((Salle) c).getCoutLoyer1()*(1+getNbMateriels()*0.001*(modif?1:-1))));
				((Salle) c).setCoutLoyer2((int)(((Salle) c).getCoutLoyer2()*(1+getNbMateriels()*0.001*(modif?1:-1))));
				((Salle) c).setCoutLoyer3((int)(((Salle) c).getCoutLoyer3()*(1+getNbMateriels()*0.001*(modif?1:-1))));
			}
		}
	}

	public boolean toutesCasesAchetees() {
		for(Case c : listePlateau) {
			if(c instanceof Propriete&&!((Propriete) c).estPossedee())return false;
		}
		return true;
	}


	private void initDossierPlateaux(){
		File plateaux = new File("plateaux");
		if(!plateaux.exists()) {
			plateaux.mkdirs();
			try {
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("plateaux/casesPetitPlateau.csv"), StandardCharsets.UTF_8);
				writer.write(CASESPETITPLATEAU);
				writer.close();
				writer = new OutputStreamWriter(new FileOutputStream("plateaux/casesGrandPlateau.csv"), StandardCharsets.UTF_8);
				writer.write(CASESGRANDPLATEAU);
				writer.close();
			}catch(Exception e){}
		}
	}

	/**
	 * 	
	 * Récupérer les informations des cases du plateau. On lit un fichier csv et on récupère les informations 
	 * des cases.
	 * 
	 */
	private void initialisationListe(){
		initDossierPlateaux();
		try {
			String fileDir = taille==28?"plateaux/casesGrandPlateau.csv":"plateaux/casesPetitPlateau.csv";
			boolean premiereLigne=true;
			for(String ligne : Files.readAllLines(Paths.get(fileDir), StandardCharsets.UTF_8)) {
				if(!premiereLigne) {
					String[] data = ligne.split(";");					
					switch(Integer.parseInt(data[2])) {
					case 1:
						listePlateau.add(new Depart(data[1], Integer.parseInt(data[0]), this.caseDepartPassage, this.caseDepartArret, data[9] ));
						break;
					case 2:
						listePlateau.add(new Salle(data[1], Integer.parseInt(data[0]), data[9], Integer.parseInt(data[3]),Integer.parseInt(data[4]),Integer.parseInt(data[5]),Integer.parseInt(data[6]),Integer.parseInt(data[7]),Integer.parseInt(data[8])));
						break;
					case 3:
						listePlateau.add(new Chance(data[1],Integer.parseInt(data[0]), data[9]));
						break;
					case 4:
						listePlateau.add(new Interactif(data[1],Integer.parseInt(data[0]), data[9]));
						break;
					case 5:
						listePlateau.add(new SalleDeDS(data[1],Integer.parseInt(data[0]), data[9],Integer.parseInt(data[3])));
						break;
					case 6:
						listePlateau.add(new TaxeDeLuxe(data[1], Integer.parseInt(data[0]), data[9]));
						break;
					case 7:
						listePlateau.add(new CentreCommercial(data[1], Integer.parseInt(data[0]), data[9],Integer.parseInt(data[3]), Plateau.loy0, Plateau.loy1, Plateau.loy2, Plateau.loy3));
						break;
					case 8:
						listePlateau.add(new Service(data[1], Integer.parseInt(data[0]), data[9], Integer.parseInt(data[3])));
						break;
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
}
