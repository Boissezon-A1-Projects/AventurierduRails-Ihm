package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.RailsIHM;
import javafx.scene.layout.Pane;

/**
 * Cette classe affiche les scores en fin de partie.
 * On peut éventuellement proposer de rejouer, et donc de revenir à la fenêtre principale
 *
 */
public class VueResultats extends Pane {

    private RailsIHM ihm;

    public VueResultats(RailsIHM ihm) {
        this.ihm = ihm;
    }

}
