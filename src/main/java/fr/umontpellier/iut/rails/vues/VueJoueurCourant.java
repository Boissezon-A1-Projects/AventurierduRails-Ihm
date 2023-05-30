package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IRoute;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {

    private Label nomJoueur;
    private VBox cartesTransportBox;

    private HBox destinationsBox;


    public VueJoueurCourant(){

        nomJoueur =new Label();
        cartesTransportBox = new VBox();
        destinationsBox = new HBox();
        getChildren().addAll(nomJoueur, destinationsBox,cartesTransportBox);

    }

    /*ListChangeListener<ICarteTransport> listenerCarteJoueur = new ListChangeListener<ICarteTransport>() {
        @Override
        public void onChanged(Change<? extends ICarteTransport> change) {
            while(change.next()){
                if(change.wasRemoved()){
                    for (ICarteTransport carte: change.getRemoved()) {

                    }
                }
            }
        }
    };*/

   /* ListChangeListener<CarteTransport> listenerCarteTransportPosee = new ListChangeListener<CarteTransport>() {
        @Override
        public void onChanged(Change<? extends CarteTransport> change) {
            while(change.next()){
                if(change.wasAdded()){
                    for (CarteTransport carte : change.getAddedSubList() ) {

                    }
                }
            }
        }
    }*/


    EventHandler<MouseEvent> eventCartesQueQuandCarteCHoisie = mouseEvent -> {
        VueCarteTransport carte = (VueCarteTransport) mouseEvent.getSource();
        ((VueDuJeu) getScene().getRoot()).getJeu().uneCarteDuJoueurEstJouee(carte.getCarteTransport());
        cartesTransportBox.getChildren().remove(carte);


    };


    ChangeListener<IJoueur> listenerJoueurCourant = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur ancienJoueur, IJoueur joueurCourant) {

            destinationsBox.getChildren().clear();
            for (IDestination d : joueurCourant.getDestinations()) {
                Label v = new Label(d.getVilles().toString());
                destinationsBox.getChildren().add(v);
            }
            nomJoueur.setText("Joueur Courant: " +joueurCourant.getNom());
            cartesTransportBox.getChildren().clear();

            for (ICarteTransport carteTransport : joueurCourant.getCartesTransport()  ) {

                VueCarteTransport carteenmain = new VueCarteTransport(carteTransport,1);
                cartesTransportBox.getChildren().add(carteenmain);
                carteenmain.addEventHandler(MouseEvent.MOUSE_CLICKED, eventCartesQueQuandCarteCHoisie);
            }
            /*joueurCourant.cartesTransportProperty().addListener(listenerCarteJoueur);
            joueurCourant.cartesTransportPoseesProperty().addListener(listenerCarteTransportPosee);*/


        }


    };

    public void creerBindings(){
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurCourant);


    }

}
