package back;

/**
 * 
 * Cette classe Modificateur permet de créer des modificateurs.
 * Un modificateur se caractérise par son temps, les dés, la réduction du temps, le nombre de tours à passer, le sens de déplacement, l'alternance, le départ,
 * siil rejoue  et le ds.
 *
 */
public class Modificateur {
	private int temps;
	private int des;
	private double reduireCout;
	private int passerTour;
	private int sensDeplacement;
	private int alternance;
	private int depart;
	private boolean rejoue;
	private int ds;
	private int toucheBourse;
	
	/**
	 * Le constructeur de la classe Modificateur permet de créer un nouveau modificateur ou tout est mis à 0. 	
	 */
	//Constructeur
	public Modificateur() {
		this.temps = 0;
		this.des = 0;
		this.reduireCout = 0;
		this.passerTour = 0;
		this.sensDeplacement = 1;
		this.alternance = 0;
		this.depart = 0;
		this.rejoue = false;
		this.ds = 0;
		this.toucheBourse = 1;
	}

	
	//Getter et Setter
	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public int getDes() {
		return des;
	}

	public void setDes(int des) {
		this.des = des;
	}

	public double getReduireCout() {
		return reduireCout;
	}

	public void setReduireCout(double reduireCout) {
		this.reduireCout = reduireCout;
	}

	public int getPasserTour() {
		return passerTour;
	}

	public void setPasserTour(int passerTour) {
		this.passerTour = passerTour;
	}

	public int getSensDeplacement() {
		return sensDeplacement;
	}

	public void setSensDeplacement(boolean sensDeplacement) {
		this.sensDeplacement = sensDeplacement?1:-1;
	}

	public int getAlternance() {
		return alternance;
	}

	public void setAlternance(int alternance) {
		this.alternance = alternance;
	}

	public int getDepart() {
		return depart;
	}

	public void setDepart(int depart) {
		this.depart = depart;
	}

	public boolean isRejoue() {
		return rejoue;
	}

	public void setRejoue(boolean rejoue) {
		this.rejoue = rejoue;
	}

	public int getDs() {
		return ds;
	}

	public void setDs(int ds) {
		this.ds = ds;
	}

	public int getToucheBourse() {
		return toucheBourse;
	}

	public void setToucheBourse(int toucheBourse) {
		this.toucheBourse = toucheBourse;
	}

	public void setSensDeplacement(int sensDeplacement) {
		this.sensDeplacement = sensDeplacement;
	}


	
	
	
	
	

	
}
