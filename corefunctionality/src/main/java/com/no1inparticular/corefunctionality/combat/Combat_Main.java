package com.no1inparticular.corefunctionality.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitTask;

import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.combat.events.Join;
import com.no1inparticular.corefunctionality.combat.events.Leave;
import com.no1inparticular.corefunctionality.combat.events.PlayerHitPlayer;

public class Combat_Main extends Module{

	public HashMap<String, BukkitTask> tagged;
	public List<String> left;
	
	Leave leaveEvent;
	Join joinEvent;
	PlayerHitPlayer phpEvent;
	
	@Override
	public void enable() {
		tagged = new HashMap<String, BukkitTask>();
		left = new ArrayList<String>();
		
		leaveEvent = new Leave(this);
		main.getServer().getPluginManager().registerEvents(leaveEvent, main);
		
		joinEvent = new Join(this);
		main.getServer().getPluginManager().registerEvents(joinEvent, main);
		
		phpEvent = new PlayerHitPlayer(this);
		main.getServer().getPluginManager().registerEvents(phpEvent, main);
	}

	@Override
	public void disable() {
		// Clear list and hashmap to prevent memory leaks
		tagged.clear();
		left.clear();
		
		HandlerList.unregisterAll(leaveEvent);
		HandlerList.unregisterAll(joinEvent);
		HandlerList.unregisterAll(phpEvent);
	}

}
