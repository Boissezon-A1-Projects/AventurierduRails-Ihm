package fr.umontpellier.iut.rails.mecanique;

import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.Couleur;
import fr.umontpellier.iut.rails.mecanique.data.TypeCarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.Ville;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RouteTerrestre extends Route {
    public RouteTerrestre(Ville ville1, Ville ville2, Couleur couleur, int longueur) {
        super(ville1, ville2, couleur, longueur);
    }

    @Override
    public int getNbPionsWagon() {
        return getLongueur();
    }

    @Override
    public boolean peutEtreCaptureePar(Joueur joueur) {
        if (!super.peutEtreCaptureePar(joueur)) {
            return false;
        }
        if (joueur.getNbPionsWagon() < getLongueur()) {
            return false;
        }

        CarteTransport.Catalogue cartesTransport = new CarteTransport.Catalogue(joueur.getCartesTransport());
        return (cartesTransport.getValeur(TypeCarteTransport.WAGON, getCouleur(), true) >= getLongueur());
    }

    public Collection<CarteTransport> getCarteAutoriseesTransports(Joueur joueur) {
        int longueur = getLongueur();
        CarteTransport.Catalogue cataloguePosees = new CarteTransport.Catalogue(joueur.getCartesTransportPosees());
        int valeurPosee = cataloguePosees.getValeur();

        CarteTransport.Catalogue catalogueMain = new CarteTransport.Catalogue(joueur.getCartesTransport());
        Couleur couleur = getCouleur();
        if (couleur == Couleur.GRIS) {
            // si la route est grise mais que le joueur a commencé à payer avec une couleur
            // on n'accepte que des cartes de cette couleur
            couleur = cataloguePosees.getCouleur();
        }

        List<CarteTransport> optionsCartes = new ArrayList<>(
                catalogueMain.get(TypeCarteTransport.JOKER, Couleur.GRIS));
        if (couleur == Couleur.GRIS) {
            for (Couleur c : Couleur.getCouleursSimples()) {
                if (catalogueMain.getValeur(TypeCarteTransport.WAGON, c, true) >= longueur - valeurPosee) {
                    optionsCartes.addAll(catalogueMain.get(TypeCarteTransport.WAGON, c));
                }
            }
        } else {
            optionsCartes.addAll(catalogueMain.get(TypeCarteTransport.WAGON, couleur));
        }
        return optionsCartes;
    }

}
