package net.uwucraft.website;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getLogger().info("test");
    }

    public void onDisable() {
        getLogger().info("end");
    }
}