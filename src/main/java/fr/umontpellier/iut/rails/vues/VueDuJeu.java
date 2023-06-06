package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private VBox bas;
    private VBox destinations;
    private TextField fieldNbPions;


    private Label piocheWagon;
    private Label piocheBateau;
    private Label piocheDestination;

    private VBox cartesVisibles;

    private Label boutonsWagons;
    private Label boutonsBateaux;

    private BooleanProperty choisiDesDestinations;


    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        VBox centre = new VBox();
        plateau = new VuePlateau();
        passer = new Button("Passer");
        instruction = new Label();
        destinations = new VBox();
        fieldNbPions = new TextField();
        fieldNbPions.setVisible(false); fieldNbPions.setDisable(true);
        centre.getChildren().addAll(instruction, destinations ,fieldNbPions,passer);
        setCenter(centre);
        destinations.setAlignment(Pos.CENTER);
        fieldNbPions.setMinSize(50,25);
        fieldNbPions.setMaxSize(50,25);
        centre.setAlignment(Pos.CENTER);

        // Instancie les vues de joueurs
        vueJoueurCourant = new VueJoueurCourant();
        vueJoueurDroite = new VueAutresJoueursDroite();
        vueJoueurGauche = new VueAutresJoueursGauche();
        vueJoueurHaut = new VueAutresJoueursHaut();
        bas =new VBox();
        bas.getChildren().addAll(vueJoueurCourant);
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

        cartesVisibles = new VBox();
        cartesVisibles.setSpacing(-50);


        // Place les vues des joueurs

        choisiDesDestinations= new SimpleBooleanProperty();
        plateau.setVisible(true);
        plateau.setDisable(false);
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

            vueJoueurDroite.setUp(listJoueurs.get((indiceJoueurCourant+1)%4));
            vueJoueurHaut.setUp(listJoueurs.get((indiceJoueurCourant+2)%4));
            vueJoueurGauche.setUp(listJoueurs.get((indiceJoueurCourant+3)%4));
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
                if(Integer.valueOf(fieldNbPions.getText()) <= 25 && Integer.valueOf(fieldNbPions.getText()) >= 10) {

                    jeu.leNombreDePionsSouhaiteAEteRenseigne(fieldNbPions.getText());
                    fieldNbPions.setVisible(false);
                    fieldNbPions.setDisable(true);
                    passer.setDisable(false);
                }
            }
        }
    };


    //changer le label instruction
    ChangeListener<String> instructionChange = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
            if(t1.equals("Saisissez un nombre de pions wagon entre 10 et 25")){
                fieldNbPions.setDisable(false);
                fieldNbPions.setVisible(true);
                passer.setDisable(true);
            }
        }
    };



    //change la scene quand plus en preparation
    ChangeListener<Boolean> estEnPreparation = (observableValue, aBoolean, t1) -> changerLePlateauDeJeu();

    public void changerLePlateauDeJeu(){
        VBox nvCentre = new VBox();
        HBox plateauEtPioches = new HBox();
        nvCentre.setPadding(new Insets(10));

        plateauEtPioches.setSpacing(15);
        VBox pioches = new VBox();
        HBox wagonsETBateauxPioches = new HBox();
        wagonsETBateauxPioches.getChildren().addAll(piocheWagon,piocheBateau); wagonsETBateauxPioches.setSpacing(15);
        pioches.setSpacing(10);
        pioches.getChildren().addAll(wagonsETBateauxPioches,piocheDestination, cartesVisibles);
        plateauEtPioches.getChildren().addAll(plateau, pioches);
        nvCentre.getChildren().addAll(instruction,plateauEtPioches);
        setCenter(nvCentre);
        plateau.creerBindings();
        HBox nvBas = new HBox();
        VBox boutonPasser= new VBox(); boutonPasser.getChildren().add(passer);

        nvBas.getChildren().addAll(vueJoueurCourant,boutonPasser);
        nvBas.setAlignment(Pos.CENTER);


        setTop(vueJoueurHaut);
        setRight(vueJoueurDroite);
        setLeft(vueJoueurGauche);


        setBottom(nvBas);
    }

    public void changerLePlateauPourDesti(){


        VBox v= new VBox();
        v.getChildren().addAll(destinations,passer);
        setCenter(v);

        setTop(vueJoueurHaut);
        setRight(vueJoueurDroite);
        setLeft(vueJoueurGauche);
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
        cartesVisibles.getChildren().remove(v);
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

            }
        }
    };


    EventHandler<MouseEvent> piocheWagonsClickée = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            jeu.uneCarteWagonAEtePiochee();
        }
    };

    EventHandler<MouseEvent> piocheBateauClickée = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            jeu.uneCarteBateauAEtePiochee();
        }
    };

    EventHandler<MouseEvent> piocheDestiClickée = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            jeu.nouvelleDestinationDemandee();
            choisiDesDestinations.setValue(true);
            /** IL FAUT DISPLAY LE PLATEAU POUR CHOISIR LES DESTI*/
        }
    };




    public void creerBindings() {
        plateau.prefWidthProperty().bind(getScene().widthProperty());
        plateau.prefHeightProperty().bind(getScene().heightProperty());
        jeu.destinationsInitialesProperty().addListener(lesDestinationsInitialesChangent);
        passer.addEventHandler(MouseEvent.MOUSE_CLICKED, actionPasserParDefaut);
        instruction.textProperty().bind(jeu.instructionProperty());
        vueJoueurCourant.creerBindings();
        jeu.jeuEnPreparationProperty().addListener(estEnPreparation);
        instruction.textProperty().addListener(instructionChange);
        fieldNbPions.addEventHandler(KeyEvent.KEY_PRESSED, nbPionsAEteChoisi);


        jeu.joueurCourantProperty().addListener(changeCourant);

        piocheDestination.addEventHandler(MouseEvent.MOUSE_CLICKED, piocheDestiClickée);
        piocheWagon.addEventHandler(MouseEvent.MOUSE_CLICKED, piocheWagonsClickée);
        piocheBateau.addEventHandler(MouseEvent.MOUSE_CLICKED, piocheBateauClickée);

        piocheDestination.disableProperty().bind(jeu.piocheDestinationVideProperty());
        piocheBateau.disableProperty().bind(jeu.piocheBateauVideProperty());
        piocheWagon.disableProperty().bind(jeu.piocheWagonVideProperty());

        jeu.cartesTransportVisiblesProperty().addListener(lesCartesVisiblesChangent);



    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> getJeu().passerAEteChoisi());

}
