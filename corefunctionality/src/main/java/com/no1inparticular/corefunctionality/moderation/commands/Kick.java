package com.no1inparticular.corefunctionality.moderation.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.moderation.Moderation_Main;

import net.md_5.bungee.api.ChatColor;

public class Kick extends BukkitCommand {
	
	Moderation_Main moderation;
	public Kick(String name, Moderation_Main instance) {
		super(name);
		this.description = "Kick a player";
		this.usageMessage = "/kick <player> <reason>";
		this.setPermission("corefunctionality.moderation.kick");
		moderation = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.moderation.kick")) {
			if(args.length > 1) {
				String targetName = args[0];
				String reason = args[1];
				for(int x = 2; x < args.length; x++) {
					reason += " " + args[x];
				}
				if(Bukkit.getPlayer(targetName) != null) {
					Player target = Bukkit.getPlayer(targetName);
					target.kickPlayer(ChatColor.DARK_RED + "Kicked for: " + ChatColor.WHITE + reason);
					sender.sendMessage(ChatColor.GREEN + "Player has been kicked.");
				} else {
					sender.sendMessage(ChatColor.RED + "Could not find specified player.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /kick <player> <reason>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
	}

}
