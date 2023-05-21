package fr.umontpellier.iut.rails.mecanique.etatsJoueur.initialisation;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class InitialisationDestinations extends EtatJoueur {
    final int nbDestinationsPiochees;
    final int nbMinDestinations;

    public InitialisationDestinations(Joueur joueurCourant, int nbDestinationsPiochees, int nbMinDestinations) {
        super(joueurCourant);
        this.nbDestinationsPiochees = nbDestinationsPiochees;
        this.nbMinDestinations = nbMinDestinations;
        getJeu().instructionProperty().setValue("Vous pouvez défausser 2 destinations");
    }

    public void prendreDestinations() {
        joueurCourant.setEtatCourant(new DefausseDestinationsInitiales(joueurCourant, joueurCourant.preparerDestinationsAChoisir(nbDestinationsPiochees).size(), nbMinDestinations));
    }

}
