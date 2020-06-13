package com.uwucraft.website;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.javaPlugin;


public class Main extends JavaPlugin{
	@Override
	public void onEnable()
	{
		//Startup and Reloads
	}
	@Override
	public void onDisable()
	{
		 //Shutdowns and Reloads
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
