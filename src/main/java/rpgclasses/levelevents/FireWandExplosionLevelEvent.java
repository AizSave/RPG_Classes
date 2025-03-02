package rpgclasses.levelevents;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;

import java.awt.*;

public class FireWandExplosionLevelEvent extends ExplosionEvent implements Attacker {
    private int particleBuffer;
    protected ParticleTypeSwitcher explosionTypeSwitcher;

    public FireWandExplosionLevelEvent() {
        this(0.0F, 0.0F, 50, 0, null);
        this.destroysObjects = false;
        this.destroysTiles = false;
    }

    public FireWandExplosionLevelEvent(float x, float y, int range, int toolTier, Mob owner) {
        super(x, y, range, new GameDamage(0), false, toolTier, owner);
        this.explosionTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC, Particle.GType.CRITICAL);
        this.targetRangeMod = 0.0F;
        this.destroysObjects = false;
        this.destroysTiles = false;
    }

    protected void playExplosionEffects() {
        SoundManager.playSound(GameResources.explosionLight, SoundEffect.effect(this.x, this.y).volume(0.8F).pitch(1F));
        this.level.getClient().startCameraShake(this.x, this.y, 100, 40, 0.5F, 0.5F, true);
    }

    public float getParticleCount(float currentRange, float lastRange) {
        return super.getParticleCount(currentRange, lastRange) * 1.5F;
    }

    public void spawnExplosionParticle(float x, float y, float dirX, float dirY, int lifeTime, float range) {
        if (this.particleBuffer < 10) {
            ++this.particleBuffer;
        } else {
            this.particleBuffer = 0;
            if (range <= (float) Math.max(this.range - 125, 25)) {
                float dx = dirX * (float) GameRandom.globalRandom.getIntBetween(40, 50);
                float dy = dirY * (float) GameRandom.globalRandom.getIntBetween(40, 50) * 0.8F;
                this.getLevel().entityManager.addParticle(x, y, this.explosionTypeSwitcher.next()).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.getIntBetween(0, 4), 0, 12)).sizeFades(70, 100).givesLight(30F, 1F).movesFriction(dx * 0.05F, dy * 0.05F, 0.8F).color((options, lifeTime1, timeAlive, lifePercent) -> {
                    float clampedLifePercent = Math.max(0.0F, Math.min(1.0F, lifePercent));
                    options.color(new Color((int) (255 - 128 * clampedLifePercent), (int) (128 - 128 * clampedLifePercent), 0));
                }).heightMoves(0.0F, 10.0F).lifeTime(lifeTime * 3);
            }
        }

    }

    @Override
    protected boolean canHitMob(Mob target) {
        return target.canBeTargeted(ownerMob, ((PlayerMob) ownerMob).getNetworkClient());
    }

    protected void onMobWasHit(Mob mob, float distance) {
        float mod = this.getDistanceMod(distance);
        float damagePercent = 0.5F;
        if (mob.isBoss()) {
            damagePercent /= 50.0F;
        } else if (mob.isPlayer || mob.isHuman) {
            damagePercent /= 5.0F;
        }

        GameDamage damage = new GameDamage(DamageTypeRegistry.TRUE, (float) mob.getMaxHealth() * damagePercent * mod);
        float knockback = (float) this.knockback * mod;
        mob.isServerHit(damage, (float) mob.getX() - this.x, (float) mob.getY() - this.y, knockback, this);
        mob.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.ON_FIRE, mob, 10000, null), true);
    }

}
