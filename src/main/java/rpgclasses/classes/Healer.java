package rpgclasses.classes;

import rpgclasses.base.AbilityRequirements;
import rpgclasses.base.RPGClass;

public class Healer extends RPGClass {
    public Healer() {
        super("healer");

        this.addPrincipalAbility("priest", new AbilityRequirements());
        this.addPrincipalAbility("paladin", new AbilityRequirements());

        this.addAbilityLevelSet("healing", 12, new AbilityRequirements());
        this.addAbilityLevelSet("manaspring", 6, new AbilityRequirements());
        this.addAbilityLevelSet("graceful", 6, new AbilityRequirements().setLockedBy("paladin_0"));
        this.addAbilityLevelSet("fortitude", 6, new AbilityRequirements().setAbilityRequired("paladin_0"));

        this.addAbility("healingarea", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("healingarea", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("healingarea", 2, new AbilityRequirements().setAffinity(12));
        this.addAbility("healingarea", 3, new AbilityRequirements().setAffinity(15));

        this.addAbility("sacredjudgment", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("sacredjudgment", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("sacredjudgment", 2, new AbilityRequirements().setAffinity(12));

    }
}
