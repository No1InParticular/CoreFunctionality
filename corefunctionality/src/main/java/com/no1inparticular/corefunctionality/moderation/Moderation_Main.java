package com.no1inparticular.corefunctionality.moderation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.event.HandlerList;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.moderation.commands.Ban;
import com.no1inparticular.corefunctionality.moderation.commands.Kick;
import com.no1inparticular.corefunctionality.moderation.commands.Kill;
import com.no1inparticular.corefunctionality.moderation.commands.Mute;
import com.no1inparticular.corefunctionality.moderation.commands.TempBan;
import com.no1inparticular.corefunctionality.moderation.events.Chat;

public class Moderation_Main extends Module{

	public List<String> muted;
	
	Chat chatEvent;
	
	@Override
	public void enable() {
		muted = new ArrayList<String>();
		((CraftServer) main.getServer()).getCommandMap().register("ban", new Ban("ban", this));
		((CraftServer) main.getServer()).getCommandMap().register("kick", new Kick("kick", this));
		((CraftServer) main.getServer()).getCommandMap().register("kill", new Kill("kill", this));
		((CraftServer) main.getServer()).getCommandMap().register("mute", new Mute("mute", this));
		((CraftServer) main.getServer()).getCommandMap().register("tempban", new TempBan("tempban", this));
		
		chatEvent = new Chat(this);
		main.getServer().getPluginManager().registerEvents(chatEvent, main);
	}

	@Override
	public void disable() {

		CoreFunctionality.getPlugin().unregisterCommand("ban");
		CoreFunctionality.getPlugin().unregisterCommand("kick");
		CoreFunctionality.getPlugin().unregisterCommand("kill");
		CoreFunctionality.getPlugin().unregisterCommand("mute");
		CoreFunctionality.getPlugin().unregisterCommand("tempban");
		
		HandlerList.unregisterAll(chatEvent);
	}

}
