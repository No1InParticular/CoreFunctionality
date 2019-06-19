package com.no1inparticular.corefunctionality;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.no1inparticular.corefunctionality.afk.AFK_Main;
import com.no1inparticular.corefunctionality.chat.Chat_Main;
import com.no1inparticular.corefunctionality.combat.Combat_Main;
import com.no1inparticular.corefunctionality.groups.Groups_Main;
import com.no1inparticular.corefunctionality.home.Home_Main;
import com.no1inparticular.corefunctionality.moderation.Moderation_Main;
import com.no1inparticular.corefunctionality.referrals.Referrals_Main;
import com.no1inparticular.corefunctionality.security.Security_Main;
import com.no1inparticular.corefunctionality.shops.Shops_Main;
import com.no1inparticular.corefunctionality.spawn.Spawn_Main;
import com.no1inparticular.corefunctionality.teleportation.Teleportation_Main;
import com.no1inparticular.corefunctionality.utility.Utility_Main;
import com.no1inparticular.corefunctionality.warps.Warps_Main;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

/**
 * Main class for the plugin that handles the different sections and enabling/disabling
 * @author Thomas Ali
 *
 */
public class CoreFunctionality extends JavaPlugin {

	private static CoreFunctionality plugin; // Static instance so modules can reference main class if required
	public HashMap<String, Module> modules; // Storing of the modules with name as key
	public static Economy econ = null; // Varaible for economy hook/integration
	/**
	 * Enable method for the plugin, gets run on load
	 */
	public void onEnable() {
		plugin = this; // Set the static instance of this class for the modules to reference
		modules = new HashMap<String, Module>(); // Instance of the module list/set

		// Adding all the modules to be loaded
		loadModule(new Groups_Main());
		loadModule(new Moderation_Main());
		loadModule(new Combat_Main());
		// Top 3 are loaded first as some others use the stuff within them, if its enabled
		loadModule(new AFK_Main());
		loadModule(new Chat_Main());
		loadModule(new Home_Main());
		loadModule(new Referrals_Main());
		loadModule(new Security_Main());
		loadModule(new Shops_Main());
		loadModule(new Spawn_Main());
		loadModule(new Teleportation_Main());
		loadModule(new Utility_Main());
		loadModule(new Warps_Main());
		
		getLogger().info("Plugin has finished enabling.");
	}
	
	/**
	 * Disable method, ran when plugin is unloaded
	 */
	public void onDisable() {
		for(String moduleName : modules.keySet()) { // Loop through all the module names/keys
			Module module = modules.get(moduleName); // Get the module
			getLogger().info("");
			getLogger().info("--------------------");
			if(module.enabled) { // If its enabled
				getLogger().info("Disabling the " + moduleName + " module...");
				try {
					module.disable(); // Try disable it
					getLogger().info("Finished disabling!");
				} catch (Exception e) {
					getLogger().info("Error whilst disabling " + moduleName + " module!");
					e.printStackTrace();
				}
			} else {
				getLogger().info("Skipping " + moduleName + " module (Already disabled)");
			}
			getLogger().info("--------------------");
		}
	}
	
	public void loadModule(Module module) {
		String moduleName = module.getClass().getSimpleName().split("_Main")[0];
		modules.put(moduleName, module);
		
		getLogger().info("");
		getLogger().info("--------------------");
		if(module.enabled) { // If its enabled
			getLogger().info("Enabling the " + moduleName + " module...");
			try {
				module.enable(); // Try run the enable code (try so can prevent crashing)
				getLogger().info("Finished enabling!");
			} catch (Exception e) {
				getLogger().info("Error whilst enabling " + moduleName + " module!");
				module.enabled = false; // Set enabled to false since it failed to load
			}
		} else {
			getLogger().info("Skipping " + moduleName + " module (Not enabled)");
		}
		getLogger().info("--------------------");
		
	}

	public static CoreFunctionality getPlugin() {
		return plugin;
	}
	
	public boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public boolean citizensIsPresent() {
		if (getServer().getPluginManager().getPlugin("Citizens") == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("corefunctionality")) {
			
			if(sender.hasPermission("corefunctionality.manage")) {
				
				if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.GREEN + "[CoreFunctionality] Help:"
										+ "\n" + ChatColor.GOLD + "/cf modules: " + ChatColor.WHITE + "Show all the modules"
										+ "\n" + ChatColor.GOLD + "/cf enable <moduleName>: " + ChatColor.WHITE + "Enable the specified module"
										+ "\n" + ChatColor.GOLD + "/cf disable <moduleName>: " + ChatColor.WHITE + "Disable the specified module");
					
				} else if (args[0].equalsIgnoreCase("modules")) { 
					
					String moduleList = "";
					for(String name : modules.keySet()) {
						Module m = modules.get(name);
						if(m.enabled) {
							moduleList += ChatColor.GREEN + name + ChatColor.WHITE + ", ";
						} else {
							moduleList += ChatColor.RED + name + ChatColor.WHITE + ", ";
						}
					}
					moduleList = moduleList.substring(0, moduleList.length()-2);
					sender.sendMessage(ChatColor.GOLD + "[CoreFunctionality] Modules:\n" + moduleList);
					
				} else if (args[0].equalsIgnoreCase("enable")) {
					
					if(args.length > 1) {
						String moduleName = args[1];
						if(modules.keySet().contains(moduleName)) {
							Module m = modules.get(moduleName);
							if(m.enabled) {
								sender.sendMessage(ChatColor.RED + "Module is already enabled.");
							} else {
								try {
									m.enable();
									m.enabled = true;
									sender.sendMessage(ChatColor.GREEN + "Enabled the " + moduleName + " module.");
								} catch (Exception e) {
									e.printStackTrace();
									sender.sendMessage(ChatColor.RED + "An error occured whilst trying to enable the " + moduleName + " module.");
								}
								
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Could not find specified module.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Please specify the module name.");
					}
					
				} else if (args[0].equalsIgnoreCase("disable")) {
					
					if(args.length > 1) {
						String moduleName = args[1];
						if(modules.keySet().contains(moduleName)) {
							Module m = modules.get(moduleName);
							if(!m.enabled) {
								sender.sendMessage(ChatColor.RED + "Module is already disabled.");
							} else {
								m.disable();
								m.enabled = false;
								sender.sendMessage(ChatColor.GREEN + "Disabled the " + moduleName + " module.");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Could not find specified module.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Please specify the module name.");
					}
					
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid argument, please do /cf help.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have the power to do this.");
			}
			
		}
		return false;
	}
	
	public void unregisterCommand(String command) {
		CommandMap commandMap = ((CraftServer) this.getServer()).getCommandMap();
		try {
            final Field f = commandMap.getClass().getDeclaredField("knownCommands");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
			Map<String, Command> cmds = (Map<String, Command>) f.get(commandMap);
            cmds.remove(command);
            f.set(commandMap, cmds);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
