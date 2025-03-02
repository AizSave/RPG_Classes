package rpgclasses.expbar;

import necesse.gfx.forms.MainGameFormManager;
import rpgclasses.data.PlayerData;

public class ExpBarManger {
    public static BarForm barForm = null;
    public static float barPercent;
    public static MainGameFormManager mainGameFormManager = null;

    public static void updateExpBar(PlayerData playerData) {
        updateExpBar(playerData.getExpActual(), playerData.getExpNext());
    }

    public static void updateExpBar(int expActual, int expNext) {
        barPercent = (float) expActual / expNext;
    }
}
