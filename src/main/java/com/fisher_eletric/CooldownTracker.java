package com.fisher_eletric;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownTracker {

    private final Map<UUID, Integer> cooldowns = new HashMap<>();

    public boolean tick(UUID uuid) {
        int remaining = cooldowns.getOrDefault(uuid, 0);
        if (remaining > 0) {
            cooldowns.put(uuid, remaining - 1);
            return false;
        }
        return true;
    }

    public void reset(UUID uuid, int ticks) {
        cooldowns.put(uuid, ticks);
    }

    public void remove(UUID uuid) {
        cooldowns.remove(uuid);
    }
}
