package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IRoute;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;
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

    private Label scoreJoueur;

    private HBox cartesTransportDefausse;

    public VueJoueurCourant(){
        this.setSpacing(15);

        VBox infoJoueur = new VBox();
        nomJoueur =new Label();
        nomJoueur.setFont(Font.font("Book Antiqua", FontWeight.BOLD,14));
        imageJoueur = new Label();
        imageJoueur.setFont(Font.font("Book Antiqua", FontWeight.BOLD,14));
        scoreJoueur = new Label();
        scoreJoueur.setFont(Font.font("Book Antiqua",14));
        infoJoueur.getChildren().addAll(nomJoueur,imageJoueur, scoreJoueur);
        infoJoueur.setAlignment(Pos.CENTER);

        cartesTransportBox = new HBox();
        cartesTransportBox.setSpacing(5);
        cartesTransportDefausse = new HBox();
        cartesTransportDefausse.setSpacing(5);
        destinationsBox = new VBox();
        destinationsBox.setAlignment(Pos.CENTER);
        Label d = new Label("Destinations :");
        destinationsBox.getChildren().add(d);
        pionsEtPorts = new VBox();

        HBox bateau = new HBox();
        Label bateauLogo = new Label();
        ImageView v = new ImageView("images/bouton-pions-bateau.png");
        v.setFitWidth(30); v.setFitHeight(30);
        bateauLogo.setGraphic(v);
        nbBateaux = new Label();
        nbBateaux.setFont(Font.font("Book Antiqua",FontWeight.BOLD,14));
        bateau.getChildren().addAll(bateauLogo,nbBateaux);
        bateau.setSpacing(10);
        bateau.setAlignment(Pos.CENTER_LEFT);
        HBox wagons = new HBox();
        Label wagonLogo = new Label();
        ImageView v2 = new ImageView("images/bouton-pions-wagon.png");
        v2.setFitWidth(30); v2.setFitHeight(30);
        wagonLogo.setGraphic(v2);
        nbWagons = new Label();
        nbWagons.setFont(Font.font("Book Antiqua",FontWeight.BOLD,14));
        wagons.getChildren().addAll(wagonLogo,nbWagons);
        wagons.setSpacing(10);
        wagons.setAlignment(Pos.CENTER_LEFT);
        HBox ports = new HBox();
        Label portLogo = new Label();
        ImageView v3 = new ImageView("images/port.png");
        v3.setFitWidth(30); v3.setFitHeight(30);
        portLogo.setGraphic(v3);
        nbPorts = new Label();
        nbPorts.setFont(Font.font("Book Antiqua",FontWeight.BOLD,14));
        ports.getChildren().addAll(portLogo,nbPorts);
        ports.setSpacing(10);
        ports.setAlignment(Pos.CENTER_LEFT);

        pionsEtPorts.getChildren().addAll(bateau,wagons,ports);
        pionsEtPorts.setSpacing(5);

        getChildren().addAll(cartesTransportDefausse,infoJoueur, destinationsBox,cartesTransportBox, pionsEtPorts);

        pionsEtPorts.setAlignment(Pos.CENTER);

        setPadding(new Insets(10));


    }


    //listener poru les cartes
    ListChangeListener<ICarteTransport> listenerCarteTransport = new ListChangeListener<ICarteTransport>() {
        @Override
        public void onChanged(Change<? extends ICarteTransport> change) {
            while(change.next()){
                System.out.println("change");
                if(change.wasAdded()){
                    System.out.println(change.getAddedSubList().size());
                    if( ((VueDuJeu) getScene().getRoot()).getJeu().jeuEnPreparationProperty().get()) {
                        for (int i = 0; i < change.getAddedSubList().size(); i += 4) {
                            List<ICarteTransport> list = new ArrayList<ICarteTransport>();
                            VBox v = new VBox();
                            v.setAlignment(Pos.CENTER);
                            v.setSpacing(-25);

                            int compteur = i;
                            if ((change.getAddedSubList().size() - i) >= 4) {
                                while (compteur < i + 4) {
                                    System.out.println("ajou");
                                    list.add(change.getAddedSubList().get(compteur));
                                    compteur++;
                                }
                            } else {

                                while (compteur < change.getAddedSubList().size()) {
                                    System.out.println("ajou");
                                    list.add(change.getAddedSubList().get(compteur));
                                    compteur++;
                                }
                            }

                            for (ICarteTransport carteTransport : list) {
                                VueCarteTransport carteenmain = new VueCarteTransport(carteTransport, 1, 60, 96);
                                carteenmain.addEventHandler(MouseEvent.MOUSE_CLICKED, eventCartesQueQuandCarteCHoisie);
                                v.getChildren().add(carteenmain);

                            }

                            cartesTransportBox.getChildren().add(v);


                        }
                    }else {
                        for (ICarteTransport c : change.getAddedSubList()) {
                            System.out.println("in");
                            VueCarteTransport v = new VueCarteTransport(c, 1, 60, 96);
                            ajouteCarte(v);
                        }
                        System.out.println("out");
                    }
                }
                if(change.wasRemoved()){
                    for (ICarteTransport c: change.getRemoved()) {
                        VueCarteTransport v = new VueCarteTransport(c,1,60,96);
                        supprimeCarte(v);
                    }
                }
            }
        }
    };

    public void ajouteCarte(VueCarteTransport v){
        VBox vb = (VBox) cartesTransportBox.getChildren().get(cartesTransportBox.getChildren().size()-1);
        if(vb.getChildren().size()<4){
            vb.getChildren().add(v);
        }else{
            VBox nvB = new VBox();
            nvB.setSpacing(-25);
            nvB.setAlignment(Pos.CENTER);

            nvB.getChildren().add(v);
            cartesTransportBox.getChildren().add(nvB);
        }
    }

    public void supprimeCarte(VueCarteTransport v){
        for (Node vb: cartesTransportBox.getChildren()) {
            VBox vbox = (VBox) vb;
            for (Node c: vbox.getChildren()) {
                VueCarteTransport carte = (VueCarteTransport) c;
                if(carte.getCarteTransport().equals(v.getCarteTransport())){
                    vbox.getChildren().remove(carte);
                    break;
                }

            }
        }

    }

    //listener pour la defausse
    ListChangeListener<ICarteTransport> listeCartePosées = new ListChangeListener<ICarteTransport>() {
        @Override
        public void onChanged(Change<? extends ICarteTransport> change) {
            while(change.next()){
                if(change.wasAdded()){
                    for (ICarteTransport c : change.getAddedSubList()) {
                        System.out.println("in");
                        VueCarteTransport v = new VueCarteTransport(c, 1, 60, 96);
                        ajouteCartePosee(v);
                    }
                    System.out.println("out");
                }
                if(change.wasRemoved()){
                    for (ICarteTransport c: change.getRemoved()) {
                        VueCarteTransport v = new VueCarteTransport(c,1,60,96);
                        supprimeCartePosee(v);
                    }
                }
            }
        }
    };

    EventHandler<MouseEvent> eventCartesQueQuandCarteCHoisie = mouseEvent -> {
        VueCarteTransport carte = (VueCarteTransport) mouseEvent.getSource();
        ((VueDuJeu) getScene().getRoot()).getJeu().uneCarteDuJoueurEstJouee(carte.getCarteTransport());
    };


    public void ajouteCartePosee(VueCarteTransport v){
        if(cartesTransportDefausse.getChildren().size()!=0) {
            VBox vb = (VBox) cartesTransportDefausse.getChildren().get(cartesTransportDefausse.getChildren().size() - 1);
            if(vb.getChildren().size()<4 ){
                vb.getChildren().add(v);
            }else{
                VBox nvB = new VBox();
                nvB.setSpacing(-25);
                nvB.setAlignment(Pos.CENTER);

                nvB.getChildren().add(v);
                cartesTransportDefausse.getChildren().add(nvB);
            }
        }else{
            VBox nvB = new VBox();
            nvB.setSpacing(-25);
            nvB.setAlignment(Pos.CENTER);

            nvB.getChildren().add(v);
            cartesTransportDefausse.getChildren().add(nvB);
        }
    }

    public void supprimeCartePosee(VueCarteTransport v){
        for (Node vb: cartesTransportDefausse.getChildren()) {
            VBox vbox = (VBox) vb;
            for (Node c: vbox.getChildren()) {
                VueCarteTransport carte = (VueCarteTransport) c;
                if(carte.getCarteTransport().equals(v.getCarteTransport())){
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
                setStyle("-fx-background-color:  #f9ca58");
                joueur.setImage(new Image("images/cartesWagons/avatar-JAUNE.png"));
            }
            else if (joueurCourant.getCouleur().equals(IJoueur.CouleurJoueur.BLEU)) {
                setStyle("-fx-background-color: #288bb6");
                joueur.setImage(new Image("images/cartesWagons/avatar-BLEU.png"));
            }
            else if (joueurCourant.getCouleur().equals(IJoueur.CouleurJoueur.ROUGE)) {
                setStyle("-fx-background-color: #af1000");
                joueur.setImage(new Image("images/cartesWagons/avatar-ROUGE.png"));
            }
            else if (joueurCourant.getCouleur().equals(IJoueur.CouleurJoueur.VERT)) {
                setStyle("-fx-background-color: #DAF7A6 ");
                joueur.setImage(new Image("images/cartesWagons/avatar-VERT.png"));
            }
            else{
                setStyle("-fx-background-color: #cd04ca");
                joueur.setImage(new Image("images/cartesWagons/avatar-ROSE.png"));
            }
            joueur.setFitHeight(84); joueur.setFitWidth(64.4);
            imageJoueur.setGraphic(joueur);
            nomJoueur.setText(joueurCourant.getNom());

            destinationsBox.getChildren().clear();
            for (IDestination d : joueurCourant.getDestinations()) {
                Label v = new Label(d.getVilles().toString());
                v.setWrapText(true);
                v.setFont(Font.font("Book Antiqua",14));
                v.setTextAlignment(TextAlignment.CENTER);
                destinationsBox.getChildren().add(v);
            }

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
            StringProperty stringScoreJoueur = new SimpleStringProperty();
            stringScoreJoueur.set("Score : " + String.valueOf(joueurCourant.getScore()));
            scoreJoueur.textProperty().bind(stringScoreJoueur);


        }
    };

    public void creerBindings(){
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurCourant);


    }

}
