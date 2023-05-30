package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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



        // Place les vues des joueurs


        plateau.setVisible(true);
        plateau.setDisable(false);
    }

    ChangeListener<IJoueur> changeCourant = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur joueurCourant) {
            List<IJoueur> list = (List<IJoueur>) ((VueDuJeu) getScene().getRoot()).getJeu().getJoueurs();
            int compteur =0;
            for (IJoueur joueur : list) {
                if(!joueur.equals(joueurCourant)){
                    if(compteur==0){
                        vueJoueurGauche.setUp(joueur);
                    }else if(compteur==1){
                        vueJoueurDroite.setUp(joueur);
                    }else{
                        vueJoueurHaut.setUp(joueur);
                    }
                    compteur++;
                }
            }
        }
    };
    EventHandler<MouseEvent> actionBoutonDestination = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            VueDestination v = (VueDestination) mouseEvent.getSource();
            destinations.getChildren().remove(v);
            jeu.uneDestinationAEteChoisie(v.getDestination());
        }
    };

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

    ChangeListener<Boolean> estEnPreparation = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            VBox nvCentre = new VBox();
            nvCentre.getChildren().addAll(instruction,plateau);
            setCenter(nvCentre);
            plateau.creerBindings();
            bas.getChildren().add(passer);
            setTop(vueJoueurHaut);
            setRight(vueJoueurDroite);
            setLeft(vueJoueurGauche);
        }
    };
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

    public VueDestination trouverLabelDansDestinations(List<Node> conteneur, IDestination desti){
        for (Node n: conteneur) {
            VueDestination l = (VueDestination) n;
            if(l.getText().equals(desti.getVilles().toString())){
                return l;
            }
        }
        return  null;
    }

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
    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> getJeu().passerAEteChoisi());

}
