package commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gigabytedx.rpgleveling.Main;
import com.gigabytedx.rpgleveling.item.AddItemToInventory;
import com.gigabytedx.rpgleveling.item.Item;

public class ViewItems implements CommandExecutor {

	Main plugin;

	public ViewItems(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			List<Item> items = plugin.items.getItems();
			player.sendMessage("size of items is: " + items.size());
			Inventory inv = Bukkit.createInventory(player, 27, ChatColor.DARK_BLUE + "All available items");
			
			for (Item item : items) {
				try {
					System.out.println(item.getName());
					System.out.println(
							plugin.playerFoundItemsConfig.getConfigurationSection("players." + player.getUniqueId())
									.getList("found items").toString());
					if (plugin.playerFoundItemsConfig.getConfigurationSection("players." + player.getUniqueId())
							.getList("found items").contains(item.getName())) {
						inv = AddItemToInventory.addItem(inv, item, plugin, true, true);
					}
				} catch (NullPointerException e) {
					System.out.println("Doesnt have a location name");
				}
			}
			
			for (Item item : items) {
				try {
					if (!(plugin.playerFoundItemsConfig.getConfigurationSection("players." + player.getUniqueId())
							.getList("found items").contains(item.getName()))) {
						inv = AddItemToInventory.addItem(inv, null, plugin, false, true);
					}
				} catch (NullPointerException e) {
					System.out.println("Doesnt have a location name");
				}
			}

			
			player.openInventory(inv);
		}
		return false;
	}
}
