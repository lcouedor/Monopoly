package back;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Configuration implements Serializable{
	private static final long serialVersionUID = -318367783034409468L;
	private static Configuration config; 
	private static ObservableList<Configuration> configs=FXCollections.observableList(new ArrayList<Configuration>());
	private String nom;
	private int nbJoueurs;
	private int nbBots;
	private String tpsTour;
	private String argentDepart;
	private String taillePlateau;
	private String argentCaseDepart;
	private String argentSurCaseDepart;
	private String nbTours;
	private String typePartie;
	private ArrayList<String> reglesAdditionnelles;

	public Configuration(){
		nom="";
		nbJoueurs=2;
		nbBots=0;
		tpsTour="30";
		argentDepart="1000";
		taillePlateau="6x6";
		argentCaseDepart="200";
		argentSurCaseDepart="400";
		nbTours="illimité";
		typePartie="Monopole";
		reglesAdditionnelles=new ArrayList<String>();
		config=this;
	}

	public static Configuration getConfig() {
		return config==null?initConfig():config;
	}

	/**
	 * Crée le dossier des configurations à l'emplacement du logiciel s'il n'existe pas.
	 */
	// Crée le dossier des configurations s'il n'existe pas
	public static void initDossierConfigs(){
		File configs = new File("configs");
		if(!configs.exists())configs.mkdirs();
	}

	/**
	 * Renvoie la dernière configuration modifiée du dossier de sauvegarde. Si aucune configuration n'est sauvegardée dans le dossier ou qu'une erreur survient lors du chargement du fichier de la configuration, la méthode renvoie une nouvelle configuration.
	 * 
	 * @return
	 * Configuration chargée
	 */
	// Charge la dernière configuration modifiée, renvoie une nouvelle configuration si aucune configuration n'est trouvée
	public static Configuration initConfig(){
		initDossierConfigs();
		File configs = new File("configs");
		Configuration config = new Configuration();
		// Vérifie qu'il y a des configurations sauvegardées
		if(configs.listFiles(profilFile).length>0) {
			int max=0;
			// Parcours les fichier avec l'extension souhaitée (.config) et trouve le dernier fichier modifié
			for(int i=0;i<configs.listFiles(profilFile).length;i++) {
				if(configs.listFiles(profilFile)[i].lastModified()>configs.listFiles(profilFile)[max].lastModified())max=i;
				ajouter(chargerConfig(configs.listFiles(profilFile)[i].getName().replaceFirst("[.][^.]+$", "")));
			}
			// Charge le dernier fichier modifié
			config=chargerConfig(configs.listFiles(profilFile)[max].getName().replaceFirst("[.][^.]+$", ""));
		}
		return config;
	}

	/**
	 * Sauvegarde la configuration dans le dossier des configurations, avec l'extension .config.
	 * 
	 * @return Réussite sauvegarde
	 */
	// Sauvegarde la configuration dans le dossier des configurations
	public boolean sauvegarderConfig(){
		initDossierConfigs();
		if(!nom.isEmpty()) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(getChemin(this.nom))));
				oos.writeObject(this);
				oos.close();
				ajouter(this);
				return true;
			}catch(Exception e){}
		}
		return false;
	}

	/**
	 * Renvoie la configuration sauvegardée dont le nom correspond au nom transmis.
	 * 
	 * @param nom
	 * Nom de la configuration à charger
	 * @return
	 * Configuration chargée
	 */
	// Renvoie la configuration lue dans le fichier des configurations
	public static Configuration chargerConfig(String nom){
		Configuration configtemp = new Configuration();
		if(!nom.isEmpty()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getChemin(nom)));
				configtemp = (Configuration)ois.readObject();
				ois.close();
			}catch(Exception e){}
		}
		return configtemp;
	}

	/**
	 * Supprime le fichier de la configuration.
	 * 
	 * @return Réussite suppression
	 */
	// Supprime une configuration du dossier des configurations
	public boolean supprimerConfig() {
		File fichier = new File(getChemin(this.nom));
		configs.remove(this);
		return fichier.delete();
	}
	
	// Filtre qui permet de savoir si un fichier possède une extension .config
	private static FileFilter profilFile = new FileFilter() {
		public boolean accept(File file) {
			if (file.getName().endsWith(".config")) {
				return true;
			}
			return false;
		}
	};

	// Rajoute la configuration passée en paramètre à la liste des configurations
	private static void ajouter(Configuration config) {
		if(!configs.contains(config)&&!config.nom.replaceAll("\\s+","").isEmpty())configs.add(new Configuration(config));
	}
	
	public boolean equals(Object o) {
		return (o instanceof Configuration && nom.equalsIgnoreCase(((Configuration)o).nom));
	}
	
	public Configuration(Configuration c) {
		nom=c.nom;
		nbJoueurs=c.nbJoueurs;
		nbBots=c.nbBots;
		tpsTour=c.tpsTour;
		argentDepart=c.argentDepart;
		taillePlateau=c.taillePlateau;
		argentCaseDepart=c.argentCaseDepart;
		argentSurCaseDepart=c.argentSurCaseDepart;
		nbTours=c.nbTours;
		typePartie=c.typePartie;
		reglesAdditionnelles=new ArrayList<String>(c.reglesAdditionnelles);
	}
	
	/**
	 * Renvoie la configuration correspondant au nom passé en paramètre.
	 * 
	 * @param nom
	 * Nom de la configuration à rechercher
	 * @return
	 * Configuration recherchée ou null si la configuration n'existe pas
	 */
	public static Configuration getConfig(String nom) {
		for(Configuration c : configs)if(c.nom.equals(nom))return c;
		return null;
	}

	// Renvoie le chemin du fichier de sauvegarde d'une configuration au nom donné
	private static String getChemin(String nom){
		return "configs/"+nom+".config";

	}

	public String toString() {
		return nom;
	}
	
	public static void setConfig(Configuration c) {
		config=c;
	}

	public static ObservableList<Configuration> getConfigs() {
		return configs;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}

	public int getNbBots() {
		return nbBots;
	}

	public void setNbBots(int nbBots) {
		this.nbBots = nbBots;
	}

	public String getTpsTour() {
		return tpsTour;
	}

	public void setTpsTour(String tpsTour) {
		this.tpsTour = tpsTour;
	}

	public String getArgentDepart() {
		return argentDepart;
	}

	public void setArgentDepart(String argentDepart) {
		this.argentDepart = argentDepart;
	}

	public String getTaillePlateau() {
		return taillePlateau;
	}

	public void setTaillePlateau(String taillePlateau) {
		this.taillePlateau = taillePlateau;
	}

	public String getArgentCaseDepart() {
		return argentCaseDepart;
	}

	public void setArgentCaseDepart(String argentCaseDepart) {
		this.argentCaseDepart = argentCaseDepart;
	}

	public String getArgentSurCaseDepart() {
		return argentSurCaseDepart;
	}

	public void setArgentSurCaseDepart(String argentSurCaseDepart) {
		this.argentSurCaseDepart = argentSurCaseDepart;
	}

	public String getNbTours() {
		return nbTours;
	}

	public void setNbTours(String nbTours) {
		this.nbTours = nbTours;
	}

	public String getTypePartie() {
		return typePartie;
	}

	public void setTypePartie(String typePartie) {
		this.typePartie = typePartie;
	}

	public ArrayList<String> getReglesAdditionnelles() {
		return reglesAdditionnelles;
	}

	public void setReglesAdditionnelles(ArrayList<String> reglesAdditionnelles) {
		this.reglesAdditionnelles = reglesAdditionnelles;
	}
}
