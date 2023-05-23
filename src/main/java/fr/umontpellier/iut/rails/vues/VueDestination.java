package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IDestination;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Button {

    private final IDestination destination;

    public VueDestination(IDestination destination) {
        this.destination = destination;
        this.setText(destination.getVilles().toString());
    }

    public IDestination getDestination() {
        return destination;
    }

}
