package rpgclasses.methodpatches.playerdata;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.client.Client;
import necesse.engine.network.client.ClientClient;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;
import rpgclasses.data.PlayerDataList;
import rpgclasses.packets.LoadPlayerDataPacket;

@ModMethodPatch(target = ClientClient.class, name = "applySpawned", arguments = {int.class})
public class InitPlayerData {

    @Advice.OnMethodExit
    public static void onExit(@Advice.FieldValue(value = "client") Client client) {
        if (client.getPlayer() != null) {
            PlayerMob player = client.getPlayer();

            if (player != null) {
                PlayerDataList.initPlayer(player);
                client.network.sendPacket(new LoadPlayerDataPacket(player.playerName, PlayerDataList.getCurrentPlayer(player)));
            }
        }
    }
}
