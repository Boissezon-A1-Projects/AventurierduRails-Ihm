package fr.umontpellier.iut.rails.mecanique;

import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IRoute;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.Couleur;
import fr.umontpellier.iut.rails.mecanique.data.Ville;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Collection;

public abstract class Route implements IRoute {
    /**
     * Première extrémité
     */
    private final Ville ville1;
    /**
     * Deuxième extrémité
     */
    private final Ville ville2;
    /**
     * Nombre de segments
     */
    private final int longueur;
    /**
     * CouleurJouer pour capturer la route (éventuellement GRIS)
     */
    private final Couleur couleur;
    /**
     * Nom unique de la route (utilisé pour l'interface utilisateur)
     */
    private final String nom;
    /**
     * Route parallèle (pour les routes doubles) ou null si la route n'est pas
     * double
     */
    private Route routeParallele = null;
    /**
     * Compteur du nombre de routes instanciées (utilisé pour donner automatiquement
     * un id unique à chaque route)
     * Vous ne devez pas toucher à cet attribut qui est utilisé pour les interfaces
     * (console et web)
     */
    private static int compteur = 1;
    /**
     * Joueur qui a capturé la route (`null` si la route est encore à prendre)
     */

    private final ObjectProperty<IJoueur> proprietaire;

    public Route(Ville ville1, Ville ville2, Couleur couleur, int longueur) {
        this.ville1 = ville1;
        this.ville2 = ville2;
        this.couleur = couleur;
        this.longueur = longueur;
        this.nom = "R" + compteur++;
        this.proprietaire = new SimpleObjectProperty<>();
    }

    public static void resetCompteur() {
        compteur = 1;
    }
    public Ville getVille1() {
        return ville1;
    }

    public Ville getVille2() {
        return ville2;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getScore() {
        return switch (longueur) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 7;
            case 5 -> 10;
            case 6 -> 15;
            case 7 -> 18;
            case 8 -> 21;
            default -> 0;
        };
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }

    public void setRouteParallele(Route route) {
        this.routeParallele = route;
    }

    public int getNbPionsBateau() {
        return 0;
    }

    public int getNbPionsWagon() {
        return 0;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire.setValue(proprietaire);
    }

    public ObjectProperty<IJoueur> proprietaireProperty() {
        return proprietaire;
    }

    /**
     * Retourne true si la route peut être capturée par le joueur
     * On suppose que la route est disponible (pas déjà capturée)
     * 
     * L'implémentation dans la classe Route ne vérifie que les conditions liées aux
     * routes doubles. Chaque sous-classe de Route doit redéfinir cette méthode pour
     * vérifier si le joueur a également assez de pions et de cartes transport pour
     * capturer la route.
     *
     * @param joueur
     * @return
     */
    public boolean peutEtreCaptureePar(Joueur joueur) {
        if (routeParallele == null) {
            return true;
        }
        Jeu jeu = joueur.getJeu();
        if (jeu.getJoueurs().size() <= 3) {
            // s'il y a 3 joueurs ou moins, on peut capturer une route double si la
            // route parallèle n'a pas déjà été capturée
            return jeu.getRoutesLibres().contains(routeParallele);
        } else {
            // s'il y a au moins 4 joueurs, on peut capturer une route double si la route
            // parallelle n'a pas déjà été capturée par le même joueur
            return !joueur.getRoutes().contains(routeParallele);
        }
    }

    public abstract Collection<CarteTransport> getCarteAutoriseesTransports(Joueur joueur);

    public int nbCartesAPayer() {
        return longueur;
    }

}
