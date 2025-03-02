package rpgclasses.classes;

import rpgclasses.base.AbilityRequirements;
import rpgclasses.base.RPGClass;

public class Ranger extends RPGClass {

    public Ranger() {
        super("ranger");

        this.addPrincipalAbility("sniper", new AbilityRequirements());
        this.addPrincipalAbility("beastmaster", new AbilityRequirements());

        this.addAbilityLevelSet("accuracy", 6, new AbilityRequirements());
        this.addAbilityLevelSet("precision", 6, new AbilityRequirements());
        this.addAbilityLevelSet("quickhands", 6, new AbilityRequirements().setAbilityRequired("sniper_0"));
        this.addAbilityLevelSet("huntersmark", 6, new AbilityRequirements().setAbilityRequired("sniper_0").setAffinity(5));
        this.addAbilityLevelSet("swiftness", 6, new AbilityRequirements().setAbilityRequired("beastmaster_0"));
        this.addAbilityLevelSet("leadership", 6, new AbilityRequirements().setAbilityRequired("beastmaster_0").setAffinity(5));

        this.addAbility("magicranger", 0, new AbilityRequirements().setAffinity(5));
        this.addAbility("magicranger", 1, new AbilityRequirements().setAffinity(8));
        this.addAbility("magicranger", 2, new AbilityRequirements().setAffinity(12));

        this.addAbility("plasmagrenade", 0, new AbilityRequirements().setAffinity(8));
        this.addAbility("plasmagrenade", 1, new AbilityRequirements().setAffinity(12));
        this.addAbility("plasmagrenade", 2, new AbilityRequirements().setAffinity(15).setAbilityRequired("sniper_0"));

        this.addAbility("bowmastery", 0, new AbilityRequirements().setAffinity(8));
        this.addAbility("bowmastery", 1, new AbilityRequirements().setAffinity(12));
        this.addAbility("bowmastery", 2, new AbilityRequirements().setAffinity(15).setAbilityRequired("beastmaster_0"));
    }
}
