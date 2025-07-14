package rpgclasses.item;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.registry.AbilityRegistry;

import java.awt.geom.Line2D;

public class ScrollOfOblivion extends ConsumableItem {
    public ScrollOfOblivion() {
        super(100, true);
        this.rarity = Rarity.UNCOMMON;
    }

    @Override
    public String canPlace(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent) {
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        return playerData.classAbilitiesStringIDs.isEmpty() ? "noabilities" : null;
    }

    @Override
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent) {
        if (player.isServerClient()) {
            ServerClient serverClient = player.getServerClient();
            PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
            String[] removedAbility = playerData.classAbilitiesStringIDs.get(playerData.classAbilitiesStringIDs.size() - 1).split("_");
            String removedAbilityName = Localization.translate("classescontainer", "abilityname", "ability", AbilityRegistry.getAbility(removedAbility[0]).getDisplayName(), "level", removedAbility[1]);
            playerData.classAbilitiesStringIDs.remove(playerData.classAbilitiesStringIDs.size() - 1);
            playerData.updateActiveBuffs();
            playerData.updateBuffs(player);
            serverClient.sendChatMessage(new LocalMessage("classmessage", "talentpointsrestarted1", "hab", removedAbilityName));
        }

        if (this.isSingleUse(player)) {
            item.setAmount(item.getAmount() - 1);
        }

        return super.onPlace(level, x, y, player, seed, item, mapContent);
    }

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltips", "scrollofoblivion"));
        tooltips.add(Localization.translate("itemtooltips", "taltentpointsrefund"));
        tooltips.add(Localization.translate("global", "rpgclasses"));
        return tooltips;
    }
}
