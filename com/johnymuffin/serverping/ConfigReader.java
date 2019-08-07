package com.johnymuffin.serverping;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;

class ConfigReader {

	private static String host = "https://node.johnymuffin.com/api2";
	private static String serverName = "BetaServer";
	private static String serverKey = "";

	ConfigReader(AtlisPing instance) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(instance.config));
		} catch (FileNotFoundException var4) {
			instance.logger.warning("[RetroBot] No properties found! Making new file...");
			var4.printStackTrace();
		} catch (IOException var5) {
			var5.printStackTrace();
		}
		// New Config
		host = prop.getProperty("host", host);
		serverName = prop.getProperty("serverName", serverName);
		serverKey = prop.getProperty("serverKey", serverKey);
		prop.setProperty("host", host);
		prop.setProperty("serverName", serverName);
		prop.setProperty("serverKey", serverKey);

		try {
			prop.store(new FileOutputStream(instance.config), "Properties for DiscordBot");
		} catch (Exception var3) {
			instance.logger.severe("Failed to save properties for RetroBot!");
			var3.printStackTrace();
		}
	}

	String getServerName() {
		return serverName;
	}
	
	String getHost() {
		return host;
	}
	
	String getServerKey() {
		return serverKey;
	}
	

	void onDisable() {
		serverName = null;
		host = null;
	}
}
