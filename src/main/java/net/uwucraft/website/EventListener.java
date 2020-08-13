package net.uwucraft.website;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import org.bukkit.event.player.PlayerAdvancementDoneEvent;

//TODO: GENERATE LOG
public class EventListener implements Listener {

    public EventExecutor executor;
    public Plugin plugin;

    EventListener(Plugin plugin) {
        this.executor = new EventExecutor();
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        executor.join(event.getPlayer().getName());
        try {
            executor.execute();
        } catch (IOException e) {
            Bukkit.getLogger().info("Failed to send join event");
        }
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        executor.leave(event.getPlayer().getName());
        try {
            executor.execute();
        } catch (IOException e) {
            Bukkit.getLogger().info("Failed to send leave event");
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        String killer;
        if (event.getEntity().getKiller() instanceof Player) {
            killer = event.getEntity().getKiller().getName();
        } else {
            // TODO: GET MOB NAME
            killer = "";
        }
        executor.death(event.getEntity().getName(), killer, event.getDeathMessage());
        CompletableFuture.runAsync(() -> {
            try {
                executor.execute();
            } catch (IOException e) {
                Bukkit.getLogger().info("Failed to send death event");
            }
        });

    }

    @EventHandler
    public void playerAdvancementEvent(PlayerAdvancementDoneEvent event) {
        // This Event Called Multiple Time
        Advancement advancement = event.getAdvancement();
        String advancementName = advancement.getKey().getKey();
        String playerName = event.getPlayer().getName();
        String message = playerName + " has just gotten " + advancementName;
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                try {
                    //This Method has to be delayed
                    executor.advancement(advancementName, playerName, message);
                    executor.execute();
                    Bukkit.getLogger().info("success sending " + advancementName);
                } catch (IOException e) {
                    Bukkit.getLogger().info("Failed to send advancement event " + advancementName);
                    e.printStackTrace();
                }
            }
        }, 100L);
    }
}