package com.no1inparticular.corefunctionality.combat.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitTask;

import com.no1inparticular.corefunctionality.combat.Combat_Main;

import net.md_5.bungee.api.ChatColor;

public class PlayerHitPlayer implements Listener {
	
	Combat_Main combat;
	public PlayerHitPlayer(Combat_Main instance) {
		combat = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerHitPlayer(EntityDamageByEntityEvent event) {
		final Entity attacker = event.getDamager();
		final Entity target = event.getEntity();
		if(event.isCancelled()) {
			return;
		}
		if( (attacker instanceof Player && ((Player) attacker).getGameMode() == GameMode.CREATIVE) || (target instanceof Player && ((Player) target).getGameMode() == GameMode.CREATIVE) ) {
			return;
		}
		if(target instanceof Player) {
			if(combat.tagged.containsKey(target.getName())) {
				combat.tagged.get(target.getName()).cancel();
				combat.tagged.remove(target.getName());
				BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(combat.main, new Runnable() {
					public void run() {
						target.sendMessage(ChatColor.GREEN + "You are no longer tagged in combat.");
						combat.tagged.remove(target.getName());
					}
				}, 10*20);
				combat.tagged.put(target.getName(), task);
			} else {
				target.sendMessage(ChatColor.RED + "You have been tagged in combat!");
				BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(combat.main, new Runnable() {
					public void run() {
						target.sendMessage(ChatColor.GREEN + "You are no longer tagged in combat.");
						combat.tagged.remove(target.getName());
					}
				}, 10*20);
				combat.tagged.put(target.getName(), task);
			}
		}
			
		if(attacker instanceof Player) {
			if(combat.tagged.containsKey(attacker.getName())) {
				combat.tagged.get(attacker.getName()).cancel();
				combat.tagged.remove(attacker.getName());
				BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(combat.main, new Runnable() {
					public void run() {
						attacker.sendMessage(ChatColor.GREEN + "You are no longer tagged in combat.");
						combat.tagged.remove(attacker.getName());
					}
				}, 10*20);
				combat.tagged.put(attacker.getName(), task);
			} else {
				attacker.sendMessage(ChatColor.RED + "You have been tagged in combat!");
				BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(combat.main, new Runnable() {
					public void run() {
						attacker.sendMessage(ChatColor.GREEN + "You are no longer tagged in combat.");
						combat.tagged.remove(attacker.getName());
					}
				}, 10*20);
				combat.tagged.put(attacker.getName(), task);
			}
		}
	}

}
