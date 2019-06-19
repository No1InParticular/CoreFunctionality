package com.no1inparticular.corefunctionality.groups.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.no1inparticular.corefunctionality.groups.Groups_Main;

public class Leave implements Listener {

	Groups_Main groups;
	public Leave(Groups_Main instance) {
		groups = instance;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		groups.removeAttachment(event.getPlayer());
	}
}
