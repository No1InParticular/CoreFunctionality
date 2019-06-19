package com.no1inparticular.corefunctionality.referrals;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.event.HandlerList;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.referrals.commands.Refer;
import com.no1inparticular.corefunctionality.referrals.events.Join;

public class Referrals_Main extends Module{
	
	Join joinEvent;
	
	@Override
	public void enable() throws Exception {
		if (!main.setupEconomy() ) {
            main.getLogger().severe("Module disabled due to Vault plugin not being found!");
            throw new Exception();
        }
		((CraftServer) main.getServer()).getCommandMap().register("refer", new Refer("refer", this));
		
		joinEvent = new Join(this);
		Bukkit.getPluginManager().registerEvents(joinEvent, main);
	}

	@Override
	public void disable() {
		CoreFunctionality.getPlugin().unregisterCommand("refer");

		HandlerList.unregisterAll(joinEvent);
	}

}
