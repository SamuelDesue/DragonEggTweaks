package org.dragonegg;

import org.dragonegg.listerners.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Dragonegg extends JavaPlugin {

    public static Logger worldLogger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ItemDestroyListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().enablePlugin(this);
        worldLogger = getLogger();
    }

    @Override
    public void onDisable() {
        getServer().getPluginManager().disablePlugin(this);

    }
}
