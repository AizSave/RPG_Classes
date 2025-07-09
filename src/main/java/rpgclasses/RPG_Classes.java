package rpgclasses;

import aphorea.mobs.bosses.UnstableGelSlime;
import aphorea.registry.AphRecipes;
import necesse.engine.GameDifficulty;
import necesse.engine.GlobalData;
import necesse.engine.commands.CommandsManager;
import necesse.engine.input.Control;
import necesse.engine.input.InputEvent;
import necesse.engine.input.InputID;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.modifiers.Modifier;
import necesse.engine.modifiers.ModifierLimiter;
import necesse.engine.network.client.Client;
import necesse.engine.registries.*;
import necesse.engine.state.MainGame;
import necesse.entity.levelEvent.nightSwarmEvent.NightSwarmLevelEvent;
import necesse.entity.mobs.MaxHealthGetter;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.bosses.*;
import necesse.entity.mobs.hostile.pirates.PirateCaptainMob;
import necesse.gfx.ui.ButtonIcon;
import necesse.gfx.ui.GameInterfaceStyle;
import necesse.gfx.ui.HoverStateTextures;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.recipe.Ingredient;
import rpgclasses.base.ClassAbility;
import rpgclasses.base.RPGClass;
import rpgclasses.buffs.DodgeBuff;
import rpgclasses.buffs.MarkedBuff;
import rpgclasses.buffs.SummonsNerfBuff;
import rpgclasses.commands.ModExp;
import rpgclasses.commands.ResetTalentPoints;
import rpgclasses.containers.ClassesContainer;
import rpgclasses.containers.ClassesContainerForm;
import rpgclasses.item.CodexOfOblivion;
import rpgclasses.item.GrimoireOfOblivion;
import rpgclasses.item.ScrollOfOblivion;
import rpgclasses.item.TomeOfOblivion;
import rpgclasses.item.weapons.BasicStaff;
import rpgclasses.item.weapons.BasicWand;
import rpgclasses.levelevents.FireWandExplosionLevelEvent;
import rpgclasses.levelevents.IceWandExplosionLevelEvent;
import rpgclasses.levelevents.PlasmaGrenadeExplosionLevelEvent;
import rpgclasses.levelevents.ThunderWandLevelEvent;
import rpgclasses.mobs.WolfMob;
import rpgclasses.packets.*;
import rpgclasses.projectiles.*;
import rpgclasses.registry.AbilityRegistry;
import rpgclasses.registry.RPGClassRegistry;

import java.lang.reflect.Field;


@ModEntry
public class RPG_Classes {

    public static final Modifier<Float> DODGE_CHANCE;

    static {
        DODGE_CHANCE = new Modifier<>(BuffModifiers.LIST, "dodgechance", 0.0F, 0.0F, Modifier.FLOAT_ADD_APPEND, (v) -> v < 0.0F ? 0.0F : (v > 0.8F ? 0.8F : v), Modifier.NORMAL_PERC_PARSER("dodgechance"), ModifierLimiter.NORMAL_PERC_LIMITER("dodgechance"));
    }

    public static int CLASSES_CONTAINER;

    public void init() {
        CLASSES_CONTAINER = ContainerRegistry.registerContainer((client, uniqueSeed, content) -> {
            return new ClassesContainerForm(client, new ClassesContainer(client.getClient(), uniqueSeed));
        }, (client, uniqueSeed, content, serverObject) -> {
            return new ClassesContainer(client, uniqueSeed);
        });

        Control.addModControl(new Control(InputID.KEY_K, "openclassesmenu") {
            @Override
            public void activate(InputEvent event) {
                super.activate(event);
                if (isPressed()) {
                    Client client = ((MainGame) GlobalData.getCurrentState()).getClient();
                    client.network.sendPacket(new ClassesMenuPacket());
                }
            }
        });

        LevelEventRegistry.registerEvent("plasmagrenadeexplosion", PlasmaGrenadeExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("firewandexplosion", FireWandExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("icewandexplosion", IceWandExplosionLevelEvent.class);
        LevelEventRegistry.registerEvent("thunderwand", ThunderWandLevelEvent.class);

        PacketRegistry.registerPacket(ClassesMenuPacket.class);
        PacketRegistry.registerPacket(ShowModExpPacket.class);
        PacketRegistry.registerPacket(ShowDodgePacket.class);
        PacketRegistry.registerPacket(ConsumeManaPacket.class);
        PacketRegistry.registerPacket(LoadPlayerDataPacket.class);
        PacketRegistry.registerPacket(UpdateClientDataPacket.class);
        PacketRegistry.registerPacket(UpdateClientExpPacket.class);
        PacketRegistry.registerPacket(UpdateClientAbilitiesPacket.class);

        ItemRegistry.registerItem("basicwand", new BasicWand(), -1F, true);
        ItemRegistry.registerItem("basicstaff", new BasicStaff(), -1F, true);
        ItemRegistry.registerItem("scrollofoblivion", new ScrollOfOblivion(), 100F, true);
        ItemRegistry.registerItem("tomeofoblivion", new TomeOfOblivion(), 250F, true);
        ItemRegistry.registerItem("codexofoblivion", new CodexOfOblivion(), 500F, true);
        ItemRegistry.registerItem("grimoireofoblivion", new GrimoireOfOblivion(), 2500F, true);

        CommandsManager.registerServerCommand(new ModExp());
        CommandsManager.registerServerCommand(new ResetTalentPoints());

        MobRegistry.registerMob("wolf", WolfMob.class, false);

        ProjectileRegistry.registerProjectile("basicwand", BasicWandProjectile.class, "none", "none");
        ProjectileRegistry.registerProjectile("basicstaff", BasicStaffProjectile.class, "none", "none");
        ProjectileRegistry.registerProjectile("firewand", FireWandProjectile.class, "none", "none");
        ProjectileRegistry.registerProjectile("icewand", IceWandProjectile.class, "none", "none");
        ProjectileRegistry.registerProjectile("thunderwand", ThunderWandProjectile.class, "none", "none");
        ProjectileRegistry.registerProjectile("plasmagrenade", PlasmaGrenadeProjectile.class, "none", "none");
        ProjectileRegistry.registerProjectile("rangeskeleton", RangeSkeletonProjectile.class, "babybone", "babybone_shadow");

        BuffRegistry.registerBuff("dodgebuff", new DodgeBuff());
        BuffRegistry.registerBuff(SummonsNerfBuff.stringID, new SummonsNerfBuff());
        BuffRegistry.registerBuff("markedbuff", new MarkedBuff());

        AbilityRegistry.registerCore();
        RPGClassRegistry.registerCore();

        for (RPGClass rpgClass : RPGClassRegistry.RPGClasses) {
            for (ClassAbility classAbility : rpgClass.classAbilities) {
                classAbility.onEndRegistry();
            }
        }
    }

    private static Config config;

    public static Config getConfig() {
        return config;
    }

    public void preInit() {
        config = new Config("settings.cfg");
        System.out.println(config);
    }

    public void postInit() {
        AphRecipes.addCraftingList("leatherdashers", RecipeTechRegistry.WORKSTATION, AphRecipes.AphCraftingRecipe.showBefore("basicwand", 1, new Ingredient("anylog", 10), new Ingredient("firemone", 1)));
        AphRecipes.addCraftingList("woodstaff", RecipeTechRegistry.IRON_ANVIL, AphRecipes.AphCraftingRecipe.showBefore("basicwand", 1, new Ingredient("anylog", 10), new Ingredient("firemone", 1)));
        AphRecipes.addCraftingList("basicwand", RecipeTechRegistry.IRON_ANVIL, AphRecipes.AphCraftingRecipe.showBefore("basicstaff", 1, new Ingredient("anylog", 10), new Ingredient("firemone", 1)));

        UnstableGelSlime.privateLootTable.items.add(
                new ChanceLootItem(0.1F, "scrollofoblivion")
        );
        EvilsProtectorMob.privateLootTable.items.add(
                new ChanceLootItem(0.1F, "scrollofoblivion")
        );
        QueenSpiderMob.privateLootTable.items.add(
                new ChanceLootItem(0.15F, "scrollofoblivion")
        );
        VoidWizard.privateLootTable.items.add(
                new ChanceLootItem(0.2F, "scrollofoblivion")
        );
        SwampGuardianHead.privateLootTable.items.add(
                new ChanceLootItem(0.25F, "scrollofoblivion")
        );
        AncientVultureMob.privateLootTable.items.add(
                new ChanceLootItem(0.3F, "scrollofoblivion")
        );
        PirateCaptainMob.privateLootTable.items.add(
                new ChanceLootItem(0.35F, "scrollofoblivion")
        );
        ReaperMob.privateLootTable.items.add(
                new ChanceLootItem(0.4F, "scrollofoblivion")
        );
        CryoQueenMob.privateLootTable.items.add(
                new ChanceLootItem(0.45F, "scrollofoblivion")
        );
        PestWardenHead.privateLootTable.items.add(
                new ChanceLootItem(0.5F, "scrollofoblivion")
        );
        FlyingSpiritsHead.privateLootTable.items.addAll(
                new LootItemList(
                        new ChanceLootItem(0.55F, "scrollofoblivion"),
                        new ChanceLootItem(0.05F, "tomeofoblivion")
                )
        );
        FallenWizardMob.privateLootTable.items.add(
                new LootItemList(
                        new ChanceLootItem(0.6F, "scrollofoblivion"),
                        new ChanceLootItem(0.1F, "tomeofoblivion")
                )
        );
        MotherSlimeMob.privateLootTable.items.add(
                new LootItemList(
                        new ChanceLootItem(0.65F, "scrollofoblivion"),
                        new ChanceLootItem(0.15F, "tomeofoblivion")
                )
        );
        NightSwarmLevelEvent.privateLootTable.items.add(
                new LootItemList(
                        new ChanceLootItem(0.7F, "scrollofoblivion"),
                        new ChanceLootItem(0.2F, "tomeofoblivion")
                )
        );
        SpiderEmpressMob.privateLootTable.items.add(
                new LootItemList(
                        new ChanceLootItem(0.75F, "scrollofoblivion"),
                        new ChanceLootItem(0.25F, "tomeofoblivion")
                )
        );
        SunlightChampionMob.privateLootTable.items.add(
                new LootItemList(
                        new ChanceLootItem(0.8F, "scrollofoblivion"),
                        new ChanceLootItem(0.3F, "tomeofoblivion")
                )
        );
        MoonlightDancerMob.privateLootTable.items.add(
                new LootItemList(
                        new ChanceLootItem(0.85F, "scrollofoblivion"),
                        new ChanceLootItem(0.35F, "tomeofoblivion")
                )
        );
        CrystalDragonHead.lootTable.items.add(
                new LootItem("codexofoblivion")
        );

        float damageTakenModifier = (float) config.getDamageTakenModifier() / 100;
        float knockbackGivenModifier = (float) config.getKnockbackGivenModifier() / 100;
        float enemySpawnRateModifier = (float) config.getEnemySpawnRateModifier() / 100;
        float enemySpawnCapModifier = (float) config.getEnemySpawnCapModifier() / 100;
        float raiderDamageModifier = (float) config.getRaiderDamageModifier() / 100;

        if (damageTakenModifier != 0) {
            for (int i = 0; i < GameDifficulty.values().length; i++) {
                GameDifficulty gameDifficulty = GameDifficulty.values()[i];
                try {
                    Field damageTakenModifierField = GameDifficulty.class.getDeclaredField("damageTakenModifier");
                    damageTakenModifierField.setAccessible(true);
                    damageTakenModifierField.set(gameDifficulty, damageTakenModifier);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (knockbackGivenModifier != 0) {
            for (int i = 0; i < GameDifficulty.values().length; i++) {
                GameDifficulty gameDifficulty = GameDifficulty.values()[i];
                try {
                    Field knockbackGivenModifierField = GameDifficulty.class.getDeclaredField("knockbackGivenModifier");
                    knockbackGivenModifierField.setAccessible(true);
                    knockbackGivenModifierField.set(gameDifficulty, knockbackGivenModifier);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (enemySpawnRateModifier != 0) {
            for (int i = 0; i < GameDifficulty.values().length; i++) {
                GameDifficulty gameDifficulty = GameDifficulty.values()[i];
                try {
                    Field enemySpawnRateModifierField = GameDifficulty.class.getDeclaredField("enemySpawnRateModifier");
                    enemySpawnRateModifierField.setAccessible(true);
                    enemySpawnRateModifierField.set(gameDifficulty, enemySpawnRateModifier);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (enemySpawnCapModifier != 0) {
            for (int i = 0; i < GameDifficulty.values().length; i++) {
                GameDifficulty gameDifficulty = GameDifficulty.values()[i];
                try {
                    Field enemySpawnCapModifierField = GameDifficulty.class.getDeclaredField("enemySpawnCapModifier");
                    enemySpawnCapModifierField.setAccessible(true);
                    enemySpawnCapModifierField.set(gameDifficulty, enemySpawnCapModifier);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (raiderDamageModifier != 0) {
            for (int i = 0; i < GameDifficulty.values().length; i++) {
                GameDifficulty gameDifficulty = GameDifficulty.values()[i];
                try {
                    Field raiderDamageModifierField = GameDifficulty.class.getDeclaredField("raiderDamageModifier");
                    raiderDamageModifierField.setAccessible(true);
                    raiderDamageModifierField.set(gameDifficulty, raiderDamageModifier);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Object[][] getBossHealthModifiers = getBossHealthModifiers();

        for (Object[] getBossHealthModifier : getBossHealthModifiers) {
            float modifier = (float) (100 + (int) getBossHealthModifier[0]) / 100;
            Class<Object> bossClass = (Class<Object>) getBossHealthModifier[1];
            String stringField = getBossHealthModifier.length < 3 ? "MAX_HEALTH" : (String) getBossHealthModifier[2];

            System.out.println("[Classes Mod] Modifying health of " + bossClass.getName());

            try {
                Field field = bossClass.getDeclaredField(stringField);

                field.setAccessible(true);

                MaxHealthGetter maxHealthGetter = (MaxHealthGetter) field.get(null);

                maxHealthGetter.set(GameDifficulty.CASUAL, (int) (maxHealthGetter.get(GameDifficulty.CASUAL) * modifier));
                maxHealthGetter.set(GameDifficulty.ADVENTURE, (int) (maxHealthGetter.get(GameDifficulty.ADVENTURE) * modifier));
                maxHealthGetter.set(GameDifficulty.CLASSIC, (int) (maxHealthGetter.get(GameDifficulty.CLASSIC) * modifier));
                maxHealthGetter.set(GameDifficulty.HARD, (int) (maxHealthGetter.get(GameDifficulty.HARD) * modifier));
                maxHealthGetter.set(GameDifficulty.BRUTAL, (int) (maxHealthGetter.get(GameDifficulty.BRUTAL) * modifier));

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Object[][] getBossHealthModifiers() {
        Config config = Config.getConfig();
        return new Object[][]{
                new Object[]{config.getHealthUnstableGelSlime(), UnstableGelSlime.class},
                new Object[]{config.getHealthEvilsProtector(), EvilsProtectorMob.class},
                new Object[]{config.getHealthQueenSpider(), QueenSpiderMob.class},
                new Object[]{config.getHealthVoidWizard(), VoidWizard.class},
                new Object[]{config.getHealthChieftain(), ChieftainMob.class},
                new Object[]{config.getHealthSwampGuardian(), SwampGuardianHead.class},
                new Object[]{config.getHealthAncientVulture(), AncientVultureMob.class},
                new Object[]{config.getHealthPirateCaptain(), PirateCaptainMob.class},
                new Object[]{config.getHealthReaper(), ReaperMob.class, "BASE_MAX_HEALTH"},
                new Object[]{config.getHealthReaperIncursion(), ReaperMob.class, "INCURSION_MAX_HEALTH"},
                new Object[]{config.getHealthTheCursedCrone(), TheCursedCroneMob.class, "BASE_MAX_HEALTH"},
                new Object[]{config.getHealthCryoQueen(), CryoQueenMob.class, "BASE_MAX_HEALTH"},
                new Object[]{config.getHealthCryoQueenIncursion(), CryoQueenMob.class, "INCURSION_MAX_HEALTH"},
                new Object[]{config.getHealthPestWarden(), PestWardenHead.class, "BASE_MAX_HEALTH"},
                new Object[]{config.getHealthPestWardenIncursion(), PestWardenHead.class, "INCURSION_MAX_HEALTH"},
                new Object[]{config.getHealthSageAndGrit(), FlyingSpiritsHead.class, "BASE_MAX_HEALTH"},
                new Object[]{config.getHealthSageAndGritIncursion(), FlyingSpiritsHead.class, "INCURSION_MAX_HEALTH"},
                new Object[]{config.getHealthFallenWizard(), FallenWizardMob.class},
                new Object[]{config.getHealthMotherSlime(), MotherSlimeMob.class},
                new Object[]{config.getHealthNightSwarmBat(), NightSwarmLevelEvent.class, "BAT_MAX_HEALTH"},
                new Object[]{config.getHealthSpiderEmpress(), SpiderEmpressMob.class},
                new Object[]{config.getHealthSunlightChampion(), SunlightChampionMob.class},
                new Object[]{config.getHealthMoonlightDancer(), MoonlightDancerMob.class},
                new Object[]{config.getHealthCrystalDragon(), CrystalDragonHead.class}
        };
    }

    public static HoverStateTextures[] class_principal_ability;
    public static HoverStateTextures[] class_principal_ability_unlocked;
    public static HoverStateTextures[] class_principal_ability_available;
    public static HoverStateTextures[] class_principal_ability_locked;

    public static HoverStateTextures[] class_ability;
    public static HoverStateTextures[] class_ability_unlocked;

    public static ButtonIcon[][] ability_level_icon;

    public void initResources() {
        int styles = GameInterfaceStyle.styles.size();
        class_principal_ability = new HoverStateTextures[styles];
        class_principal_ability_unlocked = new HoverStateTextures[styles];
        class_principal_ability_available = new HoverStateTextures[styles];
        class_principal_ability_locked = new HoverStateTextures[styles];

        class_ability = new HoverStateTextures[styles];
        class_ability_unlocked = new HoverStateTextures[styles];

        ability_level_icon = new ButtonIcon[styles][];

        for (int i = 0; i < styles; i++) {
            GameInterfaceStyle style = GameInterfaceStyle.styles.get(i);
            class_principal_ability[i] = new HoverStateTextures(style, "classprincipalability");
            class_principal_ability_unlocked[i] = new HoverStateTextures(style, "classprincipalability_unlocked");
            class_principal_ability_available[i] = new HoverStateTextures(style, "classprincipalability_available");
            class_principal_ability_locked[i] = new HoverStateTextures(style, "classprincipalability_locked");

            class_ability[i] = new HoverStateTextures(style, "classability");
            class_ability_unlocked[i] = new HoverStateTextures(style, "classability_unlocked");

            ability_level_icon[i] = new ButtonIcon[]{
                    new ButtonIcon(style, "level1"),
                    new ButtonIcon(style, "level2"),
                    new ButtonIcon(style, "level3"),
                    new ButtonIcon(style, "level4"),
                    new ButtonIcon(style, "level5"),
                    new ButtonIcon(style, "level6"),
                    new ButtonIcon(style, "level7"),
                    new ButtonIcon(style, "level8"),
                    new ButtonIcon(style, "level9"),
                    new ButtonIcon(style, "level10"),
                    new ButtonIcon(style, "level11"),
                    new ButtonIcon(style, "level12")
            };
        }
        RPGClassRegistry.loadAllResources();
    }

}
