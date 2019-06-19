package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class InvSee extends BukkitCommand {

	Utility_Main utility;

	public InvSee(String name, Utility_Main instance) {
		super(name);
		this.description = "Look into an inventory";
		this.usageMessage = "/invsee <player>";
		this.setPermission("corefunctionality.utility.invsee");
		utility = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.utility.invsee.use")) {
				if (args.length > 0) {
					String targetName = args[0];
					Player target = Bukkit.getPlayer(targetName);
					if (target != null) {
						player.openInventory(target.getInventory());
						player.sendMessage(ChatColor.GREEN + "Opened " + target.getName() + "'s inventory.");
					} else {
						player.sendMessage(ChatColor.RED + "Could not find specified player.");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Syntax Error: /invsee <player>");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to do this command.");
		}
		return false;
	}

}
