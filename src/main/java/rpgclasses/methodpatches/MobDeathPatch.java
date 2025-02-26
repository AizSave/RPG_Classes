package rpgclasses.methodpatches;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BiomeRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;
import rpgclasses.Config;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.packets.ShowModExpPacket;

import java.util.HashSet;
import java.util.Objects;

@ModMethodPatch(target = Mob.class, name = "onDeath", arguments = {Attacker.class, HashSet.class})
public class MobDeathPatch {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.This Mob mob, @Advice.Argument(0) Attacker attacker, @Advice.Argument(1) HashSet<Attacker> attackers) {
        if (mob.isServer() && mob.getLevel() != null && mob.isHostile && !mob.isSummoned) {
            Level level = mob.getLevel();
            HashSet<PlayerMob> processedPlayers = new HashSet<>();

            for (Attacker attackerMob : attackers) {
                if (attackerMob.getAttackOwner() != null && attackerMob.getAttackOwner().isPlayer) {
                    PlayerMob player = (PlayerMob) attackerMob.getAttackOwner();

                    if (processedPlayers.contains(player)) {
                        continue;
                    }

                    processedPlayers.add(player);

                    ServerClient serverClient = player.getServerClient();
                    float exp = (float) mob.getMaxHealth() / 20 * GameRandom.globalRandom.getFloatBetween(0.9F, 1.1F) * Config.getConfig().getExperienceMod();
                    if (mob.getLevel().getIslandDimension() == -2) {
                        exp *= 4;
                    } else if (mob.getLevel().getIslandDimension() == -1) {
                        exp *= 1.5F;
                    }

                    if (!mob.isBoss()) {
                        String biomeID = level.biome.getStringID();
                        if (biomeID.equals(BiomeRegistry.DESERT.getStringID())) {
                            exp *= 2;
                        } else if (biomeID.equals(BiomeRegistry.DESERT_VILLAGE.getStringID())) {
                            exp *= 2;
                        } else if (biomeID.equals(BiomeRegistry.SWAMP.getStringID())) {
                            exp *= 1.75F;
                        } else if (biomeID.equals(BiomeRegistry.SNOW.getStringID())) {
                            exp *= 1.25F;
                        } else if (biomeID.equals(BiomeRegistry.SNOW_VILLAGE.getStringID())) {
                            exp *= 1.25F;
                        } else if (biomeID.equals(BiomeRegistry.DUNGEON_ISLAND.getStringID())) {
                            if (Objects.equals(level.getStringID(), "dungeon")) {
                                exp *= 1.5F;
                            }
                        } else if (biomeID.equals(BiomeRegistry.TEMPLE.getStringID())) {
                            exp *= 3;
                        } else if (biomeID.equals(BiomeRegistry.FOREST_DEEP_CAVE_INCURSION.getStringID())) {
                            exp *= 3;
                        } else if (biomeID.equals(BiomeRegistry.SNOW_DEEP_CAVE_INCURSION.getStringID())) {
                            exp *= 3.2F;
                        } else if (biomeID.equals(BiomeRegistry.SWAMP_DEEP_CAVE_INCURSION.getStringID())) {
                            exp *= 3.4F;
                        } else if (biomeID.equals(BiomeRegistry.DESERT_DEEP_CAVE_INCURSION.getStringID())) {
                            exp *= 3.6F;
                        } else if (biomeID.equals(BiomeRegistry.SLIME_CAVE.getStringID())) {
                            exp *= 3.8F;
                        } else if (biomeID.equals(BiomeRegistry.GRAVEYARD.getStringID())) {
                            exp *= 4F;
                        } else if (biomeID.equals(BiomeRegistry.SPIDER_CASTLE.getStringID())) {
                            exp *= 4.2F;
                        } else if (biomeID.equals(BiomeRegistry.CRYSTAL_HOLLOW.getStringID())) {
                            exp *= 4.4F;
                        }
                    }

                    final int finalExp;
                    if (serverClient.characterStats().mob_kills.getKills(mob.getStringID()) == 0) {
                        finalExp = (int) (exp * 5);
                        serverClient.sendChatMessage(new LocalMessage("classmessage", "newmobkill", "mob", mob.getDisplayName(), "exp", finalExp));
                    } else {
                        finalExp = (int) exp;
                    }

                    if (finalExp > 0) {
                        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
                        playerData.modExp(serverClient, finalExp);
                    }
                }
            }
        }
    }
}
