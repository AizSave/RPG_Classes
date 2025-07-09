package rpgclasses.expbar;

import necesse.gfx.forms.MainGameFormManager;
import rpgclasses.data.PlayerData;

public class ExpBarManger {
    public static BarForm barForm = null;
    public static float barPercent;
    public static MainGameFormManager mainGameFormManager = null;
    public static boolean movedByInv = false;
    public static boolean anyProgressBars = false;
    public static boolean vertical = false;

    public static void updateExpBar(PlayerData playerData) {
        updateExpBar(playerData.getExpActual(), playerData.getExpNext());
    }

    public static void updateExpBar(int expActual, int expNext) {
        barPercent = (float) expActual / expNext;
    }

    public static void updateExpBarPosition(MainGameFormManager mainGameFormManager) {
        if (mainGameFormManager.inventory.isHidden()) {
            vertical = false;
            ExpBarManger.barForm.setPosition(mainGameFormManager.toolbar.getX(), mainGameFormManager.toolbar.getY() - 17);
            ExpBarManger.barForm.setWidth(408);
            ExpBarManger.barForm.setHeight(6);
        } else {
            vertical = true;
            ExpBarManger.barForm.setPosition(mainGameFormManager.crafting.getX() + mainGameFormManager.crafting.getWidth() + 11, mainGameFormManager.crafting.getY());
            ExpBarManger.barForm.setWidth(6);
            ExpBarManger.barForm.setHeight(mainGameFormManager.crafting.getHeight());
        }
    }
}
