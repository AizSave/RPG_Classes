package rpgclasses.registry;

import aphorea.registry.AphModifiers;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import rpgclasses.RPG_Classes;
import rpgclasses.base.Ability;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.buffs.SummonsNerfBuff;
import rpgclasses.buffs.ability.*;
import rpgclasses.buffs.principalability.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static necesse.engine.registries.BuffRegistry.registerBuff;

public final class AbilityRegistry {
    public static final List<Ability> abilities = new ArrayList<>();

    public static void registerCore() {
        registerSubClasses();

        registerBasicAbilities();

        registerAbilities();
    }

    public static void registerSubClasses() {
        registerAbility(new Ability("warrior", 1, 3));
        registerBuff("warrior_0", new FighterBuff());

        registerAbility(new Ability("barbarian", 1, 3));
        registerBuff("barbarian_0", new BarbarianBuff());

        registerAbility(new Ability("assassin", 1, 3));
        registerBuff("assassin_0", new AssassinBuff());

        registerAbility(new Ability("shadow", 1, 3));
        registerBuff("shadow_0", new ShadowBuff());

        registerAbility(new Ability("sniper", 1, 3));
        registerBuff("sniper_0", new SniperBuff());

        registerAbility(new Ability("beastmaster", 1, 3));
        registerBuff("beastmaster_0", new BeastMasterBuff());

        registerAbility(new Ability("apprentice", 1, 3));
        registerBuff("apprentice_0", new ApprenticeBuff());

        registerAbility(new Ability("wizard", 1, 3));
        registerBuff("wizard_0", new WizardBuff());

        registerAbility(new Ability("summoner", 1, 3));
        registerBuff("summoner_0", new SummonerBuff());
        SummonsNerfBuff.evadeNerfSummonsBuffs.add("summoner_0");

        registerAbility(new Ability("priest", 1, 3));
        registerBuff("priest_0", new PriestBuff());

        registerAbility(new Ability("paladin", 1, 3));
        registerBuff("paladin_0", new PaladinBuff());
    }

    public static void registerBasicAbilities() {
        registerAbility(new Ability("endurance", 6, 1));
        registerBuff("endurance_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_HEALTH_FLAT, 5), new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 0.99F)));
        registerBuff("endurance_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_HEALTH_FLAT, 10), new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 0.98F)));
        registerBuff("endurance_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_HEALTH_FLAT, 15), new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 0.97F)));
        registerBuff("endurance_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_HEALTH_FLAT, 20), new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 0.96F)));
        registerBuff("endurance_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_HEALTH_FLAT, 30), new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 0.94F)));
        registerBuff("endurance_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_HEALTH_FLAT, 40), new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 0.92F)));

        registerAbility(new Ability("fortitude", 6, 1));
        registerBuff("fortitude_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ARMOR, 0.1F)));
        registerBuff("fortitude_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ARMOR, 0.2F)));
        registerBuff("fortitude_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ARMOR, 0.3F)));
        registerBuff("fortitude_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ARMOR, 0.4F)));
        registerBuff("fortitude_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ARMOR, 0.6F)));
        registerBuff("fortitude_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ARMOR, 0.8F)));

        registerAbility(new Ability("strength", 6, 1));
        registerBuff("strength_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, 0.03F), new ModifierValue<>(BuffModifiers.MINING_SPEED, 0.05F)));
        registerBuff("strength_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, 0.06F), new ModifierValue<>(BuffModifiers.MINING_SPEED, 0.1F)));
        registerBuff("strength_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, 0.09F), new ModifierValue<>(BuffModifiers.MINING_SPEED, 0.15F)));
        registerBuff("strength_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, 0.12F), new ModifierValue<>(BuffModifiers.MINING_SPEED, 0.20F)));
        registerBuff("strength_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, 0.18F), new ModifierValue<>(BuffModifiers.MINING_SPEED, 0.30F)));
        registerBuff("strength_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MELEE_DAMAGE, 0.24F), new ModifierValue<>(BuffModifiers.MINING_SPEED, 0.40F)));

        registerAbility(new Ability("recovery", 6, 1));
        registerBuff("recovery_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.HEALTH_REGEN, 0.1F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN, 0.05F)));
        registerBuff("recovery_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.HEALTH_REGEN, 0.2F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN, 0.1F)));
        registerBuff("recovery_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.HEALTH_REGEN, 0.3F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN, 0.15F)));
        registerBuff("recovery_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.HEALTH_REGEN, 0.4F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN, 0.2F)));
        registerBuff("recovery_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.HEALTH_REGEN, 0.6F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN, 0.3F)));
        registerBuff("recovery_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.HEALTH_REGEN, 0.8F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN, 0.4F)));

        registerAbility(new Ability("lethality", 6, 1));
        registerBuff("lethality_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.02F), new ModifierValue<>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.05F)));
        registerBuff("lethality_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.04F), new ModifierValue<>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.1F)));
        registerBuff("lethality_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.06F), new ModifierValue<>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.15F)));
        registerBuff("lethality_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.08F), new ModifierValue<>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.2F)));
        registerBuff("lethality_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.12F), new ModifierValue<>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.3F)));
        registerBuff("lethality_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.16F), new ModifierValue<>(BuffModifiers.MELEE_CRIT_DAMAGE, 0.4F)));

        registerAbility(new Ability("swiftness", 6, 1));
        registerBuff("swiftness_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SPEED, 0.03F), new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.03F)));
        registerBuff("swiftness_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SPEED, 0.06F), new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.06F)));
        registerBuff("swiftness_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SPEED, 0.09F), new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.09F)));
        registerBuff("swiftness_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SPEED, 0.12F), new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.12F)));
        registerBuff("swiftness_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SPEED, 0.18F), new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.18F)));
        registerBuff("swiftness_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SPEED, 0.24F), new ModifierValue<>(RPG_Classes.DODGE_CHANCE, 0.24F)));

        registerAbility(new Ability("quickhands", 6, 1));
        registerBuff("quickhands_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ATTACK_SPEED, 0.05F), new ModifierValue<>(BuffModifiers.THROWING_VELOCITY, 0.1F)));
        registerBuff("quickhands_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ATTACK_SPEED, 0.1F), new ModifierValue<>(BuffModifiers.THROWING_VELOCITY, 0.2F)));
        registerBuff("quickhands_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ATTACK_SPEED, 0.15F), new ModifierValue<>(BuffModifiers.THROWING_VELOCITY, 0.3F)));
        registerBuff("quickhands_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ATTACK_SPEED, 0.2F), new ModifierValue<>(BuffModifiers.THROWING_VELOCITY, 0.4F)));
        registerBuff("quickhands_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ATTACK_SPEED, 0.3F), new ModifierValue<>(BuffModifiers.THROWING_VELOCITY, 0.6F)));
        registerBuff("quickhands_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.ATTACK_SPEED, 0.4F), new ModifierValue<>(BuffModifiers.THROWING_VELOCITY, 0.8F)));

        registerAbility(new Ability("stealth", 6, 1));
        registerBuff("stealth_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.TARGET_RANGE, -0.05F)));
        registerBuff("stealth_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.TARGET_RANGE, -0.1F)));
        registerBuff("stealth_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.TARGET_RANGE, -0.15F)));
        registerBuff("stealth_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.TARGET_RANGE, -0.2F)));
        registerBuff("stealth_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.TARGET_RANGE, -0.3F)));
        registerBuff("stealth_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.TARGET_RANGE, -0.4F)));

        registerAbility(new Ability("huntersmark", 6, 1));
        registerBuffs("huntersmark", HuntersMarkBuff.class, 6);
        BuffRegistry.registerBuff("huntersmarkcooldown", new ShownCooldownBuff());

        registerAbility(new Ability("accuracy", 6, 1));
        registerBuff("accuracy_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, 0.03F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.05F)));
        registerBuff("accuracy_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, 0.06F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.1F)));
        registerBuff("accuracy_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, 0.09F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.15F)));
        registerBuff("accuracy_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, 0.12F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.20F)));
        registerBuff("accuracy_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, 0.18F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.30F)));
        registerBuff("accuracy_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.RANGED_DAMAGE, 0.24F), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.40F)));

        registerAbility(new Ability("precision", 6, 1));
        registerBuff("precision_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.02F), new ModifierValue<>(BuffModifiers.RANGED_CRIT_DAMAGE, 0.05F)));
        registerBuff("precision_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.04F), new ModifierValue<>(BuffModifiers.RANGED_CRIT_DAMAGE, 0.1F)));
        registerBuff("precision_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.06F), new ModifierValue<>(BuffModifiers.RANGED_CRIT_DAMAGE, 0.15F)));
        registerBuff("precision_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.08F), new ModifierValue<>(BuffModifiers.RANGED_CRIT_DAMAGE, 0.2F)));
        registerBuff("precision_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.12F), new ModifierValue<>(BuffModifiers.RANGED_CRIT_DAMAGE, 0.3F)));
        registerBuff("precision_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.16F), new ModifierValue<>(BuffModifiers.RANGED_CRIT_DAMAGE, 0.4F)));

        registerAbility(new Ability("manaspring", 6, 1));
        registerBuff("manaspring_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, 5), new ModifierValue<>(BuffModifiers.MANA_USAGE, -0.03F)));
        registerBuff("manaspring_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, 10), new ModifierValue<>(BuffModifiers.MANA_USAGE, -0.06F)));
        registerBuff("manaspring_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, 15), new ModifierValue<>(BuffModifiers.MANA_USAGE, -0.09F)));
        registerBuff("manaspring_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, 20), new ModifierValue<>(BuffModifiers.MANA_USAGE, -0.12F)));
        registerBuff("manaspring_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, 30), new ModifierValue<>(BuffModifiers.MANA_USAGE, -0.18F)));
        registerBuff("manaspring_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, 40), new ModifierValue<>(BuffModifiers.MANA_USAGE, -0.24F)));

        registerAbility(new Ability("arcanepower", 6, 1));
        registerBuff("arcanepower_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAGIC_DAMAGE, 0.03F), new ModifierValue<>(BuffModifiers.MANA_REGEN, 0.05F)));
        registerBuff("arcanepower_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAGIC_DAMAGE, 0.06F), new ModifierValue<>(BuffModifiers.MANA_REGEN, 0.1F)));
        registerBuff("arcanepower_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAGIC_DAMAGE, 0.09F), new ModifierValue<>(BuffModifiers.MANA_REGEN, 0.15F)));
        registerBuff("arcanepower_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAGIC_DAMAGE, 0.12F), new ModifierValue<>(BuffModifiers.MANA_REGEN, 0.20F)));
        registerBuff("arcanepower_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAGIC_DAMAGE, 0.18F), new ModifierValue<>(BuffModifiers.MANA_REGEN, 0.30F)));
        registerBuff("arcanepower_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAGIC_DAMAGE, 0.24F), new ModifierValue<>(BuffModifiers.MANA_REGEN, 0.40F)));

        registerAbility(new Ability("arcaneprecision", 6, 1));
        registerBuff("arcaneprecision_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.02F), new ModifierValue<>(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.05F)));
        registerBuff("arcaneprecision_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.04F), new ModifierValue<>(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.1F)));
        registerBuff("arcaneprecision_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.06F), new ModifierValue<>(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.15F)));
        registerBuff("arcaneprecision_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.08F), new ModifierValue<>(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.2F)));
        registerBuff("arcaneprecision_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.12F), new ModifierValue<>(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.3F)));
        registerBuff("arcaneprecision_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.CRIT_CHANCE, 0.16F), new ModifierValue<>(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.4F)));

        registerAbility(new Ability("abundantarmy", 6, 3));
        registerBuff("abundantarmy_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1)));
        registerBuff("abundantarmy_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 2)));
        registerBuff("abundantarmy_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 3)));
        registerBuff("abundantarmy_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 4)));
        registerBuff("abundantarmy_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 5)));
        registerBuff("abundantarmy_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 6)));

        registerAbility(new Ability("leadership", 12, 1));
        registerBuff("leadership_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.05F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.05F)));
        registerBuff("leadership_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.1F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.1F)));
        registerBuff("leadership_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.15F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.15F)));
        registerBuff("leadership_3", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.2F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.2F)));
        registerBuff("leadership_4", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.3F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.3F)));
        registerBuff("leadership_5", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.4F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.4F)));
        registerBuff("leadership_6", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.5F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.5F)));
        registerBuff("leadership_7", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.6F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.6F)));
        registerBuff("leadership_8", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.7F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.7F)));
        registerBuff("leadership_9", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.8F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.8F)));
        registerBuff("leadership_10", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.9F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.9F)));
        registerBuff("leadership_11", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 1F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 1F)));

        registerAbility(new Ability("healing", 12, 1));
        registerBuff("healing_0", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.05F)));
        registerBuff("healing_1", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.1F)));
        registerBuff("healing_2", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.15F)));
        registerBuff("healing_3", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.2F)));
        registerBuff("healing_4", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.3F)));
        registerBuff("healing_5", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.4F)));
        registerBuff("healing_6", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.5F)));
        registerBuff("healing_7", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.6F)));
        registerBuff("healing_8", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.7F)));
        registerBuff("healing_9", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.8F)));
        registerBuff("healing_10", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 0.9F)));
        registerBuff("healing_11", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING, 1F)));

        registerAbility(new Ability("graceful", 6, 1));
        registerBuff("graceful_0", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING_GRACE, 0.05F)));
        registerBuff("graceful_1", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING_GRACE, 0.1F)));
        registerBuff("graceful_2", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING_GRACE, 0.15F)));
        registerBuff("graceful_3", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING_GRACE, 0.2F)));
        registerBuff("graceful_4", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING_GRACE, 0.3F)));
        registerBuff("graceful_5", new SimpleClassBuff(new ModifierValue<>(AphModifiers.MAGIC_HEALING_GRACE, 0.4F)));

    }

    public static void registerAbilities() {
        registerAbility(new Ability("armoredflesh", 3, 1, 1, 2));
        registerBuffs("armoredflesh", ArmoredFleshBuff.class, 3);

        registerAbility(new Ability("frenziedguard", 3, 1, 1, 2));
        registerBuffs("frenziedguard", FrenziedGuardBuff.class, 3);
        registerBuffs("frenziedguardcharge", FrenziedGuardBuff.FrenziedGuardChargeBuff.class, 3);

        registerAbility(new Ability("bloodhaste", 3, 1, 1, 2));
        registerBuffs("bloodhaste", BloodHasteBuff.class, 3);

        registerAbility(new Ability("ironwill", 3, 1, 1, 2));
        registerBuff("ironwill_0", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.5F)));
        registerBuff("ironwill_1", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 1F)));
        registerBuff("ironwill_2", new SimpleClassBuff(new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 2F)));

        registerAbility(new Ability("relentlessstrikes", 3, 1, 1, 2));
        registerBuffs("relentlessstrikes", RelentlessStrikesBuff.class, 3);
        registerBuffs("relentlessstrikescharge", RelentlessStrikesBuff.RelentlessStrikesChargeBuff.class, 3);

        registerAbility(new Ability("relentlesschase", 3, 1, 1, 2));
        registerBuffs("relentlesschase", RelentlessChaseBuff.class, 3);
        registerBuffs("relentlesschasecharge", RelentlessChaseBuff.RelentlessChaseChargeBuff.class, 3);

        registerAbility(new Ability("relentlessevasion", 3, 1, 1, 2));
        registerBuffs("relentlessevasion", RelentlessEvasionBuff.class, 3);
        registerBuffs("relentlessevasioncharge", RelentlessEvasionBuff.RelentlessEvasionChargeBuff.class, 3);

        registerAbility(new Ability("criticaledge", 3, 1, 1, 2));
        registerBuffs("criticaledge", CriticalEdgeBuff.class, 3);

        registerAbility(new Ability("evasiveinstinct", 3, 1, 1, 2));
        registerBuffs("evasiveinstinct", EvasiveInstinctBuff.class, 3);
        registerBuffs("evasiveinstinctcharge", EvasiveInstinctBuff.EvasiveInstinctChargeBuff.class, 3);

        registerAbility(new Ability("evasivelethality", 3, 1, 1, 2));
        registerBuffs("evasivelethality", EvasiveLethalityBuff.class, 3);
        registerBuffs("evasivelethalitycharge", EvasiveLethalityBuff.EvasiveLethalityChargeBuff.class, 3);

        registerAbility(new Ability("plasmagrenade", 3, 1, 1, 2));
        registerBuffs("plasmagrenade", PlasmaGrenadeBuff.class, 3);

        registerAbility(new Ability("bowmastery", 3, 1, 1, 2));
        registerBuffs("bowmastery", BowMasteryBuff.class, 3);
        BuffRegistry.registerBuff("bowmasterycooldown", new ShownCooldownBuff());

        registerAbility(new Ability("magicranger", 3, 1, 1, 2));
        registerBuffs("magicranger", MagicRangerBuff.class, 3);

        registerAbility(new Ability("firewand", 4, 1, 1, 2, 3));
        registerBasicBuffs("firewand", 4);
        BuffRegistry.registerBuff("firewandcooldown", new ShownCooldownBuff());

        registerAbility(new Ability("icewand", 4, 1, 1, 2, 3));
        registerBasicBuffs("icewand", 4);
        BuffRegistry.registerBuff("icewandcooldown", new ShownCooldownBuff());

        registerAbility(new Ability("thunderwand", 4, 1, 1, 2, 3));
        registerBasicBuffs("thunderwand", 4);
        BuffRegistry.registerBuff("thunderwandcooldown", new ShownCooldownBuff());

        registerAbility(new Ability("overcharge", 3, 1, 1, 2));
        registerBuffs("overcharge", OverchargeBuff.class, 3);

        registerAbility(new Ability("firereaction", 3, 1, 1, 2));
        registerBuffs("firereaction", FireReactionBuff.class, 3);
        BuffRegistry.registerBuff("firereactioncooldown", new ShownCooldownBuff());

        registerAbility(new Ability("icereaction", 3, 1, 1, 2));
        registerBuffs("icereaction", IceReactionBuff.class, 3);
        BuffRegistry.registerBuff("icereactioncooldown", new ShownCooldownBuff());

        registerAbility(new Ability("thunderreaction", 3, 1, 1, 2));
        registerBuffs("thunderreaction", ThunderReactionBuff.class, 3);
        BuffRegistry.registerBuff("thunderreactioncooldown", new ShownCooldownBuff());

        registerAbility(new Ability("healingarea", 4, 1, 1, 2, 3));
        registerBuffs("healingarea", HealingAreaBuff.class, 4);

        registerAbility(new Ability("sacredjudgment", 3, 1, 1, 2));
        registerBuffs("sacredjudgment", SacredJudgmentBuff.class, 3);
        registerBuffs("sacredjudgmentcharge", SacredJudgmentBuff.SacredJudgmentChargeBuff.class, 3);
    }

    public static void registerAbility(Ability ability) {
        abilities.add(ability);
    }

    public static Ability getAbility(String stringID) {
        for (Ability ability : abilities) {
            if (Objects.equals(ability.stringID, stringID)) {
                return ability;
            }
        }
        throw new RuntimeException(stringID + " ability not registered");
    }

    public static boolean hasAbility(String stringID) {
        for (Ability ability : abilities) {
            if (Objects.equals(ability.stringID, stringID)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Buff> void registerBuffs(String stringID, Class<T> buffClass, int levels) {
        try {
            for (int i = 0; i < levels; i++) {
                Constructor<T> constructor = buffClass.getDeclaredConstructor(int.class);
                constructor.setAccessible(true);
                T buffInstance = constructor.newInstance(i);
                registerBuff(stringID + "_" + i, buffInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerBasicBuffs(String stringID, int levels) {
        try {
            for (int i = 0; i < levels; i++) {
                registerBuff(stringID + "_" + i, new SimpleClassBuff());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
