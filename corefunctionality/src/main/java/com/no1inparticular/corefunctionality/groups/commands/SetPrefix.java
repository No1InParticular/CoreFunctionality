package com.no1inparticular.corefunctionality.groups.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class SetPrefix  extends BukkitCommand {
	
	Groups_Main groups;
	public SetPrefix(String name, Groups_Main instance) {
        super(name);
        this.description = "Set the prefix of a rank";
        this.usageMessage = "/setprefix <rank> <prefix>";
        this.setPermission("corefunctionality.groups.addPerm");
		groups = instance;
	}
	
	@Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.groups.addPerm")) {
			if(args.length > 1) {
				String rankName = args[0];
				String prefix = args[1];
				for(int x = 2; x < args.length; x++) {
					prefix += " " + args[x];
				}
				for(Rank rank : groups.ranks) {
					if(rankName.equalsIgnoreCase(rank.name)) {
						rank.prefix = prefix;
						sender.sendMessage(ChatColor.GREEN + "The prefix of " + rankName + " has been changed to " + prefix);
						groups.recalculatePerms();
						groups.saveRanks();
						return true;
					}
				}
				sender.sendMessage(ChatColor.RED + "Could not find specified rank.");
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /setprefix <rank> <prefix>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
    }

}
