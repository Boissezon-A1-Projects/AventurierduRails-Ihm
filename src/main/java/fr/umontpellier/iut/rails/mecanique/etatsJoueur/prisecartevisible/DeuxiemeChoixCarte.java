package fr.umontpellier.iut.rails.mecanique.etatsJoueur.prisecartevisible;

import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.TypeCarteTransport;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class DeuxiemeChoixCarte extends EtatJoueur {
    public DeuxiemeChoixCarte(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Vous pouvez prendre une deuxième carte");
    }

    public void prendreCarte(CarteTransport carte) {
        if (carte.getType() == TypeCarteTransport.JOKER) {
            getJeu().instructionProperty().setValue("Vous ne pouvez pas prendre de Joker - Choisissez une autre carte");
        } else {
            joueurCourant.getCartesTransport().add(joueurCourant.prendreCarteTransportVisible(carte.getNom()));
            if (!getJeu().piocheWagonEstVide() || !getJeu().piocheBateauEstVide()) {
                prochainEtat = new ReveleNouvelleCarteTransportVisible2(joueurCourant);
                joueurCourant.setEtatCourant(prochainEtat);
            } else
                finDuTour();
        }
    }

    public void piocherWagon() {
        if (!getJeu().piocheWagonEstVide()) {
            CarteTransport cartePiochee = getJeu().piocherCarteWagon();
            joueurCourant.getCartesTransport().add(cartePiochee);
            finDuTour();
        } else {
            getJeu().instructionProperty().setValue("Cette pioche est vide - Choisissez une autre action");
        }
    }

    public void piocherBateau() {
        if (!getJeu().piocheBateauEstVide()) {
            CarteTransport cartePiochee = getJeu().piocherCarteBateau();
            joueurCourant.getCartesTransport().add(cartePiochee);
            finDuTour();
        } else {
            getJeu().instructionProperty().setValue("Cette pioche est vide - Choisissez une autre action");
        }
    }

    public void passer() {
        if (getJeu().piocheWagonEstVide() && getJeu().piocheBateauEstVide()) {
            finDuTour();
        }
    }
}