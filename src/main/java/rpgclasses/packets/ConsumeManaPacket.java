package rpgclasses.packets;

import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.client.ClientClient;
import necesse.engine.network.packet.PacketRequestPlayerData;
import necesse.entity.mobs.PlayerMob;

public class ConsumeManaPacket extends Packet {
    public final int slot;
    public final float manaCost;

    public ConsumeManaPacket(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);
        this.slot = reader.getNextByteUnsigned();
        this.manaCost = reader.getNextFloat();
    }

    public ConsumeManaPacket(int slot, float manaCost) {
        this.slot = slot;
        this.manaCost = manaCost;
        PacketWriter writer = new PacketWriter(this);
        writer.putNextByteUnsigned(slot);
        writer.putNextFloat(manaCost);
    }

    public void processClient(NetworkPacket packet, Client client) {
        if (client.getLevel() != null) {
            ClientClient target = client.getClient(this.slot);
            if (target != null && target.isSamePlace(client.getLevel())) {
                applyToPlayer(target.playerMob, manaCost);
            } else {
                client.network.sendPacket(new PacketRequestPlayerData(this.slot));
            }

        }
    }

    public static void applyToPlayer(PlayerMob player, float manaCost) {
        player.useMana(manaCost, player.isServerClient() ? player.getServerClient() : null);
    }
}
