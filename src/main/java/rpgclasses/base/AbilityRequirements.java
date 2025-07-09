package rpgclasses.base;

import java.util.ArrayList;
import java.util.Arrays;

public class AbilityRequirements {
    public String abilityRequired;
    public ArrayList<String> lockedBy = new ArrayList<>();
    public int affinity;

    public AbilityRequirements() {
    }

    public AbilityRequirements setAbilityRequired(String ability) {
        this.abilityRequired = ability;
        return this;
    }

    public AbilityRequirements setLockedBy(String... lockedBy) {
        this.lockedBy = new ArrayList<>(Arrays.asList(lockedBy));
        return this;
    }

    public AbilityRequirements addLockedBy(String lockedBy) {
        this.lockedBy.add(lockedBy);
        return this;
    }

    public AbilityRequirements setAffinity(int affinity) {
        this.affinity = affinity;
        return this;
    }
}
