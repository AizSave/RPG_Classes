package rpgclasses.buffs.ability;

import aphorea.utils.magichealing.AphMagicHealingFunctions;
import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolItem;
import org.jetbrains.annotations.Nullable;
import rpgclasses.buffs.SimpleClassBuff;
import rpgclasses.buffs.SimpleClassChargeBuff;

public class SacredJudgmentBuff extends SimpleClassBuff implements AphMagicHealingFunctions {
    public int abilityLevel;

    public SacredJudgmentBuff(int abilityLevel) {
        super();
        this.abilityLevel = abilityLevel;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void onMagicalHealing(Mob healer, Mob target, int healing, int realHealing, @Nullable ToolItem toolItem, @Nullable InventoryItem item) {
        for (int i = 0; i < (abilityLevel == 2 ? realHealing * 2 : realHealing); i++) {
            healer.buffManager.addBuff(new ActiveBuff("sacredjudgmentcharge" + "_" + abilityLevel, healer, 3F, null), true);
        }
    }

    public static class SacredJudgmentChargeBuff extends SimpleClassChargeBuff {
        public int abilityLevel;

        public SacredJudgmentChargeBuff(int abilityLevel) {
            super(new ModifierValue<>(BuffModifiers.ALL_DAMAGE, 0.1F));
            this.abilityLevel = abilityLevel;
        }

        @Override
        public void drawIcon(int x, int y, ActiveBuff buff) {
            GameTexture drawIcon = this.getDrawIcon(buff);
            drawIcon.initDraw().size(32, 32).draw(x, y);
            int stacksDisplayCount = this.getStacksDisplayCount(buff);
            String text;
            int width;
            if (stacksDisplayCount > 1) {
                text = "+" + stacksDisplayCount + "%";
                width = FontManager.bit.getWidthCeil(text, durationFontOptions);
                FontManager.bit.drawString((float) (x + 28 - width), (float) (y + 30 - FontManager.bit.getHeightCeil(text, durationFontOptions)), text, durationFontOptions);
            }

            if (this.shouldDrawDuration(buff)) {
                text = buff.getDurationText();
                width = FontManager.bit.getWidthCeil(text, durationFontOptions);
                FontManager.bit.drawString((float) (x + 16 - width / 2), (float) (y + 30), text, durationFontOptions);
            }
        }

        @Override
        public int getStackSize(ActiveBuff buff) {
            return 30 * (int) Math.pow(2, abilityLevel + 1);
        }
    }
}