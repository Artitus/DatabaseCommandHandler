package com.masonherbel.infusedmc.databasecommandhandler;

import net.minelink.ctplus.CombatTagPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseCommandHandler extends JavaPlugin implements CommandExecutor {

    CombatTagPlus ct;
    List<String> kickReasons = new ArrayList<String>();

    @Override
    public void onEnable() {

        // Load CombatTagPlus integration
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("CombatTagPlus")) {
            ct = (CombatTagPlus) Bukkit.getServer().getPluginManager().getPlugin("CombatTagPlus");
        }

        // Load list of kick reasons into Array
        this.saveDefaultConfig();
        kickReasons.addAll(this.getConfig().getStringList("kickreasons"));


        this.getCommand("database").setExecutor(this);
        this.getCommand("db").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;

        if (ct.getTagManager().isTagged(player.getUniqueId()))
            return false;

        Random randomIndex = new Random();
        String kickReason = kickReasons.get(randomIndex.nextInt(kickReasons.size()));

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick -s " + player.getName() + " " + kickReason);

        return true;
    }
}