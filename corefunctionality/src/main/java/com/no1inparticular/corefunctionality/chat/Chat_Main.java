package com.no1inparticular.corefunctionality.chat;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.chat.events.PlayerChat;

public class Chat_Main extends Module {
	
	PlayerChat chatEvent;
	
	@Override
	public void enable() {
		chatEvent = new PlayerChat(this);
		Bukkit.getPluginManager().registerEvents(chatEvent, main);
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		HandlerList.unregisterAll(chatEvent);
	}

}
