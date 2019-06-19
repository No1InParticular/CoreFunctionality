package com.no1inparticular.corefunctionality.combat.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.no1inparticular.corefunctionality.combat.Combat_Main;

import net.md_5.bungee.api.ChatColor;

public class Join implements Listener {
	
	Combat_Main combat;
	public Join(Combat_Main instance) {
		combat = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if(combat.left.contains(player.getName())) {
			Bukkit.getScheduler().runTaskLater(combat.main, new Runnable() {
				public void run() {
					player.sendMessage(ChatColor.RED + "You was killed because you left during combat...");
					combat.left.remove(player.getName());
				}
			}, 1*20);
		}
	}

}
