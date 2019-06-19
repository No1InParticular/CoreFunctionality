package com.no1inparticular.corefunctionality.spawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.combat.Combat_Main;
import com.no1inparticular.corefunctionality.spawn.Spawn_Main;

import net.md_5.bungee.api.ChatColor;

public class Spawn extends BukkitCommand {
	
	Spawn_Main spawn; 
	public Spawn(String name, Spawn_Main instance) {
		super(name);
		this.description = "Teleport to the spawn point";
		this.usageMessage = "/spawn";
		this.setPermission("corefunctionality.spawn.use");
		spawn = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.spawn.use")) {
				
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
				
				player.sendMessage(ChatColor.GREEN + "Teleporting to spawn...");
				World world = Bukkit.getWorld(spawn.config.getString("world"));
				double x = spawn.config.getDouble("x");
				double y = spawn.config.getDouble("y");
				double z = spawn.config.getDouble("z");
				float yaw = (float) spawn.config.getDouble("yaw");
				float pitch = (float) spawn.config.getDouble("pitch");
				player.teleport(new Location(world, x, y, z, yaw, pitch));
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command");
		}
		return false;
	}

}
