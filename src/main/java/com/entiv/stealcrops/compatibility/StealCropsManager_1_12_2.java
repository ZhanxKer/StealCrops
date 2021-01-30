package com.entiv.stealcrops.compatibility;

import org.bukkit.CropState;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

public class StealCropsManager_1_12_2 extends StealCropsManager {

    @Override
    public void steal(Player player, Block block) {

        double age = getConfigAge();
        MaterialData data = block.getState().getData();

        Crops crops = (Crops) data;

        boolean isRipe = crops.getState().equals(CropState.RIPE);
        if (!isRipe) return;

        dropCrops(player, block.getDrops(), block.getLocation());

        crops.setState(CropState.getByData((byte) age));
        block.setType(crops.getItemType());
    }

    @Override
    public boolean isCrops(Block block) {
        MaterialData data = block.getState().getData();
        return data instanceof Crops;
    }
}
