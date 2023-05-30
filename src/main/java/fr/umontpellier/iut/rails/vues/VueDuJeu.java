package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private VBox destinations;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        VBox centre = new VBox();
        plateau = new VuePlateau();
        passer = new Button("Passer");
        instruction = new Label();
        destinations = new VBox();

        // Instancie les vues de joueurs
        vueJoueurCourant = new VueJoueurCourant();
        vueJoueurDroite = new VueAutresJoueursDroite();
        vueJoueurGauche = new VueAutresJoueursGauche();
        vueJoueurHaut = new VueAutresJoueursHaut();

        centre.getChildren().addAll(instruction, destinations ,passer);
        setCenter(centre);
        VBox bas = new VBox();
        bas.getChildren().add(vueJoueurCourant);

        // Place les vues des joueurs
        setBottom(bas);
        setTop(vueJoueurHaut);
        setRight(vueJoueurDroite);
        setLeft(vueJoueurGauche);

        plateau.setVisible(true);
        plateau.setDisable(false);
    }

    EventHandler<MouseEvent> actionBoutonDestination = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            VueDestination v = (VueDestination) mouseEvent.getSource();
            destinations.getChildren().remove(v);
            jeu.uneDestinationAEteChoisie(v.getDestination());
        }
    };

    ChangeListener<Boolean> estEnPreparation = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            HBox nvCentre = new HBox();
            nvCentre.getChildren().addAll(instruction,plateau);
            setCenter(nvCentre);
            plateau.creerBindings();
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

    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> getJeu().passerAEteChoisi());

}
