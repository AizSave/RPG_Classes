package rpgclasses.buffs.principalability;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.level.maps.Level;
import rpgclasses.buffs.SimpleClassBuff;

public class ShadowBuff extends SimpleClassBuff {
    public ShadowBuff() {
        super(
                new ModifierValue<>(BuffModifiers.TARGET_RANGE, -0.2F),
                new ModifierValue<>(BuffModifiers.SPEED, 0.3F),
                new ModifierValue<>(BuffModifiers.ARMOR, -0.25F),
                new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.25F)
        );
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
        if (buff.owner.isPlayer) {
            PlayerMob player = (PlayerMob) buff.owner;
            Level level = player.getLevel();
            if (level.getLightLevel(player.getTileX(), player.getTileY()).getLevel() <= 80) {
                buff.setModifier(BuffModifiers.MELEE_CRIT_DAMAGE, 0.5F);
                buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.2F);
            }
        }
    }
}
