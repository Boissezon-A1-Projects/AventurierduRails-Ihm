package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueursGauche extends VBox {
    private Label nomJoueur;
    private VBox destinations;
    private Label scoreJoueur;
    private ImageView imageJoueur;

    public VueAutresJoueursGauche(){
        nomJoueur = new Label();
        destinations = new VBox();
        scoreJoueur = new Label();
        imageJoueur = new ImageView();
        getChildren().addAll(imageJoueur, nomJoueur);
        setAlignment(Pos.CENTER);
        setMaxWidth(100);
        setMaxHeight(500);

    }

    public void setUp(IJoueur joueur){
        destinations.getChildren().clear();
        for (IDestination d : joueur.getDestinations()) {
            Label v = new Label(d.getVilles().toString());
            destinations.getChildren().add(v);
        }

        nomJoueur.setText("Joue dans 3 tours");
        if(joueur.getCouleur().equals(IJoueur.CouleurJoueur.JAUNE)){
            setStyle("-fx-background-color:  #e9d460");
            imageJoueur.setImage(new Image("images/cartesWagons/avatar-JAUNE.png"));
        } else if (joueur.getCouleur().equals(IJoueur.CouleurJoueur.BLEU)) {
            setStyle("-fx-background-color: #60c4e9");
            imageJoueur.setImage(new Image("images/cartesWagons/avatar-BLEU.png"));

        } else if (joueur.getCouleur().equals(IJoueur.CouleurJoueur.ROUGE)) {
            setStyle("-fx-background-color: #e96060");
            imageJoueur.setImage(new Image("images/cartesWagons/avatar-ROUGE.png"));

        }else if (joueur.getCouleur().equals(IJoueur.CouleurJoueur.VERT)) {
            setStyle("-fx-background-color: #60e96c");
            imageJoueur.setImage(new Image("images/cartesWagons/avatar-VERT.png"));

        }else{
            setStyle("-fx-background-color: #e960d8");
            imageJoueur.setImage(new Image("images/cartesWagons/avatar-ROSE.png"));

        }

        imageJoueur.setFitHeight(84); imageJoueur.setFitWidth(64.4);

        StringProperty stringScoreJoueur = new SimpleStringProperty();
        stringScoreJoueur.set("Score de " + joueur.getNom() + " : " + String.valueOf(joueur.getScore()));
        scoreJoueur.textProperty().bind(stringScoreJoueur);
    }
}



