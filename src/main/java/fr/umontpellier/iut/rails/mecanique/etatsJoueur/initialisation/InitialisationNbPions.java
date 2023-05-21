package fr.umontpellier.iut.rails.mecanique.etatsJoueur.initialisation;

import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions.PionsWagonDemandes;

public class InitialisationNbPions extends PionsWagonDemandes {

    public InitialisationNbPions(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Saisissez le nombre de pions wagon souhaité");
    }

    public void prendrePionsWagon() {
        getJeu().saisieNbPionsWagonAutoriseeProperty().setValue(true);
        joueurCourant.setEtatCourant(new SaisieNbInitialPionsWagon(joueurCourant));
    }

}
