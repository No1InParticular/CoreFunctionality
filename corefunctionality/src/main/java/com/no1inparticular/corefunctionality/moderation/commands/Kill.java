package com.no1inparticular.corefunctionality.moderation.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.moderation.Moderation_Main;

import net.md_5.bungee.api.ChatColor;

public class Kill extends BukkitCommand {
	
	Moderation_Main moderation;
	public Kill(String name, Moderation_Main instance) {
		super(name);
		this.description = "Kill a player";
		this.usageMessage = "/kill <player>";
		this.setPermission("corefunctionality.moderation.kill");
		moderation = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.moderation.kill")) {
			if(args.length > 0) {
				String targetName = args[0];
				if(Bukkit.getPlayer(targetName) != null) {
					Player target = Bukkit.getPlayer(targetName);
					target.setHealth(0);
					sender.sendMessage(ChatColor.GREEN + "Player has been killed.");
				} else {
					sender.sendMessage(ChatColor.RED + "Could not find specified player.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /kill <player>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
	}

}
