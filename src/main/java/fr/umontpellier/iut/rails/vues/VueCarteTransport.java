package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Transport.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteTransport extends Pane {

    private final ICarteTransport carteTransport;

    public VueCarteTransport(ICarteTransport carteTransport, int nbCartes) {
        this.carteTransport = carteTransport;
    }

    public ICarteTransport getCarteTransport() {
        return carteTransport;
    }

}
