package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Suicide extends BukkitCommand {

	Utility_Main utility;

	public Suicide(String name, Utility_Main instance) {
		super(name);
		this.description = "Kill yourself";
		this.usageMessage = "/suicide";
		this.setPermission("corefunctionality.utility.suicide");
		utility = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.utility.suicide")) {
				player.setHealth(0);
				player.sendMessage(ChatColor.GREEN + "You commited suicide.");
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command");
		}
		return false;
	}

}
