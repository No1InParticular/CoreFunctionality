package com.no1inparticular.corefunctionality.utility;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.event.HandlerList;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.utility.commands.Broadcast;
import com.no1inparticular.corefunctionality.utility.commands.ClearInventory;
import com.no1inparticular.corefunctionality.utility.commands.Enchant;
import com.no1inparticular.corefunctionality.utility.commands.Feed;
import com.no1inparticular.corefunctionality.utility.commands.Fly;
import com.no1inparticular.corefunctionality.utility.commands.Gamemode;
import com.no1inparticular.corefunctionality.utility.commands.Give;
import com.no1inparticular.corefunctionality.utility.commands.Heal;
import com.no1inparticular.corefunctionality.utility.commands.InvSee;
import com.no1inparticular.corefunctionality.utility.commands.Message;
import com.no1inparticular.corefunctionality.utility.commands.Repair;
import com.no1inparticular.corefunctionality.utility.commands.Reply;
import com.no1inparticular.corefunctionality.utility.commands.SetXP;
import com.no1inparticular.corefunctionality.utility.commands.Suicide;
import com.no1inparticular.corefunctionality.utility.events.InvClick;
import com.no1inparticular.corefunctionality.utility.events.Join;
import com.no1inparticular.corefunctionality.utility.events.Leave;

public class Utility_Main extends Module{

	public HashMap<String, String> reply;
	
	InvClick invclickEvent;
	Join joinEvent;
	Leave leaveEvent;
	
	@Override
	public void enable() {
		reply = new HashMap<String, String>();
		((CraftServer) main.getServer()).getCommandMap().register("broadcast", new Broadcast("broadcast", this));
		((CraftServer) main.getServer()).getCommandMap().register("clearinventory", new ClearInventory("clearinventory", this));
		((CraftServer) main.getServer()).getCommandMap().register("enchant", new Enchant("enchant", this));
		((CraftServer) main.getServer()).getCommandMap().register("feed", new Feed("feed", this));
		((CraftServer) main.getServer()).getCommandMap().register("fly", new Fly("fly", this));
		((CraftServer) main.getServer()).getCommandMap().register("gamemode", new Gamemode("gamemode", this));
		((CraftServer) main.getServer()).getCommandMap().register("give", new Give("give", this));
		((CraftServer) main.getServer()).getCommandMap().register("heal", new Heal("heal", this));
		((CraftServer) main.getServer()).getCommandMap().register("invsee", new InvSee("invsee", this));
		((CraftServer) main.getServer()).getCommandMap().register("message", new Message("message", this));
		((CraftServer) main.getServer()).getCommandMap().register("repair", new Repair("repair", this));
		((CraftServer) main.getServer()).getCommandMap().register("reply", new Reply("reply", this));
		((CraftServer) main.getServer()).getCommandMap().register("setxp", new SetXP("setxp", this));
		((CraftServer) main.getServer()).getCommandMap().register("suicide", new Suicide("suicide", this));
		
		invclickEvent = new InvClick(this);
		main.getServer().getPluginManager().registerEvents(invclickEvent, main);

		joinEvent = new Join(this);
		main.getServer().getPluginManager().registerEvents(joinEvent, main);
		
		leaveEvent = new Leave(this);
		main.getServer().getPluginManager().registerEvents(leaveEvent, main);
	}

	@Override
	public void disable() {
		CoreFunctionality.getPlugin().unregisterCommand("broadcast");
		CoreFunctionality.getPlugin().unregisterCommand("clearinventory");
		CoreFunctionality.getPlugin().unregisterCommand("enchant");
		CoreFunctionality.getPlugin().unregisterCommand("feed");
		CoreFunctionality.getPlugin().unregisterCommand("fly");
		CoreFunctionality.getPlugin().unregisterCommand("gamemode");
		CoreFunctionality.getPlugin().unregisterCommand("give");
		CoreFunctionality.getPlugin().unregisterCommand("heal");
		CoreFunctionality.getPlugin().unregisterCommand("invsee");
		CoreFunctionality.getPlugin().unregisterCommand("message");
		CoreFunctionality.getPlugin().unregisterCommand("repair");
		CoreFunctionality.getPlugin().unregisterCommand("reply");
		CoreFunctionality.getPlugin().unregisterCommand("setxp");
		CoreFunctionality.getPlugin().unregisterCommand("suicide");
		
		HandlerList.unregisterAll(invclickEvent);
		HandlerList.unregisterAll(joinEvent);
		HandlerList.unregisterAll(leaveEvent);
	}

	
}
