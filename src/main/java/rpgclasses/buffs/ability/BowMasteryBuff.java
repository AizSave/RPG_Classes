package rpgclasses.buffs.ability;

import aphorea.utils.AphDistances;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;
import rpgclasses.buffs.MarkedBuff;
import rpgclasses.buffs.SimpleClassBuff;

public class BowMasteryBuff extends SimpleClassBuff {
    public int abilityLevel;

    public BowMasteryBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    @Override
    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack) {
        if (attackerMob.isServer() && !attackerMob.buffManager.hasBuff("bowmasterycooldown") && item.item.type == Item.Type.TOOL) {
            ToolItem toolItem = (ToolItem) item.item;
            if (toolItem instanceof BowProjectileToolItem) {
                int cooldown = 4000;
                if (abilityLevel == 1) {
                    cooldown /= 2;
                } else if (abilityLevel == 2) {
                    cooldown /= 4;
                }
                BowProjectileToolItem projectileToolItem = (BowProjectileToolItem) toolItem;

                int seed = Item.getRandomAttackSeed(GameRandom.globalRandom);

                Item arrow = ItemRegistry.getItem("unstablegelarrow");

                int newTargetX;
                int newTargetY;
                Mob targetMob = AphDistances.findClosestMob(attackerMob, 500, m -> MarkedBuff.isMarked(attackerMob, m));
                if (targetMob == null) {
                    newTargetX = GameRandom.globalRandom.getIntOffset(targetX, 32);
                    newTargetY = GameRandom.globalRandom.getIntOffset(targetY, 32);
                } else {
                    newTargetX = targetMob.getX();
                    newTargetY = targetMob.getY();
                }

                fireProjectiles(attackerMob.getLevel(), newTargetX, newTargetY, attackerMob, projectileToolItem, item, seed, (ArrowItem) arrow, item.getGndData());
                attackerMob.buffManager.addBuff(new ActiveBuff("bowmasterycooldown", attackerMob, cooldown, null), true);

            }
        }
    }

    protected void fireProjectiles(Level level, int x, int y, ItemAttackerMob attackerMob, BowProjectileToolItem projectileToolItem, InventoryItem item, int seed, ArrowItem arrow, GNDItemMap mapContent) {
        Projectile projectile = projectileToolItem.getProjectile(level, x, y, attackerMob, item, seed, arrow, false, mapContent);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(projectileToolItem.getResilienceGain(item)));
        projectile.dropItem = false;
        projectile.getUniqueID(new GameRandom(seed));
        level.entityManager.projectiles.add(projectile);
    }

}