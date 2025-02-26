package rpgclasses.methodpatches.playerdata;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.save.SaveData;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;

@ModMethodPatch(target = PlayerMob.class, name = "addSaveData", arguments = {SaveData.class})
public class SavePlayerData {

    @Advice.OnMethodExit
    static void onExit(@Advice.This PlayerMob playerMob, @Advice.Argument(0) SaveData saveData) {
        PlayerData player = PlayerDataList.getCurrentPlayer(playerMob);
        player.saveData(saveData);
    }

}