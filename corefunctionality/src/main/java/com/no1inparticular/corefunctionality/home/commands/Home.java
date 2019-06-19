package com.no1inparticular.corefunctionality.home.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.combat.Combat_Main;
import com.no1inparticular.corefunctionality.home.Home_Main;

import net.md_5.bungee.api.ChatColor;

public class Home extends BukkitCommand {

	Home_Main home;
	public Home(String name, Home_Main instance) {
        super(name);
        this.description = "Teleport to a set home";
        this.usageMessage = "/home <name>";
        this.setPermission("corefunctionality.home.use");
		home = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.home.use")) {
				
				// Checking if tagged in combat
				if(CoreFunctionality.getPlugin().modules.containsKey("Combat") && CoreFunctionality.getPlugin().modules.get("Combat").enabled) {
					// If the combat module exists and is enabled
					if( ( (Combat_Main)CoreFunctionality.getPlugin().modules.get("Combat") ).tagged.containsKey(player.getName())) {
						// If the Combat module (which is cast to Combat_Main class) tagged list contains player 
						// then stop them from teleporting
						player.sendMessage(ChatColor.RED + "You cannot do this whilst tagged in combat!");
						return false;
					}
				}
				
				
				if(args.length > 0) {
					String input = args[0];
					if(input.contains(":")) {
						// Others
						if(player.hasPermission("corefunctionality.home.use.other")) {
							
							@SuppressWarnings("deprecation")
							OfflinePlayer target = Bukkit.getOfflinePlayer(input);
							if(target != null) {
								String[] words = input.split(":");
								if(words[1] == null) {
									// List others homes
									String message = ChatColor.GREEN + words[0] + "'s Homes:";
									for(String homeName : home.config.getConfigurationSection("homes." + target.getUniqueId()).getKeys(false)) {
										message += " " + homeName;
									}
									player.sendMessage(message);
								} else {
									// Tp to others home
									if(home.config.getConfigurationSection("homes." + target.getUniqueId() + "." + words[1]) != null) {
										String world = home.config.getString("homes." + target.getUniqueId() + "." + words[1] + ".world");
										double x = home.config.getDouble("homes." + target.getUniqueId() + "." + words[1] + ".x");
										double y = home.config.getDouble("homes." + target.getUniqueId() + "." + words[1] + ".y");
										double z = home.config.getDouble("homes." + target.getUniqueId() + "." + words[1] + ".z");
										float yaw = (float) home.config.getDouble("homes." + target.getUniqueId() + "." + words[1] + ".yaw");
										float pitch = (float) home.config.getDouble("homes." + target.getUniqueId() + "." + words[1] + ".pitch");
										Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
										player.sendMessage(ChatColor.GREEN + "Teleporting to " + words[0] + "'s home '" + words[1] + "'");
										player.teleport(location);
									} else {
										player.sendMessage(ChatColor.RED + "Could not find specified home.");
									}
								}
							} else {
								player.sendMessage(ChatColor.RED + "Could not find specified player.");
							}
							
						} else {
							player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
						}
					} else {
						// Self

						if(home.config.getConfigurationSection("homes." + player.getUniqueId() + "." + input) != null) {
							String world = home.config.getString("homes." + player.getUniqueId() + "." + input + ".world");
							double x = home.config.getDouble("homes." + player.getUniqueId() + "." + input + ".x");
							double y = home.config.getDouble("homes." + player.getUniqueId() + "." + input + ".y");
							double z = home.config.getDouble("homes." + player.getUniqueId() + "." + input + ".z");
							float yaw = (float) home.config.getDouble("homes." + player.getUniqueId() + "." + input + ".yaw");
							float pitch = (float) home.config.getDouble("homes." + player.getUniqueId() + "." + input + ".pitch");
							Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
							player.sendMessage(ChatColor.GREEN + "Teleporting to your home '" + input + "'");
							player.teleport(location);
						} else {
							player.sendMessage(ChatColor.RED + "Could not find specified home.");
						}
					}
				} else {

					if(home.config.getConfigurationSection("homes." + player.getUniqueId()) != null) {
						String message = ChatColor.GREEN + "Homes:";
						for(String homeName : home.config.getConfigurationSection("homes." + player.getUniqueId().toString()).getKeys(false)) {
							message += " " + homeName;
						}
						player.sendMessage(message);
					} else {
						player.sendMessage(ChatColor.RED + "You do not have any homes set.");
					}
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
