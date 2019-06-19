package com.no1inparticular.corefunctionality.groups.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class AddRank  extends BukkitCommand {
	
	Groups_Main groups;
	public AddRank(String name, Groups_Main instance) {
        super(name);
        this.description = "Add/create a rank";
        this.usageMessage = "/addrank <rankName> <prefix>";
        this.setPermission("corefunctionality.groups.addRank");
		groups = instance;
	}
	
	@Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.groups.addRank")) {
			if(args.length > 1) {
				String rankName = args[0];
				for(Rank rank : groups.ranks) {
					if(rankName.equalsIgnoreCase(rank.name)) {
						sender.sendMessage(ChatColor.RED + "That rank already exists.");
						return true;
					}
				}
				String prefix = args[1];
				for(int x = 2; x < args.length; x++) {
					prefix += " " + args[x];
				}
				Rank rank = new Rank(rankName, prefix);
				groups.ranks.add(rank);
				sender.sendMessage(ChatColor.GREEN + "The rank " + rankName + " has been created.");
				groups.recalculatePerms();
				groups.saveRanks();
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /addrank <rankName> <prefix>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
    }

}
