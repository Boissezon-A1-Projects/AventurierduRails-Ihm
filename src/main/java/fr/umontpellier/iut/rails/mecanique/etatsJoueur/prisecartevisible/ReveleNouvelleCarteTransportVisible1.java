package fr.umontpellier.iut.rails.mecanique.etatsJoueur.prisecartevisible;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class ReveleNouvelleCarteTransportVisible1 extends EtatJoueur {
    public ReveleNouvelleCarteTransportVisible1(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Choisissez une pioche pour révéler une carte");
    }

    public void piocherWagon() {
        if (!getJeu().piocheWagonEstVide()) {
            joueurCourant.revelerCarteTransport("WAGON");
            prochainEtat = new DeuxiemeChoixCarte(joueurCourant);
            joueurCourant.setEtatCourant(prochainEtat);
        } else {
            if (!getJeu().piocheBateauEstVide())
                getJeu().instructionProperty().setValue("Cette pioche est vide - Choisissez l'autre pioche");
            else {
                getJeu().instructionProperty().setValue("Les deux pioches sont vides - Choisissez une carte visible");
                prochainEtat = new DeuxiemeChoixCarte(joueurCourant);
                joueurCourant.setEtatCourant(prochainEtat);
            }
        }
    }

    public void piocherBateau() {
        if (!getJeu().piocheBateauEstVide()) {
            joueurCourant.revelerCarteTransport("BATEAU");
            prochainEtat = new DeuxiemeChoixCarte(joueurCourant);
            joueurCourant.setEtatCourant(prochainEtat);
        } else {
            if (!getJeu().piocheWagonEstVide())
                getJeu().instructionProperty().setValue("Cette pioche est vide - Choisissez l'autre pioche");
            else {
                getJeu().instructionProperty().setValue("Les deux pioches sont vides - Choisissez une carte visible");
                prochainEtat = new DeuxiemeChoixCarte(joueurCourant);
                joueurCourant.setEtatCourant(prochainEtat);
            }
        }
    }
}
