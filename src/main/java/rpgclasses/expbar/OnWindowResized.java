package rpgclasses.expbar;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.window.GameWindow;
import necesse.gfx.forms.MainGameFormManager;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = MainGameFormManager.class, name = "onWindowResized", arguments = {GameWindow.class})
public class OnWindowResized {
    @Advice.OnMethodExit
    static void onExit(@Advice.This MainGameFormManager mainGameFormManager) {
        if (ExpBarManger.barForm != null) {
            ExpBarManger.updateExpBarPosition(mainGameFormManager);
        }
    }
}
