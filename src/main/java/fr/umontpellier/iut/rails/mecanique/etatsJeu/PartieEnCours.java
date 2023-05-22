package fr.umontpellier.iut.rails.mecanique.etatsJeu;

import fr.umontpellier.iut.rails.mecanique.Jeu;

public class PartieEnCours extends EtatJeu {
    public PartieEnCours(Jeu jeu) {
        super(jeu);
    }

    @Override
    public void demarrerPartie() {
        jeu.jeuEnPreparationProperty().setValue(false);
        jeu.initialiserCartesVisibles();
        jeu.passeAuJoueurSuivant();
        jeu.getJoueurCourant().jouerTour();
    }

}
