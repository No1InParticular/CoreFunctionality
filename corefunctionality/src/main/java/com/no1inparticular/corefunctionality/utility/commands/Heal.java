package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Heal extends BukkitCommand {

	Utility_Main utility;

	public Heal(String name, Utility_Main instance) {
		super(name);
		this.description = "Heal a player";
		this.usageMessage = "/heal [player]";
		this.setPermission("corefunctionality.utility.heal");
		utility = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.utility.heal")) {

				if (args.length > 0 && args[0] != null) {
					if (player.hasPermission("server.utility.heal.other")) {
						String targetName = args[0];
						Player target = Bukkit.getPlayer(targetName);
						if (target != null) {
							player.sendMessage(ChatColor.GREEN + target.getName() + " has been healed.");
							target.sendMessage(ChatColor.GREEN + "You have been healed.");
							target.setHealth(20);
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

				player.setHealth(20);
				player.sendMessage(ChatColor.GREEN + "You have been healed.");
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			if (args.length > 0 && args[0] != null) {
				if (sender.hasPermission("server.utility.heal.other")) {
					String targetName = args[0];
					Player target = Bukkit.getPlayer(targetName);
					if (target != null) {
						sender.sendMessage(ChatColor.GREEN + target.getName() + " has been healed.");
						target.sendMessage(ChatColor.GREEN + "You have been healed.");
						target.setHealth(20);
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
				sender.sendMessage(ChatColor.RED + "Please specify someone to heal.");
			}
		}
		return false;
	}

}
