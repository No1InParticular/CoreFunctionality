package com.no1inparticular.corefunctionality.home.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.home.Home_Main;

import net.md_5.bungee.api.ChatColor;

public class SetHome extends BukkitCommand {

	Home_Main home;
	public SetHome(String name, Home_Main instance) {
		super(name);
		this.description = "Set a home";
		this.usageMessage = "/sethome <name>";
		this.setPermission("corefunctionality.home.use");
		home = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.home.use")) {
				if(args.length >= 1) {
					String homeName = args[0].toLowerCase();
					Location loc = player.getLocation();
					home.config.set("homes." + player.getUniqueId() + "." + homeName + ".world", loc.getWorld().getName());
					home.config.set("homes." + player.getUniqueId() + "." + homeName + ".x", loc.getX());
					home.config.set("homes." + player.getUniqueId() + "." + homeName + ".y", loc.getY());
					home.config.set("homes." + player.getUniqueId() + "." + homeName + ".z", loc.getZ());
					home.config.set("homes." + player.getUniqueId() + "." + homeName + ".yaw", loc.getYaw());
					home.config.set("homes." + player.getUniqueId() + "." + homeName + ".pitch", loc.getPitch());
					player.sendMessage(ChatColor.GREEN + "Home has been set.");
					home.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "Please specifiy the name of the home to be set.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command");
		}
		return false;
	}
}
