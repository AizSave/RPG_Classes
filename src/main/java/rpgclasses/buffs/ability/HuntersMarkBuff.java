package rpgclasses.buffs.ability;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobBeforeHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import rpgclasses.buffs.MarkedBuff;
import rpgclasses.buffs.SimpleClassBuff;

public class HuntersMarkBuff extends SimpleClassBuff {
    public int abilityLevel;

    public HuntersMarkBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    @Override
    public void onBeforeAttacked(ActiveBuff buff, MobBeforeHitEvent event) {
        super.onBeforeAttacked(buff, event);
        if (MarkedBuff.isMarked(buff.owner, event.target)) {
            float level;
            switch (abilityLevel) {
                case (1):
                    level = 0.2F;
                    break;
                case (2):
                    level = 0.3F;
                    break;
                case (3):
                    level = 0.4F;
                    break;
                case (4):
                    level = 0.6F;
                    break;
                case (5):
                    level = 0.8F;
                    break;
                default:
                    level = 0.1F;
            }
            event.damage = event.damage.modDamage(1F + level);
        } else if (event.damage.type == DamageTypeRegistry.RANGED && !buff.owner.buffManager.hasBuff("huntersmarkcooldown")) {
            event.target.buffManager.addBuff(new ActiveBuff("markedbuff", event.target, 5000, buff.owner), true);
            buff.owner.buffManager.addBuff(new ActiveBuff("huntersmarkcooldown", buff.owner, 5000, null), true);
        }
    }
}
