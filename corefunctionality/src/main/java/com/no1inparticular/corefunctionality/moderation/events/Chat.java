package com.no1inparticular.corefunctionality.moderation.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.no1inparticular.corefunctionality.moderation.Moderation_Main;

public class Chat  implements Listener {

	Moderation_Main moderation;
	public Chat(Moderation_Main instance) {
		moderation = instance;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(moderation.muted.contains(player.getUniqueId().toString())) {
			event.getRecipients().clear();
			event.getRecipients().add(event.getPlayer()); // Since they are muted clear who it should be sent to and only add them, so they still think they spoke but no one else sees it
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.hasPermission("corefunctionality.moderation.mute.listen")) {
					p.sendMessage(ChatColor.GRAY + "[MUTED] " + player.getName() + " >> " + event.getMessage());
				}
			}
		}
	}

}
