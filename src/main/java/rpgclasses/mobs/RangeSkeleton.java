package rpgclasses.mobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import rpgclasses.projectiles.RangeSkeletonProjectile;

import java.awt.*;
import java.util.List;

public class RangeSkeleton extends AttackingFollowingMob {
    public RangeSkeleton() {
        super(10);
        this.setSpeed(60.0F);
        this.setFriction(3.0F);
        this.attackCooldown = 750;
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-12, -14, 24, 24);
        this.selectBox = new Rectangle(-13, -30, 26, 40);
        this.swimMaskMove = 12;
        this.swimMaskOffset = 0;
        this.swimSinkOffset = 0;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<RangeSkeleton>(576, 320, false, false, 640, 64) {
            public boolean attackTarget(RangeSkeleton mob, Mob target) {
                if (mob.canAttack()) {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = new RangeSkeletonProjectile(mob.getLevel(), mob, mob.x, mob.y, target.x, target.y, 150.0F, 640);
                    projectile.setTargetPrediction(target, -10.0F);
                    projectile.moveDist(10.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void playHitSound() {
        float pitch = GameRandom.globalRandom.getOneOf(0.95F, 1.0F, 1.05F);
        SoundManager.playSound(GameResources.crack, SoundEffect.effect(this).volume(1.6F).pitch(pitch));
    }

    protected void playDeathSound() {
        float pitch = GameRandom.globalRandom.getOneOf(0.95F, 1.0F, 1.05F);
        SoundManager.playSound(GameResources.crackdeath, SoundEffect.effect(this).volume(0.3F).pitch(pitch));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for (int i = 0; i < 5; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.babySkeletonMage.body, i, 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }

    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, MobRegistry.Textures.babySkeletonMage)).sprite(sprite).mask(swimMask).mirrorX((dir == 0 || dir == 2) && this.moveX < 0.1F).dir(dir).light(light);
        if (dir == 1 || dir == 3) {
            humanDrawOptions.attackArmPosOffset(2, 0);
        }

        float animProgress = this.getAttackAnimProgress();
        if (this.isAttacking) {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).itemSprite(MobRegistry.Textures.babySkeletonMage.body, 0, 9, 32).itemRotatePoint(4, 4).itemEnd().armSprite(MobRegistry.Textures.babySkeletonMage.body, 0, 8, 32).swingRotation(animProgress).light(light);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }

        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = MobRegistry.Textures.human_baby_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        return shadowTexture.initDraw().sprite(this.getDir(), 0, res).light(light).pos(drawX, drawY);
    }

    public int getRockSpeed() {
        return 10;
    }
}
