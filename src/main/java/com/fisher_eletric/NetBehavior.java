package com.fisher_eletric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class NetBehavior implements ItemBehavior {

    @Override
    public Item getItem() {
        return ModItems.ELECTRIC_FISHING_NET.get();
    }

    @Override
    public int getCooldownTicks() {
        return Config.Net.cooldownTicks.get();
    }

    @Override
    public boolean shouldActivate(Player player) {
        return WaterDetector.isNearWater(
                player.level(), player.blockPosition(), Config.waterSearchRadius.get());
    }

    @Override
    public void execute(Player player) {
        LootGenerator.generateFishingLoot((ServerLevel) player.level(), player);
    }
}
