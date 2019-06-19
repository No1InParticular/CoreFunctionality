package com.no1inparticular.corefunctionality.utility.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.no1inparticular.corefunctionality.utility.Utility_Main;

import net.md_5.bungee.api.ChatColor;

public class InvClick implements Listener {
	
	Utility_Main utility;
	public InvClick(Utility_Main instance) {
		utility = instance;
	}
	
	@EventHandler
	public void InvClickEvent(InventoryClickEvent event) {
		if(event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof Player) {
			if(event.getClickedInventory() != null && !event.getWhoClicked().equals(event.getClickedInventory().getHolder())) {
				Player player = (Player) event.getWhoClicked();
				if(!player.hasPermission("server.utility.invsee.edit")) {
					// ^^ IS NEGATED
					player.sendMessage(ChatColor.RED + "You do not have the power to do this.");
					event.setCancelled(true);
				}
			}
		}
	}
	
}
