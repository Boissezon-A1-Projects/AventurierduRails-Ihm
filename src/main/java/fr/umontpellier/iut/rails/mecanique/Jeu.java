package fr.umontpellier.iut.rails.mecanique;

import fr.umontpellier.iut.rails.*;
import fr.umontpellier.iut.rails.mecanique.data.*;
import fr.umontpellier.iut.rails.mecanique.etatsJeu.EtatJeu;
import fr.umontpellier.iut.rails.mecanique.etatsJeu.InitialisationJoueurs;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Jeu implements IJeu {

    /**
     * Liste des joueurs
     */
    private final List<Joueur> joueurs;
    /**
     * Liste des villes disponibles sur le plateau de jeu
     */
    private final List<Ville> portsLibres;
    /**
     * Liste des ports sur le plateau de jeu
     */
    private final List<IVille> ports;
    /**
     * Liste des routes disponibles sur le plateau de jeu
     */
    private final List<Route> routesLibres;
    /**
     * Liste des routes sur le plateau de jeu
     */
    private final List<IRoute> routes;
    /**
     * Pile de pioche et défausse des cartes wagon
     */
    private final PilesCartesTransport pilesDeCartesWagon;
    /**
     * Pile de pioche et défausse des cartes bateau
     */
    private final PilesCartesTransport pilesDeCartesBateau;
    /**
     * Cartes de la pioche face visible (normalement il y a 6 cartes face visible)
     */
    private final ObservableList<CarteTransport> cartesTransportVisibles;
    /**
     * Pile des cartes "Destination"
     */
    private final ObservableList<Destination> pileDestinations;
    /**
     * Le joueur dont c'est le tour
     */
    private final ObjectProperty<IJoueur> joueurCourant;
    /**
     * Une information qui précise au joueur courant ce qu'il doit faire
     */
    private final ObjectProperty<String> instruction;
    /**
     * Liste des destinations parmi lesquelles choisir au début de la partie
     */
    private final ObservableList<Destination> destinationsInitiales;
    /**
     * Permet de savoir si le jeu est en préparation ou si les tours ont commencé
     */
    private final BooleanProperty jeuEnPreparation;
    /**
     * Permet de savoir si la partie est terminée, les scores finaux sont calculés
     */
    private BooleanProperty finDePartie, piocheWagonVide, piocheBateauVide, piocheDestinationVide, saisieNbPionsWagonAutorisee, saisieNbPionsBateauAutorisee;

    /**
     * Accès aux propriétés
     */
    @Override
    public ObservableList<? extends ICarteTransport> cartesTransportVisiblesProperty() {
        return cartesTransportVisibles;
    }
    @Override
    public ObservableList<Destination> destinationsInitialesProperty() {
        return destinationsInitiales;
    }
    @Override
    public ObjectProperty<IJoueur> joueurCourantProperty() {
        return joueurCourant;
    }
    @Override
    public ObjectProperty<String> instructionProperty() {
        return instruction;
    }
    @Override
    public BooleanProperty jeuEnPreparationProperty() {
        return jeuEnPreparation;
    }
    @Override
    public BooleanProperty finDePartieProperty() {
        return finDePartie;
    }
    @Override
    public BooleanProperty piocheWagonVideProperty() {
        return piocheWagonVide;
    }
    @Override
    public BooleanProperty piocheBateauVideProperty() {
        return piocheBateauVide;
    }
    @Override
    public BooleanProperty piocheDestinationVideProperty() {
        return piocheDestinationVide;
    }
    @Override
    public BooleanProperty saisieNbPionsWagonAutoriseeProperty() {
        return saisieNbPionsWagonAutorisee;
    }
    @Override
    public BooleanProperty saisieNbPionsBateauAutoriseeProperty() {
        return saisieNbPionsBateauAutorisee;
    }

    public Jeu(String[] nomJoueurs) {
        // création des villes et des routes
        Route.resetCompteur();
        Plateau plateau = Plateau.makePlateauMonde();
        portsLibres = plateau.getPorts();
        ports = plateau.getIPorts();
        routesLibres = plateau.getRoutes();
        routes = plateau.getIRoutes();

        // création des piles de pioche et défausses des cartes Transport (wagon et
        // bateau)
        ArrayList<CarteTransport> cartesWagon = new ArrayList<>();
        ArrayList<CarteTransport> cartesBateau = new ArrayList<>();
        for (Couleur c : Couleur.values()) {
            if (c == Couleur.GRIS) {
                continue;
            }
            for (int i = 0; i < 4; i++) {
                // Cartes wagon simples avec une ancre
                cartesWagon.add(new CarteTransport(TypeCarteTransport.WAGON, c, false, true));
            }
            for (int i = 0; i < 7; i++) {
                // Cartes wagon simples sans ancre
                cartesWagon.add(new CarteTransport(TypeCarteTransport.WAGON, c, false, false));
            }
            for (int i = 0; i < 4; i++) {
                // Cartes bateau simples (toutes avec une ancre)
                cartesBateau.add(new CarteTransport(TypeCarteTransport.BATEAU, c, false, true));
            }
            for (int i = 0; i < 6; i++) {
                // Cartes bateau doubles (toutes sans ancre)
                cartesBateau.add(new CarteTransport(TypeCarteTransport.BATEAU, c, true, false));
            }
        }
        for (int i = 0; i < 14; i++) {
            // Cartes wagon joker
            cartesWagon.add(new CarteTransport(TypeCarteTransport.JOKER, Couleur.GRIS, false, true));
        }
        pilesDeCartesWagon = new PilesCartesTransport(cartesWagon);
        pilesDeCartesBateau = new PilesCartesTransport(cartesBateau);

        // création de la liste pile de cartes transport visibles
        // (les cartes seront retournées plus tard, au début de la partie dans run())
        cartesTransportVisibles = FXCollections.observableArrayList(); //new ArrayList<>();

        // création des destinations
        pileDestinations = FXCollections.observableArrayList();
        pileDestinations.addAll(Destination.makeDestinationsMonde());
        Collections.shuffle(pileDestinations);

        // création des joueurs
        // les cartes des joueurs sont distribuées lors de la préparation du jeu dans
        // run()
        ArrayList<IJoueur.CouleurJoueur> couleurs = new ArrayList<>(Arrays.asList(IJoueur.CouleurJoueur.values()));
        Collections.shuffle(couleurs);
        joueurs = new ArrayList<>();
        for (String nomJoueur : nomJoueurs) {
            joueurs.add(new Joueur(nomJoueur, this, couleurs.remove(0)));
        }

        destinationsInitiales = FXCollections.observableArrayList();
        joueurCourant = new SimpleObjectProperty<>();
        instruction = new SimpleObjectProperty<>("Début de partie");

        finDePartie = new SimpleBooleanProperty();
        jeuEnPreparation = new SimpleBooleanProperty(true);
        piocheWagonVide = new SimpleBooleanProperty();
        piocheWagonVide.bind(Bindings.isEmpty(pilesDeCartesWagon.getPilePioche()));
        piocheBateauVide = new SimpleBooleanProperty();
        piocheBateauVide.bind(Bindings.isEmpty(pilesDeCartesBateau.getPilePioche()));
        piocheDestinationVide = new SimpleBooleanProperty();
        piocheDestinationVide.bind(Bindings.isEmpty(pileDestinations));
        saisieNbPionsWagonAutorisee = new SimpleBooleanProperty(false);
        saisieNbPionsBateauAutorisee = new SimpleBooleanProperty(false);
    }

    /**
     * Getters utiles
     */
    @Override
    public List<IVille> getPorts() {
        return ports;
    }
    @Override
    public List<IRoute> getRoutes() {
        return routes;
    }
    @Override
    public List<Joueur> getJoueurs() {
        return joueurs;
    }
    public Joueur getJoueurCourant() {
        return (Joueur) joueurCourant.getValue();
    }
    public PilesCartesTransport getPilesDeCartesWagon() { // ajout pour tester
        return pilesDeCartesWagon;
    }
    public PilesCartesTransport getPilesDeCartesBateau() { // ajout pour tester
        return pilesDeCartesBateau;
    }
    public List<Ville> getPortsLibres() {
        return portsLibres;
    }
    public List<Route> getRoutesLibres() {
        return new ArrayList<>(routesLibres);
    }
    public List<CarteTransport> getCartesTransportVisibles() {
        return new ArrayList<>(cartesTransportVisibles);
    }

    /**
     * Exécute la partie
     */
    public void run() {
        joueurCourant.setValue(joueurs.get(0));
        etatCourantDuJeu = new InitialisationJoueurs(this);
    }

    /**
     * Supprime de la liste des ports disponibles le port dont le nom est passé en
     * argument
     *
     * @return la ville du port supprimé ou null
     */
    public Ville retirerPortLibre(String nom) {
        for (Ville v : portsLibres) {
            if (v.nom().equals(nom)) {
                portsLibres.remove(v);
                return v;
            }
        }
        return null;
    }

    /**
     * Supprime de la liste des routes disponibles la route dont le nom est passé en
     * argument
     *
     * @return la route supprimée ou null
     */
    public Route retirerRouteLibre(String nom) {
        for (Route r : routesLibres) {
            if (r.getNom().equals(nom)) {
                routesLibres.remove(r);
                return r;
            }
        }
        return null;
    }

    /**
     * Retire une carte transport de la liste des cartes visibles.
     */
    public void retirerCarteTransportVisible(CarteTransport c) {
        cartesTransportVisibles.remove(c);
    }

    /**
     * Modifie l'attribut joueurCourant pour passer au joueur suivant dans l'ordre
     * du tableau joueurs
     * (le tableau est considéré circulairement)
     */
    public void passeAuJoueurSuivant() {
        int i = joueurs.indexOf(joueurCourant.getValue());
        i = (i + 1) % joueurs.size();
        joueurCourant.setValue(joueurs.get(i));
    }

    /**
     * Ajoute une carte transport dans la pile de défausse correspondante en
     * fonction de son type
     *
     * @param c carte à défausser
     */
    public void defausserCarteTransport(CarteTransport c) {
        if (c.getType() == TypeCarteTransport.BATEAU) {
            pilesDeCartesBateau.defausser(c);
        } else {
            pilesDeCartesWagon.defausser(c);
        }
    }

    /**
     * Pioche une carte de la pile de pioche des cartes wagon.
     *
     * @return la carte qui a été piochée (ou null si aucune carte disponible)
     */
    public CarteTransport piocherCarteWagon() {
        return pilesDeCartesWagon.piocher();
    }

    /**
     * Révèle (dans la liste des cartes visibles) une carte de la pile de pioche des
     * cartes wagon
     * 
     * @return la carte qui a été retournée (ou null si aucune carte disponible)
     */
    public CarteTransport revelerCarteWagon() {
        CarteTransport c = pilesDeCartesWagon.piocher();
        if (c != null) {
            cartesTransportVisibles.add(c);
            updateCartesTransportVisibles();
        }
        return c;
    }

    /**
     * @return true si les piles de cartes wagon sont vides
     */
    public boolean piocheWagonEstVide() {
        return pilesDeCartesWagon.estVide();
    }

    /**
     * Pioche une carte de la pile de pioche des cartes bateau.
     *
     * @return la carte qui a été piochée (ou null si aucune carte disponible)
     */
    public CarteTransport piocherCarteBateau() {
        return pilesDeCartesBateau.piocher();
    }

    /**
     * Révèle (dans la liste des cartes visibles) une carte de la pile de pioche des
     * cartes bateau
     * 
     * @return la carte qui a été retournée (ou null si aucune carte disponible)
     */
    public CarteTransport revelerCarteBateau() {
        CarteTransport c = pilesDeCartesBateau.piocher();
        if (c != null) {
            cartesTransportVisibles.add(c);
            updateCartesTransportVisibles();
        }
        return c;
    }

    /**
     * @return true si les piles de cartes bateau sont vides
     */
    public boolean piocheBateauEstVide() {
        return pilesDeCartesBateau.estVide();
    }

    /**
     * PilesCartesTransport et renvoie la destination du dessus de la pile de
     * destinations.
     *
     * @return la destination qui a été piochée (ou `null` si aucune destination
     *         disponible)
     */
    public Destination piocherDestination() {
        if (pileDestinations.isEmpty())
            return null;
        return pileDestinations.remove(0);
    }

    /**
     * Replace une liste de destinations à la fin de la pile de destinations
     */
    public void defausserDestination(Destination destination) {
        pileDestinations.add(destination);
    }

    /**
     * Teste si la pile de destinations est vide
     * (pour préserver l'encapsulation du Jeu et de sa pile de destination)
     */
    public boolean pileDestinationsEstVide() {
        return pileDestinations.isEmpty();
    }

    /**
     * Cette méthode redistribue automatiquement les cartes face visible s'il y a au
     * moins 3 jokers face visible (sauf si les cartes disponibles ne permettent pas
     * d'avoir moins de 3 jokers face visible)
     * 
     * Cette méthode est appelée à chaque fois qu'une nouvelle carte transport est
     * retournée face visible, et au début du tour de chaque joueur.
     */
    public void updateCartesTransportVisibles() {
        if (cartesTransportVisibles
                .stream()
                .filter(c -> c.getType() == TypeCarteTransport.JOKER).count() < 3) {
            // il y a < 3 cartes Joker face visible
            // pas besoin de redistribuer les cartes transport visibles
            return;
        }

        int nbCartesWagonSimples = 0;
        int nbCartesBateau = 0;
        List<CarteTransport> cartesTransport = new ArrayList<>();
        cartesTransport.addAll(cartesTransportVisibles);
        cartesTransport.addAll(pilesDeCartesWagon.getCartes());
        cartesTransport.addAll(pilesDeCartesBateau.getCartes());
        for (CarteTransport c : cartesTransport) {
            switch (c.getType()) {
                case WAGON -> nbCartesWagonSimples += 1;
                case BATEAU -> nbCartesBateau += 1;
                default -> {
                }
            }
        }

        if (nbCartesWagonSimples == 0 || nbCartesWagonSimples + nbCartesBateau <= 3) {
            // il n'y a pas assez d'autres cartes pour pouvoir avoir moins de 3 cartes
            // Joker face visible. Ce n'est pas la peine de redistribuer les cartes
            return;
        }

        // défausser les 6 cartes transport visibles
        while (!cartesTransportVisibles.isEmpty()) {
            defausserCarteTransport(cartesTransportVisibles.remove(0));
        }
        // retourner des nouvelles cartes
        remplirCartesTransportVisibles();
    }

    /**
     * Retourne 3 cartes wagon et 3 cartes bateau face visible.
     * Si l'une des deux piles contient moins de 3 cartes, on ajoute des cartes de
     * l'autre pile jusqu'à avoir retourné 6 cartes ou épuisé les deux piles.
     */
    public void remplirCartesTransportVisibles() {
        for (int i = 0; i < 3; i++) {
            CarteTransport c = revelerCarteBateau();
            if (c == null)
                break;
        }
        for (int i = 0; i < 3; i++) {
            CarteTransport c = revelerCarteWagon();
            if (c == null)
                break;
        }

        // Si une pioche est vide, on complète à 6 cartes avec des cartes de l'autre
        // pioche
        while (cartesTransportVisibles.size() < 6 && !pilesDeCartesWagon.estVide()) {
            revelerCarteWagon();
        }
        while (cartesTransportVisibles.size() < 6 && !pilesDeCartesBateau.estVide()) {
            revelerCarteBateau();
        }
        updateCartesTransportVisibles();
    }

    public void initialiserCartesVisibles() {
        ArrayList<CarteTransport> temporaire = new ArrayList<>();
        // Retourner 3 cartes wagon et 3 cartes bateau
        for (int i = 0; i < 3; i++) {
            temporaire.add(piocherCarteWagon());
        }
        for (int i = 0; i < 3; i++) {
            temporaire.add(piocherCarteBateau());
        }
        cartesTransportVisibles.addAll(temporaire);
        updateCartesTransportVisibles();
    }

    public Route trouverRoute(String nom) {
        for (Route r : routesLibres) {
            if (r.getNom().equals(nom)) {
                return r;
            }
        }
        return null;
    }

    public Ville trouverPort(String nom) {
        for (Ville p : portsLibres) {
            if (p.getNom().equals(nom)) {
                return p;
            }
        }
        return null;
    }

    public void calculerScoresFinaux() {
        for (Joueur j : joueurs) {
            j.calculerScoreFinal();
            finDePartie.setValue(true);
        }
    }

    public boolean onPeutEncorePiocher() {
        return !piocheWagonEstVide() || !piocheBateauEstVide();
    }

    public boolean onNePeutPasPrendreDeDeuxiemeCarteVisible() {
        return getCartesTransportVisibles().size() == 0 ||
                ilNeResteQueDesJokers();
    }

    public boolean ilNeResteQueDesJokers() {
        for (CarteTransport carte : getCartesTransportVisibles())
            if (carte.getType() != TypeCarteTransport.JOKER)
                return false;
        return true;
    }

    public void joueurSuivant() {
        passeAuJoueurSuivant();
        remplirPioches();
        instructionProperty().setValue(getEtatCourant().getInfosPhase() + getJoueurCourant().getNom());
        if (leJoueurCourantDoitRevelerCartesTransportVisibles()) {
            getJoueurCourant().revelerCartesVisibles();
        }
        else
            getJoueurCourant().jouerTour();
    }

    public void initialiserJoueurSuivant() {
        getEtatCourant().prochainTour();
        passeAuJoueurSuivant();
        getJoueurCourant().preparation();
    }

    public void remplirPioches() {
        pilesDeCartesWagon.remplirAvecDefausse();
        pilesDeCartesBateau.remplirAvecDefausse();
    }

    public boolean leJoueurCourantDoitRevelerCartesTransportVisibles() {
        int nbCartesVisiblesManquantes = 6 - cartesTransportVisibles.size();
        if (nbCartesVisiblesManquantes > 0) {
            if ((getPilesDeCartesWagon().getPilePioche().size() + getPilesDeCartesBateau().getPilePioche().size()) > nbCartesVisiblesManquantes) {
                if (!getPilesDeCartesBateau().estVide() && !getPilesDeCartesWagon().estVide()) {
                    return true;
                }
            }
            while (!getPilesDeCartesWagon().estVide() && cartesTransportVisibles.size() != 6) {
                revelerCarteWagon();
            }
            while (!getPilesDeCartesBateau().estVide() && cartesTransportVisibles.size() != 6) {
                revelerCarteBateau();
            }
        }
        return false;
    }


    /**
     * Gestion des phases (états) du jeu
     */
    private EtatJeu etatCourantDuJeu;

    public void setEtatCourant(EtatJeu etatCourantDuJeu) {
        this.etatCourantDuJeu = etatCourantDuJeu;
    }

    public EtatJeu getEtatCourant() {
        return etatCourantDuJeu;
    }

    @Override
    public void passerAEteChoisi() {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().passer();
    }

    @Override
    public void uneCarteWagonAEtePiochee() {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().piocherWagon();
    }

    @Override
    public void uneCarteBateauAEtePiochee() {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().piocherBateau();
    }

    @Override
    public void nouvelleDestinationDemandee() {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().prendreDestinations();
    }

    @Override
    public void uneDestinationAEteChoisie(IDestination destination) {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().defausseDestination((Destination) destination);
    }

    @Override
    public void nouveauxPionsWagonsDemandes() {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().prendrePionsWagon();
    }

    @Override
    public void nouveauxPionsBateauxDemandes() {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().prendrePionsBateau();
    }

    @Override
    public void leNombreDePionsSouhaiteAEteRenseigne(String nbPionsChoisis) {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().mettreAJourPions(nbPionsChoisis);
    }

    @Override
    public void uneCarteTransportAEteChoisie(ICarteTransport carte) {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().prendreCarte((CarteTransport) carte);
    }

    @Override
    public void uneCarteDuJoueurEstJouee(ICarteTransport carte) {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().jouerCarte((CarteTransport) carte);
    }

    @Override
    public void uneRouteAEteChoisie(String nom) {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().prendreRoute(nom);
    }

    @Override
    public void unPortAEteChoisi(String nom) {
        Joueur leJoueur = ((Joueur) joueurCourant.getValue());
        leJoueur.getEtatCourant().construirePort(nom);
    }

}
