package rpgclasses.containers;

import necesse.engine.Settings;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameBackground;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameFont.FontOptions;
import necesse.gfx.gameFont.GameFontHandler;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.GameTooltipManager;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.TooltipLocation;
import necesse.gfx.ui.GameInterfaceStyle;
import necesse.gfx.ui.HoverStateTextures;
import rpgclasses.RPG_Classes;
import rpgclasses.base.ClassAbility;
import rpgclasses.base.RPGClass;
import rpgclasses.data.PlayerData;

import java.awt.*;

public class PrincipalAbilityClassContainer extends FormCustomButton {
    public final PlayerData playerData;
    public final ClassAbility classAbility;
    public final RPGClass rpgClass;
    public final Client client;

    public PrincipalAbilityClassContainer(int x, int y, int width, int height, PlayerData playerData, ClassAbility classAbility, Client client) {
        super(x, y, width, height);
        this.playerData = playerData;
        this.classAbility = classAbility;
        this.rpgClass = classAbility.rpgClass;
        this.client = client;
    }

    @Override
    public void draw(TickManager tickManager, PlayerMob playerMob, Rectangle rectangle) {
        GameFontHandler font = FontManager.bit;

        int style = GameInterfaceStyle.styles.indexOf(Settings.UI);

        HoverStateTextures hoverStateTextures;
        if (playerData.classAbilitiesStringIDs.contains(classAbility.getStringID())) {
            hoverStateTextures = RPG_Classes.class_principal_ability_unlocked[style];
        } else {
            if (classAbility.canUnlock(playerMob, playerData) == null) {
                hoverStateTextures = RPG_Classes.class_principal_ability_available[style];
            } else if (classAbility.isLocked(playerData)) {
                hoverStateTextures = RPG_Classes.class_principal_ability_locked[style];
            } else {
                hoverStateTextures = RPG_Classes.class_principal_ability[style];
            }
        }

        GameTexture slotTexture;
        if (isHovering()) {
            slotTexture = hoverStateTextures.highlighted;

            GameTooltips tooltips = classAbility.getToolTips(client.getPlayer());
            if (tooltips != null) {
                GameTooltipManager.addTooltip(tooltips, GameBackground.getItemTooltipBackground(), TooltipLocation.FORM_FOCUS);
            }
        } else {
            slotTexture = hoverStateTextures.active;
        }
        slotTexture.initDraw().draw(this.getX(), this.getY());

        rpgClass.icons.get(classAbility.ability.stringID).initDraw().draw(this.getX() + 16, this.getY() + 16);

        FontOptions titleOptions = new FontOptions(28).defaultColor(Settings.UI.activeTextColor);
        font.drawString(this.getX() + 168, this.getY() + 18, classAbility.ability.getDisplayName(), titleOptions);

        FontOptions descriptionOptions = new FontOptions(14).defaultColor(Settings.UI.activeTextColor);
        String descriptionText = Localization.translate("abilitydescription", classAbility.ability.stringID + "_0");
        String[] descriptionSplit = descriptionText.trim().split("\n");
        for (int i = 0; i < descriptionSplit.length; i++) {
            String textLine = descriptionSplit[i];
            if (!textLine.isEmpty()) {
                font.drawString(this.getX() + 168, this.getY() + 66 + 20 * i, textLine, descriptionOptions);
            }
        }
    }

}
