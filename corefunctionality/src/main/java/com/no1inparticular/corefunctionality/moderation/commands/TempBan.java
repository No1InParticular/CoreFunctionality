package com.no1inparticular.corefunctionality.moderation.commands;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.no1inparticular.corefunctionality.moderation.Moderation_Main;

import net.md_5.bungee.api.ChatColor;

public class TempBan  extends BukkitCommand {
	
	Moderation_Main moderation;
	public TempBan(String name, Moderation_Main instance) {
		super(name);
		this.description = "Temporarily ban a player";
		this.usageMessage = "/tempban <player> <hours / days:hours> <reason>";
		this.setPermission("corefunctionality.afk.use");
		moderation = instance;
	}
	
	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if(sender.hasPermission("server.moderation.tempban")) {
			if(args.length > 2) {
				String targetName = args[0];
				String length = args[1];
				String[] time = length.split(":");
				int days = 0;
				int hours = 0;
				try{
					Date unbanDate = null;
					if(time.length == 1) {
						// hours
						hours = Integer.parseInt(time[0]);
						Calendar cal = Calendar.getInstance(); // creates calendar
					    cal.setTime(new Date()); // sets calendar time/date
					    cal.add(Calendar.HOUR_OF_DAY, hours); // adds one hour
					    unbanDate = cal.getTime();
					} else {
						// days:hours
						days = Integer.parseInt(time[0]);
						hours = Integer.parseInt(time[1]);
						Calendar cal = Calendar.getInstance(); // creates calendar
					    cal.setTime(new Date()); // sets calendar time/date
					    cal.add(Calendar.DAY_OF_MONTH, days); // adds days
					    cal.add(Calendar.HOUR_OF_DAY, hours); // adds hours
					    unbanDate = cal.getTime();
					}
					String reason = args[2];
					for(int x = 3; x < args.length; x++) {
						reason += " " + args[x];
					}
					if(Bukkit.getPlayer(targetName) != null) {
						Player target = Bukkit.getPlayer(targetName);
						target.kickPlayer(ChatColor.DARK_RED + "You were banned for: " + ChatColor.WHITE  + reason + ChatColor.DARK_RED + "\nDuration: " + ChatColor.WHITE + days + " days & " + hours + " hours.");
						Bukkit.getBanList(Type.NAME).addBan(target.getName(), reason, unbanDate, sender.getName());
						Bukkit.getBanList(Type.IP).addBan(target.getAddress().toString(), reason, unbanDate, sender.getName());
						sender.sendMessage(ChatColor.GREEN + "Player along with their IP has been banned.");
					} else {
						Bukkit.getBanList(Type.NAME).addBan(targetName, reason, unbanDate, sender.getName());
						sender.sendMessage(ChatColor.RED + "Player has been banned (not ip) although they are not currently online, check the username.");
					}
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Invalid time specified.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Syntax Error: /tempban <player> <hours / days:hours> <reason>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
		}
		return false;
	}

}
