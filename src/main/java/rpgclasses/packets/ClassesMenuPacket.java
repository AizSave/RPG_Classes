package rpgclasses.packets;

import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.packet.PacketOpenContainer;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.ContainerRegistry;
import rpgclasses.RPG_Classes;
import rpgclasses.data.PlayerDataList;

public class ClassesMenuPacket extends Packet {
    public ClassesMenuPacket(byte[] data) {
        super(data);
    }

    public ClassesMenuPacket() {
    }

    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        if (client.checkHasRequestedSelf() && !client.isDead()) {
            client.sendPacket(new UpdateClientDataPacket(client.playerMob.playerName, PlayerDataList.getCurrentPlayer(client.playerMob)));

            client.checkSpawned();
            PacketOpenContainer p = new PacketOpenContainer(RPG_Classes.CLASSES_CONTAINER);
            ContainerRegistry.openAndSendContainer(client, p);
        }
    }
}