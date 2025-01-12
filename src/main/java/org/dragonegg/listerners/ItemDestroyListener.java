package org.dragonegg.listerners;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.dragonegg.Dragonegg;

import static org.bukkit.Bukkit.getLogger;

public class ItemDestroyListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // Check if the entity is an item and the item is a dragon egg
        if (event.getEntity() instanceof Item item) {
            ItemStack itemStack = item.getItemStack();

            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                getLogger().info("Item " + itemStack.getType() + " (x" + itemStack.getAmount() + ") was deleted by the void!");
            }

            if (itemStack.getType() == Material.DRAGON_EGG) {
                // Check if the damage will destroy the item
                if (event.getFinalDamage() >= item.getHealth() || event.getCause() == DamageCause.VOID) {
                    handleDragonEggDestruction(item);
                }
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().stream()
                .filter(block -> block.getType() == Material.DRAGON_EGG)
                .forEach(block -> {
                    block.setType(Material.AIR);
                    handleDragonEggDestruction(block.getLocation());
                });
    }

    private void handleDragonEggDestruction(Location location) {
        Dragonegg.worldLogger.info("The dragon egg has been destroyed.");

        World endWorld = Bukkit.getWorld("world_the_end");
        if (endWorld != null) {
            Location spawnPos = new Location(endWorld, 0, 255, 0);
            spawnPos.getBlock().setType(Material.DRAGON_EGG);
        }

        location.getWorld().getPlayers().forEach(player ->
                player.sendMessage(ChatColor.RED + "The dragon egg has been destroyed, it is respawning in the end.")
        );
    }

    private void handleDragonEggDestruction(Item item) {
        handleDragonEggDestruction(item.getLocation());
        item.remove();
    }
}