package rpgclasses.buffs;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SimpleClassChargeBuff extends Buff {
    protected ModifierValue<?>[] modifiers;

    public SimpleClassChargeBuff(ModifierValue<?>... modifiers) {
        this.isVisible = true;
        this.isImportant = true;
        this.canCancel = false;
        this.shouldSave = true;
        this.modifiers = modifiers;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        for (ModifierValue<?> modifier : this.modifiers) {
            modifier.apply(buff);
        }
    }

    @Override
    public void updateLocalDisplayName() {
        String[] stringID = this.getStringID().replace("charge", "").split("_");
        this.displayName = this.isVisible ? new StaticMessage(
                Localization.translate("classescontainer", "abilityname",
                        "ability", Localization.translate("rpgclass", stringID[0]),
                        "level", stringID[1]
                )
        ) : new StaticMessage(this.getStringID());
    }

}
