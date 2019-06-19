package com.no1inparticular.corefunctionality.groups.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.no1inparticular.corefunctionality.groups.Groups_Main;

public class Join implements Listener {

	Groups_Main groups;
	public Join(Groups_Main instance) {
		groups = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		groups.createAttachment(event.getPlayer());
	}
}
