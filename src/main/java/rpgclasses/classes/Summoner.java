package rpgclasses.classes;

import rpgclasses.base.AbilityRequirements;
import rpgclasses.base.RPGClass;

public class Summoner extends RPGClass {

    public Summoner() {
        super("summoner");

        this.addPrincipalAbility("necromancer", new AbilityRequirements());

        this.addAbilityLevelSet("abundantarmy", 6, new AbilityRequirements());

        this.addAbilityLevelSet("leadership", 12, new AbilityRequirements());

        this.addAbility("meleeskeleton", 0, new AbilityRequirements().setAffinity(5).setAbilityRequired("necromancer_0"));
        this.addAbility("meleeskeleton", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("meleeskeleton", 2, new AbilityRequirements().setAffinity(12).setLockedBy("rangeskeleton_2"));

        this.addAbility("rangeskeleton", 0, new AbilityRequirements().setAffinity(5).setAbilityRequired("necromancer_0"));
        this.addAbility("rangeskeleton", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("rangeskeleton", 2, new AbilityRequirements().setAffinity(12));

    }
}