package com.no1inparticular.corefunctionality.moderation.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.moderation.Moderation_Main;

import net.md_5.bungee.api.ChatColor;

public class Mute  extends BukkitCommand {
	
	Moderation_Main moderation;
	public Mute(String name, Moderation_Main instance) {
		super(name);
		this.description = "Mute a player";
		this.usageMessage = "/mute <player>";
		this.setPermission("corefunctionality.moderation.mute.use");
		moderation = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.moderation.mute.use")) {
			if(args.length > 0) {
				String targetName = args[0];
				if(Bukkit.getPlayer(targetName) != null) {
					Player target = Bukkit.getPlayer(targetName);
					if(moderation.muted.contains(target.getUniqueId().toString())) {
						moderation.muted.remove(target.getUniqueId().toString());
						moderation.config.set("muted", moderation.muted);
						moderation.saveConfig();
						sender.sendMessage(ChatColor.GREEN + target.getName() + " can now talk again.");
					} else {
						moderation.muted.add(target.getUniqueId().toString());
						moderation.config.set("muted", moderation.muted);
						moderation.saveConfig();
						sender.sendMessage(ChatColor.GREEN + target.getName() + " has been silenced.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Could not find specified player.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /mute <player>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
	}

}
