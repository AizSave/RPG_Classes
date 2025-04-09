package rpgclasses.commands;

import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.commands.parameterHandlers.ServerClientParameterHandler;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.PlayerMob;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.packets.UpdateClientDataPacket;

public class ModExp extends ModularChatCommand {

    public ModExp() {
        super("modexp", "Add or remove experience from a player", PermissionLevel.ADMIN, true,
                new CmdParameter("player", new ServerClientParameterHandler(true, false), true),
                new CmdParameter("amount", new IntParameterHandler(), false)
        );
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog commandLog) {
        String author = serverClient != null ? serverClient.playerMob.getDisplayName() : "Unknown";

        ServerClient target = (ServerClient) args[0];

        if (target == null) {
            if (serverClient != null) {
                target = serverClient;
            } else {
                commandLog.add("Error: No player specified and no client context available. (Author: " + author + ")");
                return;
            }
        }

        int amount = (int) args[1];

        PlayerMob player = target.playerMob;
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);

        int previousExp = playerData.getExp();
        playerData.modExp(target, amount);
        commandLog.add(String.format("%s's experience modified by %d (%d -> %d) by %s", player.getDisplayName(), amount, previousExp, playerData.getExp(), author));

        target.sendPacket(new UpdateClientDataPacket(target.playerMob.playerName, PlayerDataList.getCurrentPlayer(target.playerMob)));
    }
}