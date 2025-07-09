package rpgclasses.buffs.ability;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.projectiles.PlasmaGrenadeProjectile;

import java.util.Objects;

public class PlasmaGrenadeBuff extends SimpleClassBuff {
    public int abilityLevel;

    int attacks;
    String weapon;

    public PlasmaGrenadeBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        attacks = 0;
        weapon = "";
    }

    @Override
    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack) {
        if (attackerMob.isServer() && item.item.type == Item.Type.TOOL) {
            ToolItem toolItem = (ToolItem) item.item;
            if (toolItem instanceof GunProjectileToolItem) {
                if (!Objects.equals(weapon, toolItem.getStringID())) {
                    attacks = 1;
                    weapon = toolItem.getStringID();
                } else if (attacks < 7) {
                    attacks++;
                } else {
                    GunProjectileToolItem projectileToolItem = (GunProjectileToolItem) toolItem;
                    GameDamage damage = projectileToolItem.getAttackDamage(item);
                    if (abilityLevel == 0) {
                        damage = damage.setDamage(damage.damage / 2);
                    } else if (abilityLevel == 2) {
                        damage = damage.setDamage(damage.damage * 2);
                    }
                    attackerMob.getLevel().entityManager.projectiles.add(new PlasmaGrenadeProjectile(attackerMob.getLevel(), attackerMob, attackerMob.x, attackerMob.y, targetX, targetY, 200, 2000, damage, 0));
                    attacks = 0;
                }
            }
        }
    }
}