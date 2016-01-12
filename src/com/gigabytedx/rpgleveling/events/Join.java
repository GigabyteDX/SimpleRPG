package com.gigabytedx.rpgleveling.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.gigabytedx.rpgleveling.Main;

public class Join implements Listener {

	private Main plugin;

	public Join(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = (Player) event.getPlayer();

		// initialize player with 0 xp if they aren't already saved in the xp
		// file
		try{
			plugin.playerFoundItemsConfig.getConfigurationSection("players")
					.createSection(player.getUniqueId().toString());
			plugin.playerFoundItemsConfig.getConfigurationSection("players." + player.getUniqueId().toString())
					.createSection("found items");

			plugin.saveCustomConfig(plugin.playerFoundItemsFile, plugin.playerFoundItemsConfig);
		}catch(NullPointerException e){
			plugin.playerFoundItemsConfig.createSection("players." + player.getUniqueId() + ".found items");
			plugin.saveCustomConfig(plugin.playerFoundItemsFile, plugin.playerFoundItemsConfig);
		}
		
		for(int itemSlot = 3; itemSlot < 9; itemSlot++){
			event.getPlayer().getInventory().setItem(itemSlot, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));
		}
		
	}
	
	
}
