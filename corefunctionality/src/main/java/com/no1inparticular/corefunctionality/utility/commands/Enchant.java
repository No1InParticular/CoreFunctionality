package com.no1inparticular.corefunctionality.utility.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Enchant extends BukkitCommand {
	
	Utility_Main utility;
	public Enchant(String name, Utility_Main instance) {
		super(name);
		this.description = "Enchant an item";
		this.usageMessage = "/enchant <enchant> [level]";
		this.setPermission("corefunctionality.utility.enchant");
		utility = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.utility.enchant")) {
				if(player.getInventory().getItemInMainHand() != null) {
					if (args.length > 0) {
						if(Enchantment.getByName(args[0].toUpperCase()) != null) {
							Enchantment toAdd = Enchantment.getByName(args[0]);
							int level = 1;
							if(args.length > 1) {
								try {
									level = Integer.parseInt(args[1]);
								} catch (Exception e) {
									player.sendMessage(ChatColor.RED + "Invalid level specified, defaulting to 1");
								}
							}
							player.getInventory().getItemInMainHand().addUnsafeEnchantment(toAdd, level);
						} else {
							player.sendMessage(ChatColor.RED + "Invalid enchantment.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "Please specifiy an enchantment.");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Please hold the item you wish to enchant in your main hand.");
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
