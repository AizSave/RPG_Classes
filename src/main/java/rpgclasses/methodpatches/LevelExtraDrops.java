package rpgclasses.methodpatches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.registries.BiomeRegistry;
import necesse.entity.mobs.Mob;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

import java.util.Objects;

@ModMethodPatch(
        target = Level.class,
        name = "getExtraMobDrops",
        arguments = {Mob.class}
)
public class LevelExtraDrops {
    @Advice.OnMethodExit
    static void onExit(@Advice.This Level level, @Advice.Return(readOnly = false) LootTable lootTable) {
        float chanceMod = 1F;
        String biomeID = level.biome.getStringID();
        if (biomeID.equals(BiomeRegistry.DESERT.getStringID())) {
            chanceMod = 2;
        } else if (biomeID.equals(BiomeRegistry.DESERT_VILLAGE.getStringID())) {
            chanceMod = 2;
        } else if (biomeID.equals(BiomeRegistry.SWAMP.getStringID())) {
            chanceMod = 1.75F;
        } else if (biomeID.equals(BiomeRegistry.SNOW.getStringID())) {
            chanceMod = 1.25F;
        } else if (biomeID.equals(BiomeRegistry.SNOW_VILLAGE.getStringID())) {
            chanceMod = 1.25F;
        } else if (biomeID.equals(BiomeRegistry.DUNGEON_ISLAND.getStringID())) {
            if (Objects.equals(level.getStringID(), "dungeon")) {
                chanceMod = 1.5F;
            }
        } else if (biomeID.equals(BiomeRegistry.TEMPLE.getStringID())) {
            chanceMod = 3;
        } else if (biomeID.equals(BiomeRegistry.FOREST_DEEP_CAVE_INCURSION.getStringID())) {
            chanceMod = 3;
        } else if (biomeID.equals(BiomeRegistry.SNOW_DEEP_CAVE_INCURSION.getStringID())) {
            chanceMod = 3.2F;
        } else if (biomeID.equals(BiomeRegistry.SWAMP_DEEP_CAVE_INCURSION.getStringID())) {
            chanceMod = 3.4F;
        } else if (biomeID.equals(BiomeRegistry.DESERT_DEEP_CAVE_INCURSION.getStringID())) {
            chanceMod = 3.6F;
        } else if (biomeID.equals(BiomeRegistry.SLIME_CAVE.getStringID())) {
            chanceMod = 3.8F;
        } else if (biomeID.equals(BiomeRegistry.GRAVEYARD.getStringID())) {
            chanceMod = 4F;
        } else if (biomeID.equals(BiomeRegistry.SPIDER_CASTLE.getStringID())) {
            chanceMod = 4.2F;
        } else if (biomeID.equals(BiomeRegistry.CRYSTAL_HOLLOW.getStringID())) {
            chanceMod = 4.4F;
        }

        lootTable.items.addAll(
                new LootItemList(
                        new ChanceLootItem(0.01F * chanceMod, "scrollofoblivion"),
                        new ChanceLootItem(0.004F * chanceMod, "tomeofoblivion"),
                        new ChanceLootItem(0.002F * chanceMod, "codexofoblivion"),
                        new ChanceLootItem(0.0004F * chanceMod, "grimoireofoblivion")
                )
        );
    }
}