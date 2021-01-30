package com.entiv.stealcrops.compatibility;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class StealCropsManager_1_13_2 extends StealCropsManager {

    @Override
    public void steal(Player player, Block block) {
            BlockData blockData = block.getBlockData();
            Ageable ageable = (Ageable) blockData;

            boolean isRipe = ageable.getAge() == ageable.getMaximumAge();
            if (!isRipe) return;

            dropCrops(player, block.getDrops(), block.getLocation());

            ageable.setAge(getConfigAge());
            block.setBlockData(ageable);
    }

    @Override
    public boolean isCrops(Block block) {
        BlockData blockData = block.getBlockData();
        return blockData instanceof Ageable;
    }
}
