package fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class PionsBateauDemandes extends EtatJoueur {
    public PionsBateauDemandes(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Saisissez un nombre de pions bateau entre 1 et " + joueurCourant.nbMaxPionsBateauPossibles());
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
