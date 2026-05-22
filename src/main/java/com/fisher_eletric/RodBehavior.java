package com.fisher_eletric;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

public class RodBehavior implements ItemBehavior {

    private final FishSpawner spawner = new FishSpawner();

    @Override
    public Item getItem() {
        return ModItems.FISH_CATCHER_ROD.get();
    }

    @Override
    public int getCooldownTicks() {
        return Config.Rod.spawnInterval.get();
    }

    @Override
    public boolean shouldActivate(Player player) {
        return WaterDetector.isNearWater(
                player.level(), player.blockPosition(), Config.waterSearchRadius.get());
    }

    @Override
    public void execute(Player player) {
        ServerLevel level = (ServerLevel) player.level();
        int radius = Config.waterSearchRadius.get();

        List<BlockPos> positions = WaterDetector.findWaterPositions(
                player.level(), player.blockPosition(), radius, 10);

        if (positions.isEmpty()) return;

        BlockPos pos = positions.get(player.getRandom().nextInt(positions.size()));
        spawner.spawnRandomFish(level, pos, player.getRandom());
    }

    @Override
    public void onServerTick() {
        spawner.tick();
    }
}
