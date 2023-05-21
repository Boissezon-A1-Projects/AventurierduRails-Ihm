package fr.umontpellier.iut.rails.mecanique.etatsJoueur.pioches;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class DestinationsDemandees extends EtatJoueur {
    final int nbDestinationsPiochees;
    final int nbMinDestinations;

    public DestinationsDemandees(Joueur joueurCourant, int nbDestinationsPiochees, int nbMinDestinations) {
        super(joueurCourant);
        this.nbDestinationsPiochees = nbDestinationsPiochees;
        this.nbMinDestinations = nbMinDestinations;
    }

    public void prendreDestinations() {
        if (!getJeu().pileDestinationsEstVide())
            joueurCourant.setEtatCourant(new DefausseDestinations(joueurCourant, joueurCourant.preparerDestinationsAChoisir(nbDestinationsPiochees).size(), nbMinDestinations));
    }
}
