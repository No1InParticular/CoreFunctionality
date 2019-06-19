package com.no1inparticular.corefunctionality;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Base class for a module of the plugin
 * @author Thomas Ali
 *
 */
public abstract class Module {

	public CoreFunctionality main = CoreFunctionality.getPlugin();
	
	public File configFile;
	public FileConfiguration config;
	
	public boolean enabled;
	
	public Module() {
		makeConfig();
		enabled = config.getBoolean("enabled");
	}
	
	/**
	 * Enable method for the module which the sub-class must inherit
	 * @throws Exception Exception thrown when fails to load
	 */
	public abstract void enable() throws Exception; // Throws exception because some require to

	/**
	 * Disable method for the module which the sub-class must inherit
	 */
	public abstract void disable();
	
	public void makeConfig() {
		// File name is simple class name (Module for this class but when extended its <module>_Main (for example AFK_Main) so split at _Main and take the first part
		configFile = new File(main.getDataFolder() + "/" + this.getClass().getSimpleName().split("_Main")[0] + ".yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		config.addDefault("enabled", true);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
