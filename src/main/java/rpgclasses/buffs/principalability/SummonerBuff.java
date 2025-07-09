package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class SummonerBuff extends SimpleClassBuff {
    public SummonerBuff() {
        super(
                new ModifierValue<>(BuffModifiers.ARMOR, -0.25F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.25F)
        );
    }
}
