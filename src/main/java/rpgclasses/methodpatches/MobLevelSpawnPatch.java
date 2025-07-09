package rpgclasses.methodpatches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.registries.BiomeRegistry;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import net.bytebuddy.asm.Advice;
import rpgclasses.Config;

import java.util.Objects;

@ModMethodPatch(target = Level.class, name = "onMobSpawned", arguments = {Mob.class})
public class MobLevelSpawnPatch {
    @Advice.OnMethodEnter
    public static void onEnter(@Advice.This Level level, @Advice.Argument(0) Mob mob) {
        if (mob.isHostile && !mob.isBoss()) {
            Config config = Config.getConfig();
            float mobHealth = 100 + config.getHealthMobs() + getLevelDifficultyModifier(config, level);

            mob.setMaxHealth((int) (mob.getMaxHealth() * mobHealth / 100));
            mob.setHealth(mob.getMaxHealth());
        }
    }

    public static float getLevelDifficultyModifier(Config config, Level level) {
        Biome biome = level.biome;
        int dimension = level.getIslandDimension();

        if (biome == BiomeRegistry.FOREST || biome == BiomeRegistry.FOREST_VILLAGE) {
            if (dimension == -2) {
                return config.getHealthDeepForestMobs();
            } else {
                return config.getHealthForestMobs();
            }

        } else if (biome == BiomeRegistry.DESERT || biome == BiomeRegistry.DESERT_VILLAGE) {
            if (dimension == -2) {
                return config.getHealthDeepDesertMobs();
            } else {
                return config.getHealthDesertMobs();
            }

        } else if (biome == BiomeRegistry.SWAMP) {
            if (dimension == -2) {
                return config.getHealthDeepSwampMobs();
            } else {
                return config.getHealthSwampMobs();
            }

        } else if (biome == BiomeRegistry.SNOW || biome == BiomeRegistry.SNOW_VILLAGE) {
            if (dimension == -2) {
                return config.getHealthDeepSnowMobs();
            } else {
                return config.getHealthSnowMobs();
            }

        } else if (biome == BiomeRegistry.DUNGEON_ISLAND) {
            if (Objects.equals(level.getStringID(), "dungeon")) {
                return config.getHealthDungeonMobs();
            }

        } else if (biome == BiomeRegistry.TEMPLE) {
            return config.getHealthTempleMobs();

        } else if (biome == BiomeRegistry.FOREST_DEEP_CAVE_INCURSION) {
            return config.getHealthForestIncursionMobs();

        } else if (biome == BiomeRegistry.SNOW_DEEP_CAVE_INCURSION) {
            return config.getHealthSnowIncursionMobs();

        } else if (biome == BiomeRegistry.SWAMP_DEEP_CAVE_INCURSION) {
            return config.getHealthSwampIncursionMobs();

        } else if (biome == BiomeRegistry.DESERT_DEEP_CAVE_INCURSION) {
            return config.getHealthDesertIncursionMobs();

        } else if (biome == BiomeRegistry.SLIME_CAVE) {
            return config.getHealthSlimeCaveIncursionMobs();

        } else if (biome == BiomeRegistry.GRAVEYARD) {
            return config.getHealthGraveyardCaveIncursionMobs();

        } else if (biome == BiomeRegistry.SPIDER_CASTLE) {
            return config.getHealthSpiderCastleCaveIncursionMobs();

        } else if (biome == BiomeRegistry.CRYSTAL_HOLLOW) {
            return config.getHealthCrystalHollowCaveIncursionMobs();

        }

        return 1;
    }

}
