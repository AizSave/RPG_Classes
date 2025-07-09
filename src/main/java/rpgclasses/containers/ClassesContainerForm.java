package rpgclasses.containers;

import necesse.engine.Settings;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.client.Client;
import necesse.engine.window.GameWindow;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameBackground;
import necesse.gfx.GameResources;
import necesse.gfx.forms.Form;
import necesse.gfx.forms.components.*;
import necesse.gfx.forms.components.localComponents.FormLocalLabel;
import necesse.gfx.forms.components.localComponents.FormLocalTextButton;
import necesse.gfx.forms.presets.containerComponent.ContainerFormSwitcher;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameFont.FontOptions;
import necesse.gfx.gameTooltips.GameTooltipManager;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.TooltipLocation;
import necesse.gfx.shader.FormShader;
import necesse.gfx.ui.ButtonColor;
import necesse.gfx.ui.GameInterfaceStyle;
import rpgclasses.RPG_Classes;
import rpgclasses.base.ClassAbility;
import rpgclasses.base.RPGClass;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.registry.RPGClassRegistry;

import java.awt.*;
import java.util.Objects;

public class ClassesContainerForm extends ContainerFormSwitcher<ClassesContainer> {
    public static int lastEntryListScroll = 0;
    public static int lastClassDataScroll = 0;
    private final Form classesForm;

    public static String openedClass = RPGClassRegistry.RPGClasses.get(0).stringID;

    FormContentBox classData;

    public FormLabel classNameLabel;
    public FormLabel classAffinityLabel;

    public ClassesContainerForm(Client client, ClassesContainer container) {
        super(client, container);

        FormComponentList formComponents = this.addComponent(new FormComponentList());
        this.classesForm = formComponents.addComponent(new Form(925, 500));
        Color breakLineBlackColor = new Color(0, 0, 0);

        FormContentBox entries = this.classesForm.addComponent(new FormContentBox(0, 10, 250, 340));
        entries.onScrollYChanged((e) -> lastEntryListScroll = e.scroll);

        FormBreakLine playerLevelBreakLike = this.classesForm.addComponent(new FormBreakLine(FormBreakLine.ALIGN_BEGINNING, 10, 355, 235, true));
        playerLevelBreakLike.color = breakLineBlackColor;

        PlayerMob player = client.getPlayer();
        PlayerData playerData = PlayerDataList.getCurrentPlayer(player);

        FormContentBox playerDataComponent = this.classesForm.addComponent(new FormContentBox(12, 360, 250 - 24, 100));
        playerDataComponent.addComponent(new FormLocalLabel(
                new LocalMessage("classescontainer", "talentpoints", "free", playerData.unassignedTalentPoints(), "total", playerData.totalTalentPoints()),
                new FontOptions(12), -1, playerDataComponent.getX(), 10
        ));
        playerDataComponent.addComponent(new FormLocalLabel(
                new LocalMessage("classescontainer", "level", "level", playerData.getLevel()),
                new FontOptions(12), -1, playerDataComponent.getX(), 30
        ));
        playerDataComponent.addComponent(new FormLocalLabel(
                new LocalMessage("classescontainer", "experience", "exp", playerData.getExpActual(), "nextexp", playerData.getExpNext()),
                new FontOptions(12), -1, playerDataComponent.getX(), 50
        ));
        playerDataComponent.addComponent(new FormLocalLabel(
                new LocalMessage("classescontainer", "totalexperience", "exp", playerData.getExp()),
                new FontOptions(12), -1, playerDataComponent.getX(), 70
        ));

        classData = this.classesForm.addComponent(new FormContentBox(250 + 6, 46 + 20, this.classesForm.getWidth() - 250 - 12 - 10, this.classesForm.getHeight() - 32 - 64 - 10 - 10));
        classData.onScrollYChanged((e) -> lastClassDataScroll = e.scroll);

        FormBreakLine middleVerticalLine = this.classesForm.addComponent(new FormBreakLine(FormBreakLine.ALIGN_BEGINNING, entries.getWidth() + 5, entries.getY(), this.classesForm.getHeight() - 55, false));
        middleVerticalLine.color = breakLineBlackColor;

        this.classesForm.addComponent(new FormLocalTextButton("ui", "closebutton", 4, this.classesForm.getHeight() - 40, this.classesForm.getWidth() - 8)).onClicked((e) -> client.closeContainer(true));
        this.makeCurrent(formComponents);

        for (int i = 0; i < RPGClassRegistry.RPGClasses.size(); i++) {
            RPGClass rpgClass = RPGClassRegistry.RPGClasses.get(i);
            entries.addComponent((new FormLocalTextButton(new StaticMessage(rpgClass.getDisplayName() + " [" + rpgClass.getAffinity(player) + "]"), 6, 6 + i * 36, 250 - 12) {
                @Override
                public void draw(TickManager tickManager, PlayerMob perspective, Rectangle renderBox) {
                    Rectangle contentRect = this.size.getContentRectangle(this.getWidth());
                    FormShader.FormShaderState textState = GameResources.formShader.startState(new Point(this.getX(), this.getY()), new Rectangle(contentRect.x, contentRect.y, contentRect.width, contentRect.height));

                    try {
                        FontOptions fontOptions = this.size.getFontOptions().color(this.getTextColor());
                        if (!Objects.equals(openedClass, rpgClass.stringID)) {
                            if (this.isHovering()) {
                                fontOptions.alpha(204);
                            } else {
                                fontOptions.alpha(153);
                            }
                        }
                        String drawText = this.getDrawText();
                        int drawX = this.getWidth() / 2 - FontManager.bit.getWidthCeil(drawText, fontOptions) / 2;
                        FontManager.bit.drawString((float) drawX, (float) (this.size.fontDrawOffset), drawText, fontOptions);
                    } finally {
                        textState.end();
                    }

                    if (this.isHovering()) {
                        this.addTooltips(perspective);
                    }
                }
            }).onClicked((e) -> {
                openedClass = rpgClass.stringID;
                updateClassData(rpgClass, player, playerData);
            }));
        }
        entries.setContentBox(new Rectangle(0, 0, 250, RPGClassRegistry.RPGClasses.size() * 36 + 6));

        this.classesForm.addComponent(classNameLabel = new FormLabel("", new FontOptions(30), -1, 256 + 20, 56 - 30 - 12, 400));
        this.classesForm.addComponent(classAffinityLabel = new FormLabel("", new FontOptions(20), 1, 909 - 20, 56 - 20 - 12, 200));

        FormBreakLine classNameBreakLine = this.classesForm.addComponent(new FormBreakLine(FormBreakLine.ALIGN_BEGINNING, 256 + 6, 56 - 6, 653 - 6, true));
        classNameBreakLine.color = breakLineBlackColor;

        updateClassData(player, playerData);

        entries.setScrollY(lastEntryListScroll);
        lastEntryListScroll = entries.getScrollY();
        classData.setScrollY(lastClassDataScroll);
        lastClassDataScroll = classData.getScrollY();
    }

    public void onWindowResized(GameWindow window) {
        super.onWindowResized(window);
        this.classesForm.setPosMiddle(window.getHudWidth() / 2, window.getHudHeight() / 2);
    }

    public void updateClassData(PlayerMob player, PlayerData playerData) {
        updateClassData(Objects.requireNonNull(RPGClassRegistry.getClass(openedClass)), player, playerData);
    }

    public void updateClassData(RPGClass rpgClass, PlayerMob player, PlayerData playerData) {
        classData.clearComponents();

        classNameLabel.setText(rpgClass.getDisplayName());
        classAffinityLabel.setText(Localization.translate("classescontainer", "affinity", "affinity", rpgClass.getAffinity(player)));

        String lastAbilityLevel = null;
        FormContentBox lastAbility = null;
        int totalAbilities = 0;
        int actualLevel = 0;

        for (int i = 0; i < rpgClass.classAbilities.size(); i++) {
            ClassAbility classAbility = rpgClass.classAbilities.get(i);

            if (rpgClass.principalAbilities.contains(classAbility.getStringID())) {
                this.classData.addComponent(new PrincipalAbilityClassContainer(classData.getX() + 6, classData.getY() + totalAbilities * 168, this.classData.getWidth() - 12, 160, playerData, classAbility, client))
                        .onClicked(c -> container.getAbility.runAndSend(classAbility.getStringID() + "_" + rpgClass.stringID));
                totalAbilities++;
                actualLevel = 0;
                lastAbility = null;
            } else {
                if (!Objects.equals(classAbility.getStringID(), rpgClass.stringID + "_0")) {
                    if (lastAbility == null || !Objects.equals(classAbility.ability.stringID, lastAbilityLevel)) {
                        lastAbility = this.classData.addComponent(new BasicAbilityClassContainer(classData.getX() + 6, classData.getY() + totalAbilities * 168, this.classData.getWidth(), 160, playerData, classAbility));
                        totalAbilities++;
                        actualLevel = 0;
                    }
                    ButtonColor buttonColor;
                    if (playerData.classAbilitiesStringIDs.contains(classAbility.getStringID())) {
                        buttonColor = ButtonColor.GREEN;
                    } else if (classAbility.canUnlock(player, playerData) == null) {
                        buttonColor = ButtonColor.YELLOW;
                    } else if (classAbility.isLocked(playerData)) {
                        buttonColor = ButtonColor.RED;
                    } else {
                        buttonColor = ButtonColor.BASE;
                    }

                    int style = GameInterfaceStyle.styles.indexOf(Settings.UI);
                    lastAbility.addComponent(new FormContentIconButton(actualLevel * 38 + 13, 115, FormInputSize.SIZE_32, buttonColor, RPG_Classes.ability_level_icon[style][classAbility.abilityLevel]) {
                        @Override
                        protected void addTooltips(PlayerMob perspective) {
                            GameTooltips tooltips = classAbility.getToolTips(player);
                            if (tooltips != null) {
                                GameTooltipManager.addTooltip(tooltips, GameBackground.getItemTooltipBackground(), TooltipLocation.FORM_FOCUS);
                            }
                        }
                    }).onClicked(c -> container.getAbility.runAndSend(classAbility.getStringID() + "_" + rpgClass.stringID));
                    actualLevel++;

                    lastAbilityLevel = classAbility.ability.stringID;
                }
            }
        }

        classData.setContentBox(new Rectangle(250 + 6, 46 + 20, this.classesForm.getWidth() - 250 - 12 - 10, totalAbilities * 168));
    }

    public boolean shouldOpenInventory() {
        return false;
    }

    public boolean shouldShowToolbar() {
        return false;
    }

}
