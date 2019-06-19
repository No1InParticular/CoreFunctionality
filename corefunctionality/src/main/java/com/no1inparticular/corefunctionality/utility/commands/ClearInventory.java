package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class ClearInventory extends BukkitCommand {
	
	Utility_Main utility;
	public ClearInventory(String name, Utility_Main instance) {
		super(name);
		this.description = "Clear an inventory";
		this.usageMessage = "/clearinventory [player]";
		this.setPermission("corefunctionality.utility.clearinventory");
		utility = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.utility.clearinventory")) {
				
				if (args.length > 0 && args[0] != null) {
					if(player.hasPermission("server.utility.clearinventory.other")) {
					    String targetName = args[0];
					    Player target = Bukkit.getPlayer(targetName);
					    if(target != null) {
							player.sendMessage(ChatColor.GREEN + target.getName() + "'s inventory has been cleared.");
							target.getInventory().clear();
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
				
				player.getInventory().clear();
				player.sendMessage(ChatColor.GREEN + "Your inventory has been cleared.");
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			if (args.length > 0 && args[0] != null) {
				if(sender.hasPermission("server.utility.clearinventory.other")) {
				    String targetName = args[0];
				    Player target = Bukkit.getPlayer(targetName);
				    if(target != null) {
						sender.sendMessage(ChatColor.GREEN + target.getName() + "'s inventory has been cleared.");
						target.getInventory().clear();
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
				sender.sendMessage(ChatColor.RED + "Please specify someones inventory to clear.");
			}
		}
		return false;
	}

}
