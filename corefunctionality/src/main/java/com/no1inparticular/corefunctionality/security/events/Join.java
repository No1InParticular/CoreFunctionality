package com.no1inparticular.corefunctionality.security.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.no1inparticular.corefunctionality.security.Security_Main;

public class Join implements Listener {
	
	Security_Main security;
	public Join(Security_Main instance) {
		security = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String ip = event.getPlayer().getAddress().toString().split(":")[0].replaceAll("\\.", ",");
		UUID uuid = event.getPlayer().getUniqueId();
		
		List<String> accounts = security.config.getStringList("accounts." + ip);
		if(accounts.contains(uuid.toString())) {
			// Already logged in on this IP
		} else if(accounts == null || accounts.size() == 0) {
			accounts = new ArrayList<String>();
			accounts.add(uuid.toString());
			security.config.set("accounts." + ip, accounts);
			security.saveConfig();
		} else if(accounts.size() == 1) {
			accounts.add(uuid.toString());
			security.config.set("accounts." + ip, accounts);
			security.saveConfig();
		} else {
			event.getPlayer().kickPlayer("You have exceeded your account limit for this IP address. If you require more please contact the owner.");
		}
	}

}
