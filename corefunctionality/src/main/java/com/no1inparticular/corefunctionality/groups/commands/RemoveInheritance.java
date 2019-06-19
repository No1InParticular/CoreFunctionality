package com.no1inparticular.corefunctionality.groups.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class RemoveInheritance extends BukkitCommand {

	Groups_Main groups;

	public RemoveInheritance(String name, Groups_Main instance) {
		super(name);
		this.description = "Remove inheritance from a rank";
		this.usageMessage = "/removeInheritance <rank> <inheritFrom>";
		this.setPermission("corefunctionality.groups.removeInheritance");
		groups = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender.hasPermission("corefunctionality.groups.removeInheritance")) {
			if (args.length > 1) {
				String rankName = args[0];
				String inheritRank = args[1];
				for (Rank rank : groups.ranks) {
					if (rankName.equalsIgnoreCase(rank.name)) {
						sender.sendMessage(
								ChatColor.GREEN + rankName + " no longer inherits from " + inheritRank + ".");
						rank.inheritance.remove(inheritRank);
						groups.recalculatePerms();
						groups.saveRanks();
						return true;
					}
				}
				sender.sendMessage(ChatColor.RED + "Could not find specified rank.");
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /removeInheritance <rank> <inheritFrom>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
	}

}
