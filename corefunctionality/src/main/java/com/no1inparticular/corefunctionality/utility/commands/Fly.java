package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Fly extends BukkitCommand {
	
	Utility_Main utility;
	public Fly(String name, Utility_Main instance) {
		super(name);
		this.description = "Toggle flight";
		this.usageMessage = "/fly [player]";
		this.setPermission("corefunctionality.utility.fly");
		utility = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.utility.fly")) {
				
				if (args.length > 0 && args[0] != null) {
					if(player.hasPermission("server.utility.fly.other")) {
					    String targetName = args[0];
					    Player target = Bukkit.getPlayer(targetName);
					    if(target != null) {
					    	if(target.getAllowFlight()) {
					    		player.sendMessage(ChatColor.RED + "Flight toggled off for " + player.getName());
					    		target.sendMessage(ChatColor.RED + "Flight toggled off by " + player.getName());
								target.setAllowFlight(false);
								return true;
							} else {
								player.sendMessage(ChatColor.GREEN + "Flight toggled on for " + target.getName());
								target.sendMessage(ChatColor.GREEN + "Flight toggled on by " + player.getName());
								target.setAllowFlight(true);
								return true;
							}
					    } else {
					        sender.sendMessage(ChatColor.RED + "Could not find specified player.");
							return false;
					    }
					} else {
						player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
						return false;
					}
				}

				if(player.getAllowFlight()) {
					player.sendMessage(ChatColor.RED + "Flight toggled off");
					player.setAllowFlight(false);
				} else {
					player.sendMessage(ChatColor.GREEN + "Flight toggled on");
					player.setAllowFlight(true);
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
