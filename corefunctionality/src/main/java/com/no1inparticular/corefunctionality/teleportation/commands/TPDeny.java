package com.no1inparticular.corefunctionality.teleportation.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.teleportation.Teleportation_Main;
import com.no1inparticular.corefunctionality.teleportation.tpRequest;

import net.md_5.bungee.api.ChatColor;

public class TPDeny extends BukkitCommand {
	
	Teleportation_Main teleport;
	public TPDeny(String name, Teleportation_Main instance) {
		super(name);
		this.description = "Deny a teleport request";
		this.usageMessage = "/tpdeny";
		this.setPermission("corefunctionality.teleportation.tpa");
		teleport = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.teleportation.tpa")) {
				String recieverName = player.getName();
				if (teleport.tpRequests.containsKey(recieverName)) {
					tpRequest request = teleport.tpRequests.get(recieverName);
					if (Bukkit.getServer().getPlayer(request.sender) != null) {
						Bukkit.getServer().getPlayer(request.sender).sendMessage(ChatColor.RED + recieverName + " denied your teleport request.");
						player.sendMessage(ChatColor.RED + "Denied " + request.sender + "'s teleport request.");
						teleport.tpRequests.remove(recieverName);
					} else {
						player.sendMessage(ChatColor.RED + "The sender is no longer online.");
					}
				} else {
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
