package com.no1inparticular.corefunctionality.home;

import org.bukkit.craftbukkit.v1_14_R1.CraftServer;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.home.commands.DelHome;
import com.no1inparticular.corefunctionality.home.commands.Home;
import com.no1inparticular.corefunctionality.home.commands.SetHome;

public class Home_Main extends Module{
	
	@Override
	public void enable() {
		
		((CraftServer) main.getServer()).getCommandMap().register("home", new Home("home", this));
		((CraftServer) main.getServer()).getCommandMap().register("delhome", new DelHome("delhome", this));
		((CraftServer) main.getServer()).getCommandMap().register("sethome", new SetHome("sethome", this));
	}

	@Override
	public void disable() {	

		CoreFunctionality.getPlugin().unregisterCommand("home");
		CoreFunctionality.getPlugin().unregisterCommand("delhome");
		CoreFunctionality.getPlugin().unregisterCommand("sethome");
		
	}

}
