package com.no1inparticular.corefunctionality.warps.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.warps.Warps_Main;

import net.md_5.bungee.api.ChatColor;

public class SetWarp extends BukkitCommand {

	Warps_Main warps;

	public SetWarp(String name, Warps_Main instance) {
		super(name);
		this.description = "Set a warp point";
		this.usageMessage = "/setwarp <name>";
		this.setPermission("corefunctionality.warp.set");
		warps = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.warp.set")) {
				if (args.length >= 1) {
					String warp = args[0].toLowerCase();
					Location loc = player.getLocation();
					warps.config.set("warps." + warp + ".world", loc.getWorld().getName());
					warps.config.set("warps." + warp + ".x", loc.getX());
					warps.config.set("warps." + warp + ".y", loc.getY());
					warps.config.set("warps." + warp + ".z", loc.getZ());
					warps.config.set("warps." + warp + ".yaw", loc.getYaw());
					warps.config.set("warps." + warp + ".pitch", loc.getPitch());
					player.sendMessage(ChatColor.GREEN + "Warp has been set.");
					warps.saveConfig();
				} else {
					player.sendMessage(ChatColor.RED + "Please specifiy the name of the warp to be set.");
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
