package fr.umontpellier.iut.rails.mecanique.data;

import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IVille;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public final class Ville implements IVille {
    private final String nom;
    private final boolean estPort;
    private final int id;
    private static int compteur = 0;
    private final ObjectProperty<IJoueur> proprietaire;

    public Ville(
            String nom,
            boolean estPort) {
        this.nom = nom;
        this.estPort = estPort;
        id = compteur++;
        proprietaire = new SimpleObjectProperty<>();
    }

    public String nom() {
        return nom;
    }

    public boolean estPort() {
        return estPort;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Ville) obj;
        return Objects.equals(this.nom, that.nom) &&
                this.estPort == that.estPort;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, estPort);
    }

    public int getId() {
        return id;
    }

    @Override
    public ObjectProperty<IJoueur> proprietaireProperty() {
        return proprietaire;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire.set(proprietaire);
    }

    @Override
    public String getNom() {
        return nom;
    }

}
