package com.no1inparticular.corefunctionality.spawn.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.no1inparticular.corefunctionality.spawn.Spawn_Main;

public class Join implements Listener {
	
	Spawn_Main spawn;
	public Join(Spawn_Main instance) {
		spawn = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!player.hasPlayedBefore()) {
			World world = Bukkit.getWorld(spawn.config.getString("world"));
			double x = spawn.config.getDouble("x");
			double y = spawn.config.getDouble("y");
			double z = spawn.config.getDouble("z");
			float yaw = (float) spawn.config.getDouble("yaw");
			float pitch = (float) spawn.config.getDouble("pitch");
			player.teleport(new Location(world, x, y, z, yaw, pitch));
		}
	}
}
