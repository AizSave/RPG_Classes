package rpgclasses.levelevents;

import aphorea.registry.AphBuffs;
import aphorea.utils.AphColors;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.HitboxEffectEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.trails.LightningTrail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.GameResources;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.regionSystem.RegionPosition;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ThunderWandLevelEvent extends HitboxEffectEvent implements Attacker {
    private int lifeTime = 0;
    private HashSet<Integer> hits = new HashSet<>();
    public int targetX;
    public int targetY;

    public ThunderWandLevelEvent() {
    }

    public ThunderWandLevelEvent(Mob owner, int targetX, int targetY) {
        super(owner, new GameRandom());
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextShortUnsigned(this.lifeTime);
        writer.putNextInt(this.targetX);
        writer.putNextInt(this.targetY);
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.lifeTime = reader.getNextShortUnsigned();
        this.targetX = reader.getNextInt();
        this.targetY = reader.getNextInt();
    }

    public void init() {
        super.init();
        this.hitsObjects = false;
        this.hits = new HashSet<>();
        if (this.isClient()) {
            SoundManager.playSound(GameResources.electricExplosion, SoundEffect.effect((float) this.targetX, (float) this.targetY).volume(1.2F).pitch(0.8F));
            float initialMoveX = (float) GameRandom.globalRandom.getIntBetween(-20, 20);
            float initialMoveY = (float) GameRandom.globalRandom.getIntBetween(-20, 20);

            for (int i = 0; i < 6; ++i) {
                float finalMoveX;
                float finalMoveY;
                if (i == 0) {
                    finalMoveX = 0.0F;
                    finalMoveY = 0.0F;
                } else {
                    finalMoveX = (float) (GameRandom.globalRandom.getIntBetween(50, 80) * (GameRandom.globalRandom.getChance(0.5F) ? -1 : 1));
                    finalMoveY = (float) (GameRandom.globalRandom.getIntBetween(50, 80) * (GameRandom.globalRandom.getChance(0.5F) ? -1 : 1));
                }

                float prevX = (float) this.targetX;
                float prevY = (float) this.targetY;
                LightningTrail trail = new LightningTrail(new TrailVector(prevX, prevY, 0.0F, 0.0F, i == 0 ? 20.0F : GameRandom.globalRandom.getFloatBetween(10.0F, 15.0F), 0.0F), this.level, this.level.isCave ? AphColors.dark_magic : AphColors.lighting);
                this.level.entityManager.addTrail(trail);

                for (int j = i == 0 ? 1 : i + 2; j < 10; ++j) {
                    float progression = (float) j / 10.0F;
                    float height = 500.0F * progression;
                    float newX = (float) (this.targetX + GameRandom.globalRandom.getIntBetween(-5, 5)) + finalMoveY * (1.0F - progression) + initialMoveX * progression;
                    float newY = (float) (this.targetY + GameRandom.globalRandom.getIntBetween(-5, 5)) + finalMoveX * (1.0F - progression) + initialMoveY * progression;
                    trail.addNewPoint(new TrailVector(newX, newY, newX - prevX, newY - prevY, trail.thickness, height));
                    prevX = newX;
                    prevY = newY;
                }
            }
        }
    }

    public void clientTick() {
        super.clientTick();
        this.lifeTime += 50;
        if (this.lifeTime >= 200) {
            this.over();
        }
    }

    public void serverTick() {
        super.serverTick();
        this.lifeTime += 50;
        if (this.lifeTime >= 200) {
            this.over();
        }

    }

    public Shape getHitBox() {
        int size = 100;
        return new Rectangle(this.targetX - size / 2, this.targetY - size / 2, size, size);
    }

    public boolean canHit(Mob mob) {
        return super.canHit(mob) && !this.hits.contains(mob.getUniqueID());
    }

    public void clientHit(Mob target) {
        this.hits.add(target.getUniqueID());
    }

    public void serverHit(Mob target, boolean clientSubmitted) {
        if (clientSubmitted || !this.hits.contains(target.getUniqueID())) {
            float damagePercent = 0.2F;
            if (target.isBoss()) {
                damagePercent = 0;
            } else if (target.isPlayer || target.isHuman) {
                damagePercent /= 5.0F;
            }

            target.isServerHit(new GameDamage(DamageTypeRegistry.TRUE, (float) target.getMaxHealth() * damagePercent), target.x - this.owner.x, target.y - this.owner.y, 0, this.owner);
            target.addBuff(new ActiveBuff(AphBuffs.STUN, target, 2000, this), true);

            this.hits.add(target.getUniqueID());
        }

    }

    public void hitObject(LevelObjectHit hit) {
    }

    public Set<RegionPosition> getRegionPositions() {
        return Collections.singleton(this.getLevel().regionManager.getRegionPosByTile(this.targetX / 32, this.targetY / 32));
    }
}
