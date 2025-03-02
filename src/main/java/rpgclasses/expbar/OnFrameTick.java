package rpgclasses.expbar;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.gfx.forms.MainGameFormManager;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = MainGameFormManager.class, name = "frameTick", arguments = {TickManager.class})
public class OnFrameTick {
    @Advice.OnMethodExit
    static void onExit(@Advice.This MainGameFormManager mainGameFormManager) {
        ExpBarManger.barForm.setHidden(mainGameFormManager.toolbar.isHidden() || !mainGameFormManager.isInventoryHidden());
    }
}