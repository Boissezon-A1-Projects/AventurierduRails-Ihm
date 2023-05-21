package fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class PionsWagonDemandes extends EtatJoueur {
    public PionsWagonDemandes(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Saisissez un nombre de pions wagon entre 1 et " + joueurCourant.nbMaxPionsWagonPossibles());
    }

    public void prendrePionsWagon() {
        int nbPionsPossibles = joueurCourant.nbMaxPionsWagonPossibles();
        if (nbPionsPossibles > 0) {
            getJeu().saisieNbPionsWagonAutoriseeProperty().setValue(true);
            joueurCourant.setEtatCourant(new SaisieNbPionsWagon(joueurCourant, nbPionsPossibles));
        } else {
            joueurCourant.setEtatCourant(new DebutTour(joueurCourant));
        }
    }

}
