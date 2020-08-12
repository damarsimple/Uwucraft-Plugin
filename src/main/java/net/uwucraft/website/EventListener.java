package net.uwucraft.website;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class EventListener implements Listener {

    public EventExecutor executor;

    EventListener() {
        this.executor = new EventExecutor();
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {

    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {

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
                Bukkit.getLogger().info("Failed to send event");
            }
        });

    }

    @EventHandler
    public void playerAdvancementEvent(PlayerAdvancementDoneEvent event) {
        Player player = (Player) event.getAdvancement().get
    }

}