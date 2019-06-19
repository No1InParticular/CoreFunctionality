package com.no1inparticular.corefunctionality.chat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.chat.Chat_Main;
import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.groups.Rank;

import net.md_5.bungee.api.ChatColor;

public class PlayerChat implements Listener {

	Chat_Main chat;
	public PlayerChat(Chat_Main instance) {
		chat = instance;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String prefix = ""; // For when groups isnt enabled it is blank so no effect
		
		// Checking if groups exists and is enabled
		if(CoreFunctionality.getPlugin().modules.containsKey("Groups") && CoreFunctionality.getPlugin().modules.get("Groups").enabled) {
			// If the combat module exists and is enabled then try get the players rank
			Rank rank = ((Groups_Main)CoreFunctionality.getPlugin().modules.get("Groups")).getRank(event.getPlayer().getUniqueId().toString());
			
			
			if(rank != null) {
				if(rank.prefix != null) {
					// If the rank is not null and the rank has a prefix
					prefix = ChatColor.translateAlternateColorCodes('&', "{tag}" + rank.prefix + " ");
				} else {
					// Has a rank but rank doesnt have prefix, so set it to same as default but with the rank name
					prefix = ChatColor.translateAlternateColorCodes('&', "{tag}&8[&7" + rank.name + "&8] ");
				}
			} else {
				// If no rank or no prefix
				prefix = ChatColor.translateAlternateColorCodes('&', "{tag}&8[&7Default&8] ");
			}
		}
		
		// Set the message format for the chat event 
		event.setFormat(prefix + " " + ChatColor.GRAY + player.getName() + ChatColor.DARK_GRAY + " >> " + ChatColor.WHITE + "{cc}" + event.getMessage().replace("%", "%%"));
		
	}
}
