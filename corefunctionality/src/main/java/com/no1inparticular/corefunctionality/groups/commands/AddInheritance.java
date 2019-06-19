package com.no1inparticular.corefunctionality.groups.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class AddInheritance extends BukkitCommand {
	
	Groups_Main groups;
	public AddInheritance(String name, Groups_Main instance) {
        super(name);
        this.description = "Add inheritance to a rank";
        this.usageMessage = "/addinheritance <rank> <inheritFrom>";
        this.setPermission("corefunctionality.groups.addInheritance");
		groups = instance;
	}
	
	 @Override
	    public boolean execute(CommandSender sender, String alias, String[] args) {
		 if(sender.hasPermission("corefunctionality.groups.addInheritance")) {
				if(args.length > 1) {
					String rankName = args[0];
					String inheritRank = args[1];
					for(Rank rank : groups.ranks) {
						if(rankName.equalsIgnoreCase(rank.name)) {
							sender.sendMessage(ChatColor.GREEN + rankName + " now inherits all permissions from " + inheritRank + ".");
							rank.inheritance.add(inheritRank);
							groups.recalculatePerms();
							groups.saveRanks();
							return true;
						}
					}
					sender.sendMessage(ChatColor.RED + "Could not find specified rank.");
				} else {
					sender.sendMessage(ChatColor.RED + "Syntax Error: /addInheritance <rank> <inheritFrom>");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
			return false;
	    }
}
