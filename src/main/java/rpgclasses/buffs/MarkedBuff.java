package rpgclasses.buffs;

import necesse.engine.GlobalData;
import necesse.engine.network.client.Client;
import necesse.engine.state.MainGame;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.gfx.gameFont.FontManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MarkedBuff extends Buff {
    public static Map<Integer, Integer> clientMarked = new HashMap<>();

    public MarkedBuff() {
        this.isVisible = true;
        this.isImportant = true;
        this.canCancel = false;
        this.shouldSave = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        if (buff.owner.isServer()) {
            clientMarked.put(buff.owner.getUniqueID(), buff.getAttacker() == null ? -1 : buff.getAttacker().getAttackerUniqueID());
        }
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        float d = (float) buff.owner.getSelectBox().width / 2;

        Client client = ((MainGame) GlobalData.getCurrentState()).getClient();
        Color color = isMarked(client.getPlayer(), buff.owner) ? new Color(255, 0, 0) : new Color(0, 255, 255);
        int particles = (int) (Math.PI * d / 2);
        for (int i = 0; i < particles; i++) {
            float angle = (float) i / particles * 360;
            float dx = (float) Math.sin(Math.toRadians(angle)) * d;
            float dy = (float) Math.cos(Math.toRadians(angle)) * d / 2;
            buff.owner.getLevel().entityManager.addParticle(buff.owner.x + dx, buff.owner.y + dy, new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC).next()).color(color).height(0).lifeTime(100);
        }

    }

    public static boolean isMarked(Mob attacker, Mob target) {
        if (attacker == null || target == null) {
            return false;
        }
        if (!target.buffManager.hasBuff("markedbuff")) {
            return false;
        }
        ActiveBuff buff = target.buffManager.getBuff("markedbuff");
        if (attacker.isServer()) {
            Attacker buffAttacker = buff.getAttacker();
            if (buffAttacker == null) {
                return false;
            }
            Mob buffAttackerMob = buffAttacker.getAttackOwner();
            return buffAttackerMob != null && buffAttackerMob == attacker;
        } else {
            int marker = clientMarked.getOrDefault(target.getUniqueID(), -1);
            return marker != -1 && marker == attacker.getUniqueID();
        }
    }

    @Override
    public void drawIcon(int x, int y, ActiveBuff buff) {
        super.drawIcon(x, y, buff);
        if (buff.getAttacker() != null && buff.getAttacker().getAttackOwner() != null) {
            String text = buff.getAttacker().getAttackOwner().getDisplayName();
            int width = FontManager.bit.getWidthCeil(text, durationFontOptions);
            FontManager.bit.drawString((float) (x + 16 - width / 2), (float) (y + 44), text, durationFontOptions);
        }
    }
}
