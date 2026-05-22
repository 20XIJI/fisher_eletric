package com.fisher_eletric;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FishSpawner {

    private static final EntityType<?>[] FISH_TYPES = {
            EntityType.COD,
            EntityType.SALMON,
            EntityType.PUFFERFISH,
            EntityType.TROPICAL_FISH
    };

    private final List<TrackedFish> tracked = new ArrayList<>();

    public void spawnRandomFish(ServerLevel level, BlockPos waterPos, RandomSource random) {
        int idx = random.nextInt(FISH_TYPES.length);
        Entity fish = FISH_TYPES[idx].create(level);
        if (fish == null) return;

        double sx = waterPos.getX() + 0.5;
        double sy = waterPos.getY() + 1.0;
        double sz = waterPos.getZ() + 0.5;

        fish.setPos(sx, sy, sz);
        level.addFreshEntity(fish);

        level.sendParticles(ParticleTypes.BUBBLE, sx, sy, sz, 5, 0.3, 0.3, 0.3, 0.05);

        int lifetime = Config.Rod.lifetimeTicks.get() + random.nextIntBetweenInclusive(-10, 15);
        tracked.add(new TrackedFish(fish, Math.max(5, lifetime)));
    }

    public void tick() {
        Iterator<TrackedFish> it = tracked.iterator();
        while (it.hasNext()) {
            TrackedFish tf = it.next();
            tf.remaining--;
            if (tf.remaining <= 0) {
                if (tf.entity.isAlive()) {
                    killWithEffect(tf.entity);
                }
                it.remove();
            }
        }
    }

    private static void killWithEffect(Entity fish) {
        RandomSource random = fish.getRandom();
        double fx = fish.getX();
        double fy = fish.getY();
        double fz = fish.getZ();

        fish.setDeltaMovement(
                (random.nextDouble() - 0.5) * 0.4,
                0.5 + random.nextDouble() * 0.4,
                (random.nextDouble() - 0.5) * 0.4
        );

        if (fish.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SPLASH, fx, fy + 0.5, fz, 8, 0.4, 0.2, 0.4, 0.1);
            serverLevel.sendParticles(ParticleTypes.BUBBLE, fx, fy + 0.2, fz, 4, 0.3, 0.2, 0.3, 0.05);
            serverLevel.playSound(null, fx, fy, fz,
                    SoundEvents.GENERIC_SPLASH, SoundSource.NEUTRAL, 0.4F, 0.8F + random.nextFloat() * 0.4F);
        }

        fish.kill();
    }

    private static class TrackedFish {
        final Entity entity;
        int remaining;

        TrackedFish(Entity entity, int remaining) {
            this.entity = entity;
            this.remaining = remaining;
        }
    }
}
