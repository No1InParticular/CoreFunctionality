package com.no1inparticular.corefunctionality.teleportation.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.combat.Combat_Main;
import com.no1inparticular.corefunctionality.teleportation.Teleportation_Main;
import com.no1inparticular.corefunctionality.teleportation.tpRequest;

import net.md_5.bungee.api.ChatColor;

public class TPAHere extends BukkitCommand {
	
	Teleportation_Main teleport;
	public TPAHere(String name, Teleportation_Main instance) {
		super(name);
		this.description = "Request someone to teleport to you";
		this.usageMessage = "/tpahere <player>";
		this.setPermission("corefunctionality.teleportation.tpa");
		teleport = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player){
			final Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.teleportation.tpa")) {
				
				// Checking if tagged in combat
				if(CoreFunctionality.getPlugin().modules.containsKey("Combat") && CoreFunctionality.getPlugin().modules.get("Combat").enabled) {
					// If the combat module exists and is enabled
					if( ( (Combat_Main)CoreFunctionality.getPlugin().modules.get("Combat") ).tagged.containsKey(player.getName())) {
						// If the Combat module (which is cast to Combat_Main class) tagged list contains player 
						// then stop them from teleporting
						player.sendMessage(ChatColor.RED + "You cannot do this whilst tagged in combat!");
						return false;
					}
				}
				
				if(!teleport.cooldown.contains(player.getName())) {
					if(args.length > 0) {
						final String targetName = args[0];
						if(Bukkit.getPlayer(targetName) != null){
							String senderName = player.getName();
							teleport.tpRequests.put(targetName, new tpRequest(senderName, true));
							Player targetPlayer = Bukkit.getPlayer(targetName);

							targetPlayer.sendMessage(ChatColor.GREEN + senderName + " has requested you teleport to them!\n/tpaccept - Accept the request\n/tpdeny - Deny the request\nThe request will time out in 30 seconds.");
							player.sendMessage(ChatColor.GREEN + "You have sent a teleport request to " + targetName);
							teleport.cooldown.add(senderName);
							Bukkit.getScheduler().runTaskLater(teleport.main, new Runnable() {
								public void run() {
									if(teleport.tpRequests.containsKey(targetName)) {
										teleport.tpRequests.remove(targetName);
										player.sendMessage(ChatColor.RED + "Teleport request timed out.");
									}
								}
							}, 30*20);
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "Could not find specified player.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "Syntax Error: /tpahere <player>");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You can only send a teleport request every 30 seconds.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command.");
		}
		return false;
	}

}
