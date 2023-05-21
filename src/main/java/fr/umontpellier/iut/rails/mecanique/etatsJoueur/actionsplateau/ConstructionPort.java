package fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau;

import fr.umontpellier.iut.rails.mecanique.data.Ville;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

import java.util.List;

public class ConstructionPort extends EtatJoueur {

    public ConstructionPort(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Vous voulez construire un port");
    }

    @Override
    public void construirePort(String nomPort) {
        List<String> optionsPorts = joueurCourant.portsPouvantEtrePris();
        if (!optionsPorts.isEmpty() && optionsPorts.contains(nomPort)) {
            Ville p = getJeu().trouverPort(nomPort);
            prochainEtat = new DefaussePourPort(joueurCourant, joueurCourant.getCartesAutoriseesPourPort(nomPort), p);
        } else {
            getJeu().instructionProperty().setValue("Vous ne pouvez pas construire de port à " + nomPort + " - Choisissez une autre action");
            prochainEtat = new DebutTour(joueurCourant);
        }
        joueurCourant.setEtatCourant(prochainEtat);
    }
}
