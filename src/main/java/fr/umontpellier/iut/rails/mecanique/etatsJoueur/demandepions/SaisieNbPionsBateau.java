package fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class SaisieNbPionsBateau extends EtatJoueur {
    private final int valeurMax;

    public SaisieNbPionsBateau(Joueur joueurCourant, int valeurMax) {
        super(joueurCourant);
        this.valeurMax = valeurMax;
    }

    public void mettreAJourPions(String nbPionsDemandes)  {
        int nbPions;
        try {
            nbPions = Integer.parseInt(nbPionsDemandes);
            if (1 <= nbPions  && nbPions <= valeurMax)  {
                joueurCourant.ajouterPionsBateau(nbPions);
                getJeu().saisieNbPionsBateauAutoriseeProperty().setValue(false);
                finDuTour();
            }
        } catch (NumberFormatException e) {}
    }
}
