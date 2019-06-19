package com.no1inparticular.corefunctionality.teleportation.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.teleportation.Teleportation_Main;

import net.md_5.bungee.api.ChatColor;

public class TPHere extends BukkitCommand {
	
	Teleportation_Main teleport;
	public TPHere(String name, Teleportation_Main instance) {
		super(name);
		this.description = "Teleport a player to you";
		this.usageMessage = "/tphere <player>";
		this.setPermission("corefunctionality.teleportation.tphere");
		teleport = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.teleportation.tphere")) {
				if (args.length > 0) {
					String targetName = args[0];
					if (Bukkit.getPlayer(targetName) != null) {
						Player targetPlayer = Bukkit.getPlayer(targetName);
						targetPlayer.teleport(player);
						player.sendMessage(ChatColor.GREEN + "Teleported " + targetName + " to you.");
					} else {
						player.sendMessage(ChatColor.RED + "Could not find specified player.");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You need to specify a player.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command.");
		}
		return false;
	}

}
