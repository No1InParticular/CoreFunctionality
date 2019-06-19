package com.no1inparticular.corefunctionality.shops.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.no1inparticular.corefunctionality.shops.Shops_Main;

import net.citizensnpcs.api.event.NPCRightClickEvent;

public class NPCInteract implements Listener {
	
	Shops_Main shops;
	public NPCInteract(Shops_Main instance) {
		shops = instance;
	}
	
	@EventHandler
	public void onPlayerInteractNPC(NPCRightClickEvent event) {
		String NPCname = event.getNPC().getName();
		Player player = event.getClicker();
		for (String key : shops.categoryInvs.keySet()) {
			if (NPCname.contains(key)) {
				player.openInventory(shops.categoryInvs.get(key));
			}
		}
	}
}
