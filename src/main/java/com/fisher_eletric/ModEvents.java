package com.fisher_eletric;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModEvents {

    private static final Map<UUID, Integer> COOLDOWNS = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        boolean holdingRod = player.getMainHandItem().is(ModItems.ELECTRIC_FISHING_ROD.get())
                || player.getOffhandItem().is(ModItems.ELECTRIC_FISHING_ROD.get());

        if (!holdingRod) {
            COOLDOWNS.remove(player.getUUID());
            return;
        }

        UUID uuid = player.getUUID();
        int remaining = COOLDOWNS.getOrDefault(uuid, 0);
        if (remaining > 0) {
            COOLDOWNS.put(uuid, remaining - 1);
            return;
        }

        if (!isNearWater(player)) {
            return;
        }

        COOLDOWNS.put(uuid, Config.cooldownTicks.get());
        generateFishingLoot(player);
    }

    private static boolean isNearWater(Player player) {
        BlockPos center = player.blockPosition();
        int radius = Config.waterSearchRadius.get();
        int r2 = radius * radius;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z > r2) {
                        continue;
                    }
                    BlockPos pos = center.offset(x, y, z);
                    if (player.level().getBlockState(pos).is(Blocks.WATER)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void generateFishingLoot(Player player) {
        ServerLevel serverLevel = (ServerLevel) player.level();

        LootTable lootTable = serverLevel.getServer()
                .reloadableRegistries()
                .getLootTable(BuiltInLootTables.FISHING);

        LootParams params = new LootParams.Builder(serverLevel)
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
                    serverLevel,
                    player.getX(),
                    player.getY() + 1.0,
                    player.getZ(),
                    stack.copy()
            );
            drop.setPickUpDelay(0);
            serverLevel.addFreshEntity(drop);
        }

        serverLevel.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.FISHING_BOBBER_SPLASH,
                SoundSource.PLAYERS,
                0.5F,
                1.0F
        );
    }
}
