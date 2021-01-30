package com.entiv.stealcrops;

import com.entiv.stealcrops.compatibility.StealCropsManager;
import com.entiv.stealcrops.compatibility.StealCropsManager_1_12_2;
import com.entiv.stealcrops.compatibility.StealCropsManager_1_13_2;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    private static Main plugin;
    private StealCropsManager stealCropsManager;

    public static Main getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        String[] message = {
                "&e&l" + getName() + "&a 插件&e v" + getDescription().getVersion() + " &a已启用",
                "&a插件制作作者:&e EnTIv &aQQ群:&e 600731934"
        };
        Message.sendConsole(message);

        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        PluginCommand command = Bukkit.getPluginCommand("StealCrops");

        if (command != null) {
            command.setExecutor(new MainCommand());
        }

        setupCompatibility();
    }

    @Override
    public void onDisable() {
        String[] message = {
                "&e&l" + getName() + "&a 插件&e v" + getDescription().getVersion() + " &a已卸载",
                "&a插件制作作者:&e EnTIv &aQQ群:&e 600731934"
        };
        Message.sendConsole(message);
    }

    private void setupCompatibility() {
        int version = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);

        if (version <= 12) {
            this.stealCropsManager = new StealCropsManager_1_12_2();
        } else {
            this.stealCropsManager = new StealCropsManager_1_13_2();
        }

    }

    public StealCropsManager getStealCrops() {
        return stealCropsManager;
    }

    public void runCommand(CommandSender commandSender, List<String> commands) {

        if (commands == null || commands.isEmpty()) return;

        for (String string : commands) {
            int separator = string.indexOf(" ");
            String type = string.substring(0, separator);
            String command = string.substring(separator + 1).replace("%player%", commandSender.getName());

            switch (type) {
                case "[CONSOLE]":
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    break;

                case "[PLAYER]":

                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        player.chat(command);
                    }
                    break;

                case "[OP]":

                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;

                        if (player.isOp()) {
                            player.chat(command);
                        } else {
                            player.setOp(true);
                            player.chat(command);
                            player.setOp(false);
                        }

                    }
                    break;

                default:
                    Message.send(Bukkit.getConsoleSender(), "&9&l" + getName() + "&6&l >> &c命令类型&b&l " + type + "&c 配置错误,请仔细检查配置");
            }
        }

    }

}
