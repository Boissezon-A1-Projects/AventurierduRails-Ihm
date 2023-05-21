package fr.umontpellier.iut.rails;

import javafx.beans.property.ObjectProperty;

public interface IRoute {
    ObjectProperty<IJoueur> proprietaireProperty();
    String getNom();
}