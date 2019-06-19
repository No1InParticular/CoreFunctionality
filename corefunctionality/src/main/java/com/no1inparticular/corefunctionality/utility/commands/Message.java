package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Message extends BukkitCommand {

	Utility_Main utility;

	public Message(String name, Utility_Main instance) {
		super(name);
		this.description = "Message another player";
		this.usageMessage = "/message <player> <message>";
		this.setPermission("corefunctionality.utility.message");
		utility = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.utility.message")) {
				if (args.length > 1) {
					String targetName = args[0];
					if (Bukkit.getPlayer(targetName) != null) {
						Player target = Bukkit.getPlayer(targetName);
						String message = args[1];
						for (int x = 2; x < args.length; x++) {
							message += " " + args[x];
						}
						player.sendMessage(
								ChatColor.GREEN + "[You -> " + target.getName() + "] " + ChatColor.GRAY + message);
						utility.reply.put(player.getName(), target.getName());

						target.sendMessage(
								ChatColor.GREEN + "[" + player.getName() + " -> You] " + ChatColor.GRAY + message);
						utility.reply.put(target.getName(), player.getName());
					} else {
						player.sendMessage(ChatColor.RED + "Could not find specified player.");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Syntax Error: /message <player> <message>");
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
