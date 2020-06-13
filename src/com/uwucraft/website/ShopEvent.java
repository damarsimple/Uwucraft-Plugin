package com.uwucraft.website;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ShopEvent implements Listener{
	
	private Main plugin;
	public ShopEvent(Main plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void onKill(EntityDeathEvent event)
	{
		if(event.getEntity() instanceof Mob)
		{
			Player player = event.getEntity().getKiller();
			if(player == null)
			{
				return;
			}
			
			Random r = new Random();
			int amount = r.nextInt(1000);
			plugin.eco.depositPlayer(player, amount);
			player.sendMessage("You get " + amount + "$");
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("hello"))
		{
			if(sender instanceof Player)
			{ //Players
				Player player = (Player) sender;
				if(player.hasPermission("hello.use"))
				{
					player.sendMessage(ChatColor.LIGHT_PURPLE + "" + "Hey Welcome !");
					return true;
				}else
				{
					player.sendMessage(ChatColor.RED + "No Permission!");
					return true;
				}
			}
			else
			{ //Console
				sender.sendMessage("Console !");
				return true;
			}
		}
		return false;
	}
}
