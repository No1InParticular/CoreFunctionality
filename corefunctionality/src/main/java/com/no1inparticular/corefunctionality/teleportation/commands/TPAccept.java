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

public class TPAccept extends BukkitCommand {
	
	Teleportation_Main teleport;
	public TPAccept(String name, Teleportation_Main instance) {
		super(name);
		this.description = "Accept a tp request";
		this.usageMessage = "/tpaccept";
		this.setPermission("corefunctionality.teleportation.tpa");
		teleport = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
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
				
				String recieverName = player.getName();
				if(teleport.tpRequests.containsKey(recieverName)){
					tpRequest request = teleport.tpRequests.get(recieverName);
					String requesterName = request.sender;
					boolean tpahere = request.tpahere;
					if(tpahere) {
						if (Bukkit.getServer().getPlayer(requesterName) != null) {
							Player tpaRequester = Bukkit.getServer().getPlayer(requesterName);
							player.teleport(tpaRequester);
							player.sendMessage(ChatColor.GREEN + requesterName + " has accepted your teleport request!");
							tpaRequester.sendMessage(ChatColor.GREEN + "Teleporting to " + recieverName + ".");
							teleport.tpRequests.remove(recieverName);
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "The sender is no longer online.");
						}
					} else {
						if (Bukkit.getServer().getPlayer(requesterName) != null) {
							Player tpaRequester = Bukkit.getServer().getPlayer(requesterName);
							tpaRequester.teleport(player);
							tpaRequester.sendMessage(ChatColor.GREEN + recieverName + " has accepted your teleport request!");
							player.sendMessage(ChatColor.GREEN + "Teleporting to " + requesterName + ".");
							teleport.tpRequests.remove(recieverName);
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "The sender is no longer online.");
						}
					}
				}else{
					player.sendMessage(ChatColor.RED + "You do not have any pending requests!");
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
