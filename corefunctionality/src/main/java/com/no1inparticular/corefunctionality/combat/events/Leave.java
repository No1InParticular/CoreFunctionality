package com.no1inparticular.corefunctionality.combat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.no1inparticular.corefunctionality.combat.Combat_Main;

public class Leave implements Listener {
	
	Combat_Main combat;
	public Leave(Combat_Main instance) {
		combat = instance;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(combat.tagged.containsKey(player.getName())) {
			combat.tagged.remove(player.getName());
			combat.left.add(player.getName()); // Since they left the game remove them from tagged, add them to left and damage them more than their health to kill them
			player.damage(player.getHealth() + 1); 
		}
	}

}
