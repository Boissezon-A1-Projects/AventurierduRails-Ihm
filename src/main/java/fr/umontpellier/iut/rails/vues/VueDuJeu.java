package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.RailsIHM;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

import java.util.List;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les cartes Transport visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private final IJeu jeu;
    private VuePlateau plateau;

    private VueJoueurCourant vueJoueurCourant;

    private VueAutresJoueursGauche vueJoueurGauche;

    private VueAutresJoueursDroite vueJoueurDroite;

    private VueAutresJoueursHaut vueJoueurHaut;

    private Button passer;

    private Label instruction;
    private HBox bas;
    private VBox destinations;
    private TextField fieldNbPions;


    private Label piocheWagon;
    private Label piocheBateau;
    private Label piocheDestination;

    private VBox cartesVisibles;

    private Label boutonsWagons;
    private Label boutonsBateaux;

    private BooleanProperty choisiDesDestinations;

    private Popup popupInfo;

    private Font f;
     private Popup popupDestinations = new Popup();


    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        Image im = new Image("images/plancher-bois-brun.jpg");
        BackgroundSize bs = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true,true,true,true);
        BackgroundImage i = new BackgroundImage(im, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,bs );
        Background bg = new Background(i);
        setBackground(bg);


        HBox plat = new HBox();
        plateau = new VuePlateau();
        plat.getChildren().add(plateau);
        passer = new Button("Passer");
        HBox instr = new HBox();
        instruction = new Label();
        instruction.setFont(Font.font("Book Antiqua", FontWeight.BOLD,16));

        instr.getChildren().add(instruction);
        instr.setAlignment(Pos.CENTER);
        instruction.setWrapText(true);
        instruction.setTextAlignment(TextAlignment.CENTER); instruction.setTextFill(Paint.valueOf("#f3f1d3"));
        destinations = new VBox();
        fieldNbPions = new TextField();
        fieldNbPions.setVisible(false); fieldNbPions.setDisable(true); fieldNbPions.setFont(Font.font("Book Antiqua", FontWeight.BOLD,16));;
        VBox droite = new VBox();
        droite.setSpacing(10);
        passer.setFont(Font.font("Book Antiqua",FontWeight.BOLD,15));
        VBox passeretTextField = new VBox(); passeretTextField.getChildren().addAll(fieldNbPions,passer); passeretTextField.setSpacing(10);
        passeretTextField.setAlignment(Pos.CENTER);
        droite.getChildren().addAll(instr, destinations ,passeretTextField);
        droite.setPrefWidth(300);
        droite.setAlignment(Pos.CENTER);
        HBox vraiCentre = new HBox();
        vraiCentre.setPadding(new Insets(0,10,0,0));
        vraiCentre.getChildren().addAll(plat);
        setCenter(vraiCentre);
        setRight(droite);
        destinations.setAlignment(Pos.CENTER); destinations.setSpacing(4);
        fieldNbPions.setMinSize(50,25);
        fieldNbPions.setMaxSize(50,25);
        droite.setAlignment(Pos.CENTER_LEFT);
        droite.setPadding(new Insets(0,10,0,-10));
        // Instancie les vues de joueurs
        vueJoueurCourant = new VueJoueurCourant();
        vueJoueurDroite = new VueAutresJoueursDroite();
        vueJoueurGauche = new VueAutresJoueursGauche();
        vueJoueurHaut = new VueAutresJoueursHaut();
        bas =new HBox();
        bas.getChildren().addAll(vueJoueurCourant);
        bas.setAlignment(Pos.CENTER);
        setBottom(bas);

        //choses pour plus tard
        ImageView imageBateauPioche = new ImageView("images/cartesWagons/dos-BATEAU.png");
        imageBateauPioche.setFitWidth(100); imageBateauPioche.setFitHeight(160);
        piocheBateau = new Label(); piocheBateau.setGraphic(imageBateauPioche);
        ImageView imageWagonPioche =new ImageView("images/cartesWagons/dos-WAGON.png") ;
        imageWagonPioche.setFitWidth(100); imageWagonPioche.setFitHeight(160);
        piocheWagon = new Label(); piocheWagon.setGraphic(imageWagonPioche);
        ImageView imageDestiPioche = new ImageView("images/cartesWagons/destinations.png");
        imageDestiPioche.setFitWidth(215); imageDestiPioche.setFitHeight(135);
        piocheDestination = new Label(); piocheDestination.setGraphic(imageDestiPioche);

        ImageView imageBoutonBateau = new ImageView("images/bouton-pions-bateau.png");
        imageBoutonBateau.setFitHeight(61); imageBoutonBateau.setFitWidth(60);
        boutonsBateaux = new Label(); boutonsBateaux.setGraphic(imageBoutonBateau);
        ImageView imageBoutonWagon = new ImageView("images/bouton-pions-wagon.png");
        imageBoutonWagon.setFitHeight(61); imageBoutonWagon.setFitWidth(60);
        boutonsWagons = new Label(); boutonsWagons.setGraphic(imageBoutonWagon);
        cartesVisibles = new VBox();
        cartesVisibles.setSpacing(-50);


        // Place les vues des joueurs

        choisiDesDestinations= new SimpleBooleanProperty();

    }


    //change les vues des joueurs
    ChangeListener<IJoueur> changeCourant = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur joueurCourant) {

            List<IJoueur> listJoueurs = (List<IJoueur>) jeu.getJoueurs();
            int indiceJoueurCourant=0;


            for (int i = 0; i < listJoueurs.size(); i++) {
                if(listJoueurs.get(i).equals((joueurCourant))){
                    indiceJoueurCourant=i;
                    break;
                }
            }

            if(jeu.getJoueurs().size() == 4){
            vueJoueurDroite.setUp(listJoueurs.get((indiceJoueurCourant+1)%4));
            vueJoueurHaut.setUp(listJoueurs.get((indiceJoueurCourant+2)%4));
            vueJoueurGauche.setUp(listJoueurs.get((indiceJoueurCourant+3)%4));
            }
            else if(jeu.getJoueurs().size() == 2){
                vueJoueurHaut.setUp(listJoueurs.get((indiceJoueurCourant+1)%2));
            }
            else if(jeu.getJoueurs().size() == 3) {
                vueJoueurGauche.setUp(listJoueurs.get((indiceJoueurCourant + 2) % 3));
                vueJoueurHaut.setUp(listJoueurs.get((indiceJoueurCourant + 1) % 3));
            }
        }
    };

    //pour prendre des destination
    EventHandler<MouseEvent> actionBoutonDestination = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            VueDestination v = (VueDestination) mouseEvent.getSource();
            destinations.getChildren().remove(v);
            jeu.uneDestinationAEteChoisie(v.getDestination());
        }
    };


    //pions choisi lors de la phase preparation
    EventHandler<KeyEvent> nbPionsAEteChoisi = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if( keyEvent.getCode().equals(KeyCode.ENTER)) {


                    jeu.leNombreDePionsSouhaiteAEteRenseigne(fieldNbPions.getText());

            }
        }
    };


    //changer le label instruction
    ChangeListener<String> instructionChange = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
            if(t1.equals("Saisissez un nombre de pions wagon entre 10 et 25")){
                fieldNbPions.clear();
                fieldNbPions.setDisable(false);
                fieldNbPions.setVisible(true);
            }

            if(!t1.contains("Saisissez un nombre de pions")){
                fieldNbPions.clear();
                fieldNbPions.setDisable(true);
                fieldNbPions.setVisible(false);
            }
        }
    };


    //change la scene quand plus en preparation
    ChangeListener<Boolean> estEnPreparation = (observableValue, aBoolean, t1) -> changerLePlateauDeJeu();

    public void changerLePlateauDeJeu(){

        //centre
        VBox nvCentre = new VBox();
        nvCentre.setAlignment(Pos.CENTER);
        nvCentre.setSpacing(5);

        //pioches wagons et bateaux
        HBox wagonsETBateauxPioches = new HBox();
        wagonsETBateauxPioches.getChildren().addAll(piocheWagon,piocheBateau); wagonsETBateauxPioches.setSpacing(15);

        //cartes visibles et boutons wagons/bateaux
         HBox cartesVEtBoutons = new HBox();
        VBox boutons = new VBox();
        VBox boutonPasserBox = new VBox(); boutonPasserBox.getChildren().addAll(new HBox(),passer);
        boutonPasserBox.setSpacing(50);
        boutons.getChildren().addAll(boutonsBateaux,boutonsWagons,boutonPasserBox);
        boutons.setAlignment(Pos.CENTER); boutons.setSpacing(5);
        cartesVEtBoutons.getChildren().addAll(cartesVisibles,boutons);
        cartesVEtBoutons.setSpacing(10);

        //Vbox avec toutes les pioches et les cartes visibles et les boutons
        VBox pioches = new VBox();
        pioches.setSpacing(10);
        pioches.getChildren().addAll(wagonsETBateauxPioches,piocheDestination, cartesVEtBoutons);
        pioches.setAlignment(Pos.CENTER_LEFT);
        pioches.setPadding(new Insets(0,30,0,0));
        //contient le plateau et les pioches
        HBox plateauEtPioches = new HBox();
        plateauEtPioches.setSpacing(30);
        nvCentre.setPadding(new Insets(10));
        HBox plat = new HBox(plateau);
        plateauEtPioches.getChildren().addAll(plat, pioches);
        instruction.setFont(Font.font("Book Antiqua", FontWeight.BOLD,20));
        nvCentre.getChildren().addAll(instruction,plateauEtPioches);
        setCenter(nvCentre);
        plateau.creerBindings();

        //bas avec vuejoueurcourant + passer
        HBox nvBas = new HBox();

        nvBas.getChildren().addAll(vueJoueurCourant);
        nvBas.setAlignment(Pos.CENTER);
        setBottom(nvBas);
        nvBas.setSpacing(100);

        //aux autres endroits les vues autres joueurs
        if(jeu.getJoueurs().size() == 4){
            HBox h = new HBox(); h.getChildren().add(vueJoueurDroite); h.setAlignment(Pos.CENTER);
            setRight(h);
            HBox h2 = new HBox(); h2.getChildren().add(vueJoueurGauche); h2.setAlignment(Pos.CENTER);
            setLeft(h2);

        }
        else if(jeu.getJoueurs().size() == 3){
            HBox h2 = new HBox(); h2.getChildren().add(vueJoueurGauche); h2.setAlignment(Pos.CENTER);
            setLeft(h2);
        }else if(jeu.getJoueurs().size()==2){
        }
        VBox v = new VBox(); v.getChildren().add(vueJoueurHaut); v.setAlignment(Pos.CENTER);
        setTop(v);


    }


    //pour changer les destinations lors de la phase de prepa
    ListChangeListener<IDestination> lesDestinationsInitialesChangent = new ListChangeListener<IDestination>() {
        @Override
        public void onChanged(Change<? extends IDestination> change) {
            while(change.next()){
                if(change.wasAdded()){
                    for (IDestination destination: change.getAddedSubList()) {
                        VueDestination labelDestination = new VueDestination(destination);
                        labelDestination.addEventHandler(MouseEvent.MOUSE_CLICKED, actionBoutonDestination);
                        labelDestination.setFont(Font.font("Book Antiqua",14));
                        destinations.getChildren().add(labelDestination);
                    }
                }
                if(change.wasRemoved()){
                    int i =0;
                    for (IDestination destination: change.getRemoved()) {
                        VueDestination asuppr = trouverLabelDansDestinations(destinations.getChildren(), destination);
                        destinations.getChildren().remove(asuppr);
                    }
                }
            }
        }
    };

    //trouver la desti a partir de label
    public VueDestination trouverLabelDansDestinations(List<Node> conteneur, IDestination desti){
        for (Node n: conteneur) {
            VueDestination l = (VueDestination) n;
            if(l.getText().equals(desti.getVilles().toString())){
                return l;
            }
        }
        return  null;
    }

    EventHandler<MouseEvent> carteVisiblesClickée = mouseEvent -> {
        VueCarteTransport v = (VueCarteTransport) mouseEvent.getSource();
        getJeu().uneCarteTransportAEteChoisie(v.getCarteTransport());
    };


    ListChangeListener<ICarteTransport> lesCartesVisiblesChangent = new ListChangeListener<ICarteTransport>() {
        @Override
        public void onChanged(Change<? extends ICarteTransport> change) {
            while (change.next()){
                if(change.wasAdded()){
                    for (ICarteTransport carteVisibles: change.getAddedSubList()) {
                        VueCarteTransport v = new VueCarteTransport(carteVisibles,1,90,126);
                        cartesVisibles.getChildren().add(v);
                        v.addEventHandler(MouseEvent.MOUSE_CLICKED,carteVisiblesClickée);
                    }
                }
                if(change.wasRemoved()){
                    for (ICarteTransport carteVisible: change.getRemoved()) {
                        VueCarteTransport v = new VueCarteTransport(carteVisible,1,90,126);
                        supprimerCarteVisible(v);
                    }
                }
            }
        }
    };



    public void supprimerCarteVisible(VueCarteTransport v){
        for (Node n: cartesVisibles.getChildren() ) {
            VueCarteTransport v1 = (VueCarteTransport) n;
            if(v.getCarteTransport().equals(v1.getCarteTransport())){
                cartesVisibles.getChildren().remove(n);
                break;
            }
        }
    }


    EventHandler<MouseEvent> piocheWagonsClickée = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent){
            System.out.println("appel pioche w");
                jeu.uneCarteWagonAEtePiochee();
        }
    };

    EventHandler<MouseEvent> piocheBateauClickée = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {

            System.out.println("appel pioche b");
            jeu.uneCarteBateauAEtePiochee();
        }
    };

    EventHandler<MouseEvent> piocheDestiClickée = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            jeu.nouvelleDestinationDemandee();
            choisiDesDestinations.setValue(true);

            // La popup
            VBox fondPopup = new VBox();
            fondPopup.getChildren().addAll(destinations);
            popupDestinations.getContent().addAll(fondPopup);
            popupDestinations.setHeight(210);
            popupDestinations.setWidth(500);

            Point2D point = piocheDestination.localToScene(0.0, 0.0);
            popupDestinations.setX(RailsIHM.getPrimaryStage().getX() + point.getX() + 10);
            popupDestinations.setY(RailsIHM.getPrimaryStage().getY() + point.getY() + 50);

            popupDestinations.show(RailsIHM.getPrimaryStage());
        }
    };

    public void popupPions(){
        VBox fondPopup = new VBox();
        fieldNbPions.setVisible(true);
        fieldNbPions.setDisable(false);
        fondPopup.getChildren().addAll(fieldNbPions);
        popupDestinations.getContent().addAll(fondPopup);
        popupDestinations.setHeight(210);
        popupDestinations.setWidth(500);
        Point2D point = boutonsWagons.localToScene(0.0, 0.0);
        popupDestinations.setX(RailsIHM.getPrimaryStage().getX() + point.getX() + 10);
        popupDestinations.setY(RailsIHM.getPrimaryStage().getY() + point.getY() + 25);

        popupDestinations.show(RailsIHM.getPrimaryStage());
    }

    EventHandler<MouseEvent> pionsWagonsClickés = mouseEvent -> {
        getJeu().nouveauxPionsWagonsDemandes();
        if(getJeu().saisieNbPionsWagonAutoriseeProperty().getValue()){
        popupPions();}
    };
    EventHandler<MouseEvent> pionsBateauClickés = mouseEvent -> {
        getJeu().nouveauxPionsBateauxDemandes();
        if(getJeu().saisieNbPionsBateauAutoriseeProperty().getValue()){
            popupPions();}
    };



    EventHandler<MouseEvent> afficherInfoJoueurGauche = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            popupInfo = new Popup();
            VBox vboxInfo = new VBox();
            vboxInfo.setSpacing(30);
            vboxInfo.setAlignment(Pos.CENTER);
            vboxInfo.setMinWidth(500);
            vboxInfo.setMinHeight(300);
            vboxInfo.setStyle(vueJoueurGauche.getBgColor());
            vboxInfo.getChildren().addAll(vueJoueurGauche.getImageJoueur(),vueJoueurGauche.getPseudoJoueur(), vueJoueurGauche.getDestinations(), vueJoueurGauche.getScoreJoueur());
            popupInfo.getContent().addAll(vboxInfo);

            Point2D point = plateau.localToScene(0.0, 0.0);
            popupInfo.setX(RailsIHM.getPrimaryStage().getX() + point.getX() + plateau.getWidth()/2.0 - 250);
            popupInfo.setY(RailsIHM.getPrimaryStage().getY() + point.getY() + plateau.getHeight()/2.0 - 150);

            popupInfo.show(RailsIHM.getPrimaryStage());
        }
    };

    EventHandler<MouseEvent> enleverPopupGauche = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            popupInfo.hide();
        }
    };

    EventHandler<MouseEvent> afficherInfoJoueurDroite = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            popupInfo = new Popup();
            VBox vboxInfo = new VBox();
            vboxInfo.setSpacing(30);
            vboxInfo.setAlignment(Pos.CENTER);
            vboxInfo.setMinWidth(500);
            vboxInfo.setMinHeight(300);
            vboxInfo.setStyle(vueJoueurDroite.getBgColor());
            vboxInfo.getChildren().addAll(vueJoueurDroite.getImageJoueur(),vueJoueurDroite.getPseudoJoueur(), vueJoueurDroite.getDestinations(), vueJoueurDroite.getScoreJoueur());
            popupInfo.getContent().addAll(vboxInfo);

            Point2D point = plateau.localToScene(0.0, 0.0);
            popupInfo.setX(RailsIHM.getPrimaryStage().getX() + point.getX() + plateau.getWidth()/2.0 - 250);
            popupInfo.setY(RailsIHM.getPrimaryStage().getY() + point.getY() + plateau.getHeight()/2.0 - popupInfo.getHeight()/2);

            popupInfo.show(RailsIHM.getPrimaryStage());
        }
    };

    EventHandler<MouseEvent> enleverPopupDroite = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            popupInfo.hide();
        }
    };

    EventHandler<MouseEvent> afficherInfoJoueurHaut = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            popupInfo = new Popup();
            VBox vboxInfo = new VBox();
            vboxInfo.setSpacing(30);
            vboxInfo.setAlignment(Pos.CENTER);
            vboxInfo.setMinWidth(500);
            vboxInfo.setMinHeight(300);
            vboxInfo.setStyle(vueJoueurHaut.getBgColor());
            vboxInfo.getChildren().addAll(vueJoueurHaut.getImageJoueur(),vueJoueurHaut.getPseudoJoueur(), vueJoueurHaut.getDestinations(), vueJoueurHaut.getScoreJoueur());
            popupInfo.getContent().addAll(vboxInfo);

            Point2D point = plateau.localToScene(0.0, 0.0);
            popupInfo.setX(RailsIHM.getPrimaryStage().getX() + point.getX() + plateau.getWidth()/2.0 - 250);
            popupInfo.setY(RailsIHM.getPrimaryStage().getY() + point.getY() + plateau.getHeight()/2.0 - popupInfo.getHeight()/2);

            popupInfo.show(RailsIHM.getPrimaryStage());
        }
    };

    EventHandler<MouseEvent> enleverPopupHaut = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            popupInfo.hide();
        }
    };






    public void creerBindings() {
        plateau.creerBindings();
        plateau.prefWidthProperty().bind(getScene().widthProperty());
        plateau.prefHeightProperty().bind(getScene().heightProperty());
        jeu.destinationsInitialesProperty().addListener(lesDestinationsInitialesChangent);
        passer.addEventHandler(MouseEvent.MOUSE_CLICKED, actionPasserParDefaut);
        instruction.textProperty().bind(jeu.instructionProperty());
        vueJoueurCourant.creerBindings();
        jeu.jeuEnPreparationProperty().addListener(estEnPreparation);
        instruction.textProperty().addListener(instructionChange);
        fieldNbPions.addEventHandler(KeyEvent.KEY_PRESSED, nbPionsAEteChoisi);
//        fieldNbPions.textProperty().addListener(changementTextField);

        jeu.joueurCourantProperty().addListener(changeCourant);

        piocheDestination.addEventHandler(MouseEvent.MOUSE_CLICKED, piocheDestiClickée);
        piocheWagon.addEventHandler(MouseEvent.MOUSE_CLICKED, piocheWagonsClickée);
        piocheBateau.addEventHandler(MouseEvent.MOUSE_CLICKED, piocheBateauClickée);

        piocheDestination.disableProperty().bind(jeu.piocheDestinationVideProperty());
        piocheBateau.disableProperty().bind(jeu.piocheBateauVideProperty());
        piocheWagon.disableProperty().bind(jeu.piocheWagonVideProperty());

        boutonsWagons.addEventHandler(MouseEvent.MOUSE_CLICKED, pionsWagonsClickés);
        boutonsBateaux.addEventHandler(MouseEvent.MOUSE_CLICKED, pionsBateauClickés);

        jeu.cartesTransportVisiblesProperty().addListener(lesCartesVisiblesChangent);

        for (IJoueur j : jeu.getJoueurs()) {
            j.cartesTransportProperty().addListener(vueJoueurCourant.listenerCarteTransport);
            j.cartesTransportPoseesProperty().addListener(vueJoueurCourant.listeCartePosées);
        }

        //Bindings pour quand on veut les infos des joueurs
        vueJoueurGauche.addEventHandler(MouseEvent.MOUSE_ENTERED, afficherInfoJoueurGauche);
        vueJoueurGauche.addEventHandler(MouseEvent.MOUSE_EXITED, enleverPopupGauche);

        vueJoueurDroite.addEventHandler(MouseEvent.MOUSE_ENTERED, afficherInfoJoueurDroite);
        vueJoueurDroite.addEventHandler(MouseEvent.MOUSE_EXITED, enleverPopupDroite);

        vueJoueurHaut.addEventHandler(MouseEvent.MOUSE_ENTERED, afficherInfoJoueurHaut);
        vueJoueurHaut.addEventHandler(MouseEvent.MOUSE_EXITED, enleverPopupHaut);
    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> getJeu().passerAEteChoisi());



}
