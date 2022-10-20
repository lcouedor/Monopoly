package view;

import java.util.Arrays;
import java.util.Collection;

import back.Configuration;
import back.Partie;
import back.Partie.ReglesAdditionnelles;
import back.Partie.TypesPartie;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import view.Texte.Polices;

public class ConfigView extends GridPane{
	private Configuration c;
	
	public ConfigView() {
		c = new Configuration(Configuration.getConfig());
		
		GridPane partG = new GridPane();
		partG.setStyle("-fx-background-color: #000000;");
		GridPane partD = new GridPane();
		partD.setStyle("-fx-background-color: #000000;");

		//partie header
		Texte titre = new Texte("CONFIGURATION DE PARTIE");
		titre.setPolice(Polices.COMICSTRICKS);
		titre.setSize(2);

		HBox head = new HBox();
		head.setStyle("-fx-background-color: #0097d8;");
		head.getChildren().add(titre);
		head.setAlignment(Pos.CENTER);

		//partie Config
		Texte label_configs = new Texte("MES CONFIGS");
		label_configs.setPolice(Polices.COMICSTRICKS);
		label_configs.setSize(1.5);
		label_configs.setMinWidth(USE_PREF_SIZE);

		ComboBox<Configuration> choix_config= new ComboBox<Configuration>();
		for(Configuration config : Configuration.getConfigs())choix_config.getItems().add(config);
		Configuration.getConfigs().addListener(new ListChangeListener<Configuration>() {
			@Override
			public void onChanged(@SuppressWarnings("rawtypes") Change change) {
				choix_config.getItems().clear();
				for(Configuration config : Configuration.getConfigs())choix_config.getItems().add(config);
				if(!Configuration.getConfigs().contains(c))choix_config.getEditor().setText("");
			}
		});
		choix_config.getEditor().setText(c.getNom());
		
		Utiles.setSize(choix_config, 1);
		choix_config.setEditable(true);
		
		Bouton btn_suppr = new Bouton("Supprimer");
		Bouton btn_enregistrer = new Bouton("Enregistrer");
		btn_suppr.setOnAction(e->{
			c.setNom(choix_config.getEditor().getText());
			actionBoutonConfig(btn_suppr, btn_enregistrer);
		});
		btn_suppr.setCouleurHover("#E50000");

		
		btn_enregistrer.setOnAction(e->{
			c.setNom(choix_config.getEditor().getText());
			actionBoutonConfig(btn_enregistrer, btn_suppr);
		});

		HBox configs = new HBox();
		configs.setPadding(new Insets(0, 0, 0, 30));
		configs.setStyle("-fx-background-color: #95cde5;");
		configs.setSpacing(20);
		setHBox(configs, Arrays.asList(label_configs,choix_config,btn_suppr,btn_enregistrer));

		//partie Joueurs
		Texte titre_joueurs = new Texte("JOUEURS");
		titre_joueurs.setPolice(Polices.COMICSTRICKS);
		titre_joueurs.setSize(1.5);

		Texte nbJoueurs = new Texte("Nombre de joueurs");
		setTexte(nbJoueurs);
		Spinner<Integer> boxNbJoueurs = new Spinner<Integer>(0,6,c.getNbJoueurs());
		setControl(boxNbJoueurs);
		HBox hnbJoueurs = new HBox();
		setHBox(hnbJoueurs, Arrays.asList(nbJoueurs,boxNbJoueurs, getInfo("Le total des joueurs humains et bots doit être compris entre 2 et 6", "ff0000AF", "!")));

		Texte nbBots = new Texte("Nombre de bots");
		setTexte(nbBots);
		Spinner<Integer> boxNbBots = new Spinner<Integer>(0,6,c.getNbBots());
		setControl(boxNbBots);
		HBox hnbBots = new HBox();
		setHBox(hnbBots, Arrays.asList(nbBots,boxNbBots, getInfo("Le total des joueurs humains et bots doit être compris entre 2 et 6", "ff0000AF", "!")));

		Texte tpsTour = new Texte("Temps par tour (s)");
		setTexte(tpsTour);
		ComboBox<String> boxTpsTour = new ComboBox<String>();
		setControl(boxTpsTour);
		boxTpsTour.setItems(FXCollections.observableArrayList("10", "20","30","illimité"));
		boxTpsTour.setEditable(true);
		boxTpsTour.setValue(c.getTpsTour());
		HBox hTpsTour = new HBox();
		setHBox(hTpsTour, Arrays.asList(tpsTour,boxTpsTour, getInfo("Le temps par tour doit être un entier positif ou \"illimité\"", "ff0000AF", "!")));

		Texte argentDepart = new Texte("Argent de départ");
		setTexte(argentDepart);
		TextField boxArgentDepart = new TextField(c.getArgentDepart());
		setControl(boxArgentDepart);
		HBox hArgentDepart = new HBox();
		setHBox(hArgentDepart, Arrays.asList(argentDepart,boxArgentDepart, getInfo("L'argent au début de la partie doit être un entier positif", "ff0000AF", "!")));

		VBox joueurs = new VBox();
		joueurs.getChildren().addAll(titre_joueurs,hnbJoueurs,hnbBots,hTpsTour,hArgentDepart);
		joueurs.setPadding(new Insets(0, 0, 0, 30));
		joueurs.setStyle("-fx-background-color: #95cde5;");
		joueurs.setAlignment(Pos.CENTER_LEFT);
		joueurs.setSpacing(10);

		//partie Plateau
		Texte titre_plateau = new Texte("PLATEAU");
		titre_plateau.setPolice(Polices.COMICSTRICKS);
		titre_plateau.setSize(1.5);

		Texte taillePlat = new Texte("Taille du plateau");
		setTexte(taillePlat);
		ComboBox<String> boxTaillePlat = new ComboBox<String>();
		setControl(boxTaillePlat);
		boxTaillePlat.setItems(FXCollections.observableArrayList("6x6", "8x8"));
		boxTaillePlat.setValue(c.getTaillePlateau());
		boxTaillePlat.valueProperty().addListener((val, oldval, newval)->c.setTaillePlateau(boxTaillePlat.getValue()));
		HBox hTaillePlat = new HBox();
		setHBox(hTaillePlat, Arrays.asList(taillePlat,boxTaillePlat, getInfo("", "ff0000AF", "")));

		Texte argentCaseDepart = new Texte("Argent au passage de la case départ");
		setTexte(argentCaseDepart);
		TextField boxArgentCaseDepart = new TextField(c.getArgentCaseDepart());
		setControl(boxArgentCaseDepart);
		HBox hArgentCaseDepart = new HBox();
		setHBox(hArgentCaseDepart, Arrays.asList(argentCaseDepart,boxArgentCaseDepart, getInfo("L'argent touché au passage de la case départ doit être un entier", "ff0000AF", "!")));

		Texte argentSurCaseDepart = new Texte("Argent sur la case départ");
		setTexte(argentSurCaseDepart);
		TextField boxArgentSurCaseDepart = new TextField(c.getArgentSurCaseDepart());
		setControl(boxArgentSurCaseDepart);
		HBox hArgentSurCaseDepart = new HBox();
		setHBox(hArgentSurCaseDepart, Arrays.asList(argentSurCaseDepart,boxArgentSurCaseDepart, getInfo("L'argent touché à l'arrêt sur la case départ doit être un entier", "ff0000AF", "!")));

		VBox plateau = new VBox();
		plateau.getChildren().addAll(titre_plateau,hTaillePlat,hArgentCaseDepart,hArgentSurCaseDepart);
		plateau.setPadding(new Insets(0, 0, 0, 30));
		plateau.setStyle("-fx-background-color: #95cde5;");
		plateau.setAlignment(Pos.CENTER_LEFT);
		plateau.setSpacing(10);

		//partie Type de partie
		Texte titre_type = new Texte("TYPE DE PARTIE");
		titre_type.setPolice(Polices.COMICSTRICKS);
		titre_type.setSize(1.5);
		titre_type.setPadding(new Insets(0, 0, 30, 0));

		ToggleGroup group = new ToggleGroup();

		HBox hMonop = new HBox();
		RadioButton btnMonopole = new RadioButton("Monopole");
		btnMonopole.selectedProperty().addListener((val, oldval, newval)->c.setTypePartie(btnMonopole.getText()));
		Utiles.setSize(btnMonopole, 1);
		btnMonopole.setToggleGroup(group);
		hMonop.getChildren().addAll(btnMonopole, getInfo("Le joueur ayant le plus gros capital monétaire et de propriétés au bout du nombre de tours défini gagne la partie. Dans le cas d’un nombre de tours illimité, c’est le dernier joueur encore en lice qui l’emporte.","0097d8AF","?"));
		hMonop.setPadding(new Insets(0, 0, 30, 0));
		hMonop.setAlignment(Pos.CENTER_LEFT);

		ComboBox<String> boxNbTours = new ComboBox<String>();
		Utiles.setSize(boxNbTours, 1);
		boxNbTours.setItems(FXCollections.observableArrayList("10", "50","100","illimité"));
		boxNbTours.getStyleClass().add("bouton");
		boxNbTours.setPrefWidth(350);
		boxNbTours.setValue(c.getNbTours());
		boxNbTours.valueProperty().addListener((val, oldval, newval)->c.setNbTours(boxNbTours.getValue()));
		Texte tours = new Texte("Tours");
		tours.setMinWidth(USE_PREF_SIZE);
		tours.setPadding(new Insets(0, 0, 0, 10));
		HBox hMonopole = new HBox();
		hMonopole.getChildren().addAll(boxNbTours, tours);
		hMonopole.setAlignment(Pos.CENTER_LEFT);

		HBox hMort = new HBox();
		RadioButton btnMort = new RadioButton("Mort subite");
		btnMort.selectedProperty().addListener((val, oldval, newval)->c.setTypePartie(btnMort.getText()));
		Utiles.setSize(btnMort, 1);
		btnMort.setToggleGroup(group);
		hMort.getChildren().addAll(btnMort, getInfo("Le premier joueur à être ruiné fait se stopper la partie, le joueur ayant le plus gros capital monétaire et de propriétés gagne la partie.", "0097d8AF", "?"));
		hMort.setPadding(new Insets(0, 0, 30, 0));

		HBox hInvest = new HBox();
		RadioButton btnInvest = new RadioButton("Investisseur");
		btnInvest.selectedProperty().addListener((val, oldval, newval)->c.setTypePartie(btnInvest.getText()));
		Utiles.setSize(btnInvest, 1);
		btnInvest.setToggleGroup(group);
		hInvest.getChildren().addAll(btnInvest, getInfo("Une fois que toutes les propriétés ont été achetées, la partie se stoppe. Le joueur ayant le capital monétaire le plus important et de propriétés gagne la partie.", "0097d8AF", "?"));
		hInvest.setPadding(new Insets(0, 0, 30, 0));

		HBox hBatisseur = new HBox();
		RadioButton btnBatisseur = new RadioButton("Bâtisseur");
		btnBatisseur.selectedProperty().addListener((val, oldval, newval)->c.setTypePartie(btnBatisseur.getText()));
		Utiles.setSize(btnBatisseur, 1);
		btnBatisseur.setToggleGroup(group);
		hBatisseur.getChildren().addAll(btnBatisseur, getInfo("Dès qu’un joueur a acheté 5 matériels, la partie se termine. Le joueur ayant le capital monétaire le plus important et de matériels gagne la partie.", "0097d8AF", "?"));
		hBatisseur.setPadding(new Insets(0, 0, 30, 0));
		switch(c.getTypePartie()) {
		case "Monopole":
			btnMonopole.setSelected(true);
			break;
		case "Mort subite":
			btnMort.setSelected(true);
			break;
		case "Investisseur":
			btnInvest.setSelected(true);
			break;
		case "Bâtisseur":
			btnBatisseur.setSelected(true);
			break;
		default:
			btnMonopole.setSelected(true);	
		}

		GridPane gridType = new GridPane(); 
		gridType.setPadding(new Insets(0, 0, 0, 30));
		gridType.setStyle("-fx-background-color: #95cde5;");
		gridType.setAlignment(Pos.CENTER_LEFT);

		VBox type = new VBox();
		type.getChildren().addAll(titre_type,hMonopole,hMort,hInvest,hBatisseur);
		type.setPadding(new Insets(0, 0, 0, 30));
		type.setStyle("-fx-background-color: #95cde5;");
		type.setAlignment(Pos.CENTER_LEFT);

		//partie Règles additionnelles
		Texte titre_regles = new Texte("REGLES ADDITIONNELLES");
		titre_regles.setPolice(Polices.COMICSTRICKS);
		titre_regles.setSize(1.5);
		titre_regles.setPadding(new Insets(0, 0, 30, 0));

		HBox hCoup = new HBox();
		CheckBox boxCoupDuSort = new CheckBox("Coup du sort");
		boxCoupDuSort.setSelected(c.getReglesAdditionnelles().contains(boxCoupDuSort.getText()));
		boxCoupDuSort.selectedProperty().addListener((val, oldval, newval)->{
			if(newval)c.getReglesAdditionnelles().add(boxCoupDuSort.getText());
			if(oldval)c.getReglesAdditionnelles().remove(boxCoupDuSort.getText());
		});
		Utiles.setSize(boxCoupDuSort, 1);
		hCoup.getChildren().addAll(boxCoupDuSort, getInfo("Au début de son tour, le joueur commence par piocher une carte chance.", "0097d8AF", "?"));
		hCoup.setPadding(new Insets(0, 0, 10, 0));

		HBox hCrise = new HBox();
		CheckBox boxCriseImmobilier = new CheckBox("Crise de l'immobilier");
		boxCriseImmobilier.setSelected(c.getReglesAdditionnelles().contains(boxCriseImmobilier.getText()));
		boxCriseImmobilier.selectedProperty().addListener((val, oldval, newval)->{
			if(newval)c.getReglesAdditionnelles().add(boxCriseImmobilier.getText());
			if(oldval)c.getReglesAdditionnelles().remove(boxCriseImmobilier.getText());
		});
		Utiles.setSize(boxCriseImmobilier, 1);
		hCrise.getChildren().addAll(boxCriseImmobilier, getInfo("Les coûts d’achat des propriétés ainsi que les loyers voient leur valeur augmenter.", "0097d8AF", "?"));
		hCrise.setPadding(new Insets(0, 0, 10, 0));

		HBox hRedis = new HBox();
		CheckBox boxRedistribution = new CheckBox("Redistribution des richesses");
		boxRedistribution.setSelected(c.getReglesAdditionnelles().contains(boxRedistribution.getText()));
		boxRedistribution.selectedProperty().addListener((val, oldval, newval)->{
			if(newval)c.getReglesAdditionnelles().add(boxRedistribution.getText());
			if(oldval)c.getReglesAdditionnelles().remove(boxRedistribution.getText());
		});
		Utiles.setSize(boxRedistribution, 1);
		hRedis.getChildren().addAll(boxRedistribution, getInfo("A la fin de chaque tour de jeu, le joueur le plus riche redistribue une partie de son capital financier équitablement entre tous les autres joueurs.", "0097d8AF", "?"));
		hRedis.setPadding(new Insets(0, 0, 30, 0));

		Bouton retour = new Bouton("Retour",1.2);
		retour.setCursor(Cursor.HAND);
		retour.setPrefWidth(350);
		Bouton suivant = new Bouton("Valider",1.2);
		suivant.setDefaultButton(true);
		suivant.setCursor(Cursor.HAND);
		suivant.setPrefWidth(350);
		HBox hbtn = new HBox();
		hbtn.getChildren().addAll(retour,suivant);
		hbtn.setAlignment(Pos.CENTER_RIGHT);
		hbtn.setPadding(new Insets(0, 30, 0, 0));
		hbtn.setSpacing(10);

		VBox regles = new VBox();
		regles.getChildren().addAll(titre_regles, hCoup, hCrise, hRedis, hbtn);
		regles.setPadding(new Insets(0, 0, 0, 30));
		regles.setStyle("-fx-background-color: #95cde5;");
		regles.setAlignment(Pos.CENTER_LEFT);
		
		choix_config.getEditor().textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				newValue=newValue.replaceAll("\\s+","");
				choix_config.getEditor().setText(newValue);
				if(Configuration.getConfig(newValue)!=null) {
					c=new Configuration(Configuration.getConfig(newValue));
					boxNbJoueurs.getValueFactory().setValue(c.getNbJoueurs());
					boxNbBots.getValueFactory().setValue(c.getNbBots());
					boxTpsTour.setValue(c.getTpsTour());
					boxArgentDepart.setText(c.getArgentDepart());
					boxTaillePlat.setValue(c.getTaillePlateau());
					boxArgentCaseDepart.setText(c.getArgentCaseDepart());
					boxArgentSurCaseDepart.setText(c.getArgentSurCaseDepart());
					boxNbTours.setValue(c.getNbTours());
					switch(c.getTypePartie()) {
					case "Monopole":
						btnMonopole.setSelected(true);
						break;
					case "Mort subite":
						btnMort.setSelected(true);
						break;
					case "Investisseur":
						btnInvest.setSelected(true);
						break;
					case "Bâtisseur":
						btnBatisseur.setSelected(true);
						break;
					default:
						btnMonopole.setSelected(true);	
					}
					boxCoupDuSort.setSelected(c.getReglesAdditionnelles().contains(boxCoupDuSort.getText()));
					boxCriseImmobilier.setSelected(c.getReglesAdditionnelles().contains(boxCriseImmobilier.getText()));
					boxRedistribution.setSelected(c.getReglesAdditionnelles().contains(boxRedistribution.getText()));
				}
				c.sauvegarderConfig();
			}
			
		});
		
		

		plateau.setStyle("-fx-background-color: #95cde5;");

		this.setStyle("-fx-background-color: #000000;");

		choix_config.getStyleClass().add("bouton");

		choix_config.setCursor(Cursor.HAND);
		choix_config.setMinWidth(200);
		btn_suppr.setPrefWidth(250);
		btn_enregistrer.setPrefWidth(250);


		ColumnConstraints col50 = Utiles.getCC(50);
		ColumnConstraints col100 = Utiles.getCC(100);
		RowConstraints rc = Utiles.getRC();

		this.getColumnConstraints().addAll(col50,col50);
		partG.getColumnConstraints().add(col100);
		partG.getRowConstraints().addAll(rc,rc);
		partD.getColumnConstraints().add(col100);
		partD.getRowConstraints().addAll(rc,rc);

		gridType.getColumnConstraints().addAll(col50,col50);
		gridType.getRowConstraints().addAll(rc,rc,rc,rc,rc);
		gridType.add(titre_type, 0, 0, 1, 1);
		gridType.add(hMonop, 0, 1, 1, 1);
		gridType.add(hMonopole, 1, 1, 1, 1);
		gridType.add(hMort, 0, 2, 1, 1);
		gridType.add(hInvest, 0, 3, 1, 1);
		gridType.add(hBatisseur, 0, 4, 1, 1);

		this.getRowConstraints().addAll(rc,rc,rc);

		this.setHgap(5);
		this.setVgap(5);
		partD.setVgap(5);
		partG.setVgap(5);

		partG.add(joueurs, 0, 0, 1, 1);
		partG.add(plateau, 0, 1, 1, 1);
		partD.add(gridType, 0, 0, 1, 1);
		partD.add(regles, 0, 1, 1, 1);

		this.add(head, 0, 0, 2, 1);
		this.add(configs, 0, 1, 2, 1);
		this.add(partG, 0, 2, 1, 1);
		this.add(partD, 1, 2, 1, 1);

		boxNbJoueurs.getEditor().textProperty().addListener((val, oldval, newval)->{
			try{
				boolean max = Integer.parseInt(newval) + Integer.parseInt(boxNbBots.getValue().toString()) > 6;
				boolean min = Integer.parseInt(newval) + Integer.parseInt(boxNbBots.getValue().toString()) < 2;
				
				suivant.setDisable(min||max);
				btn_enregistrer.setDisable(min||max);
				hnbJoueurs.getChildren().get(2).setVisible(min||max);
				hnbBots.getChildren().get(2).setVisible(min||max);

				if (hArgentSurCaseDepart.getChildren().get(2).isVisible() || hArgentCaseDepart.getChildren().get(2).isVisible() || hArgentDepart.getChildren().get(2).isVisible() || hTpsTour.getChildren().get(2).isVisible() || hnbBots.getChildren().get(2).isVisible() || hnbJoueurs.getChildren().get(2).isVisible()) {
					suivant.setDisable(true);
				}
				c.setNbJoueurs(Integer.parseInt(newval));
			}catch(Exception e){
				hnbJoueurs.getChildren().get(2).setVisible(true);
				suivant.setDisable(true);
			}
		});

		boxNbBots.getEditor().textProperty().addListener((val, oldval, newval)->{
			try {
				boolean max = Integer.parseInt(newval) + Integer.parseInt(boxNbJoueurs.getValue().toString()) > 6;
				boolean min = Integer.parseInt(newval) + Integer.parseInt(boxNbJoueurs.getValue().toString()) < 2;
				
				suivant.setDisable(min||max);
				btn_enregistrer.setDisable(min||max);
				hnbJoueurs.getChildren().get(2).setVisible(min||max);
				hnbBots.getChildren().get(2).setVisible(min||max);

				if (hArgentSurCaseDepart.getChildren().get(2).isVisible() || hArgentCaseDepart.getChildren().get(2).isVisible() || hArgentDepart.getChildren().get(2).isVisible() || hTpsTour.getChildren().get(2).isVisible() || hnbBots.getChildren().get(2).isVisible() || hnbJoueurs.getChildren().get(2).isVisible()) {
					suivant.setDisable(true);
					
				}
				c.setNbBots(Integer.parseInt(newval));
			}catch(Exception e) {
				hnbBots.getChildren().get(2).setVisible(true);
				suivant.setDisable(true);
			}
		});
		
		ChangeListener<String> changeBoxTpsTour = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				suivant.setDisable(!newValue.toString().matches("[0-9]+|illimité"));
				btn_enregistrer.setDisable(!newValue.toString().matches("[0-9]+|illimité"));
				hTpsTour.getChildren().get(2).setVisible(!newValue.toString().matches("[0-9]+|illimité"));

				if (hArgentSurCaseDepart.getChildren().get(2).isVisible() || hArgentCaseDepart.getChildren().get(2).isVisible() || hArgentDepart.getChildren().get(2).isVisible() || hTpsTour.getChildren().get(2).isVisible() || hnbBots.getChildren().get(2).isVisible() || hnbJoueurs.getChildren().get(2).isVisible()) {
					suivant.setDisable(true);
				}else {
					c.setTpsTour(newValue);
				}
			}
		};

		boxTpsTour.getEditor().textProperty().addListener(changeBoxTpsTour);

		boxArgentDepart.textProperty().addListener(e->{
			suivant.setDisable(!boxArgentDepart.getText().matches("[0-9]+"));
			btn_enregistrer.setDisable(!boxArgentDepart.getText().matches("[0-9]+"));
			hArgentDepart.getChildren().get(2).setVisible(!boxArgentDepart.getText().matches("[0-9]+"));

			if (hArgentSurCaseDepart.getChildren().get(2).isVisible() || hArgentCaseDepart.getChildren().get(2).isVisible() || hArgentDepart.getChildren().get(2).isVisible() || hTpsTour.getChildren().get(2).isVisible() || hnbBots.getChildren().get(2).isVisible() || hnbJoueurs.getChildren().get(2).isVisible()) {
				suivant.setDisable(true);
			}else {
				c.setArgentDepart(boxArgentDepart.getText());
			}
		});

		boxArgentCaseDepart.textProperty().addListener(e->{
			suivant.setDisable(!boxArgentCaseDepart.getText().matches("-?[0-9]+"));
			btn_enregistrer.setDisable(!boxArgentCaseDepart.getText().matches("-?[0-9]+"));
			hArgentCaseDepart.getChildren().get(2).setVisible(!boxArgentCaseDepart.getText().matches("-?[0-9]+"));

			if (hArgentSurCaseDepart.getChildren().get(2).isVisible() || hArgentCaseDepart.getChildren().get(2).isVisible() || hArgentDepart.getChildren().get(2).isVisible() || hTpsTour.getChildren().get(2).isVisible() || hnbBots.getChildren().get(2).isVisible() || hnbJoueurs.getChildren().get(2).isVisible()) {
				suivant.setDisable(true);
			}else {
				c.setArgentCaseDepart(boxArgentCaseDepart.getText());
			}
		});


		boxArgentSurCaseDepart.textProperty().addListener(e->{
			suivant.setDisable(!boxArgentSurCaseDepart.getText().matches("-?[0-9]+"));
			btn_enregistrer.setDisable(!boxArgentSurCaseDepart.getText().matches("-?[0-9]+"));
			hArgentSurCaseDepart.getChildren().get(2).setVisible(!boxArgentSurCaseDepart.getText().matches("-?[0-9]+"));
			
			if (hArgentSurCaseDepart.getChildren().get(2).isVisible() || hArgentCaseDepart.getChildren().get(2).isVisible() || hArgentDepart.getChildren().get(2).isVisible() || hTpsTour.getChildren().get(2).isVisible() || hnbBots.getChildren().get(2).isVisible() || hnbJoueurs.getChildren().get(2).isVisible()) {
				suivant.setDisable(true);
			}else {
				c.setArgentSurCaseDepart(boxArgentSurCaseDepart.getText());
			}
		});
		
		retour.setOnAction(e->{
			Configuration.setConfig(new Configuration(c));
			MonopolyIUT.setScene(new AccueilView());
		});
		
		suivant.setOnAction(e->{
			Configuration.setConfig(new Configuration(c));
			boxTpsTour.getEditor().textProperty().removeListener(changeBoxTpsTour);
			if(boxTpsTour.getValue().equals("illimité"))boxTpsTour.setValue(Integer.toString(-1));
			if(boxNbTours.getValue().equals("illimité"))boxNbTours.setValue(Integer.toString(-2));
			MonopolyIUT.setPartie(new Partie(
					Integer.parseInt(boxArgentDepart.getText()), 
					Integer.parseInt(boxArgentCaseDepart.getText()), 
					Integer.parseInt(boxArgentSurCaseDepart.getText()), 
					(Integer.parseInt(boxTaillePlat.getValue().toString().substring(0,1))-1)*4, 
					Integer.parseInt(boxTpsTour.getValue()),
					btnMonopole.isSelected()?Integer.parseInt(boxNbTours.getValue().toString()):-2));
			if(boxCriseImmobilier.isSelected())MonopolyIUT.getPartie().ajouterRegle(ReglesAdditionnelles.CRISE_IMMOBILIER);
			if(boxCoupDuSort.isSelected())MonopolyIUT.getPartie().ajouterRegle(ReglesAdditionnelles.COUP_DU_SORT);
			if(boxRedistribution.isSelected())MonopolyIUT.getPartie().ajouterRegle(ReglesAdditionnelles.REDISTRIBUTION_RICHESSES);
			if(btnMonopole.isSelected())MonopolyIUT.getPartie().setTypePartie(TypesPartie.MONOPOLE);
			if(btnMort.isSelected())MonopolyIUT.getPartie().setTypePartie(TypesPartie.MORT_SUBITE);
			if(btnInvest.isSelected())MonopolyIUT.getPartie().setTypePartie(TypesPartie.INVESTISSEUR);
			if(btnBatisseur.isSelected())MonopolyIUT.getPartie().setTypePartie(TypesPartie.BATISSEUR);
			MonopolyIUT.setScene(new JoueursView(Integer.parseInt(boxNbJoueurs.getValue().toString()),Integer.parseInt(boxNbBots.getValue().toString())));
		});



	}

	public StackPane getInfo(String s, String couleur, String logo) {
		StackPane stack = new StackPane();
		Circle cercle = new Circle(15);
		cercle.radiusProperty().bind(MonopolyIUT.getFont().divide(2));
		cercle.setStyle("-fx-fill : #"+couleur+";");
		Texte i = new Texte(logo, 1);
		i.getStyleClass().add("white");
		i.setMouseTransparent(true);
		stack.getChildren().addAll(cercle, i);
		if (couleur == "ff0000AF") {
			stack.setVisible(false);
		}
		Tooltip tooltip=new Tooltip(s);
		tooltip.setStyle("-fx-background-color : #"+couleur+"; -fx-font-size : 20px; -fx-background-radius : 0px;");
		tooltip.setTextAlignment(TextAlignment.JUSTIFY);
		tooltip.setWrapText(true);
		tooltip.setMaxWidth(350);
		installTooltip(cercle,tooltip);
		stack.setPadding(new Insets(0, 20, 0, 20));
		return stack;
	}
	
	private void installTooltip(Node n, Tooltip t) {
		t.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				t.setAnchorX(event.getScreenX());
				t.setAnchorY(event.getScreenY());
				try{if(!n.contains(n.screenToLocal(event.getScreenX(), event.getScreenY())))t.hide();}catch(Exception e) {t.hide();}
			}
		});
		n.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				t.show(n, event.getScreenX(), event.getScreenY());
				try{if(!n.contains(n.screenToLocal(event.getScreenX(), event.getScreenY())))t.hide();}catch(Exception e) {t.hide();}
			}
		});
	}
	
	public void actionBoutonConfig(Bouton bouton, Bouton autreBouton) {
		bouton.disable(true, false);
		autreBouton.setDisable(true);
		if(bouton.getText().equals("Enregistrer")) {
			if(c.sauvegarderConfig()) {
				bouton.setText("Enregistré");
				bouton.setStyle("-fx-border-color:transparent;-fx-background-color:transparent;-fx-text-fill:green");
			}else {
				bouton.setText("Erreur");
				bouton.setStyle("-fx-border-color:transparent;-fx-background-color:transparent;-fx-text-fill:red");
			}
			PauseTransition pause = new PauseTransition(Duration.millis(2000));
			pause.setOnFinished(e->{
				bouton.setStyle("");
				bouton.setText("Enregistrer");
				bouton.disable(false, false);
				autreBouton.setDisable(false);
			});
			pause.play();
		}else if(bouton.getText().equals("Supprimer")){
			if(c.supprimerConfig()) {
				bouton.setText("Supprimé");
				bouton.setStyle("-fx-border-color:transparent;-fx-background-color:transparent;-fx-text-fill:green");
			}else {
				bouton.setText("Erreur");
				bouton.setStyle("-fx-border-color:transparent;-fx-background-color:transparent;-fx-text-fill:red");
			}
			PauseTransition pause = new PauseTransition(Duration.millis(2000));
			pause.setOnFinished(e->{
				bouton.setStyle("");
				bouton.setText("Supprimer");
				bouton.disable(false, false);
				autreBouton.setDisable(false);
			});
			pause.play();
		}
	}
	
	private void setTexte(Texte t) {
		t.setMinWidth(USE_PREF_SIZE);
		HBox.setHgrow(t, Priority.ALWAYS);
		t.setMaxWidth(Integer.MAX_VALUE);
	}
	
	private void setControl(Control c) {
		Utiles.setSize(c, 1);
		c.minWidthProperty().bind(widthProperty().divide(5));
		c.maxWidthProperty().bind(widthProperty().divide(5));
		c.getStyleClass().add("bouton");
	}

	private void setHBox(HBox h, Collection<Node> c) {
		h.getChildren().addAll(c);
		h.setAlignment(Pos.CENTER_LEFT);
	}
	
}
