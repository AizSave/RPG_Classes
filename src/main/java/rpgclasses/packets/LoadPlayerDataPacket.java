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
import rpgclasses.expbar.ExpBarManger;

public class LoadPlayerDataPacket extends Packet {

    public final String name;
    public final int exp;
    public final String[] abilities;

    public LoadPlayerDataPacket(byte[] data) {
        super(data);
        PacketReader reader = new PacketReader(this);

        name = reader.getNextString();
        exp = reader.getNextInt();
        abilities = reader.getNextStringLong().split("-");
    }

    public LoadPlayerDataPacket(String name, PlayerData playerData) {
        this.name = name;
        this.exp = playerData.getBaseExp();
        this.abilities = playerData.classAbilitiesStringIDs.toArray(new String[0]);

        PacketWriter writer = new PacketWriter(this);

        writer.putNextString(name);
        writer.putNextInt(exp);
        writer.putNextStringLong(String.join("-", abilities));
    }

    @Override
    public void processClient(NetworkPacket packet, Client client) {
        PlayerMob player = client.getPlayer();
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        playerData.loadData(exp, abilities);
        playerData.updateBuffs(player, false);
        ExpBarManger.updateExpBar(playerData);
    }

    @Override
    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        PlayerData playerData = PlayerDataList.getCurrentPlayer(name);
        playerData.updateActiveBuffs();
        playerData.updateBuffs(client.playerMob, false);
        client.sendPacket(new LoadPlayerDataPacket(client.playerMob.playerName, playerData));
    }
}