package rpgclasses.projectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.DamageTypeRegistry;
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

import java.awt.*;
import java.util.List;

public class RangeSkeletonProjectile extends Projectile {
    private long spawnTime;

    public RangeSkeletonProjectile() {
    }

    public RangeSkeletonProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance) {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(new GameDamage(0));
        this.knockback = 0;
        this.doesImpactDamage = false;
    }


    public void init() {
        super.init();
        this.setWidth(10.0F);
        this.height = 18.0F;
        this.spawnTime = this.getWorldEntity().getTime();
        this.trailOffset = 0.0F;
        this.doesImpactDamage = false;
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(170, 170, 170), 10.0F, 250, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.getX()) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.getY()) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int) this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle(), this.texture.getHeight() / 2);
        }
    }

    public float getAngle() {
        return this.dx < 0.0F ? (float) (this.spawnTime - this.getWorldEntity().getTime()) : (float) (this.getWorldEntity().getTime() - this.spawnTime);
    }

    @Override
    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        super.doHitLogic(mob, object, x, y);
        if (mob != null && mob.isServer()) {
            float damagePercent = 0.01F;
            if (mob.isBoss()) {
                damagePercent /= 50.0F;
            } else if (mob.isPlayer || mob.isHuman) {
                damagePercent /= 5.0F;
            }

            mob.isServerHit(new GameDamage(DamageTypeRegistry.TRUE, damagePercent * mob.getMaxHealth()), x, y, 0, getOwner());
        }
    }
}
