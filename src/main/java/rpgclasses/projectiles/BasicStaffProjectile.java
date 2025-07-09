package rpgclasses.projectiles;

import aphorea.utils.AphDistances;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
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
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class BasicStaffProjectile extends FollowingProjectile {
    public int dispawnTimer = 140;

    public BasicStaffProjectile() {
    }

    public BasicStaffProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
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

    @Override
    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextInt(dispawnTimer);
    }

    @Override
    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.dispawnTimer = reader.getNextInt();
    }

    @Override
    public void applyData(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) {
        super.applyData(x, y, targetX, targetY, speed, distance, damage, knockback, owner);

    }

    public void init() {
        super.init();
        this.turnSpeed = 1F;
        this.givesLight = true;
        this.height = 18.0F;
        this.trailOffset = 0F;
        this.setWidth(1.0F, true);
        this.piercing = 0;
        this.bouncing = 0;
        this.doesImpactDamage = true;
    }

    public Color getColor() {
        return target == null ? new Color(255, 0, 255) : new Color(255, 0, 0);
    }

    public Color getParticleColor() {
        return getColor();
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), getColor(), 2.0F, 500, this.getHeight());
    }

    @Override
    protected Color getWallHitColor() {
        return getColor();
    }

    public void updateTarget() {
        if (target == null) {
            target = AphDistances.findClosestMob(getLevel(), x, y, distance,
                    m -> {
                        if (m.getDistance(this.x, this.y) > distance) {
                            return false;
                        }
                        int focus = getOwner().isPlayer ? ((PlayerMob) getOwner()).summonFocusUniqueID : -1;
                        if (focus != -1) {
                            return focus == m.getUniqueID();
                        }
                        return this.getOwner().canCollisionHit(m) && m.isHostile;
                    });
        } else if (target.removed()) {
            this.remove();
        }
    }

    @Override
    public void serverTick() {
        super.serverTick();
        if (target == null) {
            dispawnTimer--;
            if (dispawnTimer < 0) {
                this.remove();
            }
        }
    }

    @Override
    public float tickMovement(float delta) {
        if (target == null) {
            this.updateTarget();
            return 0;
        }
        return super.tickMovement(delta);
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
}
