    package rpgclasses.buffs.ability;

    import aphorea.packets.AphSingleAreaShowPacket;
    import aphorea.utils.area.AphArea;
    import aphorea.utils.area.AphAreaList;
    import necesse.entity.mobs.Mob;
    import necesse.entity.mobs.buffs.ActiveBuff;
    import necesse.entity.mobs.buffs.BuffEventSubscriber;
    import rpgclasses.buffs.SimpleClassBuff;

    import java.awt.*;

    public class HealingAreaBuff extends SimpleClassBuff {
        public int abilityLevel;
        public AphAreaList areaList = new AphAreaList(
                new AphArea(300, 0.5F, new Color(0, 255, 0))
        );
        public int timer;
        public int executeTimer;

        public HealingAreaBuff(int abilityLevel) {
            super();
            this.abilityLevel = abilityLevel;
        }

        public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        }

        @Override
        public void onLoad(ActiveBuff buff) {
            timer = 0;
            executeTimer = 240 - 60 * abilityLevel;
        }

        @Override
        public void serverTick(ActiveBuff buff) {
            timer++;
            if (timer >= executeTimer) {
                timer = 0;
                Mob mob = buff.owner;
                AphArea area = areaList.areas[0];
                mob.getServer().network.sendToAllClients(new AphSingleAreaShowPacket(mob.x, mob.y, area.range, area.colors[0]));
                areaList.areas[0].setHealingArea((int) (mob.getMaxHealth() * 0.02F));
                areaList.executeAreas(mob);
            }
        }
    }