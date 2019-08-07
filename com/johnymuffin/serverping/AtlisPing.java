package com.johnymuffin.serverping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import io.utils.*;
import org.bukkit.event.Listener;;

public class AtlisPing extends JavaPlugin implements Listener {

	Logger logger;

	// User Settings
	static String ip = "0.0.0.0";
	static String ServerName = "BetaServer";
	static String host = "http://localhost:3982/api2/?data=";
	static String serverKey = "";
	
	private ConfigReader configReader;
	private int taskId = 0;
	public static final String PLUGIN_FOLDER = "./plugins/AtlisPing";
	private File pluginFolder = new File("./plugins/AtlisPing");
	File config;
	
	public void onLoad() {
		this.config = new File(this.pluginFolder, "config.properties");
		this.logger = getServer().getLogger();
		if (!this.pluginFolder.exists() || !this.pluginFolder.exists()) {
			this.pluginFolder.mkdirs();
		}

		if (!this.config.exists()) {
			try {
				this.config.createNewFile();
			} catch (IOException var2) {
				var2.printStackTrace();
			}
		}
	}
	
	
	public void onEnable() {
		this.configReader = new ConfigReader(this);
		ServerName = this.configReader.getServerName();
		host = this.configReader.getHost();
		serverKey = this.configReader.getServerKey();
		ip = com.johnymuffin.serverping.Utility.getExternalIP();
		this.logger = this.getServer().getLogger();
		this.logger.info("[AtlisPing] Enabling 1.4 - Public");

		taskId = this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				String jsonData = com.johnymuffin.serverping.JsonBuilder.jsonBuild();
				jsonData = com.johnymuffin.serverping.Utility.encodeValue(jsonData);
				try {

					System.setProperty("http.agent", "");
					final URLConnection connection = new URL(host + jsonData)
							.openConnection();
					connection.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
					connection.setConnectTimeout(1000);
					final String jsonearly = IOUtils.toString(connection.getInputStream());
					System.out.println(jsonearly);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, 0L, 1200);

	}

	public void onDisable() {
		this.logger.warning("[AtlisPing] Successfully stopped!");
		Bukkit.getServer().getScheduler().cancelTask(taskId);
	}

	public static String GetIP() {
		return ip;
	}
	public static String GetServerName() {
		return ServerName;
	}
	public static String GetServerKey() {
		return serverKey;
	}

}
