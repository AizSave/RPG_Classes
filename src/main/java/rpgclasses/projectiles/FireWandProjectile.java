package rpgclasses.projectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import rpgclasses.levelevents.FireWandExplosionLevelEvent;

import java.awt.*;
import java.util.List;

public class FireWandProjectile extends Projectile {
    public FireWandProjectile() {
    }

    public FireWandProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, int knockback) {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(new GameDamage(0));
        this.knockback = knockback;
        this.doesImpactDamage = false;
    }

    public void init() {
        super.init();
        this.givesLight = true;
        this.height = 18.0F;
        this.trailOffset = 0F;
        this.setWidth(4.0F, true);
        this.piercing = 0;
        this.bouncing = 0;
        this.doesImpactDamage = true;
        this.canBounce = false;
    }

    public Color getParticleColor() {
        return new Color(255, 128, 0);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(255, 128, 0), 4.0F, 500, this.getHeight());
    }

    @Override
    protected Color getWallHitColor() {
        return new Color(255, 128, 0);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, 2).pos(drawX, drawY - (int) this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), this.texture.getWidth() / 2, 2);
        }
    }

    @Override
    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        super.doHitLogic(mob, object, x, y);
        getLevel().entityManager.addLevelEvent(new FireWandExplosionLevelEvent(x, y, 50, 0, getOwner()));
    }
}
