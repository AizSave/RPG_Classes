package rpgclasses.packets;

import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.PlayerMob;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;

public class UpdateClientAbilitiesPacket extends Packet {

    public final String name;
    public final String[] abilities;

    public UpdateClientAbilitiesPacket(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);

        name = reader.getNextString();
        abilities = reader.getNextStringLong().split("-");
    }

    public UpdateClientAbilitiesPacket(String name, PlayerData playerData) {
        this.name = name;
        this.abilities = playerData.classAbilitiesStringIDs.toArray(new String[0]);

        PacketWriter writer = new PacketWriter(this);

        writer.putNextString(name);
        writer.putNextStringLong(String.join("-", abilities));
    }

    @Override
    public void processClient(NetworkPacket packet, Client client) {
        PlayerMob player = client.getPlayer();
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        playerData.loadData(abilities);
    }

    @Override
    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        PlayerData playerData = PlayerDataList.getCurrentPlayer(name);
        client.sendPacket(new UpdateClientAbilitiesPacket(client.playerMob.playerName, playerData));
    }
}