package rpgclasses.buffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MobBeforeHitCalculatedEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import rpgclasses.RPG_Classes;
import rpgclasses.packets.ShowDodgePacket;

import java.util.List;
import java.util.stream.Collectors;

public class DodgeBuff extends SimpleClassBuff {
    public DodgeBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void onBeforeHitCalculated(ActiveBuff buff, MobBeforeHitCalculatedEvent event) {
        super.onBeforeHitCalculated(buff, event);
        if (preventDamage(buff)) {
            event.prevent();
            event.showDamageTip = false;
            event.playHitSound = false;
        }
    }

    private boolean preventDamage(ActiveBuff buff) {
        String prevent = this.shouldPreventDamage(buff);
        if (prevent != null && buff.owner.isServer()) {
            if (prevent.equals("dodge")) {
                buff.owner.getServer().network.sendToAllClients(new ShowDodgePacket(buff.owner.getX(), buff.owner.getY()));
                final List<ActiveBuff> dodgeClassBuffs = buff.owner.buffManager.getBuffs().values().stream().filter(activeBuff -> activeBuff.buff instanceof DodgeClassBuff).collect(Collectors.toList());
                for (ActiveBuff activeBuff : dodgeClassBuffs) {
                    ((DodgeClassBuff) activeBuff.buff).onDodge(activeBuff);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public String shouldPreventDamage(ActiveBuff buff) {
        if (GameRandom.globalRandom.getChance(buff.owner.buffManager.getModifier(RPG_Classes.DODGE_CHANCE))) {
            return "dodge";
        }
        return null;
    }
}
