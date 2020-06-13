package com.uwucraft.website.sql;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.uwucraft.website.Main;
import com.uwucraft.website.SerializeItem;


public class SQLGetter {

		private Main plugin;
		private String playerdata;
		private String prefix;
		private SerializeItem SItem;
		public SQLGetter(Main plugin)
		{
			this.plugin = plugin;
			this.prefix = plugin.getConfig().getString("prefix");
			this.playerdata = plugin.getConfig().getString("playerdata");
			this.SItem = new SerializeItem();
		}
		
		public void createTable()
		{
			PreparedStatement ps;
			
			try {
				ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `"+ this.prefix + this.playerdata + "` (`UUID` varchar(36) NOT NULL,`username` varchar(255) NOT NULL,`health` double NOT NULL,`hunger` int(11) NOT NULL,`gamemode` varchar(32) NOT NULL,`level` int(11) NOT NULL,`experience` float NOT NULL,`exhaustion` float NOT NULL,`saturation` float NOT NULL,`air` int(11) NOT NULL,`potionEffect` longtext NOT NULL,`inventory` longtext NOT NULL,`armorInventory` longtext NOT NULL,`offHand` longtext NOT NULL,`enderChest` longtext NOT NULL,`money` double NOT NULL,`last_sync` bigint(20) NOT NULL,`last_seen` bigint(20) NOT NULL, PRIMARY KEY (`UUID`))");
				Bukkit.getLogger().info("[UWUCRAFT] Table Linked !");
				ps.executeUpdate();

			}catch(SQLException e)
		
			{
				Bukkit.getLogger().info("[UWUCRAFT] Cant Create Table !");
			}
		}
		

		@SuppressWarnings("deprecation")
		public void createPlayer(Player player)
		{

		try {
			UUID uuid = player.getUniqueId();
				if(!exist(uuid))
				{
					PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO `"+ this.prefix + this.playerdata + "` (`UUID`, `username`, `health`, `hunger`, `gamemode`, `level`, `experience`, `exhaustion`, `saturation`, `air`, `potionEffect`, `inventory`, `armorInventory`, `offHand`, `enderChest`, `money`, `last_sync`, `last_seen`) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					ps2.setString(1, player.getUniqueId().toString());
					ps2.setString(2, player.getName());
					ps2.setDouble(3, player.getHealth());
					ps2.setInt(4, player.getFoodLevel());
					ps2.setString(5, player.getGameMode().toString());
					ps2.setInt(6, player.getLevel());
					ps2.setFloat(7, player.getExp());
					ps2.setFloat(8, player.getExhaustion());
					ps2.setFloat(9, player.getSaturation());
					ps2.setInt(10, player.getRemainingAir());
					ps2.setString(11, player.getActivePotionEffects().toString());
					ps2.setString(12, this.SItem.ItemStackToString(player.getInventory().getContents()));
					ps2.setString(13, this.SItem.ItemStackToString(player.getInventory().getArmorContents()));
					ps2.setString(14, player.getInventory().getItemInOffHand().toString());
					ps2.setString(15, this.SItem.ItemStackToString(player.getEnderChest().getContents()));
					ps2.setDouble(16, plugin.eco.getBalance(player.getName()));
					ps2.setInt(17, 	Math.toIntExact(System.currentTimeMillis()/1000));
					ps2.setInt(18, 	Math.toIntExact(System.currentTimeMillis()/1000));
					
					ps2.executeUpdate();
					Bukkit.getLogger().info("[UWUCRAFT] Creating " + player.getName() + " To Database");
		
				}
			}catch(Exception e)
			{
				Bukkit.getLogger().info("[UWUCRAFT] Cant Create " + player.getName() + " To Database");
			}
		}
		
		public boolean exist(UUID uuid)
		{
			try {
				PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM " + this.prefix + this.playerdata +  " WHERE UUID=?");
				ps.setString(1, uuid.toString());
				
				ResultSet results = ps.executeQuery();
				if(results.next())
				{
					return true;
				}
				return false;
			} catch(SQLException e)
			{
				e.printStackTrace();
			}
			return false;
		}
		@SuppressWarnings("deprecation")
		public void SyncData(Player player)
		{
			try {
				PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE " + this.prefix + this.playerdata + " SET health = ?, hunger = ?,gamemode = ?, level = ?, experience = ?, exhaustion = ?,saturation = ?,air = ?,potionEffect = ?,inventory = ?,armorInventory = ?, offHand = ?,enderChest = ?,money = ?,last_sync = ? WHERE UUID = ?");
				ps.setDouble(1, player.getHealth());
				ps.setInt(2, player.getFoodLevel());
				ps.setString(3, player.getGameMode().toString());
				ps.setInt(4, player.getLevel());
				ps.setFloat(5, player.getExp());
				ps.setFloat(6, player.getExhaustion());
				ps.setFloat(7, player.getSaturation());
				ps.setInt(8, player.getRemainingAir());
				ps.setString(9, player.getActivePotionEffects().toString());
				ps.setString(10, this.SItem.ItemStackToString(player.getInventory().getContents()));
				ps.setString(11, this.SItem.ItemStackToString(player.getInventory().getArmorContents()));
				ps.setString(12, player.getInventory().getItemInOffHand().toString());
				ps.setString(13, this.SItem.ItemStackToString(player.getEnderChest().getContents()));
				ps.setDouble(14, plugin.eco.getBalance(player.getName()));
				ps.setInt(15, 	Math.toIntExact(System.currentTimeMillis()/1000));
				ps.setString(16, player.getUniqueId().toString());
				ps.executeUpdate();
				Bukkit.getLogger().info("[UWUCRAFT] Syncing " + player.getName() + " To Database");
			} catch(SQLException e)
			{
				Bukkit.getLogger().info("[UWUCRAFT] Cant Sync " + player.getName());
			}
		}
		public void SyncDataMoney(int money)
		{
			
		}
		public void LastSeen(Player player)
		{
			try {
				PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE "+ this.prefix + this.playerdata+ "  SET last_seen = ?");
				ps.setInt(1, Math.toIntExact(System.currentTimeMillis()/1000));
				ps.executeUpdate();
			}catch(SQLException e)
			{
				Bukkit.getLogger().info("[UWUCRAFT] Cant Update Last Seen");
			}
		}
}
















// Raw Sql
/* CREATE TABLE IF NOT EXISTS `uwucraft`.`mc_playerdata` ( `UUID` VARCHAR(36) NOT NULL , `username` VARCHAR(255) NOT NULL , `health` DOUBLE NOT NULL , `hunger` INT(11) NOT NULL , `gamemode` VARCHAR(32) NOT NULL , `level` INT(11) NOT NULL , `experience` FLOAT NOT NULL , `exhaustion` FLOAT NOT NULL , `saturation` FLOAT NOT NULL , `air` INT(11) NOT NULL , `inventory` LONGTEXT NOT NULL , `armorInventory` LONGTEXT NOT NULL , `offHand` LONGTEXT NOT NULL , `enderChest` LONGTEXT NOT NULL , `money` DOUBLE NOT NULL , `last_sync` BIGINT(20) NOT NULL , `last_seen` BIGINT(20) NOT NULL ) ENGINE = InnoDB; 
 */