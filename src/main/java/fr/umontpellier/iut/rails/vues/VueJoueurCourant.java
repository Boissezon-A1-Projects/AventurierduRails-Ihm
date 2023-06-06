package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IRoute;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends HBox {

    private Label nomJoueur;

    private Label imageJoueur;
    private HBox cartesTransportBox;

    private VBox destinationsBox;

    private VBox pionsEtPorts;

    private Label nbBateaux;
    private Label nbWagons;
    private Label nbPorts;

    public VueJoueurCourant(){
        this.setSpacing(15);
        VBox infoJoueur = new VBox();
        nomJoueur =new Label();
        imageJoueur = new Label();
        infoJoueur.getChildren().addAll(nomJoueur,imageJoueur);
        cartesTransportBox = new HBox();
        cartesTransportBox.setSpacing(5);
        destinationsBox = new VBox();

        pionsEtPorts = new VBox();

        HBox bateau = new HBox();
        Label bateauLogo = new Label();
        ImageView v = new ImageView("images/bouton-pions-bateau.png");
        v.setFitWidth(20); v.setFitHeight(20);
        bateauLogo.setGraphic(v);
        nbBateaux = new Label();
        bateau.getChildren().addAll(bateauLogo,nbBateaux);
        bateau.setSpacing(2);
        HBox wagons = new HBox();
        Label wagonLogo = new Label();
        ImageView v2 = new ImageView("images/bouton-pions-wagon.png");
        v2.setFitWidth(20); v2.setFitHeight(20);
        wagonLogo.setGraphic(v2);
        nbWagons = new Label();
        wagons.getChildren().addAll(wagonLogo,nbWagons);
        wagons.setSpacing(2);
        HBox ports = new HBox();
        Label portLogo = new Label();
        ImageView v3 = new ImageView("images/port.png");
        v3.setFitWidth(20); v3.setFitHeight(20);
        portLogo.setGraphic(v3);
        nbPorts = new Label();
        ports.getChildren().addAll(portLogo,nbPorts);
        ports.setSpacing(2);
        pionsEtPorts.getChildren().addAll(bateau,wagons,ports);


        getChildren().addAll(infoJoueur, destinationsBox,cartesTransportBox, pionsEtPorts);

        pionsEtPorts.setAlignment(Pos.CENTER);

        setPadding(new Insets(10));
    }



   /* ListChangeListener<CarteTransport> listenerCarteTransportPosee = new ListChangeListener<CarteTransport>() {
        @Override
        public void onChanged(Change<? extends CarteTransport> change) {
            while(change.next()){
                if(change.wasAdded()){
                    for (CarteTransport carte : change.getAddedSubList() ) {

                    }
                }
            }
        }
    }*/


    EventHandler<MouseEvent> eventCartesQueQuandCarteCHoisie = mouseEvent -> {
        VueCarteTransport carte = (VueCarteTransport) mouseEvent.getSource();
        supprimeCarte(carte);
        ((VueDuJeu) getScene().getRoot()).getJeu().uneCarteDuJoueurEstJouee(carte.getCarteTransport());


    };

    public void supprimeCarte(VueCarteTransport v){
        for (Node vb: cartesTransportBox.getChildren()) {
            VBox vbox = (VBox) vb;
            for (Node c: vbox.getChildren()) {
                VueCarteTransport carte = (VueCarteTransport) c;
                if(carte.equals(v)){
                    vbox.getChildren().remove(carte);
                    break;
                }

            }
        }

    }

    ChangeListener<IJoueur> listenerJoueurCourant = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur ancienJoueur, IJoueur joueurCourant) {

            nbWagons.setText(String.valueOf(joueurCourant.getNbPionsWagon()));
            nbBateaux.setText(String.valueOf(joueurCourant.getNbPionsBateau()));
            nbPorts.setText(String.valueOf(joueurCourant.getNbPorts()));
            ImageView joueur = new ImageView();
            if(joueurCourant.getCouleur().equals(IJoueur.CouleurJoueur.JAUNE)){
                setStyle("-fx-background-color:  #e9d460");
                joueur.setImage(new Image("images/cartesWagons/avatar-JAUNE.png"));
            } else if (joueurCourant.getCouleur().equals(IJoueur.CouleurJoueur.BLEU)) {
                setStyle("-fx-background-color: #60c4e9");
                joueur.setImage(new Image("images/cartesWagons/avatar-BLEU.png"));
            } else if (joueurCourant.getCouleur().equals(IJoueur.CouleurJoueur.ROUGE)) {
                setStyle("-fx-background-color: #e96060");
                joueur.setImage(new Image("images/cartesWagons/avatar-ROUGE.png"));
            }else if (joueurCourant.getCouleur().equals(IJoueur.CouleurJoueur.VERT)) {
                setStyle("-fx-background-color: #60e96c");
                joueur.setImage(new Image("images/cartesWagons/avatar-VERT.png"));
            }else{
                setStyle("-fx-background-color: #e960d8");
                joueur.setImage(new Image("images/cartesWagons/avatar-ROSE.png"));
            }
            joueur.setFitHeight(84); joueur.setFitWidth(64.4);
            imageJoueur.setGraphic(joueur);


            destinationsBox.getChildren().clear();
            for (IDestination d : joueurCourant.getDestinations()) {
                Label v = new Label(d.getVilles().toString());
                destinationsBox.getChildren().add(v);
            }
            nomJoueur.setText("Joueur Courant: " +joueurCourant.getNom());
            cartesTransportBox.getChildren().clear();
            for (int i = 0; i < joueurCourant.getCartesTransport().size(); i+=4) {
                List<ICarteTransport> list = new ArrayList<ICarteTransport>();
                VBox v = new VBox();
                v.setAlignment(Pos.CENTER);
                v.setSpacing(-25);

                int compteur =i;
                if((joueurCourant.getCartesTransport().size()-i)>=4){
                    while(compteur<i+4){

                        list.add(joueurCourant.getCartesTransport().get(compteur));
                        compteur++;
                    }
                }else{

                    while(compteur<joueurCourant.getCartesTransport().size()){
                        list.add(joueurCourant.getCartesTransport().get(compteur));
                        compteur++;
                    }
                }

                for (ICarteTransport carteTransport : list  ) {
                    VueCarteTransport carteenmain = new VueCarteTransport(carteTransport,1,60,96);
                    carteenmain.addEventHandler(MouseEvent.MOUSE_CLICKED,eventCartesQueQuandCarteCHoisie);
                    v.getChildren().add(carteenmain);

                }

                cartesTransportBox.getChildren().add(v);
            }

        }


    };

    public void creerBindings(){
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurCourant);
    }

}
