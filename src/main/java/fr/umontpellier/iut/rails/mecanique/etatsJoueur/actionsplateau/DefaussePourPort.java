package fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau;

import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.Ville;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;

import java.util.List;

public class DefaussePourPort extends DefausseAvecRestrictionCartes {

    private int nbCartesPosees;
    private final Ville port;

    public DefaussePourPort(Joueur joueurCourant, List<String> optionsChoix, Ville port) {
        super(joueurCourant, optionsChoix);
        nbCartesPosees = 0;
        this.port = port;
        getJeu().instructionProperty().setValue("Il faut défausser 4 cartes pour construire un port à " + port.getNom());
    }

    @Override
    public void jouerCarte(CarteTransport carte) {
        if (optionsChoix.contains(carte.getNom())) {
            optionsChoix.remove(carte.getNom());
            nbCartesPosees += joueurCourant.poserCarteTransport(carte.getNom());
            if (4 - nbCartesPosees <= 0) {
                joueurCourant.finaliserConstruirePort(port);
                finDuTour();
            } else {
                int nbRestants = 4 - nbCartesPosees;
                String pluriel = nbRestants > 1 ? "s" :"";
                getJeu().instructionProperty().setValue("Il faut encore défausser " + nbRestants + " carte" + pluriel + " pour construire un port à " + port.getNom());
                optionsChoix.clear();
                optionsChoix.addAll(joueurCourant.getCartesAutoriseesPourPort(port.getNom()));
            }
        }
    }

    @Override
    public void construirePort(String nomPort) {
        if (joueurCourant.getCartesTransportPosees() != null) {
            prochainEtat = new ConstructionPort(joueurCourant);
            prochainEtat.prendreRoute(nomPort);
        }
    }
}