package rpgclasses.buffs.ability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.buffs.SimpleClassChargeBuff;

public class RelentlessChaseBuff extends SimpleClassBuff {
    public int abilityLevel;

    public RelentlessChaseBuff(int abilityLevel) {
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

    public static class RelentlessChaseChargeBuff extends SimpleClassChargeBuff {
        public int abilityLevel;

        public RelentlessChaseChargeBuff(int abilityLevel) {
            super(new ModifierValue<>(BuffModifiers.SPEED, 0.1F));
            this.abilityLevel = abilityLevel;
        }

        @Override
        public int getStackSize(ActiveBuff buff) {
            return (int) Math.pow(2, abilityLevel + 1);
        }
    }
}