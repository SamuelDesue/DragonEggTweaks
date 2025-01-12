package org.dragonegg.listerners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        // Check if the inventory is not a player inventory and contains a dragon egg
        if (inventory.getType() != InventoryType.PLAYER && inventory.contains(Material.DRAGON_EGG)) {
            for (ItemStack item : inventory.getContents()) {
                if (item != null && item.getType() == Material.DRAGON_EGG) {
                    // Remove the dragon egg from the container
                    inventory.remove(item);
                    // Drop the dragon egg on top of the container
                    player.getWorld().dropItem(inventory.getLocation().add(0,1,0), item);
                    player.sendMessage(ChatColor.RED + "The" + ChatColor.DARK_PURPLE + " dragon egg " + ChatColor.RED + "cannot be stored in a container and has been dropped.");
                }
            }
        }
    }
}