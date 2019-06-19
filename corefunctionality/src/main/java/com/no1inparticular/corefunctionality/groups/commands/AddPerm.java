package com.no1inparticular.corefunctionality.groups.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class AddPerm extends BukkitCommand {
	
	Groups_Main groups;
	public AddPerm(String name, Groups_Main instance) {
        super(name);
        this.description = "Add a permission to a rank";
        this.usageMessage = "/addperm <rank> <permission>";
        this.setPermission("corefunctionality.groups.addPerm");
		groups = instance;
	}
	
	@Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.groups.addPerm")) {
			if(args.length > 1) {
				String rankName = args[0];
				String perm = args[1];
				for(Rank rank : groups.ranks) {
					if(rankName.equalsIgnoreCase(rank.name)) {
						sender.sendMessage(ChatColor.GREEN + "The permission " + perm + " has been added to the group " + rank.name + ".");
						rank.permissions.add(perm);
						groups.recalculatePerms();
						groups.saveRanks();
						return true;
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
