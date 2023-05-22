package fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class PionsBateauDemandes extends EtatJoueur {
    public PionsBateauDemandes(Joueur joueurCourant) {
        super(joueurCourant);
        int nbPionsBateauPossibles = joueurCourant.nbMaxPionsBateauPossibles();
        if (nbPionsBateauPossibles != 0)
            getJeu().instructionProperty().setValue("Saisissez un nombre de pions bateau entre 1 et " +nbPionsBateauPossibles);
        else {
            getJeu().instructionProperty().setValue("Vous ne pouvez plus prendre de pions bateau - Choisissez une autre action");
            joueurCourant.setEtatCourant(new DebutTour(joueurCourant));
        }
    }

    public void prendrePionsBateau() {
        int nbPionsPossibles = joueurCourant.nbMaxPionsBateauPossibles(); // min(nbPionsBateauEnReserve, nbPionsWagon.get());
        if (nbPionsPossibles > 0) {
            getJeu().saisieNbPionsBateauAutoriseeProperty().setValue(true);
            joueurCourant.setEtatCourant(new SaisieNbPionsBateau(joueurCourant, nbPionsPossibles));
        } else {
            joueurCourant.setEtatCourant(new DebutTour(joueurCourant));
        }
    }
}
