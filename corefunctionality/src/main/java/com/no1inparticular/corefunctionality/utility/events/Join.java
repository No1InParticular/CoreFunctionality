package com.no1inparticular.corefunctionality.utility.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class Join implements Listener {
	
	Utility_Main utility;
	public Join(Utility_Main instance) {
		utility = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5+&8] &7" + player.getName() + " has joined the game."));
		if(!player.hasPlayedBefore()) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "Welcome to the server " + player.getName() + "!");
		}
	}
	
}
