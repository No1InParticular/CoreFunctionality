package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Reply extends BukkitCommand {
	
	Utility_Main utility;
	
	public Reply(String name, Utility_Main instance) {
		super(name);
		this.description = "Reply to a message";
		this.usageMessage = "/reply <message>";
		this.setPermission("corefunctionality.utility.message");
		utility = instance;
	}
	
		public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.utility.message")) {
				if(utility.reply.containsKey(player.getName())) {
					if(Bukkit.getPlayer(utility.reply.get(player.getName())) != null) {
						Player target = Bukkit.getPlayer(utility.reply.get(player.getName()));
						if(args.length > 0) {
							String message = args[0];
							for(int x = 1; x < args.length; x++) {
								message += " " + args[x];
							}
							player.sendMessage(ChatColor.GREEN + "[You -> " + target.getName() + "] " + ChatColor.GRAY + message);
							utility.reply.put(player.getName(), target.getName());
							
							target.sendMessage(ChatColor.GREEN + "[" + player.getName() + " -> You] " + ChatColor.GRAY + message);
							utility.reply.put(target.getName(), player.getName());
							
						} else {
							player.sendMessage(ChatColor.RED + "Syntax Error: /reply <message>");
						}
					} else {
						player.sendMessage(ChatColor.RED + "The person is no longer online.");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You do not have anyone to reply to.");
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
