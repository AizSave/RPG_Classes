package rpgclasses.classes;

import rpgclasses.base.AbilityRequirements;
import rpgclasses.base.RPGClass;

public class Fighter extends RPGClass {

    public Fighter() {
        super("fighter");

        this.addPrincipalAbility("warrior", new AbilityRequirements());
        this.addPrincipalAbility("barbarian", new AbilityRequirements());

        this.addAbilityLevelSet("endurance", 6);
        this.addAbilityLevelSet("fortitude", 6, new AbilityRequirements().setLockedBy("barbarian_0"));
        this.addAbilityLevelSet("swiftness", 6, new AbilityRequirements().setAbilityRequired("barbarian_0"));
        this.addAbilityLevelSet("strength", 6);
        this.addAbilityLevelSet("recovery", 6);

        this.addAbility("ironwill", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("ironwill", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("ironwill", 2, new AbilityRequirements().setAffinity(12).setLockedBy("armoredflesh_2", "frenziedguard_2", "bloodhaste_2"));

        this.addAbility("armoredflesh", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("armoredflesh", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("armoredflesh", 2, new AbilityRequirements().setAffinity(12).setLockedBy("frenziedguard_2", "bloodhaste_2"));

        this.addAbility("frenziedguard", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("frenziedguard", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("frenziedguard", 2, new AbilityRequirements().setAffinity(12).setLockedBy("bloodhaste_2"));

        this.addAbility("bloodhaste", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("bloodhaste", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("bloodhaste", 2, new AbilityRequirements().setAffinity(12));
    }
}

