package net.uwucraft.website;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getLogger().info("test");
        try {
            EventExecutor.executeWebhooks("Server has been Turn On");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onDisable() {
        try {
            EventExecutor.executeWebhooks("Server has been Turn Off");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getLogger().info("end");
    }
}