package fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau;

import fr.umontpellier.iut.rails.mecanique.Route;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.Joueur;

import java.util.List;

public class DefaussePourRoutePaireOuGrise extends DefaussePourRoute {
    public DefaussePourRoutePaireOuGrise(Joueur joueurCourant, List<String> optionsChoix, Route route) {
        super(joueurCourant, optionsChoix, route);
    }

    @Override
    public void jouerCarte(CarteTransport carte) {
        optionsChoix.clear();
        optionsChoix.addAll(joueurCourant.getCartesAutoriseesPourRoute(route.getNom()));
        super.jouerCarte(carte);
    }

    public void prendreRoute(String nomRoute) {
        if (joueurCourant.getCartesTransportPosees() != null) {
            prochainEtat = new PriseDeRoute(joueurCourant);
            prochainEtat.prendreRoute(nomRoute);
        }
    }
}
