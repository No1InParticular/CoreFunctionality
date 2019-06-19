package com.no1inparticular.corefunctionality.warps;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_14_R1.CraftServer;

import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.warps.commands.SetWarp;
import com.no1inparticular.corefunctionality.warps.commands.Warp;

public class Warps_Main extends Module{

	public HashMap<String, String> reply;
	
	@Override
	public void enable() {
		((CraftServer) main.getServer()).getCommandMap().register("warp", new Warp("warp", this));
		((CraftServer) main.getServer()).getCommandMap().register("setwarp", new SetWarp("setwarp", this));
	}

	@Override
	public void disable() {	}

	
}
