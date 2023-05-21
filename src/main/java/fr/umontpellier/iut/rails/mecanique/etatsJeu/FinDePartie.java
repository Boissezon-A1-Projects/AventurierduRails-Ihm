package fr.umontpellier.iut.rails.mecanique.etatsJeu;

import fr.umontpellier.iut.rails.mecanique.Jeu;

public class FinDePartie extends EtatJeu {

    int nbToursAvantFin = jeu.getJoueurs().size() * 2 - 1;

    public FinDePartie(Jeu jeu) {
        super(jeu);
    }

    public void prochainTour() {
        nbToursAvantFin--;
    }

    public boolean finPhase() {
        return nbToursAvantFin == 0;
    }

    public String getInfosPhase() {
        if (nbToursAvantFin < jeu.getJoueurs().size())
            return "Dernier tour";
        return "Avant-dernier tour";
    }
}
