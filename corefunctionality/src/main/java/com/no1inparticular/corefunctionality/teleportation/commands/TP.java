package com.no1inparticular.corefunctionality.teleportation.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.teleportation.Teleportation_Main;

import net.md_5.bungee.api.ChatColor;

public class TP extends BukkitCommand {
	
	Teleportation_Main teleport;
	public TP(String name, Teleportation_Main instance) {
		super(name);
		this.description = "Teleport to a player";
		this.usageMessage = "/tp <player>";
		this.setPermission("corefunctionality.teleportation.tp");
		teleport = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.teleportation.tp")) {
				if (args.length > 0) {
					String targetName = args[0];
					if (Bukkit.getPlayer(targetName) != null) {
						Player targetPlayer = Bukkit.getPlayer(targetName);
						player.teleport(targetPlayer);
						player.sendMessage(ChatColor.GREEN + "Teleported to " + targetName + ".");
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
