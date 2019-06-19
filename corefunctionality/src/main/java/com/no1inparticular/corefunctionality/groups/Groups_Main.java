package com.no1inparticular.corefunctionality.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.PermissionAttachment;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.groups.commands.AddInheritance;
import com.no1inparticular.corefunctionality.groups.commands.AddPerm;
import com.no1inparticular.corefunctionality.groups.commands.AddRank;
import com.no1inparticular.corefunctionality.groups.commands.RemoveInheritance;
import com.no1inparticular.corefunctionality.groups.commands.RemovePerm;
import com.no1inparticular.corefunctionality.groups.commands.SetPrefix;
import com.no1inparticular.corefunctionality.groups.commands.SetRank;
import com.no1inparticular.corefunctionality.groups.events.Join;
import com.no1inparticular.corefunctionality.groups.events.Leave;

public class Groups_Main extends Module {

	public List<Rank> ranks;
	public HashMap<String,PermissionAttachment> attachments;
	
	Join joinEvent;
	Leave leaveEvent;
	
	@Override
	public void enable() {
		ranks = new ArrayList<Rank>();
		attachments = new HashMap<String,PermissionAttachment>();
		
		joinEvent = new Join(this);
		main.getServer().getPluginManager().registerEvents(joinEvent, main);

		leaveEvent = new Leave(this);
		main.getServer().getPluginManager().registerEvents(leaveEvent, main);
		
		((CraftServer) main.getServer()).getCommandMap().register("setrank", new SetRank("setrank", this));
		
		((CraftServer) main.getServer()).getCommandMap().register("addrank", new AddRank("addrank", this));
		
		((CraftServer) main.getServer()).getCommandMap().register("addperm", new AddPerm("addperm", this));
		
		((CraftServer) main.getServer()).getCommandMap().register("removeperm", new RemovePerm("removeperm", this));
		
		((CraftServer) main.getServer()).getCommandMap().register("addinheritance", new AddInheritance("addinheritance", this));
		
		((CraftServer) main.getServer()).getCommandMap().register("removeinheritance", new RemoveInheritance("removeinheritance", this));
		
		((CraftServer) main.getServer()).getCommandMap().register("setprefix", new SetPrefix("setprefix", this));
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			createAttachment(p);
		}
	}
	
	public void createAttachment(Player player) {
		Rank rank = getRank(player.getUniqueId().toString());
		PermissionAttachment attachment = player.addAttachment(main);
		if(rank != null) {
			for(String perm : rank.permissions) {
				attachment.setPermission(perm, true);
			}
		} else {
			for(Rank r : ranks) {
				if(r.name.equals("Default")) {
					r.members.add(player.getUniqueId().toString());
					for(String perm : r.permissions) {
						attachment.setPermission(perm, true);
					}
				}
			}
		}
		attachments.put(player.getUniqueId().toString(), attachment);
	}
	
	public void removeAttachment(Player player) {
		player.removeAttachment(attachments.get(player.getUniqueId().toString()));
		attachments.remove(player.getUniqueId().toString());
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		CoreFunctionality.getPlugin().unregisterCommand("setrank");
		CoreFunctionality.getPlugin().unregisterCommand("addrank");
		CoreFunctionality.getPlugin().unregisterCommand("addperm");
		CoreFunctionality.getPlugin().unregisterCommand("removeperm");
		CoreFunctionality.getPlugin().unregisterCommand("addinheritance");
		CoreFunctionality.getPlugin().unregisterCommand("removeinheritance");
		CoreFunctionality.getPlugin().unregisterCommand("setprefix");
		
		HandlerList.unregisterAll(joinEvent);
		HandlerList.unregisterAll(leaveEvent);
	}
	
	public void recalculatePerms() {
		Bukkit.getScheduler().runTaskAsynchronously(main, new Runnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					//Remove the stuff
					if(attachments.get(player.getUniqueId().toString()) != null) {
						player.removeAttachment(attachments.get(player.getUniqueId().toString()));
						attachments.remove(player.getUniqueId().toString());
					}
					
					//Re-add it
					Rank rank = getRank(player.getUniqueId().toString());
					PermissionAttachment attachment = player.addAttachment(main);
					for(String perm : rank.permissions) {
						attachment.setPermission(perm, true);
					}
					for(String inheritRank : rank.inheritance) {
						for(Rank r : ranks) {
							if(r.name.equals(inheritRank)) {
								for(String perm : r.permissions) {
									attachment.setPermission(perm, true);
								}
							}
						}
					}
					//Add inheritance
					attachments.put(player.getUniqueId().toString(), attachment);
				}
			}
		});
		
	}
	
	public Rank getRank(String uuid) {
		Rank toReturn = null;
		for(Rank rank : ranks) {
			if(rank.members.contains(uuid)) {
				toReturn = rank;
				break;
			}
		}
		return toReturn;
	}
	
	public void loadRanks() {
		for(String rankName : config.getConfigurationSection("ranks").getKeys(false)) {
			String prefix = config.getString("ranks." + rankName + ".prefix");
			List<String> permissions = config.getStringList("ranks." + rankName + ".permissions");
			List<String> inheritance = config.getStringList("ranks." + rankName + ".inheritance");
			List<String> members = config.getStringList("ranks." + rankName + ".members");
			
			Rank rank = new Rank(rankName, prefix);
			rank.permissions = permissions;
			rank.inheritance = inheritance;
			rank.members = members;
			ranks.add(rank);
		}
		for(Player player : Bukkit.getOnlinePlayers()) {
			Rank rank = getRank(player.getUniqueId().toString());
			PermissionAttachment attachment = player.addAttachment(main);
			if(rank != null) {
				for(String perm : rank.permissions) {
					attachment.setPermission(perm, true);
				}
				for(String inheritRank : rank.inheritance) {
					for(Rank r : ranks) {
						if(r.name.equals(inheritRank)) {
							for(String perm : r.permissions) {
								attachment.setPermission(perm, true);
							}
						}
					}
				}
			}
			attachments.put(player.getUniqueId().toString(), attachment);
		}
	}
	
	public void saveRanks() {
		config.set("ranks", null);
		for(Rank rank : ranks) {
			config.set("ranks." + rank.name + ".prefix", rank.prefix);
			config.set("ranks." + rank.name + ".permissions", rank.permissions);
			config.set("ranks." + rank.name + ".inheritance", rank.inheritance);
			config.set("ranks." + rank.name + ".members", rank.members);
		}
		saveConfig();
	}
}
