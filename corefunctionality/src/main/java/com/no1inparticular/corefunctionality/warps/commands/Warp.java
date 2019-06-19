package com.no1inparticular.corefunctionality.warps.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.combat.Combat_Main;
import com.no1inparticular.corefunctionality.warps.Warps_Main;

import net.md_5.bungee.api.ChatColor;

public class Warp extends BukkitCommand {
	
	Warps_Main warps;
	public Warp(String name, Warps_Main instance) {
        super(name);
        this.description = "Warp to a designated point";
        this.usageMessage = "/warp";
        this.setPermission("corefunctionality.warp.use");
        this.setAliases(new ArrayList<String>());
		warps = instance;
	}
	
	@Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("corefunctionality.warp.use")) {
				
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
				
				if (args.length >= 1) {
					String warp = args[0].toLowerCase();
					if (warps.config.getConfigurationSection("warps." + warp) != null) {
						if (player.hasPermission("server.warp.use." + warp)) {
							player.sendMessage(ChatColor.GREEN + "Warping to " + warp + "...");
							World world = Bukkit.getWorld(warps.config.getString("warps." + warp + ".world"));
							double x = warps.config.getDouble("warps." + warp + ".x");
							double y = warps.config.getDouble("warps." + warp + ".y");
							double z = warps.config.getDouble("warps." + warp + ".z");
							float yaw = (float) warps.config.getDouble("warps." + warp + ".yaw");
							float pitch = (float) warps.config.getDouble("warps." + warp + ".pitch");
							player.teleport(new Location(world, x, y, z, yaw, pitch));
						} else {
							player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "This warp does not exist.");
					}
				} else {
					if (warps.config.getConfigurationSection("warps") != null) {
						String warpList = ChatColor.GREEN + "Warps:\n";
						boolean canWarp = false;
						for(String warp : warps.config.getConfigurationSection("warps").getKeys(false)) {
							if (player.hasPermission("server.warp.use." + warp)) {
								warpList += warp + ", ";
								canWarp = true;
							}
						}
						warpList = warpList.substring(0, warpList.length() - 2);
						if(!canWarp) {
							player.sendMessage(ChatColor.RED + "There is no warps you have access to.");
						} else {
							player.sendMessage(warpList);
						}
					} else {
						player.sendMessage(ChatColor.RED + "There is no set warps.");
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
