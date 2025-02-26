package rpgclasses.buffs.ability;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.buffs.SimpleClassChargeBuff;

public class FrenziedGuardBuff extends SimpleClassBuff {
    public int abilityLevel;

    public FrenziedGuardBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    @Override
    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        if (event != null && !event.wasPrevented && event.damageType == DamageTypeRegistry.MELEE && event.damage > 0 && event.target != null) {
            buff.owner.buffManager.addBuff(new ActiveBuff("frenziedguardcharge" + "_" + abilityLevel, buff.owner, abilityLevel == 2 ? 5F : 3F, null), false);
        }
    }

    public static class FrenziedGuardChargeBuff extends SimpleClassChargeBuff {
        public int abilityLevel;

        public FrenziedGuardChargeBuff(int abilityLevel) {
            super(new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 0.85F));
            this.abilityLevel = abilityLevel;
        }

        @Override
        public int getStackSize(ActiveBuff buff) {
            return abilityLevel + 1;
        }
    }
}