package fr.umontpellier.iut.rails.mecanique.data;

import fr.umontpellier.iut.rails.ICarteTransport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CarteTransport implements Comparable<CarteTransport>, ICarteTransport {

    /**
     * Compteur du nombre de routes instanciées (utilisé pour donner automatiquement
     * un id unique à chaque route)
     */

    private static int compteur = 1;
    private final TypeCarteTransport type;
    private final Couleur couleur;
    private final boolean estDouble;
    private final boolean ancre;
    private final String nom;

    public CarteTransport(TypeCarteTransport type, Couleur couleur, boolean estDouble, boolean ancre) {
        this.type = type;
        this.couleur = couleur;
        this.estDouble = estDouble;
        this.ancre = ancre;
        this.nom = "C" + compteur++;
    }

    @Override
    public int compareTo(CarteTransport o) {
        if (getType() != o.getType()) {
            return getType().compareTo(o.getType());
        }
        if (getCouleur() != o.getCouleur()) {
            return getCouleur().compareTo(o.getCouleur());
        }
        if (estDouble() != o.estDouble()) {
            return Boolean.compare(estDouble(), o.estDouble());
        }
        if (getAncre() != o.getAncre()) {
            return Boolean.compare(getAncre(), o.getAncre());
        }
        return getNom().compareTo(o.getNom());
    }

    public TypeCarteTransport getType() {
        return type;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public String getStringCouleur() {
        return couleur.toString();
    }

    public boolean estDouble() {
        return estDouble;
    }

    @Override
    public boolean estWagon() {
        return type == TypeCarteTransport.WAGON;
    }

    @Override
    public boolean estBateau() {
        return type == TypeCarteTransport.BATEAU;
    }

    @Override
    public boolean estJoker() {
        return type == TypeCarteTransport.JOKER;
    }

    public boolean getAncre() {
        return ancre;
    }

    public String getNom() {
        return nom;
    }

    public static class Catalogue {
        private final Map<TypeCarteTransport, Map<Couleur, List<CarteTransport>>> catalogue = new HashMap<>();

        public Catalogue(List<CarteTransport> cartes) {
            for (TypeCarteTransport t : TypeCarteTransport.values()) {
                catalogue.put(t, new HashMap<>());
                for (Couleur c : Couleur.values()) {
                    catalogue.get(t).put(c, new ArrayList<>());
                }
            }
            for (CarteTransport c : cartes) {
                catalogue.get(c.getType()).get(c.getCouleur()).add(c);
            }
        }

        /**
         * Renvoie la liste des cartes du catalogue correspondant au type et à la
         * couleur
         */
        public List<CarteTransport> get(TypeCarteTransport type, Couleur couleur) {
            return catalogue.get(type).get(couleur);
        }

        public List<CarteTransport> getDoubles(TypeCarteTransport type, Couleur couleur) {
            return catalogue.get(type).get(couleur).stream().filter(CarteTransport::estDouble).toList();
        }

        public List<CarteTransport> getSimples(TypeCarteTransport type, Couleur couleur) {
            return catalogue.get(type).get(couleur).stream().filter(c -> !c.estDouble).toList();
        }

        /**
         * Renvoie la valeur totale des cartes du catalogue correspondant à un type
         *
         * @param type        le type des cartes à compter
         * @param compteJoker si vrai, les jokers sont comptés comme des cartes du type
         *                    demandé
         */
        public int getValeur(TypeCarteTransport type, boolean compteJoker) {
            int total = 0;
            for (Couleur couleur : Couleur.values()) {
                for (CarteTransport carte : get(type, couleur)) {
                    if (carte.estDouble()) {
                        total += 2;
                    } else {
                        total += 1;
                    }
                }
            }
            if (type != TypeCarteTransport.JOKER && compteJoker) {
                total += getValeur(TypeCarteTransport.JOKER, false);
            }
            return total;
        }

        /**
         * Renvoie la valeur totale des cartes du catalogue correspondant à un type.
         * Les jokers ne sont pas comptés.
         */
        public int getValeur(TypeCarteTransport type) {
            return getValeur(type, false);
        }

        /**
         * Renvoie la valeur totale des cartes du catalogue correspondant à un type et à
         * une couleur
         *
         * @param type        le type des cartes à compter
         * @param couleur     la couleur des cartes comptées. Si couleur est GRIS, la
         *                    méthode renvoie la valeur de la couleur ayant la plus
         *                    grande valeur
         * @param compteJoker si vrai, les jokers sont comptés comme des cartes de la
         *                    couleur demandée
         */
        public int getValeur(TypeCarteTransport type, Couleur couleur, boolean compteJoker) {

            if (couleur == Couleur.GRIS) {
                return Couleur.getCouleursSimples().stream()
                        .map(c -> getValeur(type, c, compteJoker))
                        .reduce(0, Integer::max);
            }

            int total = 0;
            for (CarteTransport c : catalogue.get(type).get(couleur)) {
                if (c.estDouble()) {
                    total += 2;
                } else {
                    total += 1;
                }
            }
            if (compteJoker && type != TypeCarteTransport.JOKER) {
                total += getValeur(TypeCarteTransport.JOKER);
            }
            return total;
        }

        /**
         * Renvoie la valeur totale des cartes du catalogue correspondant à un type et à
         * une couleur.
         * Les jokers ne sont pas comptés.
         *
         * @param type    le type des cartes à compter
         * @param couleur la couleur des cartes comptées. Si couleur est GRIS, la
         *                méthode renvoie la valeur de la couleur ayant la plus grande
         *                valeur
         */
        public int getValeur(TypeCarteTransport type, Couleur couleur) {
            return getValeur(type, couleur, false);
        }

        /**
         * Renvoie la valeur totale des cartes du catalogue
         */
        public int getValeur() {
            int total = 0;
            for (TypeCarteTransport t : TypeCarteTransport.values()) {
                total += getValeur(t);
            }
            return total;
        }

        /**
         * Renvoie la première couleur dont le catalogue contient au moins une carte
         * (WAGON ou BATEAU).
         * Si le catalogue ne contient aucune carte de couleur, la méthode renvoie GRIS.
         */
        public Couleur getCouleur() {
            for (Couleur c : Couleur.getCouleursSimples()) {
                if (!get(TypeCarteTransport.WAGON, c).isEmpty() || !get(TypeCarteTransport.BATEAU, c).isEmpty()) {
                    return c;
                }
            }
            return Couleur.GRIS;
        }
    }
}
