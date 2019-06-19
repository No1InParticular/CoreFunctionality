package com.no1inparticular.corefunctionality.afk;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.event.HandlerList;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.afk.commands.AFK;
import com.no1inparticular.corefunctionality.afk.events.Chat;
import com.no1inparticular.corefunctionality.afk.events.Damage;
import com.no1inparticular.corefunctionality.afk.events.Interact;
import com.no1inparticular.corefunctionality.afk.events.Move;

public class AFK_Main extends Module {

	// Instances of classes to unregister incase of disable by command
	Chat chatEvent;
	Damage damageEvent;
	Interact interactEvent;
	Move moveEvent;
	
	public List<String> list;
	public List<String> temp;
	
	@Override // Overriding the enable module to define what needs to be loaded
	public void enable() {
		list = new ArrayList<String>();
		temp = new ArrayList<String>(); // Create the array lists to store the players
		
		((CraftServer) main.getServer()).getCommandMap().register("afk", new AFK("afk", this)); // Register the commands class (what it should run)
		
		chatEvent = new Chat(this);
		main.getServer().getPluginManager().registerEvents(chatEvent, main);
		
		damageEvent = new Damage(this);
		main.getServer().getPluginManager().registerEvents(damageEvent, main); // Register the event classes with the server
		
		interactEvent = new Interact(this);
		main.getServer().getPluginManager().registerEvents(interactEvent, main);
		
		moveEvent = new Move(this);
		main.getServer().getPluginManager().registerEvents(moveEvent, main);
	}

	@Override
	public void disable() {
		// Clear the lists just to prevent any chance of memory leaks
		list.clear();
		temp.clear();
		
		CoreFunctionality.getPlugin().unregisterCommand("afk");
		
		HandlerList.unregisterAll(chatEvent);
		HandlerList.unregisterAll(damageEvent);
		HandlerList.unregisterAll(interactEvent);
		HandlerList.unregisterAll(moveEvent);
		
	}
	
	// Extra methods required for this module

	public void setAFK(String name) {
		list.add(name);
	}
	
	public void unsetAFK(final String name) {
		list.remove(name);
		temp.add(name); // Add them to the temp list and set a delayed runnable to remove them after a little bit
		Bukkit.getScheduler().runTaskLaterAsynchronously(main, new Runnable() {
			public void run() {
				temp.remove(name);
			}
		}, 5*20); // Game runs in ticks, 20 ticks is one second so 5 seconds
	}
}
