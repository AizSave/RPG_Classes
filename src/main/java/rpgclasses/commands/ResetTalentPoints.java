package rpgclasses.commands;

import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.ServerClientParameterHandler;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.PlayerMob;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.packets.UpdateClientDataPacket;

public class ResetTalentPoints extends ModularChatCommand {

    public ResetTalentPoints() {
        super("resettalentpoints", "Reset Talent Points from a player", PermissionLevel.ADMIN, true,
                new CmdParameter("player", new ServerClientParameterHandler(true, false), true)
        );
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog commandLog) {
        String author = serverClient != null ? serverClient.playerMob.getDisplayName() : "Unknown";

        ServerClient target = (ServerClient) args[0];

        PlayerMob player = target.playerMob;
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        playerData.classAbilitiesStringIDs.clear();
        playerData.classActiveAbilitiesStringIDs.clear();
        playerData.updateBuffs(player);

        target.sendChatMessage(new LocalMessage("classmessage", "talentpointsrestarted"));

        target.sendPacket(new UpdateClientDataPacket(target.playerMob.playerName, PlayerDataList.getCurrentPlayer(target.playerMob)));

        commandLog.add(String.format("%s's Talent Points restarted by %s", player.getDisplayName(), author));
    }
}