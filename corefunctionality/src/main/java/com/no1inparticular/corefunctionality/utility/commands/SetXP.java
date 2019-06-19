package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class SetXP extends BukkitCommand {

	Utility_Main utility;

	public SetXP(String name, Utility_Main instance) {
		super(name);
		this.description = "Set a players XP";
		this.usageMessage = "/setxp <amount> [player]";
		this.setPermission("corefunctionality.utility.setxp");
		utility = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		/*
		 * /setxp 5 /setxp name 5
		 */
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.utility.setxp")) {

				if (args.length == 1) {
					try {
						int amount = Integer.parseInt(args[0]);
						player.setLevel(amount);
						player.sendMessage(ChatColor.GREEN + "Your xp level has been set.");
					} catch (Exception e) {
						player.sendMessage(ChatColor.RED + "Invalid amount specified.");
					}
				} else if (args.length == 2) {

					if (player.hasPermission("server.utility.setxp.other")) {
						try {
							int amount = Integer.parseInt(args[0]);
							Player target = Bukkit.getPlayer(args[1]);
							if (target != null) {
								target.setLevel(amount);
								player.sendMessage(ChatColor.GREEN + target.getName() + "'s xp level has been set.");
							} else {
								player.sendMessage(ChatColor.RED + "Could not find specified player.");
							}
						} catch (Exception e) {
							player.sendMessage(ChatColor.RED + "Invalid amount specified.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Syntax Error: /setxp <amount> [player]");
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
