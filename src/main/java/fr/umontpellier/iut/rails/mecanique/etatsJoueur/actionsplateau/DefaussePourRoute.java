package fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau;

import fr.umontpellier.iut.rails.mecanique.Route;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.Joueur;

import java.util.List;

public class DefaussePourRoute extends DefausseAvecRestrictionCartes {

    private int valeurPosee;
    protected final Route route;
    private final int valeurAPayer;

    public DefaussePourRoute(Joueur joueurCourant, List<String> optionsChoix, Route route) {
        super(joueurCourant, optionsChoix);
        valeurPosee = 0;
        this.route = route;
        valeurAPayer = route.nbCartesAPayer();
        String pluriel = valeurAPayer > 1 ? "s" :"";
        getJeu().instructionProperty().setValue("Il faut défausser " + valeurAPayer + " carte"+ pluriel + " pour prendre la route " + route.getVille1().getNom() + "-" + route.getVille2().getNom());
    }

    @Override
    public void jouerCarte(CarteTransport carte) {
        if (optionsChoix.contains(carte.getNom())) {
            optionsChoix.remove(carte.getNom());
            valeurPosee += joueurCourant.poserCarteTransport(carte.getNom());
            if (valeurAPayer - valeurPosee <= 0) {
                joueurCourant.finaliserCaptureRoute(route);
                finDuTour();
            } else {
                int nbRestants = valeurAPayer - valeurPosee;
                String pluriel = nbRestants > 1 ? "s" :"";
                getJeu().instructionProperty().setValue(String.format("Il faut défausser %d carte%s" , nbRestants, pluriel));
            }
        }
    }

    public void prendreRoute(String nomRoute) {
        if (joueurCourant.getCartesTransportPosees() != null) {
            prochainEtat = new PriseDeRoute(joueurCourant);
            prochainEtat.prendreRoute(nomRoute);
        }
    }

}