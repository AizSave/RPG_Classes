package rpgclasses.methodpatches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.MageHumanMob;
import necesse.level.maps.levelData.villageShops.ShopItem;
import necesse.level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;

@ModMethodPatch(
        target = MageHumanMob.class,
        name = "getShopItems",
        arguments = {VillageShopsData.class, ServerClient.class}
)
public class MageShopPatch {
    public MageShopPatch() {
    }

    @Advice.OnMethodEnter
    static boolean onEnter(@Advice.This MageHumanMob mageHumanMob, @Advice.Argument(0) VillageShopsData data, @Advice.Argument(1) ServerClient client) {
        return false;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This MageHumanMob mageHumanMob, @Advice.Argument(0) VillageShopsData data, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> shopItems) {
        if (shopItems == null) {
            shopItems = new ArrayList<>();
        }

        GameRandom random = new GameRandom(mageHumanMob.getShopSeed() + 5L);
        shopItems.add(ShopItem.item("basicwand", random.getIntBetween(25, 50)));
        shopItems.add(ShopItem.item("scrollofoblivion", random.getIntBetween(200, 240)));
        shopItems.add(ShopItem.item("tomeofoblivion", random.getIntBetween(500, 600)));
        shopItems.add(ShopItem.item("codexofoblivion", random.getIntBetween(1000, 1200)));
        shopItems.add(ShopItem.item("grimoireofoblivion", random.getIntBetween(5000, 6000)));
    }
}