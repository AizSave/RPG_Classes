package rpgclasses.containers;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.forms.components.FormButton;
import necesse.gfx.forms.position.FormFixedPosition;
import necesse.gfx.forms.position.FormPosition;
import necesse.gfx.forms.position.FormPositionContainer;

import java.awt.*;
import java.util.List;

public abstract class FormCustomButton extends FormButton implements FormPositionContainer {
    public FormPosition position;
    public int width;
    public int height;

    public FormCustomButton(int x, int y, int width, int height) {
        this.position = new FormFixedPosition(x, y);
        this.width = width;
        this.height = height;
    }

    public abstract void draw(TickManager tickManager, PlayerMob perspective, Rectangle renderBox);

    public List<Rectangle> getHitboxes() {
        return singleBox(new Rectangle(this.getX(), this.getY(), this.width, this.height));
    }

    public FormPosition getPosition() {
        return this.position;
    }

    public void setPosition(FormPosition position) {
        this.position = position;
    }
}
