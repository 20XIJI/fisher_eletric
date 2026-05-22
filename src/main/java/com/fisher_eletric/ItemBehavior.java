package com.fisher_eletric;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface ItemBehavior {

    Item getItem();

    int getCooldownTicks();

    boolean shouldActivate(Player player);

    void execute(Player player);

    default void onServerTick() {}
}
