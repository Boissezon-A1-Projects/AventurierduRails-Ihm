package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueursDroite extends VBox {
    private Label nomJoueur;
    private Label scoreJoueur;
    private VBox destinations;
    public VueAutresJoueursDroite(){
        nomJoueur = new Label();
        scoreJoueur = new Label();
        destinations = new VBox();
        getChildren().addAll(nomJoueur, destinations, scoreJoueur);
        setAlignment(Pos.CENTER);
        setMaxWidth(100);
    }

    public void setUp(IJoueur joueur){
        destinations.getChildren().clear();
        for (IDestination d : joueur.getDestinations()) {
            Label v = new Label(d.getVilles().toString());
            destinations.getChildren().add(v);
        }

        nomJoueur.setText("Prochain joueur: " + joueur.getNom());
        if(joueur.getCouleur().equals(IJoueur.CouleurJoueur.JAUNE)){
            setStyle("-fx-background-color:  #e9d460");
        } else if (joueur.getCouleur().equals(IJoueur.CouleurJoueur.BLEU)) {
            setStyle("-fx-background-color: #60c4e9");
        } else if (joueur.getCouleur().equals(IJoueur.CouleurJoueur.ROUGE)) {
            setStyle("-fx-background-color: #e96060");
        }else if (joueur.getCouleur().equals(IJoueur.CouleurJoueur.VERT)) {
            setStyle("-fx-background-color: #60e96c");
        }else{
            setStyle("-fx-background-color: #e960d8");
        }
        StringProperty stringScoreJoueur = new SimpleStringProperty();
        stringScoreJoueur.set("Score de " + joueur.getNom() + " : " + String.valueOf(joueur.getScore()));
        scoreJoueur.textProperty().bind(stringScoreJoueur);
    }
}