package fr.umontpellier.iut.rails.mecanique.etatsJoueur;

import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau.ConstructionPort;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau.PriseDeRoute;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions.PionsBateauDemandes;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions.PionsWagonDemandes;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.pioches.CartePiocheDemandee;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.pioches.DestinationsDemandees;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.prisecartevisible.PremiereCarteTransportChoisie;

public class DebutTour extends EtatJoueur {

    public DebutTour(Joueur joueurCourant) {
        super(joueurCourant);
    }

    public void passer() {
        finDuTour();
    }

    public void prendreDestinations() {
        prochainEtat = new DestinationsDemandees(joueurCourant, 4 , 1);
        prochainEtat.prendreDestinations();
    }
    public void prendrePionsWagon() {
        prochainEtat = new PionsWagonDemandes(joueurCourant);
        prochainEtat.prendrePionsWagon();
    }
    public void prendrePionsBateau() {
        prochainEtat = new PionsBateauDemandes(joueurCourant);
        prochainEtat.prendrePionsBateau();
    }
    public void piocherWagon() {
        prochainEtat = new CartePiocheDemandee(joueurCourant);
        prochainEtat.piocherWagon();
    }
    public void piocherBateau() {
        prochainEtat = new CartePiocheDemandee(joueurCourant);
        prochainEtat.piocherBateau();
    }
    public void prendreRoute(String route) {
        prochainEtat = new PriseDeRoute(joueurCourant);
        prochainEtat.prendreRoute(route);
    }
    public void construirePort(String port) {
        prochainEtat = new ConstructionPort(joueurCourant);
        prochainEtat.construirePort(port);
    }
    public void prendreCarte(CarteTransport carte) {
        prochainEtat = new PremiereCarteTransportChoisie(joueurCourant);
        prochainEtat.prendreCarte(carte);
    }

}
