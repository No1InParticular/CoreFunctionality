package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Broadcast extends BukkitCommand {
	
	Utility_Main utility;
	public Broadcast(String name, Utility_Main instance) {
		super(name);
		this.description = "Broadcast a message";
		this.usageMessage = "/broadcast <message>";
		this.setPermission("corefunctionality.utility.broadcast");
		utility = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender.hasPermission("corefunctionality.utility.broadcast")) {
			if(args.length > 0) {
				String message = args[0];
				for(int x = 1; x < args.length; x++) {
					message += " " + args[x];
				}
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Broadcast&8] &a" + message));
			} else {
				sender.sendMessage(ChatColor.RED + "Please specify a message to broadcast.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
	}

}
