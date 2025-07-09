package rpgclasses.buffs.ability;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.DodgeClassBuff;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.buffs.SimpleClassChargeBuff;

public class EvasiveLethalityBuff extends SimpleClassBuff implements DodgeClassBuff {
    public int abilityLevel;

    public EvasiveLethalityBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    @Override
    public void onDodge(ActiveBuff buff) {
        buff.owner.buffManager.addBuff(new ActiveBuff("evasivelethalitycharge_" + abilityLevel, buff.owner, 5F, null), true);
    }

    public static class EvasiveLethalityChargeBuff extends SimpleClassChargeBuff {
        public int abilityLevel;

        public EvasiveLethalityChargeBuff(int abilityLevel) {
            super();
            this.abilityLevel = abilityLevel;
        }

        @Override
        public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
            super.init(buff, eventSubscriber);
            float mod = 0.4F;
            if (abilityLevel == 0) {
                mod /= 4;
            } else if (abilityLevel == 1) {
                mod /= 2;
            }
            buff.setModifier(BuffModifiers.CRIT_CHANCE, mod);
        }
    }
}