package fr.umontpellier.iut.rails.mecanique.etatsJoueur;

import fr.umontpellier.iut.rails.mecanique.Jeu;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.Destination;
import fr.umontpellier.iut.rails.mecanique.etatsJeu.FinDePartie;

public abstract class EtatJoueur {
    protected final Joueur joueurCourant;
    protected EtatJoueur prochainEtat;

    public EtatJoueur(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
        prochainEtat = this;
    }

    public void passer() {}
    public void prendreDestinations() {}
    public void prendrePionsWagon() {}
    public void prendrePionsBateau() {}
    public void mettreAJourPions(String nbPionsChoisis) {}
    public void piocherWagon() {}
    public void piocherBateau() {}
    public void prendreCarte(CarteTransport carte) {}
    public void prendreRoute(String route) {}
    public void construirePort(String port) {}
    public void jouerCarte(CarteTransport carte) {}
    public void defausseDestination(Destination destination) {}

    public void finDuTour() {
        if (getJeu().getEtatCourant().finPhase()) {
            getJeu().calculerScoresFinaux();
            System.exit(0);
        } else {
            if (getJeu().getEtatCourant() instanceof FinDePartie) {
                getJeu().getEtatCourant().prochainTour();
            } else if (joueurCourant.nbInsuffisantPions()) {
                getJeu().setEtatCourant(new FinDePartie(getJeu()));
            }
            getJeu().joueurSuivant();
        }
    }

    protected Jeu getJeu() {
        return joueurCourant.getJeu();
    }
}