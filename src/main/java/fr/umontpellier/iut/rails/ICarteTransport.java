package fr.umontpellier.iut.rails;

import fr.umontpellier.iut.rails.mecanique.data.Couleur;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public interface ICarteTransport {
    boolean estWagon();
    boolean estBateau();
    boolean estJoker();
    boolean getAncre();
    boolean estDouble();
    String getStringCouleur();

    static List<String> getCouleursSimples() {
        return Stream.of(Couleur.values()).filter(c -> c != Couleur.GRIS).map(Objects::toString).toList();
    }

}
