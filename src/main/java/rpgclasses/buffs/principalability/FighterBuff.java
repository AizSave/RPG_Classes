package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import rpgclasses.buffs.SimpleClassBuff;

public class FighterBuff extends SimpleClassBuff {
    public FighterBuff() {
        super(
                new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, 0.2F),
                new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, 0.2F),
                new ModifierValue<>(BuffModifiers.MAX_MANA, -0.5F)
        );
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event) {
        super.onWasHit(buff, event);
        if (!event.wasPrevented && buff.owner.isServer()) {
            Mob attackOwner = event.attacker != null ? event.attacker.getAttackOwner() : null;
            boolean hasOwnerInChain = event.attacker != null && event.attacker.isInAttackOwnerChain(buff.owner);
            if (attackOwner != null && !hasOwnerInChain) {
                float dx = (float) (attackOwner.getX() - buff.owner.getX());
                float dy = (float) (attackOwner.getY() - buff.owner.getY());

                float damage = buff.owner.getArmor();
                if (attackOwner.getDistance(buff.owner) > 100) {
                    damage /= 2;
                }

                if (damage > 0) {
                    attackOwner.isServerHit(new GameDamage(damage), dx, dy, 50.0F, buff.owner);
                }
            }
        }
    }
}