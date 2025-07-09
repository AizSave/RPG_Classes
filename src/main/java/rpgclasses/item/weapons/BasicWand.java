package rpgclasses.item.weapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.ItemStatTipList;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.projectiles.BasicWandProjectile;
import rpgclasses.projectiles.FireWandProjectile;
import rpgclasses.projectiles.IceWandProjectile;
import rpgclasses.projectiles.ThunderWandProjectile;

public class BasicWand extends MagicProjectileToolItem implements ItemInteractAction {

    public BasicWand() {
        super(100);
        this.rarity = Rarity.NORMAL;
        this.attackAnimTime.setBaseValue(600);
        this.attackDamage.setBaseValue(10.0F).setUpgradedValue(1.0F, 60.0F);
        this.velocity.setBaseValue(100);
        this.knockback.setBaseValue(0);
        this.attackRange.setBaseValue(400);
        this.manaCost.setBaseValue(1.0F);
        this.attackXOffset = 12;
        this.attackYOffset = 10;
    }

    public ListGameTooltips getPostEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPostEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("global", "rpgclasses"));
        return tooltips;
    }

    @Override
    public void addStatTooltips(ItemStatTipList list, InventoryItem currentItem, InventoryItem lastItem, ItemAttackerMob perspective, boolean forceAdd) {
        this.addAttackDamageTip(list, currentItem, lastItem, perspective, forceAdd);
        this.addAttackSpeedTip(list, currentItem, lastItem, perspective);
        this.addCritChanceTip(list, currentItem, lastItem, perspective, forceAdd);
        this.addManaCostTip(list, currentItem, lastItem, perspective);
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent) {
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(attackerMob).volume(0.7F).pitch(GameRandom.globalRandom.getFloatBetween(1.0F, 1.1F)));
        }
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {

        Projectile projectile = new BasicWandProjectile(level, attackerMob, attackerMob.x, attackerMob.y, (float) x, (float) y, (float) this.getProjectileVelocity(item, attackerMob), this.getAttackRange(item), this.getAttackDamage(item), this.getKnockback(item, attackerMob));

        if (attackerMob.isPlayer) {
            PlayerMob player = (PlayerMob) attackerMob;

            PlayerData playerData = PlayerDataList.getCurrentPlayer(player);

            if (!player.buffManager.hasBuff("firewandcooldown") && playerData.classAbilitiesStringIDs.contains("firewand_0")) {
                projectile = new FireWandProjectile(level, player, player.x, player.y, (float) x, (float) y, (float) this.getProjectileVelocity(item, player), this.getAttackRange(item), this.getKnockback(item, player));

                int cooldown = 24000;
                if (playerData.classAbilitiesStringIDs.contains("firewand_3")) {
                    cooldown = 4000;
                } else if (playerData.classAbilitiesStringIDs.contains("firewand_2")) {
                    cooldown /= 4;
                } else if (playerData.classAbilitiesStringIDs.contains("firewand_1")) {
                    cooldown /= 2;
                }
                player.buffManager.addBuff(new ActiveBuff("firewandcooldown", player, cooldown, null), true);
            }

            if (!player.buffManager.hasBuff("icewandcooldown") && playerData.classAbilitiesStringIDs.contains("icewand_0")) {
                projectile = new IceWandProjectile(level, player, player.x, player.y, (float) x, (float) y, (float) this.getProjectileVelocity(item, player), this.getAttackRange(item), this.getKnockback(item, player));

                int cooldown = 24000;
                if (playerData.classAbilitiesStringIDs.contains("icewand_3")) {
                    cooldown = 4000;
                } else if (playerData.classAbilitiesStringIDs.contains("icewand_2")) {
                    cooldown /= 4;
                } else if (playerData.classAbilitiesStringIDs.contains("icewand_1")) {
                    cooldown /= 2;
                }

                player.buffManager.addBuff(new ActiveBuff("icewandcooldown", player, cooldown, null), true);
            }

            if (!player.buffManager.hasBuff("thunderwandcooldown") && playerData.classAbilitiesStringIDs.contains("thunderwand_0")) {
                projectile = new ThunderWandProjectile(level, player, player.x, player.y, (float) x, (float) y, (float) this.getProjectileVelocity(item, player), this.getAttackRange(item), this.getKnockback(item, player));

                int cooldown = 24000;
                if (playerData.classAbilitiesStringIDs.contains("thunderwand_3")) {
                    cooldown = 4000;
                } else if (playerData.classAbilitiesStringIDs.contains("thunderwand_2")) {
                    cooldown /= 4;
                } else if (playerData.classAbilitiesStringIDs.contains("thunderwand_1")) {
                    cooldown /= 2;
                }

                player.buffManager.addBuff(new ActiveBuff("thunderwandcooldown", player, cooldown, null), true);
            }
        }

        GameRandom random = new GameRandom(seed);
        projectile.resetUniqueID(random);
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToAllClients(new PacketSpawnProjectile(projectile));
        }

        this.consumeMana(attackerMob, item);
        return item;
    }
}