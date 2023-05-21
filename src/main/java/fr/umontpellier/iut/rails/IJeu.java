package fr.umontpellier.iut.rails;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import java.util.List;

public interface IJeu {

    ObjectProperty<String> instructionProperty();
    ObservableList<? extends ICarteTransport> cartesTransportVisiblesProperty();
    ObservableList<? extends IDestination> destinationsInitialesProperty();
    ObjectProperty<IJoueur> joueurCourantProperty();
    BooleanProperty jeuEnPreparationProperty();
    BooleanProperty finDePartieProperty();
    BooleanProperty saisieNbPionsWagonAutoriseeProperty();
    BooleanProperty saisieNbPionsBateauAutoriseeProperty();
    BooleanProperty piocheWagonVideProperty();
    BooleanProperty piocheBateauVideProperty();
    BooleanProperty piocheDestinationVideProperty();

    List<? extends IJoueur> getJoueurs();
    List<? extends IVille> getPorts();
    List<? extends IRoute> getRoutes();

    void passerAEteChoisi();
    void uneCarteWagonAEtePiochee();
    void uneCarteBateauAEtePiochee();
    void nouvelleDestinationDemandee();
    void nouveauxPionsWagonsDemandes();
    void nouveauxPionsBateauxDemandes();
    void leNombreDePionsSouhaiteAEteRenseigne(String nbPionsChoisis);
    void uneRouteAEteChoisie(String nom);
    void unPortAEteChoisi(String nom);
    void uneDestinationAEteChoisie(IDestination destination); // parmi les destinations affichées
    void uneCarteTransportAEteChoisie(ICarteTransport carte); // parmi les 6 cartes transport visibles
    void uneCarteDuJoueurEstJouee(ICarteTransport carte); // parmi les cartes du joueur courant
}
