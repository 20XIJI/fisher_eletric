package com.fisher_eletric;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemEventHandler {

    private final List<Entry> entries = new ArrayList<>();

    public void register(ItemBehavior behavior) {
        entries.add(new Entry(behavior, new CooldownTracker()));
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        for (Entry entry : entries) {
            entry.behavior.onServerTick();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        for (Entry entry : entries) {
            ItemBehavior behavior = entry.behavior;
            CooldownTracker cooldowns = entry.cooldowns;

            if (!isHolding(player, behavior.getItem())) {
                cooldowns.remove(player.getUUID());
                continue;
            }

            if (!cooldowns.tick(player.getUUID())) {
                continue;
            }

            if (!behavior.shouldActivate(player)) {
                continue;
            }

            cooldowns.reset(player.getUUID(), behavior.getCooldownTicks());
            behavior.execute(player);
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        for (Entry entry : entries) {
            entry.cooldowns.remove(event.getEntity().getUUID());
        }
    }

    private static boolean isHolding(Player player, Item item) {
        return player.getMainHandItem().is(item) || player.getOffhandItem().is(item);
    }

    private static class Entry {
        final ItemBehavior behavior;
        final CooldownTracker cooldowns;

        Entry(ItemBehavior behavior, CooldownTracker cooldowns) {
            this.behavior = behavior;
            this.cooldowns = cooldowns;
        }
    }
}
