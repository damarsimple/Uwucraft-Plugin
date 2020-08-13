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
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

//TODO: GENERATE LOG
public class EventListener implements Listener {

    public EventExecutor executor;

    EventListener() {
        this.executor = new EventExecutor();
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        executor.join(event.getPlayer().getName());
        try {
            executor.execute();
        } catch (IOException e) {
            Bukkit.getLogger().info("Failed to send join event");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        executor.leave(event.getPlayer().getName());
        try {
            executor.execute();
        } catch (IOException e) {
            Bukkit.getLogger().info("Failed to send leave event");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

    @EventHandler
    public void playerAdvancementEvent(PlayerAdvancementDoneEvent event) {
        //This Event Called Multiple Time
        Advancement advancement = event.getAdvancement();
        Advancement test = Bukkit.getAdvancement(advancement.getKey());
        String advancementName = advancement.getKey().getKey();
        String playerName = event.getPlayer().getName();
        String message = playerName + " has just gotten " + advancementName;
        executor.advancement(advancementName, playerName, message);
        CompletableFuture.runAsync(() -> {
            try {
                executor.execute();
            } catch (IOException | InterruptedException e) {
                Bukkit.getLogger().info("Failed to send advancement event");
            }
        });
        System.out.println(advancementName + " " + playerName + test.getKey().getNamespace());
    }

}