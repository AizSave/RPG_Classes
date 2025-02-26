package rpgclasses.buffs.ability;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.levelevents.FireWandExplosionLevelEvent;

public class FireReactionBuff extends SimpleClassBuff {
    public int abilityLevel;

    public FireReactionBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    @Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
    }

    @Override
    public void onWasHit(ActiveBuff buff, MobWasHitEvent event) {
        super.onWasHit(buff, event);
        Mob mob = buff.owner;
        if (!mob.buffManager.hasBuff("firereactioncooldown")) {
            mob.getLevel().entityManager.addLevelEvent(new FireWandExplosionLevelEvent(mob.getX(), mob.getY(), 50, 0, mob));

            int cooldown = 16000;
            if (abilityLevel == 2) {
                cooldown /= 4;
            } else if (abilityLevel == 1) {
                cooldown /= 2;
            }
            mob.buffManager.addBuff(new ActiveBuff("firereactioncooldown", mob, cooldown, null), false);
        }
    }
}
