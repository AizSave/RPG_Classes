package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.packet.PacketSummonFocus;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.MobBeforeHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import rpgclasses.buffs.MarkedBuff;
import rpgclasses.buffs.SimpleClassBuff;

public class BeastMasterBuff extends SimpleClassBuff {
    public BeastMasterBuff() {
        super(
                new ModifierValue<>(BuffModifiers.RANGED_CRIT_DAMAGE, 1F),
                new ModifierValue<>(BuffModifiers.ARMOR, -0.25F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.25F),
                new ModifierValue<>(BuffModifiers.ATTACK_SPEED, -0.5F)
        );
    }

    @Override
    public void onBeforeAttacked(ActiveBuff buff, MobBeforeHitEvent event) {
        super.onBeforeAttacked(buff, event);
        if (MarkedBuff.isMarked(buff.owner, event.target)) {
            event.damage = event.damage.setCritChance(100F);
        } else {
            ((PlayerMob) buff.owner).serverFollowersManager.summonFocusMob = event.target;

            ((PlayerMob) buff.owner).getServerClient().sendPacket(new PacketSummonFocus(event.target));
        }
    }

    @Override
    public void onLoad(ActiveBuff buff) {
        if (buff.owner.isServer() && buff.owner.isPlayer) {
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("wolf", buff.owner.getLevel());

            ((PlayerMob) buff.owner).serverFollowersManager.addFollower("wolf", mob, FollowPosition.PYRAMID, "beastmaster_0", 1, 1, null, true);
            mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
        }
    }
}
