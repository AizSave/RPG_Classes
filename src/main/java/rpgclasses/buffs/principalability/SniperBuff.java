package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobBeforeHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class SniperBuff extends SimpleClassBuff {
    public SniperBuff() {
        super(
                new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 1F),
                new ModifierValue<>(BuffModifiers.ARMOR, -0.25F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.25F)
        );
    }

    @Override
    public void onBeforeAttacked(ActiveBuff buff, MobBeforeHitEvent event) {
        super.onBeforeAttacked(buff, event);
        if (event.damage.type == DamageTypeRegistry.RANGED) {
            float distance = event.target.getDistance(buff.owner);
            if (distance > 0) {
                float damageMod = Math.min(1F + (distance / 700), 3F);
                if (damageMod > 1F) {
                    event.damage = event.damage.setDamage(event.damage.damage * damageMod);
                }
            }
        }
    }
}
