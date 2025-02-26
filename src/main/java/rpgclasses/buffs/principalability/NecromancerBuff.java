package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.MobFollower;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import rpgclasses.buffs.SimpleClassBuff;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NecromancerBuff extends SimpleClassBuff {
    int maxSummons;

    public NecromancerBuff() {
        super(
                new ModifierValue<>(BuffModifiers.ARMOR, -0.5F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.5F)
        );
    }

    @Override
    public void onLoad(ActiveBuff buff) {
        maxSummons = 0;
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        if (buff.owner.isPlayer) {
            PlayerMob player = ((PlayerMob) buff.owner);
            int newMaxSummons = getMaxSummons(player);
            if (maxSummons != newMaxSummons) {
                runSummons(buff, newMaxSummons);
            }
        }
    }

    public void runSummons(ActiveBuff buff, int newMaxSummons) {
        PlayerMob player = ((PlayerMob) buff.owner);
        if (maxSummons > 0) {
            List<MobFollower> necromancerFollowers = player.getServerClient().streamFollowers()
                    .filter(f -> Objects.equals(f.summonType, "necromancer"))
                    .collect(Collectors.toList());

            for (MobFollower m : necromancerFollowers) {
                player.getServerClient().removeFollower(m.mob, true);
            }
        }
        maxSummons = newMaxSummons;
        for (int i = 0; i < maxSummons; i++) {
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("basicskeleton", buff.owner.getLevel());
            player.getServerClient().addFollower("necromancer", mob, FollowPosition.PYRAMID, "necromancer_0", 1, getMaxSummons(player), null, true);
            mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
        }
    }

    public int getMaxSummons(PlayerMob player) {
        return player == null ? BuffModifiers.MAX_SUMMONS.defaultBuffManagerValue : player.buffManager.getModifier(BuffModifiers.MAX_SUMMONS);
    }

}
