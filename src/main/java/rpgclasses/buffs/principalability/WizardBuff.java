package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class WizardBuff extends SimpleClassBuff {
    public WizardBuff() {
        super(
                new ModifierValue<>(BuffModifiers.MAGIC_DAMAGE, 2F),
                new ModifierValue<>(BuffModifiers.MANA_REGEN, 1F),
                new ModifierValue<>(BuffModifiers.ATTACK_MOVEMENT_MOD, 0F),
                new ModifierValue<>(BuffModifiers.MANA_USAGE, 1.5F),
                new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, -1F),
                new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, -1F),
                new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, -1F),
                new ModifierValue<>(BuffModifiers.ARMOR, -0.5F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.5F),
                new ModifierValue<>(BuffModifiers.SPEED, -0.5F)
        );
    }
}
