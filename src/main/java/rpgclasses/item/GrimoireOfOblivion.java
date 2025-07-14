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

import java.awt.geom.Line2D;

public class GrimoireOfOblivion extends ConsumableItem {
    public GrimoireOfOblivion() {
        super(1, true);
        this.rarity = Rarity.LEGENDARY;
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
            playerData.classAbilitiesStringIDs.clear();
            playerData.classActiveAbilitiesStringIDs.clear();
            playerData.updateBuffs(player);
            serverClient.sendChatMessage(new LocalMessage("classmessage", "talentpointsrestarted"));
        }

        if (this.isSingleUse(player)) {
            item.setAmount(item.getAmount() - 1);
        }

        return super.onPlace(level, x, y, player, seed, item, mapContent);
    }

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltips", "grimoireofoblivion"));
        tooltips.add(Localization.translate("itemtooltips", "taltentpointsrefund"));
        tooltips.add(Localization.translate("global", "rpgclasses"));
        return tooltips;
    }
}