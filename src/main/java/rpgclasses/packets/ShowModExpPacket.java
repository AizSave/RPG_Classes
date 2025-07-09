package rpgclasses.packets;

import necesse.engine.localization.Localization;
import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.util.GameRandom;
import necesse.entity.particle.Particle;
import necesse.entity.particle.fireworks.FireworksExplosion;
import necesse.entity.particle.fireworks.FireworksRocketParticle;
import necesse.gfx.gameFont.FontOptions;
import necesse.inventory.item.placeableItem.FireworkPlaceableItem;
import necesse.level.maps.Level;
import necesse.level.maps.hudManager.floatText.UniqueFloatText;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.expbar.ExpBarManger;

import java.awt.*;

public class ShowModExpPacket extends Packet {
    public final int x;
    public final int y;
    public final int exp;
    public final boolean levelUp;

    public ShowModExpPacket(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);
        this.x = reader.getNextInt();
        this.y = reader.getNextInt();
        this.exp = reader.getNextInt();
        this.levelUp = reader.getNextBoolean();
    }

    public ShowModExpPacket(int x, int y, int exp, boolean levelUp) {
        this.x = x;
        this.y = y;
        this.exp = exp;
        this.levelUp = levelUp;

        PacketWriter writer = new PacketWriter(this);
        writer.putNextInt(x);
        writer.putNextInt(y);
        writer.putNextInt(exp);
        writer.putNextBoolean(levelUp);
    }

    public void processClient(NetworkPacket packet, Client client) {
        if (exp != 0) {
            UniqueFloatText text = new UniqueFloatText(x, y - 20, (exp > 0 ? "+" + exp : exp) + " " + Localization.translate("message", "xp") + (levelUp ? " - " + Localization.translate("message", "levelup") : ""), (new FontOptions(16)).outline().color(exp > 0 ? new Color(100, 200, 100) : new Color(200, 100, 100)), "expmod") {
                public int getAnchorX() {
                    return x;
                }

                public int getAnchorY() {
                    return y - 20;
                }
            };
            Level level = client.getLevel();
            level.hudManager.addElement(text);

            if (levelUp) {
                level.entityManager.addParticle(new FireworksRocketParticle(level, x, y, 600, 300, getExplosion(GameRandom.globalRandom), GameRandom.globalRandom), true, Particle.GType.CRITICAL);
            }

            PlayerData playerData = PlayerDataList.getCurrentPlayer(client.getPlayer());
            ExpBarManger.updateExpBar(playerData);
        }
    }

    public static FireworksExplosion getExplosion(GameRandom random) {
        FireworksExplosion explosion = new FireworksExplosion(null);
        FireworkPlaceableItem.FireworksShape.Star.explosionModifier.play(explosion, 250, random);
        FireworkPlaceableItem.FireworkColor.Confetti.explosionModifier.accept(explosion);
        FireworkPlaceableItem.FireworkCrackle.Crackle.explosionModifier.accept(explosion);
        return explosion;
    }
}