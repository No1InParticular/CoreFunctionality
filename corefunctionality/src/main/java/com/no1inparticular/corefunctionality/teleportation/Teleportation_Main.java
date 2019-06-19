package com.no1inparticular.corefunctionality.teleportation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.craftbukkit.v1_14_R1.CraftServer;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.teleportation.commands.TP;
import com.no1inparticular.corefunctionality.teleportation.commands.TPA;
import com.no1inparticular.corefunctionality.teleportation.commands.TPAHere;
import com.no1inparticular.corefunctionality.teleportation.commands.TPAccept;
import com.no1inparticular.corefunctionality.teleportation.commands.TPDeny;
import com.no1inparticular.corefunctionality.teleportation.commands.TPHere;

public class Teleportation_Main extends Module{

	public HashMap<String, tpRequest> tpRequests;
	public List<String> cooldown;
	
	@Override
	public void enable() {
		tpRequests = new HashMap<String, tpRequest>();
		cooldown = new ArrayList<String>();
		
		((CraftServer) main.getServer()).getCommandMap().register("tp", new TP("tp", this));
		((CraftServer) main.getServer()).getCommandMap().register("tphere", new TPHere("tphere", this));
		((CraftServer) main.getServer()).getCommandMap().register("tpa", new TPA("tpa", this));
		((CraftServer) main.getServer()).getCommandMap().register("tpaccept", new TPAccept("tpaccept", this));
		((CraftServer) main.getServer()).getCommandMap().register("tpahere", new TPAHere("tpahere", this));
		((CraftServer) main.getServer()).getCommandMap().register("tpdeny", new TPDeny("tpdeny", this));
	}

	@Override
	public void disable() {
		CoreFunctionality.getPlugin().unregisterCommand("tp");
		CoreFunctionality.getPlugin().unregisterCommand("tphere");
		CoreFunctionality.getPlugin().unregisterCommand("tpa");
		CoreFunctionality.getPlugin().unregisterCommand("tpaccept");
		CoreFunctionality.getPlugin().unregisterCommand("tpahere");
		CoreFunctionality.getPlugin().unregisterCommand("tpdeny");

	}

}
