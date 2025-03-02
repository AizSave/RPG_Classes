package rpgclasses.base;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.registry.AbilityRegistry;
import rpgclasses.registry.RPGClassRegistry;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ClassAbility {
    public Ability ability;
    public final int abilityLevel;
    public final AbilityRequirements abilityRequirements;
    public final RPGClass rpgClass;

    public ClassAbility(String stringID, int abilityLevel, AbilityRequirements abilityRequirements, RPGClass rpgClass) {
        this.abilityLevel = abilityLevel;
        this.abilityRequirements = abilityRequirements;
        this.rpgClass = rpgClass;
        this.ability = AbilityRegistry.getAbility(stringID);
    }

    public String canUnlock(PlayerMob player, PlayerData playerData) {
        if (playerData.classAbilitiesStringIDs.contains(this.getStringID())) {
            return "alreadyunlocked";
        }
        boolean isPrincipal = rpgClass.principalAbilities.contains(getStringID());
        boolean havePrincipal = playerData.haveAtLeastOneAbility(rpgClass.principalAbilities);
        if (!isPrincipal && !havePrincipal) {
            return "needsprincipalability";
        }
        if (isPrincipal && havePrincipal) {
            return "alreadyprincipalability";
        }
        if (abilityLevel != 0 && !playerData.classAbilitiesStringIDs.contains(getAntLevelStringID())) {
            return "needsanteriorlevel";
        }
        if (abilityRequirements.abilityRequired != null && !playerData.classAbilitiesStringIDs.contains(abilityRequirements.abilityRequired)) {
            return "needsrequiredability";
        }
        if (playerData.haveAtLeastOneAbility(abilityRequirements.lockedBy)) {
            return "lockedbyanotherability";
        }
        if (rpgClass.getAffinity(player) < abilityRequirements.affinity) {
            return "needsmoreaffinity";
        }
        if (playerData.unassignedTalentPoints() < ability.talentPoints[abilityLevel]) {
            return "needsmoretalentpoints";
        }
        return null;
    }

    public boolean isLocked(PlayerData playerData) {
        if (playerData.haveAtLeastOneAbility(abilityRequirements.lockedBy)) {
            return true;
        }
        boolean isPrincipal = rpgClass.principalAbilities.contains(getStringID());
        boolean havePrincipal = playerData.haveAtLeastOneAbility(rpgClass.principalAbilities);
        if (isPrincipal && havePrincipal) {
            return true;
        }
        if (abilityRequirements.abilityRequired != null && !playerData.classAbilitiesStringIDs.contains(abilityRequirements.abilityRequired)) {
            ClassAbility abilityRequired = rpgClass.classAbilities.stream().filter(ca -> Objects.equals(ca.getStringID(), abilityRequirements.abilityRequired)).findFirst().orElse(null);
            if (abilityRequired != null && abilityRequired.isLocked(playerData)) return true;
        }
        if (abilityLevel != 0) {
            ClassAbility antLevel = rpgClass.classAbilities.stream().filter(ca -> Objects.equals(ca.ability.stringID, ability.stringID) && ca.abilityLevel == abilityLevel - 1).findFirst().orElse(null);
            if (antLevel != null) return antLevel.isLocked(playerData);
        }
        return false;
    }

    public Set<String> canUnlockAdvanced(PlayerMob player, PlayerData playerData) {
        Set<String> list = new HashSet<>();
        if (playerData.classAbilitiesStringIDs.contains(this.getStringID())) {
            list.add("alreadyunlocked");
        }
        boolean isPrincipal = rpgClass.principalAbilities.contains(getStringID());
        boolean havePrincipal = playerData.haveAtLeastOneAbility(rpgClass.principalAbilities);
        if (!isPrincipal && !havePrincipal) {
            list.add("needsprincipalability");
        }
        if (isPrincipal && havePrincipal) {
            list.add("alreadyprincipalability");
        }
        if (abilityLevel != 0 && !playerData.classAbilitiesStringIDs.contains(getAntLevelStringID())) {
            list.add("needsanteriorlevel");
        }
        if (abilityRequirements.abilityRequired != null && !playerData.classAbilitiesStringIDs.contains(abilityRequirements.abilityRequired)) {
            list.add("needsrequiredability");
        }
        if (playerData.haveAtLeastOneAbility(abilityRequirements.lockedBy)) {
            list.add("lockedbyanotherability");
        }
        if (rpgClass.getAffinity(player) < abilityRequirements.affinity) {
            list.add("needsmoreaffinity");
        }
        if (playerData.unassignedTalentPoints() < ability.talentPoints[abilityLevel]) {
            list.add("needsmoretalentpoints");
        }
        return list;
    }

    public String canUnlock(PlayerMob player) {
        return canUnlock(player, PlayerDataList.getCurrentPlayer(player));
    }

    public String getStringID() {
        return ability.stringID + "_" + abilityLevel;
    }

    public String getAntLevelStringID() {
        return ability.stringID + "_" + (abilityLevel - 1);
    }

    public ListGameTooltips getToolTips(PlayerMob player) {
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        boolean unlocked = playerData.classAbilitiesStringIDs.contains(this.getStringID());
        Set<String> canUnlockAdvanced = unlocked ? new HashSet<>() : canUnlockAdvanced(player, playerData);
        boolean canUnlock = !unlocked && canUnlockAdvanced.isEmpty();

        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("abilitydescription", "name", "ability", ability.getDisplayName(), "level", abilityLevel + 1));

        tooltips.add("\n");
        tooltips.add(Localization.translate("abilitydescription", getStringID()));
        tooltips.add("\n");

        if (canUnlockAdvanced.contains("alreadyprincipalability")) {
            tooltips.add(Localization.translate("abilitydescription", "alreadyprincipalability"));
        }
        if (canUnlockAdvanced.contains("needsprincipalability")) {
            tooltips.add(Localization.translate("abilitydescription", "principalabilityreq"));
        }
        if (canUnlockAdvanced.contains("needsanteriorlevel")) {
            tooltips.add(Localization.translate("abilitydescription", "abilityantreq"));
        }
        tooltips.add((unlocked ? "" : (canUnlockAdvanced.contains("needsmoretalentpoints") ? "§6" : "§7")) +
                Localization.translate("abilitydescription", "pointtalentreq", "points", ability.talentPoints[abilityLevel])
        );
        if (abilityRequirements.affinity > 0) {
            tooltips.add((unlocked ? "" : (canUnlockAdvanced.contains("needsmoreaffinity") ? "§6" : "§7")) +
                    Localization.translate("abilitydescription", "affinityreq", "affinity", abilityRequirements.affinity)
            );
        }
        if (abilityRequirements.abilityRequired != null) {
            tooltips.add((unlocked ? "" : (canUnlockAdvanced.contains("needsrequiredability") ? "§6" : "§7")) +
                    Localization.translate("abilitydescription", "abilityreq", "ability", Objects.requireNonNull(AbilityRegistry.getAbility(abilityRequirements.abilityRequired.split("_")[0])).getDisplayName())
            );
        }
        if (!abilityRequirements.lockedBy.isEmpty()) {
            if (abilityRequirements.lockedBy.size() == 1) {
                String[] abilitySplit = abilityRequirements.lockedBy.get(0).split("_");
                tooltips.add((unlocked ? "" : (canUnlockAdvanced.contains("lockedbyanotherability") ? "§6" : "§7")) +
                        Localization.translate("abilitydescription", "lockedbyanotherabilitysingle", "ability",
                                Localization.translate("classescontainer", "abilityname", "ability", Objects.requireNonNull(AbilityRegistry.getAbility(abilitySplit[0])).getDisplayName(), "level", Integer.parseInt(abilitySplit[1]) + 1)
                        )
                );
            } else {
                tooltips.add((unlocked ? "" : (canUnlockAdvanced.contains("lockedbyanotherability") ? "§6" : "§7")) +
                        Localization.translate("abilitydescription", "lockedbyanotherabilitylist")
                );
                for (String lockedByStringID : abilityRequirements.lockedBy) {
                    String[] abilitySplit = lockedByStringID.split("_");
                    tooltips.add((unlocked || !playerData.classAbilitiesStringIDs.contains(lockedByStringID) ? "" : "§6")
                            + "- " + Localization.translate("classescontainer", "abilityname", "ability", Objects.requireNonNull(AbilityRegistry.getAbility(abilitySplit[0])).getDisplayName(), "level", Integer.parseInt(abilitySplit[1]) + 1)
                    );
                }

            }
        }

        if (canUnlock) {
            tooltips.add("\n");
            tooltips.add(Localization.translate("abilitydescription", "clicktounlock"));
        }

        return tooltips;
    }

    public int getTalentPoints() {
        return this.ability.talentPoints[this.abilityLevel];
    }

    public String getDisplayName() {
        return Localization.translate("classescontainer", "abilityname", "ability", ability.getDisplayName(), "level", abilityLevel + 1);
    }

    public void onEndRegistry() {
        for (RPGClass rpgClass : RPGClassRegistry.RPGClasses) {
            for (ClassAbility classAbility : rpgClass.classAbilities) {
                String abilityID = classAbility.getStringID();
                if (!isLockedBy(abilityID) && classAbility.isLockedBy(this.getStringID())) {
                    this.abilityRequirements.addLockedBy(abilityID);
                }
            }
        }
    }

    public boolean isLockedBy(String abilityID) {
        return this.abilityRequirements.lockedBy.contains(abilityID);
    }
}
