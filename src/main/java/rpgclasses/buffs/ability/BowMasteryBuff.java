package rpgclasses.buffs.ability;

import aphorea.utils.AphDistances;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
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
    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack) {
        if (player.isServer() && !player.buffManager.hasBuff("bowmasterycooldown") && item.item.type == Item.Type.TOOL) {
            ToolItem toolItem = (ToolItem) item.item;
            if (toolItem instanceof BowProjectileToolItem) {
                int cooldown = 4000;
                if (abilityLevel == 1) {
                    cooldown /= 2;
                } else if (abilityLevel == 2) {
                    cooldown /= 4;
                }
                BowProjectileToolItem projectileToolItem = (BowProjectileToolItem) toolItem;

                Packet attackContent = new Packet();
                PacketReader contentReader = new PacketReader(attackContent);
                int seed = Item.getRandomAttackSeed(GameRandom.globalRandom);

                Item arrow = ItemRegistry.getItem("unstablegelarrow");

                int newTargetX;
                int newTargetY;
                Mob targetMob = AphDistances.findClosestMob(player, m -> MarkedBuff.isMarked(player, m), 500);
                if (targetMob == null) {
                    newTargetX = GameRandom.globalRandom.getIntOffset(targetX, 32);
                    newTargetY = GameRandom.globalRandom.getIntOffset(targetY, 32);
                } else {
                    newTargetX = targetMob.getX();
                    newTargetY = targetMob.getY();
                }

                fireProjectiles(player.getLevel(), newTargetX, newTargetY, player, projectileToolItem, item, seed, (ArrowItem) arrow, contentReader);
                player.buffManager.addBuff(new ActiveBuff("bowmasterycooldown", player, cooldown, null), true);

            }
        }
    }

    protected void fireProjectiles(Level level, int x, int y, PlayerMob player, BowProjectileToolItem projectileToolItem, InventoryItem item, int seed, ArrowItem arrow, PacketReader contentReader) {
        Projectile projectile = projectileToolItem.getProjectile(level, x, y, player, item, seed, arrow, false, contentReader);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(projectileToolItem.getResilienceGain(item)));
        projectile.dropItem = false;
        projectile.getUniqueID(new GameRandom(seed));
        level.entityManager.projectiles.add(projectile);
    }

}