package rpgclasses.expbar;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.Renderer;
import necesse.gfx.forms.Form;

import java.awt.*;

public class BarForm extends Form {
    public BarForm(String name, int width, int height) {
        super(name, width, height);
    }

    @Override
    public void draw(TickManager tickManager, PlayerMob perspective, Rectangle renderBox) {
        super.draw(tickManager, perspective, renderBox);

        Renderer.initQuadDraw((int) (4 + ExpBarManger.barPercent * (getWidth() - 6)), getHeight() + 4).color(0, 0.6F, 0, 0.6F).draw(getX() + 1, getY() - 2);
        Renderer.initQuadDraw((int) (ExpBarManger.barPercent * (getWidth() - 6)), getHeight()).color(0, 255, 0).draw(getX() + 3, getY());
    }
}