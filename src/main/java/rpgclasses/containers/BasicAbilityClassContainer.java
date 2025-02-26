package rpgclasses.containers;

import necesse.engine.Settings;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.forms.components.FormContentBox;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameFont.FontOptions;
import necesse.gfx.gameFont.GameFontHandler;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.ui.GameInterfaceStyle;
import rpgclasses.RPG_Classes;
import rpgclasses.base.ClassAbility;
import rpgclasses.data.PlayerData;

import java.awt.*;

public class BasicAbilityClassContainer extends FormContentBox {
    public final PlayerData playerData;
    public final ClassAbility classAbility;

    public BasicAbilityClassContainer(int x, int y, int width, int height, PlayerData playerData, ClassAbility classAbility) {
        super(x, y, width, height);
        this.playerData = playerData;
        this.classAbility = classAbility;
    }

    @Override
    public void draw(TickManager tickManager, PlayerMob perspective, Rectangle renderBox) {
        GameFontHandler font = FontManager.bit;

        int style = GameInterfaceStyle.styles.indexOf(Settings.UI);

        GameTexture slotTexture = playerData.classAbilitiesStringIDs.contains(classAbility.getStringID()) ?
                RPG_Classes.class_ability_unlocked[style].active : RPG_Classes.class_ability[style].active;

        slotTexture.initDraw().draw(this.getX(), this.getY());

        int lastLevelAbility = Math.max(playerData.getHighestLevelAbility(classAbility.ability.stringID), 0);

        FontOptions titleOptions = new FontOptions(18).defaultColor(Settings.UI.activeTextColor);
        String titleText = classAbility.getDisplayName();
        font.drawString(this.getX() + 25, this.getY() + 13, titleText, titleOptions);

        FontOptions descriptionOptions = new FontOptions(12).defaultColor(Settings.UI.activeTextColor);
        String descriptionText = Localization.translate("abilitydescription", classAbility.ability.stringID + "_" + lastLevelAbility);
        String[] descriptionSplit = descriptionText.trim().split("\n");
        for (int i = 0; i < descriptionSplit.length; i++) {
            String textLine = descriptionSplit[i];
            if (!textLine.isEmpty()) {
                font.drawString(this.getX() + 25, this.getY() + 43 + 18 * i, textLine, descriptionOptions);
            }
        }

        super.draw(tickManager, perspective, renderBox);
    }

}
