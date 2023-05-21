package fr.umontpellier.iut.rails.mecanique.etatsJoueur.initialisation;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.pioches.DefausseDestinations;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class DefausseDestinationsInitiales extends DefausseDestinations {

    public DefausseDestinationsInitiales(Joueur joueurCourant, int nbInitialDestinations, int nbMinDestinations) {
        super(joueurCourant, nbInitialDestinations, nbMinDestinations);
        this.nbInitialDestinations++; // pour pouvoir passer
    }

    @Override
    public void finDuTour() {
        prochainEtat = new InitialisationNbPions(joueurCourant);
        joueurCourant.setEtatCourant(prochainEtat);
        prochainEtat.prendrePionsWagon();
    }
}
