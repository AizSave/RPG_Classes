package rpgclasses.base;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTexture.GameTexture;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class RPGClass {
    public final String stringID;
    public final List<String> principalAbilities = new ArrayList<>();
    public final List<ClassAbility> classAbilities = new ArrayList<>();

    public Map<String, GameTexture> icons = new HashMap<>();

    public void loadResources() {
        icons.clear();
        for (String principalAbility : principalAbilities) {
            String id = principalAbility.replace("_0", "");
            icons.put(id, GameTexture.fromFile("classes/" + id));
        }
    }

    public RPGClass(String stringID) {
        this.stringID = stringID;
    }

    public String getDisplayName() {
        return Localization.translate("rpgclass", stringID);
    }

    public int getAffinity(PlayerMob playerMob) {
        int talentPoints = 0;
        List<ClassAbility> unlockedAbilities = getUnlockedAbilities(playerMob);
        for (ClassAbility classAbility : unlockedAbilities) {
            talentPoints += classAbility.getTalentPoints();
        }
        return talentPoints;
    }

    public List<ClassAbility> getUnlockedAbilities(PlayerMob player) {
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        return classAbilities.stream()
                .filter(classAbility -> playerData.classAbilitiesStringIDs.contains(classAbility.getStringID()))
                .collect(Collectors.toList());
    }

    public void addAbility(ClassAbility classAbility) {
        classAbilities.add(classAbility);
    }

    public void addPrincipalAbility(String stringID, AbilityRequirements abilityRequirements) {
        ClassAbility classAbility = new ClassAbility(stringID, 0, abilityRequirements, this);
        principalAbilities.add(stringID + "_0");
        classAbilities.add(classAbility);
    }

    public void addAbility(String stringID, int abilityLevel, AbilityRequirements abilityRequirements) {
        classAbilities.add(new ClassAbility(stringID, abilityLevel, abilityRequirements, this));
    }

    public ClassAbility getAbility(String stringID) {
        return classAbilities.stream()
                .filter(classAbility -> classAbility.getStringID().equals(stringID))
                .findFirst()
                .orElse(null);
    }

    public ClassAbility getAbility(String stringID, int abilityLevel) {
        return getAbility(stringID + "_" + abilityLevel);
    }

    public void addAbilityLevelSet(String stringID, int firstAbilityLevel, int lastAbilityLevel, AbilityRequirements firstAbilityRequirements) {
        if (lastAbilityLevel <= firstAbilityLevel) {
            throw new RuntimeException("lastAbilityLevel must be higher than firstAbilityLevel");
        }
        addAbility(stringID, firstAbilityLevel - 1, firstAbilityRequirements);
        for (int i = firstAbilityLevel; i < lastAbilityLevel; i++) {
            addAbility(stringID, i, new AbilityRequirements());
        }
    }


    public void addAbilityLevelSet(String stringID, int abilityLevels, AbilityRequirements firstAbilityRequirements) {
        if (abilityLevels <= 0) {
            throw new RuntimeException("abilityLevels must be higher than 0");
        }
        addAbility(stringID, 0, firstAbilityRequirements);
        for (int i = 1; i < abilityLevels; i++) {
            addAbility(stringID, i, new AbilityRequirements());
        }
    }

    public void addAbilityLevelSet(String stringID, int abilityLevels) {
        addAbilityLevelSet(stringID, abilityLevels, new AbilityRequirements());
    }
}
