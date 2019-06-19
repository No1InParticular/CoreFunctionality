package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Repair extends BukkitCommand {

	Utility_Main utility;

	public Repair(String name, Utility_Main instance) {
		super(name);
		this.description = "Repair an item";
		this.usageMessage = "/repair";
		this.setPermission("corefunctionality.utility.repair");
		utility = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.utility.repair")) {
				ItemStack itemStack = player.getInventory().getItemInMainHand();
				if (itemStack.getType().getMaxDurability() > 30) {
					player.getInventory().getItemInMainHand().setDurability((short) 0);
					player.sendMessage(ChatColor.GREEN + "Item repaired.");
				} else {
					player.sendMessage(ChatColor.RED + "You cannot repair this item.");
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
