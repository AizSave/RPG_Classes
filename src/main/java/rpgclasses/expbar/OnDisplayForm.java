package rpgclasses.expbar;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.gfx.forms.MainGameFormManager;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = MainGameFormManager.class, name = "setup", arguments = {})
public class OnDisplayForm {
    @Advice.OnMethodExit
    static void onExit(@Advice.This MainGameFormManager mainGameFormManager) {
        ExpBarManger.mainGameFormManager = mainGameFormManager;
        ExpBarManger.barForm = mainGameFormManager.addComponent(new BarForm("expbar", 408, 6));
        ExpBarManger.updateExpBarPosition(mainGameFormManager);
    }
}