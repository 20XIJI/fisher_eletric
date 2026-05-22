package com.fisher_eletric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class LootGenerator {

    public static void generateFishingLoot(ServerLevel level, Player player) {
        LootTable lootTable = level.getServer()
                .reloadableRegistries()
                .getLootTable(BuiltInLootTables.FISHING);

        LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withParameter(LootContextParams.TOOL, player.getMainHandItem())
                .withLuck(player.getLuck())
                .create(LootContextParamSets.FISHING);

        List<ItemStack> items = lootTable.getRandomItems(params);

        if (items.isEmpty()) {
            return;
        }

        for (ItemStack stack : items) {
            ItemEntity drop = new ItemEntity(
                    level,
                    player.getX(),
                    player.getY() + 1.0,
                    player.getZ(),
                    stack.copy()
            );
            drop.setPickUpDelay(0);
            level.addFreshEntity(drop);
        }

        level.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.FISHING_BOBBER_SPLASH,
                SoundSource.PLAYERS,
                0.5F,
                1.0F
        );
    }
}
