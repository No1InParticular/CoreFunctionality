package com.no1inparticular.corefunctionality.referrals.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.referrals.Referrals_Main;

import net.md_5.bungee.api.ChatColor;

public class Refer extends BukkitCommand {
	
	Referrals_Main referrals;
	public Refer(String name, Referrals_Main instance) {
		super(name);
		this.description = "Referral system command";
		this.usageMessage = "/refer";
		this.setPermission("corefunctionality.refer.use");
		referrals = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.refer.use")) {
				if(args.length > 1 && args[0].equalsIgnoreCase("set")) {
					String refererName = args[1];
					if(Bukkit.getPlayer(refererName) != null) {
						Player referrer = Bukkit.getPlayer(refererName);
						if(referrer.equals(player) || referrer.getAddress().equals(player.getAddress())) { 
							player.sendMessage(ChatColor.RED + "You cannot refer yourself or someone on the same IP as you."); 
							return false;
						}
						if(referrals.config.getConfigurationSection("referrals") != null) {
							for(String referrerN : referrals.config.getConfigurationSection("referrals").getKeys(false)) {
								for(String referred : referrals.config.getStringList("referrals." + referrerN)) {
									if(referred.equals(player.getUniqueId().toString())) {
										player.sendMessage(ChatColor.RED + "You have already set someone as your referrer.");
										return false;
									}
								}
							}
						}
						List<String> refered = referrals.config.getStringList("referrals." + referrer.getUniqueId());
						refered.add(player.getUniqueId().toString());
						referrals.config.set("referrals." + referrer.getUniqueId(), refered);
						referrals.saveConfig();
						player.sendMessage(ChatColor.GREEN + referrer.getName() + " has been set as your referrer. You have both been rewarded with £1000");
						referrer.sendMessage(ChatColor.GREEN + player.getName() + " has set you as their referrer. You have both been rewarded with £1000");
						CoreFunctionality.econ.depositPlayer(player, 1000.00);
						CoreFunctionality.econ.depositPlayer(referrer, 1000.00);
					} else {
						player.sendMessage(ChatColor.RED + "Could not find specified player.");
					}
				} else {
					List<String> refered = referrals.config.getStringList("referrals." + player.getUniqueId());
					int count = 0;
					if(refered != null) {
						count = refered.size();
					}
					player.sendMessage(ChatColor.GREEN + "You have refered " + count + " people.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to do this command.");
		}
		return false;
	}

}
