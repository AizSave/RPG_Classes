package rpgclasses.methodpatches;

import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;
import rpgclasses.Config;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;

import java.util.HashSet;

@ModMethodPatch(target = Mob.class, name = "onDeath", arguments = {Attacker.class, HashSet.class})
public class MobDeathPatch {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.This Mob mob, @Advice.Argument(0) Attacker attacker, @Advice.Argument(1) HashSet<Attacker> attackers) {
        if (mob.isServer() && mob.getLevel() != null && mob.isHostile && !mob.isSummoned) {
            Config config = Config.getConfig();

            float exp = (float) mob.getMaxHealth() / 20 * GameRandom.globalRandom.getFloatBetween(0.9F, 1.1F) * config.getExperienceMod();
            if (mob.getLevel().getIslandDimension() == -2) {
                exp *= 4;
            } else if (mob.getLevel().getIslandDimension() == -1) {
                exp *= 1.5F;
            }

            if (!mob.isBoss()) {
                exp = (float) Math.pow(exp, 1.1F);
            }

            HashSet<PlayerMob> processedPlayers = new HashSet<>();

            for (Attacker attackerMob : attackers) {
                if (attackerMob.getAttackOwner() != null && attackerMob.getAttackOwner().isPlayer) {
                    PlayerMob player = (PlayerMob) attackerMob.getAttackOwner();

                    if (processedPlayers.contains(player)) {
                        continue;
                    }

                    processedPlayers.add(player);

                    ServerClient serverClient = player.getServerClient();

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
