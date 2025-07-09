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

public class TomeOfOblivion extends ConsumableItem {
    public TomeOfOblivion() {
        super(10, true);
        this.rarity = Rarity.RARE;
    }

    @Override
    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap mapContent) {
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        return playerData.classAbilitiesStringIDs.isEmpty() ? "noabilities" : null;
    }

    @Override
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent) {
        if (player.isServerClient()) {
            ServerClient serverClient = player.getServerClient();
            PlayerData playerData = PlayerDataList.getCurrentPlayer(player);

            String[] removedAbilityNames = new String[3];

            for (int i = 0; i < 3; i++) {
                if (!playerData.classAbilitiesStringIDs.isEmpty()) {
                    String[] removedAbility = playerData.classAbilitiesStringIDs.get(playerData.classAbilitiesStringIDs.size() - 1).split("_");
                    removedAbilityNames[i] = Localization.translate("classescontainer", "abilityname", "ability", AbilityRegistry.getAbility(removedAbility[0]).getDisplayName(), "level", removedAbility[1]);

                    playerData.classAbilitiesStringIDs.remove(playerData.classAbilitiesStringIDs.size() - 1);
                } else {
                    removedAbilityNames[i] = "None";
                }
            }
            playerData.updateActiveBuffs();
            playerData.updateBuffs(player);
            serverClient.sendChatMessage(
                    new LocalMessage("classmessage", "talentpointsrestarted3",
                            "hab0", removedAbilityNames[0],
                            "hab1", removedAbilityNames[1],
                            "hab2", removedAbilityNames[2]
                    )
            );
        }

        if (this.singleUse) {
            item.setAmount(item.getAmount() - 1);
        }

        return super.onPlace(level, x, y, player, seed, item, mapContent);
    }

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltips", "tomeofoblivion"));
        tooltips.add(Localization.translate("itemtooltips", "taltentpointsrefund"));
        tooltips.add(Localization.translate("global", "rpgclasses"));
        return tooltips;
    }
}
