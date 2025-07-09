package rpgclasses.buffs;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;

import java.util.ArrayList;
import java.util.List;

public class SummonsNerfBuff extends SimpleClassBuff {
    public static List<String> evadeNerfSummonsBuffs = new ArrayList<>();
    public static String stringID = "summonsnerfbuff";

    public SummonsNerfBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, -0.25F).max(0F).apply(buff);
    }
}
