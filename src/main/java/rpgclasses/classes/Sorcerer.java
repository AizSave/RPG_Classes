package rpgclasses.classes;

import rpgclasses.base.AbilityRequirements;
import rpgclasses.base.RPGClass;

public class Sorcerer extends RPGClass {
    public Sorcerer() {
        super("sorcerer");

        this.addPrincipalAbility("apprentice", new AbilityRequirements());
        this.addPrincipalAbility("wizard", new AbilityRequirements());

        this.addAbility("firewand", 0, new AbilityRequirements().setLockedBy("icewand_0", "thunderwand_0"));
        this.addAbility("firewand", 1, new AbilityRequirements().setAffinity(5));
        this.addAbility("firewand", 2, new AbilityRequirements().setAffinity(8));
        this.addAbility("firewand", 3, new AbilityRequirements().setAffinity(12));

        this.addAbility("icewand", 0, new AbilityRequirements().setLockedBy("thunderwand_0"));
        this.addAbility("icewand", 1, new AbilityRequirements().setAffinity(5));
        this.addAbility("icewand", 2, new AbilityRequirements().setAffinity(8));
        this.addAbility("icewand", 3, new AbilityRequirements().setAffinity(12));

        this.addAbility("thunderwand", 0, new AbilityRequirements());
        this.addAbility("thunderwand", 1, new AbilityRequirements().setAffinity(5));
        this.addAbility("thunderwand", 2, new AbilityRequirements().setAffinity(8));
        this.addAbility("thunderwand", 3, new AbilityRequirements().setAffinity(12));

        this.addAbilityLevelSet("manaspring", 6, new AbilityRequirements().setAffinity(4));
        this.addAbilityLevelSet("arcanepower", 6, new AbilityRequirements().setAffinity(4));
        this.addAbilityLevelSet("arcaneprecision", 6, new AbilityRequirements().setAffinity(4));

        this.addAbility("overcharge", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("overcharge", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("overcharge", 2, new AbilityRequirements().setAffinity(12));

        this.addAbility("firereaction", 0, new AbilityRequirements().setAffinity(8).setLockedBy("icereaction_0", "thunderreaction_0"));
        this.addAbility("firereaction", 1, new AbilityRequirements().setAffinity(12));
        this.addAbility("firereaction", 2, new AbilityRequirements().setAffinity(15));

        this.addAbility("icereaction", 0, new AbilityRequirements().setAffinity(8).setLockedBy("thunderreaction_0"));
        this.addAbility("icereaction", 1, new AbilityRequirements().setAffinity(12));
        this.addAbility("icereaction", 2, new AbilityRequirements().setAffinity(15));

        this.addAbility("thunderreaction", 0, new AbilityRequirements().setAffinity(8));
        this.addAbility("thunderreaction", 1, new AbilityRequirements().setAffinity(12));
        this.addAbility("thunderreaction", 2, new AbilityRequirements().setAffinity(15));
    }

}

