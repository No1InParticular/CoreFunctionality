package com.no1inparticular.corefunctionality.moderation.commands;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.moderation.Moderation_Main;

import net.md_5.bungee.api.ChatColor;

public class Ban extends BukkitCommand {
	
	Moderation_Main moderation;
	public Ban(String name, Moderation_Main instance) {
		super(name);
		this.description = "Ban a player";
		this.usageMessage = "/ban <player> <reason>";
		this.setPermission("corefunctionality.moderation.ban");
		moderation = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.moderation.ban")) {
			if(args.length > 1) {
				String targetName = args[0];
				String reason = args[1];
				for(int x = 2; x < args.length; x++) {	
					reason += " " + args[x];
				}
				if(Bukkit.getPlayer(targetName) != null) {
					Player target = Bukkit.getPlayer(targetName);
					target.kickPlayer(ChatColor.DARK_RED + "You were banned for: " + ChatColor.WHITE  + reason);
					Bukkit.getBanList(Type.NAME).addBan(target.getName(), reason, null, sender.getName());
					Bukkit.getBanList(Type.IP).addBan(target.getAddress().toString(), reason, null, sender.getName());
					sender.sendMessage(ChatColor.GREEN + "Player along with their IP has been banned.");
				} else {
					Bukkit.getBanList(Type.NAME).addBan(targetName, reason, null, sender.getName());
					sender.sendMessage(ChatColor.RED + "Player has been banned (not ip) although not currently online, check the username.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /ban <player> <reason>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
	}

}
