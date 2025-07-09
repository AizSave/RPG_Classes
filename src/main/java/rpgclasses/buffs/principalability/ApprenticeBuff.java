package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class ApprenticeBuff extends SimpleClassBuff {
    public ApprenticeBuff() {
        super(
                new ModifierValue<>(BuffModifiers.MAX_MANA, 1F),
                new ModifierValue<>(BuffModifiers.MANA_REGEN, 1F),
                new ModifierValue<>(BuffModifiers.ARMOR, -0.5F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.25F)
        );
    }
}
