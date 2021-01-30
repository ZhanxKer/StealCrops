package com.entiv.stealcrops;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

import java.util.Collection;
import java.util.Random;

public class PlayerListener implements Listener {

    FileConfiguration config = Main.getInstance().getConfig();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if (!item.getType().equals(Material.AIR)) return;
        if (block == null) return;


        boolean isEnableWorld = config.getStringList("启用的世界").contains(block.getWorld().getName());
        if (!isEnableWorld) return;

        int version = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);

        int maturity = config.getInt("偷菜后的作物成熟度");

        if (version <= 12) {

            MaterialData data = block.getState().getData();
            boolean isCrops = data instanceof Crops;

            if (!isCrops) return;

            Crops crops = (Crops) data;

            boolean isRipe = crops.getState().equals(CropState.RIPE);
            if (!isRipe) return;

            dropCrops(block.getDrops(), block.getLocation());

            crops.setState(CropState.getByData((byte) maturity));
            block.setType(crops.getItemType());
        } else {

            BlockData blockData = block.getBlockData();
            boolean isAgeable = blockData instanceof Ageable;

            if (!isAgeable) return;
            Ageable ageable = (Ageable) blockData;

            boolean isRipe = ageable.getAge() == ageable.getMaximumAge();
            if (!isRipe) return;

            dropCrops(block.getDrops(), block.getLocation());

            ageable.setAge(maturity);
            block.setBlockData(ageable);
        }
    }

    public void dropCrops(Collection<ItemStack> drops, Location location) {
        double change = config.getDouble("掉落概率");

        double random = new Random().nextDouble();

        if (change < random) return;

        for (ItemStack drop : drops) {
            location.getWorld().dropItem(location, drop);
        }
    }
}
