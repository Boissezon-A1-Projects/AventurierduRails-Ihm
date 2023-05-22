package fr.umontpellier.iut.rails.mecanique.etatsJoueur.prisecartevisible;

import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.TypeCarteTransport;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

public class PremiereCarteTransportChoisie extends EtatJoueur {
    public PremiereCarteTransportChoisie(Joueur joueurCourant) {
        super(joueurCourant);
    }

    public void prendreCarte(CarteTransport carte) {
        joueurCourant.getCartesTransport().add(joueurCourant.prendreCarteTransportVisible(carte.getNom()));
        if (getJeu().onPeutEncorePiocher()) {
            if (carte.getType() == TypeCarteTransport.JOKER) {
                prochainEtat = new ReveleNouvelleCarteTransportVisible2(joueurCourant);
            } else {
                prochainEtat = new ReveleNouvelleCarteTransportVisible1(joueurCourant);
            }
            joueurCourant.setEtatCourant(prochainEtat);
        } else {
            if (carte.getType() == TypeCarteTransport.JOKER || getJeu().onNePeutPasPrendreDeDeuxiemeCarteVisible()) {
                finDuTour();
            } else {
                getJeu().instructionProperty().setValue("Les pioches sont vides - Vous devez prendre une carte visible");
                prochainEtat = new DeuxiemeChoixCarte(joueurCourant);
                joueurCourant.setEtatCourant(prochainEtat);
            }
        }
    }

}