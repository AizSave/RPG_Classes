package rpgclasses.item.weapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.ItemControllerInteract;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.ItemStatTipList;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;
import rpgclasses.projectiles.BasicStaffProjectile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class BasicStaff extends MagicProjectileToolItem implements ItemInteractAction {

    public BasicStaff() {
        super(100);
        this.rarity = Rarity.NORMAL;
        this.attackAnimTime.setBaseValue(600);
        this.attackDamage.setBaseValue(10.0F).setUpgradedValue(1.0F, 80.0F);
        this.velocity.setBaseValue(100);
        this.knockback.setBaseValue(0);
        this.attackRange.setBaseValue(250);
        this.manaCost.setBaseValue(0F);
        this.attackXOffset = 12;
        this.attackYOffset = 10;

        this.damageType = DamageTypeRegistry.SUMMON;

        this.setItemCategory("equipment", "weapons", "summonweapons");
        this.setItemCategory(ItemCategory.craftingManager, "equipment", "weapons", "summonweapons");
        this.keyWords.add("summon");

    }

    @Override
    public ListGameTooltips getPostEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPostEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("global", "rpgclasses"));
        return tooltips;
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(attackerMob).volume(0.7F).pitch(GameRandom.globalRandom.getFloatBetween(1.0F, 1.1F)));
        }
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {

        Projectile projectile = new BasicStaffProjectile(level, attackerMob, attackerMob.x, attackerMob.y, (float) x, (float) y, (float) this.getProjectileVelocity(item, attackerMob), this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, attackerMob));
        projectile.moveDist(40);
        GameRandom random = new GameRandom(seed);
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToAllClients(new PacketSpawnProjectile(projectile));
        }

        this.consumeMana(attackerMob, item);
        return item;
    }

    @Override
    public ItemControllerInteract getControllerInteract(Level level, PlayerMob player, InventoryItem item, boolean beforeObjectInteract, int interactDir, LinkedList<Rectangle> mobInteractBoxes, LinkedList<Rectangle> tileInteractBoxes) {
        Point2D.Float controllerAimDir = player.getControllerAimDir();
        Point levelPos = this.getControllerAttackLevelPos(level, controllerAimDir.x, controllerAimDir.y, player, item);
        return new ItemControllerInteract(levelPos.x, levelPos.y) {
            public DrawOptions getDrawOptions(GameCamera camera) {
                return null;
            }

            public void onCurrentlyFocused(GameCamera camera) {
            }
        };
    }

    @Override
    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item) {
        return true;
    }

    @Override
    public int getLevelInteractCooldownTime(InventoryItem item, ItemAttackerMob attackerMob) {
        return 0;
    }

    @Override
    public int getLevelInteractAttackAnimTime(InventoryItem item, ItemAttackerMob attackerMob) {
        return 0;
    }

    @Override
    public InventoryItem onLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int seed, GNDItemMap mapContent) {
        if (!attackerMob.isPlayer) {
            return item;
        } else {
            SummonToolItem.runSummonFocus(level, x, y, (PlayerMob) attackerMob);
            return item;
        }
    }

    @Override

    public void showLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.shake, SoundEffect.effect(attackerMob).volume(0.9F).pitch(GameRandom.globalRandom.getFloatBetween(1.5F, 1.7F)));
        }

    }

    @Override
    public void addStatTooltips(ItemStatTipList list, InventoryItem currentItem, InventoryItem lastItem, ItemAttackerMob perspective, boolean forceAdd) {
        this.addAttackDamageTip(list, currentItem, lastItem, perspective, forceAdd);
        this.addAttackSpeedTip(list, currentItem, lastItem, perspective);
        this.addResilienceGainTip(list, currentItem, lastItem, perspective, forceAdd);
        this.addCritChanceTip(list, currentItem, lastItem, perspective, forceAdd);
        this.addManaCostTip(list, currentItem, lastItem, perspective);
    }

}