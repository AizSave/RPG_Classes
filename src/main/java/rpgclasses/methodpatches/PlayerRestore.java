package rpgclasses.methodpatches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;
import rpgclasses.data.PlayerDataList;

@ModMethodPatch(target = PlayerMob.class, name = "restore", arguments = {})
public class PlayerRestore {

    @Advice.OnMethodExit
    static void onExit(@Advice.This PlayerMob playerMob) {
        PlayerDataList.getCurrentPlayer(playerMob).updateBuffs(playerMob);
    }

}