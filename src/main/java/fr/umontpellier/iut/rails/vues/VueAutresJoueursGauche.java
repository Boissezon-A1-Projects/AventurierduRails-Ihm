package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueursGauche extends VBox {
    private Label nomJoueur;
    private HBox destinations;
    private VBox cartesTransport;

    public VueAutresJoueursGauche(){
        nomJoueur = new Label();
        cartesTransport = new VBox();
        destinations = new HBox();
        getChildren().addAll(nomJoueur, destinations, cartesTransport);
    }

    public void setUp(IJoueur joueur){
        destinations.getChildren().clear();
        for (IDestination d : joueur.getDestinations()) {
            Label v = new Label(d.getVilles().toString());
            destinations.getChildren().add(v);
        }

        nomJoueur.setText("Prochain joueur : " + joueur.getNom());
        cartesTransport.getChildren().clear();
        for (ICarteTransport carteTransport : joueur.getCartesTransport()) {
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
            cartesTransport.getChildren().add(carteenmain);
        }
    }
}



