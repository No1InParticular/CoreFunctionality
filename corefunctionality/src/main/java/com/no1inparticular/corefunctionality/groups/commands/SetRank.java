package com.no1inparticular.corefunctionality.groups.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class SetRank  extends BukkitCommand {
	
	Groups_Main groups;
	public SetRank(String name, Groups_Main instance) {
        super(name);
        this.description = "Set a players rank";
        this.usageMessage = "/setrank <player> <rank>";
        this.setPermission("corefunctionality.groups.set");
		groups = instance;
	}
	
	@Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("corefunctionality.groups.set")) {
			if(args.length > 1) {
				String targetName = args[0];
				String rankName = args[1];
				if(Bukkit.getPlayer(targetName) != null) {
					Player target = Bukkit.getPlayer(targetName);
					for(Rank rank : groups.ranks) {
						if(rankName.equalsIgnoreCase(rank.name)) {
							if(groups.getRank(target.getUniqueId().toString()) != null) {
								groups.getRank(target.getUniqueId().toString()).members.remove(target.getUniqueId().toString());
							}
							sender.sendMessage(ChatColor.GREEN + target.getName() + " has been set in the group " + rank.name + ".");
							rank.members.add(target.getUniqueId().toString());
							groups.recalculatePerms();
							groups.saveRanks();
							return true;
						}
					}
					sender.sendMessage(ChatColor.RED + "Could not find specified rank.");
				} else {
					sender.sendMessage(ChatColor.RED + "Could not find specified player.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /setrank <player> <rank>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
    }

}
