package fr.umontpellier.iut.rails.mecanique.etatsJoueur.initialisation;

import fr.umontpellier.iut.rails.mecanique.etatsJeu.PartieEnCours;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions.SaisieNbPionsWagon;

public class SaisieNbInitialPionsWagon extends SaisieNbPionsWagon {

    public SaisieNbInitialPionsWagon(Joueur joueurCourant) {
        super(joueurCourant, 25);
        valeurMin = 10;
        getJeu().instructionProperty().setValue("Saisissez un nombre de pions wagon entre 10 et 25");
    }

    @Override
    public void finDuTour() {
        if (getJeu().getEtatCourant().finPhase()) {
            getJeu().setEtatCourant(new PartieEnCours(getJeu()));
            getJeu().getEtatCourant().demarrerPartie();
        } else {
            getJeu().initialiserJoueurSuivant();
        }
    }

    @Override
    public void ajouterPions(int nbPionsDemandes) {
        joueurCourant.ajouterPionsWagonInitial(nbPionsDemandes);
    }

    @Override
    public void passer() {
        getJeu().saisieNbPionsWagonAutoriseeProperty().setValue(false);
        finDuTour();
    }
}
