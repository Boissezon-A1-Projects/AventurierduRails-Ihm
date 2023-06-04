package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Transport.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteTransport extends Label {

    private final ICarteTransport carteTransport;

    public VueCarteTransport(ICarteTransport carteTransport, int nbCartes, double hauteur, double largeur) {
        this.carteTransport = carteTransport;
        StringBuffer  nomCarte = new StringBuffer();
        nomCarte.append("carte-");
        if(this.carteTransport.estBateau()){
            if(this.carteTransport.estDouble()){
                nomCarte.append("DOUBLE-");
            }else{nomCarte.append("BATEAU-");}

        } else if (carteTransport.estJoker()) {
            nomCarte.append("JOKER-");
        }else{
            nomCarte.append("WAGON-");
        }
        nomCarte.append(carteTransport.getStringCouleur());
        if(this.carteTransport.getAncre()){
            nomCarte.append("-A");
        }
        ImageView imageCarte = new ImageView("images/cartesWagons/"+nomCarte+".png");
        imageCarte.setFitHeight(hauteur); imageCarte.setFitWidth(largeur);
        this.setGraphic(imageCarte);
    }

    public ICarteTransport getCarteTransport() {
        return carteTransport;
    }

}
