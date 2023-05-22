package fr.umontpellier.iut.rails.mecanique.etatsJoueur.pioches;

import fr.umontpellier.iut.rails.mecanique.data.Destination;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class DefausseDestinations extends EtatJoueur {
    final int nbMinDestinations;
    protected int nbInitialDestinations;

    public DefausseDestinations(Joueur joueurCourant, int nbInitialDestinations, int nbMinDestinations) {
        super(joueurCourant);
        this.nbInitialDestinations = nbInitialDestinations;
        this.nbMinDestinations = nbMinDestinations;
        getJeu().instructionProperty().setValue(String.format("Vous pouvez défausser %d destinations" , getJeu().destinationsInitialesProperty().size() - nbMinDestinations));
    }

    public void passer() {
            joueurCourant.mettreAJourDestinations();
            finDuTour();
    }

    public void defausseDestination(Destination destination) {
        joueurCourant.defausserDestination(destination);
        if (getJeu().destinationsInitialesProperty().size() == nbMinDestinations) {
            joueurCourant.mettreAJourDestinations();
            finDuTour();
        } else {
            int nbRestants = getJeu().destinationsInitialesProperty().size() - nbMinDestinations;
            String pluriel = nbRestants > 1 ? "s" :"";
            getJeu().instructionProperty().setValue(String.format("Vous pouvez défausser %d destination%s" , nbRestants, pluriel));
        }
    }

}