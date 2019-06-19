package com.no1inparticular.corefunctionality.shops.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.no1inparticular.corefunctionality.CoreFunctionality;
import com.no1inparticular.corefunctionality.shops.Shops_Main;

import net.md_5.bungee.api.ChatColor;

public class InventoryClick implements Listener {
	
	Shops_Main shops;
	public InventoryClick(Shops_Main instance) {
		shops = instance;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory() == null || event.getClickedInventory() == null) {
			return;
		}
		for (Inventory inv : shops.categoryInvs.values()) {
			if(event.getWhoClicked().getOpenInventory().getTopInventory().equals(inv))
				event.setCancelled(true);
			if (event.getClickedInventory().equals(inv)) {
				event.setCancelled(true);
				if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
					return;
				}

				Player player = (Player) event.getWhoClicked();
				ItemStack item = event.getCurrentItem();
				String name = item.getItemMeta().getDisplayName();
				player.openInventory(shops.shopInvs.get(name));
			}
		}
		for (Inventory inv : shops.shopInvs.values()) {
			if(event.getWhoClicked().getOpenInventory().getTopInventory().equals(inv))
				event.setCancelled(true);
			if (event.getClickedInventory().equals(inv)) {
				event.setCancelled(true);
				if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
					return;
				}

				Player player = (Player) event.getWhoClicked();
				ItemStack item = event.getCurrentItem();
				
				if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
					if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Go Back!")) {
						Inventory prevInv = shops.categoryInvs.get(((InventoryView) event.getWhoClicked().getOpenInventory().getTopInventory()).getTitle().split(" ")[0].trim());
						player.closeInventory();
						player.openInventory(prevInv);
						return;
					}
				}
				
				if (item.getType().toString().contains("STAINED_GLASS_PANE") || item.getType() == Material.NETHER_STAR) {
					return;
				}
				
				double buyPrice = 0;
				double sellPrice = 0;
				for (String line : item.getItemMeta().getLore()) {
					if (line.startsWith("Buy:")) {
						buyPrice = Double.parseDouble(line.split(" ")[1]);
					} else if (line.startsWith("Sell:")) {
						sellPrice = Double.parseDouble(line.split(" ")[1]);
					}
				}

				if (event.isLeftClick()) {
					if (player.getInventory().firstEmpty() == -1) {
						player.sendMessage(ChatColor.RED + "You do not have enough space in your inventory for this!");
						event.setCancelled(true);
						return;
					}
				}

				if (event.isLeftClick() && !event.isShiftClick()) {
					if (buyPrice <= CoreFunctionality.econ.getBalance(player)) {
						try {
							player.getInventory().addItem(new ItemStack(item.getType(), 1, item.getDurability()));
							CoreFunctionality.econ.withdrawPlayer(player, buyPrice);
							player.updateInventory();
							player.sendMessage(ChatColor.GREEN + "Purchased a(n) " + ChatColor.GOLD + item.getType().name().toLowerCase() + ChatColor.GREEN + "!");
							event.setCancelled(true);
						} catch (Exception e) {
							player.sendMessage(ChatColor.RED + "You do not have enough space in your inventory for this!");
							event.setCancelled(true);
							return;
						}
					} else {
						player.sendMessage(ChatColor.RED + "You do not have enough money to buy a(n) " + ChatColor.GOLD + item.getType().name().toLowerCase() + ChatColor.RED + "!");
						event.setCancelled(true);
					}
				} else if (event.isLeftClick() && event.isShiftClick()) {
					if (((InventoryView) event.getWhoClicked().getOpenInventory().getTopInventory()).getTitle().contains("Blacksmith")) {
						player.sendMessage(ChatColor.RED + "You cannot purchase multiple items at once from the blacksmith!");
						event.setCancelled(true);
					}else if (buyPrice * 32 <= CoreFunctionality.econ.getBalance(player)) {
						try {
							CoreFunctionality.econ.withdrawPlayer(player, buyPrice * 32);
							player.getInventory().addItem(new ItemStack(item.getType(), 32, item.getDurability()));
							player.updateInventory();
							player.sendMessage(ChatColor.GREEN + "Purchased 32 " + ChatColor.GOLD + item.getType().name().toLowerCase() + "(s)" + ChatColor.GREEN +"!");
							event.setCancelled(true);
						} catch (Exception e) {
							player.sendMessage(ChatColor.RED + "You do not have enough space in your inventory for this!");
							event.setCancelled(true);
							return;
						}
					} else {
						player.sendMessage(ChatColor.RED + "You do not have enough money to buy 32 " + ChatColor.GOLD + item.getType().name().toLowerCase() + ChatColor.RED + "!");
						event.setCancelled(true);
					}
				} else if (event.isRightClick() && !event.isShiftClick()) {
					if (player.getInventory().containsAtLeast(new ItemStack(item.getType(), 1, item.getDurability()), 1)) {
						CoreFunctionality.econ.depositPlayer(player, sellPrice);
						player.getInventory().removeItem(new ItemStack(item.getType(), 1, item.getDurability()));
						player.updateInventory();
						player.sendMessage(ChatColor.GREEN + "Sold a(n) " + ChatColor.GOLD + item.getType().name().toLowerCase() + ChatColor.GREEN + "!");
						event.setCancelled(true);
					} else {
						player.sendMessage(ChatColor.RED + "You do not have a(n) " + ChatColor.GOLD + item.getType().name().toLowerCase() + ChatColor.RED +"!");
						event.setCancelled(true);
					}
				} else if (event.isRightClick() && event.isShiftClick()) {
					if (player.getInventory().containsAtLeast(new ItemStack(item.getType(), 1, item.getDurability()), 32)) {
						CoreFunctionality.econ.depositPlayer(player, sellPrice * 32);
						for(int x = 0; x < 32; x++) {
							player.getInventory().removeItem(new ItemStack(item.getType(), 1, item.getDurability()));
						}
						player.updateInventory();
						player.sendMessage(ChatColor.GREEN + "Sold 32 " + ChatColor.GOLD + item.getType().name().toLowerCase() + "(s)" + ChatColor.GREEN +"!");
						event.setCancelled(true);
					} else {
						player.sendMessage(ChatColor.RED + "You do not have 32 " + ChatColor.GOLD + item.getType().name().toLowerCase() + "(s)" + ChatColor.RED +"!");
						event.setCancelled(true);
					}
				}
				event.setCancelled(true);
			}
		}
	}

}
