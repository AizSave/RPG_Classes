package rpgclasses.data;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.server.ServerClient;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import rpgclasses.Config;
import rpgclasses.base.Ability;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.buffs.SummonsNerfBuff;
import rpgclasses.packets.ShowModExpPacket;
import rpgclasses.registry.AbilityRegistry;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerData {
    public final String playerName;
    private int exp;
    public List<String> classAbilitiesStringIDs;
    public List<String> classActiveAbilitiesStringIDs;

    public PlayerData(String playerName) {
        this.playerName = playerName;
        this.classAbilitiesStringIDs = new ArrayList<>();
        this.classActiveAbilitiesStringIDs = new ArrayList<>();
    }

    public void loadData(LoadData loadData) {
        loadData(
                loadData.getInt("exp", 0),
                loadData.getStringArray("abilities", new String[0])
        );
    }

    public void loadData(int exp, String[] abilities) {
        loadData(exp);
        loadData(abilities);
    }

    public void loadData(int exp) {
        this.exp = exp;
    }

    public void loadData(String[] abilities) {
        this.classAbilitiesStringIDs.clear();
        classAbilitiesStringIDs.addAll(Arrays.stream(abilities)
                .filter(abilityStringID -> {
                    if (abilityStringID == null || abilityStringID.isEmpty()) {
                        return false;
                    }
                    String[] abilityName = abilityStringID.split("_");
                    if (abilityName.length != 2 || abilityName[0].isEmpty() || abilityName[1].isEmpty()) {
                        return false;
                    }
                    try {
                        int abilityLevel = Integer.parseInt(abilityName[1]);
                        return abilityLevel >= 0 && AbilityRegistry.getAbility(abilityName[0]).abilityLevels >= abilityLevel;
                    } catch (RuntimeException ignore) {
                        return false;
                    }
                })
                .collect(Collectors.toList()));

        classActiveAbilitiesStringIDs.clear();
        getHighestLevelAbilities().forEach((key, value) -> classActiveAbilitiesStringIDs.add(key + "_" + value));
    }

    public void saveData(SaveData saveData) {
        saveData.addInt("exp", exp);
        saveData.addStringArray("abilities", classAbilitiesStringIDs.toArray(new String[0]));
    }

    public int totalTalentPoints() {
        return getLevel() + 3;
    }


    public int assignedTalentPoints() {
        int talentPoints = 0;
        for (String s : classAbilitiesStringIDs) {
            try {
                String abilityStringID = s.split("_")[0];
                int abilityLevel = Integer.parseInt(s.split("_")[1]);
                Ability ability = AbilityRegistry.getAbility(abilityStringID);
                talentPoints += ability.talentPoints[abilityLevel];
            } catch (RuntimeException ignored) {

            }
        }
        return talentPoints;
    }

    public int unassignedTalentPoints() {
        return totalTalentPoints() - assignedTalentPoints();
    }

    public int getHighestLevelAbility(String abilityText) {
        int maxLevel = -1;

        for (String s : classAbilitiesStringIDs) {
            int underscoreIndex = s.lastIndexOf('_');
            if (underscoreIndex == -1) continue;

            String abilityStringID = s.substring(0, underscoreIndex);
            if (!abilityStringID.equals(abilityText)) continue;

            try {
                int abilityLevel = Integer.parseInt(s.substring(underscoreIndex + 1));
                if (abilityLevel > maxLevel) {
                    maxLevel = abilityLevel;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        return maxLevel;
    }

    public Map<String, Integer> getHighestLevelAbilities() {
        Map<String, Integer> maxLevels = new HashMap<>();

        for (String s : classAbilitiesStringIDs) {
            int underscoreIndex = s.lastIndexOf('_');
            if (underscoreIndex == -1) continue;

            String abilityStringID = s.substring(0, underscoreIndex);
            try {
                int abilityLevel = Integer.parseInt(s.substring(underscoreIndex + 1));
                if (!maxLevels.containsKey(abilityStringID) || abilityLevel > maxLevels.get(abilityStringID)) {
                    maxLevels.put(abilityStringID, abilityLevel);
                }
            } catch (NumberFormatException ignored) {
            }
        }

        return maxLevels;
    }

    public int getBaseExp() {
        return this.exp;
    }

    public int getExp() {
        return this.exp + Config.getConfig().getStartingExperience();
    }

    public int getExpActual() {
        return this.getExp() - getExpRequiredForLevel(this.getLevel());
    }

    public int getExpNext() {
        return getExpRequiredForLevel(this.getLevel() + 1) - getExpRequiredForLevel(getLevel());
    }

    public int getExpRequiredForLevel(int level, Config config) {
        return level * config.getFirstExperienceReq() + config.getExperienceReqInc() * (level * (level - 1)) / 2 + config.getSquareExperienceReqInc() * (level * (level - 1) * (2 * level - 1)) / 6 + config.getCubeExperienceReqInc() * (int) Math.pow((double) (level * (level - 1)) / 2, 2);
    }


    private int getExpRequiredForLevel(int level) {
        return getExpRequiredForLevel(level, Config.getConfig());
    }

    public int getLevel() {
        int level = 0;
        Config config = Config.getConfig();
        while (getExpRequiredForLevel(level + 1, config) <= this.getExp()) {
            level++;
        }
        return level;
    }

    public void updateActiveBuffs() {
        this.classActiveAbilitiesStringIDs.clear();
        this.getHighestLevelAbilities().forEach((key, value) -> this.classActiveAbilitiesStringIDs.add(key + "_" + value));
    }

    public void updateBuffs(PlayerMob player) {
        updateBuffs(player, true);
    }

    public void updateBuffs(PlayerMob player, boolean sendUpdatePacket) {
        if (player.isServer() || !sendUpdatePacket) {
            player.buffManager.addBuff(new ActiveBuff("dodgebuff", player, 1000, null), true);

            for (Ability ability : AbilityRegistry.abilities) {
                for (int level = 0; level < ability.abilityLevels; level++) {
                    String stringID = ability.stringID + "_" + level;
                    boolean hasBuff = player.buffManager.hasBuff(stringID);
                    boolean shouldHave = classActiveAbilitiesStringIDs.contains(stringID);
                    if (hasBuff != shouldHave) {
                        if (hasBuff) {
                            player.buffManager.removeBuff(stringID, true);
                        } else {
                            ActiveBuff providedBuff = new ActiveBuff(stringID, player, 1000, null);
                            player.buffManager.addBuff(providedBuff, true);
                            if (providedBuff.buff instanceof SimpleClassBuff)
                                ((SimpleClassBuff) providedBuff.buff).onLoad(providedBuff);
                        }
                    }
                }
            }

            if (haveAtLeastOneAbility(SummonsNerfBuff.evadeNerfSummonsBuffs)) {
                if (player.buffManager.hasBuff(SummonsNerfBuff.stringID)) {
                    player.buffManager.removeBuff(SummonsNerfBuff.stringID, true);
                }
            } else {
                player.buffManager.addBuff(new ActiveBuff(SummonsNerfBuff.stringID, player, 1000, null), true);
            }
        }
    }

    public boolean haveAtLeastOneAbility(List<String> abilities) {
        for (String abilityStringID : classAbilitiesStringIDs) {
            if (abilities.contains(abilityStringID)) {
                return true;
            }
        }
        return false;
    }

    public void modExp(ServerClient serverClient, int amount) {
        int antLevel = this.getLevel();

        this.exp += amount;

        boolean levelUp = antLevel < this.getLevel();
        while (antLevel < this.getLevel()) {
            antLevel++;
            serverClient.sendChatMessage(new LocalMessage("classmessage", "newlevel", "level", antLevel));
        }

        serverClient.getServer().network.sendToAllClients(new ShowModExpPacket(serverClient.playerMob.getX(), serverClient.playerMob.getY(), amount, levelUp));
    }

}