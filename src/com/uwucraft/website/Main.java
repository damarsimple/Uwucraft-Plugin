package com.uwucraft.website;

import java.sql.SQLException;
//Spagehtti Code Coming UP

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.uwucraft.website.sql.MySQL;
import com.uwucraft.website.sql.SQLGetter;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener{

	public MySQL SQL;
	public SQLGetter data;
	public Economy eco;
	public long lastSync = System.currentTimeMillis();
	@Override
	public void onEnable()
	{	
		this.saveDefaultConfig();
		//Start SQL
		this.SQL = new MySQL(
				this.getConfig().getString("host"),
				Integer.parseInt(this.getConfig().getString("port")),
				this.getConfig().getString("database"),
				this.getConfig().getString("username"),
				this.getConfig().getString("password"),
				this.getConfig().getString("SSL")
				);
		this.data = new SQLGetter(this);
	
		try {
			SQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			Bukkit.getLogger().info("[UWUCRAFT] Database is not Connected");
			Bukkit.getLogger().info("[UWUCRAFT] Incorrect Information?");
		}
		if(SQL.isConnected())
		{
			Bukkit.getLogger().info("[UWUCRAFT] Database is Connected Happy To Go !");
			data.createTable();
			//this.getServer().getPluginManager().registerEvents(new ShopEvent(this), this);
			this.getServer().getPluginManager().registerEvents(new PlayerEvent(this), this);
		}
		//Start Economy
		if(!setupEconomy())
		{
			Bukkit.getLogger().info("[UWUCRAFT] Thou must have Vault !" + " Disabling Plugins");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		//Start Async Task
		this.RunAsync();
	}
	@Override
	public void onDisable()
	{
		SQL.disconnect();
	}
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economy !=null)
		{
			eco = economy.getProvider();
			return(eco != null);

		}
		return false;
	}
	private void RunAsync()
	{
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
	          public void run() {
	        	  Main.this.RunSync();

	          }
	        },  20L, 20L);
	}
	private void RunSync()
	{
		if(!Bukkit.getOnlinePlayers().isEmpty() && System.currentTimeMillis() - this.lastSync >= Integer.parseInt(this.getConfig().getString("interval")) * 60 *1000)
		{
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
		        this.data.SyncData(player);
				}
			this.lastSync = System.currentTimeMillis();
		}
	}
//	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
//	{
//		if (label.equalsIgnoreCase("uwu"))
//		{
//			if(sender instanceof Player)
//			{
//				sender.sendMessage(ChatColor.DARK_RED + "You're Not Allowed to run this command !");
//			}
//			else
//			{ 
//				sender.sendMessage("/uwu shop withdraw|deposit|inventory player value|item");
//				String first = args[0].toLowerCase();
//				switch(first)
//					{
//						case "shop":
//						sender.sendMessage("b");
//						return true;
//						default:
//							sender.sendMessage("/uwu shop withdraw|deposit|inventory player value|item");
//						return true;
//					}
//			}
//		}
//		return false;
//	}
}
