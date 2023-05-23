package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IRoute;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
                StringBuffer  nomCarte = new StringBuffer();
                nomCarte.append("carte-");
                if(carteTransport.estBateau()){
                    nomCarte.append("BATEAU-");
                } else if (carteTransport.estDouble()) {
                    nomCarte.append("DOUBLE-");
                }else{
                    nomCarte.append("WAGON-");
                }
                nomCarte.append(carteTransport.getStringCouleur());
                if(carteTransport.getAncre()){
                    nomCarte.append("-A");
                }
                Label carteenmain = new Label(nomCarte.toString());
                cartesTransportBox.getChildren().add(carteenmain);
            }
            /*for (IRoute route: joueurCourant. ) {

            }*/


        }
    };

    public void creerBindings(){
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurCourant);
    }

}
