package com.entiv.stealcrops.compatibility;

import com.entiv.stealcrops.Main;
import com.entiv.stealcrops.Message;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Random;

public abstract class StealCropsManager {

    public abstract void steal(Player player, Block block);

    public abstract boolean isCrops(Block block);

    protected void dropCrops(Player player, Collection<ItemStack> drops, Location location) {

        double change = getConfigChange();

        double random = new Random().nextDouble();

        if (change < random) {

            Message.send(player, getFailMessage());

        } else {

            for (ItemStack drop : drops) {
                location.getWorld().dropItem(location, drop);
            }

            Message.send(player, getSuccessMessage());
        }


    }

    int getConfigAge() {
        return Main.getInstance().getConfig().getInt("偷菜后的作物年龄");
    }

    double getConfigChange() {
        return Main.getInstance().getConfig().getDouble("掉落概率");
    }

    String getSuccessMessage() {
        return Main.getInstance().getConfig().getString("消息提示.偷菜成功");
    }

    String getFailMessage() {
        return Main.getInstance().getConfig().getString("消息提示.偷菜失败");
    }
}
