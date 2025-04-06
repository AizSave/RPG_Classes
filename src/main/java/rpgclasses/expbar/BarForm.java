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

        if (ExpBarManger.vertical) {
            int progress = (int) (ExpBarManger.barPercent * (getHeight() - 6));

            Renderer.initQuadDraw(getWidth() + 4, 4 + progress)
                    .color(0, 0.6F, 0, 0.6F)
                    .draw(getX() - 2, getY() + getHeight() - progress - 1);

            Renderer.initQuadDraw(getWidth(), progress)
                    .color(0, 255, 0)
                    .draw(getX(), getY() + getHeight() - progress - 3);
            ;
        } else {
            int progress = (int) (ExpBarManger.barPercent * (getHeight() - 6));

            Renderer.initQuadDraw(4 + progress, getHeight() + 4)
                    .color(0, 0.6F, 0, 0.6F)
                    .draw(getX() + 1, getY() - 2);

            Renderer.initQuadDraw(progress, getHeight())
                    .color(0, 255, 0)
                    .draw(getX() + 3, getY());
        }
    }
}