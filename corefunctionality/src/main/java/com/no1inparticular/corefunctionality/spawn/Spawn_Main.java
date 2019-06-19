package com.no1inparticular.corefunctionality.spawn;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.spawn.commands.SetSpawn;
import com.no1inparticular.corefunctionality.spawn.commands.Spawn;
import com.no1inparticular.corefunctionality.spawn.events.Join;

public class Spawn_Main extends Module{

	public HashMap<String, Inventory> shopInvs;
	public HashMap<String, Inventory> categoryInvs;
	
	Join joinEvent;
	
	@Override
	public void enable() {
		config.addDefault("world", "World");
		config.addDefault("x", 1.0);
		config.addDefault("y", 64.0);
		config.addDefault("z", 1.0);
		config.addDefault("yaw", 0);
		config.addDefault("pitch", 0);
		config.options().copyDefaults(true);
		saveConfig();
		
		((CraftServer) main.getServer()).getCommandMap().register("spawn", new Spawn("spawn", this));
		((CraftServer) main.getServer()).getCommandMap().register("setspawn", new SetSpawn("setspawn", this));
		
		joinEvent = new Join(this);
		main.getServer().getPluginManager().registerEvents(joinEvent, main);
	}

	@Override
	public void disable() {
		CoreFunctionality.getPlugin().unregisterCommand("spawn");
		CoreFunctionality.getPlugin().unregisterCommand("setspawn");

		HandlerList.unregisterAll(joinEvent);
	}

	
}
