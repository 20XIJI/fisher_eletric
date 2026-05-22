package com.fisher_eletric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class WaterDetector {

    public static boolean isNearWater(BlockGetter level, BlockPos center, int radius) {
        return !findWaterPositions(level, center, radius, 1).isEmpty();
    }

    public static List<BlockPos> findWaterPositions(BlockGetter level, BlockPos center, int radius, int limit) {
        List<BlockPos> positions = new ArrayList<>();
        int r2 = radius * radius;

        for (int x = -radius; x <= radius && positions.size() < limit; x++) {
            for (int y = -radius; y <= radius && positions.size() < limit; y++) {
                for (int z = -radius; z <= radius && positions.size() < limit; z++) {
                    if (x * x + y * y + z * z > r2) continue;
                    BlockPos pos = center.offset(x, y, z);
                    if (level.getBlockState(pos).is(Blocks.WATER)) {
                        positions.add(pos.immutable());
                    }
                }
            }
        }
        return positions;
    }
}
