package rpgclasses.methodpatches.playerdata;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.save.LoadData;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;

@ModMethodPatch(target = PlayerMob.class, name = "applyLoadData", arguments = {LoadData.class})
public class LoadPlayerData {

    @Advice.OnMethodExit
    static void onExit(@Advice.This PlayerMob player, @Advice.Argument(0) LoadData loadData) {
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);
        playerData.loadData(loadData);
        playerData.updateBuffs(player);
    }

}
