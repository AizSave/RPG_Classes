package rpgclasses.base;

import necesse.engine.localization.Localization;

public class Ability {
    public final String stringID;
    public final int[] talentPoints;
    public final int abilityLevels;

    public Ability(String stringID, int abilityLevels, int... talentPoints) {
        this.stringID = stringID;
        this.talentPoints = talentPoints;
        this.abilityLevels = abilityLevels;

        if (talentPoints.length != abilityLevels) {
            throw new RuntimeException("TalentPoints length must be equal to abilityLevels");
        }
    }

    public Ability(String stringID, int abilityLevels, int talentPoints) {
        this.stringID = stringID;
        this.abilityLevels = abilityLevels;

        this.talentPoints = new int[abilityLevels];
        for (int i = 0; i < abilityLevels; i++) {
            this.talentPoints[i] = talentPoints;
        }
    }

    public String getDisplayName() {
        return Localization.translate("rpgclass", stringID);
    }
}
