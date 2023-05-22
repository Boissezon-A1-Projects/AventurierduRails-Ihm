package fr.umontpellier.iut.rails.mecanique;

import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.mecanique.data.*;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.DebutTour;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.initialisation.InitialisationDestinations;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.pioches.ReveleCartesVisiblesQuandPiochesVides;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Joueur implements IJoueur {

    /**
     * Nom du joueur
     */
    private final ObjectProperty<String> nom;
    /**
     * Liste des cartes que le joueur a en main
     */
    final ObservableList<CarteTransport> cartesTransport;
    /**
     * Liste temporaire de cartes transport que le joueur est en train de jouer pour
     * payer la capture d'une route ou la construction d'un port
     */
    final ObservableList<CarteTransport> cartesTransportPosees;
    /**
     * Nombre de pions wagons que le joueur peut encore poser sur le plateau
     */
    private final IntegerProperty nbPionsWagon;
    /**
     * Nombre de pions bateaux que le joueur peut encore poser sur le plateau
     */
    private final IntegerProperty nbPionsBateau;
    /**
     * Liste des destinations à réaliser pendant la partie
     */
    private final ObservableList<Destination> destinations;
    /**
     * Score courant du joueur (somme des valeurs des routes capturées), et points
     *      perdus lors des échanges de pions)
     */
    private final IntegerProperty score;
    /**
     * Jeu auquel le joueur est rattaché
     */
    final Jeu jeu;
    /**
     * CouleurJouer du joueur (pour représentation sur le plateau)
     */
    private final CouleurJoueur couleur;
    /**
     * Liste des villes sur lesquelles le joueur a construit un port
     */
    private final List<Ville> ports;
    /**
     * Liste des routes capturées par le joueur
     */
    private final List<Route> routes;
    /**
     * Nombre de pions wagons que le joueur a dans sa réserve (dans la boîte)
     */
    private int nbPionsWagonEnReserve;
    /**
     * Nombre de pions bateaux que le joueur a dans sa réserve (dans la boîte)
     */
    private int nbPionsBateauEnReserve;

    public Joueur(String nom, Jeu jeu, CouleurJoueur couleur) {
        this.nom =  new SimpleObjectProperty<>(nom);
        this.jeu = jeu;
        this.couleur = couleur;
        this.ports = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.nbPionsWagon = new SimpleIntegerProperty();
        this.nbPionsWagonEnReserve = 25;
        this.nbPionsBateau = new SimpleIntegerProperty(60);
        this.nbPionsBateauEnReserve = -10;
        this.cartesTransport = FXCollections.observableArrayList();
        this.cartesTransportPosees = FXCollections.observableArrayList();
        this.destinations = FXCollections.observableArrayList();
        this.score = new SimpleIntegerProperty();
    }

    /**
     * Accès aux propriétés
     */
    public ObservableList<CarteTransport> cartesTransportProperty() { return cartesTransport; }
    public ObservableList<CarteTransport> cartesTransportPoseesProperty() { return cartesTransportPosees; }
    public IntegerProperty nbPionsWagonsProperty() {
        return nbPionsWagon;
    }
    public IntegerProperty nbPionsBateauxProperty() {
        return nbPionsBateau;
    }

    public ObjectProperty<String> nomProperty() {
        return nom;
    }
    public ObservableList<Destination> destinationsProperty() {
        return destinations;
    }
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Getters utiles
     */
    @Override
    public String getNom() {
        return nomProperty().getValue();
    }
    @Override
    public CouleurJoueur getCouleur() {
        return couleur;
    }
    @Override
    public List<Destination> getDestinations() {
        return destinations;
    }
    @Override
    public List<CarteTransport> getCartesTransport() {
        return cartesTransport;
    }
    @Override
    public int getNbPionsWagon() {
        return nbPionsWagon.getValue();
    }
    @Override
    public int getNbPionsBateau() {
        return nbPionsBateau.getValue();
    }
    @Override
    public int getScore() {
        return score.getValue();
    }
    @Override
    public int getNbPorts() {
        return ports.size();
    }
    public Jeu getJeu() {
        return jeu;
    }
    public List<Route> getRoutes() {
        return routes;
    }
    public List<CarteTransport> getCartesTransportPosees() {
        return cartesTransportPosees;
    }


    /**
     * Ajoute une carte transport à la main du joueur
     *
     * @param c la carte à ajouter
     */
    public void ajouterCarteTransport(CarteTransport c) {
        cartesTransport.add(c);
    }

    /**
     * Déplace une carte transport de la main du joueur vers sa pile de cartes
     * posées
     *
     * @param c carte transport à poser
     */
    private void poserCarteTransport(CarteTransport c) {
        if (cartesTransport.remove(c)) {
            cartesTransportPosees.add(c);
        }
    }

    /**
     * Déplace une carte transport de la main du joueur vers sa pile de cartes
     * posées
     *
     * @param nom Nom de la carte transport à poser
     */
    public int poserCarteTransport(String nom) {
        for (CarteTransport c : cartesTransport) {
            if (c.getNom().equals(nom)) {
                poserCarteTransport(c);
                return c.estDouble() ? 2 : 1;
            }
        }
        return 0;
    }

    /**
     * Défausse toutes les cartes transport posées devant le joueur.
     * Elles sont mises dans la pile de défausse du jeu correspondante à leur type
     * (wagon ou bateau)
     */
    private void defausserCartesTransportPosees() {
        while (!cartesTransportPosees.isEmpty()) {
            jeu.defausserCarteTransport(cartesTransportPosees.remove(0));
        }
    }

    public void defausserDestination(Destination destinationChoisie) {
        jeu.destinationsInitialesProperty().remove(destinationChoisie);
        jeu.defausserDestination(destinationChoisie);
    }

    public void mettreAJourDestinations() {
        List<Destination> destinationsPiochees = jeu.destinationsInitialesProperty();
        destinations.addAll(destinationsPiochees);
        jeu.destinationsInitialesProperty().clear();
    }

    public List<String> preparerDestinationsAChoisir(int nbDestinationsPiochees) {
        List<String> destinationsPiochees = new ArrayList<>();
        for (int i = 0; i < nbDestinationsPiochees; i++) {
            Destination d = jeu.piocherDestination();
            if (d == null) {
                break;
            }
            destinationsPiochees.add(d.getNom());
            jeu.destinationsInitialesProperty().add(d);
        }
        return destinationsPiochees;
    }

    /**
     * Cette méthode est exécutée lorsque le joueur pioche des destinations
     *
     */
    void prendreDestinationsInitiales() {
        etatCourant = new InitialisationDestinations(this, 5 , 3);
        etatCourant.prendreDestinations();
    }

    /**
     * Méthode appelée au début de la partie pour préparer l'état initial du joueur
     *
     * - distribue 3 cartes wagon et 7 cartes bateau
     * - fait choisir des destinations au joueur (il doit en conserver au moins 3
     * parmi 5 proposées)
     * - demande au joueur le nombre de pions wagon qu'il veut prendre (10-25) et
     * distribue les pions wagon et bateau
     */
    public void preparation() {
        ArrayList<CarteTransport> temporaire = new ArrayList<>();
        // Piocher 3 cartes wagon et 7 cartes bateau
        for (int i = 0; i < 3; i++) {
            temporaire.add(jeu.piocherCarteWagon());
        }
        for (int i = 0; i < 7; i++) {
            temporaire.add(jeu.piocherCarteBateau());
        }
        cartesTransport.addAll(temporaire);
        ajouterPionsWagonInitial(20);

        // Piocher 5 destinations. Le joueur doit en garder au moins 3.
        prendreDestinationsInitiales();
    }

    /**
     * Méthode principale qui exécute le tour d'un joueur.
     * Cette méthode fait la liste de tous les choix correspondant aux différentes
     * actions que le joueur peut réaliser à son tour, demande un choix de
     * l'utilisateur et exécute la méthode correspondant à l'action choisie.
     *
     * Les 5 actions possibles sont :
     * - piocher une carte transport (visible ou dans une pioche)
     * - piocher des destinations
     * - capturer une route
     * - construire un port
     * - échanger des pions wagon/bateau
     *
     * Remarque : le joueur a le droit de passer son tour en ne faisant aucune
     * action (ce n'est pas ce qui est précisé dans les règles mais c'est plus
     * pratique pour tester le bon fonctionnement du programme)
     */
    public void jouerTour() {
        getJeu().instructionProperty().setValue(getJeu().getEtatCourant().getInfosPhase() + nom.getValue());
        etatCourant = new DebutTour(this);
    }

    public void revelerCartesVisibles() {
        int nbCartesAReveler = 6 - getJeu().getCartesTransportVisibles().size();
        String pluriel = nbCartesAReveler > 1 ? "s" :"";
        getJeu().instructionProperty().setValue(getJeu().instructionProperty().getValue() + " - Vous devez d'abord réveler " + nbCartesAReveler + " carte" + pluriel);
        etatCourant = new ReveleCartesVisiblesQuandPiochesVides(this, nbCartesAReveler);
    }

    boolean peutPayerPort() {
        CarteTransport.Catalogue catalogue = new CarteTransport.Catalogue(
                cartesTransport.stream().filter(CarteTransport::getAncre).toList());
        int nbJokers = catalogue.getValeur(TypeCarteTransport.JOKER);
        for (Couleur c : Couleur.getCouleursSimples()) {
            int valeurWagon = Math.min(2, catalogue.getValeur(TypeCarteTransport.WAGON, c));
            int valeurBateau = Math.min(2, catalogue.getValeur(TypeCarteTransport.BATEAU, c));
            if (valeurWagon + valeurBateau + nbJokers >= 4) {
                return true;
            }
        }
        return false;
    }

    public List<String> portsPouvantEtrePris() {
        List<String> options = new ArrayList<>();
        if (ports.size() < 3 && peutPayerPort()) {
            for (Ville v : jeu.getPortsLibres()) {
                for (Route r : routes) {
                    if (r.getVille1() == v || r.getVille2() == v) {
                        options.add(v.nom());
                        break;
                    }
                }
            }
        }
        return options;
    }

    public int nbMaxPionsWagonPossibles() {
        return Math.min(nbPionsWagonEnReserve, nbPionsBateau.get());
    }

    public int nbMaxPionsBateauPossibles() {
        return Math.min(nbPionsBateauEnReserve, nbPionsWagon.get());
    }

    public void ajouterPionsWagon(int n) {
        nbPionsBateau.set(nbPionsBateau.get() - n);
        nbPionsBateauEnReserve += n;
        nbPionsWagon.set(nbPionsWagon.get() + n);
        nbPionsWagonEnReserve -= n;
        score.setValue(score.getValue() - n);
    }

    public void ajouterPionsWagonInitial(int n) {
        nbPionsWagon.setValue(n);
        nbPionsWagonEnReserve = 25 - n;
        nbPionsBateau.setValue(60 - n);
        nbPionsBateauEnReserve = n - 10;
    }

    public void ajouterPionsBateau(int n) {
        nbPionsBateau.set(nbPionsBateau.get() + n);
        nbPionsBateauEnReserve -= n;
        nbPionsWagon.set(nbPionsWagon.get() - n);
        nbPionsWagonEnReserve += n;
        score.setValue(score.getValue() - n);
    }

    public void finaliserCaptureRoute(Route r) {
        jeu.retirerRouteLibre(r.getNom());
        r.setProprietaire(this);
        defausserCartesTransportPosees();
        nbPionsBateau.set(nbPionsBateau.get() - r.getNbPionsBateau());
        nbPionsWagon.set(nbPionsWagon.get() - r.getNbPionsWagon());
        score.setValue(score.getValue() + r.getScore()); // this.score += r.getScore();
        routes.add(r);
    }

    public void finaliserConstruirePort(Ville port) {
        jeu.retirerPortLibre(port.getNom());
        port.setProprietaire(this);
        defausserCartesTransportPosees();
        ports.add(port);
    }

    private Set<String> getNomsVillesVoisines(String nomVille) {
        Set<String> nomsVillesVoisines = new HashSet<>();
        for (Route r : routes) {
            if (r.getVille1().nom().equals(nomVille)) {
                nomsVillesVoisines.add(r.getVille2().nom());
            } else if (r.getVille2().nom().equals(nomVille)) {
                nomsVillesVoisines.add(r.getVille1().nom());
            }
        }
        return nomsVillesVoisines;
    }

    boolean villesSontConnectees(String nomVille1, String nomVille2) {
        List<String> frontiere = new ArrayList<>();
        Set<String> dejaVues = new HashSet<>();
        frontiere.add(nomVille1);
        dejaVues.add(nomVille1);

        while (!frontiere.isEmpty()) {
            String nomVille = frontiere.remove(0);
            if (nomVille.equals(nomVille2)) {
                return true;
            }
            for (String nomVilleVoisine : getNomsVillesVoisines(nomVille)) {
                if (!dejaVues.contains(nomVilleVoisine)) {
                    frontiere.add(nomVilleVoisine);
                    dejaVues.add(nomVilleVoisine);
                }
            }
        }
        return false;
    }

    boolean destinationEstComplete(Destination d) {
        // ATTENTION : cette méthode est incomplète, car elle ne gère pas complètement
        // les Destinations Itinéraire (elle n'est pas forcément la plus adaptée si on
        // veut utiliser des graphes...).
        // Elle est à réécrire en utilisant les Graphes
        for (int i = 0; i < d.getVilles().size() - 1; i++) {
            if (!villesSontConnectees(d.getVilles().get(i), d.getVilles().get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    public int calculerScoreFinal() {
        int scoreFinal = score.getValue();
        List<Destination> destinationsCompletes = new ArrayList<>();
        List<Destination> destinationsIncompletes = new ArrayList<>();
        for (Destination d : destinations) {
            if (destinationEstComplete(d)) {
                destinationsCompletes.add(d);
            } else {
                destinationsIncompletes.add(d);
            }
        }
        for (Destination d : destinationsCompletes) {
            scoreFinal += d.getValeurSimple();
        }
        for (Destination d : destinationsIncompletes) {
            scoreFinal -= d.getPenalite();
        }
        for (Ville port : ports) {
            int nbVilles = 0;
            for (Destination d : destinationsCompletes) {
                if (d.getVilles().contains(port.nom())) {
                    nbVilles += 1;
                }
            }
            switch (nbVilles) {
                case 0 -> {
                }
                case 1 -> scoreFinal += 20;
                case 2 -> scoreFinal += 30;
                default -> scoreFinal += 40;
            }
        }
        scoreFinal -= 4 * (3 - ports.size());
        return scoreFinal;
    }

    /**
     * Renvoie une collection contenant un plus court ensemble de routes (en nombre
     * total de pions utilisés) que le joueur peut capturer pour compléter la
     * destination passée en paramètre
     * <p>
     * La méthode renvoie une collection vide si la destination est déjà complète ou
     * s'il n'est pas possible de la compléter
     */
    public Collection<Route> routesPourCompleterDestination(Destination d) {
        // Cette fonction est à réécrire entièrement
        throw new RuntimeException("Méthode non implémentée");
    }

    /**
     * Renvoie une collection contenant un plus court ensemble de routes (en nombre
     * total de pions utilisés) que le joueur peut capturer pour compléter la
     * destination passée en paramètre en utilisant les pions dont le joueur dispose
     * actuellement
     * <p>
     * La méthode renvoie une collection vide si la destination est déjà complète ou
     * s'il n'est pas possible de la compléter.
     */
    public Collection<Route> routesPourCompleterDestinationAvecPions(Destination d) {
        // Cette fonction est à réécrire entièrement
        throw new RuntimeException("Méthode non implémentée");
    }

    public List<String> getCartesAutoriseesPourRoute(String nom) {
        Route r = jeu.trouverRoute(nom);
        if (r != null) {
            Collection<CarteTransport> optionsCartes = r.getCarteAutoriseesTransports(this);
            return optionsCartes.stream().map(CarteTransport::getNom).toList();
        }
        return new ArrayList<>();
    }

    public boolean nbInsuffisantPions() {
        return nbPionsWagon.get() + nbPionsBateau.get() <= 6;
    }

    public void revelerCarteTransport(String choix) {
        if (choix.equals("WAGON")) {
            jeu.revelerCarteWagon();
        } else if (choix.equals("BATEAU")) {
            jeu.revelerCarteBateau();
        }
    }

    public CarteTransport prendreCarteTransportVisible(String choix) {
        for (CarteTransport c : jeu.getCartesTransportVisibles()) {
            if (c.getNom().equals(choix)) {
                jeu.retirerCarteTransportVisible(c);
                return c;
            }
        }
        return null;
    }

    public List<String> getCartesAutoriseesPourPort(String nom) {
        CarteTransport.Catalogue catalogueCartesPosees = new CarteTransport.Catalogue(cartesTransportPosees);
        // on ne compte que les cartes en main ayant une ancre
        CarteTransport.Catalogue catalogueCartesEnMain = new CarteTransport.Catalogue(
                cartesTransport.stream().filter(CarteTransport::getAncre).toList());
        Couleur couleur = catalogueCartesPosees.getCouleur();
        int nbJokersPoses = catalogueCartesPosees.get(TypeCarteTransport.JOKER, Couleur.GRIS).size();
        int nbJokersEnMain = catalogueCartesEnMain.get(TypeCarteTransport.JOKER, Couleur.GRIS).size();

        List<CarteTransport> optionsCartes = new ArrayList<>(
                catalogueCartesEnMain.get(TypeCarteTransport.JOKER, Couleur.GRIS));
        if (couleur == Couleur.GRIS) {
            // aucune carte simple (non-joker) posée
            for (Couleur c : Couleur.getCouleursSimples()) {
                int valeurWagonEnMain = Math.min(2, catalogueCartesEnMain.getValeur(TypeCarteTransport.WAGON, c));
                int valeurBateauEnMain = Math.min(2, catalogueCartesEnMain.getValeur(TypeCarteTransport.BATEAU, c));
                if (valeurWagonEnMain + valeurBateauEnMain + nbJokersEnMain + nbJokersPoses >= 4) {
                    // le joueur peut payer le port avec des cartes de la couleur c
                    optionsCartes.addAll(catalogueCartesEnMain.get(TypeCarteTransport.WAGON, c));
                    optionsCartes.addAll(catalogueCartesEnMain.get(TypeCarteTransport.BATEAU, c));
                }
            }
        } else {
            if (catalogueCartesPosees.getValeur(TypeCarteTransport.WAGON, couleur) < 2) {
                optionsCartes.addAll(catalogueCartesEnMain.get(TypeCarteTransport.WAGON, couleur));
            }
            if (catalogueCartesPosees.getValeur(TypeCarteTransport.BATEAU, couleur) < 2) {
                optionsCartes.addAll(catalogueCartesEnMain.get(TypeCarteTransport.BATEAU, couleur));
            }
        }
        return optionsCartes.stream().map(CarteTransport::getNom).toList();
    }

    /**
     * Gestion des états du joueur courant
     */
    private EtatJoueur etatCourant;

    public void setEtatCourant(EtatJoueur etatCourant) {
        this.etatCourant = etatCourant;
    }

    public EtatJoueur getEtatCourant() {
        return etatCourant;
    }

}



