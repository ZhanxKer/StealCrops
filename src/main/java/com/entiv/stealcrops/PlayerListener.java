package com.entiv.stealcrops;

import com.entiv.stealcrops.compatibility.StealCropsManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    FileConfiguration config = Main.getInstance().getConfig();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!item.getType().equals(Material.AIR)) return;
        if (block == null) return;

        boolean isEnableWorld = config.getStringList("启用的世界").contains(block.getWorld().getName());
        if (!isEnableWorld) return;

        StealCropsManager stealCropsManager = Main.getInstance().getStealCrops();

        if (!stealCropsManager.isCrops(block)) return;

        stealCropsManager.steal(player, block);
    }
}
