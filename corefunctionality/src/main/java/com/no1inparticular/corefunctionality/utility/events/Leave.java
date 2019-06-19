package com.no1inparticular.corefunctionality.utility.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

public class Leave implements Listener {
	
	Utility_Main utility;
	public Leave(Utility_Main instance) {
		utility = instance;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c-&8] &7" + player.getName() + " has left the game."));
	}
	
}
