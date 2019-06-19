package com.no1inparticular.corefunctionality.afk.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.afk.AFK_Main;

import net.md_5.bungee.api.ChatColor;

public class AFK extends BukkitCommand {

	AFK_Main afk;

	public AFK(String name, AFK_Main instance) {
		super(name);
		this.description = "Set yourself as AFK";
		this.usageMessage = "/afk";
		this.setPermission("corefunctionality.afk.use");
		afk = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) { // If the sender is a player
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.afk.use")) { // If the player has permission
				if (afk.temp.contains(player.getName())) { // If they are inside the temp list dont let them go afk
															// again
					player.sendMessage(ChatColor.RED + "You was recently afk, please wait before doing this again.");
					return false;
				}
				if (afk.list.contains(player.getName())) { // If they are in the afk list remove them since they are no
															// longer afk
					player.sendMessage(ChatColor.GREEN + "You are no longer afk");
					afk.unsetAFK(player.getName());
					player.setCollidable(true); // Sets players so they can be pushed (normal behaviour)
				} else {
					player.sendMessage(ChatColor.GREEN + "You are now afk"); // If they wasnt in it add them since they
																				// wish to be afk
					afk.setAFK(player.getName());
					player.setCollidable(false); // Sets players so they cant be pushed (to prevent people moving afk
													// players)
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this."); // They do not have
																								// permission to do this
			}
		} else {
			sender.sendMessage("You must be a player to use this command"); // They arent a player
		}
		return false;
	}
}
