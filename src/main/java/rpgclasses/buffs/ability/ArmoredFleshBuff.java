package rpgclasses.buffs.ability;

import aphorea.registry.AphBuffs;
import necesse.engine.network.packet.PacketLifelineEvent;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.MobBeforeDamageOverTimeTakenEvent;
import necesse.entity.mobs.MobBeforeHitCalculatedEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.level.maps.Level;
import rpgclasses.buffs.SimpleClassBuff;

public class ArmoredFleshBuff extends SimpleClassBuff {
    public int abilityLevel;

    public ArmoredFleshBuff(int abilityLevel) {
        super();

        this.abilityLevel = abilityLevel;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        eventSubscriber.subscribeEvent(MobBeforeDamageOverTimeTakenEvent.class, (event) -> {
            if (this.runLogic(buff, event.getExpectedHealth())) {
                event.prevent();
            }
        });
    }

    public void onBeforeHitCalculated(ActiveBuff buff, MobBeforeHitCalculatedEvent event) {
        super.onBeforeHitCalculated(buff, event);
        if (this.runLogic(buff, event.getExpectedHealth())) {
            event.prevent();
        }

    }

    protected boolean runLogic(ActiveBuff buff, int expectedHealth) {
        Level level = buff.owner.getLevel();
        if (level.isServer() && !buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.LIFELINE_COOLDOWN.getID()) && expectedHealth <= 0) {
            buff.owner.setHealth(1);
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.INVULNERABLE_ACTIVE, buff.owner, 5F, null), true);
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.LIFELINE_COOLDOWN, buff.owner, 90F - 30F * abilityLevel, null), true);
            level.getServer().network.sendToClientsWithEntity(new PacketLifelineEvent(buff.owner.getUniqueID()), buff.owner);
            return true;
        } else {
            return false;
        }
    }

}