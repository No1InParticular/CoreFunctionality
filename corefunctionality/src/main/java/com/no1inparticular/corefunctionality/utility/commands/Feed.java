package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Feed extends BukkitCommand {
	
	Utility_Main utility;
	public Feed(String name, Utility_Main instance) {
		super(name);
		this.description = "Feed a person";
		this.usageMessage = "/feed [player]";
		this.setPermission("corefunctionality.utility.feed");
		utility = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.utility.feed")) {
				
				if (args.length > 0 && args[0] != null) {
					if(player.hasPermission("server.utility.feed.other")) {
					    String targetName = args[0];
					    Player target = Bukkit.getPlayer(targetName);
					    if(target != null) {
							player.sendMessage(ChatColor.GREEN + target.getName() + " has been fed.");
							target.sendMessage(ChatColor.GREEN + "You have been fed.");
							target.setFoodLevel(20);
							return true;
					    } else {
					        sender.sendMessage(ChatColor.RED + "Could not find specified player.");
							return false;
					    }
					} else {
						player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
						return false;
					}
				}
				
				player.setFoodLevel(20);
				player.sendMessage(ChatColor.GREEN + "You have been fed.");
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			if (args.length > 0 && args[0] != null) {
				if(sender.hasPermission("server.utility.feed.other")) {
				    String targetName = args[0];
				    Player target = Bukkit.getPlayer(targetName);
				    if(target != null) {
						sender.sendMessage(ChatColor.GREEN + target.getName() + " has been fed.");
						target.sendMessage(ChatColor.GREEN + "You have been fed.");
						target.setFoodLevel(20);
						return true;
				    } else {
				        sender.sendMessage(ChatColor.RED + "Could not find specified player.");
						return false;
				    }
				} else {
					sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Please specify someone to feed.");
			}
		}
		return false;
	}

}
