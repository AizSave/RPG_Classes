package rpgclasses.expbar;

import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.gfx.forms.MainGameFormManager;
import net.bytebuddy.asm.Advice;

import java.util.Collection;

@ModMethodPatch(target = MainGameFormManager.class, name = "frameTick", arguments = {TickManager.class})
public class OnFrameTick {
    public static int invOpenMovement;

    @Advice.OnMethodExit
    static void onExit(@Advice.This MainGameFormManager mainGameFormManager) {
        boolean anyUpdate = false;
        if (mainGameFormManager.isInventoryHidden() == ExpBarManger.movedByInv) {
            ExpBarManger.movedByInv = !ExpBarManger.movedByInv;
            anyUpdate = true;
        }
        boolean newAnyProgressBars = anyProgressBars(EventStatusBarManager.getStatusBars());
        if (ExpBarManger.anyProgressBars != newAnyProgressBars) {
            anyUpdate = true;
            ExpBarManger.anyProgressBars = newAnyProgressBars;
        }
        if (anyUpdate) {
            ExpBarManger.updateExpBarPosition(mainGameFormManager);
        }
        ExpBarManger.barForm.setHidden(mainGameFormManager.toolbar.isHidden() || (mainGameFormManager.inventory.isHidden() && newAnyProgressBars));
    }

    public static boolean anyProgressBars(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return !((Collection<?>) iterable).isEmpty();
        }

        for (Object ignored : iterable) {
            return true;
        }
        return false;
    }
}
