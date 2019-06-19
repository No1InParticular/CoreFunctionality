package com.no1inparticular.corefunctionality.spawn.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.spawn.Spawn_Main;

import net.md_5.bungee.api.ChatColor;

public class SetSpawn extends BukkitCommand {
	
	Spawn_Main spawn; 
	public SetSpawn(String name, Spawn_Main instance) {
		super(name);
		this.description = "Set the spawn point";
		this.usageMessage = "/setspawn";
		this.setPermission("corefunctionality.spawn.set");
		spawn = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("corefunctionality.spawn.set")) {
				Location loc = player.getLocation();
				spawn.config.set("world", loc.getWorld().getName());
				spawn.config.set("x", loc.getX());
				spawn.config.set("y", loc.getY());
				spawn.config.set("z", loc.getZ());
				spawn.config.set("yaw", loc.getYaw());
				spawn.config.set("pitch", loc.getPitch());
				player.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
				player.getWorld().getSpawnLocation().setYaw(loc.getYaw());
				player.getWorld().getSpawnLocation().setPitch(loc.getPitch());
				player.sendMessage(ChatColor.GREEN + "Spawn has been set.");
				spawn.saveConfig();
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command");
		}
		return false;
	}

}
