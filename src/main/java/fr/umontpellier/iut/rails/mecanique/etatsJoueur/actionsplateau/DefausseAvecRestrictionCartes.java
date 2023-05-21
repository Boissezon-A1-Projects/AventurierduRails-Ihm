package fr.umontpellier.iut.rails.mecanique.etatsJoueur.actionsplateau;

import fr.umontpellier.iut.rails.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;

import java.util.ArrayList;
import java.util.List;

public abstract class DefausseAvecRestrictionCartes extends EtatJoueur {
    protected List<String> optionsChoix;

    public DefausseAvecRestrictionCartes(Joueur joueurCourant, List<String> optionsChoix) {
        super(joueurCourant);
        this.optionsChoix = new ArrayList<>();
        this.optionsChoix.addAll(optionsChoix);
        prochainEtat = this;
    }

}
