package fr.umontpellier.iut.rails.mecanique.etatsJeu;

import fr.umontpellier.iut.rails.mecanique.Jeu;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class EtatJeu {
    protected final Jeu jeu;
    protected final Joueur joueurCourant;

    public EtatJeu(Jeu jeu) {
        this.jeu = jeu;
        joueurCourant = jeu.getJoueurCourant();
    }

    public void demarrerPartie() {}
    public void prochainTour() {}
    public String getInfosPhase() {
        return "Début du tour de ";
    }
    public boolean finPhase() {
        return false;
    }

}
