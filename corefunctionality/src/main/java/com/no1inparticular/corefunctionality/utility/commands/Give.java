package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Give extends BukkitCommand {

	Utility_Main utility;

	public Give(String name, Utility_Main instance) {
		super(name);
		this.description = "Give an item";
		this.usageMessage = "/give <item> [amount] [player]";
		this.setPermission("corefunctionality.utility.give");
		utility = instance;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		/*
		 * /give <item> <amount> <player> /give <item> <amount> /give <item>
		 */
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.utility.give")) {

				if (args.length == 1) {
					String itemName = args[0].toUpperCase();
					if (Material.getMaterial(itemName) != null) {
						ItemStack item = new ItemStack(Material.getMaterial(itemName));
						player.getInventory().addItem(item);
						player.sendMessage(ChatColor.GREEN + "You have been given " + itemName);
					} else {
						player.sendMessage(ChatColor.RED + "Invalid item specified.");
					}
				} else if (args.length == 2) {
					String itemName = args[0].toUpperCase();
					if (Material.getMaterial(itemName) != null) {
						String amount = args[1];
						try {
							int quantity = Integer.parseInt(amount);
							ItemStack item = new ItemStack(Material.getMaterial(itemName), quantity);
							player.getInventory().addItem(item);
							player.sendMessage(ChatColor.GREEN + "You have been given " + quantity + " " + itemName);
						} catch (Exception e) {
							player.sendMessage(ChatColor.RED + "Invalid amount specified.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "Invalid item specified.");
					}
				} else if (args.length == 3) {
					if (player.hasPermission("server.utility.give.other")) {

						String itemName = args[0].toUpperCase();
						if (Material.getMaterial(itemName) != null) {
							String amount = args[1];
							try {
								int quantity = Integer.parseInt(amount);

								String target = args[2];
								if (Bukkit.getPlayer(target) != null) {
									ItemStack item = new ItemStack(Material.getMaterial(itemName), quantity);
									Bukkit.getPlayer(target).getInventory().addItem(item);
									player.sendMessage(ChatColor.GREEN + "You have given " + target + " " + quantity
											+ " " + itemName);
								} else {
									player.sendMessage(ChatColor.RED + "Could not find specified player.");
								}

							} catch (Exception e) {
								player.sendMessage(ChatColor.RED + "Invalid amount specified.");
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid item specified.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
					}

				} else {
					sender.sendMessage(ChatColor.RED + "Syntax Error: /give <item> [amount] [player]");
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
