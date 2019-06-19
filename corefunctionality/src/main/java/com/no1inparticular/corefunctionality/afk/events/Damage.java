package com.no1inparticular.corefunctionality.afk.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.no1inparticular.corefunctionality.afk.AFK_Main;

import net.md_5.bungee.api.ChatColor;

public class Damage implements Listener {

	AFK_Main afk;
	public Damage(AFK_Main instance) {
		afk = instance;
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(afk.list.contains(player.getName())) {
				event.setCancelled(true); // Stop people from hurting afk players, since they are not there
				if(event.getDamager() instanceof Player) {
					Player damager = (Player) event.getDamager();
					damager.sendMessage(ChatColor.RED + "You cannot hurt AFK players.");
				}
			} else if(afk.temp.contains(player.getName())) {
				event.setCancelled(true);
				if(event.getDamager() instanceof Player) {  // Stop people from hurting players that were recently afk
					Player damager = (Player) event.getDamager();
					damager.sendMessage(ChatColor.RED + "You cannot hurt players that have recently been AFK.");
				}
			}
			
			if(event.getDamager() instanceof Player && (afk.list.contains(event.getDamager().getName()) || afk.temp.contains(event.getDamager().getName())) ) {
				event.setCancelled(true);
				event.getDamager().sendMessage(ChatColor.RED + "You were recently AFK, please wait before harming anything."); 
				// Stops people attacking things when they are in grace period from being afk
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByBlockEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(afk.list.contains(player.getName())) {
				event.setCancelled(true); // Stop afk players taking block damage
			}
		}
	}
	
	@EventHandler
	public void onTakeDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(afk.list.contains(player.getName())) { // Stop afk players taking general damage
				event.setCancelled(true);
			}
		}
	}
}
