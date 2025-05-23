package rpgclasses.buffs.ability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import rpgclasses.RPG_Classes;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.buffs.SimpleClassChargeBuff;

public class RelentlessEvasionBuff extends SimpleClassBuff {
    public int abilityLevel;

    public RelentlessEvasionBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    @Override
    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        if (event != null && !event.wasPrevented && event.target != null && event.attacker != null && event.attacker.getAttackOwner() != null && event.target.getDistance(event.attacker.getAttackOwner()) <= 100 && event.damage > 0) {
            buff.owner.buffManager.addBuff(new ActiveBuff("relentlesschasecharge" + "_" + abilityLevel, buff.owner, 2F, null), false);
        }
    }

    public static class RelentlessEvasionChargeBuff extends SimpleClassChargeBuff {
        public int abilityLevel;

        public RelentlessEvasionChargeBuff(int abilityLevel) {
            super(new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.03F));
            this.abilityLevel = abilityLevel;
        }

        @Override
        public int getStackSize(ActiveBuff buff) {
            return (int) Math.pow(2, abilityLevel + 1);
        }
    }
}