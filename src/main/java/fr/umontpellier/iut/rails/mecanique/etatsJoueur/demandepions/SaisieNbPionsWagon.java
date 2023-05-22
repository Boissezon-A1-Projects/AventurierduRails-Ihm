package fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class SaisieNbPionsWagon extends EtatJoueur {
    protected int valeurMin;
    protected final int valeurMax;

    public SaisieNbPionsWagon(Joueur joueurCourant, int valeurMax) {
        super(joueurCourant);
        valeurMin = 1;
        this.valeurMax = valeurMax;
    }

    public void mettreAJourPions(String nbPionsDemandes)  {
        int nbPions;
        try {
            nbPions = Integer.parseInt(nbPionsDemandes);
            if (valeurMin <= nbPions  && nbPions <= valeurMax) {
                ajouterPions(nbPions);
                getJeu().saisieNbPionsWagonAutoriseeProperty().setValue(false);
                finDuTour();
            }
        } catch (NumberFormatException e) {}
    }

    public void ajouterPions(int nbPionsDemandes) {
        joueurCourant.ajouterPionsWagon(nbPionsDemandes);
    }
}
