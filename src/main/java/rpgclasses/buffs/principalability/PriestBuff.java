package rpgclasses.buffs.principalability;

import aphorea.registry.AphModifiers;
import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class PriestBuff extends SimpleClassBuff {
    public PriestBuff() {
        super(
                new ModifierValue<>(AphModifiers.MAGIC_HEALING, 1F),
                new ModifierValue<>(BuffModifiers.ARMOR, -0.5F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.5F)
        );
    }
}
