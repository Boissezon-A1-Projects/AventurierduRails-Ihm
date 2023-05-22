package fr.umontpellier.iut.rails.mecanique.etatsJoueur.pioches;

import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.prisecartevisible.DeuxiemeChoixCarte;

public class CartePiocheDemandee extends EtatJoueur {

    public CartePiocheDemandee(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Vous pouvez prendre une deuxième carte");
    }

    public void piocherWagon() {
        if (!getJeu().piocheWagonEstVide()) {
            CarteTransport cartePiochee = getJeu().piocherCarteWagon();
            joueurCourant.getCartesTransport().add(cartePiochee);
            if (getJeu().onNePeutPasPrendreDeDeuxiemeCarteVisible())
                finDuTour();
            else
                prochainEtat = new DeuxiemeChoixCarte(joueurCourant);
        } else {
            getJeu().instructionProperty().setValue("Cette pioche est vide - Choisissez une autre action");
            prochainEtat = new DebutTour(joueurCourant);
        }
        joueurCourant.setEtatCourant(prochainEtat);
    }

    public void piocherBateau() {
        if (!getJeu().piocheBateauEstVide()) {
            CarteTransport cartePiochee = getJeu().piocherCarteBateau();
            joueurCourant.getCartesTransport() .add(cartePiochee);
            if (getJeu().onNePeutPasPrendreDeDeuxiemeCarteVisible())
                finDuTour();
            else
                prochainEtat = new DeuxiemeChoixCarte(joueurCourant);
        } else {
            getJeu().instructionProperty().setValue("Cette pioche est vide - Choisissez une autre action");
            prochainEtat = new DebutTour(joueurCourant);
        }
        joueurCourant.setEtatCourant(prochainEtat);
    }

}
