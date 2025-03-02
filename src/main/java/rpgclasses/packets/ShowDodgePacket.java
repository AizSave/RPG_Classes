package rpgclasses.packets;

import necesse.engine.localization.Localization;
import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.gfx.gameFont.FontOptions;
import necesse.level.maps.hudManager.floatText.UniqueFloatText;

import java.awt.*;

public class ShowDodgePacket extends Packet {
    public final int x;
    public final int y;

    public ShowDodgePacket(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);
        this.x = reader.getNextInt();
        this.y = reader.getNextInt();
    }

    public ShowDodgePacket(int x, int y) {
        this.x = x;
        this.y = y;

        PacketWriter writer = new PacketWriter(this);
        writer.putNextInt(x);
        writer.putNextInt(y);
    }

    public void processClient(NetworkPacket packet, Client client) {
        UniqueFloatText text = new UniqueFloatText(x, y - 20, Localization.translate("message", "dodge"), (new FontOptions(16)).outline().color(new Color(100, 200, 100)), "dodge") {
            public int getAnchorX() {
                return x;
            }

            public int getAnchorY() {
                return y - 20;
            }
        };
        client.getLevel().hudManager.addElement(text);
    }
}
