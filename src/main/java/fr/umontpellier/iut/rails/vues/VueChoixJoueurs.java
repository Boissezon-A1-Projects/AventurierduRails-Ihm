package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.RailsIHM;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private final ObservableList<String> nomsJoueurs;
    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        nomsJoueurs = FXCollections.observableArrayList();
        Image im = new Image("images/plancher-bois-brun.jpg");
        BackgroundSize bs = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true,true,true,true);
        BackgroundImage i = new BackgroundImage(im, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,bs );
        VBox root = new VBox();
        root.setBackground(new Background(i));
        Label bienvenue = new Label("BIENVENUE DANS :");
        bienvenue.setFont(Font.font("Book Antiqua",45)); bienvenue.setTextFill(Paint.valueOf("#FFFFFF"));
        Label aventurierRails = new Label("Les Aventuriers du rails !");
        aventurierRails.setFont(Font.font("Stencil",75)); aventurierRails.setTextFill(Paint.valueOf("#FFFFFF"));


        root.getChildren().addAll(bienvenue,aventurierRails);
        root.setAlignment(Pos.CENTER); root.setSpacing(5);
        Scene s = new Scene(root, Screen.getPrimary().getBounds().getWidth() * DonneesGraphiques.pourcentageEcran, Screen.getPrimary().getBounds().getHeight() * DonneesGraphiques.pourcentageEcran);

        setScene(s);

    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {}

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {}

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        throw new RuntimeException("Methode à implémenter");
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        throw new RuntimeException("Methode à implémenter");
    }

}
