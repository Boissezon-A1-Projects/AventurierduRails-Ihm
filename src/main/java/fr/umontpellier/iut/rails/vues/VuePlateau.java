package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IRoute;
import fr.umontpellier.iut.rails.IVille;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit les handlers à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront à jour le plateau après la prise d'une route ou d'un port par un joueur
 */
public class VuePlateau extends Pane {

    @FXML
    private ImageView mapMonde;



    private IRoute routeChoisie;

    private IVille portChoisi;

    public VuePlateau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMinSize(Screen.getPrimary().getBounds().getWidth()/3, Screen.getPrimary().getBounds().getHeight()/3) ;
    }

    private ListChangeListener<ICarteTransport> ajouteDesCartesPoseesPourRoute = new ListChangeListener<ICarteTransport>() {
        @Override
        public void onChanged(Change<? extends ICarteTransport> change) {

            routeChoisie.proprietaireProperty().setValue( ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().get());

        }
    };

    private ListChangeListener<ICarteTransport> ajouteDesCartesPoseesPourPort = new ListChangeListener<ICarteTransport>() {
        @Override
        public void onChanged(Change<? extends ICarteTransport> change) {

            portChoisi.proprietaireProperty().setValue( ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().get());

        }
    };

    private  ChangeListener<IJoueur> changeDeProprioRoute = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
            for (Node o: getChildren() ) {
                if(o.getId().equals(routeChoisie.getNom())) {
                    Image v = new Image("images/wagons/image-wagon-" + routeChoisie.proprietaireProperty().get().getCouleur() + ".png");

                    Rectangle r = (Rectangle) o;
                    r.setFill(new ImagePattern(v));

                }
            }
        }
    };

    private ChangeListener<IJoueur> changeDeProprioPort = new ChangeListener<IJoueur>() {
        @Override
        public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
            for (Node o: getChildren() ) {
                if(o.getId().equals(portChoisi.getNom())) {
                    Image v = new Image("images/gares/gare-" + portChoisi.proprietaireProperty().get().getCouleur() + ".png");

                    Circle c = (Circle) o;
                    c.setFill(new ImagePattern(v));

                }
            }
        }
    };


    EventHandler<MouseEvent> choixRoute = event -> {
        List<? extends IRoute> listeRoutes = ((VueDuJeu) getScene().getRoot()).getJeu().getRoutes();
        Rectangle rect = (Rectangle) event.getSource();
        routeChoisie = listeRoutes.stream().filter(r -> r.getNom().equals(rect.getId())).findAny().orElse(null);
        ((VueDuJeu) getScene().getRoot()).getJeu().uneRouteAEteChoisie(rect.getId());
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().get().cartesTransportProperty().addListener(ajouteDesCartesPoseesPourRoute);

    };



    EventHandler<MouseEvent> choixPort = event -> {
        List<? extends IVille> listePorts = ((VueDuJeu) getScene().getRoot()).getJeu().getPorts();
        Circle ville = (Circle) event.getSource();
        portChoisi = listePorts.stream().filter(r -> r.getNom().equals(ville.getId())).findAny().orElse(null);
        ((VueDuJeu) getScene().getRoot()).getJeu().unPortAEteChoisi(ville.getId());
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().get().cartesTransportProperty().addListener(ajouteDesCartesPoseesPourPort);
    };

    public void creerBindings() {
        ajouterPorts();
        ajouterRoutes();
        bindRedimensionEtCentragePlateau();
    }

    private void ajouterPorts() {
        List<? extends IVille> listePorts = ((VueDuJeu) getScene().getRoot()).getJeu().getPorts();
        for (String nomPort : DonneesGraphiques.ports.keySet()) {
            DonneesGraphiques.DonneesCerclesPorts positionPortSurPlateau = DonneesGraphiques.ports.get(nomPort);
            Circle cerclePort = new Circle(positionPortSurPlateau.centreX(), positionPortSurPlateau.centreY(), DonneesGraphiques.rayonInitial);
            cerclePort.setId(nomPort);
            getChildren().add(cerclePort);
            bindCerclePortAuPlateau(positionPortSurPlateau, cerclePort);
            cerclePort.setOnMouseClicked(choixPort);
            IVille v = listePorts.stream().filter(r -> r.getNom().equals(nomPort)).findAny().orElse(null);
            v.proprietaireProperty().addListener(changeDeProprioPort);
        }
    }






    private void ajouterRoutes() {
        List<? extends IRoute> listeRoutes = ((VueDuJeu) getScene().getRoot()).getJeu().getRoutes();
        for (String nomRoute : DonneesGraphiques.routes.keySet()) {
            ArrayList<DonneesGraphiques.DonneesSegments> segmentsRoute = DonneesGraphiques.routes.get(nomRoute);
            IRoute route = listeRoutes.stream().filter(r -> r.getNom().equals(nomRoute)).findAny().orElse(null);

            for (DonneesGraphiques.DonneesSegments unSegment : segmentsRoute) {
                Rectangle rectangleSegment = new Rectangle(unSegment.getXHautGauche(), unSegment.getYHautGauche(), DonneesGraphiques.largeurRectangle, DonneesGraphiques.hauteurRectangle);
                rectangleSegment.setId(nomRoute);
                rectangleSegment.setRotate(unSegment.getAngle());
                getChildren().add(rectangleSegment);
                rectangleSegment.setOnMouseClicked(choixRoute);
                bindRectangle(rectangleSegment, unSegment.getXHautGauche(), unSegment.getYHautGauche());

            }
            route.proprietaireProperty().addListener(changeDeProprioRoute);

        }
    }




    private void bindRedimensionEtCentragePlateau() {
        mapMonde.fitWidthProperty().bind(widthProperty());
        mapMonde.fitHeightProperty().bind(heightProperty());
        mapMonde.layoutXProperty().bind(new DoubleBinding() { // Pour maintenir le plateau au centre
            {
                super.bind(widthProperty(),heightProperty());
            }
            @Override
            protected double computeValue() {
                double imageViewWidth = mapMonde.getLayoutBounds().getWidth();
                return (getWidth() - imageViewWidth) / 2;
            }
        });
    }

    private void bindCerclePortAuPlateau(DonneesGraphiques.DonneesCerclesPorts port, Circle cerclePort) {
        cerclePort.centerXProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return mapMonde.getLayoutX() + port.centreX() * mapMonde.getLayoutBounds().getWidth()/ DonneesGraphiques.largeurInitialePlateau;
            }
        });
        cerclePort.centerYProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return mapMonde.getLayoutY() + port.centreY() * mapMonde.getLayoutBounds().getHeight()/ DonneesGraphiques.hauteurInitialePlateau;
            }
        });
        cerclePort.radiusProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return DonneesGraphiques.rayonInitial * mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {
        rect.widthProperty().bind(new DoubleBinding() {
            { super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesGraphiques.largeurRectangle * mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.heightProperty().bind(new DoubleBinding() {
            { super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesGraphiques.hauteurRectangle * mapMonde.getLayoutBounds().getWidth()/ DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty(), mapMonde.xProperty());
            }
            @Override
            protected double computeValue() {
                return mapMonde.getLayoutX() + layoutX * mapMonde.getLayoutBounds().getWidth()/ DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.xProperty().bind(new DoubleBinding() {
            { super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty(), mapMonde.xProperty());}
            @Override
            protected double computeValue() {
                return mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * mapMonde.getLayoutBounds().getHeight()/ DonneesGraphiques.hauteurInitialePlateau;
            }
        });
        rect.yProperty().bind(new DoubleBinding() {
            { super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return mapMonde.getLayoutBounds().getHeight()/ DonneesGraphiques.hauteurInitialePlateau;
            }
        });
    }

}