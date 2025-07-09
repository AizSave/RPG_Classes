package rpgclasses.buffs.ability;

import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MobBeforeHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import rpgclasses.buffs.MarkedBuff;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.packets.ConsumeManaPacket;

public class MagicRangerBuff extends SimpleClassBuff {
    public int abilityLevel;

    public MagicRangerBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    @Override
    public void onBeforeAttacked(ActiveBuff buff, MobBeforeHitEvent event) {
        super.onBeforeAttacked(buff, event);
        if (event.damage.type == DamageTypeRegistry.RANGED) {
            if ((float) buff.owner.getMaxMana() / 2 <= buff.owner.getMana()) {
                float manaCost = 2F;
                if (abilityLevel == 1) {
                    manaCost /= 2;
                } else if (abilityLevel == 2) {
                    manaCost /= 4;
                }
                PlayerMob player = ((PlayerMob) buff.owner);
                buff.owner.useMana(manaCost, player.isServerClient() ? player.getServerClient() : null);
                buff.owner.getServer().network.sendToClientsWithAnyRegion(new ConsumeManaPacket(((PlayerMob) buff.owner).getServerClient().slot, manaCost), buff.owner.getRegionPositions());
                event.damage = event.damage.modDamage(MarkedBuff.isMarked(buff.owner, event.target) ? 1.2F : 1.4F);
            }
        }
    }
}
