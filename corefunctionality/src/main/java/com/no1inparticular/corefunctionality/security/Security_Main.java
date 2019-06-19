package com.no1inparticular.corefunctionality.security;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.security.events.Join;

public class Security_Main extends Module{
	
	Join joinEvent;
	
	@Override
	public void enable() {
		joinEvent = new Join(this);
		Bukkit.getPluginManager().registerEvents(joinEvent, main);
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(joinEvent);
	}

}
