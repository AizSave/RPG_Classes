package rpgclasses.buffs;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SimpleClassBuff extends Buff {
    protected ModifierValue<?>[] modifiers;

    public SimpleClassBuff(ModifierValue<?>... modifiers) {
        this.canCancel = false;
        this.isVisible = false;
        this.isPassive = true;
        this.shouldSave = false;
        this.modifiers = modifiers;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        for (ModifierValue<?> modifier : this.modifiers) {
            modifier.apply(buff);
        }
    }

    public void onLoad(ActiveBuff buff) {
    }
}
