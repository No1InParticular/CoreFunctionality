package com.no1inparticular.corefunctionality.afk.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.no1inparticular.corefunctionality.afk.AFK_Main;

import net.md_5.bungee.api.ChatColor;

public class Interact implements Listener {

	AFK_Main afk;
	public Interact(AFK_Main instance) {
		afk = instance;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(afk.list.contains(player.getName())) {
			afk.unsetAFK(player.getName()); // Send the user a message saying they are no longer afk (since they interacted)
			player.sendMessage(ChatColor.GREEN + "You are no longer afk");
			player.setCollidable(true);
		}
	}
}
