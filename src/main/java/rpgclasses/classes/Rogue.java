package rpgclasses.classes;

import rpgclasses.base.AbilityRequirements;
import rpgclasses.base.RPGClass;

public class Rogue extends RPGClass {
    public Rogue() {
        super("rogue");

        this.addPrincipalAbility("assassin", new AbilityRequirements());
        this.addPrincipalAbility("shadow", new AbilityRequirements());

        this.addAbilityLevelSet("lethality", 4);
        this.addAbilityLevelSet("lethality", 5, 6, new AbilityRequirements().setAbilityRequired("assassin_0"));
        this.addAbilityLevelSet("swiftness", 6);
        this.addAbilityLevelSet("quickhands", 6);
        this.addAbilityLevelSet("stealth", 4);
        this.addAbilityLevelSet("stealth", 5, 6, new AbilityRequirements().setAbilityRequired("shadow_0"));

        this.addAbility("relentlessstrikes", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("relentlessstrikes", 1, new AbilityRequirements().setAffinity(8).setLockedBy("relentlesschase_1", "relentlessevasion_1"));
        this.addAbility("relentlessstrikes", 2, new AbilityRequirements().setAffinity(12));

        this.addAbility("relentlesschase", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("relentlesschase", 1, new AbilityRequirements().setAffinity(8).setLockedBy("relentlessevasion_1"));
        this.addAbility("relentlesschase", 2, new AbilityRequirements().setAffinity(12));

        this.addAbility("relentlessevasion", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("relentlessevasion", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("relentlessevasion", 2, new AbilityRequirements().setAffinity(12));

        this.addAbility("criticaledge", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("criticaledge", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("criticaledge", 2, new AbilityRequirements().setAffinity(12).setLockedBy("relentlessstrikes_2").setLockedBy("relentlesschase_2").setLockedBy("relentlessevasion_2"));

        this.addAbility("evasiveinstinct", 0, new AbilityRequirements().setAffinity(8).setLockedBy("evasivelethality_0"));
        this.addAbility("evasiveinstinct", 1, new AbilityRequirements().setAffinity(12));
        this.addAbility("evasiveinstinct", 2, new AbilityRequirements().setAffinity(16));

        this.addAbility("evasivelethality", 0, new AbilityRequirements().setAffinity(8));
        this.addAbility("evasivelethality", 1, new AbilityRequirements().setAffinity(12));
        this.addAbility("evasivelethality", 2, new AbilityRequirements().setAffinity(16));
    }
}