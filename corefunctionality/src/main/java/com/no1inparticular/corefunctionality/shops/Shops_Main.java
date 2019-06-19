package com.no1inparticular.corefunctionality.shops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.no1inparticular.corefunctionality.Module;
import com.no1inparticular.corefunctionality.shops.events.InventoryClick;
import com.no1inparticular.corefunctionality.shops.events.NPCInteract;

import net.md_5.bungee.api.ChatColor;

public class Shops_Main extends Module{

	public HashMap<String, Inventory> shopInvs;
	public HashMap<String, Inventory> categoryInvs;
	
	InventoryClick invclickEvent;
	NPCInteract npcinteractEvent;
	
	@Override
	public void enable() throws Exception {
		if (!main.setupEconomy() ) {
            main.getLogger().severe("Module disabled due to Vault plugin not being found! (Or no economy if vault is present)");
            throw new Exception();
        }
		if (!main.citizensIsPresent() ) {
            main.getLogger().severe("Module disabled due to Citizens plugin not being found!");
            throw new Exception();
        }
		shopInvs = new HashMap<String, Inventory>();
		categoryInvs = new HashMap<String, Inventory>();
		makeInvs();
		invclickEvent = new InventoryClick(this);
		main.getServer().getPluginManager().registerEvents(invclickEvent, main);
		npcinteractEvent = new NPCInteract(this);
		main.getServer().getPluginManager().registerEvents(npcinteractEvent, main);
	}

	@Override
	public void disable() {	
		for (Inventory inv : shopInvs.values()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getOpenInventory().getTopInventory().equals(inv)) {
					p.closeInventory();
				}
			}
		}
		for (Inventory inv : categoryInvs.values()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getOpenInventory().getTopInventory().equals(inv)) {
					p.closeInventory();
				}
			}
		}
		HandlerList.unregisterAll(invclickEvent);
		HandlerList.unregisterAll(npcinteractEvent);
	}

	private void makeInvs() {
		if (config.getConfigurationSection("Shops") != null) {
			for (String invKey : config.getConfigurationSection("Shops").getKeys(false)) {

				Inventory categoryInv = Bukkit.createInventory(null, 9, invKey);
				for (String categoryKey : config.getConfigurationSection("Shops." + invKey).getKeys(false)) {
					// Make categoryInvs
					String categoryItemName = config.getString("Shops." + invKey + "." + categoryKey + ".DisplayItem");

					try {
						ItemStack item = new ItemStack(Material.getMaterial(categoryItemName), 1);
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(categoryKey);

						item.setItemMeta(itemMeta);

						categoryInv.addItem(item);
					} catch (Exception e) {
						Bukkit.getLogger().info("UNABLE TO ADD ITEM: " + "Shops." + invKey + "." + categoryKey + " ("
								+ categoryKey + ")");
					}

					// Make shopInvs
					int slot = 0;
					Inventory shopInv = Bukkit.createInventory(null, 54, invKey + " | " + categoryKey);
					for (String itemKey : config.getConfigurationSection("Shops." + invKey + "." + categoryKey)
							.getKeys(false)) {
						if (itemKey.equals("DisplayItem")) {
							continue;
						}
						if (slot == 36) {
							Bukkit.getLogger().info(
									"Stopped adding items to " + invKey + " | " + categoryKey + " - Category is full.");
							break;
						}
						String name = config.getString("Shops." + invKey + "." + categoryKey + "." + itemKey + ".Name");
						List<String> lore = new ArrayList<String>();
						for (String s : config
								.getStringList("Shops." + invKey + "." + categoryKey + "." + itemKey + ".Lore")) {
							double buyPrice = config
									.getDouble("Shops." + invKey + "." + categoryKey + "." + itemKey + ".Buy");
							double sellPrice = buyPrice / 2;
							s = s.replaceAll("%buyprice%", buyPrice + "").replaceAll("%sellprice%", sellPrice + "");
							lore.add(ChatColor.translateAlternateColorCodes('&', s));
						}
						int damage = config.getInt("Shops." + invKey + "." + categoryKey + "." + itemKey + ".Damage");

						try {
							ItemStack item = new ItemStack(Material.getMaterial(name), 1, (short) damage);
							ItemMeta itemMeta = item.getItemMeta();

							itemMeta.setLore(lore);

							item.setItemMeta(itemMeta);

							shopInv.addItem(item);
						} catch (Exception e) {
							Bukkit.getLogger().info("UNABLE TO ADD ITEM: " + "Shops." + invKey + "." + categoryKey + "."
									+ itemKey + " (" + name + ")");
						}
						slot++;
					}
					for (int x = 36; x < 45; x++) {
						ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
						shopInv.setItem(x, glass);
					}
					ItemStack arrow = new ItemStack(Material.ARROW, 1);
					ItemMeta arrowMeta = arrow.getItemMeta();
					arrowMeta.setDisplayName(ChatColor.GOLD + "Go Back!");
					arrow.setItemMeta(arrowMeta);
					shopInv.setItem(45, arrow);

					ItemStack netherStar = new ItemStack(Material.NETHER_STAR, 1);
					ItemMeta starMeta = netherStar.getItemMeta();
					starMeta.setDisplayName(ChatColor.GOLD + "How To Use The Shop!");
					starMeta.setLore(Arrays.asList(ChatColor.DARK_PURPLE + "-----Buying-----",
							ChatColor.GREEN + "Left click an item to buy 1",
							ChatColor.GREEN + "Shift left click an item to buy 32",
							ChatColor.DARK_PURPLE + "-----Selling-----",
							ChatColor.GREEN + "Right click an item to sell 1",
							ChatColor.GREEN + "Shift right click an item to sell 32"));
					netherStar.setItemMeta(starMeta);
					shopInv.setItem(49, netherStar);
					categoryInvs.put(invKey, categoryInv);
					shopInvs.put(categoryKey, shopInv);
				}
			}
		}
	}

}
