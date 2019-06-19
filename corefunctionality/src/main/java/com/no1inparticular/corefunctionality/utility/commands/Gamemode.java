package com.no1inparticular.corefunctionality.utility.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Gamemode extends BukkitCommand {
	
	Utility_Main utility;
	public Gamemode(String name, Utility_Main instance) {
        super(name);
        this.description = "Change your, or anothers gamemode";
        this.usageMessage = "/gamemode <gamemode> [target]";
        this.setPermission("corefunctionality.utility.gamemode");
        this.setAliases(new ArrayList<String>());
		utility = instance;
	}
	
	 @Override
	    public boolean execute(CommandSender sender, String alias, String[] args) {
		 if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission("corefunctionality.utility.gamemode")) {
					
					if (args.length > 1) {
						if(player.hasPermission("server.utility.gamemode.other")) {
							String gamemode = args[0].toUpperCase();
							if(gamemode.equals("C")) {
								gamemode = "CREATIVE";
							} else if (gamemode.equals("S")) {
								gamemode = "SURVIVAL";
							} else if (gamemode.equals("A")) {
								gamemode = "ADVENTURE";
							} else if (gamemode.equals("SP")) {
								gamemode = "SPECTATOR";
							}
						    String targetName = args[1];
						    Player target = Bukkit.getPlayer(targetName);
						    if(target != null) {
						    	try{
						    		target.setGameMode(GameMode.valueOf(gamemode));
						    		target.sendMessage(ChatColor.GREEN + "Your gamemode has been updated.");
						    		player.sendMessage(ChatColor.GREEN + target.getName() + "'s gamemode has been updated.");
						    	}catch (Exception e) {
						    		player.sendMessage(ChatColor.RED + "Invalid gamemode specified.");
								}
						    } else {
						    	player.sendMessage(ChatColor.RED + "Could not find specified player.");
						    }
						} else {
							player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
						}
					} else if (args.length > 0) {
						try{
							String gamemode = args[0].toUpperCase();
							if(gamemode.equals("C")) {
								gamemode = "CREATIVE";
							} else if (gamemode.equals("S")) {
								gamemode = "SURVIVAL";
							} else if (gamemode.equals("A")) {
								gamemode = "ADVENTURE";
							} else if (gamemode.equals("SP")) {
								gamemode = "SPECTATOR";
							}
				    		player.setGameMode(GameMode.valueOf(gamemode));
				    		player.sendMessage(ChatColor.GREEN + "Your gamemode has been updated.");
				    	}catch (Exception e) {
				    		player.sendMessage(ChatColor.RED + "Invalid gamemode specified.");
						}
					} else {
			    		player.sendMessage(ChatColor.RED + "Syntax Error: /gamemode <gamemode> [target].");
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
