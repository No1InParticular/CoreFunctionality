package com.no1inparticular.corefunctionality.referrals.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.no1inparticular.corefunctionality.referrals.Referrals_Main;

public class Join implements Listener {
	
	Referrals_Main referrals;
	public Join(Referrals_Main instance) {
		referrals = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(referrals.config.getConfigurationSection("referrals." + event.getPlayer().getUniqueId()) == null) {
			List<String> placeholder = new ArrayList<String>();
			referrals.config.set("referrals." + event.getPlayer().getUniqueId(), placeholder);
		}
	}

}
