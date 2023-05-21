package fr.umontpellier.iut.rails;

import javafx.beans.property.ObjectProperty;

public interface IVille {
    ObjectProperty<IJoueur> proprietaireProperty();
    String getNom();
}