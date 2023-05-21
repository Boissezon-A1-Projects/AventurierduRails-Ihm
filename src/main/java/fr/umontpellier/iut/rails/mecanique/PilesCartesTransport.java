package fr.umontpellier.iut.rails.mecanique;

import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PilesCartesTransport {
    private final ObservableList<CarteTransport> pilePioche;
    private final List<CarteTransport> pileDefausse;

    public ObservableList<CarteTransport> getPilePioche() { //ajout pour tester
        return pilePioche;
    }

    public List<CarteTransport> getPileDefausse() { //ajout pour tester
        return pileDefausse;
    }

    public PilesCartesTransport(List<CarteTransport> cartes) {
        pilePioche = FXCollections.observableArrayList(cartes);
        pileDefausse = new ArrayList<>();
        // on mélange la pile de pioche
        Collections.shuffle(pilePioche);
    }

    /**
     * Retire une carte. Si la pioche est vide, alors on mélange les cartes de la défausse et on les transfère dans la pioche.
     * La fonction retire et renvoie la première carte de la pioche (si elle existe) et null sinon.
     * @return la carte retirée ou null
     */
    public CarteTransport piocher() {
        remplirAvecDefausse();
        if (pilePioche.isEmpty()) {
            return null;
        }
        return pilePioche.remove(0);
    }

    public void remplirAvecDefausse() {
        if (pilePioche.isEmpty()) {
            pilePioche.addAll(pileDefausse);
            pileDefausse.clear();
            Collections.shuffle(pilePioche);
        }
    }

    public void defausser(CarteTransport carte) {
        pileDefausse.add(carte);
    }

    /**
     * @return true si aucune carte ne peut être prise (si les deux piles sont vides)
     */
    public boolean estVide() {
        return pilePioche.isEmpty() && pileDefausse.isEmpty();
    }

    public List<CarteTransport> getCartes() {
        ArrayList<CarteTransport> cartes = new ArrayList<>();
        cartes.addAll(pilePioche);
        cartes.addAll(pileDefausse);
        return cartes;
    }

    public Map<String, Object> dataMap() {
        return Map.ofEntries(
                Map.entry("pioche", pilePioche.size()),
                Map.entry("defausse", pileDefausse));
    }
}
