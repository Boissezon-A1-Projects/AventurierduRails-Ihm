package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.*;
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
import javafx.geometry.Point2D;
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
import javafx.stage.Popup;

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

    private HBox destinationsQuandTrop;

    private Popup p;

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
        destinationsBox.setMaxHeight(250);
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

        destinationsQuandTrop = new HBox();
        p = new Popup();
        p.getContent().addAll(destinationsQuandTrop);
        Point2D point = new Point2D(RailsIHM.getPrimaryStage().getHeight()/2, RailsIHM.getPrimaryStage().getWidth()/2);
        p.setAnchorX(RailsIHM.getPrimaryStage().getX() + point.getX());
        p.setAnchorY(RailsIHM.getPrimaryStage().getY() + point.getY());
    }


    //listener poru les cartes
    ListChangeListener<ICarteTransport> listenerCarteTransport = new ListChangeListener<ICarteTransport>() {
        @Override
        public void onChanged(Change<? extends ICarteTransport> change) {
            while(change.next()){
                if(change.wasAdded()){
                    if( ((VueDuJeu) getScene().getRoot()).getJeu().jeuEnPreparationProperty().get()) {
                        for (int i = 0; i < change.getAddedSubList().size(); i += 4) {
                            List<ICarteTransport> list = new ArrayList<ICarteTransport>();
                            VBox v = new VBox();
                            v.setAlignment(Pos.CENTER);
                            v.setSpacing(-25);

                            int compteur = i;
                            if ((change.getAddedSubList().size() - i) >= 4) {
                                while (compteur < i + 4) {
                                    list.add(change.getAddedSubList().get(compteur));
                                    compteur++;
                                }
                            } else {

                                while (compteur < change.getAddedSubList().size()) {
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
                            VueCarteTransport v = new VueCarteTransport(c, 1, 60, 96);
                            ajouteCarte(v);
                        }
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

    EventHandler<MouseEvent> faitApparaitrePopupDestination = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            p.show(RailsIHM.getPrimaryStage());
        }
    };
    EventHandler<MouseEvent> faireDisparaitrePopupDestination = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {

            p.hide();
        }
    };

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
            if(joueurCourant.getDestinations().size()<=8) {
                for (IDestination d : joueurCourant.getDestinations()) {
                    Label v = new Label(d.getVilles().toString());
                    v.setWrapText(true);
                    v.setFont(Font.font("Book Antiqua", 14));
                    v.setTextAlignment(TextAlignment.CENTER);
                    destinationsBox.getChildren().add(v);
                }
            }else{
                for (int i=0; i<8; i++) {
                    IDestination d = joueurCourant.getDestinations().get(i);
                    Label v = new Label(d.getVilles().toString());
                    v.setWrapText(true);
                    v.setFont(Font.font("Book Antiqua", 14));
                    v.setTextAlignment(TextAlignment.CENTER);
                    destinationsBox.getChildren().add(v);
                }
                Label point = new Label("...");
                destinationsBox.getChildren().add(point);
                for (int i = 0; i < joueurCourant.getDestinations().size(); i+=5) {
                    List<IDestination> list = new ArrayList<IDestination>();
                    VBox v = new VBox();
                    v.setAlignment(Pos.CENTER);
                    v.setSpacing(10);
                    v.setStyle("-fx-background-color: white");

                    int compteur =i;
                    if((joueurCourant.getDestinations().size()-i)>=5){
                        while(compteur<i+5){

                            list.add(joueurCourant.getDestinations().get(compteur));
                            compteur++;
                        }
                    }else{

                        while(compteur<joueurCourant.getDestinations().size()){
                            list.add(joueurCourant.getDestinations().get(compteur));
                            compteur++;
                        }
                    }

                    for (IDestination desti : list  ) {
                        Label destination = new Label(desti.getVilles().toString());
                        v.getChildren().add(destination);

                    }

                    destinationsQuandTrop.getChildren().add(v);
                }



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
        destinationsBox.addEventHandler(MouseEvent.MOUSE_ENTERED,faitApparaitrePopupDestination);
        destinationsBox.addEventHandler(MouseEvent.MOUSE_EXITED,faireDisparaitrePopupDestination);

    }

}
