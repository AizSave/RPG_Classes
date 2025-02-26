package rpgclasses.projectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import rpgclasses.buffs.MarkedBuff;
import rpgclasses.levelevents.PlasmaGrenadeExplosionLevelEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlasmaGrenadeProjectile extends FollowingProjectile {
    public PlasmaGrenadeProjectile() {
    }

    public PlasmaGrenadeProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void init() {
        super.init();
        this.turnSpeed = 0.5F;
        this.givesLight = true;
        this.height = 18.0F;
        this.trailOffset = 0F;
        this.setWidth(1.0F, true);
        this.piercing = 0;
        this.bouncing = 0;
        this.doesImpactDamage = false;
    }

    public Color getParticleColor() {
        return new Color(0, 255, 255);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(0, 255, 255), 2.0F, 500, this.getHeight());
    }

    @Override
    protected Color getWallHitColor() {
        return new Color(0, 255, 255);
    }

    public void updateTarget() {
        if (this.traveledDistance > 50F) {
            PlayerMob player = (PlayerMob) getOwner();

            ArrayList<Mob> mobs = new ArrayList<>();
            GameAreaStream<Mob> areaMobs = getLevel().entityManager.streamAreaMobsAndPlayers(x, y, (int) (distance - traveledDistance)).filter(
                    m -> MarkedBuff.isMarked(player, m)
            );
            Objects.requireNonNull(mobs);
            areaMobs.forEach(mobs::add);
            mobs.sort((m1, m2) -> {
                float d1 = m1.getDistance(x, y);
                float d2 = m2.getDistance(x, y);
                return Float.compare(d1, d2);
            });
            target = mobs.stream().findFirst().orElse(null);
        }

    }

    @Override
    public float getTurnSpeed(int targetX, int targetY, float delta) {
        return super.getTurnSpeed(targetX, targetY, delta) * 0.002F * (traveledDistance - 50F);
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
        getLevel().entityManager.addLevelEvent(new PlasmaGrenadeExplosionLevelEvent(x, y, 50, getDamage(), 0, getOwner()));
    }
}
