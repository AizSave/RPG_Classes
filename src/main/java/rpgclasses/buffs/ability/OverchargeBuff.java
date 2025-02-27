package rpgclasses.buffs.ability;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class OverchargeBuff extends SimpleClassBuff {
    public int abilityLevel;

    public OverchargeBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    @Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
        updateBuff(buff);
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        updateBuff(buff);
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        updateBuff(buff);
    }

    public void updateBuff(ActiveBuff buff) {
        float manaPercent = buff.owner.getMana() / buff.owner.getMaxMana();
        float mod = 0.8F;
        if (abilityLevel == 0) {
            mod /= 4;
        } else if (abilityLevel == 1) {
            mod /= 2;
        }
        buff.setModifier(
                BuffModifiers.MAGIC_DAMAGE, manaPercent * mod
        );
    }
}
