package com.uwucraft.website.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class MySQL{

	private Connection connection;
	private String host;
	private Integer port;
	private String database;
	private String username;
	private String password;
	private String SSL;
	public MySQL(String host,Integer port,String database,String username,String password,String SSL)
	{
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		this.SSL = SSL;
	}
	public boolean isConnected()
	{
		return (connection  == null ? false : true);
	}
	
	public void connect() throws ClassNotFoundException, SQLException
	{
		if(!isConnected()) {
		   	connection = DriverManager.getConnection("jdbc:mysql://" +
				   this.host + ":" + this.port + "/" + this.database + "?useSSL=" + this.SSL,
				   this.username, this.password);

		}
	}
	
	public void disconnect()
	{
		if(isConnected())
		{
			try {
				connection.close();
			}catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	public Connection getConnection() {
		return connection;
	}
}
