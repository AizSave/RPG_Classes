package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.RPG_Classes;
import rpgclasses.buffs.SimpleClassBuff;

public class AssassinBuff extends SimpleClassBuff {
    public AssassinBuff() {
        super(
                new ModifierValue<>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.5F),
                new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.1F),
                new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.2F),
                new ModifierValue<>(BuffModifiers.ARMOR, -0.25F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.25F)
        );
    }
}
