package fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau;

import fr.umontpellier.iut.rails.mecanique.Route;
import fr.umontpellier.iut.rails.mecanique.RoutePaire;
import fr.umontpellier.iut.rails.mecanique.data.Couleur;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

import java.util.List;

public class PriseDeRoute extends EtatJoueur {

    public PriseDeRoute(Joueur joueurCourant) {
        super(joueurCourant);
        getJeu().instructionProperty().setValue("Vous ne pouvez pas prendre cette route - Choisissez une autre action");
    }

    public void prendreRoute(String nomRoute) {
        List<String> optionsRoutes = joueurCourant.getCartesAutoriseesPourRoute(nomRoute);
        Route r = getJeu().trouverRoute(nomRoute);
        if (r != null && !optionsRoutes.isEmpty() && r.peutEtreCaptureePar(joueurCourant)) {
            if (r instanceof RoutePaire || r.getCouleur() == Couleur.GRIS)
                prochainEtat = new DefaussePourRoutePaireOuGrise(joueurCourant, optionsRoutes, r);
            else
                prochainEtat = new DefaussePourRoute(joueurCourant, optionsRoutes, r);
        } else {
            prochainEtat = new DebutTour(joueurCourant);
        }
        joueurCourant.setEtatCourant(prochainEtat);
    }
}
