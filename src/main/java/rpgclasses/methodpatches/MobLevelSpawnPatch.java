package rpgclasses.methodpatches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.registries.BiomeRegistry;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;
import rpgclasses.Config;

import java.util.Objects;

@ModMethodPatch(target = Level.class, name = "onMobSpawned", arguments = {Mob.class})
public class MobLevelSpawnPatch {
    @Advice.OnMethodEnter
    public static void onEnter(@Advice.This Level level, @Advice.Argument(0) Mob mob) {
        if (mob.isHostile && !mob.isBoss()) {
            Config config = Config.getConfig();
            float mobHealthModifier = 100 + config.getMobsHealthModifier();

            String biomeID = level.biome.getStringID();
            if (biomeID.equals(BiomeRegistry.FOREST.getStringID())) {
                mobHealthModifier += config.getForestMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.FOREST_VILLAGE.getStringID())) {
                mobHealthModifier += config.getForestMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.DESERT.getStringID())) {
                mobHealthModifier += config.getDesertMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.DESERT_VILLAGE.getStringID())) {
                mobHealthModifier += config.getDesertMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.SWAMP.getStringID())) {
                mobHealthModifier += config.getSwampMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.SNOW.getStringID())) {
                mobHealthModifier += config.getSnowMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.SNOW_VILLAGE.getStringID())) {
                mobHealthModifier += config.getSnowMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.DUNGEON_ISLAND.getStringID())) {
                if (Objects.equals(level.getStringID(), "dungeon")) {
                    mobHealthModifier += config.getDungeonMobsHealthModifier();
                }
            } else if (biomeID.equals(BiomeRegistry.TEMPLE.getStringID())) {
                mobHealthModifier += config.getTempleMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.FOREST_DEEP_CAVE_INCURSION.getStringID())) {
                mobHealthModifier += config.getForestIncursionMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.SNOW_DEEP_CAVE_INCURSION.getStringID())) {
                mobHealthModifier += config.getSnowIncursionMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.SWAMP_DEEP_CAVE_INCURSION.getStringID())) {
                mobHealthModifier += config.getSwampIncursionMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.DESERT_DEEP_CAVE_INCURSION.getStringID())) {
                mobHealthModifier += config.getDesertIncursionMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.SLIME_CAVE.getStringID())) {
                mobHealthModifier += config.getSlimeCaveIncursionMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.GRAVEYARD.getStringID())) {
                mobHealthModifier += config.getGraveyardCaveIncursionMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.SPIDER_CASTLE.getStringID())) {
                mobHealthModifier += config.getSpiderCastleCaveIncursionMobsHealthModifier();
            } else if (biomeID.equals(BiomeRegistry.CRYSTAL_HOLLOW.getStringID())) {
                mobHealthModifier += config.getCrystalHollowCaveIncursionMobsHealthModifier();
            }

            mob.setMaxHealth((int) (mob.getMaxHealth() * mobHealthModifier / 100));
            mob.setHealth(mob.getMaxHealth());
        }
    }

}
