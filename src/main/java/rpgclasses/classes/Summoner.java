package rpgclasses.classes;

import rpgclasses.base.AbilityRequirements;
import rpgclasses.base.RPGClass;

public class Summoner extends RPGClass {

    public Summoner() {
        super("summoner");

        this.addPrincipalAbility("summoner", new AbilityRequirements());

        this.addAbilityLevelSet("abundantarmy", 6, new AbilityRequirements());

        this.addAbilityLevelSet("leadership", 12, new AbilityRequirements());

    }
}