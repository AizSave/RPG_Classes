package rpgclasses.registry;

import rpgclasses.base.RPGClass;
import rpgclasses.classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RPGClassRegistry {
    public static final List<RPGClass> RPGClasses = new ArrayList<>();

    public static RPGClass FIGHTER;
    public static RPGClass ROGUE;
    public static RPGClass RANGER;
    public static RPGClass MAGE;
    public static RPGClass SUMMONER;
    public static RPGClass HEALER;

    public static void registerCore() {
        RPGClassRegistry.registerClass(FIGHTER = new Fighter());
        RPGClassRegistry.registerClass(ROGUE = new Rogue());
        RPGClassRegistry.registerClass(RANGER = new Ranger());
        RPGClassRegistry.registerClass(MAGE = new Sorcerer());
        RPGClassRegistry.registerClass(SUMMONER = new Summoner());
        RPGClassRegistry.registerClass(HEALER = new Healer());
    }

    public static void registerClass(RPGClass rpgclass) {
        RPGClasses.add(rpgclass);
    }

    public static RPGClass getClass(String stringID) {
        for (RPGClass rpgClass : RPGClasses) {
            if (Objects.equals(rpgClass.stringID, stringID)) {
                return rpgClass;
            }
        }
        throw new RuntimeException(stringID + " class not registered");
    }

    public static void loadAllResources() {
        for (RPGClass rpgClass : RPGClasses) {
            rpgClass.loadResources();
        }
    }
}
