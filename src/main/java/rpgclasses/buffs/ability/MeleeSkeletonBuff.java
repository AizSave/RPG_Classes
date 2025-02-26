package rpgclasses.buffs.ability;

import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.MobFollower;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import rpgclasses.buffs.SimpleClassBuff;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MeleeSkeletonBuff extends SimpleClassBuff {
    public int abilityLevel;

    public MeleeSkeletonBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    @Override
    public void onLoad(ActiveBuff buff) {
        if (buff.owner.isServer() && buff.owner.isPlayer) {
            runSummons(buff, abilityLevel + 1);
        }
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        if (buff.owner.isPlayer) {
            runSummons(buff, abilityLevel + 1);
        }
    }

    public void runSummons(ActiveBuff buff, int summons) {
        PlayerMob player = ((PlayerMob) buff.owner);
        List<MobFollower> necromancerFollowers = player.getServerClient().streamFollowers()
                .filter(f -> Objects.equals(f.summonType, "meleeskeleton"))
                .collect(Collectors.toList());

        if (necromancerFollowers.size() != summons) {
            for (MobFollower m : necromancerFollowers) {
                player.getServerClient().removeFollower(m.mob, true);
            }

            for (int i = 0; i < summons; i++) {
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("meleeskeleton", buff.owner.getLevel());
                player.getServerClient().addFollower("meleeskeleton", mob, FollowPosition.PYRAMID, "meleeskeleton_" + abilityLevel, 1, summons, null, true);
                mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
            }
        }
    }

}
