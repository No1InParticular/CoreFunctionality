package com.no1inparticular.corefunctionality.groups.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class RemovePerm  extends BukkitCommand {
	
	Groups_Main groups;
	public RemovePerm(String name, Groups_Main instance) {
        super(name);
        this.description = "Remove a permission from a rank";
        this.usageMessage = "/addperm <rank> <permission>";
        this.setPermission("corefunctionality.groups.removePerm");
		groups = instance;
	}
	
	@Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.groups.removePerm")) {
			if(args.length > 1) {
				String rankName = args[0];
				String perm = args[1];
				for(Rank rank : groups.ranks) {
					if(rankName.equalsIgnoreCase(rank.name)) {
						if(rank.permissions.contains(perm)) {
							sender.sendMessage(ChatColor.GREEN + "The permission " + perm + " has been removed from the group " + rank.name + ".");
							rank.permissions.remove(perm);
							groups.recalculatePerms();
							groups.saveRanks();
							return true;
						} else {
							sender.sendMessage(ChatColor.RED + "The rank does not have this permission node.");
						}
					}
				}
				sender.sendMessage(ChatColor.RED + "Could not find specified rank.");
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /addperm <rank> <permission>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
    }
	
}
