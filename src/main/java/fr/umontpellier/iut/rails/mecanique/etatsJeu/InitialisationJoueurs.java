package fr.umontpellier.iut.rails.mecanique.etatsJeu;

import fr.umontpellier.iut.rails.mecanique.Jeu;

public class InitialisationJoueurs extends EtatJeu {

    private int numeroProchainJoueurAInitialiser = 0;

    public InitialisationJoueurs(Jeu jeu) {
        super(jeu);
        joueurCourant.preparation();
    }

    public void prochainTour() {
        numeroProchainJoueurAInitialiser++;
    }

    @Override
    public boolean finPhase() {
        return numeroProchainJoueurAInitialiser == jeu.getJoueurs().size() - 1;
    }

}
